import bagel.*;
import bagel.util.*;

import java.io.*;
import java.util.*;

public class Board {

    //Constants
    private static final String[] FILE_SOURCE = {"res/0.csv", "res/1.csv", "res/2.csv", "res/3.csv", "res/4.csv"};
    private static final int[] FILE_SIZE = {165, 35, 50, 66, 47};
    private static final int PARTIAL = 5;

    private int currentLevel;
    public Board(int level) {
        this.currentLevel = level;
    }

    //Load board based on the current level
    public void loadBoard(ArrayList<Peg> pegs) {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(FILE_SOURCE[this.currentLevel]))) {

            String line;
            int i = 0;
            int size = FILE_SIZE[this.currentLevel];
            int redPegCount = size/PARTIAL;
            Integer[] indexes;
            int totalCount = 0;

            indexes = integerGenerator(size, redPegCount);

            //Randomly initialize red pegs
            while (i < redPegCount) {
                while ((line = csvReader.readLine()) != null) {

                    String[] cells = line.split(",");
                    String pegName = cells[0];
                    int x = Integer.parseInt(cells[1]);
                    int y = Integer.parseInt(cells[2]);
                    Point position = new Point(x, y);

                    boolean isContained = false;
                    for (Integer integer : indexes) {
                        if (integer.equals(totalCount)) {
                            isContained = true;
                            break;
                        }
                    }
                    Collections.shuffle(Arrays.asList(indexes));

                    //Initialize blue pegs and gray pegs
                    if (pegName.contains("blue") && !isContained) {
                        if (pegName.contains("vertical")) {
                            pegs.add(new BluePeg(position, "vertical"));
                        } else if (pegName.contains("horizontal")) {
                            pegs.add(new BluePeg(position, "horizontal"));
                        } else {
                            pegs.add(new BluePeg(position, "normal"));
                        }
                    } else if (pegName.contains("blue") && isContained) {
                        if (pegName.contains("vertical")) {
                            pegs.add(new RedPeg(position, "vertical"));
                        } else if (pegName.contains("horizontal")) {
                            pegs.add(new RedPeg(position, "horizontal"));
                        } else {
                            pegs.add(new RedPeg(position, "normal"));
                        }
                    } else {
                        if (pegName.contains("vertical")) {
                            pegs.add(new GrayPeg(position, "vertical"));
                        } else if (pegName.contains("horizontal")) {
                            pegs.add(new GrayPeg(position, "horizontal"));
                        } else {
                            pegs.add(new GrayPeg(position, "normal"));
                        }
                    }
                    totalCount++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Randomly initialize a single green peg
    public void setGreenPeg(ArrayList<Peg> pegs) {
        Random random = new Random();
        if (this.getNumOfBluePeg(pegs) > 0) {
            while (true) {
                int index = random.nextInt(pegs.size());
                if (pegs.get(index) instanceof BluePeg) {
                    Peg oldPeg = pegs.get(index);
                    GreenPeg newPeg = new GreenPeg(oldPeg.getPosition(), oldPeg.getShape());
                    pegs.remove(index);
                    pegs.add(newPeg);
                    break;
                }
            }
        }
    }

    //Remove a single green peg if there is one over screen
    public void removeGreenPeg(ArrayList<Peg> pegs) {
        if (this.getNumOfGreenPeg(pegs) > 0) {
            Iterator<Peg> pegIterator = pegs.iterator();
            while (pegIterator.hasNext()) {
                Peg peg = pegIterator.next();
                if (peg instanceof GreenPeg) {
                    Peg newPeg = new BluePeg(peg.getPosition(), peg.getShape());
                    pegIterator.remove();
                    pegs.add(newPeg);
                    break;
                }
            }

        }
    }

    //Generate non-repeating integers
    public static Integer[] integerGenerator(int end, int resultRange) {
        Integer[] arr1 = new Integer[end];
        Integer[] arr2 = new Integer[resultRange];
        for (int i = 0; i < end; i++) {
            arr1[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr1));
        for (int j = 0; j < resultRange; j++) {
            arr2[j] = arr1[j];
        }
        return arr2;
    }

    //Get the number of red peg
    public int getNumOfRedPeg(ArrayList<Peg> pegs) {
        int num = 0;
        for (Peg peg : pegs) {
            if (peg instanceof RedPeg) {
                num++;
            }
        }
        return num;
    }

    //Get the number of green peg
    public int getNumOfGreenPeg(ArrayList<Peg> pegs) {
        int num = 0;
        for (Peg peg : pegs) {
            if (peg instanceof GreenPeg) {
                num++;
            }
        }
        return num;
    }

    //Get the number of blue peg
    public int getNumOfBluePeg(ArrayList<Peg> pegs) {
        int num = 0;
        for (Peg peg : pegs) {
            if (peg instanceof BluePeg) {
                num++;
            }
        }
        return num;
    }

}
