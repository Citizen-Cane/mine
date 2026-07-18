package teaselib.scripts.mine.test;

import org.junit.jupiter.api.AfterEach;

import java.io.IOException;

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

    @AfterEach
    public void close() {
        preset.close();
    }

}
