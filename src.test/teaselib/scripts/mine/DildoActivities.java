package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.Player;
import pcm.controller.TestFailureTrigger;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Bondage;
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
public class DildoActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Toys.Ankle_Restraints, Toys.Wrist_Restraints, Toys.Nipple_Clamps,
            Toys.Pussy_Clamps, Household.Clothes_Pegs, Toys.Gag, Toys.Blindfold, Toys.Dildo, Toys.Buttplug };

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("Dildo worshipping activity first time full penetration", 900, 9852,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Dildo inserted all the way, Miss", Response.Choose));
                responseActions.add(new ResponseAction("Yes Miss, it's all the way in", Response.Choose));
                responseActions.add(new ResponseAction("Yes please, Mistress", Response.Choose));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshiping activity first time partial penetration", 900, 9851,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Dildo inserted all the way, Miss", Response.Ignore));
                responseActions.add(new ResponseAction("Yes please, Mistress",
                        () -> player.action.number == 3561 ? Response.Choose : Response.Ignore));
                responseActions.add(new ResponseAction("No Miss, it won't slip in",
                        () -> player.action.number == 3644 ? Response.Choose : Response.Ignore));
                responseActions.add(new ResponseAction("No thanks, Mistress",
                        () -> player.action.number == 3645 ? Response.Choose : Response.Ignore));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshipping activity first time no penetration", 900, 9850,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("No thanks, Mistress",
                        () -> player.action.number == 3561 ? Response.Choose : Response.Ignore));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshipping activity while plugged", 900, 9852, Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Anal_Douche).apply();
                player.item(Toys.Anal_Douche).remove();

                player.item(Toys.Buttplug).apply();

                player.breakPoints.add("mine-dildo", new TestFailureTrigger("Buttplug not removed",
                        actions(player, new ActionRange(3160, 3169), action(player, 3161))));

                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Dildo inserted all the way, Miss", Response.Choose));
                responseActions.add(new ResponseAction("Yes Miss, it's all the way in", Response.Choose));
                responseActions.add(new ResponseAction("Yes please, Mistress", Response.Choose));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshipping activity while gagged", 900, 9850, Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Gag).apply();
                player.item(Toys.Buttplug).apply();
                player.item(Bondage.Harness).apply();

                player.breakPoints.add("mine-dildo", new TestFailureTrigger("Buttplug not removed",
                        actions(player, new ActionRange(3160, 3169), action(player, 3169))));

                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Yes please, Mistress", Response.Choose));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshipping activity while gagged & plugged", 900, 9852,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Anal_Douche).apply();
                player.item(Toys.Anal_Douche).remove();

                player.item(Toys.Buttplug).apply();
                player.item(Toys.Gag).apply();

                player.breakPoints.add("mine-dildo", new TestFailureTrigger("Buttplug not removed",
                        actions(player, new ActionRange(3160, 3169), action(player, 3161))));

                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Dildo inserted all the way, Miss", Response.Choose));
                responseActions.add(new ResponseAction("Yes Miss, it's all the way in", Response.Choose));
                responseActions.add(new ResponseAction("Yes please, Mistress", Response.Choose));

                return responseActions;
            }
        });

        tests.add(new TestParameters("Dildo worshipping activity while gagged during single activity - only riding",
                900, 9852, Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Gag).apply();
                player.state.set(221);

                player.breakPoints.add("mine-dildo", new TestFailureTrigger("Buttplug not removed",
                        actions(player, new ActionRange(3160, 3169), action(player, 3168))));

                List<ResponseAction> responseActions = new ArrayList<>(super.getResponseActions(player));

                responseActions.add(new ResponseAction("Dildo inserted all the way, Miss", Response.Choose));
                responseActions.add(new ResponseAction("Yes Miss, it's all the way in", Response.Choose));
                responseActions.add(new ResponseAction("Yes please, Mistress", Response.Choose));

                return responseActions;
            }

        });

        return tests;
    }

    public DildoActivities(TestParameters testParameters)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super(testParameters, Mine.DILDO, MinePrompts.dildo());
    }
}
