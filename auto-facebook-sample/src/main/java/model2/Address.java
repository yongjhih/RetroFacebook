package model2;

import auto.facebook.AutoFacebook;
import android.os.Parcelable;
import org.json.JSONObject;
//import javax.annotation.Nullable;
import android.support.annotation.Nullable;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.LoganSquare;

@AutoFacebook
public abstract class Address implements Parcelable {
  @Nullable
  @AutoFacebook.Field
  public abstract double[] coordinates();

  @Nullable
  @AutoFacebook.Field(name = "is_hidden")
  public abstract String cityName();

  public static Address create(double[] coordinates, String cityName) {
      return builder().coordinates(coordinates).cityName(cityName).build();
  }

  public static Builder builder() {
      return new AutoFacebook_Address.Builder();
  }

  @AutoFacebook.Builder
  public interface Builder {
      public Builder coordinates(double[] x);
      public Builder cityName(String x);
      public Address build();
  }

  @AutoFacebook.Validate
  public void validate() {
      if (cityName().length() < 2) {
          throw new IllegalStateException("Not a city name");
      }
  }
}
