package com.njsyg.smarthomeapp.adapters.DeviceSelect;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;

/**
 * 任务管理
 * Created by HUAQING on 2016/11/3.
 */

public class MissionAdapter extends BaseAdapter{

    private Context mContext;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.device_selete_row_view,null);
        ImageView iv_deviceSeleteImage = (ImageView) view.findViewById(R.id.tv_device_selete_image);
        TextView tv_deviceSeleteName = (TextView) view.findViewById(R.id.tv_device_selete_name);

//        iv_deviceSeleteImage.setImageResource();
        return null;
    }
}
