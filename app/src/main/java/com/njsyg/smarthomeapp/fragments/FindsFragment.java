package com.njsyg.smarthomeapp.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.njsyg.smarthomeapp.R;
import com.njsyg.smarthomeapp.common.utils.ScrollSwipeRefreshLayout;

/**
 * Created by zz on 2016/10/20.
 */
public class FindsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    WebView webView;
    private ScrollSwipeRefreshLayout refreshLayout = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finds, container, false);
        Log.i("FindsFragment","FindsFragment onCreateView");
        InitView(view);
        return view;
    }

    /**
     * 初始化InitView
     * @param view
     */
    public void  InitView(View view)
    {
        webView  = (WebView)view.findViewById(R.id.webView);
        refreshLayout = (ScrollSwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setViewGroup(webView);//设置监听滚动的子view
        refreshLayout.setOnRefreshListener(this);
        //refreshLayout.setColorScheme(getResources().getColor(R.color.green),getResources().getColor(R.color.line_and_outline_grey), getResources().getColor(R.color.colorTitle), getResources().getColor(R.color.colorBackground));
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl("http://192.168.1.135:3000/index");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //设置加载完成后结束动画
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    public void onRefresh() {
        //下拉重新加载
        webView.reload();
    }
}
