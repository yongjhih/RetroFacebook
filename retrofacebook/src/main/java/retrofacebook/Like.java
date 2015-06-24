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

@AutoJson
public abstract class Like {
    @Nullable
    @AutoJson.Field
    public abstract User user();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder user(User x);
        public abstract Like build();
    }

    public static Builder builder() {
        return new AutoJson_Like.Builder();
    }

    public abstract Bundle toBundle();
}
