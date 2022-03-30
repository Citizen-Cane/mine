package teaselib.scripts.mine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.model.Action;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.conditions.Must;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.Preset;

/**
 * @author Citizen-Cane
 *
 */

public class MineMaidPositionNoToysTest extends MaidPositionAbstractTest {

    private static final List<Integer> POSITIONS_THAT_REQUIRE_TOYS = Arrays.asList(642, 643, 644, 645, 646, 647, 650);

    private static final int MAID_TRAINING_USER_DOESNT_HAVE_EQUIPMENT = 9730;

    @Parameters(name = "Position {0} @ difficulty={1}")
    public static Iterable<Integer[]> data()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = createPlayer(new Preset(), MinePrompts.maidNoToys(), false);
        return positions(mine);
    }

    public MineMaidPositionNoToysTest(int position, int difficulty)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        super(MinePrompts.maidNoToys(), false, position, difficulty);
        assumeFalse("Position requires toys", POSITIONS_THAT_REQUIRE_TOYS.contains(position));
    }

    @Test
    public void testActivityPositionNoToys() throws AllActionsSetException, ScriptExecutionException {
        mine.state.resetRange(new ActionRange(0, 389));
        mine.state.resetRange(new ActionRange(700, 9999));
        mine.state.setRange(new ActionRange(400, 699));
        mine.state.set(100);
        mine.state.set(110 + difficulty);
        mine.state.set(ENABLE_MINE_MAID_DEBUGGING);

        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(MAID_TRAINING_USER_DOESNT_HAVE_EQUIPMENT));
        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(9981));

        Action startAction = mine.script.actions.get(position + POSITION_TO_SELECT_OFFSET);
        Assume.assumeFalse("Position " + position + " not implemented yet", startAction == null);

        Action positionAction = checkPositionAvailable();
        selectSinglePosition(positionAction);
        mine.playRange(new ActionRange(1, 3999));
        mine.playRange(new ActionRange(4000, 6999));
        assertTrue(mine.action.number >= 7700 || mine.action.number == 1820);
        assertToysRemoved();
    }

    Action getExecutablePositionAction() {
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

}
