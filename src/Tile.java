// the Tile class represents a single Tile on the GameBoard. 
// This is where the other objects in the game are stored. 
// Used for Cellular Automata
// A2 1020
// c Dylan Fries 2019
public class Tile{

	// --- Provided --- For art 
	// - Tile Art - 
	private String deadTileArt = "Dead.png";
	private String aliveTileArt = "Alive.png";
	private boolean isAlive;
	private TileList neighbors;
	
	
	public Tile() {
		isAlive = false;
	}
	
	
	// Provided
	// Tiles states (for art)
	public String getImageName(){
		
		if(isAlive){
			return aliveTileArt;
		}else{
			return deadTileArt; // if using different costing tiles
		}
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public String toString() {
		if (isAlive()) {
			return "The Tile is alive ,it has * neighbors and * neighbors still alive.";
		} else {
			return "The Tile isn't alive ,it has * neighbors and * neighbors still alive.";
		}			
	}
	
	public void setRandom(double randomProb) {
		if(randomProb == 0.0) isAlive = false;
		if(randomProb ==  1.0) isAlive = true;
		
		if(randomProb == 0.5) {
			if (Math.random() >= 0.5) {
				isAlive = true;
			} else {
				isAlive = false;
			}
		}
	}
}