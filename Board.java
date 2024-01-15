/**
* Name: Madeleine Drefke
* Pennkey: mdrefke
* Execution: N/A (run through Minesweeper.java)
*
* Description: This class stores a bunch of methods needed to help Minesweeper.java
* run. This class contains
* 1) A constructor for a board that creates a 2D array of mines, a 2D integer array 
* of number of nearby mines, and a 2D array of cells. 
* 2) A function to set up the minesweeper board such that there are 81 tiles in a 
* square with a reset button up top whose functionality will be added later. 
* 3) A function to turn the double values gathered by clicking on the PennDraw canvas
* into integer values representing the row and column of the 2D array that 
* corresponds to the tile clicked on. 
* 4) A function to do the reverse (with the resulting values being the middle
* of the space corresponding to the input dimensions). 
* 5) A method to create a boolean 2D array where "true" indicates the presence of a
* mine at that spot and "false" indicates a safe cell. 
* 6) A method that counts the number of adjacent mines to each cell (including itself
* if there is a mine at that cell). 
* 7) A method that creates cells within the 2D cell array with their row and column
* in the array, whether the tile has a mine and how many mines are adjacent.
* 8) A getter method for the private cellArray.
* 9) A method that effectively advances the game. This method checks whether the cell
* just clicked on is a mine and ends the game if so, but otherwise marks the cell as 
* revealed and continues the game.
* 10) OpenBoxes opens cells adjacent to chains of 0-value cells that have been 
* revealed. 
* 11) drawBoard() iterates through the 2D array of cells and draws them each. 
* 12) resetBoard() resets values related to the board so the game can be reset.
**/

public class Board {
    
    private Cell[][] cellArr;
    public int safeLeft = 70;
    
