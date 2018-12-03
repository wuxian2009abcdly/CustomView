package com.genvict.customview.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

import com.genvict.customview.R;
import com.genvict.customview.view.QQStepView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author longy
 */
public class QQStepViewActivity extends AppCompatActivity {

    @BindView(R.id.qqStepView)
    QQStepView qqStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqstep_view);
        ButterKnife.bind(this);

        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, 3000);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentStep = (int) animation.getAnimatedValue();
                qqStepView.setCurrentStep(currentStep);
            }
        });
        valueAnimator.start();
    }

}
