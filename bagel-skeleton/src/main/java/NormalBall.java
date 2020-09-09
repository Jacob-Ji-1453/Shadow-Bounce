import bagel.*;
import bagel.util.*;

public class NormalBall extends Ball {

    //Constants
    private static final String NORMAL_BALL_IMAGE = "res/ball.png";


    public NormalBall(Point position) {
        super(position);
        this.setImage(NORMAL_BALL_IMAGE);
    }


}
