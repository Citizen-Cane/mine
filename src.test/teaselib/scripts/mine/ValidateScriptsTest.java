package teaselib.scripts.mine;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.configuration.DebugSetup;
import teaselib.scripts.mine.test.Preset;
import teaselib.scripts.mine.test.PresetTestable;

public class ValidateScriptsTest extends PresetTestable {

    Mine mine;

    /**
     * @throws IOException
     */
    public ValidateScriptsTest() throws IOException {
        super(new Preset(new DebugSetup().ignoreMissingResources()));
    }

    @Test
    public void testSyntax() throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = preset.script(Mine.MAIN).mine();
        mine.loadScripts();
        List<ValidationIssue> validationIssues = mine.validateProject();
        mine.reportValidationIssues(validationIssues);
    }

    @Test
    public void testCoverage() throws ScriptExecutionException, IOException, ScriptParsingException, ValidationIssue {
        Mine mine = preset.script(Mine.MAIN).mine();
        mine.loadScripts();
        List<ValidationIssue> validationIssues = mine.validateCoverage();
        mine.reportValidationIssues(validationIssues);
    }

}
