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
import android.widget.EditText;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

import rx.Observable;
import rx.Subscription;
import rx.functions.*;
import rx.subjects.*;

import rx.android.app.*;
import rx.android.view.ViewObservable;

import retrofacebook.*;
import android.support.v4.widget.SwipeRefreshLayout;

public class RxCardsFragment extends Fragment {
    @InjectView(R.id.list)
    RecyclerView listView;
    @InjectView(R.id.refresh)
    SwipeRefreshLayout refreshView;

    //Subject<View, View> viewSubject = PublishSubject.create();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_swipe, container, false);
        ButterKnife.inject(this, view);
        //viewSubject.onNext(view);

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

        refreshView.setOnRefreshListener(() -> {
            load();
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            //if (refreshView == null) {
                //viewSubject.asObservable().subscribe(v -> load()); // FIXME onDestoryView unsubscribe subscription?
            //} else {
                load();
            //}
        }
    }

    public Subscription load() {
        if (refreshView != null) refreshView.setRefreshing(true);
        return AppObservable.bindFragment(RxCardsFragment.this, items).toList().subscribe(list -> {
            android.util.Log.d("RetroFacebook", "list: " + list);
            android.util.Log.d("RetroFacebook", "list.size(): " + list.size());
            listAdapter.getList().clear();
            listAdapter.getList().addAll(list);
            listAdapter.notifyDataSetChanged();
        }, e -> {}, () -> {
            refreshView.setRefreshing(false);
        });
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
        @InjectView(R.id.comments)
        RecyclerView commentsView;
        @InjectView(R.id.likeCount)
        TextView likeCountView;
        @InjectView(R.id.like)
        ImageView likeView;
        @InjectView(R.id.comment)
        ImageView commentView;
        @InjectView(R.id.commentCount)
        TextView commentCountView;
        @InjectView(R.id.comment_avatar)
        ImageView commentAvatar;
        @InjectView(R.id.comment_edit)
        EditText commentEdit;
        @InjectView(R.id.send)
        ImageView sendView;

        ListRecyclerAdapter<Comment, CommentViewHolder> commentsAdapter;
        boolean liked;
        int likeCount;
        int commentCount;
        User meUser;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            commentsAdapter = ListRecyclerAdapter.create();
            commentsAdapter.createViewHolder(new Func2<ViewGroup, Integer, CommentViewHolder>() {
                @Override
                public CommentViewHolder call(ViewGroup parent, Integer position) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

                    return new CommentViewHolder(view);
                }
            });

