/**
* Name: Madeleine Drefke
* Pennkey: mdrefke
* Execution: N/A (used in Board.java which in turn is used in Minesweeper.java)
*
* Description: Each cell is initialized using its position in the 2D array of cells, 
* the boolean value representing whether the cell contains a mine, and the number
* of mines that the cell touches. Cells can also be characterized by whether they
* have been accounted for in a zero-chain in openBoxes() in the Board class and
* whether they have been revealed. The class also has a method that draws the
* contents of a cell and getter methods.
**/
public class Cell {
    
    public boolean revealed;
    public boolean zeroCounted;
    private boolean isMine;
    private int num;
    private int i;
    private int j;
    
    
    /**
    * Inputs: the row of the cell in the 2D array, the column of the cell in the
    * 2D array, whether the tile associated with this cell holds a mine, and the 
    * number of mines that this cell is adjacent to
    * Outputs: an object of type Cell
    * 
    * Description: This constructor takes in parameters i, j, isMine and num 
    * to create an object celled a Cell and initializes revealed and zeroCounted as
    * false so those can be adjusted later.
    */
    public Cell(int i, int j, boolean isMine, int num) {
        this.i = i;
        this.j = j;
        this.revealed = false;
        this.isMine = isMine;
        this.num = num;
        this.zeroCounted = false;
    }
    
    
    /**
    * Inputs: N/A
    * Outputs: i
    * Description: Getter for the row
    */
    public int getI() {
        return this.i;
    }
    
    /**
    * Inputs: N/A
    * Outputs: j
    * Description: Getter for the column
    */
    public int getJ() {
        return this.j;
    }
    
    
    /**
    * Inputs: N/A
    * Outputs: the mine status
    * Description: Getter for isMine
    */
    public boolean getIsMine() {
        return this.isMine;
    }
    
    /**
    * Inputs: N/A
    * Outputs: num
    * Description: Getter for the number of adjacent mines
    */
    public int getNum() {
        return this.num;
    }
    
    
    /**
    * Inputs: N/A
    * Outputs: N/A
    * Description: This function works in canvas 2D coordinate space, and draws a
    * a cells coordinates: if the cell has been revealed and is a mine, it will
    * draw a red square over the tile and add a picture of a mine, if it has been
    * revealed but is a safe space, it will be slightly darker than before and
    * display its number if it isnt zero, and otherwise prints a square the same
    * color as the tiles before (because it has not been revealed and should look
    * the same as before).
    */
    public void drawCell() {
        double[] canvas = Board.turnDimToMouse(i, j);
        
        if (revealed && isMine) {
            PennDraw.setPenColor(255, 0, 0);
            PennDraw.filledRectangle(canvas[0], canvas[1], 0.5, 0.5);
            PennDraw.picture(canvas[0], canvas[1], "mine.jpg", 40, 40);
            PennDraw.advance();
            } else if (revealed && !isMine) {
            PennDraw.setPenColor(130, 130, 130);
            PennDraw.filledRectangle(canvas[0], canvas[1], 0.5, 0.5);
            PennDraw.setPenColor(0, 0, 255);
            if (num != 0) {
                PennDraw.text(canvas[0], canvas[1], "" + num);
            }
            
            } else {
            PennDraw.setPenColor(165, 165, 165);
            PennDraw.filledRectangle(canvas[0], canvas[1], 0.5, 0.5);
        }
    }
}
