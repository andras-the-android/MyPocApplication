package com.example.andras.myapplication;

/**
 * Created by Andras_Nemeth on 2015.09.02..
 */
public class RecyclerViewListItemDto {

    private long id;
    private String text;

    public RecyclerViewListItemDto(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
