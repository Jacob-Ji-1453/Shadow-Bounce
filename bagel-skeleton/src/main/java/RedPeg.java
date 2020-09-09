import bagel.*;
import bagel.util.*;

public class RedPeg extends Peg {

    //Constants
    private static final String RED_PEG_IMAGE = "res/red-peg.png";
    private static final String RED_HORIZONTAL_PEG_IMAGE = "res/red-horizontal-peg.png";
    private static final String RED_VERTICAL_PEG_IMAGE = "res/red-vertical-peg.png";

    public RedPeg(Point position, String shape) {
        super(position);
        this.setShape(shape);

        //Initialize shape
        if (shape.equals("horizontal")) {
            this.setImage(RED_HORIZONTAL_PEG_IMAGE);
        } else if (shape.equals("vertical")) {
            this.setImage(RED_VERTICAL_PEG_IMAGE);
        } else {
            this.setImage(RED_PEG_IMAGE);
        }
    }
}
