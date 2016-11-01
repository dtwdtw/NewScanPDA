package com.irulu.scanpda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.irulu.scanpda.Adapter.ListAdapter_SkuSnQty_RecycleView;
import com.irulu.scanpda.Data.API;
import com.irulu.scanpda.Data.NetWorkDataIO.ServerData;
import com.irulu.scanpda.Data.UniqueKey;
import com.irulu.scanpda.Model.JsonModel.BoxReceiptResult;
import com.irulu.scanpda.Model.JsonModel.ScanBoxBackInfo;
import com.irulu.scanpda.Model.JsonModel.SkuSnQtyBean;
import com.irulu.scanpda.Model.JsonModel.UserInfo;
import com.irulu.scanpda.Tools.UrlParameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtw on 16/10/31.
 */

public class ReceiptBySomethingElseActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Spinner typeSpiner;
    TextView searchedTypeTextView, resultCountTextView;
    EditText searchNumEditText;
    Button confirmButton;
    RadioGroup searchedTypeRadioGroup;
    Bundle bundle = null;
    List<SkuSnQtyBean> skuSnQtyBeanList;
    ListAdapter_SkuSnQty_RecycleView listAdapter_SkuSnQty_recycleView;
    final int TYPE_BOX=0;
    final int TYPE_SN=1;
    int selectedType;
    int scanType=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somethingelse_receipt);

        skuSnQtyBeanList = new ArrayList<>();
        bundle = getIntent().getBundleExtra(UniqueKey.getUserInfoKey());

        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_datashow);
        searchedTypeTextView = (TextView) findViewById(R.id.textview_searchedtype);
        resultCountTextView = (TextView) findViewById(R.id.textview_count);
        searchNumEditText = (EditText) findViewById(R.id.edittext_search);
        confirmButton = (Button) findViewById(R.id.button_confirm);
        searchedTypeRadioGroup = (RadioGroup) findViewById(R.id.readiogroup_searchedtype);
        typeSpiner= (Spinner) findViewById(R.id.spinner_type);

        recyclerView.setLayoutManager(new LinearLayoutManager(ReceiptBySomethingElseActivity.this));
        listAdapter_SkuSnQty_recycleView = new ListAdapter_SkuSnQty_RecycleView(skuSnQtyBeanList,false);
        listAdapter_SkuSnQty_recycleView.setHead(R.layout.head_purcase_skuqty_recycleview);
        recyclerView.setAdapter(listAdapter_SkuSnQty_recycleView);
