/**********************************************************************
 *  Project: Minesweeper
 **********************************************************************/


Name: Madeleine Drefke
PennKey: mdrefke
Hours to complete assignment: 40



/**********************************************************************
 *  Please list all help, collaboration, and outside resources
 *  you used here. 
 *
 *  If you did not get any help in outside of TA office hours,
 *  and did not use any materials outside of the standard
 *  course materials and piazza, write the following statement below:
 *  "I did not receive any help outside of TA office hours.  I
 *  did not collaborate with anyone, and I did not use any
 *  resources beyond the standard course materials."
 **********************************************************************/
 I went to office hours for a few hours on Monday, December 13. Becca
 helped me out for a while (probably about 30 minutes), but we still
 couldn't figure out what was wrong so she called over Giancarlo who 
 helped me on zoom for about 1.5 hours. Giancarlo helped me figure out
 what was wrong with my code, so that it recognized all the cells as 
 having different values as they should rather than all mirroring the
 cell in the bottom righthand corner of the drawing.



/**********************************************************************
 *  How to use the program:
 **********************************************************************/
To run the program, all you need to do is compile and run the program
using java Minesweeper without any commandline arguments. In the PennDraw
window, click on the tiles you wish to open, and if you want to reset
the board, click on the smiley-face at the top of the window. 


/**********************************************************************
 *  Explanations of the classes:                                    
 **********************************************************************/
 Cell.java creates an object of type Cell and has a method that draws out
 the content of the cell depending on whether it has been opened and whether
 it contains a mine.
 
 Board.java creates an object of type Board. It has multiple methods needed
 in the Minesweeper class. Most important are the methods that create and
 fill cells with values defined by arrays created in the constructor, and a 
 method that advances the game by adjusting the board and the drawing based
 on the inpt click.
 
 Minesweeper.java contains only a main method. This file takes in clicks and
 outsources the manipulation of arrays and cells to the Board and Cell classes.
 Minesweeper identifies the tile clicked on, resets the board when the button
 is clicked on, creates a board, checks if the player has won, and if not
 tells Board to continue the game.
 
 
 