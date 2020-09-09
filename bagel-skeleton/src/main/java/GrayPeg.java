import bagel.*;
import bagel.util.*;

public class GrayPeg extends Peg {

    //Constants
    private static final String GRAY_PEG_IMAGE = "res/grey-peg.png";
    private static final String GRAY_HORIZONTAL_PEG_IMAGE = "res/grey-horizontal-peg.png";
    private static final String GRAY_VERTICAL_PEG_IMAGE = "res/grey-vertical-peg.png";

    public GrayPeg(Point position, String shape) {
        super(position);
        this.setShape(shape);

        //Initialize shape
        if (shape.equals("horizontal")) {
            this.setImage(GRAY_HORIZONTAL_PEG_IMAGE);
        } else if (shape.equals("vertical")) {
            this.setImage(GRAY_VERTICAL_PEG_IMAGE);
        } else {
            this.setImage(GRAY_PEG_IMAGE);
        }
    }


}
