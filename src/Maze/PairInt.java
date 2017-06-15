/*
 * Jordan Handwerger
 * October 28, 2016
 * CS284A
 * Pledge: I pledge my honor that I have abided by the Stevens Honor System. 
 */
package Maze;

public class PairInt {
    private int x;
    private int y;
    //Constructor
    /**
     * Creates a new PairInt object
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public PairInt(int x, int y){
   		this.x = x;
   		this.y = y;
   	}
    
    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
   	public int getX(){
   		return x;
   	}
    
   	/**
     * Gets the y coordinate
     * @return the y coordinate
     */
    public int getY(){
   		return y;
   	}
    
    /**
     * Sets the x coordinate
     * @param x new x coordinate
     */
   	public void setX(int x){
   		this.x = x;
   	}
    
   	/**
     * Sets the y coordinate
     * @param y new y coordinate
     */
    public void setY(int y){
   		this.y = y;
   	}
    
    /**
     *Overrides the equals method and checks equality
     * @return true if equal, false if not 
     */
   	@Override
    public boolean equals(Object p){
   		if(!(p instanceof PairInt)){
   			return false;
    	}else{
    		PairInt P = (PairInt)p;
   			return P.x == this.x && P.y == this.y;
   		}
   	}
    
   	/**
   	 * Overrides the toString method 
   	 * @return String representations of the PairInt 
   	 */
   	public String toString(){
   		return "(" + x + ", " + y + ")";  
    }
    
   	/**
   	 * Creates a copy of a PairInt
   	 * @return a PairInt of equal value
   	 */
   	public PairInt copy(){
   		return new PairInt(this.x, this.y);
   	}

}
