import java.util.ArrayList;

public class Controller{
	
	// Preset sizes of the maps
	private int[][] mapSizes = {{1,1},{3,3},{5,10},{10,20},{20,35},{30,55}};
	private int[] currentSize = {3,3}; // current size of the map {row, col}
	// Controller references to the Board and Window
	private GameBoard board;
	private BoardWindow window;

	// Enable this boolean to get extra debugging info from the Controller. 
	private boolean DEBUG_MODE = true;
	private boolean isPaused = true;

	// Can use this to update the speed without messing with the Timer
	// Less steps per turn is faster, more is slower
	private int stepsThisTurn = 0;  
	private final int STEPS_PER_TURN = 10;

	// Start the game
    public static void main(String[] args){
    	Controller runtime = new Controller();
    }

	public Controller(){
		// We will not change this because the BoardWindow is subscribing to it. 
		board = new GameBoard();
		// Initialize our Graphics / Input Window
    	// Needs to happen before we load our board
    	resetBoard();
    	

    	// Loading the Window after the Board is loaded helps 
		window = new BoardWindow(board, this);
	}

	// Reset the board
	private void resetBoard(){
		if( DEBUG_MODE ) { System.out.println("Reloading new Map of size :"  + currentSize[0] +":"+ currentSize[1]);}
		
    	
		// Phase 2
    	//board.initializeBoard(currentSize[0],currentSize[1]); // not random
    }

    // Set the existing board to random Tile values
    public void randomBoard(){
    	// Phase 2
    	// board.resetBoard(true); // random
    }

    // Update the Cellular Automata to the next step
    public void nextState(){
    	System.out.println("Warning: board.nextState Not implemented yet");
    	// Uncomment for phase 3
    	//board.nextState();
    }
	
	// Non arrow key pressed
	public void keyPressed(char key){
		if( DEBUG_MODE) { System.out.println("Key Pressed: " + key);}
		            // if this is a number
        if(Character.isDigit(key)){
            if( DEBUG_MODE ) { System.out.println("Load Map Size:"  + key);}
            // convert to an int
            int selected = Character.digit(key,10);

			if(selected >=0 && selected < mapSizes.length ){
				currentSize = mapSizes[selected];
				// Reload map
				resetBoard();

			}
		}

		// P also means pause timer
        if(key =='p'){
           if(isPaused = !isPaused){   
               System.out.println(" Execution Started ");  
           }else{
               System.out.println(" Execution Paused ");
           }
         // R key sets things to random
        }else if( key == 'r'){
			randomBoard();
		// N key goes to the next state. 
		}else if(key == 'n'){
			nextState();
		}
	}

	// This is called automatically on a Timer from the BoardWindow class. 
	public void stepUpdate(){
		// This gets called all the time if active
		
		if( !isPaused){
			stepsThisTurn++;
			if(stepsThisTurn > STEPS_PER_TURN){
				stepsThisTurn = 0;
				

				nextState(); // start the next turn
				//if( DEBUG_MODE){ System.out.println("StepUpdate"); }
			}
		}
	}
}