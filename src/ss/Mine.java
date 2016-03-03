package ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import pcm.controller.Player;
import pcm.model.ActionRange;
import pcm.model.ParseError;
import pcm.model.ValidationError;
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
    private static final String AssetRoot = "Mine";
    private static final String Namespace = "Mine";
    private static final Actor MineMistress = new Actor(Actor.Dominant,
            Voice.Gender.Female, "en-us");

    static final String[] Assets = { "Mine Scripts.zip", "Mine Resources.zip",
            "Mine Mistress.zip" };

    public static void main(String argv[]) {
        String basePath = "scripts";
        try {
            recordVoices(basePath, AssetRoot, MineMistress, Assets, "Mine");
        } catch (ParseError e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (ValidationError e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (IOException e) {
            TeaseLib.instance().log.error(argv, e);
        } catch (Throwable t) {
            TeaseLib.instance().log.error(argv, t);
        }
        System.exit(0);
    }

    public Mine(TeaseLib teaseLib, String basePath, String mistressPath) {
        super(teaseLib, new ResourceLoader(basePath, AssetRoot), MineMistress,
                Namespace, mistressPath);
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
        String script = null;
        File debug = resources.getAssetPath("debug.txt");
        if (debug.exists()) {
            Player.debugOutput = true;
            Player.validateScripts = true;
            try {
                BufferedReader debugReader = new BufferedReader(
                        new FileReader(debug));
                try {
                    String line;
                    while ((line = debugReader.readLine()) != null) {
                        line = line.trim();
                        int comment = line.indexOf("//");
                        if (comment == 0) {
                            continue;
                        } else if (comment > 0) {
                            line = line.substring(0, comment - 1);
                        }
                        if (!line.isEmpty()) {
                            script = line;
                            break;
                        }
                    }
                } catch (Throwable t) {
                    teaseLib.log.error(this, t);
                } finally {
                    debugReader.close();
                }
            } catch (Throwable t) {
                // Don't reveal we're doing this, since this debug facility is
                // meant for developers only, and not for cheating
                // TeaseLib.log(this, t);
            }
        }
        if (script == null) {
            script = getClass().getSimpleName();
        }
        play(script);
    }

    private void play(String script) {
        // TeaseLib.log("PCMPlayer: " + script + " (basePath = " + basePath
        // + ", libBase = " + libBase + ")");
        StringTokenizer argv = new StringTokenizer(script, " ");
        String scriptName = argv.nextToken();
        final ActionRange range;
        if (argv.hasMoreElements()) {
            int start = Integer.parseInt(argv.nextToken());
            if (argv.hasMoreElements()) {
                int end = Integer.parseInt(argv.nextToken());
                range = new ActionRange(start, end);
            } else {
                range = new ActionRange(start);
            }
        } else {
            range = null;
        }
        play(scriptName, range);
    }
}
