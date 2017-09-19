package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.model.Action;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;

@RunWith(Parameterized.class)
public class MineMaidTest {
    private static final int POSITION_TO_SELECT_OFFSET = 1000;

    @Parameters(name = "Position {0} @ difficulty={1}")
    public static Iterable<Integer[]> data()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        mine = new Preset().submitted().responses(MinePrompts.all()).mine(Mine.MAID);

        List<Integer[]> positions = new ArrayList<Integer[]>();
        add(positions, new ActionRange(400, 499), 0);
        add(positions, new ActionRange(500, 574), 1);
        add(positions, new ActionRange(575, 649), 2);
        add(positions, new ActionRange(650, 699), 3);

        return positions;
    }

    private static void add(List<Integer[]> positions, ActionRange range, int difficulty) {
        List<Action> actions = mine.script.actions.getAll(
                new ActionRange(range.start + POSITION_TO_SELECT_OFFSET, range.end + POSITION_TO_SELECT_OFFSET));
        for (Action action : actions) {
            positions.add(new Integer[] { action.number - POSITION_TO_SELECT_OFFSET, difficulty });
        }
    }

    static final int ENABLE_MINE_MAID_DEBUGGING = 121;

    final int position;
    final int difficulty;

    private static Mine mine;

    public MineMaidTest(int position, int difficulty) {
        this.position = position;
        this.difficulty = difficulty;
    }

    @Test
    public void testActivityPosition() throws AllActionsSetException, ScriptExecutionException {
        // Exclude save range
        mine.state.resetRange(new ActionRange(0, 389));
        mine.state.resetRange(new ActionRange(700, 9999));

        mine.state.set(100);
        mine.state.set(110 + difficulty);
        mine.state.set(ENABLE_MINE_MAID_DEBUGGING);

        Action startAction = mine.script.actions.get(position + 1000);
        Assume.assumeFalse("Position " + position + " not implemented yet", startAction == null);

        List<Action> positionAction = mine.range(new ActionRange(position + POSITION_TO_SELECT_OFFSET));
        Assume.assumeFalse("Position " + position + " not available", positionAction.isEmpty());
        assertEquals(1, positionAction.size());

        playPosition(positionAction.get(0));

        // Expect a good ending
        assertEquals("Script not ended as expected:", ScriptState.SET, mine.state.get(9950));

        // Expect position to be completed
        assertEquals("Position not completed:", ScriptState.SET, mine.state.get(position));
    }

    // TODO Test punishment position
    // TODO Test positions that require a lot of toys - analyze action for items?

    // TODO Test punishment position failing -> leads to retry with an easier but longer position, eventually leads to
    // AllActionsSet

    private static void playPosition(Action positionAction) throws ScriptExecutionException, AllActionsSetException {
        selectSinglePosition(positionAction);
        mine.playRange(new ActionRange(1, 9950));
        assertThatAllActionsSetDidntOccur(mine);
    }

    private static void selectSinglePosition(Action positionAction)
            throws AllActionsSetException, ScriptExecutionException {
        mine.state.overwrite(148, ScriptState.UNSET);

        ActionRange allPositionsRange = new ActionRange(1400, 1699);
        List<Action> allPositions = mine.script.actions.getAll(allPositionsRange);

        mine.play(new ActionRange(1219), new ActionRange(1000, 1219));
        selectPositionsBydisablingllUnwanted(positionAction, allPositionsRange, allPositions);
        mine.playRange(new ActionRange(1000, 3999));
        undoDisableUnwantedPositions(positionAction, allPositions);

        assertEquals(0, mine.range(new ActionRange(positionAction.number)).size());
    }

    public static void selectPositionsBydisablingllUnwanted(Action positionAction, ActionRange allPositionsRange,
            List<Action> allPositions) throws ScriptExecutionException {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.set(action);
            }
        }
        assertEquals(1, mine.range(allPositionsRange).size());
        assertEquals(1, mine.range(new ActionRange(positionAction.number)).size());
    }

    public static void undoDisableUnwantedPositions(Action positionAction, List<Action> allPositions) {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.unset(action.number);
            }
        }
    }

    private static void assertThatAllActionsSetDidntOccur(Mine mine) {
        assertNotEquals("All actions set handler invoked", new ActionRange(9999), mine.range);
    }
}
