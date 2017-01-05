package com.velemjaro.app.screen.home.details.settlement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.velemjaro.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeDetailsSettlementListItem extends LinearLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String ATTR_TITLE = "title";
    private static final String ATTR_MEASUREMENT = "measurement";


    @BindView(R.id.list_item_settlement_detail_title)
    TextView titleTextView;
    @BindView(R.id.list_item_settlement_detail_value)
    TextView valueTextView;
    @BindView(R.id.list_item_settlement_detail_measurement)
    TextView measurementTextView;

    private Context context;

    public HomeDetailsSettlementListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @SuppressLint("SetTextI18n")
    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.list_item_settlement_detail, this, true);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);
        titleTextView.setText(getAttributeValue(attrs, ATTR_TITLE));
        measurementTextView.setText(getAttributeValue(attrs, ATTR_MEASUREMENT));
        if (isInEditMode()) {
            valueTextView.setText("Value");
        }
    }

    public void setValue(String value) {
        valueTextView.setText(value);
    }

    public void setValue(int value) {
        valueTextView.setText(String.valueOf(value));
    }

    public void setValue(float value) {
        valueTextView.setText(String.valueOf(value));
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
