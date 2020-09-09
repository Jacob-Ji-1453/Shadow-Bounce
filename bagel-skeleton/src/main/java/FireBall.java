import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;


public class FireBall extends Ball {

    //Constants
    private static final String FIRE_BALL_IMAGE = "res/fireball.png";
    private static final int DESTROY_RADIAN = 70;


    public FireBall(Point position) {
        super(position);
        this.setImage(FIRE_BALL_IMAGE);
    }

    //Storing to-be-removed ball in a list
    public ArrayList<Peg> destroy(ArrayList<Peg> pegs, Peg targetPeg) {
        ArrayList<Peg> pegToRemove = new ArrayList<>();
        for (Peg peg : pegs) {
            if ((targetPeg.getBoundingBox().centre().asVector().sub(peg.getBoundingBox().centre().asVector()))
                    .length() < DESTROY_RADIAN && !(peg instanceof GrayPeg)) {
                pegToRemove.add(peg);
            }
        }
        return pegToRemove;
    }
}

