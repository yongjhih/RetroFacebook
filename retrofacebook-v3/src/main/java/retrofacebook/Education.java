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
public abstract class Education {
    @Nullable
    @AutoJson.Field
    public abstract String school();
    @Nullable
    @AutoJson.Field
    public abstract String degree();
    @Nullable
    @AutoJson.Field
    public abstract String year();
    @Nullable
    @AutoJson.Field(name = "concentration")
    public abstract List<String> concentrations();
    @Nullable
    @AutoJson.Field
    public abstract List<User> with();
    @Nullable
    @AutoJson.Field
    public abstract String type();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder school(String x);
        public abstract Builder degree(String x);
        public abstract Builder year(String x);
        public abstract Builder concentrations(List<String> x);
        public abstract Builder with(List<User> x);
        public abstract Builder type(String x);

        public abstract Education build();
    }

    public static Builder builder() {
        return new AutoJson_Education.Builder();
    }
}

