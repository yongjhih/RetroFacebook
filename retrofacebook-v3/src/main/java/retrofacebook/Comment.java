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

import auto.json.AutoJson;
import android.support.annotation.Nullable;

import android.os.Bundle;
import java.util.List;
import rx.Observable;

@AutoJson
public abstract class Comment {
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field
    public abstract Attachment attachment();
    @Nullable
    @AutoJson.Field(name = "can_comment")
    public abstract Boolean canComment();
    @Nullable
    @AutoJson.Field(name = "can_remove")
    public abstract Boolean canRemove();
    @Nullable
    @AutoJson.Field(name = "comment_count")
    public abstract Integer commentCount();
    @Nullable
    @AutoJson.Field(name = "created_time")
    public abstract Long createdTime();
    @Nullable
    @AutoJson.Field
    public abstract User from();
    @Nullable
    @AutoJson.Field(name = "like_count")
    public abstract Integer likeCount();
    @Nullable
    @AutoJson.Field
    public abstract String message();
    @Nullable
    @AutoJson.Field(name = "message_tags")
    public abstract List<String> messageTags();
    @Nullable
    @AutoJson.Field
    public abstract Comment parent();
    @Nullable
    @AutoJson.Field(name = "user_likes")
    public abstract Boolean userLikes();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder attachment(Attachment x);
        public abstract Builder canComment(Boolean x);
        public abstract Builder canRemove(Boolean x);
        public abstract Builder commentCount(Integer x);
        public abstract Builder createdTime(Long x);
        public abstract Builder from(User x);
        public abstract Builder likeCount(Integer x);
        public abstract Builder message(String x);
        public abstract Builder messageTags(List<String> x);
        public abstract Builder parent(Comment x);
        public abstract Builder userLikes(Boolean x);

        public abstract Comment build();
    }

    public abstract Bundle toBundle();

    public static Builder builder() {
        return new AutoJson_Comment.Builder();
    }

    public Observable<Struct> like() {
        return Facebook.get().like(this);
    }

    public Observable<Struct> unlike() {
        return Facebook.get().unlike(this);
    }
}
