package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.PresetTestable;
import teaselib.util.Interval;

@RunWith(Parameterized.class)
public class MaidTrainingPunishmentTest extends PresetTestable {
    private static final int MAID_TRAINIMG_EXCESSIVE_DISOBEDIENCE_PUNISHMENT = 1003;
    private static final int MAID_TRAINIMG_FAILURE_1_PUNISHMENT = 1002;
    private static final int MAID_TRAINIMG_FAILURE_2_PUNISHMENT = 1005;

    private static final int MAID_POSITION_SUCCESS = 8300;
    private static final int PUNISHMENT_FINAL_FAILURE = 9769;
    private static final int MAID_TRAINING_GOOD_END = 9950;

    @Parameters(name = "Punishment acceptance={0}")
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
        testMaidTrainingGoodEnd(MAID_TRAINIMG_EXCESSIVE_DISOBEDIENCE_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure1GoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingGoodEnd(MAID_TRAINIMG_FAILURE_1_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure2GoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingGoodEnd(MAID_TRAINIMG_FAILURE_2_PUNISHMENT);
    }

    private void testMaidTrainingGoodEnd(int trainigFailure2) throws ScriptParsingException, ValidationIssue,
            ScriptExecutionException, IOException, AllActionsSetException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidGood()).mine();
        mine.play(new ActionRange(trainigFailure2), new ActionRange(1000, 9950));
        assertEquals(ScriptState.SET, mine.state.get(MAID_TRAINING_GOOD_END));
    }

    @Test
    public void testExcessiveDisobediencePunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINIMG_EXCESSIVE_DISOBEDIENCE_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure1PunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINIMG_FAILURE_1_PUNISHMENT);
    }

    @Test
    public void testTrainingFailure2PunishmentFinalFailure()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        testMaidTrainingPunishmentFinalFailure(MAID_TRAINIMG_FAILURE_2_PUNISHMENT);
    }

    public void testMaidTrainingPunishmentFinalFailure(int start)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidGivingUp())
                .punishmentAcceptance(punishmentAcceptance).mine();
        MaidPositionAbstractTest.setToysAvailable(mine, true);

        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(PUNISHMENT_FINAL_FAILURE));
        mine.breakPoints.add(Mine.MAID, mine.script.actions.get(MAID_POSITION_SUCCESS));

        mine.play(new ActionRange(start), new ActionRange(1000, 9950));

        assertEquals(mine.script.actions.get(PUNISHMENT_FINAL_FAILURE), mine.action);
        assertEquals(mine.script.actions.get(PUNISHMENT_FINAL_FAILURE), mine.action);

        assertFalse("Toy(s) still applied: " + mine.items(MaidPositionAbstractTest.TOYS).getApplied(),
                mine.items(MaidPositionAbstractTest.TOYS).anyApplied());
    }

}
