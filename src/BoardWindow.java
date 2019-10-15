// Game Board using Java Graphics2D library and a Thread
// Most of this is beyond the scope of the 1020 course
//
// c Dylan Fries 2019
// Please do not distribute without permission
// No Warrenty is provided, Use at your own risk
// 
// Drawing libraries
import javax.swing.*;

import com.sun.istack.internal.FinalArrayList;

import java.awt.*;
import java.awt.event.*; //This is needed for the mouse and key events
import java.awt.image.BufferedImage; // BufferedImage
// Util library for Random
import java.util.Random;

// Some examples of using Graphics and JFrame. 
// http://zetcode.com/gfx/java2d/basicdrawing/
// http://zetcode.com/tutorials/javagamestutorial/animation/
// JFrame is the whole Window and will hold all the other Panels
// where we draw the graphics to. 
// We will talk about what extends means later on
public class BoardWindow extends JFrame{ 
    
    // Enable Debugging output
    private boolean DEBUG_MODE = false;

    // ----- Window Settings -----
    // Adjust these (but do so at your own risk!)
    // Board size in pixels
    final int WINDOW_WIDTH = 600;
    final int WINDOW_HEIGHT = 600;
    final int DIALOGUE_HEIGHT = 200; // the bottom part of the screen
    final int WINDOW_MARGIN = 20; // border margin around the outside of the window

    // Speed (For realtime updates)
    final static int SPEED = 25; //Time between generations when on "play" (in msecs)

    // -------------- Visualization ------------
    final Color closedColor = Color.LIGHT_GRAY;
    final Color openColor = Color.GREEN;
    final Color originColor = Color.DARK_GRAY;
    final Color goalColor = Color.RED;

    // =========================================
    // ============== Don't edit this ==========
    // ----- Tile settings -----
    // The size of a single tile
    // this gets set programmatically(overwritten) based on tile count and available space
    private int tileSize = 25; 

    // ----- Controller references ----
    // References to the Panel within the Window
    private BoardPanel panel; // panel within the window
    // Reference to the Game Board, which will store the board state. 
    private GameBoard board; // board reference
    // Reference to the Controller class, which we will use to control gameplay actions
    private Controller controls;

    // [ ] Not enabled right now
    private boolean running; //True if playing, false if paused.

    // ---Thread / Timer classes ---
    //These objects allow the simulation to run automatically at the indicated SPEED
    private ActionListener doOneGeneration;
    private Timer myTimer;

    /**
     * Constructor for objects of class Board
     * Accepts references to both the GameBoard and Controller.
     */
    public BoardWindow(GameBoard board, final Controller controls){
        // Let the board reference our other controller classes
        this.board = board;
        this.controls = controls;

        // *** Methods related to JFrame
        setTitle("A4 Board"); // Call the JFrame methods
        // Set Window size in pixels
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT); 
        setBackground(Color.BLACK); // Board background color
        
        // Add the Panel
        // Note this must be added BEFORE setVisible(true) or the panel won't draw (on Mac only)
        panel = new BoardPanel(this); // Create a panel and pass it the JFrame reference
        add(panel); // add the panel to the window

        // Enable the Window and bring it to the foreground
        setFocusable(true);
        requestFocusInWindow(); // give the window focus
        setVisible(true); // make the window visable

        // ----- Input Listeners -----
        // Listen for Mouse Input - Not Used Yet
        // panel.addMouseListener(new HandleMouse());
        // Listen for keyboard Keys
        addKeyListener(new HandleKeys());

        running=true; // initially we are running

        // Enable the Timer and Action Listener
        doOneGeneration = new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                controls.stepUpdate(); // Lets the Controller know we are on a new frame
                repaint(); // ? do I need this here? 
            };
        };

        // Start a timer thread that fires every SPEED ms. 
        myTimer = new Timer(SPEED, doOneGeneration);
        myTimer.start();
        
        // Exit the code when the window is closed. 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // A Private class that extends JPanel (we will talk about what this means later!)
    // Basically it is a container for other GUI elements. 
    private class BoardPanel extends JPanel{

        private BoardWindow myWindow;

        public BoardPanel(BoardWindow window){
            myWindow = window;// Get the reference to the JFrame this panel is in
        }

        // called by repaint
        @Override
        public void paintComponent(Graphics graphicsObj){
            super.paintComponent(graphicsObj);
            
            drawBoard(graphicsObj, board);
            //drawInfoPanel(graphicsObj);
        }

        // Draws a gameboard to the screen. It will adapt its size to fit the correct
        // number of tiles to the window size. 
        private void drawBoard(Graphics graphicsObj, GameBoard board){
            // Cast the Graphics object to a graphics 2d object
            Graphics2D graphicsObj2D = (Graphics2D) graphicsObj;

            if(board.getWidth() < 1 || board.getHeight() < 1){
                System.out.println("Board Size is invalid ("+board.getWidth() + " by " + board.getHeight() + " Tiles. Terminating program." );
                System.out.println("You must complete Phase 1 before the board will be loaded again!");
                System.exit(0);
            }

            int width = getWidth() - (WINDOW_MARGIN * 2); // width of the window
            //int height = getHeight() - DIALOGUE_HEIGHT - WINDOW_MARGIN * 2; // height of the window

            int height = getHeight() - WINDOW_MARGIN * 2; // height of the window

            // Calculate the size of the squares needed to fit the game board. 
            //int shortBoardEdge = Math.min(width, height);
            int minHorizontal = width / board.getWidth();
            int minVertical = height / board.getHeight();
            // This is in pixels
            // Get the largest tile size we can fit
            tileSize = Math.min(minHorizontal, minVertical);            


            // Assignment 4 Upgrade, iterate the array of Tiles directly and draw images. 
            Tile currentTile = null;

            // Iterate the array and draw the tiles with Images
            for(int row =0 ; row < board.getHeight(); row++){
                for(int col = 0; col < board.getWidth(); col++){
                    currentTile = board.getTile(row,col); // load the BufferedImage for the tile
                    drawTile(graphicsObj2D, currentTile, row, col); // Draw the Tile directly
                }
            }

            //System.out.println("In Board Window Redraw" + board.getWidth()  +":"+ board.getHeight());
        }

        // Draw the graphics directly based on the image in the tile. 
        // This is a replacement for the drawTile method below that only can accept a char
        private void drawTile(Graphics2D g2d, Tile currentTile, int row, int col){

             // Get the offset based on the map size
            int xOffset = (col * tileSize) + WINDOW_MARGIN;
            int yOffset = (row * tileSize) + WINDOW_MARGIN;

            // ---- Loading the Background Image ----
            // Simplest version here because subclassing allows us to distribute the complexity to other methods
            BufferedImage backgroundImage = ImageLoader.loadImage(currentTile.getImageName());
            g2d.drawImage(backgroundImage, xOffset, yOffset, tileSize, tileSize, null);

        }

    } // End Board Panel private inner class

    // Our HandleKeys method implements the KeyListener interface (which is beyond the scope of this course)
    // Basically it listens for key presses, then passes those to the Controller class. 
    private class HandleKeys implements KeyListener {
        //The standard methods are required.
        public void keyPressed(KeyEvent key){ 
            //System.out.println("Key Pressed: " + e.toString());
            char keyCode = key.getKeyChar();
            
            controls.keyPressed(keyCode); // Tell the controller a key was pressed
 
        }

        // Need to be implimented because of the interface but not used. 
        public void keyReleased(KeyEvent e){ /*Do nothing */ }
        public void keyTyped(KeyEvent e){ /*Do nothing */ }
    }

} // End BoardWindow
