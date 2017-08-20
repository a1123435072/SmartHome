package com.njsyg.smarthomeapp.bll.socket_bll.models;

import java.util.List;

/**
 * Created by user on 2016/7/7.
 */
public class T_User {
    private int user_id;
    private String user_phoneNumber;
    private String user_nickName;
    private String user_pwd;
    private String user_email;
    private String user_headImg;
    private String remark;
    private String NewPassword;
    private List<View_HServerAndUser> view_hServerAndUser;
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public String getUser_nickName() {
        return user_nickName;
    }

    public void setUser_nickName(String user_nickName) {
        this.user_nickName = user_nickName;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_headImg() {
        return user_headImg;
    }

    public void setUser_headImg(String user_headImg) {
        this.user_headImg = user_headImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public List<View_HServerAndUser> getView_hServerAndUser() {
        return view_hServerAndUser;
    }

    public void setView_hServerAndUser(List<View_HServerAndUser> view_hServerAndUser) {
        this.view_hServerAndUser = view_hServerAndUser;
    }
}
