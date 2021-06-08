package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;

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
    Triggers triggers;

    public MineFirstRunTest() throws IOException {
    }

    private Triggers triggers() {
        return new Triggers(new CheckPointTrigger("good end", mine.script.actions.get(850), true),
                new CheckPointTrigger("saved", mine.script.actions.get(898), true),
                new CheckPointTrigger("All actions set - see console output for offending action",
                        mine.script.actions.get(860), false));
    }

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException, IOException,
            ScriptParsingException, ValidationIssue {
        mine = preset.script(Mine.MAIN).responses(MinePrompts.all()).mine();
        triggers = triggers();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();

        assertScriptEndedGracefully();
    }

    @Test
    public void testSubmitted() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        mine = preset.script(Mine.MAIN).submitted().responses(MinePrompts.all()).mine();
        triggers = triggers();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();

        assertScriptEndedGracefully();
    }

    protected void assertScriptEndedGracefully() {
        assertEquals("All actions set - see console output for offending action", ScriptState.SET, mine.state.get(850));
    }

}
