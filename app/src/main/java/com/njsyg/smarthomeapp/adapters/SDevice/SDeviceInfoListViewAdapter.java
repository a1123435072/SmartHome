package com.njsyg.smarthomeapp.adapters.SDevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;

import java.util.ArrayList;

/**
 * Created by zz on 2016/9/7.
 */
public class SDeviceInfoListViewAdapter extends BaseAdapter {
    // 声明数组链表，其装载的类型是ListItem(封装了一个Drawable和一个String的类)
    private ArrayList<SDeviceInfoListItem> mList;
    private LayoutInflater mInflater;
    private DataControlDelegate mDelegate;

    /**
     * 委托接口，用于对adapter中的数据进行操作，此处提供删除、修改方法
     *
     * @author Change
     */
    public static interface DataControlDelegate {

        /**
         * 选择
         */
        public void selectedSDevice(String sdeviceSN);
    }

    public SDeviceInfoListViewAdapter(Context context, ArrayList<SDeviceInfoListItem> datas) {
        mInflater = LayoutInflater.from(context);
        mList = datas;
    }

    /**
     * 返回item的个数
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    /**
     * 返回item的内容
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    /**
     * 返回item的id
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * 返回item的视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SDeviceInfoListItemView listItemView;
        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = mInflater.inflate(R.layout.items_sdevice_infolist, null);
            // 实例化一个封装类ListItemView，并实例化它的两个域
            listItemView = new SDeviceInfoListItemView();
            listItemView.imageView = (ImageView) convertView.findViewById(R.id.item_sdevice_img);
            listItemView.textViewNick = (TextView) convertView.findViewById(R.id.item_sdevice_nick);
            listItemView.textViewPlace = (TextView) convertView.findViewById(R.id.item_sdevice_place);
            listItemView.imageViewSelect = (ImageView) convertView.findViewById(R.id.item_sdevice_select);
            // 将ListItemView对象传递给convertView
            convertView.setTag(listItemView);
        } else {
            // 从converView中获取ListItemView对象
            listItemView = (SDeviceInfoListItemView) convertView.getTag();
        }
        String devicesn=mList.get(position).getSdeviceSN();
        String nick = mList.get(position).getNick();
        String place= mList.get(position).getPlace();
        listItemView.textViewNick.setText(nick);
        listItemView.textViewNick.setTag(devicesn);
        listItemView.textViewPlace.setText(place);
        final String sdeviceSN = mList.get(position).getSdeviceSN();

        listItemView.imageViewSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDelegate)
                    mDelegate.selectedSDevice(sdeviceSN);//选择
            }
        });
        // 返回convertView对象
        return convertView;
    }

    /**
     * 设置委托者
     *
     * @param _delegate
     */
    public void setDataControlDelegate(DataControlDelegate _delegate) {
        this.mDelegate = _delegate;
    }
    class SDeviceInfoListItemView {
        ImageView imageView;
        TextView textViewNick;
        TextView textViewPlace;
        ImageView imageViewSelect;
    }
}
