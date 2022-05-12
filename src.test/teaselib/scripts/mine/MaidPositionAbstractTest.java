package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.AssumptionViolatedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pcm.controller.AllActionsSetException;
import pcm.model.Action;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.Condition;
import pcm.state.conditions.ItemCondition;
import pcm.state.conditions.Must;
import pcm.state.conditions.MustNot;
import pcm.state.persistence.ScriptState;
import pcm.util.TestPlayer;
import teaselib.Bondage;
import teaselib.Household;
import teaselib.Toys;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.test.Preset;
import teaselib.scripts.mine.test.PresetTestable;

@RunWith(Parameterized.class)
public abstract class MaidPositionAbstractTest extends PresetTestable {

    static final Enum<?>[] TOYS = { Toys.Nipple_Clamps, Toys.Pussy_Clamps, Toys.Collar, Toys.Gag,
            Bondage.Wrist_Restraints, Bondage.Ankle_Restraints, Household.Clothes_Pegs, Toys.Blindfold, Toys.Dildo };

    static final int POSITION_TO_SELECT_OFFSET = 1000;

    static final int ENABLE_MINE_MAID_DEBUGGING = 121;
    static final int POSITION_NOT_CONTINUEABLE = 146;
    static final int MAID_TRAINING_PUNISHMENT_FLAG = 179;
    static final int SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED = 277;

    static Mine createPlayer(Preset preset, Collection<ResponseAction> responses, boolean toysAvailability)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(responses).mine();
        setToysAvailable(mine, toysAvailability);
        return mine;
    }

    static void setToysAvailable(Mine mine, boolean toysAvailable) {
        for (Enum<?> toy : TOYS) {
            mine.item(toy).setAvailable(toysAvailable);
        }
    }

    static Iterable<Integer[]> positions(Mine mine) {
        List<Integer[]> positions = new ArrayList<>();
        add(mine, positions, new ActionRange(400, 499), 0);
        add(mine, positions, new ActionRange(500, 574), 1);
        add(mine, positions, new ActionRange(575, 649), 2);
        add(mine, positions, new ActionRange(650, 699), 3);
        return positions;
    }

    static void add(Mine mine, List<Integer[]> positions, ActionRange range, int difficulty) {
        List<Action> actions = mine.script.actions.getAll(
                new ActionRange(range.start + POSITION_TO_SELECT_OFFSET, range.end + POSITION_TO_SELECT_OFFSET));
        for (Action action : actions) {
            positions.add(new Integer[] { action.number - POSITION_TO_SELECT_OFFSET, difficulty });
        }
    }

    Mine mine;
    final int position;
    final int difficulty;

    public MaidPositionAbstractTest(Collection<ResponseAction> responses, boolean toyAvaiability, int position,
            int difficulty) throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        super();
        this.mine = createPlayer(preset, responses, toyAvaiability);
        this.position = position;
        this.difficulty = difficulty;
    }

    Action checkPositionAvailable() throws AssertionError {
        Action startAction = mine.script.actions.get(position + POSITION_TO_SELECT_OFFSET);
        if (startAction == null) {
            throw new AssumptionViolatedException("Position " + position + " not implemented yet");
        }

        if (startAction.conditions.contains(new Must(MAID_TRAINING_PUNISHMENT_FLAG))) {
            mine.state.set(MAID_TRAINING_PUNISHMENT_FLAG);
        } else {
            mine.state.unset(MAID_TRAINING_PUNISHMENT_FLAG);
        }

        if (startAction.conditions.contains(new Must(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED))) {
            preset.strictSelfBondage(2);
            mine.state.set(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED);
        } else {
            preset.strictSelfBondage(0);
            mine.state.unset(SEMI_SAFE_SELF_BONDAGE_SCENARIOS_ENABLED);
        }

        mine.state.set(POSITION_NOT_CONTINUEABLE);

        List<Action> positionActions = mine.range(new ActionRange(startAction.number));
        if (positionActions.isEmpty()) {
            startAction.conditions.stream().filter(x -> x instanceof ItemCondition).forEach(x -> {
                ((ItemCondition) x).items().stream().forEach(item -> {
                    mine.item(item).apply();
                });
            });
            positionActions = mine.range(new ActionRange(startAction.number));
        }

        if (positionActions.isEmpty()) {
            startAction.conditions.stream().filter(c -> (c instanceof Must)).forEach(m -> {
                ((Must) m).stream().forEach(n -> {
                    mine.state.set(n);
                });
            });

            startAction.conditions.stream().filter(c -> (c instanceof MustNot)).forEach(m -> {
                ((MustNot) m).stream().forEach(n -> {
                    mine.state.set(n);
                });
            });
            positionActions = mine.range(new ActionRange(startAction.number));
        }

        if (positionActions.isEmpty()) {
            List<Condition> unmatchedconditions = TestPlayer.umatchedConditions(startAction, mine.state);
            fail("Position " + position + " not available: " + TestPlayer.toString(unmatchedconditions));
        }

        assertEquals(1, positionActions.size());
        return positionActions.get(0);
    }

    void selectSinglePosition(Action positionAction) throws AllActionsSetException, ScriptExecutionException {
        mine.state.overwrite(148, ScriptState.UNSET);

        ActionRange allPositionsRange = new ActionRange(1400, 1699);
        List<Action> allPositions = mine.script.actions.getAll(allPositionsRange);

        mine.play(new ActionRange(1219), new ActionRange(1000, 1219));
        selectPositionsByDisablingUnwanted(positionAction, allPositionsRange, allPositions);
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

    void selectPositionsByDisablingUnwanted(Action positionAction, ActionRange allPositionsRange,
            List<Action> allPositions) throws ScriptExecutionException {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.set(action);
            }
        }
        assertEquals(1, mine.range(allPositionsRange).size());
        assertEquals(1, mine.range(new ActionRange(positionAction.number)).size());
    }

    void undoDisableUnwantedPositions(Action positionAction, List<Action> allPositions) {
        for (Action action : allPositions) {
            if (action.number != positionAction.number) {
                mine.state.unset(action.number);
            }
        }
    }

    void assertToysRemoved() {
        var applied = mine.items(TOYS).getApplied();
        for (var item : applied) {
            assertTrue("Toy(s) still applied: " + applied, item.applied() && !item.is(mine.namespaceApplyAttribute));
        }
    }
}
