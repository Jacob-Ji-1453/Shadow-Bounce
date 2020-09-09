import bagel.*;
import bagel.util.*;

public class Bucket extends GameObject {

    //Constants
    private static final String BUCKET_IMAGE = "res/bucket.png";
    private static final Vector2 BUCKET_INIT_VELOCITY = new Vector2(-4,0);

    private Vector2 velocity;

    public Bucket(Point position) {
        super(position);
        this.setImage(BUCKET_IMAGE);
        this.velocity = BUCKET_INIT_VELOCITY;
    }

    //Let the bucket move
    public void move() {
        Point oldPosition = getPosition();
        Point newPosition = oldPosition.asVector().add(this.velocity).asPoint();
        this.setPosition(newPosition);
    }

    //Update bucket
    //Change velocity direction if hitting wall
    @Override
    public void update() {
        this.render();
        this.move();
        this.updateBoundingBox();
        if (this.getBoundingBox().left() < 0 || this.getBoundingBox().right() > Window.getWidth()){
            this.velocity = new Vector2(-this.velocity.x, this.velocity.y);
        }
    }

}
