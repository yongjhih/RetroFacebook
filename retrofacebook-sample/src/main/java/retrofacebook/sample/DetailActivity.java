package retrofacebook.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import retrofacebook.Facebook;

public class DetailActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Facebook facebook = Facebook.create();
  }

  private TextView textView(int id) {
    return (TextView) findViewById(id);
  }
}
