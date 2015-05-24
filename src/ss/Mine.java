package ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import pcm.controller.Player;
import pcm.model.ActionRange;
import teaselib.TeaseLib;

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
    final String assetRoot = "Mine/";

    public Mine(TeaseLib teaseLib) {
        super(teaseLib, "en-us", "Mine");
    }

    @Override
    public void run() {
        String script = null;
        File debug = teaseLib.resources.getAssetPath("debug.txt");
        if (debug.exists()) {
            Player.debugOutput = true;
            Player.validateScripts = true;
            try {
                BufferedReader debugReader = new BufferedReader(new FileReader(
                        debug));
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
                    TeaseLib.log(this, t);
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
