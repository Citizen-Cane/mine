package teaselib.scripts.mine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.Assertion;
import pcm.controller.Player;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.Accessoires;
import teaselib.Body;
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
public class OoeActivities extends ActivityTest {
    private static final Enum<?>[] TOYS = { Toys.Nipple_Clamps, Toys.Pussy_Clamps, Household.Clothes_Pegs, Toys.Gag };

    static final int SLAVE_IS_ODDS = 10;
    static final int SLAVE_IS_EVENS = 11;

    @Parameters(name = "Activity {0}")
    public static Iterable<TestParameters> tests() {
        List<TestParameters> tests = new ArrayList<>();

        tests.add(new TestParameters("Generic game, random rules - win", new ActionRange(1000, 9999), 1000, 9000,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return win(player);
            }
        });

        tests.add(new TestParameters("Generic game, random rules - loose", new ActionRange(1000, 9999), 1000, 9500,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        tests.add(new TestParameters("Spanking game - win", new ActionRange(1000, 9999), 1001, 9001,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return win(player);
            }
        });

        tests.add(new TestParameters("Spanking game - loose", new ActionRange(1000, 9999), 1001, 9501,
                Arrays.asList(TOYS)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        tests.add(new TestParameters("Nipple torture game - nipple clamps - win", new ActionRange(1000, 9999), 1002,
                9002, Arrays.asList(Toys.Nipple_Clamps)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return win(player);
            }

        });

        tests.add(new TestParameters("Nipple torture game - nipple clamps - loose", new ActionRange(1000, 9999), 1002,
                9502, Arrays.asList(Toys.Nipple_Clamps)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs - win", new ActionRange(1000, 9999), 1002,
                9002, Arrays.asList(Toys.Nipple_Clamps)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return win(player);
            }

        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs - loose", new ActionRange(1000, 9999), 1002,
                9502, Arrays.asList(Household.Clothes_Pegs)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs on titties - win", new ActionRange(1000, 9999),
                1002, 9002, Arrays.asList(Household.Clothes_Pegs, Toys.Pussy_Clamps)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Pussy_Clamps).apply();
                asserteSingleGameFlag(player);
                return win(player);
            }

        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs on titties - loose",
                new ActionRange(1000, 9999), 1002, 9502, Arrays.asList(Household.Clothes_Pegs, Toys.Pussy_Clamps)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Toys.Pussy_Clamps).apply();
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs on pussy - win", new ActionRange(1000, 9999),
                1002, 9002, Arrays.asList(Household.Clothes_Pegs, Accessoires.Breast_Forms)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Accessoires.Breast_Forms).to(Body.OnNipples).apply();
                asserteSingleGameFlag(player);
                return win(player);
            }

        });

        tests.add(new TestParameters("Nipple torture game - clothes pegs on pussy - loose", new ActionRange(1000, 9999),
                1002, 9502, Arrays.asList(Household.Clothes_Pegs, Accessoires.Breast_Forms)) {
            @Override
            public List<ResponseAction> getResponseActions(Player player) {
                player.item(Accessoires.Breast_Forms).to(Body.OnNipples).apply();
                asserteSingleGameFlag(player);
                return loose(player);
            }
        });

        return tests;
    }

    static void asserteSingleGameFlag(Player player) {
        player.breakPoints.add("mine-ooe", new Assertion("Single game flag", player.script.actions.get(1400), //
                () -> Stream.of(401, 402, 403, 404, 405, 406, 407, 408).map(player.state::get)
                        .filter(ScriptState.SET::equals).count() == 1));
    }

    static List<ResponseAction> win(Player player) {
        List<ResponseAction> responseActions = new ArrayList<>();

        responseActions.add(new ResponseAction("One, Mistress", () -> chooseWin(player)));
        responseActions.add(new ResponseAction("Two, Mistress", Response.Choose));

        return responseActions;
    }

    static Response chooseWin(Player player) {
        if (player.state.get(SLAVE_IS_EVENS) == ScriptState.SET) {
            player.state.set(2111);
            player.state.set(2112);
            player.state.set(2120);
        } else if (player.state.get(SLAVE_IS_ODDS) == ScriptState.SET) {
            player.state.set(2110);
            player.state.set(2113);
            player.state.set(2130);
        } else {
            throw new IllegalStateException();
        }
        return player.randomItem(Response.Choose, Response.Ignore);
    }

    static List<ResponseAction> loose(Player player) {
        List<ResponseAction> responseActions = new ArrayList<>();

        responseActions.add(new ResponseAction("One, Mistress", () -> chooseLoose(player)));
        responseActions.add(new ResponseAction("Two, Mistress", Response.Choose));

        return responseActions;
    }

    static Response chooseLoose(Player player) {
        if (player.state.get(SLAVE_IS_EVENS) == ScriptState.SET) {
            player.state.set(2110);
            player.state.set(2113);
            player.state.set(2120);
        } else if (player.state.get(SLAVE_IS_ODDS) == ScriptState.SET) {
            player.state.set(2111);
            player.state.set(2112);
            player.state.set(2130);
        } else {
            throw new IllegalStateException();
        }
        return player.randomItem(Response.Choose, Response.Ignore);
    }

    public OoeActivities(TestParameters testParameters)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        super(testParameters, Mine.OOE, MinePrompts.ooe());
    }
}
