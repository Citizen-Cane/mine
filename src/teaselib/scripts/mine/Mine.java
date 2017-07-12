package teaselib.scripts.mine;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pcm.controller.Player;
import pcm.model.ScriptParsingException;
import pcm.model.ValidationIssue;
import pcm.state.persistence.MappedScriptItemValue;
import pcm.state.persistence.MappedScriptState;
import pcm.state.persistence.MappedScriptStateValue;
import teaselib.Actor;
import teaselib.Body;
import teaselib.Features;
import teaselib.Household;
import teaselib.Images;
import teaselib.ScriptFunction;
import teaselib.Sexuality;
import teaselib.State;
import teaselib.Toys;
import teaselib.core.ResourceLoader;
import teaselib.core.TeaseLib;
import teaselib.core.TeaseLib.PersistentBoolean;
import teaselib.core.texttospeech.Voice;
import teaselib.util.Items;

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

    private static final Actor MineMistress = new Actor("Mistress", "Miss,", Voice.Gender.Female, Locale.UK,
            Actor.Key.DominantFemale, Images.None);

    private static final String[] Assets = { "Mine Scripts.zip", "Mine Resources.zip", "Mine Mistress.zip" };

    private static final String[] OptionalAssets = { "Mine Speech.zip" };

    private static final String MistressVanaImageResources = "mistress/Vana/";

    // TODO Map all punishments to state
    public enum Punishment {
        BadIntroduction,
        NotNaked,
        CummingWithoutPermission
    }

    // TODO Map all assignments to state
    public enum Assignments {
        ClearSessionTimerCheck,
        CarryOutEnema
    }

    public static void main(String argv[]) {
        try {
            recordVoices(MineMistress, MainScript, new File(argv[0]), ResourcesRoot, Assets);
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
        this(teaseLib, MistressVanaImageResources);
    }

    public Mine(TeaseLib teaseLib, File basePath) {
        this(teaseLib, basePath, MistressVanaImageResources);
    }

    public Mine(TeaseLib teaseLib, String mistressPath) {
        this(teaseLib, ResourceLoader.getProjectPath(Mine.class), mistressPath);
    }

    public Mine(TeaseLib teaseLib, File basePath, String mistressPath) {
        super(teaseLib, new ResourceLoader(basePath, ResourcesRoot, Assets, OptionalAssets), MineMistress, Namespace,
                mistressPath);
        // resources.addAssets(OptionalAssets);

        mapToysToMultipleItems();
        mapToysToSingleItem();
        mapInGameToysNotInMineToyList();
        mapSelfReferencingBodyParts();
        mapAssignments();
        mapPersistentToys();
    }

    private void mapToysToMultipleItems() {
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(363, items(Toys.Wrist_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(364, items(Toys.Ankle_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(365, items(Toys.Collar)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(367, items(Toys.Gag)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(368, items(Toys.Buttplug)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(370, items(Toys.Spanking_Implement)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(380, item(Toys.Chastity_Device)));

        Items dildos;
        if (persistentEnum(Sexuality.Sex.class).value() == Sexuality.Sex.Female) {
            dildos = items(Toys.Dildo).all(Features.Anal);
        } else {
            dildos = items(Toys.Dildo).all();
        }
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(388, dildos));

        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(389, items(Toys.Cockring).all(Features.Vibrating),
                        items(Toys.VaginalInsert).all(Features.Vibrating),
                        items(Toys.Vibrator).all(Toys.Vibrators.HandsFree), items(Toys.EStim_Device)));
    }

    private void mapToysToSingleItem() {
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(361, item(Toys.Nipple_Clamps)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(362, item(Household.Clothes_Pegs)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(366, item(Toys.Rope)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(360, item(Toys.Chains)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(382, item(Toys.Blindfold)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(384, item(Toys.Humbler)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(383, item(Toys.Enema_Kit)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(387, item(Toys.Pussy_Clamps)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(385, item(Toys.Ball_Stretcher)));
    }

    private void mapInGameToysNotInMineToyList() {
        // TODO map as tools
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(371, item(Household.Hairbrush)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(372, item(Household.Wooden_Spoon)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(373, item(Household.Ruler)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(374, item(Toys.Enema_Bulb)));
    }

    private void mapSelfReferencingBodyParts() {
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(20, state(Body.OnNipples), Body.OnNipples));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(21, state(Body.InButt), Body.InButt));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(22, state(Body.InMouth), Body.InMouth));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(23, state(Body.OnPenis), Body.OnPenis));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(24, state(Body.OnBalls), Body.OnBalls));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(25, state(Body.AnklesTied), Body.AnklesTied));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(26, state(Body.WristsTied), Body.WristsTied));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(27, state(Body.AroundWaist), Body.AroundWaist));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(28, state(Body.AroundNeck), Body.AroundNeck));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(30, state(Body.CantJerkOff), Body.CantJerkOff));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(31, state(Body.CantSitOnChair), Body.CantSitOnChair));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(32, state(Body.CantStand), Body.CantStand));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(33, state(Body.CantKneel), Body.CantKneel));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(34, state(Body.CantTypeOrUseMouse), Body.CantTypeOrUseMouse));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.ForSession(35, state(Body.Tethered), Body.Tethered));
    }

    private void mapAssignments() {
        triggerCarryOutEnema();
        clearSessionTimerCheck();
    }

    private void clearSessionTimerCheck() {
        PersistentBoolean clearTimeCheck = persistentBoolean(Assignments.ClearSessionTimerCheck);
        if (clearTimeCheck.isTrue()) {
            setScriptState(MainScript, 95, 0);
            clearTimeCheck.clear();
        }
    }

    private void triggerCarryOutEnema() {
        PersistentBoolean triggerCarryOutEnema = persistentBoolean(Assignments.CarryOutEnema);
        if (triggerCarryOutEnema.isTrue()) {
            setScriptState(MainScript, 267, 1);
            triggerCarryOutEnema.clear();
        }
    }

    private void mapPersistentToys() {
        int chastified = 44;
        int haveKey = 45;
        State chastityCage = state(Toys.Chastity_Device);
        Body[] peers = new Body[] { Body.OnPenis, Body.CantJerkOff };

        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptStateValue.Indefinitely(chastified, chastityCage, peers));

        state.addStateTimeMapping(MappedScriptState.Global, haveKey, chastityCage, peers);
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

    private int getScriptState(String scriptName, long n) {
        return getInteger(scriptName + "." + n);
    }

    private void setScriptState(String scriptName, int n, long value) {
        set(scriptName + "." + n, value);
    }

    public <T> void override(T toy, boolean available) {
        state.overwrite(toy, available);
    }
}
