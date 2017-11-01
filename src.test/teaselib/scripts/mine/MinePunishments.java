package teaselib.scripts.mine;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pcm.controller.AllActionsSetException;
import pcm.controller.BreakPoint;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.Preset;

@RunWith(Parameterized.class)
public class MinePunishments {
    @Parameters(name = "Punishment {0}")
    public static Iterable<Integer> data() {
        List<Integer> punishments = Arrays.asList(500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513,
                514, 515, 516, 517, 518, 519, 520, 521, 525, 548, 549);
        return punishments;
    }

    final int punishment;

    public MinePunishments(int punishment) {
        this.punishment = punishment;
    }

    private Mine mine;

    @Test
    public void testPunishments() throws AllActionsSetException, ScriptExecutionException, ScriptParsingException,
            ValidationIssue, IOException {
        mine = new Preset().script(Mine.MAIN).submitted().set(punishment).responses(MinePrompts.all()).mine();

        assertEquals("Punishment pending", ScriptState.SET, mine.state.get(punishment));
        mine.breakPoints.add(mine.script.name, 3000, BreakPoint.STOP);
        mine.playFrom(new ActionRange(845, 846));
        assertEquals("Punishment executed", ScriptState.UNSET, mine.state.get(punishment));
    }

    protected void assertScriptEndedGracefully() {
        assertEquals("All actions set - see console output for offending action", ScriptState.UNSET,
                mine.state.get(861));
        assertEquals("Save and quit", ScriptState.SET, mine.state.get(898));
    }
}
