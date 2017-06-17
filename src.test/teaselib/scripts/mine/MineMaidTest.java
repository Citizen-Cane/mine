package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.core.Debugger;
import teaselib.core.TeaseLib;
import teaselib.hosts.DummyHost;
import teaselib.hosts.DummyPersistence;

public class MineMaidTest {
    static final int ENABLE_MINE_MAID_DEBUGGING = 121;

    @Test
    public void testRandomPosition()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Debugger debugger = new Debugger(new DummyHost(), new DummyPersistence());

        TeaseLib teaseLib = debugger.teaseLib;

        Mine mine = new Mine(teaseLib, new File("../SexScripts/scripts/"));

        debugger.freezeTime();

        debugger.addResponse("*spurted*", Debugger.Response.Ignore);
        debugger.addResponse("*give*", Debugger.Response.Ignore);
        debugger.addResponse("Exit", Debugger.Response.Choose);

        mine.loadScript("mine-maid");
        mine.state.set(ENABLE_MINE_MAID_DEBUGGING);
        mine.play(new ActionRange(1000), new ActionRange(1000, 9950));

        // Expect a good ending
        assertEquals(ScriptState.SET, mine.state.get(9950));
    }

}
