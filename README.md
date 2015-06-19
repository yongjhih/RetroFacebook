# RetroFacebook

![RetroFacebook.png](art/retrofacebook.png)

OGM, Object Graph Mapping, Graph to POJO. Support V3 and V4.

Inspired by retrofit.


![photos.png](art/screenshot-photos.png)
![photos.png](art/screenshot-friends.png)
![posts.png](art/screenshot-posts.png)

## Usage

My posts:

```java
Facebook facebook = Facebook.create(activity);

Observable<Post> myPosts = facebook.getPosts();
myPosts.forEach(post -> System.out.println(post.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/me/feed")
    Observable<Post> getPosts();
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

My UploadedPhotos:

```java
Observable<Photo> myUploadedPhotos = facebook.getPhotosTypeOf("uploaded");
myPhotos.forEach(photo -> System.out.println(photo.id()));
```

```java
@RetroFacebook
abstract class Facebook {
    @GET("/me/photos")
    abstract Observable<Post> getPhotosTypeOf(@Query("type") String type); // getPhotosTypeOf("uploaded") -> /me/photos?type=uploaded
}
```

Publish:

```java
facebook.post(Post.builder()
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
    abstract Observable<Struct> post(@Body Post post);
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

## Installation

via jitpack.io:

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.yongjhih:RetroFacebook:1.0.1'
}
```

## TODO

Missing Models:

* Account
* AppRequest
* Checkin
* Event
* FamilyUser
* Feed
* Group
* Notification
* Page
* Parking
* Publishable
* RestaurantService
* RestaurantSpecialties
* Score
* Story
* Video

## Credit

* https://github.com/frankiesardo/auto-parcel
* https://github.com/bluelinelabs/LoganSquare

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
