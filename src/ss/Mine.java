package ss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import pcm.controller.Player;
import teaselib.TeaseLib;


/**
 * @author someone
 *
 * Just boot up the whole thing.
 * 
 * Debug:
 * - Place a file "debug.txt" in the resource path, (next to the jars), to execute a script different from the default
 * - Copy the script names from below 
 * - Comment out the script you want to start
 */

/* Contents of Debug.txt: */

//Debug Mine-dildo
//Debug Mine-equip
//Debug Mine-maid
//Debug Mine-nipt
//Debug Mine-ooe
//Debug Mine-paddle
//Debug Mine-sb
//Debug Mine-td
//Debug Mine

public class Mine {

	private final IScript scriptInterface;
	
	final String assetsBasePath = "Mine/";
	
	public Mine(IScript script)
	{
		this.scriptInterface = script;
	}
	
	public static void enableValidateScripts(boolean enable)
	{
		Player.validateScripts = enable;
	}

	public static void enableDebugOutput(boolean enable)
	{
		Player.debugOutput = enable;
	}

	public void play() throws IOException
	{
		String resourcesBase = "scripts/";
		String libBase = "lib/";
		String scriptName = null;
		File debug = new File(resourcesBase, "debug.txt"); 
		if (debug.exists())
		{
			scriptName = null;
			try {
				BufferedReader debugReader = new BufferedReader(new FileReader(
						debug));
					try {
						String line;
						while ((line = debugReader.readLine()) != null) {
							if (!line.startsWith("//")) {
								scriptName = line;
								break;
							}
						}
					} catch (Throwable t) {
						TeaseLib.log(this, t);
					}
					finally
					{
						debugReader.close();
					}
			} catch (Throwable t) {
				// Don't reveal we're doing this, since the debug facility is for devs only
				// TeaseLib.log(this, t);
			}
		}
		if (scriptName == null)
		{
			scriptName = getClass().getSimpleName(); 
		}
		play(scriptName, resourcesBase, libBase);
	}
	public void play(String name, String resourcesBase, String libBase) throws IOException
	{
		URI[] uris = {
				new File(resourcesBase + assetsBasePath).toURI(),
				new File(resourcesBase + "Mine Scripts.zip").toURI(),
				new File(resourcesBase + "Mine Resources.zip").toURI(),
				new File(resourcesBase + "Mine Mistress.zip").toURI(),
				new File(resourcesBase + "Mine.jar").toURI(),
				new File(libBase + "TeaseLib.jar").toURI()
		};
		Player player = new Player(
				uris,
				assetsBasePath,
				new SexScriptsHost(scriptInterface),
				new SexScriptsStatePersistence("Mine", scriptInterface));
		player.play(name);
	}
}
