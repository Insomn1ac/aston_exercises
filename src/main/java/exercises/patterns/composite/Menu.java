package exercises.patterns.composite;

import java.util.List;

public interface Menu {
    String showMenu();
    String getName();
    List<Menu> getMenuList();
    Action getAction();
    Action select(String itemName);
}
