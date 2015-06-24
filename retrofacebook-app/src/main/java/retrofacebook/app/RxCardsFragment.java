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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

import rx.Observable;
import rx.functions.*;

import rx.android.app.*;
import rx.android.view.ViewObservable;

import retrofacebook.*;

public class RxCardsFragment extends Fragment {
    RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = (RecyclerView) inflater.inflate(R.layout.fragment_list, container, false);

        listAdapter = ListRecyclerAdapter.create();
        listAdapter.createViewHolder(new Func2<ViewGroup, Integer, CardViewHolder>() {
            @Override
            public CardViewHolder call(ViewGroup parent, Integer position) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

                /*
                TypedValue typedValue = new TypedValue();
                parent.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
                view.setBackgroundResource(typedValue.resourceId);
                */

                return new CardViewHolder(view);
            }
        });

        listView.setLayoutManager(new LinearLayoutManager(listView.getContext()));
        listView.setAdapter(listAdapter);

        return listView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            AppObservable.bindFragment(RxCardsFragment.this, items).toList().subscribe(list -> {
                android.util.Log.d("RetroFacebook", "list: " + list);
                android.util.Log.d("RetroFacebook", "list.size(): " + list.size());
                listAdapter.getList().clear();
                listAdapter.getList().addAll(list);
                listAdapter.notifyDataSetChanged();
            });
        }
    }

    private ListRecyclerAdapter<RxCard, CardViewHolder> listAdapter;
    Observable<RxCard> items;

    public RxCardsFragment items(Observable<RxCard> items) {
        this.items = items;

        return this;
    }

    public static RxCardsFragment create() {
        return new RxCardsFragment();
    }

    public static class CardViewHolder extends BindViewHolder<RxCard> {
        @InjectView(R.id.icon)
        ImageView icon;
        @InjectView(R.id.text1)
        TextView text1;
        @InjectView(R.id.message)
        TextView message;
        @InjectView(R.id.image)
        ImageView image;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        };

        @Override
        public void onBind(int position, RxCard item) {
            ViewObservable.bindView(icon, item.icon).filter(url -> !android.text.TextUtils.isEmpty(url)).subscribe(url -> {
                Glide.with(itemView.getContext())
                    .load(url)
                    .fitCenter()
                    .into(icon);
            });
            ViewObservable.bindView(text1, item.text1)
                .filter(s -> !android.text.TextUtils.isEmpty(s))
                .subscribe(s -> text1.setText(s));
            ViewObservable.bindView(message, item.message)
                .filter(s -> !android.text.TextUtils.isEmpty(s))
                .subscribe(s -> message.setText(s));
            ViewObservable.bindView(image, item.image).filter(url -> !android.text.TextUtils.isEmpty(url)).subscribe(url -> {
                Glide.with(itemView.getContext())
                    .load(url)
                    .fitCenter()
                    .into(image);
            });
            ViewObservable.clicks(itemView).take(1).subscribe(ev -> {
                Context context = ev.view().getContext();
                Intent intent = new Intent(context, CheeseDetailActivity.class);

                String name = item.text1.toBlocking().singleOrDefault(null);
                if (!android.text.TextUtils.isEmpty(name)) intent.putExtra(CheeseDetailActivity.EXTRA_NAME, name);

                context.startActivity(intent);
            });
        }
    }
}

