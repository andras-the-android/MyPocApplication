package com.example.andras.myapplication.sniplets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andras.myapplication.R;


public class ExtendedCompoundView extends LinearLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String ATTR_1 = "someAttr";
    private static final String ATTR_2 = "anotherAttr";

    TextView tw1;
    TextView tw2;
    TextView tw3;

    private Context context;

    public ExtendedCompoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @SuppressLint("SetTextI18n")
    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_compound, this, true);
        tw1 = (TextView) findViewById(R.id.view_compound_1);
        tw2 = (TextView) findViewById(R.id.view_compound_2);
        tw3 = (TextView) findViewById(R.id.view_compound_3);


        setOrientation(VERTICAL);
        tw1.setText(getAttributeValue(attrs, ATTR_1));
        tw3.setText(getAttributeValue(attrs, ATTR_2));
        if (isInEditMode()) {
            tw2.setText("TW2");
        }
    }

    public void setValue(String value) {
        tw2.setText(value);
    }

    public void setValue(int value) {
        tw2.setText(String.valueOf(value));
    }

    public void setValue(float value) {
        tw2.setText(String.valueOf(value));
    }

    private String getAttributeValue(AttributeSet attrs, String attrName) {
        String value = attrs.getAttributeValue(NAMESPACE, attrName);
        if (value == null || value.isEmpty()) {
            return "";
        }

        if (isResourceId(value)) {
            int resourceId = Integer.valueOf(value.substring(1));
            return context.getString(resourceId);
        }

        return value;
    }

    private boolean isResourceId(String value) {
        //first letter is @ and the others are numbers
        return value.matches("^@[0-9]*$");
    }
}
