package com.njsyg.smarthomeapp.bll.socket_bll;

import android.util.Log;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.EConnectState;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketReceiveThread extends Thread {
    private boolean isStop = false;

    public void setIsStop(boolean value) {
        isStop = value;
    }

    private Selector selector;
    private CallBack callBack;
    private android.os.Handler handler;

    public SocketReceiveThread(Selector selector, CallBack callBack) {
        this.selector = selector;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;
        SocketChannel client;
        String receiveText;

        while (!isStop) {
            if (selector == null | selector.isOpen() == false) {
                return;
            }
            try {
                int readChannels = selector.select();//阻塞至相关事件被触发
                if (readChannels == 0) {
                    continue;
                }
            } catch (IOException e) {
                Log.i("SocketReceiveThread", "selector.select():" + e.getStackTrace().toString());
            } catch (ClosedSelectorException e) {
                Log.i("SocketReceiveThread", "ClosedSelectorException:" + e.getStackTrace().toString());
            }
            try {


                //返回此选择器的已选择键集。
                selectionKeys = selector.selectedKeys();
                iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    try {
                        selectionKey = iterator.next();
                        if (selectionKey.isReadable()) {
                            ByteBuffer receiveBuffer = ByteBuffer.allocate(10240);
                            client = (SocketChannel) selectionKey.channel();

                            byte[] data = null;
                            while (receiveBuffer.hasRemaining()) {
                                try {

                                    int bytesRead = client.read(receiveBuffer);
                                    if (bytesRead == 0) {
                                        //无可读数据，跳出
                                        Log.d("test", "无可读数据，跳出");
                                        break;
                                    } else if (bytesRead < 0) {
                                        //连接关闭/断开
                                        //callBack.updConnectState(EConnectState.Exception);
                                        break;
                                    } else {
                                        receiveBuffer.flip();//设置为可读状态
                                        if (data == null) {//首次读取数据
                                            data = new byte[bytesRead];
                                            receiveBuffer.get(data, 0, bytesRead);
                                        } else {//读取未完成数据
                                            byte[] receiveData = new byte[bytesRead];
                                            receiveBuffer.get(receiveData, 0, bytesRead);

                                            byte[] tmpData = new byte[data.length + bytesRead];
                                            System.arraycopy(data, 0, tmpData, 0, data.length);
                                            System.arraycopy(receiveData, 0, tmpData, data.length, receiveData.length);

                                            data = tmpData;
                                        }
                                        receiveBuffer.clear();//清空数据
                                    }
                                } catch (IOException e) {
                                    //Log.d("SocketReceiveThread","IOException连接异常"+e.toString());
                                    //callBack.updConnectState(EConnectState.Exception);
                                } catch (NotYetConnectedException e) {
//                            Log.d("SocketReceiveThread","NotYetConnectedException连接异常"+e.toString());
//                            //连接异常
//                            callBack.updConnectState(EConnectState.Exception);
                                }
                            }
                            //数据不为空
                            if (data != null && data.length > 0) {
                                receiveText = byte2HexStr(data);
                                Log.i("SocketReceiveThread", "接受数据--:" + receiveText);
                                callBack.readData(data);
                                Log.i("SocketReceiveThread", "接受数据--:callBack.readData(data)");
                                //正常获取数据，连接通道正常，更新连接状态
                                callBack.updConnectState(EConnectState.Normal);
                            }
                            try {
                                client.register(selector, SelectionKey.OP_READ);
                            } catch (ClosedChannelException e) {
                                Log.i("SocketReceiveThread", "register1:" + e.getStackTrace().toString());
                            } catch (CancelledKeyException e) {
                                Log.i("SocketReceiveThread", "register2:" + e.getStackTrace().toString());
                            } catch (NullPointerException e) {
                                Log.i("SocketReceiveThread", "NullPointerException:" + e.getStackTrace().toString());
                            }
                        }
                    } catch (CancelledKeyException err) {
                        Log.i("socketclient", "selectionKey.isReadable()" + err.getStackTrace().toString());
                    }
                    try {
                        iterator.remove();
                    } catch (Exception err) {
                        Log.i("socketclient", "iterator.remove()" + err.getStackTrace().toString());
                    }
                }
            } catch (ClosedSelectorException err) {
                Log.i("socketclient", "ClosedSelectorException)" + err.getStackTrace().toString());
            }
        }
    }

    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }
}