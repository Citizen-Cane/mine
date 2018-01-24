package teaselib.scripts.mine;

import java.io.IOException;

import org.junit.Test;

import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.scripts.mine.test.Preset;
import teaselib.test.DebugSetup;

public class ValidateProject {
    @Test
    public void testSyntaxAndResources()
            throws ScriptExecutionException, IOException, ScriptParsingException, ValidationIssue {
        Mine mine = new Preset(new DebugSetup().withOutput()).script(Mine.MAIN).mine();
        mine.validateProject();
    }
}
