package com.example.andras.myapplication.sniplets;

import android.content.Context;
import android.graphics.PorterDuff;
import androidx.annotation.StringRes;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.andras.myapplication.R;


/**
 * Created by Andras on 2015.10.19..
 */
public class TextInputLayoutValidationHelper {

    private EditText editText;
    private TextInputLayout textInputLayout;
    private String errorMessage;
    private int red;
    private boolean errorRaised;

//    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (hasFocus) {
//                dismissError();
//            }
//        }
//    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            dismissError();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public TextInputLayoutValidationHelper(Context context, TextInputLayout textInputLayout, @StringRes int errorMessageResId) {
        this.textInputLayout = textInputLayout;
        editText = (EditText) textInputLayout.getChildAt(0);
        //editText.setOnFocusChangeListener(focusChangeListener);
        editText.addTextChangedListener(textWatcher);
        errorMessage = context.getString(R.string.mandatory_field);
        red = ContextCompat.getColor(context, android.R.color.holo_red_dark);
    }

    public void raiseError() {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
        editText.getBackground().setColorFilter(red, PorterDuff.Mode.SRC_ATOP);
        errorRaised = true;
    }

    public void dismissError() {
        if (errorRaised) {
            textInputLayout.setErrorEnabled(false);
            editText.getBackground().clearColorFilter();
            errorRaised = false;
        }
    }

    public boolean isEmpty() {
        return editText.getText().length() == 0;
    }
}
