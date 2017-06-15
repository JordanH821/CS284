/*
 * Jordan Handwerger
 * October 28, 2016
 * CS284A
 * Pledge: I pledge my honor that I have abided by the Stevens Honor System. 
 */
package Maze;
import java.util.ArrayList;

/**
 * Class that solves maze problems with backtracking.
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }
    
    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * @pre Possible path cells are in BACKGROUND color;
     *      barrier cells are in ABNORMAL color.
     * @post If a path is found, all cells on it are set to the
     *       PATH color; all cells that were visited but are
     *       not on the path are in the TEMPORARY color.
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true;
     *         otherwise, false
     */

    public boolean findMazePath(int x, int y) {
        if(x >= 0 && y >= 0 && x < maze.getNCols() && y < maze.getNRows() && maze.getColor(x, y) == NON_BACKGROUND){//checks if (x,y) is in the grid and a possible path
        	
        	if(x == (maze.getNCols() - 1) && y == (maze.getNRows() - 1)){// checks if (x,y) is at the exit
        		maze.recolor(x, y, PATH);
        		return true;
        	}else{
        		maze.recolor(x, y, PATH);
        		if(findMazePath(x+1, y) || findMazePath(x-1, y) || findMazePath(x, y+1) || findMazePath(x, y-1)){//recursive calls and checks if any reach the exit
        			return true;
        		} else{
        			maze.recolor(x, y, TEMPORARY); //the cell has nowhere else to go
        			return false;
        		}
        	}
        }else{//was not in grid or did not have NON_BACKGROUND color
        	return false;
        }
    }
    
    
    /**
     * Finds all possible paths to the exit
     * @param x starting x coordinate 
     * @param y starting y coordinate
     * @return an ArrayList of ArrayLists containing all the paths to the exit
     */
    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y){
    	if(findMazePath()){//Checks if it should run
    		maze.recolor(PATH, NON_BACKGROUND);
    		maze.recolor(TEMPORARY, NON_BACKGROUND);
	    	return findMazePath(0,0, new ArrayList<PairInt>());
    	}else{//no paths exist
    		maze.recolor(PATH, NON_BACKGROUND); //recoloring not necessary but for continuation after this method call
    		maze.recolor(TEMPORARY, NON_BACKGROUND);
    		ArrayList<ArrayList<PairInt>> ret = new ArrayList<ArrayList<PairInt>>();
    		ArrayList<PairInt> empty = new ArrayList<PairInt>(); //weird IDK if there are other solutions to returning [[]]
    		ret.add(empty);
    		return ret;
    	}
    }
    /**
     * Helper function for findAllMazePaths
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param current ArrayList of all the PairInts that have been visited in this recursive call
     * @return an ArrayList of ArrayLists containing all the paths to the exit
     */
    public ArrayList<ArrayList<PairInt>> findMazePath(int x, int y, ArrayList<PairInt> current ){
    	if(x < maze.getNCols() && x >= 0 && y < maze.getNRows() && y >= 0  && maze.getColor(x, y) == NON_BACKGROUND){//Checks if (x, y) is out of the grid or not part of the possible path
    		current.add(new PairInt(x,y));
	    	if(x == (maze.getNCols()-1) && y == (maze.getNRows()-1)){
	    		ArrayList<ArrayList<PairInt>> ret = new ArrayList<ArrayList<PairInt>>();
	    		// first make copy of current and then insert into ret
	    		@SuppressWarnings("unchecked")
				ArrayList<PairInt> currentCopy = (ArrayList<PairInt>) current.clone();
	    		ret.add(currentCopy);
	    		current.remove(new PairInt(x,y));
	    		return ret;
	    	}else{//recursive calls
	    		maze.recolor(x, y, PATH);
	    		//right
	    		ArrayList<ArrayList<PairInt>> right = findMazePath(x+1, y, current);
	    		//left
	    		ArrayList<ArrayList<PairInt>> left = findMazePath(x-1, y, current);
	    		//up
	    		ArrayList<ArrayList<PairInt>> up = findMazePath(x, y+1, current);
	    		//down
	    		ArrayList<ArrayList<PairInt>> down = findMazePath(x, y-1, current);
	    		//combine all lists
	    		ArrayList<ArrayList<PairInt>> combined = new ArrayList<ArrayList<PairInt>>();
	    		combined.addAll(right);
	    		combined.addAll(left);
	    		combined.addAll(up);
	    		combined.addAll(down);
	    		maze.recolor(x, y, NON_BACKGROUND);
	    		current.remove(new PairInt(x,y));
	    		return combined;
	    	}
    	}else{//not part of a path
    		return new ArrayList<ArrayList<PairInt>>();
    	}
    }
    
    /**
     * Finds the shortest path to exit
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @return ArrayList containing the smallest path to the exit
     */
    public ArrayList<PairInt> findMazePathMin(int x, int y){
    	maze.recolor(PATH, NON_BACKGROUND); //resets the maze colors
    	ArrayList<ArrayList<PairInt>> result = findAllMazePaths(x,y);
    	if(result.size() != 0){//Case where there exists at least one path to the exit
	    	ArrayList<PairInt> min = result.get(0);
	    	int minLength = min.size();
	    	for(int i=1; i<result.size(); i++){// loops through and compares
	    		if(minLength >= result.get(i).size()){
	    			min = result.get(i);
	    			minLength = min.size();
	    		}
	    	}
	    	return min;
    	}else{//no path to exit
    		return new ArrayList<PairInt>();
    	}
    }
    

    /*<exercise chapter="5" section="6" type="programming" number="2">*/
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /*</exercise>*/

    /*<exercise chapter="5" section="6" type="programming" number="3">*/
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /*</exercise>*/
}
/*</listing>*/
