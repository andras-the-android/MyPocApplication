package com.example.andras.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Andras_Nemeth on 2015.08.14..
 */
public class MaterialTestAdapter extends RecyclerView.Adapter<MaterialTestAdapter.ViewHolder> {

    private static final int NUMBER_OF_ITEMS = 20;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new MaterialTestListItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText("#" + (position + 1) + " Hello world!");
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_ITEMS;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTestListItem itemView;

        public ViewHolder(MaterialTestListItem itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setText(String text) {
            itemView.setText(text);
        }

        public MaterialTestListItem getView() {
            return itemView;
        }
    }
}
