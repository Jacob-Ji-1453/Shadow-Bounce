import bagel.*;
import bagel.util.*;

public class FinalPicture extends GameObject {

    //Constants
    private static final String WIN_IMAGE = "res/win.png";
    private static final String LOSE_IMAGE = "res/lose.png";
    private static final String PENDING_IMAGE = "res/continue.png";
    private static final Point FINAL_POSITION = new Point(Window.getWidth()/2, Window.getHeight()/2);


    public FinalPicture(String result) {
        super(FINAL_POSITION);

        //Display win or lose image
        if (result.equals("win")) {
            this.setImage(WIN_IMAGE);
        } else if (result.equals(("lose"))) {
            this.setImage(LOSE_IMAGE);
        } else {
            this.setImage(PENDING_IMAGE);
        }
    }

    @Override
    public void update() {
        render();
    }
}
