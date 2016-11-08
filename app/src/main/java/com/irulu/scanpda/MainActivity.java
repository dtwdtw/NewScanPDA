package com.irulu.scanpda;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.irulu.scanpda.Adapter.GridAdapter_RecyclerView;
import com.irulu.scanpda.Data.UniqueKey;
import com.irulu.scanpda.Adapter.DefaultItemDecoration.RecycleViewItemDecoration;
import com.irulu.scanpda.Model.AdapterItemModel.CardItemModle;
import com.irulu.scanpda.Model.JsonModel.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewMain;
    Bundle bundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getBundleExtra(UniqueKey.getUserInfoKey());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);


        recyclerViewMain = (RecyclerView) findViewById(R.id.recycleview_main);
        recyclerViewMain.setLayoutManager(gridLayoutManager);
        recyclerViewMain.addItemDecoration(new RecycleViewItemDecoration(2));
        final UserInfo userInfo = bundle.getParcelable(UniqueKey.getUserInfoKey());
        GridAdapter_RecyclerView mainRecycleAdapter = new GridAdapter_RecyclerView(getDataForRecyclerViewAdapter(userInfo));
        recyclerViewMain.setAdapter(mainRecycleAdapter);
        mainRecycleAdapter.setOnItemClickListener(new GridAdapter_RecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startScanActivity(userInfo, position);
            }
        });
    }

    private List<CardItemModle> getDataForRecyclerViewAdapter(UserInfo userInfo) {
        List<CardItemModle> cardItemModleList = new ArrayList<>();
        CardItemModle cardItemModle;
        switch (userInfo.getAreaCode()) {
            case "1006":
                setTitle(R.string.receiving_group);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.receipt_by_boxnum));
                cardItemModleList.add(cardItemModle);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.receipt_by_something_else));
                cardItemModleList.add(cardItemModle);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.return_by_formnum));
                cardItemModleList.add(cardItemModle);
                break;
            case "1007":
                setTitle(R.string.quality_control);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.receipt_return_by_formnum));
                cardItemModleList.add(cardItemModle);
                break;
            case "1008":
                setTitle(R.string.warehouse);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.receipt_by_formnum));
                cardItemModleList.add(cardItemModle);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.transfer_allocation));
                cardItemModleList.add(cardItemModle);
                cardItemModle = new CardItemModle();
                cardItemModle.setItemImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                cardItemModle.setItemName(getResources().getString(R.string.transfer_receipt));
                cardItemModleList.add(cardItemModle);
                break;
        }
        return cardItemModleList;
    }

    private void startScanActivity(UserInfo userInfo, int clickPosition) {
        Intent intent;
        switch (userInfo.getAreaCode()) {
            //采购
            case "1006":
                switch (clickPosition) {
                    case 0:
                        intent = new Intent(MainActivity.this, ReceiptByBoxNumActivity.class);
                        intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ReceiptBySomethingElseActivity.class);
                        intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, ReceiptByFormNumActivity.class);
                        intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                        startActivity(intent);
                        break;
                }
                break;
            //品控
            case "1007":
                switch (clickPosition) {
                    case 0:
                        intent = new Intent(MainActivity.this, ReceiptByFormNumActivity.class);
                        intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
            //仓库
            case "1008":
                switch (clickPosition) {
                    case 0:
                        intent = new Intent(MainActivity.this, ReceiptByFormNumActivity.class);
                        intent.putExtra(UniqueKey.getUserInfoKey(), bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
        }
    }
}
