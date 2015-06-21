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

@AutoJson
public abstract class Attachment {
    @Nullable
    @AutoJson.Field
    public abstract String id();

    @Nullable
    @AutoJson.Field
    public abstract String description();

    @Nullable
    @AutoJson.Field(name = "description_tags")
    public abstract List<Profile> descriptionTags();
    //public abstract StoryAttachmentMedia media();
    @Nullable
    @AutoJson.Field
    public abstract List<Attachment> subAttachments();
    //public abstract StoryAttachmentTarget target();
    @Nullable
    @AutoJson.Field
    public abstract String title();
    @Nullable
    @AutoJson.Field
    public abstract String type();
    @Nullable
    @AutoJson.Field
    public abstract String url();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);

        public abstract Builder description(String x);
        public abstract Builder descriptionTags(List<Profile> x);
        //public abstract Builder media(StoryAttachmentMedia x);
        public abstract Builder subAttachments(List<Attachment> x);
        //public abstract Builder target(StoryAttachmentTarget x);
        public abstract Builder title(String x);
        public abstract Builder type(String x);
        public abstract Builder url(String x);
        public abstract Attachment build();
    }

    public static Builder builder() {
        return new AutoJson_Attachment.Builder();
    }
}
