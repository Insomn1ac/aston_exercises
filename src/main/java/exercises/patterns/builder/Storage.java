package exercises.patterns.builder;

public enum Storage {
    HARD_DISK_DRIVE,
    SOLID_STATE_DRIVE,
    EXTERNAL_HARD_DRIVE,
    NO_DISK_DRIVE;

    public int getPrice() {
        Storage storage = this;
        return (storage == Storage.HARD_DISK_DRIVE) ? 250
                : (storage == Storage.SOLID_STATE_DRIVE) ? 400
                : (storage == Storage.EXTERNAL_HARD_DRIVE) ? 800
                : 0;
    }
}
