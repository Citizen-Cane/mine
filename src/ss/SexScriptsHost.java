package ss;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pcm.model.Interval;
import teaselib.Host;
import teaselib.ScriptInterruptedException;
import teaselib.TeaseLib;
import teaselib.TeaseScript;
import teaselib.util.Delegate;

/**
 * SexScripts renderer: Workarounds some of SS shortcomings (or it's just my
 * taste :^) TODO sounds are not playable from stream in SS - workarounded by
 * playing sounds myself - same for prerendered speech TODO UI layout wastes a
 * lot of screen estate, sub optimal for portrait images TODO TExt pane is noit
 * fixed, I liked it better the way CM did it - workarounded by adding pixels
 * left and right of portrait images TODO Invisible progress bar eats screen
 * estate TODO Buttons should be layouted vertically, better for longer button
 * texts
 * 
 * @author someone
 * 
 */
public class SexScriptsHost implements Host {

	private ss.IScript ss;

	private BufferedWriter log = null;
	private final File path = new File("./TeaseLib.log");

	public SexScriptsHost(ss.IScript script) {
		this.ss = script;
	}

//	@Override
//	public boolean askYesNo(String yes, String no) {
//		return ss.getBoolean(null, yes, no);
//	}

	@Override
	public long getTime() {
		return ss.getTime();
	}

	@Override
	public int getRandom(int min, int max) {
		return min + ss.getRandom(max - min + 1);
	}

	@Override
	public void log(String line) {
		if (log == null) {
			try {
				log = new BufferedWriter(new FileWriter(path));
			} catch (IOException e) {
				ss.showPopup("Cannot open log file " + path.getAbsolutePath());
			}
		}
		try {
			log.write(line + "\n");
			log.flush();
		} catch (IOException e) {
			ss.showPopup("Cannot write log file " + path.getAbsolutePath());
		}
		System.out.println(line);
	}

	@Override
	public void playSound(String file) {
		ss.playBackgroundSound(file);
	}

	@Override
	public void playBackgroundSound(String path, InputStream sound) {
		// SexScripts doesn't accept WAV input streams or sound objects,
		// so we're going to cache them in the Sounds folder
		File file = new File(path);
		if (!file.exists()) {
			try {
				Files.copy(sound, Paths.get(file.toURI()));
			} catch (IOException e) {
				// ignore
				if (e != null) {
					TeaseLib.log(this, e);
				}
			}
		}
		ss.playBackgroundSound(file.getAbsolutePath());
	}

	@Override
	public void setImage(Image image) {
		if (image != null) {
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			boolean portrait = width < height;
			if (portrait) {
				// Enlarge the image with alpha pixels left and right to make
				// the text area a bit smaller
				// Improves readability on wide screen displays, as the text is
				// laid out a bit more portrait (instead of landscape)
				// TODO SS scales down when expanding too much
				BufferedImage expanded = new BufferedImage(height, height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D) expanded.getGraphics();
				// g2d.drawRect(0, 0, expanded.getWidth() - 1,
				// expanded.getHeight() -1);
				g2d.drawImage(image, (expanded.getWidth() - width) / 2, 0, null);
				ss.setImage(expanded, false);
			} else {
				ss.setImage(image, false);
			}
		} else {
			ss.setImage(null);
		}
	}

	@Override
	public void show(String message) {
		// TODO Align text to top
		// TODO Fix width of jlabel
		// -> Text always starts at the same position, better anchor point for
		// viewing
		// PROBLEM: Text top right, buttons bottom right
		ss.show(message);
	}

//	@Override
//	public void showButton(String message) {
//		ss.showButton(message);
//	}

	@Override
	public List<Boolean> showCheckboxes(String caption, List<String> texts,
			List<Boolean> values) {
		return ss.getBooleans(caption, texts, values);
	}

	@Override
	public int showMenu(List<String> texts) {
		return ss.getSelectedValue(null, texts);
	}

	@Override
	public boolean showButton(String message, int seconds) {
		return ss.showButton(message, seconds) < seconds;
	}

	@Override
	public void stopSounds() {
		try {
			((ss.Script) ss).stopSoundThreads();
		} catch (Exception e) {
			TeaseLib.log(this, e);
		}
	}

	@Override
	public void useFile(String file) {
		ss.useFile(file);
	}

	@Override
	public void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			throw new ScriptInterruptedException();
		}
	}

	@Override
	public List<Delegate> getClickableChoices(List<String> choices) {
		try {
			// Get main frame
			Class<?> scriptClass = ss.getClass().getSuperclass();
			Field mainField = scriptClass.getDeclaredField("main");
			mainField.setAccessible(true);
			ss.MainFrame mainFrame = (ss.MainFrame) mainField.get(ss);
			// Get buttons
			Class<?> mainFrameClass = mainFrame.getClass();
			List<Delegate> result = new ArrayList<>(choices.size());
			// Multiple choices are managed via an array of buttons,
			// whereas a single choice is implemented as a single button 
			Field buttonsField = mainFrameClass.getDeclaredField("buttons");
			buttonsField.setAccessible(true);
			javax.swing.JButton[] ssButtons = (javax.swing.JButton[]) buttonsField
					.get(mainFrame);
			// Must also test the single button
			Field buttonField = mainFrameClass.getDeclaredField("button");
			buttonField.setAccessible(true);
			final javax.swing.JButton ssButton = (javax.swing.JButton) buttonField.get(mainFrame);
			List<javax.swing.JButton> buttons = new ArrayList<>(ssButtons.length + 1);
			for(javax.swing.JButton button : ssButtons)
			{
				buttons.add(button);
			}
			// TODO Only for timeout button?
			buttons.add(ssButton);
			// Check pretty buttons for corresponding text
			for (int index : new Interval(choices)) {
				result.add(index, null);
			}
			// There might be more buttons than expected, probably some kind of
			// caching
			for (final javax.swing.JButton button : buttons) {
				for (int index : new Interval(choices)) {
					String buttonText = button.getText(); 
					if (buttonText.contains(choices.get(index))) {
						Delegate click = new Delegate() {
							@Override
							public void run() {
								button.doClick();
							}
						};
						result.set(index, click);
					}
				}
			}
			// If a choice wasn't found, the element at the corresponding index
			// would be null
			return result;
		} catch (IllegalAccessException e) {
			TeaseLib.log(this, e);
		} catch (NoSuchFieldException e) {
			TeaseLib.log(this, e);
		} catch (SecurityException e) {
			TeaseLib.log(this, e);
		}
		return new ArrayList<>();
	}

	@Override
	public int choose(List<String> choices, int timeout) {
		if (choices.size() == 1 && timeout > 0)
		{
			// TODO Doesn't wait for speech recognition in progress, need to solve this in TeaseScript
			return ss.showButton(choices.get(0), (double) timeout) < timeout ? 0 : TeaseScript.Timeout;
		}
		else
		{
			return ss.getSelectedValue(null, choices);
		}
	}

	@Override
	public void showText(String text) {
		show(text);
	}
}
