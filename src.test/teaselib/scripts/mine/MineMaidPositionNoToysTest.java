package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.controller.Player;
import pcm.model.Action;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.Condition;
import pcm.state.conditions.Must;
import pcm.state.persistence.ScriptState;
import teaselib.Household;
import teaselib.Toys;
import teaselib.core.debug.DebugPersistence;

/**
 * @author Citizen-Cane
 *
 */
@RunWith(Parameterized.class)
public class MineMaidPositionNoToysTest {
    private static final int MAID_TRAINING_USER_DOESNT_HAVE_EQUIPMENT = 9730;
    private static final int POSITION_NOT_CONTINUEABLE = 146;
    private static final int MAID_TRAINING_PUNISHMENT_FLAG = 179;
    private static final int SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED = 277;

    private static final int POSITION_TO_SELECT_OFFSET = 1000;

    private static final Enum<?>[] TOYS = { Toys.Collar, Toys.Gag, Toys.Wrist_Restraints, Toys.Ankle_Restraints,
            Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs, Toys.Blindfold };

    private static DebugPersistence.Storage storage = new DebugPersistence.Storage();

    @Parameters(name = "Position {0} @ difficulty={1}")
    public static Iterable<Integer[]> data()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {

        Mine mine = createPlayer(new Preset());

        List<Integer[]> positions = new ArrayList<>();
        add(mine, positions, new ActionRange(400, 499), 0);
        add(mine, positions, new ActionRange(500, 574), 1);
        add(mine, positions, new ActionRange(575, 649), 2);
        add(mine, positions, new ActionRange(650, 699), 3);

        return positions;
    }

    public static Mine createPlayer(Preset preset)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidNoToys()).mine();

        for (Enum<?> toy : TOYS) {
            mine.item(toy).setAvailable(true);
        }

        return mine;
    }

    private static void add(Mine mine, List<Integer[]> positions, ActionRange range, int difficulty) {
        List<Action> actions = mine.script.actions.getAll(
                new ActionRange(range.start + POSITION_TO_SELECT_OFFSET, range.end + POSITION_TO_SELECT_OFFSET));
        for (Action action : actions) {
            positions.add(new Integer[] { action.number - POSITION_TO_SELECT_OFFSET, difficulty });
        }
    }

    static final int ENABLE_MINE_MAID_DEBUGGING = 121;

    final int position;
    final int difficulty;

    Preset preset;
    Mine mine;

    public MineMaidPositionNoToysTest(int position, int difficulty)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        this.preset = new Preset(storage);
        this.mine = createPlayer(preset);

        this.position = position;
        this.difficulty = difficulty;
    }

    @Test
    public void testActivityPositionNoToys() throws AllActionsSetException, ScriptExecutionException {
        mine.state.resetRange(new ActionRange(0, 389));
        mine.state.resetRange(new ActionRange(700, 9999));

        mine.state.setRange(new ActionRange(400, 699));

        mine.state.set(100);
        mine.state.set(110 + difficulty);
        mine.state.set(ENABLE_MINE_MAID_DEBUGGING);

        List<Action> positionActions = checkPositionAvailable(mine);

        mine.breakPoints.add(Mine.MAID, MAID_TRAINING_USER_DOESNT_HAVE_EQUIPMENT);
        mine.breakPoints.add(Mine.MAID, 9981);

        selectSinglePosition(positionActions.get(0));
        mine.playRange(new ActionRange(1, 3999));
        mine.playRange(new ActionRange(4000, 6999));

        assertTrue(mine.range.start >= 7700 || mine.range.start == 1820);
    }

    public List<Action> checkPositionAvailable(Player mine) throws AssertionError {
        Action startAction = mine.script.actions.get(position + 1000);
        assertTrue("Position " + position + " not implemented yet", startAction != null);

        Action action = getExecutablePositionAction();

        List<Action> positionActions = mine.range(new ActionRange(position + POSITION_TO_SELECT_OFFSET));

        if (positionActions.isEmpty()) {
            for (Enum<?> toy : TOYS) {
                if (position != 641 || toy != Toys.Ankle_Restraints)
                    mine.item(toy).apply();
            }
            positionActions = mine.range(new ActionRange(position + POSITION_TO_SELECT_OFFSET));
        }

        if (positionActions.isEmpty()) {
            List<Condition> unmatchedconditions = pcm.util.TestUtils.umatchedConditions(action, mine.state);
            throw new AssertionError(
                    "Position " + position + " not available: " + pcm.util.TestUtils.toString(unmatchedconditions));
        } else {
            assertEquals(1, positionActions.size());
        }
        return positionActions;
    }

    public Action getExecutablePositionAction() {
        Action action = mine.script.actions.get(position + POSITION_TO_SELECT_OFFSET);

        if (action.conditions.contains(new Must(MAID_TRAINING_PUNISHMENT_FLAG))) {
            mine.state.set(MAID_TRAINING_PUNISHMENT_FLAG);
        } else {
            mine.state.unset(MAID_TRAINING_PUNISHMENT_FLAG);
        }

        if (action.conditions.contains(new Must(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED))) {
            preset.strictSelfBondage(2);
            mine.state.set(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED);
        } else {
            preset.strictSelfBondage(0);
            mine.state.unset(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED);
        }

        mine.state.set(POSITION_NOT_CONTINUEABLE);
        return action;
    }

    private void selectSinglePosition(Action positionAction) throws AllActionsSetException, ScriptExecutionException {
        mine.state.overwrite(148, ScriptState.UNSET);

        ActionRange allPositionsRange = new ActionRange(1400, 1699);
        List<Action> allPositions = mine.script.actions.getAll(allPositionsRange);

        mine.play(new ActionRange(1219), new ActionRange(1000, 1219));
        selectPositionsBydisablingllUnwanted(positionAction, allPositionsRange, allPositions);
        mine.playRange(new ActionRange(1000, 3999));

        undoDisableUnwantedPositions(positionAction, allPositions);

        assertEquals(0, mine.range(new ActionRange(positionAction.number)).size());
    }

    static int forward(int n) {
        if (n == 648)
            return 519;
        else if (n == 649)
            return 520;
        else
            return n;
    }

    public void selectPositionsBydisablingllUnwanted(Action positionAction, ActionRange allPositionsRange,
            List<Action> allPositions) throws ScriptExecutionException {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.set(action);
            }
        }
        assertEquals(1, mine.range(allPositionsRange).size());
        assertEquals(1, mine.range(new ActionRange(positionAction.number)).size());
    }

    public void undoDisableUnwantedPositions(Action positionAction, List<Action> allPositions) {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.unset(action.number);
            }
        }
    }
}
