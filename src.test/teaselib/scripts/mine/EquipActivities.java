package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.test.ActivityTest;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.TestParameters;

/**
 * @author Citizen-Cane
 *
 */
public class EquipActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = {};

    public static List<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("Chains", 2100, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Nipple clamps", 2200, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Clothes pegs", 2300, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Wrist restraints", 2400, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Ankle restraints", 2500, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Collar", 2600, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Rope", 2700, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Gag", 2800, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Butt plug", 2900, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Crop and paddle", 3000, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Chastity cage", 3100, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Humbler", 3200, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Ball harness", 3300, 9001, Arrays.asList(TOYS)));

        tests.add(new TestParameters("Enema kit", 3500, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Blindfold", 3600, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Dildo", 3700, 9001, Arrays.asList(TOYS),
                new ResponseAction("To fuck my mouth, Miss")));
        tests.add(new TestParameters("Vibrator", 3800, 9001, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Pussy clamps", 3900, 9001, Arrays.asList(TOYS)));

        return tests;
    }

    public EquipActivities()
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super( Mine.EQUIP, MinePrompts.sb());
    }

    @ParameterizedTest(name = "activity {0}")
    @MethodSource("tests")
    public void executeActivityTest(TestParameters parameters) throws ScriptExecutionException {
        super.testWith(parameters);
    }

}
