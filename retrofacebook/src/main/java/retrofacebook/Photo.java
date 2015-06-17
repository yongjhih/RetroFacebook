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
import java.util.List;
import java.util.Date;

@AutoJson
public abstract class Photo {
    @Nullable
    @AutoJson.Field
    public abstract String caption();
    @Nullable
    @AutoJson.Field
    public abstract String url();

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
    @AutoJson.Field(name = "backdate_time_granularity")
    public abstract BackDatetimeGranularity backDatetimeGranularity();
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
    @AutoJson.Field(name = "page_story_id")
    public abstract String pageStoryId();
    @Nullable
    @AutoJson.Field
    public abstract String picture();
    @Nullable
    @AutoJson.Field
    public abstract Place place();
    @Nullable
    @AutoJson.Field
    public abstract String source();
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Date updatedTime();
    @Nullable
    @AutoJson.Field
    public abstract Integer width();
    @Nullable
    public abstract String placeId();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder caption(String x);
        public abstract Builder url(String x);

        public abstract Builder id(String x);
        public abstract Builder album(Album x);
        public abstract Builder backDateTime(Date x);
        public abstract Builder backDatetimeGranularity(BackDatetimeGranularity x);
        public abstract Builder createdTime(Date x);
        public abstract Builder from(User x);
        public abstract Builder height(Integer x);
        public abstract Builder icon(String x);
        public abstract Builder images(List<Image> x);
        public abstract Builder link(String x);
        public abstract Builder name(String x);
        public abstract Builder pageStoryId(String x);
        public abstract Builder picture(String x);
        public abstract Builder place(Place x);
        public abstract Builder source(String x);
        public abstract Builder updatedTime(Date x);
        public abstract Builder width(Integer x);
        public abstract Builder placeId(String x);

        public abstract Photo build();
    }

    public static Builder builder() {
        return new AutoJson_Photo.Builder();
    }
}
