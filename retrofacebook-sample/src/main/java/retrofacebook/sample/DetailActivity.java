package retrofacebook.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import retrofacebook.Facebook4;

public class DetailActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    Facebook4 facebook = Facebook4.create();
  }

  private TextView textView(int id) {
    return (TextView) findViewById(id);
  }
}
