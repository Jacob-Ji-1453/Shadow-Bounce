import bagel.*;
import bagel.util.*;

public class Peg extends GameObject {

    private String shape;


    public Peg(Point position) {
        super(position);
    }

    //Get the peg's shape
    public String getShape() {
        return this.shape;
    }

    //Set the peg's shape
    public void setShape(String shape) {
        this.shape = shape;
    }

    //Update peg
    @Override
    public void update() {
        this.updateBoundingBox();
        this.render();
    }

}
