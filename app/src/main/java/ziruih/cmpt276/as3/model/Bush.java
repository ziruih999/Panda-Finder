/*
Each cell on game board is a Bush object
A Bush may or may not contain a panda
A Bush can be revealed
 */



package ziruih.cmpt276.as3.model;

public class Bush {
    boolean hasPanda;
    boolean hasRevealed;

    public Bush(boolean hasMine, boolean digged) {
        this.hasPanda = hasMine;
        this.hasRevealed = digged;
    }

    public boolean isHasPanda() {
        return hasPanda;
    }

    public boolean isHasRevealed() {
        return hasRevealed;
    }

    public void setHasPanda(boolean hasPanda) {
        this.hasPanda = hasPanda;
    }

    public void setHasRevealed(boolean hasRevealed) {
        this.hasRevealed = hasRevealed;
    }

}
