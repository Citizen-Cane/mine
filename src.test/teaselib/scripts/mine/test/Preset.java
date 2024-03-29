/**
 * 
 */
package teaselib.scripts.mine.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.Debugger;
import teaselib.core.Debugger.ResponseAction;
import teaselib.core.TeaseLib;
import teaselib.core.configuration.DebugSetup;
import teaselib.core.configuration.Setup;
import teaselib.core.debug.DebugHost;
import teaselib.scripts.mine.Mine;

/**
 * @author Citizen-Cane
 *
 */
public class Preset implements teaselib.core.Closeable {

    private DebugHost host;
    private final TeaseLib teaseLib;
    private final Mine mine;
    private final Debugger debugger;

    public Preset() throws IOException {
        this(new DebugSetup().ignoreMissingResources());
    }

    public Preset(Setup setup) throws IOException {
        this.host = new DebugHost();
        this.teaseLib = new TeaseLib(this.host, setup);
        this.mine = new Mine(teaseLib, new File("../SexScripts/scripts/"));
        debugger = new Debugger(mine.teaseLib);
        mine.teaseLib.globals.store(debugger);
    }

    public Preset set(int... n) {
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

    public Preset clearHandlers() {
        mine.script.onAllSet = null;
        mine.script.onClose = null;
        return this;
    }

    public Preset submitted() {
        if (mine.script.name != "Mine") {
            throw new IllegalStateException("Wrong script");
        }
        return set(100, 101);
    }

    public Preset responses(Collection<ResponseAction> responses) {
        debugger.addResponses(responses);
        return this;
    }

    public Preset responses(TestParameters testParameters) {
        debugger.addResponses(testParameters.getResponseActions(mine));
        return this;
    }

    public Mine mine() {
        return mine;
    }

    public Preset punishmentAcceptance(int value) {
        return setLevel(711, value);
    }

    public Preset strictSelfBondage(int value) {
        return setLevel(741, value);
    }

    public Preset setLevel(int setting, int value) {
        if (value < 0 || value > 5)
            throw new IllegalArgumentException("Out of range: " + value);

        int i = 0;
        for (; i < value; i++) {
            mine.state.set(setting + i);
        }

        for (; i <= 5; i++) {
            mine.state.unset(setting + i);
        }

        return this;
    }

    @Override
    public void close() {
        teaseLib.close();
        host.close();
    }

}
