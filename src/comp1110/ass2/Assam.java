package comp1110.ass2;

/**
 * Author: Bei Jin and Madison
 * Description: This is a Java program about string representation of Assam.
 * Madison give the basic information without string representation
 * Bei refined this part.
 */

public class Assam {
    private int x;
    private int y;
    private char orientation;

    public Assam(int x, int y, char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    //create Assam string representation
    public Assam(String string){
        this.x = Integer.parseInt(""+string.charAt(1));//the first Assam string code means
        this.y = Integer.parseInt(""+string.charAt(2));//the second Assam string code means
        this.orientation = string.charAt(3);//the third Assam string code means
    }

    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX() { return x; }
    public int getY() {
        return y;
    }
    public char getOrientation(){ return orientation; }
    public void setOrientation(char newOrientation) {
        this.orientation = newOrientation;
    }

    public String rotateAssam(int rotation) {
        Assam assam =this;

        // Finding the current orientation of Assam (4th char in the string)
        char currentOrientation = this.getOrientation();

        // 4 possible orientation that Assam could be facing on the board
        char[] ornt = {'N', 'E', 'S', 'W'};

        // Index of current orientation of Assam in orientations array
        int currentIdx = -1;
        for (int idx = 0; idx < ornt.length; idx++) {
            if (currentOrientation == ornt[idx]) {
                currentIdx = idx;
                break;
            }
        }

        // Calculating the new index in the orientation array based on rotation input for Assam
        int newIdx;
        if (rotation == 90) {
            // Rotate 90 degrees clockwise by adding 1 to the current index
            newIdx = (currentIdx + 1) % ornt.length;
        } else if (rotation == 270) {
            // Rotate 90 degrees anti-clockwise by subtracting 1 from the current index
            newIdx = (currentIdx - 1 + ornt.length) % ornt.length;
        } else {
            // Invalid rotation, do nothing and return the current stage
            return this.toString();
        }

        // Extracting the new orientation after rotation
        char newOrnt = ornt[newIdx];

        // Update the orientation of Assam using the setOrientation method
        this.setOrientation(newOrnt);

        // Return the string with the updated orientation
        return assam.toString();
    }


    //the method to object
    public String moveAssam(int dieResult) {

        Assam assam = this;
        int x = assam.getX();
        int y = assam.getY();
        char currentOrnt = assam.getOrientation();


        // 4 possible orientations that Assam could be facing on the board
        char[] orientations = {'N', 'E', 'S', 'W'};

        // Index of the current orientation in the orientations array
        int currentIndex = -1;
        for (int index = 0; index < orientations.length; index++) {
            if (currentOrnt == orientations[index]) {
                currentIndex = index;
                break;
            }
        }

        // Following Assam's movement tile by tile
        for (int i = 0; i < dieResult; i++) {
            if (currentIndex == 0) {
                y--;
                if (y == -1) {
                    if (x == 0 || x == 2 || x == 4) {
                        x++;
                        currentIndex = (currentIndex + 2) % 4;
                    } else if (x == 1 || x == 3 || x == 5) {
                        x--;
                        currentIndex = (currentIndex + 2) % 4;
                    } else if (x == 6) {
                        currentIndex = (currentIndex + 3) % 4;
                    }
                    y = 0;
                    continue; // Avoid possible out-of-bounds in the same iteration
                }
            }


            // Assam is facing East
            else if (currentIndex == 1) {
                // Assam moves to the right on the board
                x = x + 1;
                // Assam following the mosaic since it is out of bounds
                if (x == 7) {
                    if (y == 1 || y == 3 || y == 5) {
                        y = y + 1;
                        currentIndex = currentIndex + 2;
                    } else if (y == 2 || y == 4 || y == 6) {
                        y = y - 1;
                        currentIndex = currentIndex + 2;
                    } else if (y == 0) {
                        currentIndex = currentIndex + 1;
                    }
                    x = 6;
                    continue;
                }
            }

            // Assam is facing South
            else if (currentIndex == 2) {
                // Assam moves down the board
                y = y + 1;
                // Assam following the mosaic since it is out of bounds
                if (y == 7) {
                    if (x == 1 || x == 3 || x == 5) {
                        x = x + 1;
                        currentIndex = currentIndex - 2;
                    } else if (x == 2 || x == 4 || x == 6) {
                        x = x - 1;
                        currentIndex = currentIndex - 2;
                    } else if (x == 0) {
                        currentIndex = currentIndex - 1;
                    }
                    y = 6;
                    continue;
                }

                // Assam is facing West
            } else if (currentIndex == 3) {
                // Assam moves left on the board
                x = x - 1;
                // Assam following the mosaic since it is out of bounds
                if (x == -1) {
                    if (y == 0 || y == 2 || y == 4) {
                        y = y + 1;
                        currentIndex = currentIndex - 2;
                    } else if (y == 1 || y == 3 || y == 5) {
                        y = y - 1;
                        currentIndex = currentIndex - 2;
                    } else if (y == 6) {
                        currentIndex = currentIndex - 3;
                    }
                    x = 0;
                    continue;
                }
            }
        }


        // Return the string with updated orientation and location
        return "A" + x + y + orientations[currentIndex];
    }


    @Override
    public String toString() {
        StringBuilder a=new StringBuilder();

        a.append('A');
        a.append(x);
        a.append(y);
        a.append(orientation);
        return a.toString();
    }

}




