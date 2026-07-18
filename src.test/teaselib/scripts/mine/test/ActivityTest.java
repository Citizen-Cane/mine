package teaselib.scripts.mine.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
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

    protected final Collection<ResponseAction> responses;

    public ActivityTest(String script, Collection<ResponseAction> responses)
            throws ScriptParsingException, ValidationIssue, ScriptExecutionException, IOException {
        preset.script(script).clearHandlers();
        this.responses = responses;
    }

    protected void testWith(TestParameters testParameters) throws ScriptExecutionException {
        var mine = preset.responses(testParameters).responses(responses).mine();
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
        assertTrue(all.remove(without), "Action to exclude not present: " + without);
        return all;
    }

    public static Set<Action> actions(Player player, int start, int end) {
        return new HashSet<>(player.script.actions.getAll(new ActionRange(start, end)));
    }

}
