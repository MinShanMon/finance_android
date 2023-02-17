package com.team3.personalfinanceapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.team3.personalfinanceapp.R;


public class StockWebViewFragment extends Fragment {


    public StockWebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stock_web_view, container, false);


        String url = "https://sg.finance.yahoo.com/quote/" + getArguments().getString("symbol");


        WebView webView = v.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        return v;
    }
}