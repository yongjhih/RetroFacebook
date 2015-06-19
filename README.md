# RetroFacebook

![RetroFacebook.png](art/retrofacebook.png)

OGM, Object Graph Mapping, Graph to POJO. Support V3 and V4.

Inspired by retrofit.


![photos.png](art/screenshot-photos.png)
![photos.png](art/screenshot-friends.png)
![posts.png](art/screenshot-posts.png)

## Usage

```java
Facebook facebook = Facebook.create(activity);

Observable<Post> posts = facebook.getPosts("4");
posts.subscribe(post -> System.out.println(post.id()));

Observable<Post> myPosts = facebook.getPosts();
myPosts.subscribe(post -> System.out.println(post.id()));

Observable<Photo> myPhotos = facebook.getPhotos();
myPhotos.subscribe(photo -> System.out.println(photo.id()));
```

```java
facebook.post(Post.builder()
    .message("yo")
    .name("RetroFacebook")
    .caption("RetroFacebook")
    .description("Retrofit Facebook Android SDK")
    .picture("https://raw.githubusercontent.com/yongjhih/RetroFacebook/master/art/retrofacebook.png")
    .link("https://github.com/yongjhih/RetroFacebook")
    .build());
```

Easy to add API:

[retrofacebook/src/main/java/retrofacebook/Facebook.java](retrofacebook/src/main/java/retrofacebook/Facebook.java):

```java
@RetroFacebook
abstract class Facebook {
    @RetroFacebook.GET("/{user-id}")
    Observable<Post> getPosts(@RetroFacebook.Path("user-id") String userId);

    @RetroFacebook.GET("/{user-id}")
    Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId);

    // ...
}
```

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
