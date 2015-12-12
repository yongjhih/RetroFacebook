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
import java.util.Date;
import rx.Observable;
import android.graphics.Bitmap;

@AutoJson
public abstract class Photo {
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field
    public abstract Album album();
    @Nullable
    @AutoJson.Field(name = "backdated_time")
    public abstract Date backDateTime();
    @Nullable
    @AutoJson.Field(name = "backdated_time_granularity", typeConverter = AccuracyConverter.class)
    public abstract Accuracy backdatedTimeGranularity();
    @Nullable
    @AutoJson.Field(name = "created_time")
    public abstract Date createdTime();
    @Nullable
    @AutoJson.Field
    public abstract User from();
    @Nullable
    @AutoJson.Field
    public abstract Integer height();
    @Nullable
    @AutoJson.Field
    public abstract String icon();
    @Nullable
    @AutoJson.Field
    public abstract List<Image> images();
    @Nullable
    @AutoJson.Field
    public abstract String link();
    @Nullable
    @AutoJson.Field
    public abstract String name();
    @Nullable
    @AutoJson.Field
    public abstract String message();
    @Nullable
    @AutoJson.Field(name = "page_story_id")
    public abstract String pageStoryId();
    @Nullable
    @AutoJson.Field
    public abstract String picture();
    @Nullable
    @AutoJson.Field(name = "picture")
    public abstract Bitmap pictureBitmap();
    @Nullable
    @AutoJson.Field
    public abstract Place place();
    /**
     * deprecated, instead of images
     */
    //@Nullable
    //@AutoJson.Field
    //public abstract String source();
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Date updatedTime();
    @Nullable
    @AutoJson.Field
    public abstract Integer width();
    //@Nullable
    //@AutoJson.Field
    //public abstract Event event(); // v2.3 // TODO
    //@Nullable
    //@AutoJson.Field(name = "name_tags")
    //public abstract Map<String, List<TextRange>> nameTags(); // TODO
    /**
     * deprecated
     */
    //@Nullable
    //@AutoJson.Field
    //public abstract Integer position();

    /**
     * People who like this
     */
    @Nullable
    @AutoJson.Field
    public abstract Users likes();

    /**
     * Comments on an object
     */
    @Nullable
    @AutoJson.Field
    public abstract Comments comments();

    // POST

    /**
     * numeric string or integer
     */
    //public abstract String id();
    /**
     * string
     */
    @Nullable
    @AutoJson.Field
    public abstract String caption();
    /**
     * URL
     */
    @Nullable
    @AutoJson.Field
    public abstract String url();
    /**
     * numeric string or integer
     */
    @Nullable
    @AutoJson.Field(name = "vault_image_id")
    public abstract String vaultImageId();
    /**
     * list&lt;Object&gt;
     */
    @Nullable
    @AutoJson.Field
    public abstract List<Tag> tags();
    /**
     * place tag
     */
    @Nullable
    @AutoJson.Field(name = "place")
    public abstract String placeId();
    /**
     * target
     */
    @Nullable
    @AutoJson.Field
    public abstract Target targeting();
    /**
     * feed target
     */
    @Nullable
    @AutoJson.Field(name = "feed_targeting")
    public abstract FeedTarget feedTargeting();
    /**
     * boolean
     */
    @Nullable
    @AutoJson.Field(name = "no_story")
    public abstract boolean noStory();
    /**
     * boolean
     */
    @Nullable
    @AutoJson.Field
    public abstract boolean published();
    /**
     * unsigned int32
     */
    @Nullable
    @AutoJson.Field(name = "offline_id")
    public abstract int offlineId();
    /**
     * unsigned int32
     */
    @Nullable
    @AutoJson.Field
    public abstract int attempt();
    /**
     * datetime
     */
    @Nullable
    @AutoJson.Field(name = "backdated_time")
    public abstract Date backdatedTime();
    /**
     * enum{year, month, day, hour, min, none}
     */
    //public abstract Accuracy backdatedTimeGranularity();
    /**
     * unsigned int32
     */
    @Nullable
    @AutoJson.Field(name = "filter_type")
    public abstract int filterType();
    /**
     * boolean
     */
    @Nullable
    @AutoJson.Field
    public abstract boolean temporary();
    /**
     * unsigned int32
     */
    @Nullable
    @AutoJson.Field(name = "scheduled_publish_time")
    public abstract long scheduledPublishTime();
    /**
     * enum {SCHEDULED, DRAFT, ADSPOST, INLINECREATED, PREVIEW, VALIDATION}
     */
    @Nullable
    @AutoJson.Field(name = "unpublished_content_type")
    public abstract String unpublishedContentType(); // TODO enum
    /**
     * string
     */
    @Nullable
    @AutoJson.Field(name = "nectar_module")
    public abstract String nectarModule();
    /**
     * id
     */
    @Nullable
    @AutoJson.Field(name = "og_action_type_id")
    public abstract String ogActionTypeId();
    /**
     * OG object ID or URL string
     */
    @Nullable
    @AutoJson.Field(name = "og_action_type_id")
    public abstract String ogObjectId();
    /**
     * string
     */
    @Nullable
    @AutoJson.Field(name = "og_phrase")
    public abstract String ogPhrase();
    /**
     * id
     */
    @Nullable
    @AutoJson.Field(name = "og_icon_id")
    public abstract String ogIconId();
    /**
     * string
     */
    @Nullable
    @AutoJson.Field(name = "og_suggestion_mechanism")
    public abstract String ogSuggestionMechanism();
    /**
     * string
     */
    @Nullable
    @AutoJson.Field(name = "composer_session_id")
    public abstract String composerSessionId();

