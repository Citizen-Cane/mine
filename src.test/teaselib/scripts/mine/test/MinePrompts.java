package teaselib.scripts.mine.test;

import java.util.ArrayList;
import java.util.Collection;

import teaselib.core.Debugger;
import teaselib.core.Debugger.Response;
import teaselib.core.Debugger.ResponseAction;

/**
 * @author Citizen-Cane
 *
 */
public class MinePrompts {

    public static Collection<ResponseAction> all() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());
        all.addAll(mine());
        all.addAll(maidGood());
        all.addAll(td());
        all.addAll(paddle());
        return all;
    }

    public static Collection<ResponseAction> defaults() {
        ArrayList<ResponseAction> all = new ArrayList<>();

        all.add(new ResponseAction("Yes, Miss", Debugger.Response.Choose));
        all.add(new ResponseAction("Exit", Debugger.Response.Choose));

        return all;
    }

    public static Collection<ResponseAction> mine() {
        ArrayList<ResponseAction> all = new ArrayList<>();
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

    public static Collection<ResponseAction> maidGood() {
        return maid(Debugger.Response.Ignore, Debugger.Response.Ignore);
    }

    public static Collection<ResponseAction> maidGivingUp() {
        return maid(Debugger.Response.Choose, Debugger.Response.Ignore);
    }

    public static Collection<ResponseAction> maidSpurtedOff() {
        return maid(Debugger.Response.Ignore, Debugger.Response.Choose);
    }

    private static Collection<ResponseAction> maid(Response giveUpResponse, Response spurtedOffResponse) {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());

        all.add(new ResponseAction("*spurted*", spurtedOffResponse));
        all.add(new ResponseAction("I give up, Miss", giveUpResponse));

        all.add(new ResponseAction("Of course*", Debugger.Response.Choose));

        // Gagged
        all.add(new ResponseAction("*spmrtmd*", spurtedOffResponse));
        all.add(new ResponseAction("I gmvm mp, Mmss", giveUpResponse));

        all.add(new ResponseAction("Of cmmrsm, Mmss", Debugger.Response.Choose));

        // 420
        all.add(new ResponseAction("Yes Miss, I've just put it on", Debugger.Response.Choose));

        // 575
        all.add(new ResponseAction("*crotch-roped*", Debugger.Response.Choose));
        all.add(new ResponseAction("*Breasts bound*", Debugger.Response.Choose));
        all.add(new ResponseAction("*Thighs tied*", Debugger.Response.Choose));

        return all;
    }

    public static Collection<ResponseAction> td() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());

        all.add(new ResponseAction("I've spurted off, Miss", Debugger.Response.Ignore));
        all.add(new ResponseAction("I'm giving up, Miss", Debugger.Response.Ignore));

        return all;
    }

    public static Collection<ResponseAction> nipt() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());

        return all;
    }

    public static Collection<ResponseAction> paddle() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());

        all.add(new ResponseAction("Yes Miss, soaking wet", Debugger.Response.Choose));

        return all;
    }

    public static Collection<ResponseAction> maidNoToys() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.add(new ResponseAction("*haven't*", Debugger.Response.Choose));
        all.add(new ResponseAction("*sorry*", Debugger.Response.Choose));
        return all;
    }

    public static Collection<ResponseAction> sb() {
        ArrayList<ResponseAction> all = new ArrayList<>();
        all.addAll(defaults());

        all.add(new ResponseAction("Yes Mistress, I have them", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I have", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I'm prepared", Debugger.Response.Choose));

        all.add(new ResponseAction("Again please, Miss", Debugger.Response.Ignore));
        all.add(new ResponseAction("I've given up, Miss", Debugger.Response.Ignore));
        all.add(new ResponseAction("I've freed myself, Miss", Debugger.Response.Choose));
        all.add(new ResponseAction("Yes Miss, I did", Debugger.Response.Choose));

        return all;
    }
}
