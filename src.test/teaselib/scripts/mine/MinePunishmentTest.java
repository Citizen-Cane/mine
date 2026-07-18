package teaselib.scripts.mine;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.MethodSource;
import pcm.controller.AllActionsSetException;
import pcm.controller.BreakPoint;
import pcm.model.ActionRange;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.ScriptState;
import teaselib.scripts.mine.test.MinePrompts;
import teaselib.scripts.mine.test.PresetTestable;

@ParameterizedClass(name = "Punishment {0}")
@MethodSource("data")
public class MinePunishmentTest extends PresetTestable {

    public static Iterable<Integer> data() {
        return List.of(
                500, 501, 502, 503, 504, 505, 506, 507, 508,
                509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519,
                520, 521, 525, 548, 549);
    }

    private final Mine mine;
    final int punishment;

    public MinePunishmentTest(int punishment)
            throws IOException, ScriptParsingException, ValidationIssue, ScriptExecutionException {
        super();
        mine = preset.script(Mine.MAIN).submitted().set(punishment).responses(MinePrompts.all()).mine();
        this.punishment = punishment;
    }

    @Test
    public void testPunishments() throws AllActionsSetException, ScriptExecutionException {
        Assertions.assertEquals(ScriptState.SET, mine.state.get(punishment),"Punishment pending");
        mine.breakPoints.add(mine.script.name, mine.script.actions.get(3000), BreakPoint.STOP);
        mine.playFrom(new ActionRange(845, 846));
        Assertions.assertEquals(ScriptState.UNSET, mine.state.get(punishment),"Punishment executed");
        Assertions.assertEquals( 3000, mine.action.number,"Punishment section not finished");
    }

}
