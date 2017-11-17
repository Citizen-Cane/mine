package teaselib.scripts.mine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import teaselib.Household;
import teaselib.Toys;
import teaselib.core.Debugger.Response;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.test.ActivityTest;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.TestParameters;

/**
 * @author Citizen-Cane
 *
 */
@RunWith(Parameterized.class)
public class NiptActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Toys.Ankle_Restraints, Toys.Nipple_Clamps, Toys.Pussy_Clamps,
            Household.Clothes_Pegs };

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();
        // tests.add(new TestParameters("Quick Pins", 1014, 9014, Arrays.asList(TOYS)));
        // tests.add(new TestParameters("Quick Pins 16", 1015, 9015, Arrays.asList(TOYS)));

        tests.add(new TestParameters("Nipple Clamp Weight Lifting", 1016, 9016, Arrays.asList(TOYS),
                new ResponseAction("Yes please*", Response.Choose), new ResponseAction("Yes Miss*", Response.Choose),
                new ResponseAction("Please stop*", Response.Ignore)));
        tests.add(new TestParameters("Nipple Clamp Weight Lifting failure 1st", 1016, 9116, Arrays.asList(TOYS),
                new ResponseAction("Yes please*", Response.Choose), new ResponseAction("Yes Miss*", Response.Choose),
                new ResponseAction("Please stop it*", Response.Ignore)));
        tests.add(new TestParameters("Nipple Clamp Weight Lifting failure 2nd", 1016, 9116, Arrays.asList(TOYS),
                new ResponseAction("Yes please*", Response.Choose), new ResponseAction("Yes Miss*", Response.Choose),
                new ResponseAction("Please stop it*", Response.Ignore),
                new ResponseAction("Please stop*", Response.Choose)));

        tests.add(new TestParameters("Light Cardio Nips", 1017, 9017, Arrays.asList(TOYS)));
        tests.add(new TestParameters("Shake Those Titties", 1018, 9018, Arrays.asList(TOYS),
                new ResponseAction("*pain*", Response.Choose), new ResponseAction("*more*", Response.Choose),
                new ResponseAction("*pleases*", Response.Choose)));

        return tests;
    }

    public NiptActivities(TestParameters testParameters) {
        super(testParameters, Mine.NIPT, MinePrompts.nipt());
    }
}
