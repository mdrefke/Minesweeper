/**
* Name: Madeleine Drefke
* Pennkey: mdrefke
* Execution: java Minesweeper
*
* Description: This class is just a main method that recreates the popular internet
* game Minesweeper. The game is played such that the user tries to avoid clicking
* on spaces that hide a "mine", with numbers indicating how many mines are adjacent
* to each given mine. This class begins PennDraw animation and initializes values
* such that a board with 10 mines is created from a 2D array of cells. This class
* accepts clicks on the PennDraw and feeds them into the board class to adjust the
* board's cell's values and reprint the board. The class also contains a method
* to reset the board (done by clicking on the smiley face at the top of the visual).
* If the player either wins or loses, the visual will display a message.
* 
**/

public class Minesweeper {
    
    public static void main(String[] args) {
        PennDraw.enableAnimation(30);
        int[] arrs = new int[2];
        Board b = new Board(arrs, -1);
        int movesDone = 0;
        boolean setUp = false;
        double firstClickX = 0.0;
        double firstClickY = 0.0;
        double clickX = 0.0;
        double clickY = 0.0;
        double[] click = new double[2];
        boolean isFinished;
        Board.setUpPennDraw();
        PennDraw.advance();
        
        b.safeLeft = 70;
        
        while (true) {
            isFinished = false;
            //use first click to generate the game
            if (PennDraw.mousePressed() && movesDone == 0) {
                if (PennDraw.mouseX() > 0 && PennDraw.mouseX() < 9) {
                    if (PennDraw.mouseY() > 0 && PennDraw.mouseY() < 9) {
                        firstClickX = PennDraw.mouseX();
                        firstClickY = PennDraw.mouseY();
                        arrs = Board.turnMouseToDim(firstClickX, firstClickY);
                        movesDone++;
                        
                    }
                }
            }
            
            if (PennDraw.mousePressed()) {
                if (PennDraw.mouseX() >= 4 && PennDraw.mouseX() <= 5) {
                    if (PennDraw.mouseY() >= 9.25 &&
                    PennDraw.mouseY() <= 10.25) {
                        b.resetBoard();
                        arrs = new int[2];
                        b = new Board(arrs, -1);
                        Board.setUpPennDraw();
                        PennDraw.advance();
                        movesDone = 0;
                        
                        setUp = false;
                        
                        firstClickX = 0.0;
                        firstClickY = 0.0;
                        clickX = 0.0;
                        clickY = 0.0;
                        click = new double[2];
                        isFinished = false;
                    }
                }
            }
            
            //use successive clicks to play game
            if (movesDone > 0) {
                //makes sure that only one board is created
                if (movesDone == 1 && !setUp) {
                    b = new Board(arrs, movesDone);
                    setUp = true;
                    isFinished =
                    b.playMove(arrs, b.getCellArr(), movesDone, isFinished);
                    PennDraw.advance();
                    
                }
                if (b.safeLeft == 0) {
                    isFinished = true;
                    PennDraw.setPenColor(255, 255, 255);
                    PennDraw.filledRectangle(4.5, 4.5, 4.5, 1);
                    PennDraw.setPenColor(0, 0, 255);
                    PennDraw.text(4.5, 4.5, "You won! :)");
                }
                
                if (PennDraw.mousePressed() && !isFinished) {
                    if (PennDraw.mouseX() > 0 && PennDraw.mouseX() < 9) {
                        if (PennDraw.mouseY() > 0 && PennDraw.mouseY() < 9) {
                            clickX = PennDraw.mouseX();
                            clickY = PennDraw.mouseY();
                            arrs = Board.turnMouseToDim(clickX, clickY);
                            isFinished = b.playMove(arrs, b.getCellArr(),
                            movesDone, isFinished);
                        }
                    }
                }
                PennDraw.advance();
            }
        }
    }
    
}
