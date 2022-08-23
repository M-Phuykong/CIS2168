import java.awt.*;
import java.util.*;

/**
 * Displays the progress of the rabbit hunt.
 * 
 * @author David Matuszek 
 * @version October 20, 2001
 */
public class View {

    // instance variables
    private Object [][] field;
    private int numberOfRows;
    private int numberOfColumns;
    private Canvas canvas;
    private Graphics graphics;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private final Color BROWN = new Color(0xAA, 0x88, 0);
    private final Color GREEN = new Color(0, 0xAA, 0);
    private final Color LOOK = new Color(0xDD, 0xDD, 0);
    private boolean initialDrawing = false;
    private int foxRow;
    private int foxColumn;
    private int rabbitRow;
    private int rabbitColumn;
    private boolean displayLookingAround = true;
    private int lookDelay;
    
    /**
     * Constructs a View for the given field.
     *
     * @param field  the field on the fox hunts the rabbit
     */
    View(Object[][] field) {
        this.field = field;
        numberOfRows = field.length;
        numberOfColumns = field[0].length;
    }
    
    /**
     * Tells this View what canvas it should use for its display.
     *
     * @param canvas the canvas to use
     */
    void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.graphics = canvas.getGraphics();
        this.height = canvas.getHeight() - 1;
        this.width = canvas.getWidth();
        graphics = canvas.getGraphics();
        cellHeight = height / numberOfRows;
        cellWidth = width / numberOfColumns;
    }        

    void setDisplayLookingAround(boolean displayLookingAround, int lookDelay) {
        this.displayLookingAround = displayLookingAround;
        this.lookDelay = lookDelay;
    }
    
    /**
     * Determines the y-coordinate on the canvas that corresponds
     * to the top edge of a given row in the field.
     *
     * @param row  the row number in the field
     * @return  the y-coordinate of the top of that row on the canvas
     */
    private int cellTop(int row) {
        return (row * height) / numberOfRows;
    }
    
    /**
     * Determines the x-coordinate on the canvas that corresponds
     * to the left edge of a given column in the field.
     *
     * @param column  the column number in the field
     * @return  the x-coordinate of the left of that column on the canvas
     */
    private int cellLeft(int column) {
        return (column * width) / numberOfColumns;
    }
        
    /**
     * Draws a rabbit.
     *
     * @param i  the row number of the rabbit
     * @param j  the column number of the rabbit
     */
    private void drawRabbit(int i, int j) {
        fillOval(i, j, BROWN, 5);
    }
    
    /**
     * Draws a fox.
     *
     * @param i  the row number of the fox
     * @param j  the column number of the fox
     */
    private void drawFox(int i, int j) {
        fillOval(i, j, Color.red, 1);
    }
    
    /**
     * Draws a bush.
     *
     * @param i  the row number of the bush
     * @param j  the column number of the bush
     */    
    private void drawBush(int i, int j) {
        graphics.setColor(GREEN);
        graphics.fillOval(cellLeft(j) + cellWidth / 4,
                          cellTop(i) + 1, cellWidth / 2, cellHeight - 1);
        graphics.fillOval(cellLeft(j) + 1, cellTop(i) + 1 + cellHeight / 4,
                          cellWidth - 1, cellHeight / 2);
    }
    
    /**
     * Erases the contents of a "cell" of the field on the screen.
     *
     * @param i  the row number of the cell to be erased
     * @param j  the column number of the cell to be erased
     */    
    private void eraseCell(int i, int j) {
        graphics.setColor(Color.white);
        graphics.fillRect(cellLeft(j) + 1, cellTop(i) + 1,
                          cellWidth - 1, cellHeight - 1);
    }
    
    /**
     * Draws the contents of a single cell on the field.
     */
    private void drawCell(int row, int column) {
        if (field[row][column] == null) {
            eraseCell(row, column);
        }
        else if (field[row][column] instanceof Bush) {
            drawBush(row, column);
        }
        else if (field[row][column] instanceof Fox) {
            drawFox(row, column);
        }
        else if (field[row][column] instanceof Rabbit) {
            drawRabbit(row, column);
        }
    }
    
    /**
     * Redraws the entire field display from scratch.
     */
    void displayEverything() {
    
        // loop through the field array and draw the things in it
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                eraseCell(i, j);
                Object obj = field[i][j];
                if (obj instanceof Rabbit) {
                    rabbitRow = i;
                    rabbitColumn = j;
                    drawRabbit(i, j);
                }
                else if (obj instanceof Fox) {
                    foxRow = i;
                    foxColumn = j;
                    drawFox(i, j);
               }
                else if (obj instanceof Bush)  {
                    drawBush(i, j);
                }
            }
        } 
        
        // draw vertical lines
        graphics.setColor(new Color(0x99, 255, 255));
        for (int i = 1; i < numberOfColumns; i++) {
            int left = cellLeft(i);
            graphics.drawLine(left, 0, left, height);
        }

        // draw horizontal lines
        for (int i = 0; i <= numberOfColumns; i++) {
            int top = cellTop(i);
            graphics.drawLine(0, top, width, top);
        }
    }
    
    /**
     * Erases the fox and/or rabbit from their old positions on the
     * screen and redraws them in their new positions. If the flag
     * <code>displayLookingAround</code> is true, will also show
     * in which directions the animal has looked.
     */
    void displayChanges() {
        // find new positions of fox and rabbit
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (field[i][j] instanceof Fox) {
                
                    // possibly display fox looking around
                    if (displayLookingAround && !Model.isRabbitsTurn) {
                        showLooks(foxRow, foxColumn, i, j);
                    }
                    
                    // if fox has moved, update display
                    if (i != foxRow || j != foxColumn) {
                        eraseCell(foxRow, foxColumn);
                        foxRow = i;
                        foxColumn = j;
                        drawFox(i, j);
                    }
                }
                else if (field[i][j] instanceof Rabbit) {
                   
                   // possibly display rabbit looking around
                   if (displayLookingAround && Model.isRabbitsTurn) {
                        showLooks(rabbitRow, rabbitColumn, i, j);
                    }
                    
                    // if rabbit has moved, update display
                    if (i != rabbitRow || j != rabbitColumn) {
                        eraseCell(rabbitRow, rabbitColumn);
                        rabbitRow = i;
                        rabbitColumn = j;
                        drawRabbit(i, j);
                    }
                }
            } // for j
        } // for i
    }
    
    /**
     * Determines whether field[row][column] is a legal location.
     *
     * @param row the row to be tested
     * @param column the column to be tested
     * @return true if the location [row][column] is within field[][]
     */
    private boolean legal(int row, int column) {
        return row >= 0 && row < numberOfRows &&
               column >= 0 && column < numberOfColumns;
    }
    
    /**
     * Shows what each animal looks at. This a bit complicated because
     * the animal has already moved by the time we do this, and we
     * want to show the "look" as having been performed before the
     * animal moved. Hence, <code>newRow</code> and <code>newColumn</code>
     * (which now contain the animal) are treated as empty cells.
     *
     * @param oldRow    The previous row of the animal (before moving)
     * @param oldColumn The previous column of the animal (before moving)
     * @param newRow    The current row of the animal (after moving)
     * @param newColumn The current column of the animal (after moving)
     */
    void showLooks(int oldRow, int oldColumn, int newRow, int newColumn) {
        int direction;
        int rowDelta;
        int columnDelta;
        boolean cellWasEmpty;
        Vector looks = Model.looks;

        // for each "look," find starting position and direction
        for (int i = 0; i < looks.size(); i++) {
            direction = ((Integer)looks.elementAt(i)).intValue();
            if (direction == Model.STAY) continue;
            int lookRow = oldRow;
            int lookColumn = oldColumn;
            rowDelta = Model.rowChange(direction);
            columnDelta = Model.columnChange(direction);
            lookRow += rowDelta;
            lookColumn += columnDelta;
            
            // draw line of dots showing places looked
            while (legal(lookRow, lookColumn)) {
                fillOval(lookRow, lookColumn, LOOK, 6);
                try { Thread.sleep(10); }
                catch (InterruptedException e) {}
                
                // we want to pretend the animal move has not happened yet,
                // so new position of animal counts as an unoccupied cell
                cellWasEmpty = (field[lookRow][lookColumn] == null) ||
                               (lookRow == newRow && lookColumn == newColumn);
                if (!cellWasEmpty) break;
                lookRow += rowDelta;
                lookColumn += columnDelta;
            }
            try { Thread.sleep(lookDelay); }
            catch (InterruptedException e) {}
            
            // erase line of dots
            lookRow = oldRow + rowDelta;
            lookColumn = oldColumn + columnDelta;
            while (legal(lookRow, lookColumn)) {
                cellWasEmpty = (field[lookRow][lookColumn] == null) ||
                               (lookRow == newRow && lookColumn == newColumn);
                if (lookRow == rabbitRow && lookColumn == rabbitColumn) {
                    drawRabbit(lookRow, lookColumn);
                }
                else if (cellWasEmpty) {
                    eraseCell(lookRow, lookColumn);
                }
                else {
                    drawCell(lookRow, lookColumn);
                }
                lookRow += rowDelta;
                lookColumn += columnDelta;
            }
        }
     }        

    /**
     * Draws a filled oval at the position on the screen representing
     * the given row and column of the field.
     *
     * @param row  the number of the row in the field to be displayed
     * @param column  the number of the column in the field to be displayed
     * @param color  the color to be used for the oval
     * @param adjustment  the amount by which to shrink the oval
     */
    private void fillOval(int row, int column, Color color, int adjustment) {
        graphics.setColor(color);
        
        // ensure oval is at least 4 pixels high and 4 pixels wide
        int widthAdjustment = adjustment;
        int heightAdjustment = adjustment;
        if (cellWidth - 2 * adjustment < 4)
            widthAdjustment = (cellWidth - 4) / 2;
        if (cellHeight - 2 * adjustment < 4)
            heightAdjustment = (cellHeight - 4) / 2;
        graphics.fillOval(cellLeft(column) + widthAdjustment,
                          cellTop(row) + heightAdjustment,
                          cellWidth - 2 * widthAdjustment,
                          cellHeight - 2 * heightAdjustment);
    }
}
