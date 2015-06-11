package retrofacebook.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import butterknife.InjectView;
import butterknife.ButterKnife;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import rx.facebook.RxFacebook;

import rx.android.app.*;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.pager)
    ViewPager mViewPager;
    @InjectView(R.id.tab)
    SmartTabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mRxFacebook = RxFacebook.create(this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "retrofacebook.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("RetroFacebook", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        }

        /*
        mRxFacebook.logIn().doOnNext(login -> {
            android.util.Log.d("RetroFacebook", "token: " + login.getAccessToken());
            android.util.Log.d("RetroFacebook", "token: " + login.getAccessToken().getToken());
        }).subscribe(login -> {
        }, e -> {
            android.util.Log.e("RetroFacebook", "error: " + e);
            e.printStackTrace();
        });
        */

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Photos", MainFragment.class)
                .create());

        mViewPager.setAdapter(adapter);

        mTab.setViewPager(mViewPager);
        //mTab.setOnPageChangeListener(mPageChangeListener);
    }

    public RxFacebook mRxFacebook;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRxFacebook.onActivityResult(requestCode, resultCode, data);
    }
}
