# RetroFacebook

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RetroFacebook-green.svg?style=flat)](https://android-arsenal.com/details/1/2007)
[![javadoc.io](https://javadocio-badges.herokuapp.com/com.infstory/retrofacebook/badge.svg)](http://www.javadoc.io/doc/com.infstory/retrofacebook/)
[![Build Status](https://travis-ci.org/yongjhih/RetroFacebook.svg)](https://travis-ci.org/yongjhih/RetroFacebook)
[![Join the chat at https://gitter.im/yongjhih/RetroFacebook](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/yongjhih/RetroFacebook?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![](https://fbstatic-a.akamaihd.net/rsrc.php/yl/r/H3nktOa7ZMg.ico)Page](https://www.facebook.com/retrothis)

[![](https://avatars0.githubusercontent.com/u/5761889?v=3&s=48)](https://github.com/Wendly)
[![](https://avatars3.githubusercontent.com/u/213736?v=3&s=48)](https://github.com/yongjhih)
Contributors..
[![](https://avatars1.githubusercontent.com/u/583039?v=3&s=48)](https://github.com/sromku)
[![](https://avatars0.githubusercontent.com/u/1476561?v=3&s=48)](https://github.com/frankiesardo)
[![](https://avatars3.githubusercontent.com/u/2039026?v=3&s=48)](https://github.com/EricKuck)
Credit..

![RetroFacebook.png](art/retrofacebook.png) + ![auto-json](art/auto-json.png)

Retrofit Facebook SDK for v3, v4.

RetroFacebook turns Facebook API into a Java interface using RxJava.

Easy to add API and model.

Inspired by retrofit.

[Live DEMO / DEMO app](#demo-app)

![photos.png](art/screenshot-photos-200.png) ![photos.png](art/screenshot-friends-200.png) ![posts.png](art/screenshot-posts-200.png)
![](art/screenshot-family-200.png) ![](art/screenshot-notifications-200.png) ![](art/screenshot-comment-200.png)

## Usage

My posts:

Before:

```java
GraphRequest request = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/feed", new GraphRequest.Callback() {
    @Override
    public void onCompleted(GraphResponse response) {
        // Gson
        // Gson gson = new Gson();
        // Posts posts = gson.fromJson(response.getJSONObject().toString(), Posts.class);
        // or
        // jackson
        // ObjectMapper mapper = new ObjectMapper();
        // Posts posts = mapper.readValue(response.getJSONObject().toString(), Posts.class);
        // or
        // LoganSquare
        // Posts posts = LoganSquare.parse(response.getJSONObject().toString(), Posts.class);
        // or manual

        // hasNext?  request = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT); blah, blah
    }
});
GraphRequest.executeBatchAsync(new GraphRequestBatch(request));
```

After:

```java
Facebook facebook = Facebook.create(activity);

Observable<Post> myPosts = facebook.getPosts();
myPosts.take(100).forEach(post -> System.out.println(post.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/me/feed")
    abstract Observable<Post> getPosts();
}
```

That's it!

And, callback mode:

```java
facebook.getPosts(new Callback<>() {
    @Override public void onCompleted(List<Post> posts) {
        // ...
    }
    @Override public void onError(Throwable e) {
        // ...
    }
});
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/me/feed")
    abstract void getPosts(Callback<Post> callback);
}
```

![Mark](https://graph.facebook.com/4/picture?width=160&height=160)Mark Elliot Zuckerberg's posts:

```java
String zuckId = "4";
Observable<Post> zuckPosts = facebook.getPosts(zuckId);
zuckPosts.forEach(post -> System.out.println(post.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/{user-id}/feed")
    abstract Observable<Post> getPosts(@Path("user-id") String userId);
}
```

Mark Elliot Zuckerberg's uploaded photos:

```java
Observable<Photo> zuckUploadedPhotos = facebook.getUploadedPhotos("4");
zuckUploadedPhotos.forEach(photo -> System.out.println(photo.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/{user-id}/photos?type=uploaded")
    abstract Observable<Photo> getUploadedPhotos() String userId);
}
```

My uploaded photos:

```java
Observable<Photo> myUploadedPhotos = facebook.getPhotosOfType("uploaded");
myPhotos.forEach(photo -> System.out.println(photo.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/me/photos")
    abstract Observable<Post> getPhotosOfType(@Query("type") String type); // getPhotosOfType("uploaded") -> /me/photos?type=uploaded
}
```

Publish:

```java
facebook.publish(Post.builder()
    .message("yo")
    .name("RetroFacebook")
    .caption("RetroFacebook")
    .description("Retrofit Facebook Android SDK")
    .picture("https://raw.githubusercontent.com/yongjhih/RetroFacebook/master/art/retrofacebook.png")
    .link("https://github.com/yongjhih/RetroFacebook")
    .build()).subscribe();
```

```java
@RetroFacebook
abstract class Facebook {
    @POST("/me/feed")
    abstract Observable<Struct> publish(@Body Post post);
}
```

## Auto Login

Auto login if needed while any API calling.

## Auto Permission

Auto request needed permission while API calling:

```java
@RetroFacebook
abstract class Facebook {
    @POST(value = "/me/feed", permissions = "publish_actions") // <- request `publish_actions` permission while `publish(post)`.
    abstract Observable<Struct> publish(@Body Post post);
}
```

Easy to add API:

[retrofacebook/src/main/java/retrofacebook/Facebook.java](retrofacebook/src/main/java/retrofacebook/Facebook.java):

Easy to add Model:

[retrofacebook/src/main/java/retrofacebook/Post.java](retrofacebook/src/main/java/retrofacebook/Post.java):

```java
@AutoJson
public abstract class Post {
    @Nullable
    @AutoJson.Field
    public abstract String id();

    @Nullable
    @AutoJson.Field(name = "is_hidden")
    public abstract Boolean isHidden();

    // ...
}
```

## Ready API [![javadoc.io](https://javadocio-badges.herokuapp.com/com.infstory/retrofacebook/badge.svg)](http://static.javadoc.io/com.infstory/retrofacebook/1.0.1/retrofacebook/Facebook.html)

* Login/Logout
 - logIn()
 - logOut()
* Publish
 - publish(Feed feed)
 - publish(Story story)
 - publish(Story album)
 - publish(Photo photo)
 - publish(Video video)
 - publish(Score score)
 - publish(Comment comment)
 - publish(Like like)
* Requests/Invite
 - -invite()-
 - -uninvite(Invite invite)-
* Get
 - getAccounts()
 - getAlbum/s()
 - getRequests()
 - getBooks()
 - getComment/s()
 - -getEvents()-
 - getFamily()
 - getFriends()
 - getGames()
 - getGroups()
 - getLikes()
 - getMovies()
 - getMusic()
 - getNotifications()
 - -getObjects()-
 - getPage()
 - getPhotos()
 - getPosts()
 - getProfile()
 - getScores()
 - -getTelevision()-
 - getVideos()

## Installation

via jcenter:

```gradle
repositories {
    jcenter()
    maven {
        url "https://jitpack.io"
    }
    maven {
        url 'https://dl.bintray.com/yongjhih/maven/'
    }
}

dependencies {
    compile 'com.infstory:retrofacebook:1.0.1' // v4
}
```

```gradle
dependencies {
    compile 'com.infstory:retrofacebook-v3:1.0.1' // v3
}
```

via jitpack.io:

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.yongjhih.RetroFacebook:retrofacebook:1.0.1' // v4
}
```

```gradle
dependencies {
    compile 'com.github.yongjhih.RetroFacebook:retrofacebook-v3:1.0.1' // v3
}
```

## Demo App

* v4 appetize: [![v4 appetize](art/appetize_iphone5_inner.jpg)](https://appetize.io/app/rvgdd2zfat913rq9b7ue9zhnhm)
* v4 apk: https://github.com/yongjhih/RetroFacebook/releases/download/1.0.1/retrofacebook-app-v4-debug.apk
* v3 appetize: [![v3 appetize](art/appetize_iphone5_inner.jpg)](https://appetize.io/app/d9hgx8na5r9eq33zd6td1w8w3r)
* v3 apk: https://github.com/yongjhih/RetroFacebook/releases/download/1.0.1/retrofacebook-app-v3-debug.apk

Here is one of test users for all permissions:

* user: retro_rgaqpkf_facebook@tfbnw.net
* password: retrofacebook

Compile:

v4 apk:

```sh
./gradlew assembleV4Debug
adb install -r ./retrofacebook-app/build/outputs/apk/retrofacebook-app-v4-debug.apk
```

v3 apk:

```sh
./gradlew assembleV3Debug
adb install -r ./retrofacebook-app/build/outputs/apk/retrofacebook-app-v3-debug.apk
```

[Sample code: MainActivity.java](retrofacebook-app/src/main/java/retrofacebook/app/MainActivity.java#L121)

## Development

* AutoJson Processor: @AutoJson: setter/getter/builder + json parser
* RetroFacebook Processor: @RetroFacebook: Facebook API -> JavaInterface
* RetroFacebook: A implementation for API definition and life cycle management. (You can replace this).

## Credit

* https://github.com/frankiesardo/auto-parcel
* https://github.com/bluelinelabs/LoganSquare
* https://github.com/sromku/android-simple-facebook

## License

```
Copyright 2015 8tory, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
