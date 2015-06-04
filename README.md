# RetroFacebook

![RetroFacebook.png](art/retrofacebook.png)

Include AutoFacebook. Inspired by retrofit.

OGM, Object GraphObject Mapping, GraphObject to POJO.

## Usage

```
Facebook facebook = RetroFacebook.create();
Observable<Post> posts = facebook.getPosts("4");
```

## Development

```java
@RetroFacebook
abstract class Facebook {
    @GET("/{postId}")
    Observable<Post> getPost(@Path String postId);

    @GET("/{userId}/posts")
    Observable<Post> getPosts(@Path String userId);

    public static Facebook create() {
        return new RetroFacebook_Facebook();
    }
}
// /{user-id}/links
// /{user-id}/statuses
// /{user-id}/tagged
```

```java
@AutoFacebook
abstract class Post {
    String id();
    String caption();
    String description();
    Profile from();

    public static Post create(GraphObject graphObject) {
        return new AutoFacebook_Post(graphObject);
    }
}
```

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
