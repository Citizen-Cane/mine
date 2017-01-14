package teaselib.scripts.mine;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pcm.controller.Player;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.MappedState;
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
    private static final String ResourcesRoot = "Mine";
    private static final String MainScript = "Mine";

    private static final Actor MineMistress = new Actor("Mistress", "Miss,",
            Voice.Gender.Female, Locale.UK, Actor.Key.DominantFemale,
            Images.None);

    private static final String[] Assets = { "Mine Scripts.zip",
            "Mine Resources.zip", "Mine Mistress.zip" };

    private static final String[] OptionalAssets = { "Mine Speech.zip" };

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
            recordVoices(MineMistress, MainScript, new File(argv[0]),
                    ResourcesRoot, Assets);
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
        this(teaseLib, "mistress/Vana/");
    }

    public Mine(TeaseLib teaseLib, String mistressPath) {
        super(teaseLib, new ResourceLoader(Mine.class, ResourcesRoot, Assets,
                OptionalAssets), MineMistress, Namespace, mistressPath);
        resources.addAssets(OptionalAssets);

        // Toy categories - multiple items
        state.addToyMapping(MappedState.Global, 363,
                items(Toys.Wrist_Restraints));
        state.addToyMapping(MappedState.Global, 364,
                items(Toys.Ankle_Restraints));
        state.addToyMapping(MappedState.Global, 365, items(Toys.Collars));
        state.addToyMapping(MappedState.Global, 367, items(Toys.Gags));
        state.addToyMapping(MappedState.Global, 368, items(Toys.Buttplugs));
        state.addToyMapping(MappedState.Global, 370,
                items(Toys.Spanking_Implements));
        state.addToyMapping(MappedState.Global, 380,
                items(Toys.Chastity_Devices));
        state.addToyMapping(MappedState.Global, 389,
                items(Toys.Vibrators, Toys.EStim_Devices));

        // Toys simple mappings
        state.addToyMapping(MappedState.Global, 361, item(Toys.Nipple_clamps));
        state.addToyMapping(MappedState.Global, 362, item(Toys.Clothespins));
        state.addToyMapping(MappedState.Global, 366, item(Toys.Rope));
        state.addToyMapping(MappedState.Global, 360, item(Toys.Chains));
        state.addToyMapping(MappedState.Global, 382, item(Toys.Blindfold));
        state.addToyMapping(MappedState.Global, 384, item(Toys.Humbler));
        state.addToyMapping(MappedState.Global, 388, item(Toys.Anal_Dildo));
        state.addToyMapping(MappedState.Global, 383, item(Toys.Enema_Kit));
        state.addToyMapping(MappedState.Global, 387, item(Toys.Pussy_Clamps));
        state.addToyMapping(MappedState.Global, 385, item(Toys.Ball_Stretcher));

        // Toy simple mappings - ingame toys (not selectable in toy list)
        state.addToyMapping(MappedState.Global, 371, item(Toys.Hairbrush));
        state.addToyMapping(MappedState.Global, 372, item(Toys.Wooden_Spoon));
        state.addToyMapping(MappedState.Global, 373, item(Toys.Ruler));
        state.addToyMapping(MappedState.Global, 374, item(Toys.Enema_Bulb));

        // state mappings
        state.addStateMapping(MappedState.Global, 20,
                state(Body.SomethingOnNipples));
        state.addStateMapping(MappedState.Global, 21,
                state(Body.SomethingInButt));
        state.addStateMapping(MappedState.Global, 22,
                state(Body.SomethingInMouth));
        state.addStateMapping(MappedState.Global, 23,
                state(Body.SomethingOnPenis));
        state.addStateMapping(MappedState.Global, 24,
                state(Body.SomethingOnBalls));
        state.addStateMapping(MappedState.Global, 25, state(Body.AnklesTied));
        state.addStateMapping(MappedState.Global, 26, state(Body.WristsTied));
        state.addStateMapping(MappedState.Global, 27,
                state(Body.SomethingAroundWaist));
        state.addStateMapping(MappedState.Global, 28, state(Body.Collared));
        state.addStateMapping(MappedState.Global, 30,
                state(Body.CannotJerkOff));
        state.addStateMapping(MappedState.Global, 31,
                state(Body.CantSitOnChair));
        state.addStateMapping(MappedState.Global, 32, state(Body.CannotStand));
        state.addStateMapping(MappedState.Global, 33, state(Body.CannotKneel));
        state.addStateMapping(MappedState.Global, 34,
                state(Body.CannotTypeOrUseMouse));
        state.addStateMapping(MappedState.Global, 35, state(Body.Tethered));
        state.addStateMapping(MappedState.Global, 36, state(Body.Harnessed));

        state.addStateMapping(MappedState.Global, 44, state(Body.Chastified));

        String mainScript = "Mine";
        state.addStateMapping(mainScript, 95, state(Assignments.NextSession));
        state.addStateMapping(mainScript, 267, state(Assignments.Enema));

        // state elapsed mappings
        // TODO Should map to TimeLock device state, see Mine.sbd:[action 9255]
        state.addStateTimeMapping(MappedState.Global, 45,
                state(Body.Chastified));
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
