package exercises.patterns.builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ComputerBuilderTest {

    @Test
    public void whenComputerWithAllPartsAdded() {
        Computer computer = new ComputerBuilder()
                .buildMotherboard(new Motherboard("1", 100))
                .buildProcessor(new Processor("2", 150))
                .buildStorage(Storage.SOLID_STATE_DRIVE)
                .buildVideocard(new Videocard("3", 325))
                .build();
        assertThat(computer.toString()).contains("3");
        assertThat(computer.getPrice()).isEqualTo(975);
    }

    @Test
    public void whenVideocardWasNotAdded() {
        Computer computer = new ComputerBuilder()
                .buildMotherboard(new Motherboard("1", 100))
                .buildProcessor(new Processor("2", 150))
                .buildStorage(Storage.EXTERNAL_HARD_DRIVE)
                .build();
        assertThat(computer.toString()).doesNotContain("3");
        assertThat(computer.getPrice()).isEqualTo(1050);
    }

    @Test
    public void whenEmptyComputer() {
        Computer computer = new ComputerBuilder()
                .build();
        assertThat(computer.toString()).contains("NO_DISK_DRIVE");
        assertThat(computer.getPrice()).isEqualTo(0);
    }
}