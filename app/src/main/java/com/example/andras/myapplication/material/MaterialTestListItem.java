package com.example.andras.myapplication.material;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andras.myapplication.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Andras_Nemeth on 2015.08.14..
 */
public class MaterialTestListItem extends LinearLayout {

    @InjectView(R.id.text_view)
    TextView textView;


    public MaterialTestListItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.listitem_material_test, this, true);
        ButterKnife.inject(this);
    }

    public void setText(String text) {
        textView.setText(text);
    }



}
