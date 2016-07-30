package teaselib.scripts.mine;

import java.io.IOException;

import pcm.controller.Player;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.Actor;
import teaselib.TeaseLib;
import teaselib.Toys;
import teaselib.core.ResourceLoader;
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
    private static final String Namespace = "Mine";
    private static final Actor MineMistress = new Actor(Actor.Dominant,
            Voice.Gender.Female, "en-us");

    static final String[] Assets = { "Mine Scripts.zip", "Mine Resources.zip",
            "Mine Mistress.zip" };

    public static void main(String argv[]) {
        try {
            recordVoices(Mine.class, MineMistress, Assets, "Mine");
        } catch (ScriptParsingException e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (ValidationIssue e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (IOException e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (Throwable t) {
            TeaseLib.instance().log.error(argv, t);
        }
        System.exit(0);
    }

    public Mine(TeaseLib teaseLib, String mistressPath) {
        super(teaseLib, new ResourceLoader(Mine.class), MineMistress, Namespace,
                mistressPath);
        resources.addAssets(Assets);
        // Toy categories - multiple items
        state.addMapping(363, toys(Toys.Wrist_Restraints));
        state.addMapping(364, toys(Toys.Ankle_Restraints));
        state.addMapping(365, toys(Toys.Collars));
        state.addMapping(367, toys(Toys.Gags));
        state.addMapping(368, toys(Toys.Buttplugs));
        state.addMapping(370, toys(Toys.Spanking_Implements));
        state.addMapping(380, toys(Toys.Chastity_Devices));
        state.addMapping(389, toys(Toys.Vibrators, Toys.EStim_Devices));

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
    }

    @Override
    public void run() {
        play("Mine");
    }

}
