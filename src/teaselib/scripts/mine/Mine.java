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

    private static final String[] Assets = { "Mine Scripts.zip",
            "Mine Resources.zip", "Mine Mistress.zip" };

    public enum Punishment {
        BadIntroduction,
        NotNaked,
        CummingWithoutPermission
    }

    public enum Assignments {
        NextSession,
        Enema
    }

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

        // Toys simple mappings
        state.addToyMapping(361, toy(Toys.Nipple_clamps));
        state.addToyMapping(362, toy(Toys.Clothespins));
        state.addToyMapping(366, toy(Toys.Rope));
        state.addToyMapping(360, toy(Toys.Chains));
        state.addToyMapping(382, toy(Toys.Blindfold));
        state.addToyMapping(384, toy(Toys.Humbler));
        state.addToyMapping(388, toy(Toys.Anal_Dildo));
        state.addToyMapping(383, toy(Toys.Enema_Kit));
        state.addToyMapping(387, toy(Toys.Pussy_Clamps));
        state.addToyMapping(385, toy(Toys.Ball_Stretcher));

        // Toy simple mappings - ingame toys (not selectable in toy list)
        state.addToyMapping(371, toy(Toys.Hairbrush));
        state.addToyMapping(372, toy(Toys.Wooden_Spoon));
        state.addToyMapping(373, toy(Toys.Ruler));
        state.addToyMapping(374, toy(Toys.Enema_Bulb));

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

        state.addStateMapping(95, state(Assignments.NextSession));
        state.addStateMapping(267, state(Assignments.Enema));

        // state elapsed mappings
        // TODO Should map to TimeLock device state, see Mine.sbd:[action 9255]
        state.addStateTimeMapping(45, state(Body.Chastified));
    }

    @Override
    public void run() {
        play("Mine");
    }

    public void punish(Punishment punishment) {
        switch (punishment) {
        case BadIntroduction:
            set("mine.500", 1);
            break;
        case NotNaked:
            if (getInteger("mine.502") == 1) {
                set("mine.503", 1);
            } else {
                set("mine.502", 1);
            }
            break;
        case CummingWithoutPermission:
            set("mine.519", 1);
            break;
        default:
            break;
        }
    }

    public void override(Toys toy, boolean available) {
        state.overwrite(toy, available);
    }
}
