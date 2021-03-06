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
    @BindView(R.id.btn_progressview)
    Button btnProgressview;
    @BindView(R.id.btn_ratingbar)
    Button btnRatingbar;
    @BindView(R.id.btn_loadView)
    Button btnLoadView;
    @BindView(R.id.btn_letterIndexView)
    Button btnLetterIndexView;
    @BindView(R.id.btn_taglayout)
    Button btnTaglayout;
    @BindView(R.id.btn_touchview)
    Button btnTouchview;
    @BindView(R.id.btn_zoomsideslip)
    Button btnZoomsideslip;
    @BindView(R.id.btn_lockpattern)
    Button btnLockpattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_textview, R.id.btn_qqstepview, R.id.btn_colorTrackView, R.id.btn_progressview, R.id.btn_ratingbar, R.id.btn_loadView, R.id.btn_letterIndexView, R.id.btn_taglayout, R.id.btn_touchview, R.id.btn_zoomsideslip, R.id.btn_lockpattern})
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
            case R.id.btn_progressview:
                intent = new Intent(this, ProgressViewActivity.class);
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
            case R.id.btn_letterIndexView:
                intent = new Intent(this, LetterSideBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_taglayout:
                intent = new Intent(this, TagLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_touchview:
                intent = new Intent(this, TouchViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_zoomsideslip:
                intent = new Intent(this, SidingMenuActivity.class);
                startActivity(intent);
            case R.id.btn_lockpattern:
                intent = new Intent(this, LockPatternActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
