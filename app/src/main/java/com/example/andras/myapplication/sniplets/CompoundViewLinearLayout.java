package com.example.andras.myapplication.sniplets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.andras.myapplication.R;

/**
 * Created by Andras_Nemeth on 2016. 12. 22..
 */

public class CompoundViewLinearLayout extends LinearLayout {

    public CompoundViewLinearLayout(Context context) {
        super(context);
        initView(context, false);
    }

    public CompoundViewLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, true);
    }

    public CompoundViewLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompoundViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, true);
    }

    private void initView(Context context, boolean hasAttributes) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_compound, this, true);
        if (!hasAttributes) {
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
        setOrientation(VERTICAL);
    }
}
