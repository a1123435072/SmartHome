package com.njsyg.smarthomeapp.adapters.MissionsPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.IndexPage.OnTapListener;
import java.util.ArrayList;
/**
 *  情景模式 RecyclerView  Adapter
 * Created by HUAQING on 2016/10/21.
 */
public class ContextualModelRecyclerViewAdapter extends RecyclerView.Adapter<ContextualModelRecyclerViewAdapter.ViewHolder> {
    private ArrayList<ContextualModelArrayListItem> dataList;
    private OnTapListener onTapListener;

    /** RecycleView设置布局样式*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contextual_model_row_view, null);
        return new ViewHolder(v);
    }
    /** RecycleView绑定*/
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onTapListener != null)
                    onTapListener.onTapView(i);
            }
        });
        viewHolder.im_contextualmodele_image.setImageDrawable(dataList.get(i).getContextualModelImage());
        viewHolder.tv_contextualmodele_name.setText(dataList.get(i).getContextualModelName());
        viewHolder.iv_contextuallight.setImageDrawable(dataList.get(i).getContextualModelLight());
        viewHolder.iv_contextualmodele_tv.setImageDrawable(dataList.get(i).getContextualModelTv());
        viewHolder.iv_contextualmodele_music.setImageDrawable(dataList.get(i).getContextualModelMusic());
        viewHolder.iv_contextualmodelestart_up.setImageDrawable(dataList.get(i).getContextualModelStartUp());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView im_contextualmodele_image;
        private TextView tv_contextualmodele_name;
        private ImageView iv_contextuallight;
        private ImageView iv_contextualmodele_tv;
        private ImageView iv_contextualmodele_music;
        private ImageView iv_contextualmodelestart_up;
        /** RecycleView找到控件ID*/
        public ViewHolder(View v) {
            super(v);
            im_contextualmodele_image = (ImageView) v.findViewById(R.id.im_contextual_modele_image);/** 情景模式  模式图片*/
            tv_contextualmodele_name = (TextView) v.findViewById(R.id.tv_contextual_modele_name);/** 情景模式  模式名称*/
            iv_contextuallight = (ImageView) v.findViewById(R.id.iv_contextual_light);/** 情景模式  智能灯泡*/
            iv_contextualmodele_tv = (ImageView) v.findViewById(R.id.iv_contextual_modele_tv);/** 情景模式  电视*/
            iv_contextualmodele_music = (ImageView) v.findViewById(R.id.iv_contextual_modele_music);/** 情景模式  音乐*/
            iv_contextualmodelestart_up = (ImageView) v.findViewById(R.id.iv_contextual_modele_start_up);/** 情景模式  开启关闭状态*/
        }
    }
    /** RecycleView 更新数据*/
    public void updataDataList(ArrayList<ContextualModelArrayListItem> contextualModelArrayListItemArrayList) {
        this.dataList = contextualModelArrayListItemArrayList;
        this.notifyDataSetChanged();
    }
    /** RecycleView 侦听接口*/
    public void setOnTapListener(OnTapListener onTapListener) {
        this.onTapListener = onTapListener;
    }
}
