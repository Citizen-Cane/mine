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
import teaselib.core.debug.DebugHost;
import teaselib.core.debug.DebugPersistence;
import teaselib.scripts.mine.Mine;
import teaselib.test.DebugSetup;

/**
 * @author Citizen-Cane
 *
 */
public class Preset {
    final Mine mine;
    final Debugger debugger;

    public Preset() throws IOException {
        this(new DebugPersistence.Storage());
    }

    public Preset(DebugPersistence.Storage storage) throws IOException {
        this(new Mine(new TeaseLib(new DebugHost(), new DebugPersistence(storage), new DebugSetup()),
                new File("../SexScripts/scripts/")));
    }

    private Preset(Mine mine) {
        this.mine = mine;
        debugger = new Debugger(mine.teaseLib);
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
        set(100, 101);
        return this;
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

}
