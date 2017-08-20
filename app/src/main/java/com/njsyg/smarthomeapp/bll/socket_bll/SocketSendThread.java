package com.njsyg.smarthomeapp.bll.socket_bll;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.njsyg.smarthomeapp.bll.socket_bll.enums.EConnectState;
import com.njsyg.smarthomeapp.bll.socket_bll.enums.ESendDataState;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zz on 2016/10/21.
 */
public class SocketSendThread extends Thread {
    private boolean isStop = false;
    public void setIsStop(boolean value) {
        isStop = value;
    }
    private static String tag = "socketOutputThread";
    private List<SendMsgEntity> sendMsgEntityList;
    public SocketSendThread() {
        sendMsgEntityList = new CopyOnWriteArrayList<SendMsgEntity>();
    }

    /**
     * 同步发送数据
     */
    public void synSendMsg(SocketChannel socketChannel, byte[] bytes, CallBack callBack, Handler handler) {
        synchronized (this) {
            SendMsgEntity sendMsgEntity = new SendMsgEntity();
            sendMsgEntity.socketChannel = socketChannel;
            sendMsgEntity.bytes = bytes;
            sendMsgEntity.callBack = callBack;
            sendMsgEntity.sendHandler = handler;
            sendMsg(sendMsgEntity);
        }
    }

    /**
     * 异步发送数据
     */
    public void asySendMsg(SocketChannel socketChannel, byte[] bytes, CallBack callBack, Handler handler) {
        synchronized (this) {
            SendMsgEntity sendMsgEntity = new SendMsgEntity();
            sendMsgEntity.socketChannel = socketChannel;
            sendMsgEntity.bytes = bytes;
            sendMsgEntity.callBack = callBack;
            sendMsgEntity.sendHandler = handler;
            sendMsgEntityList.add(sendMsgEntity);
            notify();
        }
    }

    /**
     * 发送数据
     */
    private void sendMsg(SendMsgEntity sendMsgEntity) {
        boolean done = false;
        ByteBuffer writeBuffer = ByteBuffer.wrap(sendMsgEntity.bytes);
        while (writeBuffer.hasRemaining()) {
            try {
                if (sendMsgEntity.socketChannel != null) {
                    sendMsgEntity.socketChannel.write(writeBuffer);
                    if (sendMsgEntity.sendHandler != null)//返回发送成功
                    {
                        Message message = new Message();
                        message.obj = sendMsgEntity.bytes;
                        message.what = ESendDataState.Success.ordinal();
                        sendMsgEntity.sendHandler.sendMessage(message);
                    }
                    done = true;
                }

            } catch (IOException e) {
                Log.d("SocketSendThread", "发送数据异常:IOException ." + e.getMessage().toString());
                done = false;
            } catch (NotYetConnectedException e) {
                Log.d("SocketSendThread", "发送数据异常:NotYetConnectedException ." + e.getMessage().toString());
                done = false;
            } finally {
                if (done == false) {
                    Log.d("SocketSendThread", "发送数据异常: .");
                    sendMsgEntity.callBack.updConnectState(EConnectState.Exception);
                    if (sendMsgEntity.sendHandler != null)//返回发送失败
                    {
                        Message message = new Message();
                        message.obj = sendMsgEntity.bytes;
                        message.what = ESendDataState.Failed.ordinal();
                        sendMsgEntity.sendHandler.sendMessage(message);
                    }
                    break;
                }
            }
        }

//        Message message = new Message();
//        message.obj = sendMsgEntity.callBack;
//        if (done) {
//            message.what = EConnectState.Normal.ordinal();
//        } else {
//            message.what = EConnectState.Exception.ordinal();
//        }
//        sendMsgEntity.handler.sendMessage(message);
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                // 锁发送list
                synchronized (sendMsgEntityList) {
                    // 发送消息
                    for (SendMsgEntity sendMsgEntity : sendMsgEntityList) {
                        sendMsg(sendMsgEntity);
                        sendMsgEntityList.remove(sendMsgEntity);
                    }
                }
                synchronized (this) {
                    wait();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送命令对象类
     */
    public class SendMsgEntity {
        public SocketChannel socketChannel;
        public byte[] bytes;
        public CallBack callBack;
        public Handler sendHandler;
    }
}
