package com.njsyg.smarthomeapp.adapters.MissionsPage;

import android.graphics.drawable.Drawable;

/**
 * Created by HUAQING on 2016/10/24.
 */
public class ContextualModelArrayListItem {
    private Drawable contextualModelImage;/** 情景模式  模式图片*/
    private String contextualModelName;/** 情景模式  模式名称*/
    private Drawable contextualModelLight;/** 情景模式  智能灯泡*/
    private Drawable contextualModelTv;/** 情景模式  电视*/
    private Drawable contextualModelMusic;/** 情景模式  音乐*/
    private Drawable contextualModelStartUp;/** 情景模式  开启关闭状态*/
    private int intSwitchState;//0 关         1 开关 状态穿过来的值


    public String getContextualModelName() {
        return contextualModelName;
    }

    public void setContextualModelName(String contextualModelName) {
        this.contextualModelName = contextualModelName;
    }

    public Drawable getContextualModelLight() {
        return contextualModelLight;
    }

    public void setContextualModelLight(Drawable contextualModelLight) {
        this.contextualModelLight = contextualModelLight;
    }

    public Drawable getContextualModelTv() {
        return contextualModelTv;
    }

    public void setContextualModelTv(Drawable contextualModelTv) {
        this.contextualModelTv = contextualModelTv;
    }

    public Drawable getContextualModelMusic() {
        return contextualModelMusic;
    }

    public void setContextualModelMusic(Drawable contextualModelMusic) {
        this.contextualModelMusic = contextualModelMusic;
    }

    public Drawable getContextualModelStartUp() {
        return contextualModelStartUp;
    }

    public void setContextualModelStartUp(Drawable contextualModelStartUp) {
        this.contextualModelStartUp = contextualModelStartUp;
    }

    public int getIntSwitchState() {
        return intSwitchState;
    }

    public void setIntSwitchState(int intSwitchState) {
        this.intSwitchState = intSwitchState;
    }

    public Drawable getContextualModelImage() {
        return contextualModelImage;
    }

    public void setContextualModelImage(Drawable contextualModelImage) {
        this.contextualModelImage = contextualModelImage;
    }
}
