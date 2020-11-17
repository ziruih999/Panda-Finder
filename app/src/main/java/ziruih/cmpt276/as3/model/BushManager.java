/*
BushManager manage an array of Bush object
support Singleton
Game Activity use BushManager to launch and maintain game.
 */


package ziruih.cmpt276.as3.model;
import java.util.Random;


public class BushManager {
    private static BushManager instance;

    private Bush[][] bushArray;
    private int row;
    private int col;
    private int pandaCount;

    private BushManager() {

    }



    public static BushManager getInstance() {
        if (instance == null) {
            instance = new BushManager();
        }
        return instance;
    }


    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPandaCount(int pandaCount) {
        this.pandaCount = pandaCount;
    }

    public void revealBush(int x, int y) {
        bushArray[x][y].setHasRevealed(true);
    }




    public void setBushArray() {
        this.bushArray = new Bush[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                bushArray[i][j] = new Bush(false,false);
            }
        }
        // set mine position
        Random dice = new Random();
        for (int i = 0; i < pandaCount; i++) {
            while (true) {
                int x = dice.nextInt(row);
                int y = dice.nextInt(col);
                if (hasPanda(x, y)) {
                    continue;
                }
                bushArray[x][y].setHasPanda(true);
                break;
            }
        }
    }

    // Getters

    public boolean hasPanda(int x, int y) {
        return bushArray[x][y].isHasPanda();
    }

    public boolean hasRevealed(int x, int y) {
        return bushArray[x][y].isHasRevealed();
    }

    public int getPandaCount() {
        return pandaCount;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // Find the scan number of a mine
    public int doScan(int x, int y) {
        int count = 0;

        // Undigged mine on same row
        for (int j = 0; j < col; j++) {
            if (j!=y && hasPanda(x,j) && !hasRevealed(x,j)) {
                count++;
            }
        }

        // Undigged mine on same col
        for (int i=0; i<row; i++) {
            if (i!=x && hasPanda(i, y) && !hasRevealed(i, y)) {
                count ++;
            }
        }
        return count;
    }

}
