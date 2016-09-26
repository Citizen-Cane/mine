package teaselib.scripts.mine;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pcm.controller.Player;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Actor;
import teaselib.Body;
import teaselib.Images;
import teaselib.Toys;
import teaselib.core.ResourceLoader;
import teaselib.core.TeaseLib;
import teaselib.core.texttospeech.Voice;

/**
 * @author someone
 *
 *         Just boot up the whole thing.
 * 
 *         Debug: - Place a file "debug.txt" in the resource path, (next to the
 *         jars), to execute a script different from the default - Copy the
 *         script names from below - Comment out the script you want to start
 */

/* Contents of Debug.txt: */

// Debug Mine-dildo
// Debug Mine-equip
// Debug Mine-maid
// Debug Mine-nipt
// Debug Mine-ooe
// Debug Mine-paddle
// Debug Mine-sb
// Debug Mine-td
// Debug Mine

public class Mine extends Player {
    private static final Logger logger = LoggerFactory.getLogger(Mine.class);

    private static final String Namespace = "Mine";

    private static final Actor MineMistress = new Actor("Mistress", "Miss,",
            Voice.Gender.Female, Locale.UK, Actor.Key.DominantFemale,
            Images.None);

    static final String[] Assets = { "Mine Scripts.zip", "Mine Resources.zip",
            "Mine Mistress.zip" };

    public static void main(String argv[]) {
        try {
            recordVoices(Mine.class, MineMistress, Assets, "Mine");
        } catch (ScriptParsingException e) {
            logger.error(e.getMessage(), e);
        } catch (ValidationIssue e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        System.exit(0);
    }

    public Mine(TeaseLib teaseLib) {
        this(teaseLib, "/Mine/mistress/Vana/");
    }

    public Mine(TeaseLib teaseLib, String mistressPath) {
        super(teaseLib, new ResourceLoader(Mine.class), MineMistress, Namespace,
                mistressPath);
        resources.addAssets(Assets);
        // Toy categories - multiple items
        state.addToyMapping(363, toys(Toys.Wrist_Restraints));
        state.addToyMapping(364, toys(Toys.Ankle_Restraints));
        state.addToyMapping(365, toys(Toys.Collars));
        state.addToyMapping(367, toys(Toys.Gags));
        state.addToyMapping(368, toys(Toys.Buttplugs));
        state.addToyMapping(370, toys(Toys.Spanking_Implements));
        state.addToyMapping(380, toys(Toys.Chastity_Devices));
        state.addToyMapping(389, toys(Toys.Vibrators, Toys.EStim_Devices));

        // Toy simple mappings
        state.addMapping(361, toy(Toys.Nipple_clamps));
        state.addMapping(362, toy(Toys.Clothespins));
        state.addMapping(366, toy(Toys.Rope));
        state.addMapping(360, toy(Toys.Chains));
        state.addMapping(382, toy(Toys.Blindfold));
        state.addMapping(384, toy(Toys.Humbler));
        state.addMapping(388, toy(Toys.Anal_Dildo));
        state.addMapping(383, toy(Toys.Enema_Kit));
        // TODO map enema bulb to flag but don't display in toy list
        // mappedState.addMapping(383, get(Toys.Enema_Bulb));
        state.addMapping(387, toy(Toys.Pussy_Clamps));
        state.addMapping(385, toy(Toys.Ball_Stretcher));

        // Toy simple mappings - ingame toys (not selectable in toy list)
        state.addMapping(383, toy(Toys.Enema_Bulb));
        state.addMapping(371, toy(Toys.Hairbrush));
        state.addMapping(372, toy(Toys.Wooden_Spoon));
        state.addMapping(373, toy(Toys.Ruler));

        // state mappings
        state.addStateMapping(20, state(Body.SomethingOnNipples));
        state.addStateMapping(21, state(Body.SomethingInButt));
        state.addStateMapping(22, state(Body.SomethingInMouth));
        state.addStateMapping(23, state(Body.SomethingOnPenis));
        state.addStateMapping(24, state(Body.SomethingOnBalls));
        state.addStateMapping(25, state(Body.AnklesTied));
        state.addStateMapping(26, state(Body.WristsTied));
        state.addStateMapping(27, state(Body.SomethingAroundWaist));
        state.addStateMapping(28, state(Body.Collared));
        state.addStateMapping(30, state(Body.CannotJerkOff));
        state.addStateMapping(31, state(Body.CantSitOnChair));
        state.addStateMapping(32, state(Body.CannotStand));
        state.addStateMapping(33, state(Body.CannotKneel));
        state.addStateMapping(34, state(Body.CannotTypeOrUseMouse));
        state.addStateMapping(35, state(Body.Tethered));
        state.addStateMapping(36, state(Body.Harnessed));

        state.addStateMapping(44, state(Body.Chastified));

        // state elapsed mappings
        // TODO Should map to TimeLock device state, see Mine.sbd:[action 9255]
        state.addStateTimeMapping(45, state(Body.Chastified));

    }

    @Override
    public void run() {
        play("Mine");
    }

}
