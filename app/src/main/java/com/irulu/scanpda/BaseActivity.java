package com.irulu.scanpda;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dtw on 16/11/1.
 */

public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private LinearLayout layoutBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseactivity);

        layoutBody= (LinearLayout) findViewById(R.id.layout_body);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);

        setSupportActionBar(toolbar);
    }

    public void setContentViewID(int layoutID) {
        View contentView=getLayoutInflater().inflate(layoutID,null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutBody.addView(contentView,layoutParams);

    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showToast(String toastText){
        Toast toast=new Toast(this);
        View view=getLayoutInflater().inflate(R.layout.view_toast,null);
        TextView toastTextView= (TextView) view.findViewById(R.id.toast_text);
        toastTextView.setText(toastText);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
