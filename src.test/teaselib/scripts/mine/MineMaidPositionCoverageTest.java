package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.model.Action;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.Preset;

public class MineMaidPositionCoverageTest extends MaidPositionAbstractTest {

    @Parameters(name = "Position {0} @ difficulty={1}")
    public static Iterable<Integer[]> data()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = createPlayer(new Preset(), MinePrompts.maidGood(), true);
        return positions(mine);
    }

    public MineMaidPositionCoverageTest(int position, int difficulty)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        super(MinePrompts.maidGood(), true, position, difficulty);
    }

    @Test
    public void testActivityPosition() throws AllActionsSetException, ScriptExecutionException {
        mine.state.resetRange(new ActionRange(0, 389));
        mine.state.resetRange(new ActionRange(700, 9999));
        mine.state.setRange(new ActionRange(400, 699));
        mine.state.set(100);
        mine.state.set(110 + difficulty);
        mine.state.set(ENABLE_MINE_MAID_DEBUGGING);

        Action startAction = mine.script.actions.get(position + 1000);
        Assume.assumeFalse("Position " + position + " not implemented yet", startAction == null);

        Action positionAction = checkPositionAvailable();
        selectSinglePosition(positionAction);
        mine.playRange(new ActionRange(1, 9950));
        assertThatAllActionsSetDidntOccur(mine);
        assertEquals("Script not ended as expected:", ScriptState.SET, mine.state.get(9950));
        assertEquals("Position not completed:", ScriptState.SET, mine.state.get(position));
        assertToysRemoved();
    }

    private static void assertThatAllActionsSetDidntOccur(Mine mine) {
        assertNotEquals("All actions set handler invoked", 9999, mine.action.number);
    }
}
