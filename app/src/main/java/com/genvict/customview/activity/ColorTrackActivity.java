package com.genvict.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.genvict.customview.R;
import com.genvict.customview.view.ColorTrackView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author longy
 */
public class ColorTrackActivity extends AppCompatActivity {

    @BindView(R.id.ctv_test)
    ColorTrackView ctvTest;
    @BindView(R.id.leftToRight)
    Button leftToRight;
    @BindView(R.id.rightToLeft)
    Button rightToLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_track);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.leftToRight, R.id.rightToLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftToRight:
                startAnimator(ColorTrackView.Direction.LEFT_TO_RIGHT);
                break;
            case R.id.rightToLeft:
                startAnimator(ColorTrackView.Direction.RIGHT_TO_LEFT);
                break;
            default:
                break;
        }
    }

    private void startAnimator(final ColorTrackView.Direction direction) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (Float) animation.getAnimatedValue();
                ctvTest.setDirection(direction);
                ctvTest.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }

}
