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
import com.bluelinelabs.logansquare.typeconverters.*;

@AutoJson
public abstract class FeedTarget {
    @Nullable
    @AutoJson.Field
    public abstract List<String> countries();
    //list<string>
    //Values for targeted countries. You can specify up to 25 countries. Use ISO 3166 format codes.
    @Nullable
    @AutoJson.Field
    public abstract List<String> regions();
    //list<string>
    //Values for targeted regions. Use type of adregion to find FeedTargeting Options and use the returned key to specify. For example:
    //search/?type=adregion&q=California
    @Nullable
    @AutoJson.Field
    public abstract List<String> cities();
    //list<string>
    //Values for targeted cities. Use type of adcity to find FeedTargeting Options and use the returned key to specify.
    @Nullable
    @AutoJson.Field
    public abstract List<String> zipcodes();
    //list<string>
    //Values for targeted zipcodes. Use type of adzipcode to find FeedTargeting Options and use the returned key to specify.
    @Nullable
    @AutoJson.Field
    public abstract List<String> excludedCountries();
    //list<string>
    //Values for excluded countries. You can specify up to 25 countries. Use ISO 3166 format codes.
    @Nullable
    @AutoJson.Field
    public abstract List<Integer> excludedRegions();
    //list<unsigned int32>
    //Values for excluded regions. Use type of adregion and list of GLOBAL to find FeedTargeting Options and use the returned key to specify. For example:
    //search/?type=adregion&q=California&list=GLOBAL
    @Nullable
    @AutoJson.Field
    public abstract List<Integer> excludedCities();
    //list<unsigned int32>
    //Values for excluded cities. Use type of adcity to find FeedTargeting Options and use the returned key to specify.
    @Nullable
    @AutoJson.Field
    public abstract List<String> excludedZipcodes();
    //list<string>
    //Values for excluded zipcodes. Use type of adzipcode to find FeedTargeting Options and use the returned key to specify.
    @Nullable
    @AutoJson.Field
    public abstract List<Integer> timezones();
    //list<unsigned int32>
    //ID for the timezone. See here.
    @Nullable
    @AutoJson.Field
    public abstract int ageMin();
    //unsigned int32
    //Must be 13 or higher. Default is 0.
    @Nullable
    @AutoJson.Field
    public abstract int ageMax();
    //unsigned int32
    //Maximum age.
    @Nullable
    @AutoJson.Field
    public abstract List<String> genders();
    //list<unsigned int32>
    //FeedTarget specific genders. 1 targets all male viewers and 2 females. Default is to target both.
    @Nullable
    @AutoJson.Field
    public abstract List<String> locales();
    //list<string>

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder countries(List<String> x);
        public abstract Builder regions(List<String> x);
        public abstract Builder cities(List<String> x);
        public abstract Builder zipcodes(List<String> x);
        public abstract Builder excludedCountries(List<String> x);
        public abstract Builder excludedRegions(List<Integer> x);
        public abstract Builder excludedCities(List<Integer> x);
        public abstract Builder excludedZipcodes(List<String> x);
        public abstract Builder timezones(List<Integer> x);
        public abstract Builder ageMin(int x);
        public abstract Builder ageMax(int x);
        public abstract Builder genders(List<String> x);
        public abstract Builder locales(List<String> x);

        public abstract FeedTarget build();
    }

    public static Builder builder() {
        return new AutoJson_FeedTarget.Builder();
    }
}

