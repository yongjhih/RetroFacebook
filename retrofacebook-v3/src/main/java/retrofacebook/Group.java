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

@AutoJson
public abstract class Group {
    // id The group ID
    @Nullable
    @AutoJson.Field
    public abstract String id();
    // string cover
    @Nullable
    @AutoJson.Field
    public abstract Cover cover();
    // description A brief description of the group.
    @Nullable
    @AutoJson.Field
    public abstract String description();
    // string email // The email address to upload content to the group. Only current members of the group can use this.  string
    @Nullable
    @AutoJson.Field
    public abstract String email();
    // icon The URL for the group's icon.
    @Nullable
    @AutoJson.Field
    public abstract String icon();
    // string link // The group's website.  string
    @Nullable
    @AutoJson.Field
    public abstract String link();
    // member_request_count The number of pending member requests
    @Nullable
    @AutoJson.Field(name = "member_request_count")
    public abstract int memberRequestCount();
    // The name of the group.  string
    @Nullable
    @AutoJson.Field
    public abstract String name();
    // owner The profile that created this group.  // User|Page parent
    @Nullable
    @AutoJson.Field
    public abstract String owner(); // TODO
    // The parent of this group, if it exists.  Group|Page|App
    @Nullable
    @AutoJson.Field
    public abstract String parent(); // TODO
    // privacy The privacy setting of the group.
    @Nullable
    @AutoJson.Field
    public abstract String privacy(); // TODO
    // string updated_time The last time the group was updated (this includes changes in the group's properties and changes in posts and comments if the session user can see them).  datetime
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Date updatedTime();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder cover(Cover x);
        public abstract Builder description(String x);
        public abstract Builder email(String x);
        public abstract Builder icon(String x);
        public abstract Builder link(String x);
        public abstract Builder memberRequestCount(int x);
        public abstract Builder name(String x);
        public abstract Builder owner(String x);
        public abstract Builder parent(String x);
        public abstract Builder privacy(String x);
        public abstract Builder updatedTime(Date x);

        public abstract Group build();
    }

    public static Builder builder() {
        return new AutoJson_Group.Builder();
    }

    public abstract Bundle toBundle();
}
