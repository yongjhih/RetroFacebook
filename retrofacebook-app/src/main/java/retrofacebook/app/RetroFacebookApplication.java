package retrofacebook.app;

import android.app.ActivityManager;
import android.app.Application;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.facebook.drawee.backends.pipeline.Fresco;

public class RetroFacebookApplication extends Application {
    //public static final String APP_NAMESPACE = "sromkuapp_vtwo"; // credit sromku
    public static final String APP_NAMESPACE = "retrofacebook";
    //public static final String APP_NAMESPACE = "retrofacebook_test";

    @Override
    public void onCreate() {
        super.onCreate();

        //Timber.plant(new Timber.DebugTree());

        Fresco.initialize(this);
    }

    public static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (info.metaData != null) {
                return info.metaData.getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }
}
