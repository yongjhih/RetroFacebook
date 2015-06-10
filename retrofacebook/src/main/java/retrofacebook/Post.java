package retrofacebook;

import auto.json.AutoJson;
import android.support.annotation.Nullable;

@AutoJson
abstract class Post {
    @Nullable
    public abstract String id();

    @Nullable
    @AutoJson.Field(name = "is_hidden")
    public abstract Boolean isHidden();

    @AutoJson.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);
        public abstract Builder isHidden(Boolean isHidden);

        public abstract Post build();
    }

    public static Builder builder() {
        return new AutoJson_Post.Builder();
    }
}
