import bagel.*;
import bagel.util.*;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class GreenPeg extends Peg {

    //Constants
    private static final String GREEN_PEG_IMAGE = "res/green-peg.png";
    private static final String GREEN_HORIZONTAL_PEG_IMAGE = "res/green-horizontal-peg.png";
    private static final String GREEN_VERTICAL_PEG_IMAGE = "res/green-vertical-peg.png";
    private static final Vector2 LEFT_STRIKING_BALL_VELOCITY = new Vector2(-5*Math.sqrt(2), -5*Math.sqrt(2));
    private static final Vector2 RIGHT_STRIKING_BALL_VELOCITY = new Vector2(5*Math.sqrt(2), -5*Math.sqrt(2));

    public GreenPeg(Point position, String shape) {
        super(position);
        this.setShape(shape);

        //Initialize shape
        if (shape.equals("horizontal")) {
            this.setImage(GREEN_HORIZONTAL_PEG_IMAGE);
        } else if (shape.equals("vertical")) {
            this.setImage(GREEN_VERTICAL_PEG_IMAGE);
        } else {
            this.setImage(GREEN_PEG_IMAGE);
        }
    }

    //Create 2 balls when green peg destroyed
    public void createStrikeBalls(Point position, Ball ball, List<Ball> balls) {
        Ball leftStrikeBall = null;
        Ball rightStrikeBall = null;
        if (ball instanceof NormalBall) {
            leftStrikeBall = new NormalBall(position);
            rightStrikeBall = new NormalBall(position);
        } else if (ball instanceof FireBall) {
            leftStrikeBall = new FireBall(position);
            rightStrikeBall = new FireBall(position);
        }
        if (leftStrikeBall != null) {
            leftStrikeBall.setVelocity(LEFT_STRIKING_BALL_VELOCITY);
            rightStrikeBall.setVelocity(RIGHT_STRIKING_BALL_VELOCITY);
            leftStrikeBall.update();
            rightStrikeBall.update();
            balls.add(leftStrikeBall);
            balls.add(rightStrikeBall);
        }
    }
}