//        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        searchedTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==searchedTypeRadioGroup.getChildAt(0).getId()){
                    selectedType=TYPE_BOX;
                    searchedTypeTextView.setText(getResources().getString(R.string.boxnum_label));
                }else if(checkedId==searchedTypeRadioGroup.getChildAt(1).getId()){
                    selectedType=TYPE_SN;
                    searchedTypeTextView.setText(getResources().getString(R.string.snnum_label));
                }
            }
        });
        //默认设置箱号收货
        searchedTypeRadioGroup.check(searchedTypeRadioGroup.getChildAt(0).getId());


        typeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:scanType=1;
                        break;
                    case 1:scanType=2;
                        break;
                    case 2:scanType=3;
                        break;
                    case 3:scanType=4;
                        break;
                    case 4:scanType=5;
                        break;
                    case 5:scanType=6;
                        break;
                    case 6:scanType=21;
                        break;
                    case 7:scanType=22;
                        break;
                    default:scanType=1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //添加文本框右侧搜索按钮点击事件
        searchNumEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        searchNumEditText.getCompoundDrawables()[2] != null &&
                        event.getX() >= searchNumEditText.getWidth() - searchNumEditText.getPaddingLeft() - searchNumEditText.getPaddingRight() - searchNumEditText.getCompoundDrawables()[2].getBounds().width() - 12
                        ) {
                    showScanResult(selectedType,searchNumEditText.getText().toString());
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
                    showScanResult(selectedType,searchNumEditText.getText().toString());
                }
            }
        });

        //确认收货
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReceiped(selectedType,searchNumEditText.getText().toString());
            }
        });
    }

    private void showScanResult(int type, String source){
        UserInfo userInfo=bundle.getParcelable(UniqueKey.getUserInfoKey());
        switch (type){
            case TYPE_BOX:
                showResultByBoxNum(source,userInfo.getAreaCode(),userInfo.getPositionCode());
                break;
            case TYPE_SN:
                break;
        }
    }

    private void onReceiped(int type,String source){
        UserInfo userInfo=bundle.getParcelable(UniqueKey.getUserInfoKey());
        switch (type){
            case TYPE_BOX:
                onReceiptByBoxNumber(source,userInfo.getPositionCode(),0,userInfo.getAdminName());
                break;
            case TYPE_SN:
                break;
        }
    }

    //获取并显示通过箱号扫描到的数据
    private void showResultByBoxNum(String boxNum,String areaCode,String positionCode) {
        Map<String, String> boxNumInfo = new HashMap<>();
        boxNumInfo.put("BoxNumber", boxNum);
        boxNumInfo.put("AreaCode", areaCode);
        boxNumInfo.put("WarehouseCode", positionCode);

        String infoByBoxNumUrl = UrlParameter.get(boxNumInfo);
        infoByBoxNumUrl = API.getInstance().getBoxInfoUrl() + infoByBoxNumUrl;

        ServerData.getJsonModel(infoByBoxNumUrl, ScanBoxBackInfo.class, new ServerData.CallBackScan<ScanBoxBackInfo>() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReceiptBySomethingElseActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final ScanBoxBackInfo model) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (model.getResultId()) {
                            case 1:
                                skuSnQtyBeanList.clear();
                                skuSnQtyBeanList.addAll(model.getData());
                                listAdapter_SkuSnQty_recycleView.notifyDataSetChanged();

                                int resultCount = 0;
                                for (SkuSnQtyBean skuSnQtyBean : model.getData()) {
                                    resultCount += skuSnQtyBean.getQty();
                                }
                                resultCountTextView.setText("Count:" + String.valueOf(resultCount));
                                break;
                            case 0:
                                skuSnQtyBeanList.clear();
                                SkuSnQtyBean noDataInfo = new SkuSnQtyBean();
                                if(model.getMsg().contains("已收")){
                                    noDataInfo.setSKU(getResources().getString(R.string.boxnum_receipt_already));
                                }else{
                                    noDataInfo.setSKU(getResources().getString(R.string.no_data_boxNum));
                                }
                                resultCountTextView.setText("");
                                skuSnQtyBeanList.add(noDataInfo);
                                listAdapter_SkuSnQty_recycleView.notifyDataSetChanged();
                                break;
                        }
                    }
                });
            }
        });

    }

    /**
     * @Description 通过箱号收货
     * @param boxNum
     * @param positionCode
     * @param typeID 默认为0
     * @param userName
     */
    private void onReceiptByBoxNumber(String boxNum, String positionCode, int typeID, String userName) {
        Map<String, String> confirmByBoxNumInfo = new HashMap<>();
        confirmByBoxNumInfo.put("boxNumber", boxNum);
        confirmByBoxNumInfo.put("warehouseCode", positionCode);
        confirmByBoxNumInfo.put("receiveTypeId", String.valueOf(typeID));
        confirmByBoxNumInfo.put("user", userName);

        String confirmByBoxNumUrl = UrlParameter.get(confirmByBoxNumInfo);
        confirmByBoxNumUrl = API.getInstance().getConfirmByBoxNumUrl() + confirmByBoxNumUrl;

        ServerData.getJsonModel(confirmByBoxNumUrl, BoxReceiptResult.class, new ServerData.CallBackScan<BoxReceiptResult>() {
            @Override
            public void onFailure(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ReceiptBySomethingElseActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final BoxReceiptResult model) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (model.getResultId()) {
                            case 1:
                                Toast.makeText(ReceiptBySomethingElseActivity.this, R.string.receipt_success, Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(ReceiptBySomethingElseActivity.this, R.string.receipt_unsuccess, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });
    }
}
