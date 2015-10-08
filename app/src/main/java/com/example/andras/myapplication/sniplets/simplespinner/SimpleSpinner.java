package com.example.andras.myapplication.sniplets.simplespinner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.andras.myapplication.R;

/**
 * Created by Andras_Nemeth on 2015.10.06..
 */
public class SimpleSpinner extends LinearLayout {

    private BaseAdapter adapter;
    private ListPopupWindow popupWindow;
    private FrameLayout container;
    private View spinnerContent;
    private int selectedPosition;
    @Nullable
    private AdapterView.OnItemSelectedListener selectionListener;
    private View view;
    private Handler handler = new Handler();

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

        popupWindow = new ListPopupWindow(context);
        popupWindow.setModal(true);
        popupWindow.setAnchorView(view);
        popupWindow.setOnItemClickListener(new OnItemClickListener());
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        popupWindow.setWidth(view.getWidth());
    }

    private int getThemeBackgroundColor(Context context) {
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
        int backgroundColor = array.getColor(0, 0xFFFFFF);
        array.recycle();
        return backgroundColor;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter, int position) {
        this.adapter = adapter;
        popupWindow.setAdapter(adapter);
        setSelection(position);
    }

    /**
     * Jump directly to a specific item in the adapter data.
     */
    public void setSelection(int position) {
        setSelection(popupWindow.getListView(), null, position, adapter.getItemId(position));
    }

    private void setSelection(final AdapterView<?> parent, final View view, final int position, final long id) {
        spinnerContent = adapter.getView(position, spinnerContent, container);
        container.removeAllViews();
        container.addView(spinnerContent);
        selectedPosition = position;
        if (selectionListener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    selectionListener.onItemSelected(parent, view, position, id);
                }
            });

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
        popupWindow.show();
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSelection(parent, view, position, id);
            popupWindow.dismiss();
        }
    }


}
