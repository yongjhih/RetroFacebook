# RetroFacebook

![RetroFacebook.png](art/retrofacebook.png)

Inspired by retrofit.

OGM, Object Graph Mapping, Graph to POJO.

![photos.png](art/screenshot-photos.png)
![posts.png](art/screenshot-posts.png)

## Usage

```java
Facebook facebook = Facebook.create();
Observable<Post> posts = facebook.getPosts("4");
posts.subscribe(post -> /* ... */);
Observable<Post> myPosts = facebook.getPosts();
myPosts.subscribe(post -> /* ... */);
Observable<Photo> myPhotos = facebook.getPhotos();
myPhotos.subscribe(photo -> /* ... */);
```

or

```java
facebook.getPosts("4", new FacebookCallback<Post>() {
    @Override public void onCompleted(List<Post> posts, FacebookException error) {
        // ...
    }
});
facebook.getPosts(new FacebookCallback<Post>() {
    @Override public void onCompleted(List<Post> posts, FacebookException error) {
        // ...
    }
});
facebook.getPhotos(new FacebookCallback<Photo>() {
    @Override public void onCompleted(List<Photo> photos, FacebookException error) {
        // ...
    }
});
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
    compile 'com.github.yongjhih:RetroFacebook:1.0.0'
}
```

## Development

[retrofacebook/src/main/java/retrofacebook/Facebook.java](retrofacebook/src/main/java/retrofacebook/Facebook.java):

```java
@RetroFacebook
abstract class Facebook {
    @RetroFacebook.GET("/{postId}")
    Observable<Post> getPost(@RetroFacebook.Path String postId);

    public static Facebook create() {
        return new RetroFacebook_Facebook();
    }
}
// /{userId}/posts
// /{user-id}/links
// /{user-id}/statuses
// /{user-id}/tagged
```

[retrofacebook/src/main/java/retrofacebook/Post.java](retrofacebook/src/main/java/retrofacebook/Post.java):
```java
@AutoJson
public abstract class Post {
    public abstract String id();
    @AutoJson.Field(name = "is_hidden")
    public abstract Boolean isHidden();
}
```

Generated:

```java
final class RetroFacebook_Facebook extends Facebook {

  @Override
  public Observable<Post> getPost(java.lang.String postId) {
        return Observable.create(new OnSubscribeGraphResponse(
                GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + postId + "", null
                )
            )
        ).map(new Func1<GraphResponse, Post>() {
            @Override public Post call(GraphResponse response) {
                try {
                return LoganSquare.parse(response.getJSONObject().toString(), AutoJson_Post.class);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
  }

//...

}
```

```java
@JsonObject
final class AutoJson_Post extends Post {

  @android.support.annotation.Nullable
  String id;

  @android.support.annotation.Nullable
  @com.bluelinelabs.logansquare.annotation.JsonField(name = {"is_hidden"})
  Boolean isHidden;

  public AutoJson_Post() {
    super();
  }

  private AutoJson_Post(
      String id,
      Boolean isHidden) {
    this.id = id;
    this.isHidden = isHidden;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public Boolean isHidden() {
    return isHidden;
  }

  @Override
  public String toString() {
    return "Post{"
        + "id=" + id + ", "
        + "isHidden=" + isHidden
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Post) {
      Post that = (Post) o;
      return ((this.id == null) ? (that.id() == null) : this.id.equals(that.id()))
           && ((this.isHidden == null) ? (that.isHidden() == null) : this.isHidden.equals(that.isHidden()));
    }
    return false;
  }

// ...
```

## Credit

* frankiesardo/auto-parcel
* bluelinelabs/LoganSquare

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
