package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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

@RunWith(Parameterized.class)
public class MinePunishments {
    @Parameters(name = "Punishment {0}")
    public static Iterable<Integer> data() {
        List<Integer> punishments = Arrays.asList(500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513,
                514, 515, 516, 517, 518, 519, 520, 521, 525, 548, 549);
        return punishments;
    }

    final int punishment;

    public MinePunishments(int punishment) {
        this.punishment = punishment;
    }

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
    public void testPunishments() throws AllActionsSetException, ScriptExecutionException {
        new Preset(mine).submitted().set(punishment);

        mine.playFrom(new ActionRange(845, 846));

        assertScriptEndedGracefully();
        assertEquals("Good end", ScriptState.SET, mine.state.get(851));
    }

    protected void assertScriptEndedGracefully() {
        assertEquals("All actions set - see console output for offending action", ScriptState.UNSET,
                mine.state.get(861));
        assertEquals("Save and quit", ScriptState.SET, mine.state.get(898));
    }
}
