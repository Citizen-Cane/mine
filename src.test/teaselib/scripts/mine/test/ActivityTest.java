package teaselib.scripts.mine.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.Mine;

/**
 * @author Citizen-Cane
 *
 */
public class ActivityTest {
    protected final TestParameters testParameters;
    protected final String script;
    protected final Collection<ResponseAction> responses;

    public ActivityTest(TestParameters testParameters, String script, Collection<ResponseAction> responses) {
        this.testParameters = testParameters;
        this.script = script;
        this.responses = responses;
    }

    @Test
    public void test() throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        Mine mine = new Preset().script(script).clearHandlers().responses(responses)
                .responses(testParameters.responseActions).mine();
        mine.breakPoints.add(mine.script.name, testParameters.end);

        for (Enum<?> toy : testParameters.toys) {
            mine.item(toy).setAvailable(true);
        }

        mine.play(new ActionRange(testParameters.start), new ActionRange(testParameters.start, testParameters.end));
        assertEquals(new ActionRange(testParameters.end), mine.range);
    }
}
