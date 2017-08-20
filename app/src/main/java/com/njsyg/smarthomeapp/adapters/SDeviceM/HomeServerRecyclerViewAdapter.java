package com.njsyg.smarthomeapp.adapters.SDeviceM;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.IndexPage.OnTapListener;

import java.util.ArrayList;

/**
 * Created by HUAQING on 2016/10/28.
 */

public class HomeServerRecyclerViewAdapter extends RecyclerView.Adapter<HomeServerRecyclerViewAdapter.ViewHolder>{
    private ArrayList<HomeServerArrayListItem> dataList;
    private DataControlDelegate mDelegate;
    /**
     * 委托接口，用于对adapter中的数据进行操作
     *
     * @author Change
     */
    public static interface DataControlDelegate {
        public void setDefautHServer(String hserverSN,int isDefault);//设置默认家庭服务器
        public void deleteHServer(String hserverSN);//删除
        public void editHServer(String hserverSN);//修改
        public void clickItem(String hserverSN,int index);//选中当前行
    }

    public HomeServerRecyclerViewAdapter(ArrayList<HomeServerArrayListItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public HomeServerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_server_row_view,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeServerRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        /**绑定空间属性*/
        final String hserverSN = dataList.get(i).getHserverSN();
        final int isDefault=dataList.get(i).getIntIsDefault();
        viewHolder.tv_homeServerName.setText(dataList.get(i).getHserverName());
        viewHolder.tv_homeServerIp.setText(dataList.get(i).getHserverIp());
        viewHolder.im_default_home_server.setImageDrawable(dataList.get(i).getHserverIsDefault());
        viewHolder.im_editHomeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDelegate != null) {
                    mDelegate.editHServer(hserverSN);
                }
            }
        });
        viewHolder.im_deleteHomeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDelegate != null) {
                    mDelegate.deleteHServer(hserverSN);
                }
            }
        });
        viewHolder.im_default_home_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDelegate != null) {
                    mDelegate.setDefautHServer(hserverSN,isDefault);
                }
            }
        });
        viewHolder.linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDelegate != null) {
                    mDelegate.clickItem(hserverSN,i);
                }
            }
        });
        if (i == selectItem) {
            viewHolder.linearLayoutItem.setBackgroundColor(Color.rgb(183, 183, 183));
        }
        else {
            viewHolder.linearLayoutItem.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    /** 找到布局中的空间*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_homeServerName;
        private TextView tv_homeServerIp;
        private ImageView im_homeServerImage;
        private ImageView im_editHomeServer;
        private ImageView im_deleteHomeServer;
        private ImageView im_default_home_server;
        private LinearLayout linearLayoutItem;
        public ViewHolder(View v) {
            super(v);
            tv_homeServerName = (TextView) v.findViewById(R.id.tv_home_server_name);/** 家庭服务器名字*/
            tv_homeServerIp = (TextView) v.findViewById(R.id.tv_home_server_ip); /** 家庭服务器ip*/
            im_homeServerImage = (ImageView) v.findViewById(R.id.im_home_server_image);/** 家庭服务器图片*/
            im_editHomeServer = (ImageView) v.findViewById(R.id.im_edit_home_server);/** 编辑家庭服务器*/
            im_deleteHomeServer = (ImageView) v.findViewById(R.id.im_delete_home_server);/** 删除家庭服务器*/
            im_default_home_server=(ImageView) v.findViewById(R.id.im_default_home_server);//默认
            linearLayoutItem=(LinearLayout)v.findViewById(R.id.linearLayoutItem);
        }
    }
    /** 更新数据*/
    public void updataDataList(ArrayList<HomeServerArrayListItem> serverDeviceArrayListItemArrayList){
        this.dataList = serverDeviceArrayListItemArrayList;
        this.notifyDataSetChanged();
    }
    /**
     * 设置委托者
     *
     * @param _delegate
     */
    public void setDataControlDelegate(DataControlDelegate _delegate) {
        this.mDelegate = _delegate;
    }
    private int selectItem=0;
    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        this.notifyDataSetChanged();
    }
}
