package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Warrior extends Actor {

    // Distintes posicions de la warrior, recta, pujant i baixant
    public static final int WARRIOR_STRAIGHT = 0;
    public static final int WARRIOR_UP = 1;
    public static final int WARRIOR_DOWN = 2;

    // Paràmetres de la warrior
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;



    public Warrior(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la warrior a l'estat normal
        direction = WARRIOR_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);


    }




    public void act(float delta) {
        super.act(delta);

        // Movem la warrior depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case WARRIOR_UP:
                if (this.position.y - Settings.WARRIOR_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.WARRIOR_VELOCITY * delta;
                }
                break;
            case WARRIOR_DOWN:
                if (this.position.y + height + Settings.WARRIOR_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.WARRIOR_VELOCITY * delta;
                }
                break;
            case WARRIOR_STRAIGHT:
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, 10);
        setBounds(position.x, position.y, width, height);


    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la warrior: Puja
    public void goUp() {
        direction = WARRIOR_UP;
    }

    // Canviem la direcció de la warrior: Baixa
    public void goDown() {
        direction = WARRIOR_DOWN;
    }

    // Posem la warrior al seu estat original
    public void goStraight() {
        direction = WARRIOR_STRAIGHT;
    }

    // Obtenim el TextureRegion depenent de la posició de la warrior
    public TextureRegion getWarriorTexture() {

        switch (direction) {

            case WARRIOR_STRAIGHT:
                return AssetManager.warrior;
            case WARRIOR_UP:
                return AssetManager.warriorUp;
            case WARRIOR_DOWN:
                return AssetManager.warriorDown;
            default:
                return AssetManager.warrior;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.WARRIOR_STARTX;
        position.y = Settings.WARRIOR_STARTY;
        direction = WARRIOR_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getWarriorTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
