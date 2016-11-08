package com.irulu.scanpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irulu.scanpda.Adapter.ListAdapter_SkuSnQty_RecycleView;
import com.irulu.scanpda.Data.API;
import com.irulu.scanpda.Data.NetWorkDataIO.ServerData;
import com.irulu.scanpda.Data.UniqueKey;
import com.irulu.scanpda.Model.JsonModel.FormReceiptReturnResult;
import com.irulu.scanpda.Model.JsonModel.ScanFormBackInfo;
import com.irulu.scanpda.Model.JsonModel.SkuSnQtyBean;
import com.irulu.scanpda.Model.JsonModel.UserInfo;
import com.irulu.scanpda.Tools.UrlParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtw on 16/10/27.
 */

public class ReceiptOrReturnByFormNumActivity extends BaseActivity {
    RecyclerView recyclerView;
    TextView searchedTypeTextView, resultCountTextView;
    EditText searchNumEditText;
    Button confirmButton;
    Bundle bundle = null;
    UserInfo userInfo;
    List<SkuSnQtyBean> skuSnQtyBeanList;
    ListAdapter_SkuSnQty_RecycleView listAdapter_SkuSnQty_recycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewID(R.layout.activity_formnum_receivereturn);

        skuSnQtyBeanList = new ArrayList<>();
        bundle = getIntent().getBundleExtra(UniqueKey.getUserInfoKey());
        userInfo = bundle.getParcelable(UniqueKey.getUserInfoKey());

        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_datashow);
        searchedTypeTextView = (TextView) findViewById(R.id.textview_searchedtype);
        resultCountTextView = (TextView) findViewById(R.id.textview_count);
        searchNumEditText = (EditText) findViewById(R.id.edittext_search);
        confirmButton = (Button) findViewById(R.id.button_confirm);

        recyclerView.setLayoutManager(new LinearLayoutManager(ReceiptOrReturnByFormNumActivity.this));
        listAdapter_SkuSnQty_recycleView = new ListAdapter_SkuSnQty_RecycleView(skuSnQtyBeanList,true);
        listAdapter_SkuSnQty_recycleView.setHead(R.layout.head_qualitycontrol_skusnqty_recycleview);
        recyclerView.setAdapter(listAdapter_SkuSnQty_recycleView);

        //添加文本框右侧搜索按钮点击事件
        searchNumEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        searchNumEditText.getCompoundDrawables()[2] != null &&
                        event.getX() >= searchNumEditText.getWidth() - searchNumEditText.getPaddingLeft() - searchNumEditText.getPaddingRight() - searchNumEditText.getCompoundDrawables()[2].getBounds().width() - 12
                        ) {
                    showScanResultByFormNum(searchNumEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        //添加文本框快速搜索条件
        searchNumEditText.addTextChangedListener(new TextWatcher() {
            CharSequence temp = null;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Math.abs(s.length() - temp.length()) > 1 || s.length() == temp.length()) {
                    showScanResultByFormNum(searchNumEditText.getText().toString());
                }
            }
        });
        //确认收货
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReceiptOrReturnByFormNum(searchNumEditText.getText().toString(), userInfo);
            }
        });
    }

    //获取并显示通过单号扫描到的数据
    private void showScanResultByFormNum(String formNum) {
        showProgressBar();
        Map<String, String> formNumInfo = new HashMap<>();
        formNumInfo.put("ReceiptNo", formNum);

        String infoFormBoxNumUrl = UrlParameter.get(formNumInfo);
        infoFormBoxNumUrl = API.getInstance().getFormNumInfoUrl() + infoFormBoxNumUrl;

        ServerData.getJsonModel(infoFormBoxNumUrl, ScanFormBackInfo.class, new ServerData.CallBackScan<ScanFormBackInfo>() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReceiptOrReturnByFormNumActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                });
            }

            @Override
            public void onResponse(final ScanFormBackInfo model) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (model.getResultId()) {
                            //正确收到数据
                            case 1:
                                skuSnQtyBeanList.clear();
                                skuSnQtyBeanList.addAll(model.getData());
                                listAdapter_SkuSnQty_recycleView.notifyDataSetChanged();

                                int resultCount = 0;
                                for (SkuSnQtyBean skuSnQtyBean : model.getData()) {
                                    resultCount += skuSnQtyBean.getQty();
                                }
                                resultCountTextView.setText(getResources().getString(R.string.count_lable) + String.valueOf(resultCount));
                                break;
                            //各种数据问题
                            case 0:
                                skuSnQtyBeanList.clear();
                                SkuSnQtyBean noDataInfo = new SkuSnQtyBean();
                                noDataInfo.setSKU(getResources().getString(R.string.no_data_form));
                                resultCountTextView.setText("");
                                skuSnQtyBeanList.add(noDataInfo);
                                showToast(getResources().getString(R.string.no_data_form));
                                listAdapter_SkuSnQty_recycleView.notifyDataSetChanged();
                                break;
                        }
                        hideProgressBar();
                    }
                });
            }
        });

    }

    private void onReceiptOrReturnByFormNum(String formNum, UserInfo userInfo) {
        showProgressBar();
        Map<String, String> confirmByFormNumInfo = new HashMap<>();
        confirmByFormNumInfo.put("ReceiptNo", formNum);
        confirmByFormNumInfo.put("WarehouseCode", userInfo.getPositionCode());
        confirmByFormNumInfo.put("user", userInfo.getAdminName());

        String confirmByFormNumUrl = UrlParameter.get(confirmByFormNumInfo);
        confirmByFormNumUrl = API.getInstance().getConfirmReceiveByFormNumUrl() + confirmByFormNumUrl;

        ServerData.getJsonModel(confirmByFormNumUrl, FormReceiptReturnResult.class, new ServerData.CallBackScan<FormReceiptReturnResult>() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReceiptOrReturnByFormNumActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                });
            }

            @Override
            public void onResponse(final FormReceiptReturnResult model) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (model.getResultId()) {
                            case 1:
                                showToast(getResources().getString(R.string.receipt_success));
                                break;
                            case 0:
                                showToast(getResources().getString(R.string.receipt_unsuccess));
                                break;
                        }
                        hideProgressBar();
                    }
                });
            }
        });
    }

}
