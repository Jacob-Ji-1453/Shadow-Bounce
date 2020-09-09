import bagel.util.*;


public class PowerUp extends GameObject {

    //Constants
    private static final String POWER_UP_IMAGE = "res/powerup.png";
    private static final int POWER_UP_VELOCITY_MAGNITUDE = 3;
    private static final int DISTANCE_BETWEEN_START_AND_END = 5;

    private Point destination;
    private Vector2 velocity;
    private double distance;

    public PowerUp(Point position) {
        super(position);
        this.setImage(POWER_UP_IMAGE);
    }

    //Get power up distance between current position and destination
    public double getDistance() {
        return this.distance;
    }

    //Set power up destination
    public void setDestination(Point destination) {
        this.destination = destination;
    }

    //Set power up velocity
    public void setVelocity() {
        this.velocity = (this.destination.asVector().sub(this.getPosition().asVector())).normalised()
                .mul(POWER_UP_VELOCITY_MAGNITUDE);
    }

    //Let power up move
    public void move() {
        Point oldPosition = this.getPosition();
        Point newPosition = oldPosition.asVector().add(this.velocity).asPoint();
        this.setPosition(newPosition);
    }

    //Update distance between current position and destination
    public void updateDistance() {
        this.distance = (this.destination.asVector().sub(this.getPosition().asVector())).length();
    }

    //Change hitting ball's type
    public FireBall changeBallType(Ball normalBall) {
        FireBall fireBall = new FireBall(normalBall.getPosition());
        fireBall.setVelocity(normalBall.getVelocity());
        fireBall.updateBoundingBox();
        return fireBall;
    }

    //Update power up
    @Override
    public void update() {
        this.move();
        this.updateDistance();
        this.updateBoundingBox();
        this.render();
    }
}
