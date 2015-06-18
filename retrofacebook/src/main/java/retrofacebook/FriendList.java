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
public abstract class FriendList {
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field
    public abstract String name();
    @Nullable
    @AutoJson.Field
    public abstract String listType(); // TODO enum
    @Nullable
    @AutoJson.Field
    public abstract String owner(); // TODO type?

    @Nullable
    @AutoJson.Field
    public abstract List<Profile> members();
    @Nullable
    @AutoJson.Field
    public abstract String picture(); // v2.3

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder name(String x);
        public abstract Builder listType(String x);
        public abstract Builder owner(String x);

        public abstract Builder members(List<Profile> x);
        public abstract Builder picture(String x);

        public abstract FriendList build();
    }

    public static Builder builder() {
        return new AutoJson_FriendList.Builder();
    }
}