    @Nullable
    @AutoJson.Field
    public abstract Privacy privacy();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder album(Album x);
        public abstract Builder backDateTime(Date x);
        public abstract Builder backdatedTimeGranularity(Accuracy x);
        public abstract Builder createdTime(Date x);
        public abstract Builder from(User x);
        public abstract Builder height(Integer x);
        public abstract Builder icon(String x);
        public abstract Builder images(List<Image> x);
        public abstract Builder link(String x);
        public abstract Builder name(String x);
        public abstract Builder message(String x);
        public abstract Builder pageStoryId(String x);
        public abstract Builder picture(String x);
        public abstract Builder pictureBitmap(Bitmap x);
        public abstract Builder place(Place x);
        public abstract Builder updatedTime(Date x);
        public abstract Builder width(Integer x);
        public abstract Builder likes(Users x);
        public abstract Builder comments(Comments x);

        // POST
        public abstract Builder caption(String x);
        public abstract Builder url(String x);
        public abstract Builder vaultImageId(String x);
        public abstract Builder tags(List<Tag> x);
        public abstract Builder placeId(String x);
        public abstract Builder targeting(Target x);
        public abstract Builder feedTargeting(FeedTarget x);
        public abstract Builder noStory(boolean x);
        public abstract Builder published(boolean x);
        public abstract Builder offlineId(int x);
        public abstract Builder attempt(int x);
        public abstract Builder backdatedTime(Date x);
        public abstract Builder filterType(int x);
        public abstract Builder temporary(boolean x);
        public abstract Builder scheduledPublishTime(long x);
        public abstract Builder unpublishedContentType(String x);
        public abstract Builder nectarModule(String x);
        public abstract Builder ogActionTypeId(String x);
        public abstract Builder ogObjectId(String x);
        public abstract Builder ogPhrase(String x);
        public abstract Builder ogIconId(String x);
        public abstract Builder ogSuggestionMechanism(String x);
        public abstract Builder composerSessionId(String x);
        public abstract Builder privacy(Privacy x);

        public abstract Photo build();
    }

    public static Builder builder() {
        return new AutoJson_Photo.Builder();
    }

    //public abstract Builder toBuilder();
    public abstract Bundle toBundle();

    public Observable<Struct> like() {
        return Facebook.get().like(this);
    }

    public Observable<Struct> unlike() {
        return Facebook.get().unlike(this);
    }

}
