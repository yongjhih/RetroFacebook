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
import java.util.Date;
import java.util.List;

/**
 * @see https://developers.facebook.com/docs/graph-api/reference/video
 */
@AutoJson
public abstract class Video {
    // id numeric string The video ID.
    @Nullable
    @AutoJson.Field
    public abstract String id();
    // created_time datetime The time the video was initially published.
    @Nullable
    @AutoJson.Field(name = "created_time")
    public abstract Date createdTime();
    // description string The description of the video.
    @Nullable
    @AutoJson.Field
    public abstract String description();
    // embed_html string The HTML element that may be embedded in a Web page to play the video.
    @Nullable
    @AutoJson.Field(name = "embed_html")
    public abstract String embedHtml();
    // format list<VideoFormat> The different formats of the video.
    @Nullable
    @AutoJson.Field
    public abstract List<VideoFormat> format();
    // from The profile that created the video.
    @Nullable
    @AutoJson.Field
    public abstract Profile from();
    // icon string The icon that Facebook displays when videos are published to the feed.
    @Nullable
    @AutoJson.Field
    public abstract String icon();
    // length float Duration of this video.
    @Nullable
    @AutoJson.Field
    public abstract float length();
    // name string The video title or caption.
    @Nullable
    @AutoJson.Field
    public abstract String name();
    // status VideoStatus Object describing the status attributes of a video.
    @Nullable
    @AutoJson.Field
    public abstract String status(); // TODO VideoStatus
    // source string A URL to the raw, playable video file.
    @Nullable
    @AutoJson.Field
    public abstract String source();
    // updated_time datetime The last time the video or its caption was updated.
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Date updatedTime();
    // privacy Privacy Privacy setting for the video.
    @Nullable
    @AutoJson.Field
    public abstract Privacy privacy();
    // published bool Whether a post about this video is published.
    @Nullable
    @AutoJson.Field
    public abstract boolean published();
    // scheduled_publish_time datetime The time that the video is scheduled to expire.
    @Nullable
    @AutoJson.Field(name = "scheduled_publish_time")
    public abstract Date scheduledPublishTime();
    // event Event If this object has a place, the event associated with the place
    @Nullable
    @AutoJson.Field
    public abstract String event(); // TODO Event
    // place Place Location associated with the video, if any.
    @Nullable
    @AutoJson.Field
    public abstract Place place();
    // backdated_time datetime The time when the video post was created.
    @Nullable
    @AutoJson.Field(name = "backdated_time")
    public abstract Date backdatedTime();
    //backdated_time_granularity enum Accuracy of the backdated time.
    @Nullable
    @AutoJson.Field(name = "backdated_time_granularity", typeConverter = AccuracyConverter.class)
    public abstract Accuracy backdatedTimeGranularity();

    //picture string The URL for the thumbnail picture of the video.
    @Nullable
    @AutoJson.Field
    public abstract String picture();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder createdTime(Date x);
        public abstract Builder description(String x);
        public abstract Builder embedHtml(String x);
        public abstract Builder format(List<VideoFormat> x);
        public abstract Builder from(Profile x);
        public abstract Builder icon(String x);
        public abstract Builder length(float x);
        public abstract Builder name(String x);
        public abstract Builder status(String x);
        public abstract Builder source(String x);
        public abstract Builder updatedTime(Date x);
        public abstract Builder privacy(Privacy x);
        public abstract Builder published(boolean x);
        public abstract Builder scheduledPublishTime(Date x);
        public abstract Builder event(String x);
        public abstract Builder place(Place x);
        public abstract Builder backdatedTime(Date x);
        public abstract Builder backdatedTimeGranularity(Accuracy x);
        public abstract Builder picture(String x);

        public abstract Video build();
    }

    public static Builder builder() {
        return new AutoJson_Video.Builder();
    }

    public abstract Bundle toBundle();
}
