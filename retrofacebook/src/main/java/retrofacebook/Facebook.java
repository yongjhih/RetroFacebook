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
import android.os.Bundle;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

/**
 * @see <a href="https://developers.facebook.com/docs/graph-api/using-graph-api/v2.3">Graph API - Facebook Developers</a>
 */
@RetroFacebook
public abstract class Facebook {
    private static Facebook self;

    public static Facebook get() {
        return self;
    }

    @RetroFacebook.GET(value = "/{post-id}", permissions = "user_posts")
    public abstract Observable<Post> getPost(@RetroFacebook.Path("post-id") String postId);

    // TODO @RetroFacebook.GET("/{userId}/photos?type=uploaded")
    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/user/photos/">Graph API User Photo Edge - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/photos", permissions = "user_photos")
    public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/photos")
    //public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId, @RetroFacebook.Query String type);

    //@RetroFacebook.GET("/{user-id}/photos")
    //public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId, @RetroFacebook.QueryMap Map<String, String> queries);

    //@RetroFacebook.GET("/{user-id}/photos")
    //public abstract Observable<Photo> getPhotos(@RetroFacebook.Path("user-id") String userId, @RetroFacebook.QueryBundle Bundle queries);

    @RetroFacebook.GET(value = "/{user-id}/photos?type=uploaded", permissions = "user_photos")
    public abstract Observable<Photo> getUploadedPhotos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Photo> getUploadedPhotos() {
        return getUploadedPhotos("me");
    }

    @RetroFacebook.GET("/search?type=topic&fields=id,name,page")
    public abstract Observable<Page> searchTopic(@RetroFacebook.Query("q") String query);

    public Observable<Photo> getPhotos() {
        return getPhotos("me");
    }

    @RetroFacebook.GET(value = "/{object-id}", permissions = "user_photos")
    public abstract Observable<Photo> getPhoto(@RetroFacebook.Path("object-id") String objectId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/user/feed">{user-id}/feed - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/feed", permissions = "user_posts")
    public abstract Observable<Post> getPosts(@RetroFacebook.Path("user-id") String userId);

    @RetroFacebook.GET(value = "/{user-id}/feed", permissions = "user_posts")
    public abstract void getPosts(@RetroFacebook.Path("user-id") String userId, final Callback<Post> callback);

    public static interface Callback<T> extends RetroFacebook.Callback<T> {
    }

    public Observable<Post> getPosts() {
        return getPosts("me");
    }

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Post> getPosts(PostType postType);

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Post> getPosts(@RetroFacebook.Path("user-id") String userId, PostType postType);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/user/friends">Graph API /user/friends - Facebook Developers</a>
     */
    @RetroFacebook.GET(value = "/{user-id}/friends", permissions = "user_friends")
    public abstract Observable<User> getFriends(@RetroFacebook.Path("user-id") String userId);

    public Observable<User> getFriends() {
        return getFriends("me");
    }

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Friend> getFriends(Properties properties);

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

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{page_id}/photos", permissions = "publish_actions")
    public abstract Observable<Struct> publishPage(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("page_id") String pageId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{user_id}/photos", permissions = "publish_actions")
    public abstract Observable<Struct> publishUser(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("user_id") String userId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{album_id}/photos", permissions = "publish_actions")
    public abstract Observable<Struct> publishAlbum(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("album_id") String albumId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{event_id}/photos", permissions = "publish_actions")
    public abstract Observable<Struct> publishEvent(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("event_id") String eventId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{group_id}/photos", permissions = "publish_actions")
    public abstract Observable<Struct> publishGroup(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("group_id") String groupId);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/post">Graph API Post Node - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{user-id}/feed", permissions = "publish_actions")
    public abstract Observable<Struct> publish(@RetroFacebook.Body Post post, @RetroFacebook.Path("user-id") String userId);

    public Observable<Struct> publish(Post post) {
        return publish(post, "me");
    }

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments">Comments - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{object-id}/comments", permissions = "publish_actions")
    public abstract Observable<Struct> comment(@RetroFacebook.Body Comment comment, @RetroFacebook.Path("object-id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments">Comments - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{comment-id}", permissions = "publish_actions")
    public abstract Observable<Struct> updateComment(@RetroFacebook.Body Comment comment, @RetroFacebook.Path("comment-id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments">Comments - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{comment-id}", permissions = "publish_pages")
    public abstract Observable<Struct> updatePageComment(@RetroFacebook.Body Comment comment, @RetroFacebook.Path("comment-id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments">Comments - Facebook Developers</a>
     */
    @RetroFacebook.POST(value = "/{object-id}/comments", permissions = "publish_pages")
    public abstract Observable<Struct> commentPage(@RetroFacebook.Body Comment comment, @RetroFacebook.Path("object-id") String id);

    public Observable<Struct> commentPage(String message, String id) {
        return comment(Comment.builder().message(message).build(), id);
    }

    public Observable<Struct> comment(String message, Post post) {
        return comment(Comment.builder().message(message).build(), post);
    }

    public Observable<Struct> comment(String message, Photo photo) {
        return comment(Comment.builder().message(message).build(), photo);
    }

    public Observable<Struct> comment(Comment comment, Post post) {
        return comment(comment, post.id());
    }

    public Observable<Struct> comment(Comment comment, Photo photo) {
        return comment(comment, photo.id());
    }


    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/post">Graph API Post Node - Facebook Developers</a>
     */
    @RetroFacebook.DELETE(value = "{id}", permissions = "publish_actions")
    public abstract Observable<Struct> deletePost(@RetroFacebook.Path("id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/post">Graph API Post Node - Facebook Developers</a>
     */
    @RetroFacebook.DELETE(value = "{id}", permissions = "publish_pages")
    public abstract Observable<Struct> deletePage(@RetroFacebook.Path("id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.DELETE(value = "{id}", permissions = "publish_actions")
    public abstract Observable<Struct> deletePhoto(@RetroFacebook.Path("id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/photo">Graph API Photo Node - Facebook Developers</a>
     */
    @RetroFacebook.DELETE(value = "{id}", permissions = {"publish_pages", "manage_pages"})
    public abstract Observable<Struct> deletePagePhoto(@RetroFacebook.Path("id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments">Comments - Facebook Developers</a>
     */
    @RetroFacebook.DELETE(value = "/{comment-id}")
    public abstract Observable<Struct> deleteComment(@RetroFacebook.Path("comment-id") String id);

    /**
     * @see <a href="https://developers.facebook.com/docs/graph-api/reference/user/accounts/">Accounts - Facebook Developers</a>
     */
    @RetroFacebook.GET("/{user-id}/accounts")
    public abstract Observable<Page> getAccounts(@RetroFacebook.Path("user-id") String userId);

    public Observable<Page> getAccounts() {
        return getAccounts("me");
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/album/
     */
    @RetroFacebook.GET(value = "/{album-id}", permissions = "user_photos")
    public abstract Observable<Album> getAlbum(@RetroFacebook.Path("album-id") String albumId);

    @RetroFacebook.GET(value = "/{user-id}/albums", permissions = "user_photos")
    public abstract Observable<Album> getAlbums(@RetroFacebook.Path("user-id") String userId);

    /**
     * @param type You use this to get a pre-specified size of picture.
     * enum{thumbnail,small,album}
     *
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/album/picture
     */
    // FACEBOOK_NON_JSON_RESULT
    //@RetroFacebook.GET(value = "/{album-id}/picture", permissions = "user_photos")
    //public abstract Observable<EdgePicture> getAlbumPicture(@RetroFacebook.Path("album-id") String albumId);

    //@RetroFacebook.GET(value = "/{album-id}/picture?type=thumbnail", permissions = "user_photos")
    //public abstract Observable<EdgePicture> getAlbumThumbnail(@RetroFacebook.Path("album-id") String albumId);

    //@RetroFacebook.GET(value = "/{album-id}/picture?type=small", permissions = "user_photos")
    //public abstract Observable<EdgePicture> getAlbumSmallPicture(@RetroFacebook.Path("album-id") String albumId);

    //@RetroFacebook.GET(value = "/{album-id}/picture?type=album", permissions = "user_photos")
    //public abstract Observable<EdgePicture> getAlbumAlbumPicture(@RetroFacebook.Path("album-id") String albumId);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/albums/
     */
    public Observable<Album> getAlbums() {
        return getAlbums("me");
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/apprequests/
     */
    @RetroFacebook.GET("/{user-id}/apprequests")
    public abstract Observable<AppRequest> getAppRequests(@RetroFacebook.Path("user-id") String userId);

    public Observable<AppRequest> getAppRequests() {
        return getAppRequests("me");
    }

    @RetroFacebook.GET("/{object-id}/attachments")
    public abstract Observable<Attachment> getAttachments(@RetroFacebook.Path("object-id") String objectId);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/books/
     */
    //public abstract Observable<Book> getBooks(Page.Properties properties);
    @RetroFacebook.GET("/{user-id}/books")
    public abstract Observable<Page> getBooks(@RetroFacebook.Path("user-id") String userId);

    public Observable<Page> getBooks() {
        return getBooks("me");
    }

    //public abstract Observable<Book> getBooks(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/comment
     */
    @RetroFacebook.GET("/{comment-id}")
    public abstract Observable<Comment> getComment(@RetroFacebook.Path("comment-id") String commentId);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/comments/
     */
    @RetroFacebook.GET("/{object-id}/comments")
    public abstract Observable<Comment> getComments(@RetroFacebook.Path("object-id") String objectId);

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Event> getEvents(EventDecision eventDecision);
    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Event> getEvents(@RetroFacebook.Path("user-id") String userId, EventDecision eventDecision);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/user/family/
     */
    @RetroFacebook.GET(value = "/{user-id}/family", permissions = "user_relationships")
    public abstract Observable<User> getFamily(@RetroFacebook.Path("user-id") String userId);

    public Observable<User> getFamily() {
        return getFamily("me");
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/games
     */
    @RetroFacebook.GET("/{user-id}/games")
    public abstract Observable<Page> getGames(@RetroFacebook.Path("user-id") String userId);

    public Observable<Page> getGames() {
        return getGames("me");
    }

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Game> getGames(Page.Properties properties);
    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Game> getGames(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/group
     * or user_managed_groups
     */
    @RetroFacebook.GET(value = "/{user-id}/groups", permissions = "user_groups")
    public abstract Observable<Group> getGroups(@RetroFacebook.Path("user-id") String userId);

    public Observable<Group> getGroups() {
        return getGroups("me");
    }

    @RetroFacebook.GET("/{user-id}/invitable_friends")
    public abstract Observable<User> getInvitableFriends(@RetroFacebook.Path("user-id") String userId);

    public Observable<User> getInvitableFriends() {
        return getInvitableFriends("me");
    }

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<InvitableFriend> getInvitableFriends(Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/likes/
     */
    @RetroFacebook.GET(value = "/{user-id}/likes", permissions = "user_likes")
    public abstract Observable<Page> getLikedPages(@RetroFacebook.Path("user-id") String userId);

    public Observable<Page> getLikedPages() {
        return getLikedPages("me");
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/likes
     */
    @RetroFacebook.GET("/{object-id}/likes")
    public abstract Observable<User> getLikedUsers(@RetroFacebook.Path("object-id") String objectId); // getLikes()

    public Observable<User> getLikedUsers(Photo photo) {
        return getLikedUsers(photo.id());
    }

    public Observable<User> getLikedUsers(Post post) {
        return getLikedUsers(post.id());
    }

    @RetroFacebook.GET("/me")
    public abstract Observable<User> me();

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Page> getMovies(Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/user/movies
     */
    @RetroFacebook.GET("/{user-id}/movies")
    public abstract Observable<Page> getMovies(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Page> getMovies(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    public Observable<Page> getMovies() {
        return getMovies("me");
    }

    public Observable<Page> getMusic() {
        return getMusic("me");
    }

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Page> getMusic(Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/user/music
     */
    @RetroFacebook.GET("/{user-id}/music")
    public abstract Observable<Page> getMusic(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Page> getMusic(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/user/notifications/
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/notification/
     */
    @RetroFacebook.GET(value = "/{user-id}/notifications", permissions = "manage_notifications")
    public abstract Observable<Notification> getNotifications(@RetroFacebook.Path("user-id") String userId);

    public Observable<Notification> getNotifications() {
        return getNotifications("me");
    }

    @RetroFacebook.GET("/{page-id}")
    public abstract Observable<Page> getPage(@RetroFacebook.Path("page-id") String pageId);

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Page> getPage(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    //public abstract Observable<Page> getPages();

    public Observable<Profile> getProfile() {
        return getProfile("me");
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/profile
     */
    @RetroFacebook.GET("/{profile-id}")
    public abstract Observable<Profile> getProfile(@RetroFacebook.Path("profile-id") String profileId);

    @RetroFacebook.DELETE("/{page_id}/blocked")
    public abstract Observable<Struct> deleteTestUser(@RetroFacebook.Body Page page, @RetroFacebook.Path("page_id") String pageId);

    @RetroFacebook.DELETE("/{user_id}")
    public abstract Observable<Struct> deleteUser(@RetroFacebook.Body User user, @RetroFacebook.Path("user_id") String userId);

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Profile> getProfile(Profile.Properties properties);
    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<Profile> getProfile(String profileId, Profile.Properties properties);

    @RetroFacebook.GET(value = "/{user-id}/scores", permissions = "user_games_activity")
    public abstract Observable<Score> getScores(@RetroFacebook.Path("user-id") String userId);

    public Observable<Score> getScores() {
        return getScores("me");
    }

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<StoryObject> getStoryObjects(String objectName);

    @RetroFacebook.GET("/{user-id}/taggable_friends")
    public abstract Observable<User> getTaggableFriends(@RetroFacebook.Path("user-id") String userId);

    //@RetroFacebook.GET("/{user-id}/") public abstract Observable<TaggableFriend> getTaggableFriends(Properties properties);

    public Observable<User> getTaggableFriends() {
        return getTaggableFriends("me");
    }

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Television> getTelevision();
    ////@RetroFacebook.GET("/{user-id}/") public abstract Observable<Television> getTelevision(Page.Properties properties);

    //@RetroFacebook.GET("/{user-id}/")
    //public abstract Observable<Television> getTelevision(@RetroFacebook.Path("user-id") String userId);
    ////@RetroFacebook.GET("/{user-id}/") public abstract Observable<Television> getTelevision(@RetroFacebook.Path("user-id") String userId, Page.Properties properties);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/user/videos
     * @see https://developers.facebook.com/docs/graph-api/reference/video
     */
    @RetroFacebook.GET(value = "/{user-id}/videos", permissions = "user_videos")
    public abstract Observable<Video> getVideos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Video> getVideos() {
        return getVideos("me");
    }

    @RetroFacebook.GET(value = "/{user-id}/videos?type=uploaded", permissions = "user_videos")
    public abstract Observable<Video> getUploadedVideos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Video> getUploadedVideos() {
        return getUploadedVideos("me");
    }

    @RetroFacebook.GET(value = "/{user-id}/videos?type=tagged", permissions = "user_videos")
    public abstract Observable<Video> getTaggedVideos(@RetroFacebook.Path("user-id") String userId);

    public Observable<Video> getTaggedVideos() {
        return getTaggedVideos("me");
    }

    @RetroFacebook.GET(value = "/{video-id}", permissions = "user_videos")
    public abstract Observable<Video> getVideo(@RetroFacebook.Path("video-id") String videoId);

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/likes
     */
    @RetroFacebook.POST(value = "/{object-id}/likes", permissions = "publish_actions"/* ? */) // as User
    public abstract Observable<Struct> like(@RetroFacebook.Path("object-id") String id);

    public Observable<Struct> like(Photo photo) {
        return like(photo.id());
    }

    public Observable<Struct> like(Post post) {
        return like(post.id());
    }

    public Observable<Struct> like(Comment comment) {
        return like(comment.id());
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/likes
     */
    @RetroFacebook.DELETE(value = "/{object-id}/likes", permissions = "publish_actions"/* ? */) // as User
    public abstract Observable<Struct> unlike(@RetroFacebook.Path("object-id") String id);

    public Observable<Struct> unlike(Photo photo) {
        return unlike(photo.id());
    }

    public Observable<Struct> unlike(Post post) {
        return unlike(post.id());
    }

    public Observable<Struct> unlike(Comment comment) {
        return unlike(comment.id());
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/likes
     */
    @RetroFacebook.DELETE(value = "/{object-id}/likes", permissions = "publish_pages"/* ? */)
    public abstract Observable<Struct> unlikePage(@RetroFacebook.Path("object-id") String id);

    public Observable<Struct> unlike(Page page) {
        return unlikePage(page.id());
    }

    /**
     * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/object/likes
     */
    @RetroFacebook.POST(value = "/{object-id}/likes", permissions = "publish_pages"/* ? */)
    public abstract Observable<Struct> likeAsPage(@RetroFacebook.Body Like like, @RetroFacebook.Path("object-id") String id);

    public Observable<Struct> score(Score score) {
        return score(score, "me");
    }

    @RetroFacebook.POST(value = "/{object-id}/scores", permissions = "publish_actions"/* ? */)
    public abstract Observable<Struct> score(@RetroFacebook.Body Score score, @RetroFacebook.Path("object-id") String id);

    //public abstract Observable<Struct> story(Story story);

    public Observable<Struct> album(Album album) {
        return album(album, "me");
    }

    @RetroFacebook.POST(value = "/{object-id}/albums", permissions = "publish_actions"/* ? */)
    public abstract Observable<Struct> album(@RetroFacebook.Body Album album, @RetroFacebook.Path("object-id") String id);

    @RetroFacebook.POST(value = "/{object-id}/photos", permissions = "publish_actions"/* ? */)
    public abstract Observable<Struct> photo(@RetroFacebook.Body Photo photo, @RetroFacebook.Path("object-id") String objectId);

    public Observable<Struct> photo(Photo photo) {
        return photo(photo, "me");
    }

    //public abstract Observable<Struct> photos(List<Photo> photos);

    @RetroFacebook.POST(value = "/{object-id}/videos", permissions = "publish_actions"/* ? */)
    public abstract Observable<Struct> video(@RetroFacebook.Body Video video, @RetroFacebook.Path("object-id") String id);

    public Observable<Struct> video(Video video) {
        return video(video, "me");
    }

    //public abstract publish(Feed feed, boolean withDialog);
    //public abstract publish(Story story, boolean withDialog);
    //public abstract publish(Photo photo, boolean withDialog);
    //public abstract publish(String entityId, Publishable publishable);

    //public abstract invite(String message, final OnInviteListener onInviteListener, String data);
    //public abstract invite(String to, String message, final OnInviteListener onInviteListener, String data);
    //public abstract invite(String[] suggestedFriends, String message, final OnInviteListener onInviteListener, String data);

    //public abstract getConfiguration();
    //public abstract requestNewPermissions(Permission[] permissions, OnNewPermissionsListener onNewPermissionsListener);
    //public abstract isAllPermissionsGranted();
    //public abstract isLogin();

    //public abstract getAccessToken();

    //public abstract getGrantedPermission();


    // LifeCycle management

    public static Facebook create(Activity activity) {
        self = new RetroFacebook_Facebook(activity).initialize(activity);
        return self;
    }

    CallbackManager callbackManager;
    Activity activity;

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public Facebook initialize(Activity activity) {
        this.activity = activity;

        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        return this;
    }

    public Observable<LoginResult> logIn() {
        return logInWithReadPermissions();
    }

    public Observable<LoginResult> logInWithReadPermissions() {
        // TODO logInWithReadPermissions(getDefaultPermissions().filterReadPermissions());
        //return logInWithReadPermissions(Arrays.asList("public_profile", "user_friends", "user_photos", "user_posts"));
        return logInWithReadPermissions(Arrays.asList("public_profile"));
    }

    public Observable<LoginResult> logInWithPublishPermissions() {
        // TODO logInWithPublishPermissions(getDefaultPermissions().filterPublishPermissions())
        return logInWithPublishPermissions(Arrays.asList("publish_actions"));
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

    public Observable<LoginResult> logInWithPublishPermissions(final Collection<String> permissions) {
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
                LoginManager.getInstance().logInWithPublishPermissions(activity, permissions);
            }
        });
    }

    public static void logOut() {
        LoginManager.getInstance().logOut();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
