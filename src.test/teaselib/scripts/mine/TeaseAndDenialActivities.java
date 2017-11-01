/**
 * 
 */
package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Household;
import teaselib.Toys;
import teaselib.core.Debugger.Response;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.Preset;

/**
 * @author Citizen-Cane
 *
 */
@RunWith(Parameterized.class)
public class TeaseAndDenialActivities {
    private static final Enum<?>[] TOYS = { Toys.Collar, Toys.Gag, Toys.Wrist_Restraints, Toys.Ankle_Restraints,
            Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs, Toys.Blindfold };

    static class TestParameters {
        public final String name;
        public final int start;
        public final int end;

        public final List<ResponseAction> responseActions;
        public final List<Enum<?>> toys;

        public TestParameters(String name, int start, int end, List<Enum<?>> toys, ResponseAction... responseActions) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.responseActions = Arrays.asList(responseActions);
            this.toys = toys;
        }

        @Override
        public String toString() {
            return name + " " + start + " " + end + " toys=" + toys;
        }
    }

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();
        tests.add(new TestParameters("Miss Kriss success", 1101, 9201, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Miss Kriss failure", 1101, 8960, Arrays.asList(TOYS),
                new ResponseAction("I'm giving up, Miss", Response.Choose)));
        tests.add(new TestParameters("Miss Kriss spurting off", 1101, 8950, Arrays.asList(TOYS),
                new ResponseAction("*spurted*", Response.Choose)));

        tests.add(new TestParameters("Terry & Jennifer Story Ch. 16", 1102, 9202, Arrays.asList(TOYS),
                new ResponseAction("*please go on", Response.Choose)));
        tests.add(new TestParameters("Terry & Jennifer Story Ch. 16 failure", 1102, 9402, Arrays.asList(TOYS),
                new ResponseAction("*No thanks, Miss", Response.Choose)));
        tests.add(new TestParameters("Diary of Carolyn Ch. 1 excerpt", 1103, 9203, Arrays.asList(TOYS),
                new ResponseAction("*stop*", Response.Ignore)));
        tests.add(new TestParameters("Diary of Carolyn Ch. 1 excerpt failure", 1103, 9403, Arrays.asList(TOYS),
                new ResponseAction("*stop*", Response.Choose)));

        tests.add(new TestParameters("1hr punishment wank", 1104, 9204, Arrays.asList(TOYS)));
        tests.add(new TestParameters("2 x  5m or less success", 1105, 9205, Arrays.asList(TOYS)));
        tests.add(new TestParameters("2 x  5m or less failure", 1105, 8960, Arrays.asList(TOYS),
                new ResponseAction("I'm giving up, Miss", Response.Choose)));
        tests.add(new TestParameters("2 x  5m or less spurted off", 1105, 8950, Arrays.asList(TOYS),
                new ResponseAction("*spurted*", Response.Choose)));

        tests.add(new TestParameters("5 x  3m", 1106, 9206, Arrays.asList(TOYS)));
        tests.add(new TestParameters("2 x 10m", 1107, 9207, Arrays.asList(TOYS)));
        tests.add(new TestParameters("4 x  5m", 1108, 9208, Arrays.asList(TOYS)));
        tests.add(new TestParameters("7 x  3m", 1109, 9209, Arrays.asList(TOYS)));
        tests.add(new TestParameters("3 x 10m", 1110, 9210, Arrays.asList(TOYS)));
        tests.add(new TestParameters("5 x  5m", 1111, 9211, Arrays.asList(TOYS)));
        tests.add(new TestParameters("9 x  3m", 1112, 9212, Arrays.asList(TOYS)));
        tests.add(new TestParameters("4 x 10m", 1113, 9213, Arrays.asList(TOYS)));
        tests.add(new TestParameters("9 x  5m", 1114, 9214, Arrays.asList(TOYS)));

        tests.add(new TestParameters("slavesinlove bd186 activity success", 1115, 9215, Arrays.asList(TOYS),
                new ResponseAction("It's enough, Miss", Response.Ignore)));
        tests.add(new TestParameters("slavesinlove bd186 activity failure", 1115, 9415, Arrays.asList(TOYS),
                new ResponseAction("It's enough, Miss", Response.Choose)));
        tests.add(new TestParameters("slavesinlove bd186 punishment success", 1116, 9216, Arrays.asList(TOYS),
                new ResponseAction("It's enough, Miss", Response.Ignore)));
        tests.add(new TestParameters("slavesinlove bd186 punishment failure", 1116, 9416, Arrays.asList(TOYS),
                new ResponseAction("It's enough, Miss", Response.Choose)));
        return tests;
    }

    public static Mine createPlayer(Preset preset)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        Mine mine = preset.script(Mine.MAID).clearHandlers().responses(MinePrompts.maidGood()).mine();

        return mine;
    }

    private final TestParameters testParameters;

    public TeaseAndDenialActivities(TestParameters testParameters) {
        this.testParameters = testParameters;
    }

    @Test
    public void test() throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = new Preset().script(Mine.TD).clearHandlers().responses(MinePrompts.td())
                .responses(testParameters.responseActions).mine();
        mine.breakPoints.add(mine.script.name, testParameters.end);

        for (Enum<?> toy : testParameters.toys) {
            mine.item(toy).setAvailable(true);
        }

        mine.play(new ActionRange(testParameters.start), new ActionRange(testParameters.start, testParameters.end));
        assertEquals(new ActionRange(testParameters.end), mine.range);
    }
}
