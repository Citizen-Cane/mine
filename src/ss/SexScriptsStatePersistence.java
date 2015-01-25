package ss;

import java.util.HashMap;
import java.util.Map;

import teaselib.Persistence;

public class SexScriptsStatePersistence implements Persistence {

    private final String root;
    private final ss.IScript host;

    private final Map<String, String> mapping = new HashMap<>();

    public SexScriptsStatePersistence(IScript host, String root) {
        this.host = host;
        this.root = root + ".";
    }

    @Override
    public String read(String name) {
        String value = host.loadString(root + name);
        return value;
    }

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
    //

    @Override
    public void write(String name, String value) {
        host.save(root + name, value);
    }

    // @Override
    // public void write(String name, int value) {
    // host.save(root + "." + name, new Integer(value));
    // }

    @Override
    public void setMapping(String internal, String external) {
        mapping.put(internal, root + external);
    }

}
