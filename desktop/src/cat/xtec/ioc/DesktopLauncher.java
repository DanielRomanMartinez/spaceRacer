package cat.xtec.ioc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import cat.xtec.ioc.utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setForegroundFPS(60);
		config.setTitle("SpaceRace");

		config.setWindowedMode(Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT * 2);


		new Lwjgl3Application(new SpaceRace(), config);
	}
}
