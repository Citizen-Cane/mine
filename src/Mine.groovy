// (SS version, title, description, author, status, color, language code, tags)
setInfos(1, "Mine", "Remastered and extended version of the classic PCM script 'Mine'",	"CitizenCane, ???", "Complete", 0xDDDDDD, "en", ["long", "wanking"]); 

///////////////////////////////////////////////
// Set the project location
///////////////////////////////////////////////

def scriptFolder = 'scripts'
def projectFolder = 'Mine'
def packageName = 'teaselib.scripts.mine'
def mainScriptClass = packageName + '.' + 'Mine'


///////////////////////////////////////////////
// Bootstrap TeaseLib
///////////////////////////////////////////////

def teaselibClasspath = new File('lib/TeaseLib/lib/TeaseLib.jar')
if (!teaselibClasspath.exists()) {
	showButton(teaselibClasspath.getAbsolutePath() + " not found - bailing out")
}

URL[] urls = [
	teaselibClasspath.toURI().toURL(),
	new File('scripts', projectFolder).toURI().toURL()]

def classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader())
Thread.currentThread().setContextClassLoader(classLoader);


///////////////////////////////////////////////
// run the script
///////////////////////////////////////////////

try {
	Class TeaseLib = Class.forName('teaselib.core.TeaseLib', true, classLoader)
	Class SexScriptsHost = Class.forName('teaselib.hosts.SexScriptsHost', true, classLoader)
	TeaseLib.run(SexScriptsHost.from(this), mainScriptClass)
} catch(teaselib.core.ScriptInterruptedException e) {
	// Ignore
}
