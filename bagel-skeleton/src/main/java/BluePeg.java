import bagel.*;
import bagel.util.*;

public class BluePeg extends Peg {

    //Constants
    private static final String BLUE_PEG_IMAGE = "res/peg.png";
    private static final String BLUE_HORIZONTAL_PEG_IMAGE = "res/horizontal-peg.png";
    private static final String BLUE_VERTICAL_PEG_IMAGE = "res/vertical-peg.png";

    public BluePeg(Point position, String shape) {
        super(position);
        this.setShape(shape);

        //Initialize shape
        if (shape.equals("horizontal")) {
            this.setImage(BLUE_HORIZONTAL_PEG_IMAGE);
        } else if (shape.equals("vertical")) {
            this.setImage(BLUE_VERTICAL_PEG_IMAGE);
        } else {
            this.setImage(BLUE_PEG_IMAGE);
        }
    }
}