    /**
    * Inputs: an array of length 2 that contains the array coordinates of the first-
    * clicked square on the drawing, the number of moves completed by the user
    * Outputs: an object of type Board that has a 2D array of cells
    * 
    * Description: This constructor builds a board and creates the following arrays:
    * a 2D booolean mine array that marks where mines are placed (randomly), an int
    * 2D array that marks how many mines a cell is touching (cells with a mine will
    * also be given a number but it wont be used). These arrays require the click
    * values to be converted into array indices in the main method. After creating
    * these arrays, the constructor assigns index values, a boolean value indicating
    * the presense of a mine, and an integer value indicating the number of nearby
    * mines to each cell in the board.
    */
    public Board(int[] arrs, int movesDone) {
        if (movesDone == -1) {
            return;
        }
        int arrX = arrs[0];
        int arrY = arrs[1];
        
        boolean[][] mines = new boolean[9][9];
        int[][] cellNums = new int[9][9];
                
        //build an array to store mines, count surrounding mines, and assign values
        mines = createMineArray(arrX, arrY);
        cellNums = createNumArray(mines);
        
        //create new cell array and initialize cell values with fillCells
        Cell[][] newCellArr = new Cell[9][9];
        fillCells(mines, cellNums, newCellArr);
        
        //mark the first-clicked cell as having been revealed
        newCellArr[arrX][arrY].revealed = true;
        this.cellArr = newCellArr;
        
    }
    
    
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: This function sets the canvas to be a size of (400, 440) and
    * rescales it such that the left side is -0.5, the right side is 9.5, the bottom
    * is -0.5, and the top is 10.5. The function then draws a gray rectangle covering
    * the whole canvas. It then draws a lighter-gray rectangle from 0 to 9 in both
    * the x and the y directions that will bound the area to be clicked on during 
    * the game. Lots of black squares are then drawn to mark the edges of each tile
    * which can be clicked on with different qualities. Finally, the little
    * signature minesweeper smiley face was added to the top of the screen as the 
    * button which will soon act as a reset button.
    * 
    */
    public static void setUpPennDraw() {
        PennDraw.setCanvasSize(400, 440);
        PennDraw.setXscale(-0.5, 9.5); //starts at 0.0, 0.0 so center of box 1 is 1,1
        PennDraw.setYscale(-0.5, 10.5);
        
        //larger box
        PennDraw.setPenColor(150, 150, 150);
        PennDraw.filledRectangle(4.5, 5, 5, 5.5);
        
        //smaller box
        PennDraw.setPenColor(165, 165, 165);
        PennDraw.filledRectangle(4.5, 4.5, 4.5, 4.5);
        
        //little squares
        PennDraw.setPenColor(0, 0, 0);
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                PennDraw.rectangle(i - 0.5, j - 0.5, 0.5, 0.5);
            }
        }
        
        //reset button
        PennDraw.picture(4.5, 9.75, "Minesweeper_Smile.jpg", 40, 40);
        
        
    }
    
    
        /**
    * Inputs: a double value representing the X coordinate of a mouse-click in the
    * PennDraw canvas, a double value representing the Y coordinate of a mouse-click
    * in the PennDraw canvas
    * Outputs: a 1D array of length 2 where arrayIJ[0] represents the row index of
    * the array block clicked on in the PennDraw canvas and where arrayIJ[1]
    * represents the column index of the array block clicked on in the PennDraw
    * canvas
    * Description: This uses the truncated x coordinate from the canvas and assigns 
    * it to the column value (j), and assigns the truncated version of 9 - the y
    * coordinate to the row value (i). 
    */
    public static int[] turnMouseToDim(double x, double y) {
        int[] arrayIJ = new int[2];
        arrayIJ[0] = (int) (9 - y);
        arrayIJ[1] = (int) x;
        
        return arrayIJ;
    }
    
    /**
    * Inputs: an integer value representing the row index of the 2D array 
    * corresponding to the place that was originally clicked, an integer value
    * representing the column index of the 2D array corresponding to the place that
    * was originally clicked
    * Outputs: a 1D array of length 2 where canvasXY[0] represents the x-coordinate
    * in 2D space on the PennDraw canvas and where canvasXY[1] represents the 
    * y-coordinate in 2D space on the PennDraw canvas
    * 
    * Description: Given that the bottom-left corner of the canvas was scaled to be
    * (-0.5, -0.5), the equation for the x-coordinate must be the column value + 0.5
    * and the y-coordinate must be 8.5 - the row value given that the center of the
    * bottom left cell must be at (0.5, 0.5) in canvas space after it has been scaled
    */
    public static double[] turnDimToMouse(int i, int j) {
        double[] canvasXY = new double[2];
        canvasXY[0] = j + 0.5;
        canvasXY[1] = 8.5 - i;
        
        return canvasXY;
    }
        
        
    
    
    /**
    * Inputs: an integer representing the row of the tile/cell first clicked on and
    * an integer representing the column of the tile first clicked on
    * Outputs: a 2D boolean array with "false" marking a safe tile and "true" marking
    * a cell that contains a mine
    * 
    * Description: The method first creates a boolean 2D array that will be filled. 
    * Then it sets the number of mines to be placed at 10 and the available spaces
    * that could hold a mine as the number of cells in the array - 1 (because the
    * space clicked on first cannot contain a mine). It then iterates through the
    * array and uses the Math.random() function paired with the tile's probability
    * of containing a mine in order to fill the cells. The probability was determined
    * by the number of mines remaining divided by the number of cells remaining
    */
    private boolean[][] createMineArray(int arrX, int arrY) {
        boolean[][] mineArrayBool = new boolean[9][9];
        int randValue;
        int minesLeft = 10;
        int cellsLeft = mineArrayBool.length * mineArrayBool[0].length - 1;
        
        //iterate through the boolean array and place true if mine should be present
        for (int i = 0; i < mineArrayBool.length; i++) {
            for (int j = 0; j < mineArrayBool[i].length; j++) {
                if (i != arrX || j != arrY) {
                    randValue = (int) (cellsLeft * Math.random());
                    mineArrayBool[i][j] = randValue < minesLeft;
                    if (mineArrayBool[i][j]) {
                        minesLeft--;
                    }
                    cellsLeft--;
                }
            }
        }
        
        return mineArrayBool;
    }
    
    /**
    * Inputs: the boolean array marking the presense of mines that was created in 
    * createMineArray
    * Outputs: a 2D integer array that holds the number of mines that a cell/tile
    * is touching (including itself if it is a space holding a mine)
    * Description: This method creates a 2D array that will hold the integers, then 
    * iterates through the array, with smaller iterations counting the number of 
    * mines directly touching a cell.
    */
    private int[][] createNumArray(boolean[][] mineArray) {
        int[][] numArray = new int[9][9];
        
        //iterates through the array
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int count = 0;
                //iterate through smaller box
                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        //checks if the box exists (no array out of bounds errors)
                        if (k >= 0 && k < 9 && l >= 0 && l < 9) {
                            if (mineArray[k][l]) {
                                count++;
                            }
                        }
                    }
                }
                numArray[i][j] = count;
            }
        }
        return numArray;
    }
    
    /**
    * Inputs: the 2D boolean array marking where mines are present, the 2D integer
    * array showing how many adjacent tiles contain mines (including itself), and 
    * a 2D array of cells
    * Outputs: N/A
    * Description: This method assigns the row and column where each cell can be 
    * found in addition to whether the cell/tile at that position contains a mine
    * and how many mines (including its own if it has one) are next to that tile.
    */
    private void 
        fillCells(boolean[][] mineArray, int[][] numArray, Cell[][] cellArr) {
        //adjust cell attributes
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellArr[i][j] = new Cell(i, j, mineArray[i][j], numArray[i][j]);
            }
        }
    }
    
    /**
    * Inputs: N/A
    * Outputs: the cell array used in this board
    * Description: get method
    */
    public Cell[][] getCellArr() {
        return this.cellArr;
    }
    
    /**
    * Inputs: the 1D array of length 2 holding the row and column of the cell/tile 
    * just clicked on in the PennDraw canvas, the cell array being used for this 
    * board, the number of moves that have been completed by the user, and whether
    * the game has been won or lost yet (false if not completed)
    * Outputs: a boolean value representing isFinished (will be true if the tile
    * just clicked on was a mine, otherwise will be false)
    * 
    * Description: This method marks the cell/tile just clicked on as having been
    * revealed. If the cell was not previously revealed and if it was not a tile
    * containing a mine, the number of safe tiles left is decremented. If the clicked
    * on tile is not touching any mines, the method calls the other method 
    * "openBoxes" to open all the adjacent boxes also with value 0 as well as the
    * boxes toughing those. It then increments the number of moves completed. If the
    * tile contained a mine, isFinished is turned to true and the board is drawn
    * and the placements of the other mines are revealed. It would also display a 
    * message. If the tile does not contain a mine, though, the board is updated and 
    * the method returns false.
    */
    public boolean playMove(int[] arrs, Cell[][] cellArr,
    int movesDone, boolean isFinished) {
        int arrX = arrs[0];
        int arrY = arrs[1];
        
        //marks the clicked on box as open
        if (!cellArr[arrX][arrY].revealed) {
            cellArr[arrX][arrY].revealed = true;
            if (!cellArr[arrX][arrY].getIsMine()) {
                safeLeft--;
            }
        }
        if (cellArr[arrX][arrY].getNum() == 0) {
            openBoxes(arrX, arrY);
        }
        movesDone++;
        
        //check for a mine and end game if so
        if (cellArr[arrX][arrY].getIsMine()) {
            isFinished = true;
            drawBoard();
            PennDraw.setPenColor(0, 0, 0);
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    PennDraw.rectangle(i - 0.5, j - 0.5, 0.5, 0.5);
                }
            }
            revealMines(cellArr);
            PennDraw.setPenColor(255, 255, 255);
            PennDraw.filledRectangle(4.5, 4.5, 4.5, 1);
            PennDraw.setPenColor(0, 0, 255);
            PennDraw.text(4.5, 4.5, "You Lost! :(");
            
            return isFinished;
            
        }
        
        drawBoard();
        PennDraw.setPenColor(0, 0, 0);
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                PennDraw.rectangle(i - 0.5, j - 0.5, 0.5, 0.5);
            }
        }
        return isFinished;
    }
    
    /**
    * Inputs: the 2D cell array
    * Outputs: N/A
    * Description: This method iterates through the array and opens all the tiles 
    * that contain a mine (will be called after the player loses). The dimensions
    * of the tiles that hold mines are converted into canvas coordinates and a 
    * picture of a minesweeper mine is placed at that coordinate.
    */
    private void revealMines(Cell[][] cellArr) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cellArr[i][j].getIsMine() && !cellArr[i][j].revealed) {
                    double[] canvas = Board.turnDimToMouse(i, j);
                    
                    PennDraw.picture(canvas[0], canvas[1], "mine.jpg", 40, 40);
                }
            }
        }
        
    }
    
    /**
    * Inputs: a row and column value (where a tile was clicked on and has a number
    * value of 0)
    * Outputs: N/A
    * Description: This recursive function marks the current 0-value cell as having 
    * been accounted for. Then, it iterates through the adjacent cells and reveals 
    * them as well. If one of the adjacent cells is also a 0-value cell, the
    * cells adjacent to that one are also revealed. If any of those adjacent cells
    * have a value of 0, the chain continues.
    */
    private void openBoxes(int arrX, int arrY) {
        cellArr[arrX][arrY].zeroCounted = true;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (arrX + i >= 0 && arrX + i < 9 && arrY + j >= 0 && arrY + j < 9) {
                    if (!cellArr[arrX + i][arrY + j].revealed) {
                        cellArr[arrX + i][arrY + j].revealed = true;
                        safeLeft--;
                    }
                    if (cellArr[arrX + i][arrY + j].getNum() == 0 &&
                    !cellArr[arrX + i][arrY + j].zeroCounted) {
                        openBoxes(arrX + i, arrY + j);
                    }
                }
            }
        }
    }
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: This function iterates through the cells of the cell array and
    * draws the tiles one by one using the drawCell() function in the Cell class
    */
    private void drawBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellArr[i][j].drawCell();
            }
        }
    }
    
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: This function resets the board by reinitializing the number of
    * safe cells left as being 70 (because the first click doesnt decrement it), and 
    * resets the 2D array of cells as being null.
    */
    public void resetBoard() {
        safeLeft = 70;
        cellArr = null;
    }
    
    
}
