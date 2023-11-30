package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import cat.xtec.ioc.objects.Warrior;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;

public class InputHandler implements InputProcessor {

    // Enter per a la gesitó del moviment d'arrastrar
    int previousY = 0;
    // Objectes necessaris
    private Warrior warrior;
    private GameScreen screen;
    private Vector2 stageCoord;

    private Stage stage;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        warrior = screen.getWarrior();
        stage = screen.getStage();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (screen.getCurrentState()) {
            case READY:
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;

            case RUNNING:

                previousY = screenY;
                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));

                Gdx.app.log("Current Stage Coord X...", String.valueOf(stageCoord.x));
                Gdx.app.log("Current Stage Coord Y...", String.valueOf(stageCoord.y));

                // Check if is pause button
                Actor pauseButtonHit = stage.hit(stageCoord.x, stageCoord.y,true);
                if(!screen.isGamePaused() && stageCoord.x >= Settings.GAME_WIDTH - 40 && pauseButtonHit != null){
                    screen.pauseGame();
                }

                if(!screen.isGamePaused()) {
                    Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
                    if (actorHit != null) {
                        Gdx.app.log("The Warrior is shooting", actorHit.getName());
                        warrior.shot();
                    }
                }
                break;

            case GAMEOVER:
                screen.reset();
                break;

            case PAUSED:
                screen.unpauseGame();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // Quan deixem anar el dit acabem un moviment
        // i posem la nau en l'estat normal
        warrior.goStraight();
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                warrior.goDown();
            } else {
                // En cas contrari cap a dalt
                warrior.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
