package com.markzl.android.demo.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.markzl.android.annotation.BindView;
import com.markzl.android.demo.R;
import com.markzl.android.viewfinder.ViewFinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_hello)
    public TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.inject(this);
        text.setText("Hello,ViewFinder");
    }
}
