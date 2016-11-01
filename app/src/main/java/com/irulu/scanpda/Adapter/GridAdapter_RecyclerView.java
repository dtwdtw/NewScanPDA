package com.irulu.scanpda.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irulu.scanpda.Model.AdapterItemModel.CardItemModle;
import com.irulu.scanpda.R;

import java.util.List;

/**
 * Created by dtw on 16/10/24.
 */

public class GridAdapter_RecyclerView extends RecyclerView.Adapter<GridAdapter_RecyclerView.CardViewHolder>{
    List<CardItemModle> itemList;
    OnItemClickListener onItemClickListener;

    public GridAdapter_RecyclerView(List<CardItemModle> itemList){
        this.itemList=itemList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catdview,null);
        CardViewHolder cardViewHolder=new CardViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.imageViewCard.setImageBitmap(itemList.get(position).getItemImage());
        holder.textViewCard.setText(itemList.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageViewCard;
        public TextView textViewCard;

        public CardViewHolder(View itemView) {
            super(itemView);
            imageViewCard= (ImageView) itemView.findViewById(R.id.imageview_card);
            textViewCard= (TextView) itemView.findViewById(R.id.textview_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
