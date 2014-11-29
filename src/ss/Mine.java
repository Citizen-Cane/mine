package ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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

public class Mine {

	private final IScript scriptInterface;

	final String assetRoot = "Mine/";

	public Mine(IScript script) {
		this.scriptInterface = script;
	}

	public void play() throws IOException {
		String resourcesBase = "scripts/";
		String teaselibBase = "lib/TeaseLib/";
		String script = null;
		File debug = new File(resourcesBase + "/" + assetRoot, "debug.txt");
		if (debug.exists()) {
			Player.debugOutput = true;
			Player.validateScripts = true;
			script = null;
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
		play(script, resourcesBase, teaselibBase);
	}

	public void play(String script, String basePath, String libBase)
			throws IOException {
		URI[] assets = { new File(basePath + "Mine Scripts.zip").toURI(),
				new File(basePath + "Mine Resources.zip").toURI(),
				new File(basePath + "Mine Mistress.zip").toURI(),
				new File(basePath + "Mine Speech.zip").toURI(),
				new File(basePath + "Mine.jar").toURI(),
				new File(libBase + "TeaseLib.jar").toURI() };
		TeaseLib teaseLib = new TeaseLib(new SexScriptsHost(scriptInterface),
				new SexScriptsStatePersistence("Mine", scriptInterface),
				basePath, assets, assetRoot);
		Player player = new Player(teaseLib);
		TeaseLib.log("PCMPlayer: " + script + " (basePath = " + basePath
				+ ", libBase = " + libBase + ")");
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
		player.play(scriptName, range);
	}
}
