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

def tljar = new File('lib/TeaseLib/TeaseLib.jar')
if (!tljar.exists())
{
	showButton(tljar.getAbsolutePath() + " not found - bailing out")
}
loader.addURL(tljar.toURI().toURL())

def minejar = new File('scripts/Mine.jar')
if (!minejar.exists())
{
	showButton(minejar.getAbsolutePath() + " not found - bailing out")
}
loader.addURL(minejar.toURI().toURL())

// def host = new ss.SexScriptsHost(this);
def Host = Class.forName('teaselib.hosts.SexScriptsHost', true, loader)
def host = Host.newInstance(this)

// def persistence = new ss.SexScriptsStatePersistence(this);
def Persistence = Class.forName('teaselib.hosts.SexScriptsStatePersistence', true, loader)
def persistence = Persistence.newInstance(this)

// teaseLib = new teaselib.TeaseLib(
def TeaseLib = Class.forName('teaselib.TeaseLib', true, loader)
def teaseLib = TeaseLib.newInstance(host,persistence)

//def mine = new Mine();
def Mine = Class.forName('ss.Mine', true, loader)
def mine = Mine.newInstance(teaseLib, "scripts/")
mine.run()
