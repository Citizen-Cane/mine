package teaselib.scripts.mine;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.util.Interval;

@RunWith(Parameterized.class)
public class MaidTrainingPunishmentTest {
    private static final int PUNISHMENT_FINAL_FAILURE = 9769;

    @Parameters(name = "Punishment acceptance={0}")
    public static Iterable<Integer> data() {
        return new Interval(0, 5);
    }

    final int punishmentAcceptance;

    public MaidTrainingPunishmentTest(int punishmentAcceptance) {
        this.punishmentAcceptance = punishmentAcceptance;
    }

    @Test
    public void testTrainingFailure2GoodEnd()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = new Preset().script(Mine.MAID).responses(MinePrompts.maidGood()).mine();

        mine.play(new ActionRange(1005), new ActionRange(1000, 9950));

        assertEquals(ScriptState.SET, mine.state.get(9950));
    }

    @Test
    public void testTrainingFailure2Fallthrough()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = new Preset().script(Mine.MAID).responses(MinePrompts.maidGivingUp())
                .punishmentAcceptance(punishmentAcceptance).mine();

        mine.breakPoints.add(Mine.MAID, PUNISHMENT_FINAL_FAILURE);
        mine.breakPoints.add(Mine.MAID, 8300);

        mine.play(new ActionRange(1005), new ActionRange(1000, 9950));

        // TODO sometimes ends up with successful punishment - wrong
        // TODO Validate scripts after building jars - prevents failing tests due to parsing errors

        assertEquals(PUNISHMENT_FINAL_FAILURE, mine.range.start);
        assertEquals(PUNISHMENT_FINAL_FAILURE, mine.range.end);
    }
}
