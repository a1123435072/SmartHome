package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.Date;

/**
 * Created by zz on 2016/8/24.
 */
public class View_TaskExecRecord_Info {
    /// <summary>
    ///
    /// </summary>
    private int  task_id;
    /// <summary>
    ///
    /// </summary>
    public Date task_time;
    /// <summary>
    ///
    /// </summary>
    public Date task_addtime;
    /// <summary>
    ///
    /// </summary>
    public Date  execute_time;
    /// <summary>
    ///
    /// </summary>
    public int execute_state;
    /// <summary>
    ///
    /// </summary>
    public String device_sn;
    /// <summary>
    ///
    /// </summary>
    public String task_frequency;
    /// <summary>
    ///
    /// </summary>
    public int  task_action;
    /// <summary>
    ///
    /// </summary>
    public int  task_state;
    /// <summary>
    ///
    /// </summary>
    public byte[] task_command;
    /// <summary>
    ///
    /// </summary>
    public String user_phoneNumber;
    /// <summary>
    ///
    /// </summary>
    public Date addtime;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public Date getTask_time() {
        return task_time;
    }

    public void setTask_time(Date task_time) {
        this.task_time = task_time;
    }

    public Date getTask_addtime() {
        return task_addtime;
    }

    public void setTask_addtime(Date task_addtime) {
        this.task_addtime = task_addtime;
    }

    public Date getExecute_time() {
        return execute_time;
    }

    public void setExecute_time(Date execute_time) {
        this.execute_time = execute_time;
    }

    public int getExecute_state() {
        return execute_state;
    }

    public void setExecute_state(int execute_state) {
        this.execute_state = execute_state;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
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
}
