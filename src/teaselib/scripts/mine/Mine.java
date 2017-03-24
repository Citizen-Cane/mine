package teaselib.scripts.mine;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pcm.controller.Player;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.MappedScriptBooleanValue;
import pcm.state.persistence.MappedScriptItemValue;
import pcm.state.persistence.MappedScriptState;
import pcm.state.persistence.MappedScriptStateValue;
import teaselib.Actor;
import teaselib.Body;
import teaselib.Images;
import teaselib.Toys;
import teaselib.core.ResourceLoader;
import teaselib.core.TeaseLib;
import teaselib.core.texttospeech.Voice;

/**
 * @author Citizen-Cane
 *
 *         Boot the whole thing up.
 * 
 */

public class Mine extends Player {
    private static final Logger logger = LoggerFactory.getLogger(Mine.class);

    private static final String Namespace = "Mine";
    private static final String ResourcesRoot = "Mine";
    private static final String MainScript = "Mine";

    private static final Actor MineMistress = new Actor("Mistress", "Miss,",
            Voice.Gender.Female, Locale.UK, Actor.Key.DominantFemale,
            Images.None);

    private static final String[] Assets = { "Mine Scripts.zip",
            "Mine Resources.zip", "Mine Mistress.zip" };

    private static final String[] OptionalAssets = { "Mine Speech.zip" };

    public enum Punishment {
        BadIntroduction,
        NotNaked,
        CummingWithoutPermission
    }

    public enum Assignments {
        NextSession,
        Enema
    }

    public static void main(String argv[]) {
        try {
            recordVoices(MineMistress, MainScript, new File(argv[0]),
                    ResourcesRoot, Assets);
        } catch (ScriptParsingException e) {
            logger.error(e.getMessage(), e);
        } catch (ValidationIssue e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        System.exit(0);
    }

    public Mine(TeaseLib teaseLib) {
        this(teaseLib, "mistress/Vana/");
    }

    public Mine(TeaseLib teaseLib, String mistressPath) {
        super(teaseLib, new ResourceLoader(Mine.class, ResourcesRoot, Assets,
                OptionalAssets), MineMistress, Namespace, mistressPath);
        resources.addAssets(OptionalAssets);

        // Toy categories - multiple items
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(363,
                        items(Toys.Wrist_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(364,
                        items(Toys.Ankle_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(365, items(Toys.Collars)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(367, items(Toys.Gags)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(368, items(Toys.Buttplugs)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(370,
                        items(Toys.Spanking_Implements)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(380,
                        items(Toys.Chastity_Devices)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(389,
                        items(Toys.Vibrators, Toys.EStim_Devices)));

        // Toys simple mappings
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(361, item(Toys.Nipple_clamps)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(362, item(Toys.Clothespins)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(366, item(Toys.Rope)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(360, item(Toys.Chains)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(382, item(Toys.Blindfold)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(384, item(Toys.Humbler)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(388, item(Toys.Anal_Dildo)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(383, item(Toys.Enema_Kit)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(387, item(Toys.Pussy_Clamps)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(385,
                        item(Toys.Ball_Stretcher)));

        // Toy simple mappings - ingame toys (not selectable in toy list)
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(371, item(Toys.Hairbrush)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(372, item(Toys.Wooden_Spoon)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(373, item(Toys.Ruler)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue<Toys>(374, item(Toys.Enema_Bulb)));

        // session state mappings
        // TODO These are currently indefinite, but should scope to the session

        // TODO It might not be possible to find a toy relationship for each
        // mapping -> use None
        /*
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(20,
         * state(Body.SomethingOnNipples)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(22, state(Body.SomethingInMouth)));
         */

        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(44,
                        state(Body.SomethingOnPenis), Toys.Chastity_Cage));

        // TODO These Body states have multiple targets,
        // but we can map to only one toy - make them reading the state only but
        // clear state on write?

        /*
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(24, state(Body.SomethingOnBalls)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(25, state(Body.AnklesTied)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(26, state(Body.WristsTied)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(27,
         * state(Body.SomethingAroundWaist)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(28, state(Body.Collared)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(30, state(Body.CannotJerkOff)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(31, state(Body.CantSitOnChair)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(32, state(Body.CannotStand)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(33, state(Body.CannotKneel)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(34,
         * state(Body.CannotTypeOrUseMouse)));
         * state.addScriptValueMapping(MappedScriptState.Global, new
         * MappedScriptStateValue.ForSession(35, state(Body.Tethered)));
         */

        // Indefinite states

        // 95 is a timed state used in conjunction with additional flags, but
        // the state mapping can be used to set the remaining wait duration
        // to zero.
        state.addScriptValueMapping(MainScript,
                new MappedScriptStateValue.Indefinitely(95,
                        state(Assignments.NextSession)));

        state.addScriptValueMapping(MainScript, new MappedScriptBooleanValue(
                267, persistentBoolean((Assignments.Enema))));

        // TODO Apply state in script
        // state time mappings
        state.addStateTimeMapping(MappedScriptState.Global, 45,
                state(Toys.Chastity_Device_Lock), Toys.Chastity_Cage);
    }

    @Override
    public void run() {
        play(MainScript);
    }

    public void punish(Punishment punishment) {
        switch (punishment) {
        case BadIntroduction:
            setScriptState(MainScript, 500, 1);
            break;
        case NotNaked:
            if (getScriptState(MainScript, 502) == 1) {
                setScriptState(MainScript, 503, 1);
            } else {
                setScriptState(MainScript, 502, 1);
            }
            break;
        case CummingWithoutPermission:
            setScriptState(MainScript, 519, 1);
            break;
        default:
            break;
        }
    }

    private int getScriptState(String scriptName, int n) {
        return getInteger(scriptName + "." + n);
    }

    private void setScriptState(String scriptName, int n, int value) {
        set(scriptName + "." + n, value);
    }

    public <T> void override(T toy, boolean available) {
        state.overwrite(toy, available);
    }
}
