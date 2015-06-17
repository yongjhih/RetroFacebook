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
public abstract class Profile {
    @Nullable
    @AutoJson.Field
    public abstract String id();
    @Nullable
    @AutoJson.Field
    public abstract String name();
    @Nullable
    @AutoJson.Field(name = "first_name")
    public abstract String firstName();
    @Nullable
    @AutoJson.Field(name = "middle_name")
    public abstract String middleName();
    @Nullable
    @AutoJson.Field(name = "last_name")
    public abstract String lastName();
    @Nullable
    @AutoJson.Field
    public abstract String gender();
    @Nullable
    @AutoJson.Field
    public abstract String locale();
    @Nullable
    @AutoJson.Field
    public abstract List<Language> languages();
    @Nullable
    @AutoJson.Field
    public abstract String link();
    @Nullable
    @AutoJson.Field(name = "age_range")
    public abstract AgeRange ageRange();
    @Nullable
    @AutoJson.Field(name = "third_party_id")
    public abstract String thirdPartyId();
    @Nullable
    @AutoJson.Field
    public abstract Boolean installed();
    @Nullable
    @AutoJson.Field
    public abstract Integer timeZone();
    @Nullable
    @AutoJson.Field(name = "updated_time")
    public abstract String updatedTime();
    @Nullable
    @AutoJson.Field
    public abstract Boolean verified();
    @Nullable
    @AutoJson.Field
    public abstract String bio();
    @Nullable
    @AutoJson.Field
    public abstract String birthday();
    @Nullable
    @AutoJson.Field
    public abstract Photo cover();
    @Nullable
    @AutoJson.Field
    public abstract String currency();
    @Nullable
    @AutoJson.Field
    public abstract List<Education> education();
    @Nullable
    @AutoJson.Field
    public abstract String email();
    @Nullable
    @AutoJson.Field
    public abstract String hometown();
    @Nullable
    @AutoJson.Field
    public abstract Location location();
    @Nullable
    @AutoJson.Field
    public abstract String political();
    @Nullable
    @AutoJson.Field(name = "favorite_athletes")
    public abstract List<String> favoriteAthletes();
    @Nullable
    @AutoJson.Field(name = "favorite_teams")
    public abstract List<String> favoriteTeams();
    @Nullable
    @AutoJson.Field
    public abstract String picture();
    @Nullable
    @AutoJson.Field
    public abstract String quotes();
    @Nullable
    @AutoJson.Field(name = "relationship_status")
    public abstract String relationshipStatus();
    @Nullable
    @AutoJson.Field
    public abstract String religion();
    @Nullable
    @AutoJson.Field
    public abstract String website();
    @Nullable
    @AutoJson.Field
    public abstract List<Work> work();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String x);
        public abstract Builder name(String x);
        public abstract Builder firstName(String x);
        public abstract Builder middleName(String x);
        public abstract Builder lastName(String x);
        public abstract Builder gender(String x);
        public abstract Builder locale(String x);
        public abstract Builder languages(List<Language> x);
        public abstract Builder link(String x);
        public abstract Builder ageRange(AgeRange x);
        public abstract Builder thirdPartyId(String x);
        public abstract Builder installed(Boolean x);
        public abstract Builder timeZone(Integer x);
        public abstract Builder updatedTime(String x);
        public abstract Builder verified(Boolean x);
        public abstract Builder bio(String x);
        public abstract Builder birthday(String x);
        public abstract Builder cover(Photo x);
        public abstract Builder currency(String x);
        public abstract Builder education(List<Education> x);
        public abstract Builder email(String x);
        public abstract Builder hometown(String x);
        public abstract Builder location(Location x);
        public abstract Builder political(String x);
        public abstract Builder favoriteAthletes(List<String> x);
        public abstract Builder favoriteTeams(List<String> x);
        public abstract Builder picture(String x);
        public abstract Builder quotes(String x);
        public abstract Builder relationshipStatus(String x);
        public abstract Builder religion(String x);
        public abstract Builder website(String x);
        public abstract Builder work(List<Work> x);

        public abstract Profile build();
    }

    public static Builder builder() {
        return new AutoJson_Profile.Builder();
    }
}
