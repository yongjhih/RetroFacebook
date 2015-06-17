/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package retrofacebook.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

import rx.Observable;
import rx.functions.*;

import rx.android.app.*;

import retrofacebook.*;

import java.util.Arrays;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";

    @InjectView(R.id.list)
    RecyclerView listView;

    private ListRecyclerAdapter<SimplePost, SimplePostViewHolder> listAdapter;

    public static class SimplePost {
        String title;
        String content;
        public String title() {
            return title;
        }
        public String content() {
            return content;
        }
        public SimplePost title(String title) {
            this.title = title;
            return this;
        }
        public SimplePost content(String content) {
            this.content = content;
            return this;
        }
        public static SimplePost create() {
            return new SimplePost();
        }
    }

    public static class SimplePostViewHolder extends BindViewHolder<SimplePost> {
        @InjectView(R.id.text1)
        TextView text1;
        @InjectView(R.id.text2)
        TextView text2;

        public SimplePostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        };

        @Override
        public void onBind(int position, SimplePost item) {
            if (!android.text.TextUtils.isEmpty(item.title())) text1.setText(item.title());
            if (!android.text.TextUtils.isEmpty(item.content())) text2.setText(item.content());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        listAdapter = ListRecyclerAdapter.create();

        listAdapter.createViewHolder(new Func2<ViewGroup, Integer, SimplePostViewHolder>() {
            @Override
            public SimplePostViewHolder call(@Nullable ViewGroup viewGroup, Integer position) {
                return new SimplePostViewHolder(LayoutInflater.from(CheeseDetailActivity.this).inflate(R.layout.item_post_card, viewGroup, false));
            }
        });

        listAdapter.getList().addAll(Arrays.asList(SimplePost.create(), SimplePost.create(), SimplePost.create()));

        listView.setLayoutManager(new android.support.v7.widget.LinearLayoutManager(this));
        listView.setAdapter(listAdapter);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
