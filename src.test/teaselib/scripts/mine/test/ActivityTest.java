package teaselib.scripts.mine.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import teaselib.core.Debugger;
import teaselib.core.Debugger.ResponseAction;
import teaselib.scripts.mine.Mine;

/**
 * @author Citizen-Cane
 *
 */
public class ActivityTest {
    private final Preset preset;
    protected final TestParameters testParameters;
    protected final Collection<ResponseAction> responses;
    protected final Debugger debugger;

    public ActivityTest(TestParameters testParameters, String script, Collection<ResponseAction> responses)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        this.preset = new Preset().script(script).clearHandlers();
        this.debugger = preset.debugger;

        this.testParameters = testParameters;
        this.responses = responses;
    }

    @Test
    public void test() throws ScriptExecutionException {
        Mine mine = preset.responses(testParameters).responses(responses).mine();
        mine.breakPoints.add(mine.script.name, testParameters.playRange.end);

        for (Enum<?> toy : testParameters.toys) {
            mine.item(toy).setAvailable(true);
        }

        mine.play(new ActionRange(testParameters.start), testParameters.playRange);
        assertEquals(new ActionRange(testParameters.playRange.end), mine.range);
    }
}
