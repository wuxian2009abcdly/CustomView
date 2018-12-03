package com.genvict.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.genvict.customview.R;
import com.genvict.customview.view.LoadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author longy
 */
public class LoadViewActivity extends AppCompatActivity {

    @BindView(R.id.loadview)
    LoadView loadview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_view);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadview.exchange();
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
