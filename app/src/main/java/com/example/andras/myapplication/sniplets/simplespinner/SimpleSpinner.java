package com.example.andras.myapplication.sniplets.simplespinner;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.internal.widget.ListViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.andras.myapplication.R;

/**
 * Created by Andras_Nemeth on 2015.10.06..
 */
public class SimpleSpinner extends LinearLayout {

    private BaseAdapter adapter;
    private ListView listView;
    private PopupWindow popupWindow;
    private FrameLayout container;
    private View spinnerContent;
    int selectedPosition;
    @Nullable
    private AdapterView.OnItemSelectedListener selectionListener;
    private View view;


    public SimpleSpinner(Context context) {
        super(context);
        init(context);
    }

    public SimpleSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.simple_spinner, this, true);
        container = (FrameLayout) findViewById(R.id.content_container);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpinnerClick(v);
            }
        });

        popupWindow = new PopupWindow(this);
        listView = new ListViewCompat(context);
        listView.setOnItemClickListener(new OnItemClickListener());
        popupWindow.setFocusable(true);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(10);
            listView.setElevation(10);
        }
        popupWindow.setContentView(listView);

        handleOutsideTouch();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        popupWindow.setWidth(view.getWidth());
    }

    /**
     * Closes the popup on outside touch below lollipop
     */
    private void handleOutsideTouch() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setTouchInterceptor(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getActionMasked() & MotionEvent.ACTION_OUTSIDE) != 0) {
                        popupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        setAdapter(adapter, 0);
    }

    public void setAdapter(BaseAdapter adapter, int position) {
        this.adapter = adapter;
        listView.setAdapter(adapter);
        setSelection(position);
    }

    /**
     * Jump directly to a specific item in the adapter data.
     */
    public void setSelection(int position) {
        setSelection(listView, null, position, adapter.getItemId(position));
    }

    private void setSelection(AdapterView<?> parent, View view, int position, long id) {
        spinnerContent = adapter.getView(position, spinnerContent, container);
        container.removeAllViews();
        container.addView(spinnerContent);
        selectedPosition = position;
        if (selectionListener != null) {
            selectionListener.onItemSelected(parent, view, position, id);
        }
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been selected.
     *
     * @param listener The callback that will run
     */
    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener listener) {
        this.selectionListener = listener;
    }

    private void onSpinnerClick(View view) {
        PopupWindowCompat.showAsDropDown(popupWindow, view, -5, 0, Gravity.RIGHT);
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSelection(parent, view, position, id);
            popupWindow.dismiss();
        }
    }


}
