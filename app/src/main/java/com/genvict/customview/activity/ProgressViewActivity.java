package com.genvict.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

import com.genvict.customview.R;
import com.genvict.customview.view.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressViewActivity extends AppCompatActivity {

    @BindView(R.id.pv_test)
    ProgressView pvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        ButterKnife.bind(this);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(8000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                pvTest.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
}
