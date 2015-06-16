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

import android.content.Intent;
import android.app.Activity;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

@RetroFacebook
public abstract class Facebook {
    @RetroFacebook.GET("/{postId}")
    public abstract Observable<Post> getPost(@RetroFacebook.Path String postId);

    // TODO @RetroFacebook.GET("/{userId}/photos?type=uploaded")
    @RetroFacebook.GET("/{userId}/photos")
    public abstract Observable<Photo> getPhotos(@RetroFacebook.Path String userId);

    public Observable<Photo> getPhotos() {
        return getPhotos("me");
    }

    @RetroFacebook.GET("/{userId}/feed")
    public abstract Observable<Post> getPosts(@RetroFacebook.Path String userId);

    public Observable<Post> getPosts() {
        return getPosts("me");
    }

    @RetroFacebook.GET("/{userId}/friends")
    public abstract Observable<User> getFriends(@RetroFacebook.Path String userId);

    public Observable<User> getFriends() {
        return getFriends("me");
    }

    public static Facebook create() {
        return new RetroFacebook_Facebook();
    }

    Activity activity;

    public Facebook initialize(Activity activity) {
        this.activity = activity;

        return this;
    }

    public static Facebook create(Activity activity) {
        return create().initialize(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
