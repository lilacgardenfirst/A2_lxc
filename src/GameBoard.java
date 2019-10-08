
// This class stores a single Map instance 
public class GameBoard
{
	
	// Sets of Life and Death rules for the Cellular Automata
	// if alive, apply life rule based on the number of neighbors == index (it, 3 neighbors would return true, 2 neighbors = false)
	private boolean[] lifeRules = {false,false,true,true,false,false,false,false,false}; // number of neighbors to come alive on
	// If dead, apply death rules based on number of neighbors (come alive if 3 neighbors only)
	private boolean[] deathRules = {false,false,true,false,false,false,false,false,false}; // turn alive in these conditions

	// -- These methods are required for the BoardWindow
	// Returns the width of the board or zero if map is not initialized
	public int getHeight(){
		if( map == null){
			return 0;
		}
		return map.length;
	}

	// Returns the width of the board or zero if map is not initialized
	public int getWidth(){
		if(map == null || map[0] == null){
			return 0;
		}
		return map[0].length;
	}

}