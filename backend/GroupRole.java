package backend;

public enum GroupRole {
    PRIMARYADMIN(1),
    ADMIN(2),
    MEMBER(3),
    PENDING(4);
    private final int value;
    GroupRole(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
