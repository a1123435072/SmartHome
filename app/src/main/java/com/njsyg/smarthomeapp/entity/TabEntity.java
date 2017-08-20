package com.njsyg.smarthomeapp.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 标题栏设置标题三种图标
 * Created by HUAQING on 2016/10/21.
 */

public class TabEntity implements CustomTabEntity {

    public int selectedIcon;
    public int unSelectedIcon;
    public String title;
    public TabEntity(String title, int selectedIcon, int unSelectedIcon){
        this.title=title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public TabEntity( int selectedIcon, int unSelectedIcon){

        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }


    @Override
    public String getTabTitle() {

        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
