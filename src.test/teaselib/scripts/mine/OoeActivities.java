package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
public class OoeActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs, Toys.Gag };

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("Generic game, random rules", 1100, 9502, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Nipple torture game", 1102, 9502, Arrays.asList(TOYS)));
        return tests;
    }

    public OoeActivities(TestParameters testParameters)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super(testParameters, Mine.OOE, MinePrompts.ooe());
    }
}
