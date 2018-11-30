package com.genvict.customview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.genvict.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author longy
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_textview)
    Button btnTextview;
    @BindView(R.id.btn_qqstepview)
    Button btnQqstepview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_textview, R.id.btn_qqstepview})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_textview:
                intent = new Intent(this, TextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qqstepview:
                intent = new Intent(this,QQStepViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
