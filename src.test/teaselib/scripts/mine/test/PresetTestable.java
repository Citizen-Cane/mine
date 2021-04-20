package teaselib.scripts.mine.test;

import java.io.IOException;

import org.junit.After;

/**
 * @author Citizen Cane
 * 
 */
public class PresetTestable {

    protected final Preset preset;

    public PresetTestable() throws IOException {
        this.preset = new Preset();
    }

    public PresetTestable(Preset preset) {
        this.preset = preset;
    }

    @After
    public void close() {
        preset.close();
    }

}
