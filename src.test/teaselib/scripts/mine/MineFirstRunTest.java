package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pcm.controller.AllActionsSetException;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.core.Debugger;
import teaselib.core.TeaseLib;
import teaselib.hosts.DummyHost;
import teaselib.hosts.DummyPersistence;
import teaselib.test.DebugSetup;

public class MineFirstRunTest {

    @Before
    public void init() throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Debugger debugger = new Debugger(new DummyHost(), new DummyPersistence(), new DebugSetup());

        TeaseLib teaseLib = debugger.teaseLib;
        debugger.freezeTime();
        debugger.addResponses(MinePrompts.all());

        mine = new Mine(teaseLib, new File("../SexScripts/scripts/"));
        mine.loadScript("Mine");
    }

    private Mine mine;

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException {
        mine.playFrom(new ActionRange(845, 846));

        assertScriptEndedGracefully();
        assertEquals("Good end", ScriptState.SET, mine.state.get(851));
    }

    @Test
    public void testSubmitted() throws AllActionsSetException, ScriptExecutionException {
        new Preset(mine).submitted();
        mine.playFrom(new ActionRange(845, 846));

        assertScriptEndedGracefully();
        assertEquals("Good end", ScriptState.SET, mine.state.get(851));
    }

    @Test
    public void testPunishments() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        int[] punishments = { 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516, 517,
                518, 519, 520, 521, 525, 548, 549 };

        for (int i : punishments) {
            init();
            new Preset(mine).submitted().set(i);

            mine.playFrom(new ActionRange(845, 846));

            assertScriptEndedGracefully();
            assertEquals("Good end", ScriptState.SET, mine.state.get(851));
        }
    }

    protected void assertScriptEndedGracefully() {
        assertEquals("All actions set - see console output for offending action", ScriptState.UNSET,
                mine.state.get(861));
        assertEquals("Save and quit", ScriptState.SET, mine.state.get(898));
    }
}
