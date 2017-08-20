package com.njsyg.smarthomeapp.activitys.devices.device;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.adapters.DeviceSelect.MissionAdapter;

import static com.njsyg.smarthomeapp.R.drawable.tv;

public class sdevice_selection_Activity extends AppCompatActivity {
    private GridView gv_home;
    private ImageView iv_deviceSeleteImage;
    private TextView tv_deviceSeleteName;
    private String[] mTextItems = new String[]{"定时任务","情景模式"};
    private int[] mImageItems = new int[]{R.drawable.addd,R.drawable.addblack};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdevice_selection);


        gv_home = (GridView) findViewById(R.id.gv_device_admin);
        gv_home.setAdapter(new DeviceSelectAdapter());

        //设置点击监听
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        System.out.println("-----------------------------------------------------------------------");
                        break;
                    case 1:
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
        });
    }



    /**GridView Adapter*/
    class DeviceSelectAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mTextItems.length;
        }
        @Override
        public Object getItem(int position) {
            return mTextItems[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(sdevice_selection_Activity.this,R.layout.device_selete_row_view,null);
            iv_deviceSeleteImage = (ImageView) view.findViewById(R.id.tv_device_selete_image);
            tv_deviceSeleteName = (TextView) view.findViewById(R.id.tv_device_selete_name);

            iv_deviceSeleteImage.setImageResource(mImageItems[position]);
            tv_deviceSeleteName.setText(mTextItems[position]);
            return view;
        }
    }
}
