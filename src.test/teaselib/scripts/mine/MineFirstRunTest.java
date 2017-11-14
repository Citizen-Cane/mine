package teaselib.scripts.mine;

import java.io.IOException;

import org.junit.Test;

import pcm.controller.AllActionsSetException;
import pcm.controller.Trigger;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.Preset;
import teaselib.scripts.mine.test.Triggers;

public class MineFirstRunTest {
    private Mine mine;

    Triggers triggers = new Triggers(new Trigger("good end", 850, true), new Trigger("saved", 898, true),
            new Trigger("All actions set - see console output for offending action", 860, false));

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException, IOException,
            ScriptParsingException, ValidationIssue {
        mine = new Preset().script(Mine.MAIN).responses(MinePrompts.all()).mine();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();
    }

    @Test
    public void testSubmitted() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        mine = new Preset().script(Mine.MAIN).submitted().responses(MinePrompts.all()).mine();

        mine.breakPoints.add(Mine.MAIN, triggers);
        mine.playFrom(new ActionRange(845, 846));

        triggers.assertExpected();
    }
}
