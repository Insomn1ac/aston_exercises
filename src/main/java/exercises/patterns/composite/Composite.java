package exercises.patterns.composite;

import java.util.*;

public class Composite implements Menu {
    private final List<Menu> menuList = new ArrayList<>();
    private final String name;
    private Action action;

    public Composite(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public Composite(String name) {
        this.name = name;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    @Override
    public Action getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    @Override
    public Action select(String itemName) {
        Optional<Action> rsl = Optional.empty();
        if (itemName.equals(name)) {
            return action;
        }
        Queue<Menu> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Menu child = queue.poll();
            if (itemName.equals(child.getName())) {
                rsl = Optional.of(child.getAction());
                break;
            }
            queue.addAll(child.getMenuList());
        }
        return rsl.get();
    }

    public boolean findBy(String itemName) {
        boolean find = itemName.equals(name);
        for (Menu menu : menuList) {
            if (itemName.equals(menu.getName())) {
                find = true;
            }
        }
        return find;
    }

    public boolean add(String itemName, String childName, Action action) {
        if (findBy(itemName)) {
            if (itemName.equals(name)) {
                return menuList.add(new MenuItem(childName, action));
            } else {
                for (Menu menu : menuList) {
                    if (itemName.equals(menu.getName())) {
                        return menu.getMenuList().add(new MenuItem(childName, action));
                    }
                }
            }
        }
        return false;
    }

    public void addToMenu(Menu menu) {
        if (!menu.getName().contains(name)) {
            throw new IllegalArgumentException("Task \"" + menu.getName() + "\" cannot be put into \"" + name + "\"");
        }
        menuList.add(menu);
    }

    @Override
    public String showMenu() {
        StringBuilder rsl = new StringBuilder(getName()).append(System.lineSeparator());
        for (Menu menu : menuList) {
            rsl.append(menu.showMenu());
        }
        return rsl.toString();
    }
}
