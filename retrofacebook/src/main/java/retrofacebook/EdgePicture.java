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

/**
 * <pre>
 * {
 *   "data": {
 *     "is_silhouette": false,
 *     "url": https://fbcdn-sphotos-e-a.akamaihd.net/hphotos-ak-xta1/v/t1.0-9/s180x540/10406xxx_10152728596979xxx_221607749089636xxx_n.jpg?oh=ea84e94275abd4d76623d899cb868xxx&oe=56321xxx&__gda__=1441311xxx_d88751c4686d307397be1a44ee21exxx
 *   }
 * }
 * </pre>
 */
@AutoJson
public abstract class EdgePicture {
    @Nullable
    @AutoJson.Field(name = "data")
    public abstract Picture data();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder data(Picture x);

        public abstract EdgePicture build();
    }

    public static Builder builder() {
        return new AutoJson_EdgePicture.Builder();
    }
}
