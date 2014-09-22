// (SS version, title, description, author, status, color, language code, tags)
setInfos(1, "Mine", "Remastered and extended version of the classic PCM script 'Mine'",	"CitizenCane, ???", "Complete", 0xDDDDDD, "en", ["long", "wanking"]);

// Well, as the PCM player is a bit to complex to fit into a single groovy script,
// and because the PCM script code is beyond translation due to its sheer size,
// let's load some java classes to get the job done
	 
def loader = this.class.classLoader
if (loader == null)
{
	showButton("No class loader, bailing out")
}

def jar = new File('Scripts/Mine.jar')
if (!jar.exists())
{
	showButton(jar.getAbsolutePath() + " not found - bailing out")
}
loader.addURL(jar.toURI().toURL())

def Mine = Class.forName('ss.Mine', true, loader)
Mine.enableValidateScripts(true);
Mine.enableDebugOutput(true);
def mine = Mine.newInstance(this);
mine.play()
