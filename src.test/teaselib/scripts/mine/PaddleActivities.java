package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Bondage;
import teaselib.Household;
import teaselib.Toys;
import teaselib.scripts.mine.test.ActivityTest;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.TestParameters;

/**
 * @author Citizen-Cane
 *
 */
public class PaddleActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Bondage.Rope, Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs,
            Toys.Gag };

    public static List<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("bad introduction (500): corner (10-15 min) + marker", new ActionRange(1000, 9822),
                2500, Arrays.asList(TOYS)));

        return tests;
    }

    public PaddleActivities()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super(Mine.PADDLE, MinePrompts.paddle());
    }

    @ParameterizedTest(name = "activity {0}")
    @MethodSource("tests")
    public void executeActivityTest(TestParameters parameters) throws ScriptExecutionException {
        super.testWith(parameters);
    }

}
