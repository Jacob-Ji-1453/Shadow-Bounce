import bagel.*;
import bagel.util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class ShadowBounce extends AbstractGame {

    private static final int TOTAL_BOARD = 5;
    private static final Point BALL_INIT_POSITION = new Point(512, 32);
    private static final Point BUCKET_INIT_POSITION = new Point(512, 744);
    private static final int BALL_INITIAL_VELOCITY_MAGNITUDE = 10;
    private static final int CIRCLE_SIZE = 20;

    private static int numOfShoot = 20;
    private boolean isStart = false;
    private boolean isLoad = false;
    private boolean isPowerUpAppeared = false;
    private int currentLevel = 0;
    private boolean isWin = false;
    private boolean isPending = false;

    private ArrayList<Peg> pegs;
    private ArrayList<Ball> balls;
    private Board board;
    private PowerUp powerUp;
    private Bucket bucket;

    //Using class "circle" to show the number of shoots
    private ArrayList<Circle> circles;

    //Display the final result
    private FinalPicture pictures = null;

    public ShadowBounce() {
        balls = new ArrayList<>();
        pegs = new ArrayList<>();
        powerUp = null;
        board = null;
        bucket = null;
        circles = new ArrayList<>();
    }

    //Main method makes the game running
    public static void main(String[] args) {
        ShadowBounce game = new ShadowBounce();
        game.run();
    }

    //Initialize power up
    //Dear tutor, if you haven't seen the power up effect for some probability problem,
    //you can manually set variable "num" into 0.
    public void powerUpInitialize() {
        Random rand = new Random();
        int num = rand.nextInt(10);

        if (num == 0) {
            int x = rand.nextInt(Window.getWidth());
            int y = rand.nextInt(Window.getHeight());
            int a = rand.nextInt(Window.getWidth());
            int b = rand.nextInt(Window.getHeight());
            Point position = new Point(x,y);
            Point destination = new Point(a, b);
            powerUp = new PowerUp(position);
            powerUp.setDestination(destination);
            powerUp.setVelocity();
        }
    }

    //Show the number of shots
    public void showNumOfShoots() {
        if (circles != null) {
            circles.clear();
        }
        int x = CIRCLE_SIZE;
        for (int i = 0; i < numOfShoot; i++) {
            Point position = new Point(x, CIRCLE_SIZE);
            Circle circle = new Circle(position);
            circles.add(circle);
            x += CIRCLE_SIZE;
        }
        if (circles != null) {
            for (Circle circle : circles) {
                circle.update();
            }
        }
    }

    @Override
    protected void update(Input input) {

        //Load the board
        if (!isLoad && currentLevel < TOTAL_BOARD) {
            board = new Board(currentLevel);
            board.loadBoard(pegs);
            board.setGreenPeg(pegs);
            bucket = new Bucket(BUCKET_INIT_POSITION);
            isLoad = true;
        }

        //Update pegs
        for (Peg peg : pegs) {
            if (peg != null) {
                peg.update();
            }
        }

        //Update balls
        for (Ball ball : balls) {
            if (ball != null) {
                ball.update();
            }
        }

        //Update power up
        if (powerUp != null) {
            powerUp.update();
            if (powerUp.getDistance() < 5) {
                powerUp = null;
            }
        }

        //Update bucket
        if (bucket != null) {
            bucket.update();
        }
        showNumOfShoots();


        //Let game start when clicking
        if (input.wasPressed(MouseButtons.LEFT) && !isStart && numOfShoot > 0) {
            numOfShoot--;
            isStart = true;
            Ball newBall = new NormalBall(BALL_INIT_POSITION);
//            Ball newBall = new FireBall(BALL_INIT_POSITION);
            Vector2 ballInitVelocity = input.directionToMouse(newBall.getPosition())
                    .mul(BALL_INITIAL_VELOCITY_MAGNITUDE);
            newBall.setVelocity(ballInitVelocity);
            balls.add(newBall);
        }


        //If game starts
        if (isStart) {

            //Initialize power up if game started
            if (powerUp == null && !isPowerUpAppeared) {
                powerUpInitialize();
                isPowerUpAppeared = true;
            }

            Iterator<Ball> ballIterator = balls.iterator();
            Iterator<Peg> pegIterator = pegs.iterator();
            List<Ball> ballToBeAdded = new ArrayList<>();
            ArrayList<Peg> pegsToRemove = null;

            //Ball out-of-screen checking
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();
                if (ball != null && ball.isOutOfScreen()) {
                    ballIterator.remove();
                }


                pegIterator = pegs.iterator();

                //Green peg effect
                while (pegIterator.hasNext()) {
                    Peg peg = pegIterator.next();
                    if (ball.isIntersects(peg)) {
                        if (peg instanceof GreenPeg) {
                            ((GreenPeg) peg).createStrikeBalls(peg.getPosition(), ball, ballToBeAdded);
                            peg.reverseBallVelocity(ball);
                            pegIterator.remove();
                        }
                    }
                }



                //Adding number of shots if hitting bucket
                if (ball != null) {
                    if (ball.isIntersects(bucket)) {
                        bucket.reverseBallVelocity(ball);
                        numOfShoot++;
                    }

                    //Collision detection
                    pegIterator = pegs.iterator();
                    while (pegIterator.hasNext()) {
                        Peg peg = pegIterator.next();
                        if (peg != null) {

                            //If a normal ball intersects a peg
                            if (ball instanceof NormalBall) {
                                if (ball.isIntersects(peg) && !(peg instanceof GrayPeg)) {
                                    peg.reverseBallVelocity(ball);
                                    pegIterator.remove();
                                } else {
                                    peg.reverseBallVelocity(ball);
                                }
                            }

                            //If a fire ball intersects a peg
                            else if (ball instanceof FireBall) {
                                if (ball.isIntersects(peg) && !(peg instanceof GrayPeg)) {
                                    pegsToRemove = ((FireBall) ball).destroy(pegs, peg);
                                    peg.reverseBallVelocity(ball);
                                    pegIterator.remove();
                                } else {
                                    peg.reverseBallVelocity(ball);
                                }

                            }


                        }
                    }
                }

                //Fireball destroy effect
                pegIterator = pegs.iterator();
                while (pegIterator.hasNext()) {
                    Peg peg = pegIterator.next();
                    if (pegsToRemove != null) {
                        for (Peg pegToRemove : pegsToRemove) {
                            if (pegToRemove.getPosition() == peg.getPosition()) {
                                if (!(peg instanceof GreenPeg)) {
                                    pegIterator.remove();
                                } else {
                                    ((GreenPeg) peg).createStrikeBalls(peg.getPosition(), ball, ballToBeAdded);
                                    pegIterator.remove();
                                }
                            }
                        }
                    }
                }

                //Reset balls if they are all out of screen
                if (balls.size() == 0) {
                    isStart = false;
                    board.removeGreenPeg(pegs);
                    board.setGreenPeg(pegs);
                }
            }

            //Power up effect
            ballIterator = balls.iterator();
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();
                if (powerUp != null) {
                    if (ball.isIntersects(powerUp)) {
                        FireBall newBall = powerUp.changeBallType(ball);
                        powerUp = null;
                        ballToBeAdded.add(newBall);
                        ballIterator.remove();
                    }
                }
            }

            //Apply green peg or fire ball effects
            if (ballToBeAdded.size() != 0) {
                balls.addAll(ballToBeAdded);
                ballToBeAdded.clear();
            }
        }


        //Update current level, moving to next board
        if (board.getNumOfRedPeg(pegs) == 0 && currentLevel < TOTAL_BOARD) {
            isLoad = false;
            isStart = false;
            if (currentLevel != TOTAL_BOARD - 1) {
                balls.clear();
                pegs.clear();
            }
            currentLevel++;
            isPowerUpAppeared = false;
            isPending = true;
            if (currentLevel >= TOTAL_BOARD) {
                isWin = true;
            }
        }

        //Set a pending image between 2 boards
        if (isPending && currentLevel < TOTAL_BOARD) {
            FinalPicture pendingPicture = new FinalPicture("pending");
            pendingPicture.update();
            if (input.wasPressed(MouseButtons.LEFT)) {
                isPending = false;
            }
        }

        //If player wins
        if (isWin) {
            FinalPicture winPicture = new FinalPicture("win");
            winPicture.update();
        }


        //If player loses
        if (numOfShoot == 0 && (balls == null || balls.size() == 0) && !isWin) {
            isStart = false;
            isLoad = false;
            FinalPicture failPicture = new FinalPicture("lose");
            failPicture.update();
        }
    }
}