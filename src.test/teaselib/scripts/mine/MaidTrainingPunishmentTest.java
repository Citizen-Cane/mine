package teaselib.scripts.mine;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;
import pcm.controller.AllActionsSetException;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.PresetTestable;
import teaselib.util.Interval;


@ParameterizedClass(name = "Punishment acceptance={0}")
@MethodSource("data")
public class MaidTrainingPunishmentTest extends PresetTestable {

    private static final int MAID_TRAINING_EXCESSIVE_DISOBEDIENCE_PUNISHMENT = 1003;
    private static final int MAID_TRAINING_FAILURE_1_PUNISHMENT = 1002;
    private static final int MAID_TRAINING_FAILURE_2_PUNISHMENT = 1005;

    private static final int MAID_POSITION_SUCCESS = 8300;
    private static final int PUNISHMENT_FINAL_FAILURE = 9769;
    private static final int MAID_TRAINING_GOOD_END = 9950;

    public static Iterable<Integer> data() {
        return new Interval(0, 5);
    }

    final int punishmentAcceptance;

    public MaidTrainingPunishmentTest(int punishmentAcceptance) throws IOException {
        this.punishmentAcceptance = punishmentAcceptance;
    }

    @Test
    public void testExcessiveDisobedienceGoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingGoodEnd(MAID_TRAINING_EXCESSIVE_DISOBEDIENCE_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure1GoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingGoodEnd(MAID_TRAINING_FAILURE_1_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure2GoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingGoodEnd(MAID_TRAINING_FAILURE_2_PUNISHMENT);
    }

    private void testMaidTrainingGoodEnd(int trainigFailure2) throws ScriptParsingException, ValidationIssue,
            ScriptExecutionException, IOException, AllActionsSetException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidGood()).mine();
        mine.play(new ActionRange(trainigFailure2), new ActionRange(1000, 9950));
        Assertions.assertEquals(ScriptState.SET, mine.state.get(MAID_TRAINING_GOOD_END));
    }

    @Test
    public void testExcessiveDisobediencePunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINING_EXCESSIVE_DISOBEDIENCE_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure1PunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINING_FAILURE_1_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure2PunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINING_FAILURE_2_PUNISHMENT);
    }

    public void testMaidTrainingPunishmentFinalFailure(int start)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidGivingUp())
                .punishmentAcceptance(punishmentAcceptance).mine();
        MaidPositionAbstractTest.setToysAvailable(mine, true);

        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(PUNISHMENT_FINAL_FAILURE));
        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(MAID_POSITION_SUCCESS));

        mine.play(new ActionRange(start), new ActionRange(1000, 9950));

        Assertions.assertEquals(mine.script.actions.get(PUNISHMENT_FINAL_FAILURE), mine.action);
        Assertions.assertEquals(mine.script.actions.get(PUNISHMENT_FINAL_FAILURE), mine.action);

        Assertions.assertFalse(
                mine.items(MaidPositionAbstractTest.TOYS).anyApplied(),
                "Toy(s) still applied: "
                        + mine.items(MaidPositionAbstractTest.TOYS).getApplied());
    }

}