            commentsView.setLayoutManager(new MeasuredLinearLayoutManager(commentsView.getContext()));
            commentsView.setAdapter(commentsAdapter);
        }

        @Override
        public void onBind(int position, RxCard item) {
            icon.setVisibility(View.GONE);
            ViewObservable.bindView(icon, item.icon).filter(url -> !android.text.TextUtils.isEmpty(url)).subscribe(url -> {
                icon.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                    .load(url)
                    .fitCenter()
                    .into(icon);
            });

            itemView.setOnClickListener(v -> {}); // clear
            ViewObservable.bindView(text1, item.text1)
                .filter(name -> !android.text.TextUtils.isEmpty(name))
                .subscribe(name -> {
                    text1.setText(name);

                    itemView.setOnClickListener(v -> {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CheeseDetailActivity.class);

                        if (!android.text.TextUtils.isEmpty(name)) intent.putExtra(CheeseDetailActivity.EXTRA_NAME, name);

                        context.startActivity(intent);
                    });
                });

            ViewObservable.bindView(message, item.message)
                .filter(s -> !android.text.TextUtils.isEmpty(s))
                .subscribe(s -> message.setText(s));

            image.setVisibility(View.GONE);
            ViewObservable.bindView(image, item.image).filter(url -> !android.text.TextUtils.isEmpty(url)).subscribe(url -> {
                image.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                    .load(url)
                    .fitCenter()
                    .into(image);
            });

            likeCount = 0;
            likeCountView.setText("" + likeCount); // clear
            ViewObservable.bindView(likeCountView, item.likeCount).subscribe(i -> {
                likeCount = i;
                likeCountView.setText("" + likeCount);
            });

            commentCount = 0;
            commentCountView.setText("" + commentCount); // clear
            ViewObservable.bindView(commentCountView, item.commentCount).subscribe(i -> {
                commentCount = i;
                commentCountView.setText("" + commentCount);
            });

            liked = false;
            likeView.setOnClickListener(v -> {}); // clear
            Glide.with(itemView.getContext())
                .load(R.drawable.ic_thumb_up_outline)
                .fitCenter()
                .into(likeView);
            ViewObservable.bindView(likeView, item.liked).subscribe(b -> {
                liked = b;
                android.util.Log.d("RetroFacebook", "liked: " + liked);
                if (liked) {
                    Glide.with(itemView.getContext())
                        .load(R.drawable.ic_thumb_up)
                        .fitCenter()
                        .into(likeView);
                } else {
                    Glide.with(itemView.getContext())
                        .load(R.drawable.ic_thumb_up_outline)
                        .fitCenter()
                        .into(likeView);
                }

                likeView.setOnClickListener(v -> {
                    liked = !liked;

                    android.util.Log.d("RetroFacebook", "be liked: " + liked);

                    if (liked) {
                        android.util.Log.d("RetroFacebook", "like");
                        likeCount += 1;
                        Glide.with(itemView.getContext())
                            .load(R.drawable.ic_thumb_up)
                            .fitCenter()
                            .into(likeView);
                        item.like.subscribe();
                    } else {
                        android.util.Log.d("RetroFacebook", "unlike");
                        likeCount -= 1;
                        Glide.with(itemView.getContext())
                            .load(R.drawable.ic_thumb_up_outline)
                            .fitCenter()
                            .into(likeView);
                        item.unlike.subscribe();
                    }

                    likeCountView.setText("" + likeCount);
                });
            });

            commentsView.setVisibility(View.GONE);
            commentsAdapter.getList().clear();
            ViewObservable.bindView(commentsView, item.comments).toList().subscribe(list -> {
                    commentsAdapter.getList().addAll(list);
                    commentsAdapter.notifyDataSetChanged();
                    commentsView.setVisibility(View.VISIBLE);
                });

            meUser = null;
            ViewObservable.bindView(commentAvatar, Facebook.get().me()).subscribe(me -> {
                meUser = me;
                Glide.with(commentAvatar.getContext())
                    .load("http://graph.facebook.com/" + me.id() + "/picture?width=400&height=400")
                    .fitCenter()
                    .into(commentAvatar);
            });

            sendView.setOnClickListener(v -> {
                if (meUser != null) {
                    final String commentText = commentEdit.getText().toString();
                    commentEdit.setText("");
                    item.comment(commentText).subscribe(struct -> {
                        Comment comment = Comment.builder()
                            .id(struct.id())
                            .message(commentText)
                            .from(meUser)
                            .likeCount(0)
                            .userLikes(false)
                            .build();
                        commentsAdapter.getList().add(comment);
                        commentsAdapter.notifyItemInserted(commentsAdapter.getItemCount() - 1);
                    });
                }
            });
        }
    }

    public static class CommentViewHolder extends BindViewHolder<Comment> {
        @InjectView(R.id.icon)
        ImageView icon;
        @InjectView(R.id.text1)
        TextView text1;
        @InjectView(R.id.like)
        ImageView likeView;
        @InjectView(R.id.likes)
        TextView likes;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        boolean liked;
        int likeCount;

        @Override
        public void onBind(int position, Comment item) {
            android.util.Log.d("RetroFacebook", "comment: " + item);
            android.util.Log.d("RetroFacebook", "comment: " + item.message());

            Glide.with(itemView.getContext())
                .load("http://graph.facebook.com/" + item.from().id() + "/picture?width=400&height=400")
                .fitCenter()
                .into(icon);
            text1.setText(item.message());

            likeCount = item.likeCount();
            likes.setText("" + likeCount);

            liked = item.userLikes();
            if (liked) {
                Glide.with(itemView.getContext())
                    .load(R.drawable.ic_thumb_up)
                    .fitCenter()
                    .into(likeView);
            } else {
                Glide.with(itemView.getContext())
                    .load(R.drawable.ic_thumb_up_outline)
                    .fitCenter()
                    .into(likeView);
            }

            likeView.setOnClickListener(v -> {
                liked = !liked;

                if (liked) {
                    likeCount += 1;
                    Glide.with(itemView.getContext())
                        .load(R.drawable.ic_thumb_up)
                        .fitCenter()
                        .into(likeView);
                    item.like().subscribe(s -> {}, e -> {});
                } else {
                    likeCount -= 1;
                    Glide.with(itemView.getContext())
                        .load(R.drawable.ic_thumb_up_outline)
                        .fitCenter()
                        .into(likeView);
                    item.unlike().subscribe(s -> {}, e -> {});
                }
                likes.setText("" + likeCount);
            });

        }
    }
}

