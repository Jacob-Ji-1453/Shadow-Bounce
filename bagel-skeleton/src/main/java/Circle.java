import bagel.*;
import bagel.util.*;

public class Circle extends GameObject{

    //Constants
    private static final String CIRCLE_IMAGE = "res/circle.png";

    public Circle(Point position) {
        super(position);
        this.setImage(CIRCLE_IMAGE);
    }

    @Override
    public void update() {
        this.render();
    }


}
