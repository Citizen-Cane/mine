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

        // Introduction
        debugger.addResponse("Yes Miss, I am", Debugger.Response.Choose);
        debugger.addResponse("Yes, I'm ready, Miss", Debugger.Response.Choose);

        // Questionaire
        debugger.addResponse("Yes Miss, please train my ass", Debugger.Response.Choose);
        debugger.addResponse("Yes Miss, please keep me chaste", Debugger.Response.Choose);
        debugger.addResponse("Yes, I'm prepared, Miss", Debugger.Response.Choose);
        debugger.addResponse("Yes Miss, I'm aware of the risk", Debugger.Response.Choose);

        // Level
        debugger.addResponse("Yes, Miss, I have some tolerance already", Debugger.Response.Choose);
        debugger.addResponse("Quite a bit harsher, please", Debugger.Response.Choose);
        debugger.addResponse("Harshly, please", Debugger.Response.Choose);

        debugger.addResponse("Yes Miss, I'm naked right now", Debugger.Response.Choose);
        debugger.addResponse("Yes Miss, I was properly naked", Debugger.Response.Choose);

        debugger.addResponse("Yes Miss, I have", Debugger.Response.Choose);

        debugger.addResponse("Yes Miss, I've been naughty", Debugger.Response.Choose);
        debugger.addResponse("Please Miss, Punish me!", Debugger.Response.Choose);

        debugger.addResponse("Yes, Miss, I do", Debugger.Response.Choose);

        // TODO sub script responses - better define them elsewhere to avoid
        // code duplication

        // mine-td
        // TODO leads to cum question - shouldn't
        debugger.addResponse("I've spurted off, Miss", Debugger.Response.Choose);

        debugger.addResponse("Yes, please let me cum, Miss", Debugger.Response.Choose);

        debugger.addResponse("Yes Miss, I obeyed you all the time", Debugger.Response.Choose);

        // Defaults
        debugger.addResponse("Yes, Miss", Debugger.Response.Choose);

        debugger.addResponse("Exit", Debugger.Response.Choose);

        mine = new Mine(teaseLib, new File("../SexScripts/scripts/"));
        mine.loadScript("Mine");
    }

    private Mine mine;

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException {
        mine.playFrom(new ActionRange(845, 846));

        assertEquals("All actions set", ScriptState.UNSET, mine.state.get(861));

        assertEquals("Good end", ScriptState.SET, mine.state.get(851));

        assertEquals("Save and quit", ScriptState.SET, mine.state.get(898));
    }
}
