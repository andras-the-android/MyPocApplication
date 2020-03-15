package com.example.andras.myapplication.dagger2.ui.feature1.list;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.andras.myapplication.R;
import com.example.andras.myapplication.dagger2.di.Feature1ListComponent;

import javax.inject.Inject;


public class Feature1ListActivity extends AppCompatActivity {

    @Inject
    Feature1ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature1_list);
        Feature1ListComponent.Get.component(this).inject(this);
        getSupportActionBar().setTitle("Feature1ListActivity");
        presenter.setView(this);
    }


    public void display(String feature1Stuff) {
        ((TextView)findViewById(R.id.text)).setText(feature1Stuff);
    }
}
