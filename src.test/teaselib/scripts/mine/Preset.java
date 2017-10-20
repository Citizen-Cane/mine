/**
 * 
 */
package teaselib.scripts.mine;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.Debugger;
import teaselib.core.Debugger.ResponseAction;
import teaselib.core.TeaseLib;
import teaselib.hosts.DummyHost;
import teaselib.hosts.DummyPersistence;
import teaselib.test.DebugSetup;

/**
 * @author Citizen-Cane
 *
 */
public class Preset {
    final Mine mine;
    final Debugger debugger;

    public Preset() throws IOException {
        this(new Mine(new TeaseLib(new DummyHost(), new DummyPersistence(), new DebugSetup()),
                new File("../SexScripts/scripts/")));
    }

    public Preset(Mine mine) {
        this.mine = mine;
        debugger = new Debugger(mine.teaseLib);
    }

    protected Preset set(int... n) {
        for (int i : n) {
            mine.state.set(i);
        }
        return this;
    }

    public Preset script(String script)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        mine.loadScript(script);
        return this;
    }

    public Preset submitted() {
        if (mine.script.name != "Mine") {
            throw new IllegalStateException("Wrong script");
        }
        set(100, 101);
        return this;
    }

    public Preset responses(Collection<ResponseAction> responses) {
        debugger.addResponses(responses);
        return this;
    }

    public Mine mine() {
        return mine;
    }
}
