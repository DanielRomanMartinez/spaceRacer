package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Enemy extends Scrollable {

    private Circle collisionCircle;

    Random r;

    int assetEnemy;
    private float explodeDeltaTime = 0;
    private boolean isRemoved = false;

    public Enemy(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetEnemy = r.nextInt(3);

        setOrigin();

        // Rotacio
        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.2f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        // Equivalent:
        // this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        this.addAction(repeat);

    }

    public void makeItExplode(){
        collisionCircle = null;
        explodeDeltaTime = Gdx.graphics.getDeltaTime();
    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions (punt central de l'enemy i el radi.
        if(explodeDeltaTime == 0) collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);


    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ENEMY, Settings.MAX_ENEMY);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetEnemy = r.nextInt(3);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(explodeDeltaTime > 0 && isRemoved == false){
            if(AssetManager.explosionAnim.isAnimationFinished(explodeDeltaTime)) {
                isRemoved = true;
                this.remove();
            }
            explodeDeltaTime += Gdx.graphics.getDeltaTime();

            batch.draw(
                (TextureRegion) AssetManager.explosionAnim.getKeyFrame(explodeDeltaTime),
                position.x,
                position.y,
                this.getOriginX(),
                this.getOriginY(),
                width,
                height,
                this.getScaleX(),
                this.getScaleY(),
                this.getRotation()
            );

            return;
        }

        batch.draw(AssetManager.enemy[assetEnemy], position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Warrior warrior) {

        if (position.x <= warrior.getX() + warrior.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'enemy estigui a la mateixa alçada que la warrior
            return (Intersector.overlaps(collisionCircle, warrior.getCollisionRect()));
        }
        return false;
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Bullet bullet) {

        if (position.x <= bullet.getX() + bullet.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'enemy estigui a la mateixa alçada que la bala
            return (Intersector.overlaps(collisionCircle, bullet.getCollisionCircle()));
        }
        return false;
    }
}
