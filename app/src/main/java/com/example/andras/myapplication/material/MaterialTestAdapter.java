package com.example.andras.myapplication.material;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andras_Nemeth on 2015.08.14..
 */
public class MaterialTestAdapter extends RecyclerView.Adapter<MaterialTestAdapter.ViewHolder> {

    private static final int ITEM_COUNT = 20;
    private List<RecyclerViewListItemDto> items;

    public MaterialTestAdapter() {
        items = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            items.add(new RecyclerViewListItemDto(i, "#" + i + " Hello world!"));
        }
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new MaterialTestListItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(items.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
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


    //these methods belongs to DragController and are from stylingandroid.com
    public void moveItem(int start, int end) {
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < items.size()) {
            RecyclerViewListItemDto item = items.remove(min);
            items.add(max, item);
            notifyItemMoved(min, max);
        }
    }

    public int getPositionForId(long id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
