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
import com.bluelinelabs.logansquare.typeconverters.*;

@AutoJson
public abstract class Privacy {
    @Nullable
    @AutoJson.Field
    public abstract String description();
    @Nullable
    @AutoJson.Field(typeConverter = PrivacySettingsConverter.class)
    public abstract PrivacySettings value();
    @Nullable
    @AutoJson.Field(typeConverter = PrivacySettingsConverter.class)
    public abstract PrivacySettings friends();
    @Nullable
    @AutoJson.Field
    public abstract List<String> allow();
    @Nullable
    @AutoJson.Field
    public abstract List<String> deny();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder description(String x);
        public abstract Builder value(PrivacySettings x);
        public abstract Builder friends(PrivacySettings x);
        public abstract Builder allow(List<String> x);
        public abstract Builder deny(List<String> x);

        public abstract Privacy build();
    }

    public static Builder builder() {
        return new AutoJson_Privacy.Builder();
    }

    public static enum PrivacySettings {
        EVERYONE("EVERYONE"),
        ALL_FRIENDS("ALL_FRIENDS"),
        FRIENDS_OF_FRIENDS("FRIENDS_OF_FRIENDS"),
        CUSTOM("CUSTOM"),
        SELF("SELF");

        private String value;

        private PrivacySettings(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static PrivacySettings fromValue(String value) {
            for (PrivacySettings privacy : values()) {
                if (privacy.value.equals(value)) return privacy;
            }
            return null;
        }
    }

    public static class PrivacySettingsConverter extends StringBasedTypeConverter<PrivacySettings> {
        @Override
        public PrivacySettings getFromString(String s) {
            return PrivacySettings.fromValue(s);
        }

        public String convertToString(PrivacySettings object) {
            return object.getValue();
        }
    }
}
