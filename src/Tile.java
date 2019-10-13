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
	private TileList neighborsList;
	
	
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
}