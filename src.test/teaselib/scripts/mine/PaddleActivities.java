package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Household;
import teaselib.Toys;
import teaselib.scripts.mine.test.ActivityTest;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.TestParameters;

/**
 * @author Citizen-Cane
 *
 */
@RunWith(Parameterized.class)
public class PaddleActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Toys.Rope, Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs,
            Toys.Gag };

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("bad introduction (500): corner (10-15 min) + marker", new ActionRange(1000, 9822),
                2500, Arrays.asList(TOYS)));

        return tests;
    }

    public PaddleActivities(TestParameters testParameters)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super(testParameters, Mine.PADDLE, MinePrompts.paddle());
    }
}
