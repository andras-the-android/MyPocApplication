package com.example.andras.myapplication.dagger2.ui.feature1.detail;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.andras.myapplication.R;
import com.example.andras.myapplication.dagger2.di.Feature1DetailComponent;
import com.example.andras.myapplication.dagger2.interactor.Feature1Interactor;

import javax.inject.Inject;

public class Feature1DetailActivity extends AppCompatActivity {

    @Inject
    Feature1Interactor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature1_list);
        Feature1DetailComponent.Get.component(this).inject(this);
        getSupportActionBar().setTitle("Feature1DetailActivity");
        ((TextView)findViewById(R.id.text)).setText(interactor.getFeature1Stuff());
    }
}
