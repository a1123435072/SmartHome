package com.njsyg.smarthomeapp.bll.socket_bll;

import android.content.Intent;
import android.util.Log;

import com.njsyg.smarthomeapp.bll.basic_bll.CommandHelper;
import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.bll.session_bll;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EConnectState;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.EServerType;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.MyCommandCode;
import com.njsyg.smarthomeapp.common.Const;
import com.njsyg.smarthomeapp.common.Converts;
import com.njsyg.smarthomeapp.fragments.IndexFragment;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.NoConnectionPendingException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Timer;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketClient implements CallBack {
    private final String TAG = "SocketClient";
    // 与服务器通信的信道
    private SocketChannel socketChannel;
    // 信道选择器
    private Selector selector;
    // 服务器对象
    public SocketServerModel serverModel;
    private EServerType eServerType;
    private String hostIp;
    private int hostPort;
    private SocketSendThread sendThread;
    private SocketReceiveThread receiveThread;
    private Timer heartTimer;
    boolean flag = true;

    public SocketClient(EServerType serverType, String hostIp, int hostPort) {
        this.serverModel = new SocketServerModel(serverType, hostIp, hostPort);
        this.eServerType = serverType;
        this.hostIp = hostIp;
        this.hostPort = hostPort;

    }

    public boolean initialize() {
        boolean done = true;
        try {
            // 打开监听信道并设置为非阻塞模式
            socketChannel = SocketChannel.open();
            if (socketChannel != null) {
                socketChannel.socket().setTcpNoDelay(false);
                socketChannel.socket().setKeepAlive(true);
                // 设置 读socket的timeout时间
                socketChannel.socket().setSoTimeout(15 * 1000);
                socketChannel.configureBlocking(false);
            }

            selector = Selector.open();
            if (selector != null) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            //启动数据发送线程
            sendThread = new SocketSendThread();
            sendThread.start();
            //启动数据接收线程
            receiveThread = new SocketReceiveThread(selector, this);
            receiveThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            done = false;
        } finally {
            return done;
        }
    }

    /**
     * 与服务器建立连接
     *
     * @return
     * @throws IOException
     */
    public boolean connect() {
        Log.d("test", "connect:");
        boolean isConnected = false;
        try {
            //如果立即连接成功，该方法返回true，
            // 如果不能立即成功连接，返回false，程序过会必须通过调用finishConnect()方法来完成连接
            isConnected = socketChannel.connect(new InetSocketAddress(hostIp, hostPort));
            Log.d(TAG, hostIp + "connect isConnected:" + isConnected);
            if (!isConnected) {
                int count = 0;
                while (!(isConnected = socketChannel.finishConnect())) {
                    try {
                        count++;
                        Log.d(TAG, hostIp + "/" + hostPort + "与服务器建立连接" + count);
                        //持续无法完成连接
                        if (count > 10) {
                            break;
                        }
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoConnectionPendingException ex) {
            ex.printStackTrace();
        } finally {
            //更新连接状态
            if (isConnected) {
                updConnectState(EConnectState.Normal);
                sendThread.synSendMsg(socketChannel, session_bll.GetSessionInfoByte(), this, null);

            } else {
                //updConnectState(EConnectState.Exception);
            }
            return isConnected;
        }
    }

    /**
     * 关闭连接
     *
     * @return
     */
    public void closeConnect() {
        try {
            if (socketChannel != null) {
                socketChannel.close();
            }

        } catch (IOException e) {

        }
        try {
            if (selector != null) {
                selector.close();
            }

        } catch (IOException e) {
        }

    }

    /**
     * 释放资源
     */
    public void dispose() {
        try {
            socketChannel.close();
            socketChannel = null;
            selector.close();
            selector = null;

            try {
                sendThread.setIsStop(true);
                sendThread.join();
                sendThread = null;

                receiveThread.setIsStop(true);
                receiveThread.join();
                receiveThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getHeartBytes() {
        byte[] myBytes = new byte[11];
        myBytes[0] = (byte) 0xfe;
        myBytes[1] = (byte) 0x01;
        myBytes[2] = (byte) 0x01;

        for (int i = 3; i < 10; i++) {
            myBytes[i] = (byte) 0x00;
        }

        myBytes[myBytes.length - 1] = (byte) 0xff;
        return myBytes;
    }

    @Override
    public void keepAlive() {
        Log.d(TAG, "心跳,连接状态" + hostIp + "/" + hostPort + ":" + serverModel.getIsConnectNor());
        if (serverModel.getIsConnectNor()) {//与服务器连接正常
            byte[] heartBytes = getHeartBytes();
            //心跳，保持长连接
            sendThread.synSendMsg(socketChannel, heartBytes, this, null);
        } else {//与服务器连接异常
            //建立连接
            closeConnect();
            initialize();
            connect();
        }
    }

    @Override
    public void updConnectState(EConnectState state) {
        switch (state) {
            case Normal://连接正常
                serverModel.connectNor();
                break;
            case Exception://连接异常
                serverModel.connectEx();
                Log.d(TAG, hostIp + "/" + hostPort + "Exception:serverModel.connectEx()");
                break;
            case InterruptOrClose://连接中断或关闭
                serverModel.connnectInterrupt();
                Log.d(TAG, hostIp + "/" + hostPort + "Exception:serverModel.connnectInterrupt()");
                break;
        }
    }

    @Override
    public boolean getConnectState() {
        return serverModel.getIsConnectNor();
    }

    @Override
    public void readData(byte[] data) {
        Log.d(TAG, "接收到" + hostIp + "/" + hostPort + "数据" + Converts.byte2HexStr(data));
        CommandStruct commandStruct = CommandHelper.UnPackageCommand(data);
        if (commandStruct != null) {

            if (eServerType == EServerType.HomeServer) {
                switch (commandStruct.getCommandCode()) {
                    case MyCommandCode.DeviceRealonlineRecord:
                    case MyCommandCode.GetDeviceRealData:
                    case MyCommandCode.ReadDeviceSwitchStatus:
                    case MyCommandCode.ReadDeviceElectricityInfo:

                        Log.d(TAG, "发送广播数据：智能设备实时在线状态，智能设备实时数据");
                        Intent i = new Intent(Const.BC);
                        i.putExtra("eServerType", eServerType);
                        i.putExtra("data", data);
                        if (IndexFragment.s_context != null) {
                            IndexFragment.s_context.sendBroadcast(i);   //发送广播
                        }
                        if (readDataDelegate != null) {
                            readDataDelegate.ReadSocketData(eServerType, data);//处理读取的数据
                            break;
                        }
                        break;
                    default:
                        if (readDataDelegate != null) {
                            readDataDelegate.ReadSocketData(eServerType, data);//处理读取的数据
                            break;
                        }
                }
            } else {
                if (readDataDelegate != null) {
                    readDataDelegate.ReadSocketData(eServerType, data);//处理读取的数据

                }
            }
        }
    }

    public void startHeartTimer() {
        heartTimer = new Timer();
        heartTimer.schedule(new SocketTimerTask(this), 3 * 1000, 3 * 1000);
    }

    public void stopHeartTimer() {
        if (heartTimer != null) {
            heartTimer.cancel();
            heartTimer = null;
        }
    }


    /**
     * 同步发送消息
     */
    public void synSendMsg(byte[] bytes, android.os.Handler handler) {
        sendThread.synSendMsg(socketChannel, bytes, this, handler);
    }


    /**
     * 异步发送消息
     */
    public void asySendMsg(byte[] bytes, android.os.Handler sendHandler) {
        sendThread.asySendMsg(socketChannel, bytes, this, sendHandler);
    }

    /**
     * 委托接口，用于对adapter中的数据进行操作，此处提供删除、修改方法
     *
     * @author Change
     */
    public interface ReadDataDelegate {
        /**
         * 修改
         *
         * @param datas
         */
        void ReadSocketData(EServerType type, byte[] datas);
    }

    private ReadDataDelegate readDataDelegate;

    /**
     * 设置委托者
     *
     * @param _delegate
     */
    public void SetReadDataDelegate(ReadDataDelegate _delegate) {
        this.readDataDelegate = _delegate;
    }
}
