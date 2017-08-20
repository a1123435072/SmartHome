package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zz on 2016/8/18.
 */
public class T_Task implements Serializable {
    /// <summary>
    /// 自动增长编号
    /// </summary>
    private int task_id;
    /// <summary>
    ///智能设备sn
    /// </summary>
    private String device_sn;
    /// <summary>
    ///任务名称
    /// </summary>
    private String task_name;
    /// <summary>
    ///任务执行时间
    /// </summary>
    private String task_time;
    /// <summary>
    ///任务频率
    /// </summary>
    private String task_frequency;
    /// <summary>
    ///动作 开/关
    /// </summary>
    private int task_action;
    /// <summary>
    ///任务状态
    /// </summary>
    private int task_state;
    /// <summary>
    ///任务命令
    /// </summary>
    private byte[] task_command;
    /// <summary>
    ///添加任务用户手机号
    /// </summary>
    private String user_phoneNumber;
    /// <summary>
    ///添加任务时间
    /// </summary>
    private Date addtime;
    /// <summary>
    ///备注
    /// </summary>
    private String remark;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public String getTask_frequency() {
        return task_frequency;
    }

    public void setTask_frequency(String task_frequency) {
        this.task_frequency = task_frequency;
    }

    public int getTask_action() {
        return task_action;
    }

    public void setTask_action(int task_action) {
        this.task_action = task_action;
    }

    public int getTask_state() {
        return task_state;
    }

    public void setTask_state(int task_state) {
        this.task_state = task_state;
    }

    public byte[] getTask_command() {
        return task_command;
    }

    public void setTask_command(byte[] task_command) {
        this.task_command = task_command;
    }

    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
