package retrofacebook;

import com.bluelinelabs.logansquare.typeconverters.*;

public class AccuracyConverter extends StringBasedTypeConverter<Accuracy> {
    @Override
    public Accuracy getFromString(String s) {
        return Accuracy.fromValue(s);
    }

    public String convertToString(Accuracy object) {
        return object.getValue();
    }
}

