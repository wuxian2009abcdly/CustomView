package com.genvict.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genvict.customview.R;
import com.genvict.customview.adapter.BaseAdapter;
import com.genvict.customview.view.TagLayout;

import java.util.ArrayList;
import java.util.List;

public class TagLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout);

        final TagLayout tagLayout = findViewById(R.id.taglayout);

        final List<String> tags = new ArrayList<>();
        tags.add("111");
        tags.add("11111");
        tags.add("111111");
        tags.add("111");
        tags.add("1111111");
        tags.add("11111111111111111");
        tags.add("111");
        tags.add("11");
        tags.add("111111111");
        tags.add("1111111111");
        tags.add("111111111111111");

        tagLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return tags.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView tagTv = LayoutInflater.from(TagLayoutActivity.this).inflate(R.layout.item_tag, parent, false).findViewById(R.id.tv_tag);
                tagTv.setText(tags.get(position));
                return tagTv;
            }
        });
    }
}
