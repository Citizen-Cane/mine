/**
 * 
 */
package teaselib.scripts.mine.test;

import java.util.Arrays;
import java.util.List;

import pcm.controller.Player;
import pcm.model.ActionRange;
import teaselib.core.Debugger.ResponseAction;

public class TestParameters {
    public final String name;
    public final ActionRange playRange;
    public final int start;

    private final List<ResponseAction> responseActions;
    public final List<Enum<?>> toys;

    public TestParameters(String name, int start, int end, List<Enum<?>> toys, ResponseAction... responseActions) {
        this.name = name;
        this.start = start;
        this.playRange = new ActionRange(start, end);
        this.responseActions = Arrays.asList(responseActions);
        this.toys = toys;
    }

    public TestParameters(String name, ActionRange playRange, int start, List<Enum<?>> toys,
            ResponseAction... responseActions) {
        this.name = name;
        this.start = start;
        this.playRange = playRange;
        this.responseActions = Arrays.asList(responseActions);
        this.toys = toys;
    }

    public List<ResponseAction> getResponseActions(Player player) {
        return responseActions;
    }

    @Override
    public String toString() {
        return name + " " + start + "->" + playRange + " toys=" + toys;
    }
}