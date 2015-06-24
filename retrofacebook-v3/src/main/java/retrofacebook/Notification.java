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

/**
 * @see https://developers.facebook.com/docs/graph-api/reference/v2.3/notification/
 */
@AutoJson
public abstract class Notification {
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field
    public abstract User from(); // TODO User|Page|App
    @Nullable
    @AutoJson.Field
    public abstract User to();
    @Nullable
    @AutoJson.Field(name = "created_time")
    public abstract Date createdTime();
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract Date updatedTime();
    @Nullable
    @AutoJson.Field
    public abstract String title();
    @Nullable
    @AutoJson.Field
    public abstract String link();
    @Nullable
    @AutoJson.Field
    public abstract App application();
    @Nullable
    @AutoJson.Field
    public abstract int unread();
    @Nullable
    @AutoJson.Field
    public abstract String object();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder from(User x);
        public abstract Builder to(User x);
        public abstract Builder createdTime(Date x);
        public abstract Builder updatedTime(Date x);
        public abstract Builder title(String x);
        public abstract Builder link(String x);
        public abstract Builder application(App x);
        public abstract Builder unread(int x);
        public abstract Builder object(String x);

        public abstract Notification build();
    }

    public static Builder builder() {
        return new AutoJson_Notification.Builder();
    }

    public abstract Bundle toBundle();
}
