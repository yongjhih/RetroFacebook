package rx.facebook;

import java.util.Collection;
import java.util.Arrays;

import android.app.Activity;

import rx.Observable;

import rx.android.app.*;

import com.facebook.*;
import com.facebook.login.*;
import android.content.Intent;

public class RxFacebook {
    CallbackManager callbackManager;
    Activity activity;

    public RxFacebook(Activity activity) {
        this.activity = activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public static RxFacebook create(Activity activity) {
        return new RxFacebook(activity);
    }

    public Observable<LoginResult> logIn() {
        return logInWithReadPermissions(Arrays.asList("public_profile", "user_friends", "user_photos", "user_posts"));
    }

    public Observable<LoginResult> logInWithReadPermissions(Collection<String> permissions) {
        return Observable.create(sub -> {
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    sub.onNext(loginResult);
                }

                @Override
                public void onCancel() {
                    sub.onCompleted();
                }

                @Override
                public void onError(FacebookException error) {
                    sub.onError(error);
                }
            });
            LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
