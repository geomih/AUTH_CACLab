package com.mythesis.michaigp.authcaclab;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import junit.runner.Version;

public class MyWebView extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    //private String mParam1;

    private OnFragmentInteractionListener mListener;
    private static String URL;

    private ProgressBar mProgressView;
    private WebView webView = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConnectivityManager cm;

    public static MyWebView newInstance(int param1,String URL1) {
        MyWebView fragment = new MyWebView();
        URL = URL1;
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MyWebView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment (which is the whole .XML file)
        View rootView =  inflater.inflate(R.layout.fragment_my_webview, container, false);
        //And here we connect the webView child of the .XML file to the java equivalent class object
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        webView = (WebView) rootView.findViewById(R.id.webView);
        mProgressView = (ProgressBar) rootView.findViewById(R.id.progressBar);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        cm = (ConnectivityManager) this.getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected()){
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            //Toast.makeText(getActivity().getApplicationContext(), "Internet", Toast.LENGTH_SHORT).show();//DEBUG
        }else{
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();//DEBUG
        }
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //Problems with the background in older versions
                if(Build.VERSION.SDK_INT < 19 ){//Pre-KitKat
                    webView.loadUrl("javascript: " +
                            "var d = document.getElementById('con').innerHTML;" +
                            "document.body.innerHTML = d;" +
                            "document.body.style.backgroundColor = 'white';" +
                            "document.body.style.color = 'black';");
                }else{//After KitKat
                    webView.loadUrl("javascript: " +
                            "var d = document.getElementById('con');" +
                            "d.style.color = 'black';" +
                            "document.body.innerHTML = d.innerHTML;");
                }
                mProgressView.setVisibility(View.INVISIBLE);
            }
            @Override //We send external URLs to the default browser
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost() != null && Uri.parse(url).getHost().equals("michaigp.webpages.auth.gr/project3/")) {
                    // This is my web site, so do not override; let my WebView load the page
                    return false;
                }
                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl){
                webView.loadUrl("file:///android_asset/error.html");
            }
        });
        webView.loadUrl(URL);
        return rootView;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_PARAM1));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        //Toast.makeText(getActivity().getApplicationContext(), "Refreshing", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                webView.loadUrl(URL);
            }
        }, 800);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
