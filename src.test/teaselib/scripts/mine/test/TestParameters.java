/**
 * 
 */
package teaselib.scripts.mine.test;

import java.util.Arrays;
import java.util.List;

import pcm.controller.Player;
import teaselib.core.Debugger.ResponseAction;

public class TestParameters {
    public final String name;
    public final int start;
    public final int end;

    private final List<ResponseAction> responseActions;
    public final List<Enum<?>> toys;

    public TestParameters(String name, int start, int end, List<Enum<?>> toys, ResponseAction... responseActions) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.responseActions = Arrays.asList(responseActions);
        this.toys = toys;
    }

    public List<ResponseAction> getResponseActions(Player player) {
        return responseActions;
    }

    @Override
    public String toString() {
        return name + " " + start + "->" + end + " toys=" + toys;
    }
}