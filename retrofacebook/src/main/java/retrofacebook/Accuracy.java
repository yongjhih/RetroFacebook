package retrofacebook;

public enum Accuracy {
    YEAR("year"),
    MONTH("month"),
    DAY("day"),
    HOUR("hour"),
    MIN("min"),
    NONE("none");

    private String mValue;

    private Accuracy(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public static Accuracy fromValue(String value) {
        for (Accuracy granularityEnum : values()) {
            if (granularityEnum.mValue.equals(value)) {
                return granularityEnum;
            }
        }
        return Accuracy.NONE;
    }
}
