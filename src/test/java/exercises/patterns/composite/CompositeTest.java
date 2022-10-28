package exercises.patterns.composite;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompositeTest {
    String ln = System.lineSeparator();

    @Test
    public void whenMainHasOneLeafAndAddToThisLeaf() {
        Menu subLeaf = new MenuItem("--------- Задача 1.1.1.", new MarkAsCloseAction());
        Composite leaf = new Composite("---- Задача 1.1.");
        Composite main = new Composite("Задача 1.", new ReturnToReworkAction());
        leaf.addToMenu(subLeaf);
        main.addToMenu(leaf);
        String expected = String.join(ln,
                "Задача 1.",
                "---- Задача 1.1.",
                "--------- Задача 1.1.1." + ln);
        assertThat(main.showMenu()).isEqualTo(expected);
        assertThat(main.getMenuList().get(0).getMenuList().get(0)).isEqualTo(subLeaf);
        leaf.add("---- Задача 1.1.", "--------- Задача 1.1.2.", new ReturnToReworkAction());
        expected += "--------- Задача 1.1.2." + ln;
        assertThat(main.getMenuList().get(0).getMenuList().get(1).getName()).contains("2");
        assertThat(main.getMenuList().get(0).getMenuList().get(1).getAction().doAction()).contains("rework");
        assertThat(main.showMenu()).isEqualTo(expected);
    }

    @Test
    public void whenMainHasTwoSubLeavesThenShowMenu() {
        Menu subLeaf1 = new MenuItem("--------- Задача 1.1.1.", new MarkAsCloseAction());
        Menu subLeaf2 = new MenuItem("--------- Задача 1.1.2.", new ReturnToReworkAction());
        Composite leaf1 = new Composite("---- Задача 1.1.");
        leaf1.addToMenu(subLeaf1);
        leaf1.addToMenu(subLeaf2);
        Composite leaf2 = new Composite("---- Задача 1.2.", new MarkAsCloseAction());
        Composite main = new Composite("Задача 1.", new ReturnToReworkAction());
        main.addToMenu(leaf1);
        main.addToMenu(leaf2);
        String expected = String.join(ln, 
                "Задача 1.",
                "---- Задача 1.1.",
                "--------- Задача 1.1.1.",
                "--------- Задача 1.1.2.",
                "---- Задача 1.2." + ln);
        assertThat(main.showMenu()).isEqualTo(expected);
        assertThat(main.getMenuList().get(0).getMenuList().get(1)).isEqualTo(subLeaf2);
        assertThat(main.getMenuList().get(0).getAction()).isNull();
        assertThat(main.getMenuList().get(1).getAction().doAction()).contains("closed");
        assertThat(main.getMenuList().get(1).getMenuList()).isEmpty();
    }

    @Test
    public void whenSelectActionByTaskName() {
        Composite leaf1 = new Composite("---- Задача 1.1.");
        Composite leaf2 = new Composite("---- Задача 1.2.", new MarkAsCloseAction());
        Composite main = new Composite("Задача 1.", new ReturnToReworkAction());
        main.addToMenu(leaf1);
        main.addToMenu(leaf2);
        assertThat(main.select("---- Задача 1.2.").doAction()).isEqualTo(" closed.");
        assertThatThrownBy(() -> main.select("---- Задача 1.1.").doAction())
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenFindByNameInMenu() {
        Composite leaf1 = new Composite("---- Задача 1.1.");
        Composite main = new Composite("Задача 1.", new ReturnToReworkAction());
        main.addToMenu(leaf1);
        assertThat(main.findBy("---- Задача 1.1.")).isTrue();
        assertThat(main.findBy("---- Задача 1.2.")).isFalse();
    }

    @Test
    public void whenAddMainMenuInsideLeafThenExceptionThrows() {
        Composite main = new Composite("Задача 1.", new ReturnToReworkAction());
        Composite leaf1 = new Composite("---- Задача 1.1.");
        assertThatThrownBy(() -> leaf1.addToMenu(main)).isInstanceOf(IllegalArgumentException.class);
    }
}