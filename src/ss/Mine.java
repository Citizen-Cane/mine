package ss;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import pcm.controller.Player;


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

	public void play(String name) throws IOException
	{
		String resourcesBase = "scripts/";
		String libBase = "lib/";
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
		player.play("Debug Mine-equip");
//		player.play(name);
	}
}
