package exercises.patterns.builder;

public class Computer {
    private Motherboard motherboard;
    private Processor processor;
    private Storage storage;
    private Videocard videocard;

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setVideocard(Videocard videocard) {
        this.videocard = videocard;
    }

    public int getPrice() {
        return motherboard.getPrice()
                + processor.getPrice()
                + storage.getPrice()
                + videocard.getPrice();
    }

    @Override
    public String toString() {
        return "Computer{"
                + "motherboard=" + motherboard.getType()
                + ", processor=" + processor.getType()
                + ", storage=" + storage
                + ", videocard=" + videocard.getType()
                + '}';
    }
}
