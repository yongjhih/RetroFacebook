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

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.*;

import retrofacebook.*;

import butterknife.InjectView;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

import com.bumptech.glide.Glide;
import android.graphics.Bitmap;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Facebook facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        setupAdapter(adapter);
        viewPager.setAdapter(adapter);
    }

    private void setupAdapter(Adapter adapter) {
        facebook = Facebook.create(this);
        adapter.fragments.add(FragmentPage.create().title("Photos").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getUploadedPhotos().take(32)
                    .doOnNext(photo -> {
                        android.util.Log.d("RetroFacebook", "user: " + photo.from());
                        android.util.Log.d("RetroFacebook", "photo.caption: " + photo.caption());
                    })
                    .map(photo -> {
                        RxCard card = new RxCard();
                        card.icon = Observable.just("http://graph.facebook.com/" + photo.from().id() + "/picture?width=400&height=400");
                        card.text1 = Observable.just(photo.from().name());
                        card.message = Observable.just(photo.caption());
                        card.image = Observable.just(photo.images().get(0).source());

                        if (photo.comments() != null) {
                            card.comments = Observable.from(photo.comments().data());
                            card.commentCount = card.comments.count();
                        } else {
                            card.comments = facebook.getComments(photo.id());
                            card.commentCount = card.comments.count();
                        }
                        card.like = photo.like();
                        card.unlike = photo.unlike();
                        if (photo.likes() != null) {
                            card.likeCount = Observable.from(photo.likes().data()).count();
                            card.liked = facebook.me().concatMap(me -> {
                                android.util.Log.d("RetroFacebook", "me: " + me.id());
                                return Observable.from(photo.likes().data()).filter(user -> user.id().equals(me.id())).isEmpty().map(b -> !b);
                            });
                        } else {
                            card.likeCount = facebook.getLikedUsers(photo).count();
                            card.liked = facebook.me().concatMap(me -> {
                                android.util.Log.d("RetroFacebook", "me: " + me.id());
                                return facebook.getLikedUsers(photo).filter(user -> user.id().equals(me.id())).isEmpty().map(b -> !b);
                            });
                        }
                        card.onCommentText = comment -> {
                            return facebook.comment(comment, photo);
                        };
                        return card;
                    }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Friends").fragment(() -> {
            return ListFragment.create().items(facebook.getFriends().map(user -> {
                    return Item.builder()
                        .icon("http://graph.facebook.com/" + user.id() + "/picture?width=400&height=400")
                        .text1(user.name())
                        .build();
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Posts").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getPosts().take(32).map(post -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + post.from().id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(post.from().name());
                    card.message = Observable.just(post.message());
                    //card.image = Observable.just(post.picture());
                    card.image = post.photo().map(photo -> photo.images().get(0).source());
                    if (post.comments() != null) {
                        card.comments = Observable.from(post.comments().data());
                        card.commentCount = card.comments.count();
                    } else {
                        card.comments = facebook.getComments(post.id());
                        card.commentCount = card.comments.count();
                    }
                    card.like = post.like();
                    card.unlike = post.unlike();
                    if (post.likes() != null) {
                        card.likeCount = Observable.from(post.likes().data()).count();
                        card.liked = facebook.me().concatMap(me -> {
                            android.util.Log.d("RetroFacebook", "me: " + me.id());
                            return Observable.from(post.likes().data()).filter(user -> user.id().equals(me.id())).isEmpty().map(b -> !b);
                        });
                    } else {
                        card.likeCount = facebook.getLikedUsers(post).count();
                        card.liked = facebook.me().concatMap(me -> {
                            android.util.Log.d("RetroFacebook", "me: " + me.id());
                            return facebook.getLikedUsers(post).filter(user -> user.id().equals(me.id())).isEmpty().map(b -> !b);
                        });
                    }
                    card.onCommentText = comment -> {
                        return facebook.comment(comment, post);
                    };
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Publish").fragment(() -> {
            return CardsFragment.create()
                .items(Observable.defer(() -> {
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide.with(this)
                            .load("https://raw.githubusercontent.com/yongjhih/RetroFacebook/master/art/retrofacebook.png")
                            .asBitmap()
                            .into(100, 100)
                            .get();
                    } catch (Throwable e) {
                        return Observable.error(e);
                    }
                    return Observable.just(bitmap);
                }).subscribeOn(Schedulers.io())
                .flatMap(bitmap -> {
                    Log.d("retrofacebook", "bitmap: " + bitmap);
                    return facebook.publish(Photo.builder()
                        .message("yo")
                        .pictureBitmap(bitmap)
                        .build());
                })
                .map(response -> {
                    return Card.builder()
                        .text1(response.id())
                        .message(response.id())
                        .build();
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Albums").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getAlbums().take(32).map(album -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + album.from().id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(album.name());
                    card.message = Observable.just(album.id());
                    //card.image = facebook.getAlbumThumbnail(album.id()).map(pic -> pic.data().url()); // FACEBOOK_NON_JSON_RESULT
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Family").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getFamily().take(32).map(user -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + user.id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(user.name());
                    card.message = Observable.just(user.relationship());
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Groups").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getGroups().take(32).map(group -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just(group.icon());
                    card.text1 = Observable.just(group.name());
                    card.message = Observable.just(group.description());
                    card.image = Observable.just(group.cover()).filter(c -> c != null).map(c -> c.source());
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Notifications").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getNotifications().take(32).map(notification -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + notification.from().id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(notification.title());
                    card.message = Observable.just(notification.title());
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Scores").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getScores().take(32).map(score -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + score.user().id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(score.application().name());
                    card.message = Observable.just("" + score.score());
                    return card;
                }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Videos").fragment(() -> {
            return RxCardsFragment.create().items(facebook.getUploadedVideos().take(32).map(video -> {
                    RxCard card = new RxCard();
                    card.icon = Observable.just("http://graph.facebook.com/" + video.from().id() + "/picture?width=400&height=400");
                    card.text1 = Observable.just(video.name());
                    card.message = Observable.just(video.description() + video.source());
                    card.image = Observable.just(video.picture());
                    return card;
                }));
        }));
        /* {FacebookServiceException: httpResponseCode: 400, facebookErrorCode: 15, facebookErrorType: OAuthException, message: (#15) This method must be called with an app access_token.}
        adapter.fragments.add(FragmentPage.create().fragment(() -> {
            return CardsFragment.create()
                .items(facebook.searchTopic("clinton")
                    .map(p -> {
                        return Card.builder()
                            .text1(p.id())
                            .message(p.name())
                            .build();
                }));
        }).title("Search"));
        // oauth
        adapter.fragments.add(FragmentPage.create().title("Mark Elliot Zuckerberg's photos").fragment(() -> {
            return CardsFragment.create()
                .items(
                    facebook.getPhotos("4")
                    .take(32)
                    .doOnNext(photo -> {
                        android.util.Log.d("RetroFacebook", "user: " + photo.from());
                        android.util.Log.d("RetroFacebook", "photo.caption: " + photo.caption());
                    })
                    .doOnCompleted(() -> {
                    })
                    .map(photo -> {
                        return Card.builder()
                            .icon("http://graph.facebook.com/" + photo.from().id() + "/picture?width=400&height=400")
                            .text1(photo.from().name())
                            .message(photo.caption())
                            .image(photo.images().get(0).source())
                            .build();
                    }));
        }));
        adapter.fragments.add(FragmentPage.create().title("Mark Elliot Zuckerberg's posts").fragment(() -> {
            return CardsFragment.create()
                .items(facebook.getPosts("4").take(32).map(post -> {
                    return Card.builder()
                        .icon("http://graph.facebook.com/" + post.from().id() + "/picture?width=400&height=400")
                        .text1(post.from().name())
                        .message(post.message())
                        .build();
                }));
        }));
        */
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 4"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 5"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 6"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 7"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 8"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 9"));
        //adapter.fragments.add(FragmentPage.create().fragment(() -> new CheeseListFragment()).title("Category 10"));
        //adapter.notifyDataSetChanged();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    static class FragmentPage {
        Func0<Fragment> onFragment;
        Fragment fragment;
        String title;

        public Fragment fragment() {
            if (fragment == null) fragment = onFragment.call();
            return fragment;
        }

        public String title() {
            return title;
        }

        public FragmentPage fragment(Func0<Fragment> onFragment) {
            this.onFragment = onFragment;
            return this;
        }

        public FragmentPage title(String title) {
            this.title = title;
            return this;
        }

        public static FragmentPage create() {
            return new FragmentPage();
        }

    }

    static class Adapter extends FragmentPagerAdapter {
        public List<FragmentPage> fragments = new ArrayList<>(); // NOTICE: memleak

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position).fragment();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).title();
        }

        //@Override
        //public int getItemPosition(Object object) {
            //return FragmentPagerAdapter.POSITION_NONE;
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.onActivityResult(requestCode, resultCode, data);
    }
}
