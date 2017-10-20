package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import pcm.controller.AllActionsSetException;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;

public class MineFirstRunTest {
    private Mine mine;

    @Test
    public void testFirstTime() throws AllActionsSetException, ScriptExecutionException, IOException,
            ScriptParsingException, ValidationIssue {
        mine = new Preset().script(Mine.MAIN).responses(MinePrompts.all()).mine();
        mine.playFrom(new ActionRange(845, 846));

        assertScriptEndedGracefully();
        assertEquals("Good end", ScriptState.SET, mine.state.get(851));
    }

    @Test
    public void testSubmitted() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        mine = new Preset().script(Mine.MAIN).submitted().responses(MinePrompts.all()).mine();
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
