package exercises.patterns.builder;

public class ComputerBuilder {
    private Motherboard motherboard = new Motherboard("No motherboard", 0);
    private Processor processor = new Processor("No processor", 0);
    private Storage storage = Storage.NO_DISK_DRIVE;
    private Videocard videocard = new Videocard("No videocard", 0);

    public ComputerBuilder buildMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
        return this;
    }

    public ComputerBuilder buildProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }

    public ComputerBuilder buildStorage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public ComputerBuilder buildVideocard(Videocard videocard) {
        this.videocard = videocard;
        return this;
    }

    public Computer build() {
        Computer computer = new Computer();
        computer.setMotherboard(motherboard);
        computer.setProcessor(processor);
        computer.setStorage(storage);
        computer.setVideocard(videocard);
        return computer;
    }
}
