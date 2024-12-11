package backend;

public enum NotificationType {
        REQUEST(1),
        PROMOTION(2),
        DEMOTION(3),
        ADDING(4),
        POST(5);
    private final int value;
    NotificationType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
