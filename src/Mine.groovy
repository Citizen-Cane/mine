// (SS version, title, description, author, status, color, language code, tags)
setInfos(1, "Mine", "Remastered and extended version of the classic PCM script 'Mine'",	"CitizenCane, ???", "Complete", 0xDDDDDD, "en", ["long", "wanking"]);

// Well, as the PCM player is a bit to complex to fit into a single groovy script,
// and because the PCM script code is beyond translation due to its sheer size,
// let's load teaselib PCMPlayer to get the job done
	 

///////////////////////////////////////////////
// Set the project location
///////////////////////////////////////////////

def scriptFolder = 'scripts'
def packageName = 'teaselib.scripts.mine'
def mainScriptClass = packageName + '.' + 'Mine'
def scriptResources = new File(scriptFolder, 'Mine.jar')


///////////////////////////////////////////////
// Bootstrap TeaseLib
///////////////////////////////////////////////

def teaselibClasspath = new File('lib/TeaseLib/TeaseLib.jar')
if (!teaselibClasspath.exists()) {
	showButton(teaselibClasspath.getAbsolutePath() + " not found - bailing out")
}

URL[] urls = [teaselibClasspath.toURI().toURL()]

def classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader())
Thread.currentThread().setContextClassLoader(classLoader);


///////////////////////////////////////////////
// run the script
///////////////////////////////////////////////

Class.forName('teaselib.core.TeaseLib', true, classLoader).run(
	Class.forName('teaselib.hosts.SexScriptsHost', true, classLoader).newInstance(this),
	Class.forName('teaselib.hosts.SexScriptsStatePersistence', true, classLoader).newInstance(this),
	scriptResources,
	mainScriptClass)
