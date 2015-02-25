package ss;

import teaselib.Persistence;
import teaselib.persistence.Clothes;
import teaselib.persistence.Item;
import teaselib.persistence.Toys;

public class SexScriptsStatePersistence implements Persistence {

    private final String root;
    private final ss.IScript host;

    public SexScriptsStatePersistence(IScript host, String root) {
        this.host = host;
        this.root = root + ".";
    }

    @Override
    public String get(String name) {
        String value = host.loadString(root + name);
        return value;
    }

    @Override
    public void set(String name, String value) {
        host.save(root + name, value);
    }

    // @Override
    // public void write(String name, int value) {
    // host.save(root + "." + name, new Integer(value));
    // }

    // @Override
    // public int read(String name, int defaultValue) {
    // Float value = host.loadFloat(root + "." + name);
    // if (value != 0)
    // {
    // return value.intValue();
    // }
    // else
    // {
    // return defaultValue;
    // }
    // }

    @Override
    public Item get(Toys toy) {
        return new Item("toys." + toy.toString().toLowerCase(), this);
    }

    @Override
    public Item get(Clothes item) {
        return new Item("clothes." + item.toString().toLowerCase(), this);
    }
}
