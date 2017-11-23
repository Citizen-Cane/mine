package teaselib.scripts.mine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.Player;
import pcm.model.ActionRange;
import pcm.state.persistence.ScriptState;
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
    private static final int QUICK_PINMS_STATE_RANGE_START = 7300;
    private static final int QUICK_PINS_QUESTION_RANGE_START = 7400;
    private static final Enum<?>[] TOYS = { Toys.Ankle_Restraints, Toys.Nipple_Clamps, Toys.Pussy_Clamps,
            Household.Clothes_Pegs };

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("Quick Pins", 1014, 9014, Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Yes Miss, please"));
                responseActions.add(new ResponseAction("Yes Miss", () -> pinAttached(player)));
                responseActions.add(new ResponseAction("No Miss", () -> pinNotAttached(player)));

                responseActions.add(new ResponseAction("Please stop, Miss", () -> allOnQuickPins(player)));
                return responseActions;
            }

            Response allOnQuickPins(Player player) {
                int[] allOn = { 7300, 7301, 7302, 7303, 7304, 7305, 7306, 7307, 7308, 7309 };
                for (int n : allOn) {
                    if (player.state.get(n).equals(ScriptState.UNSET)) {
                        return Response.Ignore;
                    }
                }
                return Response.Choose;
            }
        });

        tests.add(new TestParameters("Quick Pins 15", 1015, 9015, Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Yes Miss, please"));
                responseActions.add(new ResponseAction("Yes Miss, I'm ready"));

                responseActions.add(new ResponseAction("Yes Miss, just one", () -> answerCorrectly(player, 7000)));
                responseActions.add(new ResponseAction("Yes Miss, one pin", () -> answerCorrectly(player, 7005)));
                responseActions.add(new ResponseAction("Yes Miss, one exactly", () -> answerCorrectly(player, 7010)));

                responseActions.add(new ResponseAction("Yes Miss, two exactly", () -> answerCorrectly(player, 7001)));
                responseActions.add(new ResponseAction("Yes Miss, two exactly", () -> answerCorrectly(player, 7006)));
                responseActions.add(new ResponseAction("Yes Miss, two pins", () -> answerCorrectly(player, 7011)));

                responseActions.add(new ResponseAction("Yes Miss, three pegs", () -> answerCorrectly(player, 7002)));
                responseActions.add(new ResponseAction("Yes Miss, three pegs", () -> answerCorrectly(player, 7007)));
                responseActions.add(new ResponseAction("Yes Miss, three pegs", () -> answerCorrectly(player, 7012)));

                responseActions.add(new ResponseAction("No Miss, I haven't"));

                responseActions.add(new ResponseAction("Please stop, Miss", () -> allOnQuickPins15(player)));
                return responseActions;
            }

            Response answerCorrectly(Player player, int n) {
                if (player.range == null)
                    return Response.Ignore;
                return player.state.get(n) == ScriptState.SET ? Response.Choose : Response.Ignore;
            }

            Response allOnQuickPins15(Player player) {
                if (new ActionRange(7029, 7124).contains(player.range.start)) {
                    int[] allAttached = { 7004, 7009, 7014 };
                    for (int n : allAttached) {
                        if (player.state.get(n).equals(ScriptState.UNSET)) {
                            return Response.Ignore;
                        }
                    }
                    return Response.Choose;
                } else {
                    return Response.Ignore;
                }
            }

        });

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

    protected static Response pinAttached(Player player) {
        return isPinAttached(player) ? Response.Choose : Response.Ignore;
    }

    protected static Response pinNotAttached(Player player) {
        return !isPinAttached(player) ? Response.Choose : Response.Ignore;
    }

    private static boolean isPinAttached(Player player) {
        int n = player.range.start;
        return player.state.get(n - QUICK_PINS_QUESTION_RANGE_START + QUICK_PINMS_STATE_RANGE_START)
                .equals(ScriptState.SET);
    }

    public NiptActivities(TestParameters testParameters) {
        super(testParameters, Mine.NIPT, MinePrompts.nipt());
    }
}
