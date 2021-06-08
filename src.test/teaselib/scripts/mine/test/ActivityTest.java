package teaselib.scripts.mine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import pcm.controller.Player;
import pcm.model.Action;
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
public class ActivityTest extends PresetTestable {

    protected final TestParameters testParameters;
    protected final Collection<ResponseAction> responses;
    protected final Mine mine;

    public ActivityTest(TestParameters testParameters, String script, Collection<ResponseAction> responses)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        preset.script(script).clearHandlers();
        this.testParameters = testParameters;
        this.responses = responses;
        this.mine = preset.responses(testParameters).responses(responses).mine();
    }

    @Test
    public void test() throws ScriptExecutionException {
        mine.breakPoints.add(mine.script.name, action(mine, testParameters.end));

        for (Enum<?> toy : testParameters.toys) {
            mine.item(toy).setAvailable(true);
        }

        mine.play(new ActionRange(testParameters.start), testParameters.playRange);
        assertEquals(testParameters.end, mine.action.number);
    }

    public static Action action(Player player, int n) {
        return player.script.actions.get(n);
    }

    public static Set<Action> actions(Player player, ActionRange range, Action without) {
        HashSet<Action> all = new HashSet<>(player.script.actions.getAll(range));
        assertTrue("Action to exclude not present: " + without, all.remove(without));
        return all;
    }

    public static Set<Action> actions(Player player, int start, int end) {
        return new HashSet<>(player.script.actions.getAll(new ActionRange(start, end)));
    }

}
