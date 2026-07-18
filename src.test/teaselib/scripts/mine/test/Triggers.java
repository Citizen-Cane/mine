package teaselib.scripts.mine.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

import pcm.controller.Trigger;

/**
 * @author Citizen-Cane
 *
 */
public class Triggers extends ArrayList<Trigger> {
    @Serial
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
            assertTrue(trigger.expected(), trigger.getMessage());
        }
    }
}
