package com.njsyg.smarthomeapp.bll.basic_bll;

/**
 * Created by PCPC on 2016/6/12.
 */
public class CommandStruct {
    private final byte[]  BeginFlag={(byte)0xfe,(byte)0xfe};//开始标志
    private byte ModuleCode;//模块编码
    private byte CommandCode;//功能编码
    private byte ParameterLength;//参数长度
    private byte[] ParameterData;//参数内容
    private byte[] DataLength;//数据长度
    private byte[] Data;//数据内容
    private byte[] CheckCode;//校验码
    private byte[] ProtocolVersion;//协议版本
    private final byte[] EndFlag={(byte)0xff,(byte)0xff};//结束标志

    public byte[] getBeginFlag() {
        return BeginFlag;
    }

    public byte getModuleCode() {
        return ModuleCode;
    }

    public void setModuleCode(byte moduleCode) {
        ModuleCode = moduleCode;
    }

    public byte getCommandCode() {
        return CommandCode;
    }

    public void setCommandCode(byte commandCode) {
        CommandCode = commandCode;
    }

    public byte getParameterLength() {
        return ParameterLength;
    }

    public void setParameterLength(byte parameterLength) {
        ParameterLength = parameterLength;
    }

    public byte[] getParameterData() {
        return ParameterData;
    }

    public void setParameterData(byte[] parameterData) {
        ParameterData = parameterData;
    }

    public byte[] getDataLength() {
        return DataLength;
    }

    public void setDataLength(byte[] dataLength) {
        DataLength = dataLength;
    }

    public byte[] getData() {
        return Data;
    }

    public void setData(byte[] data) {
        Data = data;
    }

    public byte[] getCheckCode() {
        return CheckCode;
    }

    public void setCheckCode(byte[] checkCode) {
        CheckCode = checkCode;
    }

    public byte[] getProtocolVersion() {
        return ProtocolVersion;
    }

    public void setProtocolVersion(byte[] protocolVersion) {
        ProtocolVersion = protocolVersion;
    }

    public byte[] getEndFlag() {
        return EndFlag;
    }
}


