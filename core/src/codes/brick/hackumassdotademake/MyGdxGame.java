package codes.brick.hackumassdotademake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Hero player1;
	Hero player2;

  public static final int VIEWPORT_WIDTH = 1024;
  public static final int VIEWPORT_HEIGHT = 256;

  private InputWatcher iw;

  private Viewport viewport;


	@Override
	public void create () {
		batch = new SpriteBatch();
		viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		player1 = new Puck();
		player1.setPosition(0, 0);
		player2 = new Puck();
		player2.setPosition(300, 100);
		player2.flip(true, false);
		iw = new InputWatcher(player1, player2);
		Gdx.input.setInputProcessor(iw);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// draw bg
		player1.draw(batch);
		player2.draw(batch);
		batch.end();
		player1.update();
		player2.update();
	}

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

	@Override
	public void dispose () {
		batch.dispose();
	}
}
