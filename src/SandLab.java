import java.awt.*;
import java.util.*;
import java.lang.*;

public class SandLab
{
    public static void main(String[] args)
    {
        SandLab lab = new SandLab(120, 80);
        lab.run();
    }

    //add constants for particle types here
    public static final int EMPTY = 0;
    public static final int METAL = 1;
    public static final int WATER = 2;
    public static final int SAND = 3;
    public static final int ICE = 4;
    public static final int GLASS = 5;
    private int timer = 0;
    private static final int SLEEP = 99999;

    //do not add any more fields
    private int[][] grid;
    private SandDisplay display;

    public SandLab(int numRows, int numCols)
    {
        String[] names;
        names = new String[6];
        names[EMPTY] = "Empty";
        names[METAL] = "Metal";
        names[WATER] = "Water";
        names[SAND] = "Sand";
        names[ICE] = "Ice";
        names[GLASS] = "Glass";
        display = new SandDisplay("Falling Sand", numRows, numCols, names);
        grid = new int[numRows][numCols];
    }

    //called when the user clicks on a location using the given tool
    private void locationClicked(int row, int col, int tool)
    {
        grid[row][col] = tool;
    }

    //copies each element of grid into the display
    public void updateDisplay()
    {
        for(int r = 0; r < grid.length; r++) {

            for(int c = 0; c < grid[r].length; c++) {
                if(grid[r][c] == EMPTY) {
                    display.setColor(r, c, Color.BLACK);
                }
                else if(grid[r][c] == METAL) {
                    display.setColor(r, c, Color.GRAY);
                }
                else if(grid[r][c] == WATER) {
                    display.setColor(r, c, Color.BLUE);
                }
                else if(grid[r][c] == SAND) {
                    display.setColor(r, c, Color.YELLOW);
                }
                else if(grid[r][c] == ICE) {
                    display.setColor(r, c, Color.CYAN);
                }
                else if(grid[r][c] == GLASS) {
                    display.setColor(r, c, Color.BLACK);
                }

            }

        }
    }

    //called repeatedly.
    //causes one random particle to maybe do something.
    public void step()
    {

        int lengthRow = grid.length - 1;
        int lengthCol = grid[0].length - 1;
        int randRow = (int)(Math.random() * lengthRow);
        int randCol = (int)(Math.random() * lengthCol);
        int randRow2 = (int)(Math.random() * lengthRow);
        int randCol2 = (int)(Math.random() * grid[0].length);
        int randRow3 = (int)(Math.random() * lengthRow);
        int randCol3 = (int)(Math.random() * grid[0].length);
        int randomOdds = (int)(Math.random() * 4);
        timer++;

        if(grid[randRow][randCol] == SAND) {
            if(grid[randRow + 1][randCol] != EMPTY) {
                grid[randRow][randCol] = SAND;
            }
            else {
                grid[randRow + 1][randCol] = SAND;
                grid[randRow][randCol] = EMPTY;
            }
        }

        if(grid[randRow2][randCol2] == WATER) {
            if(randRow2 <= lengthRow && grid[randRow2 + 1][randCol2] == EMPTY && randomOdds == 1) {
                grid[randRow2 + 1][randCol2] = WATER;
                grid[randRow2][randCol2] = EMPTY;
            }
            else if(randCol2 + 1 <= lengthCol && grid[randRow2][randCol2 + 1] == EMPTY && randomOdds == 2) {
                grid[randRow2][randCol2 + 1] = WATER;
                grid[randRow2][randCol2] = EMPTY;
            }
            else if(randCol2 - 1 >= 0 && grid[randRow2][randCol2 - 1] == EMPTY && randomOdds == 3) {
                grid[randRow2][randCol2 - 1] = WATER;
                grid[randRow2][randCol2] = EMPTY;

            }

        }

        if(grid[randRow][randCol] == SAND && grid[randRow + 1][randCol] == WATER) {
            grid[randRow][randCol] = WATER;
            grid[randRow + 1][randCol] = SAND;
        }

        if(grid[randRow3][randCol3] == ICE) {

            if(timer > SLEEP) {
                grid[randRow3][randCol3] = WATER;
                timer = 0;

            }

        }
    }
    //do not modify
    public void run()
    {
        while (true)
        {
            for (int i = 0; i < display.getSpeed(); i++)
                step();
            updateDisplay();
            display.repaint();
            display.pause(1);  //wait for redrawing and for mouse
            int[] mouseLoc = display.getMouseLocation();
            if (mouseLoc != null)  //test if mouse clicked
                locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
        }
    }

}
