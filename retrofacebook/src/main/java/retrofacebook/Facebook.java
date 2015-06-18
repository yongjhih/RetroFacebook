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

import rx.functions.*;

import com.facebook.*;
import com.facebook.login.*;

import android.content.Intent;
import android.app.Activity;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

@RetroFacebook
public abstract class Facebook {
    @RetroFacebook.GET("/{post-id}")
    public abstract Observable<Post> getPost(@RetroFacebook.Path("post-id") String postId);

    // TODO @RetroFacebook.GET("/{userId}/photos?type=uploaded")
    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/user/photos/">Graph API User Photo Edge - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/photos", permissions = "user_photos")
    public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/photos")
    //public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId, @RetroFacebook.Query String type);

    @RetroFacebook.GET("/{user-id}/photos?type=uploaded")
    public abstract Observable<Photo> getUploadedPhotos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Photo> getPhotos() {
        return getPhotos("me");
    }

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/user/feed">{user-id}/feed - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/feed", permissions = "user_posts")
    public abstract Observable<Post> getPosts(@RetroFacebook.Path("user-id") String userId);

    public Observable<Post> getPosts() {
        return getPosts("me");
    }

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/user/friends">Graph API /user/friends - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/friends", permissions = "user_friends")
    public abstract Observable<User> getFriends(@RetroFacebook.Path("user-id") String userId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/user/friendlists">User friendlists - Facebook Developers</a>
     */
    @RetroFacebook.GET("/{user-id}/friendlists")
    public abstract Observable<FriendList> getFriendLists(@RetroFacebook.Path("user-id") String userId);

    /**
     * Single.
     *
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/friend-list/">Friend List - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{friend-list-id}", permissions = "read_custom_friendlists")
    public abstract Observable<FriendList> getFriendList(@RetroFacebook.Path("friend-list-id") String friendListId);

    public Observable<User> getFriends() {
        return getFriends("me");
    }

    public static Facebook create() {
        return new RetroFacebook_Facebook();
    }

    // LifeCycle management

    CallbackManager callbackManager;
    Activity activity;

    public Facebook initialize(Activity activity) {
        this.activity = activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        return this;
    }

    public static Facebook create(Activity activity) {
        return create().initialize(activity);
    }

    public Observable<LoginResult> logIn() {
        return logInWithReadPermissions(Arrays.asList("public_profile", "user_friends", "user_photos", "user_posts"));
    }

    public Observable<LoginResult> logInWithReadPermissions(final Collection<String> permissions) {
        return Observable.create(new Observable.OnSubscribe<LoginResult>() {
            @Override public void call(final Subscriber<? super LoginResult> sub) {
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        sub.onNext(loginResult);
                        sub.onCompleted();
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
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
