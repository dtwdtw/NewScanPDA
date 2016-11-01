package com.irulu.scanpda.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irulu.scanpda.Model.JsonModel.SkuSnQtyBean;
import com.irulu.scanpda.R;

import java.util.List;

/**
 * Created by dtw on 16/10/25.
 */

public class ListAdapter_SkuSnQty_RecycleView extends RecyclerView.Adapter<ListAdapter_SkuSnQty_RecycleView.LinerViewHolder> {
    List<SkuSnQtyBean> skuSnQtyBeenList;
    private int TYPE_HEAD = 0;
    private int TYPE_NORMAL = 1;
    private int headResourceID = -1;
    private Boolean showSn;

    public ListAdapter_SkuSnQty_RecycleView(List<SkuSnQtyBean> skuSnQtyBeenList,Boolean showSn) {
        this.skuSnQtyBeenList = skuSnQtyBeenList;
        this.showSn=showSn;
    }

    public void setHead(int headResourceID) {
        this.headResourceID = headResourceID;
//        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (headResourceID != -1 && position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public LinerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD && headResourceID != -1) {
            return new LinerViewHolder(LayoutInflater.from(parent.getContext()).inflate(headResourceID, parent, false));
        } else {
            if(showSn) {
                return new LinerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skusnqty_showsn_linerview, parent, false));
            }else{
                return new LinerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skusnqty_hidesn_linerview, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(LinerViewHolder holder, int position) {
        int itemPosition=position;
        if (getItemViewType(position) == TYPE_NORMAL) {
            if(headResourceID!=-1){
                itemPosition = position - 1;
            }
            holder.skuTextView.setText(skuSnQtyBeenList.get(itemPosition).getSKU());
            holder.snTextView.setText(skuSnQtyBeenList.get(itemPosition).getSN());
            holder.qtyTextView.setText(String.valueOf(skuSnQtyBeenList.get(itemPosition).getQty()));
        }
    }

    @Override
    public int getItemCount() {
        if (headResourceID == -1) {
            return skuSnQtyBeenList.size();
        } else {
            return skuSnQtyBeenList.size() + 1;
        }
    }

    public class LinerViewHolder extends RecyclerView.ViewHolder {
        public TextView skuTextView, snTextView, qtyTextView;

        public LinerViewHolder(View itemView) {
            super(itemView);
            skuTextView = (TextView) itemView.findViewById(R.id.textview_first);
            snTextView = (TextView) itemView.findViewById(R.id.textview_second);
            qtyTextView = (TextView) itemView.findViewById(R.id.textview_third);
        }
    }
}
