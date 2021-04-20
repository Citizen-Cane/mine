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

/**
 * @author Citizen-Cane
 *
 */
public class ValidateProjectResourcesTest extends PresetTestable {

    public ValidateProjectResourcesTest() throws IOException {
        super(new Preset(new DebugSetup().withOutput()));
    }

    @Test
    public void testResources() throws ScriptExecutionException, IOException, ScriptParsingException, ValidationIssue {
        Mine mine = preset.script(Mine.MAIN).mine();
        mine.loadScripts();
        List<ValidationIssue> validationIssues = mine.validateResources();
        mine.reportValidationIssues(validationIssues);
    }

}
