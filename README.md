# RetroFacebook

Include AutoFacebook, Inspired by retrofit.

For Development:

```java
interface FacebookService {
    @GET("/{postId}")
    Post getPost(@Path String postId);
    
    @GET("/{userId}/posts")
    Post getPosts(@Path String userId);
    
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
}
```
