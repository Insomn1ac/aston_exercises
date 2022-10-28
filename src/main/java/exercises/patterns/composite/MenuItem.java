package exercises.patterns.composite;

import java.util.List;

public class MenuItem implements Menu {
    private final String name;
    private Action action;

    public MenuItem(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Menu> getMenuList() {
        return List.of(this);
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Action select(String itemName) {
        if (!itemName.equals(name)) {
            throw new IllegalArgumentException("Action doesn't exist.");
        }
        return action;
    }

    @Override
    public String showMenu() {
        return getName() + System.lineSeparator();
    }
}
