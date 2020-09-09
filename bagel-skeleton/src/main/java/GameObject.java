import bagel.*;
import bagel.util.*;

public abstract class GameObject {

    private Point position;
    private Image image;
    private Rectangle BoundingBox;

    public GameObject(Point position) {
        this.position = position;
    }

    //Set object image and create its bounding box at the same time
    public void setImage(String fileName) {
        this.image = new Image(fileName);
        this.BoundingBox = image.getBoundingBoxAt(position);
    }

    //Get object position
    public Point getPosition() {
        return this.position;
    }

    //Set object position
    public void setPosition(Point position) {
        this.position = position;
    }

    //Get object bounding box
    public Rectangle getBoundingBox() {
        return this.BoundingBox;
    }

    //Update object bounding box if it is moving
    public void updateBoundingBox() {
        this.BoundingBox = image.getBoundingBoxAt(position);
    }




    //Determine this object whether intersects others
    public boolean isIntersects(GameObject other) {
        return this.BoundingBox.intersects(other.BoundingBox);
    }

    //Draw the object's image on the window
    public void render() {
        this.image.draw(this.getBoundingBox().centre().x, this.getBoundingBox().centre().y);
    }

    //Reverse ball velocity if hitting pegs
    public void reverseBallVelocity(Ball ball) {
        Point position = this.getPosition();
        Rectangle boundingBox = this.getBoundingBox();
        Vector2 ballVelocity = ball.getVelocity();
        Side side = boundingBox.intersectedAt(position, ballVelocity);

        if (ball.isIntersects(this)) {
            if(side.equals(Side.BOTTOM) || side.equals(Side.TOP)) {
                ball.reverseVerticalDirection();
            }
            else if (side.equals(Side.LEFT) || side.equals(Side.RIGHT)) {
                ball.reverseHorizontalDirection();
            }
        }
    }

    public abstract void update();
}
