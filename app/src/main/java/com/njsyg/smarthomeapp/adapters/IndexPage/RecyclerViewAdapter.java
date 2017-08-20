package com.njsyg.smarthomeapp.adapters.IndexPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;

import java.util.ArrayList;

/**RecyclerView  Adapter
 * Created by HUAQING on 2016/10/21.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<IndexDeviceArrayListItem> dataList;
    private DataControlDelegate mDelegate;
    /**
     * 委托接口，用于对adapter中的数据进行操作
     *
     * @author Change
     */
    public static interface DataControlDelegate {
        public void setDeviceSwitchState(String sdeviceSN, int currentSwitchState);
        public void showDeviceRealData(String sdeviceSN);

    }

    public RecyclerViewAdapter(ArrayList<IndexDeviceArrayListItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_view, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.textView_installPlace.setText(dataList.get(i).getInstallPlace()+"/"+dataList.get(i).getNickName());
        viewHolder.textView_dian_liu.setText(dataList.get(i).getDianLiu());
        viewHolder.TextView_dian_ya.setText(dataList.get(i).getDianYa());
        viewHolder.textView_dian_liang.setText(dataList.get(i).getBattery());
        viewHolder.imageViewOnLineState.setImageDrawable(dataList.get(i).getOnlineState());
        viewHolder.imageViewSwitchStatus.setImageDrawable(dataList.get(i).getSwitchState());
        final String sDeviceSN = dataList.get(i).getDeviceSN();
        final int currentSwitchState = dataList.get(i).getIntSwitchState();
        viewHolder.imageViewSwitchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mDelegate)
                    //控制开关状态
                    mDelegate.setDeviceSwitchState(sDeviceSN, currentSwitchState);
            }
        });
        viewHolder.linearLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mDelegate)
                    //选择当前行
                    mDelegate.showDeviceRealData(sDeviceSN);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView_installPlace;
        private TextView textView_dian_liu;
        private  TextView TextView_dian_ya;
        private TextView textView_dian_liang;
        private ImageView imageViewOnLineState;
        private ImageView imageViewSwitchStatus;
        private LinearLayout linearLayoutItem;
        public ViewHolder(View v)
        {
            super(v);
            textView_installPlace = (TextView) v.findViewById(R.id.tv_kai_guan_wei_zhi);/**  安装位置*/
            textView_dian_liu = (TextView) v.findViewById(R.id.tv_dian_liu);/**  电流*/
            TextView_dian_ya = (TextView) v.findViewById(R.id.tv_dian_ya);/**  电压*/
            textView_dian_liang= (TextView) v.findViewById(R.id.tv_hao_dian_liang);/** 电量 */
            imageViewOnLineState=(ImageView) v.findViewById(R.id.im_zhuang_tai);/** 离线、在线状态 */
            imageViewSwitchStatus=(ImageView) v.findViewById(R.id.im_kai_guan);/**  开关状态*/
            linearLayoutItem=(LinearLayout)v.findViewById(R.id.linearLayoutItem);
        }
    }
    public void  updataDataList(){
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
}
