package retrofacebook.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.NavUtils;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.*;

import rx.android.app.*;

import rx.facebook.RxFacebook;
import retrofacebook.*;

import android.support.v4.widget.SwipeRefreshLayout;

public class MainFragment extends Fragment {

    @InjectView(R.id.list)
    RecyclerView listView;
    @InjectView(R.id.loading)
    SwipeRefreshLayout loading;

    private Handler handler;
    private SwipeRefreshLayout.OnRefreshListener refresher;

    private ListRecyclerAdapter<Photo, PhotoViewHolder> listAdapter;

    public MainFragment() {
        handler = new Handler();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.util.Log.d("RetroFacebook", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        listAdapter = ListRecyclerAdapter.create();

        listAdapter.createViewHolder(new Func2<ViewGroup, Integer, PhotoViewHolder>() {
            @Override
            public PhotoViewHolder call(@Nullable ViewGroup viewGroup, Integer position) {
                return new PhotoViewHolder(inflater.inflate(R.layout.item_photo, viewGroup, false));
            }
        });

        listView.setLayoutManager(new android.support.v7.widget.LinearLayoutManager(getActivity()));
        listView.setAdapter(listAdapter);

        return view;
    }

    private RxFacebook mRxFacebook;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRxFacebook = ((MainActivity) getActivity()).mRxFacebook;

        refresher = new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                loading.setRefreshing(true);
                AppObservable.bindFragment(MainFragment.this, mRxFacebook.logIn().doOnNext(login -> {
                            android.util.Log.d("RetroFacebook", "token: " + login.getAccessToken());
                            android.util.Log.d("RetroFacebook", "token: " + login.getAccessToken().getToken());
                        }).flatMap(login -> Facebook.create().getPhotos("me")))
                        .doOnNext(p -> {
                            android.util.Log.d("RetroFacebook", "caption: " + p.caption() + " :caption");
                            android.util.Log.d("RetroFacebook", "from: " + p.from() + " :from");
                            android.util.Log.d("RetroFacebook", "from.name: " + ((User) p.from()).name() + " :from.name");
                            android.util.Log.d("RetroFacebook", "picture: " + p.picture() + " :picture");
                            try {
                                //android.util.Log.d("RetroFacebook", "image: " + p.images() == null ? null : ((Image) p.images().get(0)).source());
                            } catch (NullPointerException e) {
                                //android.util.Log.d("RetroFacebook", "image: " + "null");
                            }
                        })
                        .take(10)
                        .toList()
                        .subscribe(new Action1<List<Photo>>() {
                            @Override
                            public void call(final List<Photo> list) {
                                android.util.Log.d("RetroFacebook", "onNext list");
                                loading.setRefreshing(false);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listAdapter.getList().clear();
                                        listAdapter.getList().addAll(list);
                                        listAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }, e -> {
                            loading.setRefreshing(false);
                            android.util.Log.e("RetroFacebook", "" + e);
                            e.printStackTrace();
                        }, () -> {
                            android.util.Log.e("RetroFacebook", "onCompleted");
                            loading.setRefreshing(false);
                        });
            }
        };

        loading.setOnRefreshListener(refresher);

        handler.post(new Runnable() {
            @Override
            public void run() {
                refresher.onRefresh();
            }
        });
    }

    public static class PhotoViewHolder extends BindViewHolder<Photo> {
        @InjectView(R.id.icon)
        SimpleDraweeView icon;
        @InjectView(R.id.text1)
        TextView text1;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        };

        @Override
        public void onBind(int position, Photo item) {
            try {
                //android.util.Log.d("RetroFacebook", "image: " + item.images() == null ? null : ((Image) item.images().get(0)).source());
                //icon.setImageURI(Uri.parse(((Image) item.images().get(0)).source()));
                icon.setImageURI(Uri.parse(item.source()));
            } catch (NullPointerException e) {
                //android.util.Log.d("RetroFacebook", "image: " + "null");
            }
            text1.setText(item.caption());
        }
    }
}
