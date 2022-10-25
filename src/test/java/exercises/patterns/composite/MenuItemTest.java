package exercises.patterns.composite;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuItemTest {

    @Test
    void whenSelectFromMenuWithSeveralCases() {
        Menu menu = new MenuItem("Задача 1.", new ReturnToReworkAction());
        Menu menu2 = new MenuItem("Задача 2.");
        assertThat(menu.getMenuList().size()).isEqualTo(1);
        assertThat(menu.select(menu.getName())).isInstanceOf(ReturnToReworkAction.class);
        assertThatThrownBy(() -> menu.select(menu2.getName())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenShowCompositeAndMenuInside() {
        Menu menu = new MenuItem("Задача 1.1.", new ReturnToReworkAction());
        Composite mainMenu = new Composite("Задача 1.");
        String expected = "Задача 1.1." + System.lineSeparator();
        assertThat(menu.showMenu()).isEqualTo(expected);
        mainMenu.addToMenu(menu);
        expected = String.join(System.lineSeparator(),
                "Задача 1.",
                "Задача 1.1." + System.lineSeparator());
        assertThat(mainMenu.showMenu()).isEqualTo(expected);
    }
}