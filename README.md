# RetroFacebook

Include AutoFacebook, Inspired by retrofit.

For Development:

```java
interface FacebookService {
    @GET("/{postId}")
    Observable<Post> getPost(@Path String postId);
    
    @GET("/{userId}/posts")
    Observable<Post> getPosts(@Path String userId);
    
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

    public static Post create() {
        return new AutoFacebook_Post();
    }
}
```
