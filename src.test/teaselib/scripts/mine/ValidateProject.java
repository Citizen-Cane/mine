package teaselib.scripts.mine;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pcm.controller.PCMScriptScanner;
import pcm.controller.Player;
import pcm.controller.ScriptCache;
import pcm.model.Script;
import pcm.model.ScriptExecutionException;
import pcm.model.ScriptParsingException;
import pcm.model.Symbols;
import pcm.model.ValidationIssue;
import teaselib.Message;
import teaselib.Message.Type;
import teaselib.MessagePart;
import teaselib.core.AbstractMessage;
import teaselib.core.ResourceLoader;
import teaselib.core.configuration.DebugSetup;
import teaselib.core.texttospeech.ScriptScanner;
import teaselib.core.texttospeech.TextToSpeechPlayer;
import teaselib.scripts.mine.test.Preset;

public class ValidateProject {
    private static final Logger logger = LoggerFactory.getLogger(ValidateProject.class);

    @Test
    public void testSyntaxAndResources()
            throws ScriptExecutionException, IOException, ScriptParsingException, ValidationIssue {
        Mine mine = new Preset(new DebugSetup().withOutput()).script(Mine.MAIN).mine();
        mine.validateProject();
    }

    @Test
    public void testPrerecordedSpeech() throws IOException, ScriptParsingException, ValidationIssue {
        String[] assets = { "Mine Scripts.zip", "Mine Resources.zip", "Mine Speech.zip" };

        ResourceLoader resources = new ResourceLoader(new File("../SexScripts/scripts/"), "Mine");
        resources.addAssets(assets);
        // TODO Resource loader intentionally treats resource locations as optional,
        // to let user unpack and change scripts as well as resources
        // but this leads to errors -> check for specific resource in asset right at the start

        TextToSpeechPlayer tts = new TextToSpeechPlayer(DebugSetup.getConfiguration());
        assertNotNull(tts);
        tts.load();
        tts.acquireVoice(Mine.MineMistress, resources);

        Symbols dominantSubmissiveRelations = Symbols.getDominantSubmissiveRelations();
        Map<Symbols, Integer> processedMessages = new LinkedHashMap<>();
        Map<Symbols, Integer> processedResources = new LinkedHashMap<>();
        for (Entry<String, String> entry : dominantSubmissiveRelations.entrySet()) {
            Symbols dominantSubmissiveRelation = new Symbols();
            dominantSubmissiveRelation.put(entry.getKey(), entry.getValue());

            int messageCount = 0;
            int resourceCount = 0;
            logger.info("Testing symbol set {}", dominantSubmissiveRelation);
            ScriptCache scripts = new ScriptCache(resources, Player.ScriptFolder, dominantSubmissiveRelation);
            for (String scriptName : Mine.Scripts) {
                Script script = scripts.get(Mine.MineMistress, scriptName);
                ScriptScanner scriptScanner = new PCMScriptScanner(script);
                for (Message message : scriptScanner) {
                    AbstractMessage speech = tts.prerenderedSpeechMessage(message.actor, message, resources);
                    speech.stream().filter(part -> part.type == Type.Speech)
                            .forEach(part -> testSpeechResource(resources, part));
                    messageCount++;
                    resourceCount += speech.stream().filter(part -> part.type == Type.Speech).count();
                }
            }

            processedMessages.put(dominantSubmissiveRelation, messageCount);
            processedResources.put(dominantSubmissiveRelation, resourceCount);
        }

        int messageCount = 0;
        int resourceCount = 0;

        for (Entry<Symbols, Integer> result : processedMessages.entrySet()) {
            Integer processedResourcesCount = processedResources.get(result.getKey());
            logger.info("{}: scanned {} messages, {} resources", result.getKey(), result.getValue(),
                    processedResourcesCount);
            messageCount += result.getValue();
            resourceCount += processedResourcesCount;
        }

        logger.info("{}: scanned {} messages, {} resources", dominantSubmissiveRelations, messageCount, resourceCount);
    }

    private static void testSpeechResource(ResourceLoader resources, MessagePart part) {
        try {
            resources.get(part.value).close();
        } catch (IOException e) {
            throw new AssertionError("Speech audio not found: " + part.value, e);
        }
    }
}
