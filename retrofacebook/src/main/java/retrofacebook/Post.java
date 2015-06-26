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
public abstract class Post {
    @Nullable
    @AutoJson.Field
    public abstract String url();

    @Nullable
    @AutoJson.Field
    public abstract List<Action> actions();
    @Nullable
    @AutoJson.Field
    public abstract Application application();
    @Nullable
    @AutoJson.Field
    public abstract Attachment attachment();
    @Nullable
    @AutoJson.Field
    public abstract String caption();
    @Nullable
    @AutoJson.Field
    public abstract Comments comments();
    @Nullable
    @AutoJson.Field
    public abstract Users likes();
    @Nullable
    @AutoJson.Field(name = "created_time")
    public abstract Long createdTime();
    @Nullable
    @AutoJson.Field
    public abstract String description();
    @Nullable
    @AutoJson.Field
    public abstract User from();
    @Nullable
    @AutoJson.Field
    public abstract String icon();
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field(name = "is_hidden")
    public abstract Boolean isHidden();
    @Nullable
    @AutoJson.Field
    public abstract String link();
    @Nullable
    @AutoJson.Field
    public abstract String message();
    @Nullable
    @AutoJson.Field(name = "message_tags")
    public abstract List<InlineTag> messageTags();
    @Nullable
    @AutoJson.Field
    public abstract String name();
    @Nullable
    @AutoJson.Field(name = "object_id")
    public abstract String objectId();
    @Nullable
    @AutoJson.Field
    public abstract String picture();
    @Nullable
    @AutoJson.Field
    public abstract Place place();
    @Nullable
    @AutoJson.Field
    public abstract Privacy privacy();
    //@Nullable
    //@AutoJson.Field
    //public abstract List<Property> properties();
    @Nullable
    @AutoJson.Field
    public abstract Integer shares();
    @Nullable
    @AutoJson.Field
    public abstract String source();
    @Nullable
    @AutoJson.Field(name = "status_type")
    public abstract String statusType();
    @Nullable
    @AutoJson.Field
    public abstract String story();
    @Nullable
    @AutoJson.Field(name = "story_tags")
    public abstract List<InlineTag> storyTags();
    @Nullable
    @AutoJson.Field
    public abstract List<User> to();
    @Nullable
    @AutoJson.Field
    public abstract String type();
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Long updatedTime();
    @Nullable
    @AutoJson.Field(name = "with_tags")
    public abstract List<User> withTags();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder url(String x);

        public abstract Builder actions(List<Action> x);
        public abstract Builder application(Application x);
        public abstract Builder attachment(Attachment x);
        public abstract Builder caption(String x);
        public abstract Builder comments(Comments x);
        public abstract Builder likes(Users x);
        public abstract Builder createdTime(Long x);
        public abstract Builder description(String x);
        public abstract Builder from(User x);
        public abstract Builder icon(String x);
        public abstract Builder id(String x);
        public abstract Builder isHidden(Boolean x);
        public abstract Builder link(String x);
        public abstract Builder message(String x);
        public abstract Builder messageTags(List<InlineTag> x);
        public abstract Builder name(String x);
        public abstract Builder objectId(String x);
        public abstract Builder picture(String x);
        public abstract Builder place(Place x);
        public abstract Builder privacy(Privacy x);
        public abstract Builder shares(Integer x);
        public abstract Builder source(String x);
        public abstract Builder statusType(String x);
        public abstract Builder story(String x);
        public abstract Builder storyTags(List<InlineTag> x);
        public abstract Builder to(List<User> x);
        public abstract Builder type(String x);
        public abstract Builder updatedTime(Long x);
        public abstract Builder withTags(List<User> x);

        public abstract Post build();
    }

    public static Builder builder() {
        return new AutoJson_Post.Builder();
    }

    public Observable<Photo> photo() {
        if ("photo".equals(type())) return Facebook.self.getPhoto(objectId());
        return Observable.empty();
    }

    //public abstract Builder toBuilder();
    public abstract Bundle toBundle();
}
