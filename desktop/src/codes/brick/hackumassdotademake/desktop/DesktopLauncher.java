package codes.brick.hackumassdotademake.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import codes.brick.hackumassdotademake.MyGdxGame;

public class DesktopLauncher {
  public static void main (String[] arg) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Phaseshift");
    config.setWindowedMode(1024, 256);
    new Lwjgl3Application(new MyGdxGame(), config);
  }
}
