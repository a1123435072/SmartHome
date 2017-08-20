package com.njsyg.smarthomeapp.bll.basic_bll;

import android.util.Log;

import com.njsyg.smarthomeapp.bll.basic_bll.CommandStruct;
import com.njsyg.smarthomeapp.common.Converts;


import java.io.ByteArrayInputStream;

/**
 * Created by PCPC on 2016/6/2.
 * 命令打包 解包
 */
public class CommandHelper {
    //命令打包
    public static byte[] PackageCommand(CommandStruct commandStruct) {
        if(commandStruct==null) {
            byte[] bytes = new byte[3];
            bytes[0] = (byte) 0xfe;
            bytes[1] = (byte) 0xee;
            bytes[2] = (byte) 0xff;
            return bytes;
        }else
        {
            Log.d("test","-3");
            int length = commandStruct.getBeginFlag().length+1+1+1;//BeginFlag;ModuleCode;CommandCode;ParameterLength
            if (commandStruct.getParameterData()!= null&&commandStruct.getParameterData().length > 0)
            {
                length += commandStruct.getParameterData().length;
            }
            length += 2;//DataLength
            Log.d("test","-2");
            if (commandStruct.getData() != null && commandStruct.getData().length > 0)
            {
                length += commandStruct.getData().length;
            }
            Log.d("test","-1");
            length += 4;//CRC;Version;
            length+=commandStruct.getEndFlag().length; // EndFlag
            byte[] source = new byte[length];

            int offset=0;
            System.arraycopy(commandStruct.getBeginFlag(), 0, source, offset, commandStruct.getBeginFlag().length); //BeginFlag
            offset+=commandStruct.getBeginFlag().length;
            source[offset] = commandStruct.getModuleCode();
            offset+=1;
            source[offset] = commandStruct.getCommandCode();
            offset+=1;
            //参数
            if (commandStruct.getParameterData() == null || commandStruct.getParameterData().length == 0)
            {
                source[offset] =0x00;
                offset += 1;
            }
            else
            {
                source[offset] = (byte)commandStruct.getParameterData().length;
                offset += 1;
                System.arraycopy(commandStruct.getParameterData(), 0, source, offset, commandStruct.getParameterData().length);
                offset += commandStruct.getParameterData().length;
            }
            Log.d("test","0");

            //数据
            if (commandStruct.getData() == null || commandStruct.getData().length == 0)
            {
                source[offset] =0x00;
                offset += 1;
                source[offset] =0x00;
                offset += 1;
            }
            else
            {
                source[offset] = (byte)commandStruct.getData().length;
                offset += 1;
                source[offset] = (byte)(commandStruct.getData().length>>8);
                offset += 1;
                System.arraycopy(commandStruct.getData(), 0, source, offset, commandStruct.getData().length);
                offset += commandStruct.getData().length;
            }
            Log.d("test","1");
            //CRC
            byte[] crcsource = new byte[offset];
            //Arrays.copyOf(source, crcsource, offset);
            System.arraycopy(source, 0, crcsource, 0,offset);
            //byte[] crc = MyCRC.CRC16(crcsource);
            byte[] crc ={0x00,0x00};
            System.arraycopy(crc, 0, source, offset, 2);
            offset += 2;
            Log.d("test","2");
            //Version
            System.arraycopy(commandStruct.getProtocolVersion(), 0, source, offset, 2);
            offset += 2;
            //EndFlag
            System.arraycopy(commandStruct.getEndFlag(), 0, source, offset,commandStruct.getEndFlag().length);
            Log.d("test","3");
            return source;
        }
    }

    //命令拆包
    public static CommandStruct UnPackageCommand(byte[] source) {
        Log.d("UnPackageCommand",Converts.byte2HexStr(source));
        try {
            CommandStruct commandStruct = new CommandStruct();
            if (source == null | source.length == 0) {
                return null;
            }
            int offset = 0;
            System.arraycopy(source, 0, commandStruct.getBeginFlag(), 0, commandStruct.getBeginFlag().length);//BeginFlag
            offset+=commandStruct.getBeginFlag().length;
            commandStruct.setModuleCode(source[offset]);//模块码
            offset+=1;
            commandStruct.setCommandCode(source[offset]);//功能码
            offset+=1;
            Log.d("test", "0");

            byte[] bytes = new byte[]{source[commandStruct.getBeginFlag().length+2]};
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            int parameterLength = in.read();
            if (offset + parameterLength >= source.length) {
                return null;
            }
            Log.d("test", "1");
            commandStruct.setParameterLength(source[offset]);//参数长度
            commandStruct.setParameterData(new byte[parameterLength]);
            offset+=1;
            System.arraycopy(source, offset, commandStruct.getParameterData(), 0, parameterLength);//参数内容


            offset += parameterLength;
            byte[] datalengthbyte = new byte[2];
            datalengthbyte[0] = source[offset];
            offset += 1;
            datalengthbyte[1] = source[offset];
            commandStruct.setDataLength(datalengthbyte);//数据长度
            offset += 1;
            Log.d("test", "2");

            int dataLength = Converts.byteToInt2(datalengthbyte);
            Log.d("test", "dataLength:" + dataLength);
            commandStruct.setData(new byte[dataLength]);
            System.arraycopy(source, offset, commandStruct.getData(), 0, dataLength);//数据内容
            Log.d("test", "3");
            offset += dataLength;
            commandStruct.setCheckCode(new byte[2]);
            System.arraycopy(source, offset, commandStruct.getCheckCode(), 0, 2);//CRC

//       //校验
//        byte[] crcSource=new byte[offset];
//        System.arraycopy(source, 0, crcSource, 0, offset);
//        byte[] crc = MyCRC.CRC16(crcSource);
//        if (!crc.equals(commandStruct.CheckCode))
//        {
//            return null;//CRC校验失败
//        }
            Log.d("test", "4");
            offset += 2;
            if (offset + 2 >= source.length) {
                return null;
            }
            commandStruct.setProtocolVersion(new byte[2]);
            System.arraycopy(source, offset, commandStruct.getProtocolVersion(), 0, 2);//Version
            offset += 2;
            Log.d("test", "5");
            //Endflag
            System.arraycopy(source, offset, commandStruct.getEndFlag(), 0, commandStruct.getEndFlag().length);//EndFlag
            Log.d("test", "6");
            return commandStruct;
        }
        catch (Exception e)
        {
            Log.d("UnPackageCommand",e.toString());
            return null;
        }
    }
}