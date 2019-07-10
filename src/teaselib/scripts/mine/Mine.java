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
import teaselib.Actor;
import teaselib.Features;
import teaselib.Household;
import teaselib.Images;
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

    public static final String MAIN = "Mine";

    public static final String MAID = "mine-maid";
    public static final String PADDLE = "mine-paddle";
    public static final String NIPT = "mine-nipt";
    public static final String TD = "mine-td";
    public static final String SB = "mine-sb";
    public static final String DILDO = "mine-dildo";
    public static final String EQUIP = "mine-equip";
    public static final String OOE = "mine-ooe";

    public static final String[] Scripts = { MAIN, MAID, PADDLE, NIPT, TD, SB, DILDO, EQUIP, OOE };

    private static final String Namespace = "Mine";
    private static final String MainScript = "Mine";

    public static final Actor MineMistress = new Actor("Mistress", "Miss,", Voice.Female, Locale.UK,
            Actor.Key.DominantFemale, Images.None);
    public static final String[] Assets = { "Mine Scripts.zip", "Mine Resources.zip", "Mine Mistress.zip" };
    public static final String[] OptionalAssets = { "Mine Speech.zip" };
    private static final String MistressVanaImageResources = "mistress/Vana/";

    public enum Punishment {
        BadIntroduction,
        NotNaked,
        CummingWithoutPermission
    }

    public enum Assignments {
        ClearSessionTimerCheck,
        CarryOutEnema
    }

    public static void main(String argv[]) {
        try {
            recordVoices(MineMistress, new File(argv[0]), Namespace, Scripts, Assets);
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
        super(teaseLib, new ResourceLoader(basePath, Namespace, Assets, OptionalAssets), MineMistress, Namespace,
                mistressPath, MainScript);

        mapToysToMultipleItems();
        mapToysToSingleItem();
        mapAssignments();
    }

    private void mapToysToMultipleItems() {
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(363, items(Toys.Wrist_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(364, items(Toys.Ankle_Restraints)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(365, items(Toys.Collar)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(367, items(Toys.Gag)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(368, items(Toys.Buttplug)));
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(370,
                items(Toys.Spanking_Implement, Household.Wooden_Spoon, Household.Hairbrush)));
        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(380, item(Toys.Chastity_Device)));

        Items dildos = items(Toys.Dildo);
        state.addScriptValueMapping(MappedScriptState.Global, new MappedScriptItemValue(388, dildos));

        state.addScriptValueMapping(MappedScriptState.Global,
                new MappedScriptItemValue(389, items(Toys.Cock_Ring).matching(Features.Vibrating),
                        items(Toys.VaginalInsert).matching(Features.Vibrating),
                        items(Toys.Vibrator).matching(Features.HandsFree), items(Toys.EStim_Device)));
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

    private void mapAssignments() {
        triggerCarryOutEnema();
        clearSessionTimerCheck();
    }

    private void clearSessionTimerCheck() {
        PersistentBoolean clearTimeCheck = persistentBoolean(Assignments.ClearSessionTimerCheck);
        if (clearTimeCheck.isTrue()) {
            setScriptState(MAIN, 95, 0);
            clearTimeCheck.clear();
        }
    }

    private void triggerCarryOutEnema() {
        PersistentBoolean triggerCarryOutEnema = persistentBoolean(Assignments.CarryOutEnema);
        if (triggerCarryOutEnema.isTrue()) {
            setScriptState(MAIN, 267, 1);
            triggerCarryOutEnema.clear();
        }
    }

    public void punish(Punishment punishment) {
        switch (punishment) {
        case BadIntroduction:
            setScriptState(MAIN, 500, 1);
            break;
        case NotNaked:
            if (getScriptState(MAIN, 502) == 1) {
                setScriptState(MAIN, 503, 1);
            } else {
                setScriptState(MAIN, 502, 1);
            }
            break;
        case CummingWithoutPermission:
            setScriptState(MAIN, 519, 1);
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
