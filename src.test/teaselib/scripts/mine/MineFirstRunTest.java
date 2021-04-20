package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import pcm.controller.AllActionsSetException;
import pcm.controller.CheckPointTrigger;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.PresetTestable;
import teaselib.scripts.mine.test.Triggers;

public class MineFirstRunTest extends PresetTestable {
    private Mine mine;

    Triggers triggers = new Triggers(new CheckPointTrigger("good end", 850, true),
            new CheckPointTrigger("saved", 898, true),
            new CheckPointTrigger("All actions set - see console output for offending action", 860, false));

    public MineFirstRunTest() throws IOException {
    }

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException, IOException,
            ScriptParsingException, ValidationIssue {
        mine = preset.script(Mine.MAIN).responses(MinePrompts.all()).mine();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();

        assertScriptEndedGracefully();
    }

    @Test
    public void testSubmitted() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        mine = preset.script(Mine.MAIN).submitted().responses(MinePrompts.all()).mine();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();

        assertScriptEndedGracefully();
    }

    protected void assertScriptEndedGracefully() {
        assertEquals("All actions set - see console output for offending action", ScriptState.SET, mine.state.get(850));
    }

}
