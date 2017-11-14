package teaselib.scripts.mine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import pcm.controller.Trigger;

/**
 * @author Citizen-Cane
 *
 */
public class Triggers extends ArrayList<Trigger> {
    private static final long serialVersionUID = 1L;

    public Triggers() {
    }

    public Triggers(Collection<? extends Trigger> triggers) {
        super(triggers);
    }

    public Triggers(Trigger... triggers) {
        for (Trigger trigger : triggers) {
            add(trigger);
        }
    }

    public void assertExpected() {
        for (Trigger trigger : this) {
            assertEquals(trigger.message, trigger.expected, trigger.actual);
        }
    }

}
