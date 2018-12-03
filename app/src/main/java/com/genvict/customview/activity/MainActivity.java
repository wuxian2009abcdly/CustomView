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
    @BindView(R.id.btn_colorTrackView)
    Button btnColorTrackView;
    @BindView(R.id.btn_ratingbar)
    Button btnRatingbar;
    @BindView(R.id.btn_loadView)
    Button btnLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_textview, R.id.btn_qqstepview, R.id.btn_colorTrackView, R.id.btn_ratingbar, R.id.btn_loadView})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_textview:
                intent = new Intent(this, TextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qqstepview:
                intent = new Intent(this, QQStepViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_colorTrackView:
                intent = new Intent(this, ColorTrackActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ratingbar:
                intent = new Intent(this, RatingBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_loadView:
                intent = new Intent(this, LoadViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
