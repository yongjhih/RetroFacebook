/*
 * Copyright (C) 2015 8tory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofacebook;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.*;
//import rx.PublishSubject;

import rx.functions.*;

import com.facebook.*;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

@RetroFacebook
public abstract class Facebook {
    @RetroFacebook.GET("/{post-id}")
    public abstract Observable<Post> getPost(@RetroFacebook.Path("post-id") String postId);

    @RetroFacebook.GET("/{user-id}/photos")
    public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/photos")
    //public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId, @RetroFacebook.Query String type);

    @RetroFacebook.GET("/{user-id}/photos?type=uploaded")
    public abstract Observable<Photo> getUploadedPhotos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Photo> getPhotos() {
        return getPhotos("me");
    }

    @RetroFacebook.GET("/{user-id}/feed")
    public abstract Observable<Post> getPosts(@RetroFacebook.Path("user-id") String userId);

    public Observable<Post> getPosts() {
        return getPosts("me");
    }

    @RetroFacebook.GET("/{user-id}/friends")
    public abstract Observable<User> getFriends(@RetroFacebook.Path("user-id") String userId);

    public Observable<User> getFriends() {
        return getFriends("me");
    }

    public static Facebook create() {
        return new RetroFacebook_Facebook();
    }

    Activity activity;
    Session.StatusCallback sessionStatusCallback;
    UiLifecycleHelper uiLifecycleHelper;
    Subject<Session, Session> sessionSubject;

    public Facebook initialize(Activity activity) {
        this.activity = activity;
        sessionSubject = PublishSubject.create();
        sessionStatusCallback = new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (exception != null) {
                    sessionSubject.onError(exception);
                } else {
                    sessionSubject.onNext(session);
                }
            }
        };
        uiLifecycleHelper = new UiLifecycleHelper(activity, sessionStatusCallback);

        return this;
    }

    public static Facebook create(Activity activity) {
        return create().initialize(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void onResume() {
        uiLifecycleHelper.onResume();
    }

    public Observable<Session> logIn() {
        return logInWithReadPermissions(Arrays.asList("public_profile", "user_friends", "user_photos", "user_posts"));
    }

    public static String getFacebookAppId(Context context) {
        return getApplicationMetaData(context, com.facebook.Settings.APPLICATION_ID_PROPERTY);
    }

    private static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (ai.metaData != null) {
                return ai.metaData.getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            // if we can't find it in the manifest, just return null
        }
        return null;
    }

    public Observable<Session> logInWithReadPermissions(final Collection<String> permissions) {
        return Observable.create(new Observable.OnSubscribe<Session>() {
            @Override
            public void call(final Subscriber<? super Session> sub) {
                Session.OpenRequest request = new Session.OpenRequest(activity);
                if (request != null) {
                    //request.setDefaultAudience(configuration.getSessionDefaultAudience());
                    //request.setLoginBehavior(configuration.getSessionLoginBehavior());

                    //if (isRead) {
                        request.setPermissions(new ArrayList(permissions));

                        /*
                         * In case there are also PUBLISH permissions, then we would ask
                         * for these permissions second time (after, user accepted the
                         * read permissions)
                         */
                        //if (configuration.hasPublishPermissions() && configuration.isAllPermissionsAtOnce()) {
                            //mSessionStatusCallback.setAskPublishPermissions(true);
                        //}

                        Session activeSession = Session.getActiveSession();
                        if (activeSession == null || activeSession.isClosed()) {
                            activeSession = new Session.Builder(activity.getApplicationContext()).setApplicationId(getFacebookAppId(activity)).build();
                            Session.setActiveSession(activeSession);
                        }
                        activeSession.addCallback(sessionStatusCallback);
                        sessionSubject.asObservable() // FIXME unsubscribe
                            .take(1)
                            .doOnNext(new Action1<Session>() {
                                @Override public void call(Session session) {
                                    if (session.getState().equals(SessionState.OPENED)) {
                                        sub.onNext(session);
                                    }
                                    sub.onCompleted();
                                }
                            })
                            .subscribe();
                        activeSession.openForRead(request);
                    //}
                }
            }
        });
    }
}
