import bagel.*;
import bagel.util.*;

public class Ball extends GameObject {

    //Constants
    private static final double DOWNWARDS_ACCELERATION = 0.15;

    private Vector2 velocity;

    public Ball(Point position) {
        super(position);
    }

    //Get the ball velocity
    public Vector2 getVelocity() {
        return this.velocity;
    }

    //set the ball velocity
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    //Update the ball position
    public void move() {
        Point oldPosition = getPosition();
        Point newPosition = oldPosition.asVector().add(this.velocity).asPoint();
        this.setPosition(newPosition);
    }

    //Reverse x if ball hits walls or pegs
    public void reverseHorizontalDirection() {
        Vector2 newVelocity = new Vector2(-this.getVelocity().x, this.getVelocity().y);
        this.setVelocity(newVelocity);
    }

    //Reverse y if ball hits pegs
    public void reverseVerticalDirection() {
        Vector2 newVelocity = new Vector2(this.getVelocity().x, -this.getVelocity().y);
        this.setVelocity(newVelocity);
    }


    //Let ball gets a downward acceleration
    public void verticalAccelerate() {
        double x = this.getVelocity().x;
        double y = this.getVelocity().y + DOWNWARDS_ACCELERATION;
        Vector2 newVelocity = new Vector2(x, y);
        this.setVelocity(newVelocity);
    }

    //Check if the ball is out of screen
    public boolean isOutOfScreen() {
        return (this.getBoundingBox().top() > Window.getHeight());
    }

    //Update ball
    @Override
    public void update() {
        this.verticalAccelerate();;
        this.move();
        this.updateBoundingBox();
        if (this.getBoundingBox().left() < 0 || this.getBoundingBox().right() > Window.getWidth()) {
            this.reverseHorizontalDirection();
        }
        this.render();
    }
}
