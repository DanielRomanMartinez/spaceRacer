package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;

import cat.xtec.ioc.helpers.AssetManager;

public class Bullet extends Scrollable {

    private Circle collisionCircle;
    private float velocity;

    public Bullet(float x, float y, float width, float height, float velocity) {

        super(x, y, width, height, 0);

        collisionCircle = new Circle();
        this.velocity = velocity;

        setOrigin();
    }

    public void setOrigin() {
        this.setOrigin(width/2 + 1, height/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        position.x = position.x + velocity * delta;
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.bullet, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }
}
