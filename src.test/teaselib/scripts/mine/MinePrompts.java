package teaselib.scripts.mine;

import java.util.ArrayList;
import java.util.Collection;

import teaselib.core.Debugger;
import teaselib.core.Debugger.ResponseAction;

/**
 * @author Citizen-Cane
 *
 */
public class MinePrompts {

    static Collection<ResponseAction> all() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();
        all.addAll(defaults());
        all.addAll(mine());
        all.addAll(maid());
        all.addAll(td());
        all.addAll(paddle());
        return all;
    }

    static Collection<ResponseAction> defaults() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();

        all.add(new ResponseAction("Yes, Miss", Debugger.Response.Choose));
        all.add(new ResponseAction("Exit", Debugger.Response.Choose));

        return all;
    }

    static Collection<ResponseAction> mine() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();
        all.addAll(defaults());

        // Introduction
        all.add(new ResponseAction("Yes Miss, I am", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes, I'm ready, Miss", Debugger.Response.Choose));

        // Questionaire
        all.add(new ResponseAction("Yes Miss, please train my ass", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, please keep me chaste", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes, I'm prepared, Miss", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I'm aware of the risk", Debugger.Response.Choose));

        // Level
        all.add(new ResponseAction("Yes, Miss, I have some tolerance already", Debugger.Response.Choose));
        all.add(new ResponseAction("Quite a bit harsher, please", Debugger.Response.Choose));
        all.add(new ResponseAction("Harshly, please", Debugger.Response.Choose));

        all.add(new ResponseAction("Yes Miss, I'm naked right now", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I was properly naked", Debugger.Response.Choose));

        all.add(new ResponseAction("Yes Miss, I have", Debugger.Response.Choose));

        // Punishments
        all.add(new ResponseAction("Yes Miss, I've been naughty", Debugger.Response.Choose));
        all.add(new ResponseAction("Please Miss, Punish me!", Debugger.Response.Choose));
        all.add(new ResponseAction("Please forgive me, Miss", Debugger.Response.Choose));

        all.add(new ResponseAction("Yes, Miss, I do", Debugger.Response.Choose));

        // cum
        all.add(new ResponseAction("Right Miss, please punish me", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, please keep training me", Debugger.Response.Choose));
        all.add(new ResponseAction("Please let me cum, Miss", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I'll lick up my cum", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I'll lick it all up", Debugger.Response.Choose));

        // TODO Add some wait time before dismissing the button
        all.add(new ResponseAction("I came, Miss", Debugger.Response.Choose));

        // epilogue
        all.add(new ResponseAction("Yes Miss, I obeyed you all the time", Debugger.Response.Choose));

        return all;
    }

    static Collection<ResponseAction> maid() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();
        all.addAll(defaults());

        all.add(new ResponseAction("*spurted*", Debugger.Response.Ignore));
        all.add(new ResponseAction("*give*", Debugger.Response.Ignore));
        all.add(new ResponseAction("*gmvm*", Debugger.Response.Ignore));
        all.add(new ResponseAction("Of course*", Debugger.Response.Choose));

        // 420
        all.add(new ResponseAction("Yes Miss, I've just put it on", Debugger.Response.Choose));

        // 575
        all.add(new ResponseAction("*crotch-roped*", Debugger.Response.Choose));
        all.add(new ResponseAction("*Breasts bound*", Debugger.Response.Choose));
        all.add(new ResponseAction("*Thighs tied*", Debugger.Response.Choose));

        return all;
    }

    static Collection<ResponseAction> td() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();
        all.addAll(defaults());

        all.add(new ResponseAction("I've spurted off, Miss", Debugger.Response.Ignore));
        all.add(new ResponseAction("I'm giving up, Miss", Debugger.Response.Ignore));

        return all;
    }

    static Collection<? extends ResponseAction> paddle() {
        ArrayList<ResponseAction> all = new ArrayList<ResponseAction>();
        all.addAll(defaults());

        all.add(new ResponseAction("Yes Miss, soaking wet", Debugger.Response.Choose));

        return all;
    }
}
