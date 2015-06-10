package retrofacebook;

import rx.Observable;
import rx.functions.*;

@RetroFacebook
public abstract class Facebook {
  @RetroFacebook.GET("/{postId}")
  public abstract Observable<Post> getPost(@RetroFacebook.Path String postId, String type);

  public static Facebook create() {
      return new RetroFacebook_Facebook();
  }
}
