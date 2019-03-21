
/**
 *
 * Author: Prabesh Khadka
*/
import java.io.*;
import java.util.Scanner;



/* MaxConnect-4 code adapted from instructor proved code
 the adapted code can represent and generate board states
 save/load the game state to and from files in the desired format
 and count the score
 *
 * the usage to run the program is as follows:
 * ( again, from the maxConnectFour directory )
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 * 
 *   
 */

public class maxconnect4
{
  public static void main(String[] args) 
  {
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
		
    // create and initialize the game board
    GameBoard currentGame = new GameBoard( input );
    
    // create the Ai Player
    AiPlayer calculon = new AiPlayer();
		
    //  variables to keep up with the game
    int playColumn = 99;				//  the players choice of column to play
    boolean playMade = false;			//  set to true once a play has been made

    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
        String output1 = args[2].toString();
        if( output1.equalsIgnoreCase( "computer-next" ) )
        {
            System.out.println("you selected comp");
            play_game(0, currentGame, depthLevel);
            return;
        }
        else if( output1.equalsIgnoreCase( "human-next" ) )
        {
            System.out.println("you selected human");
            play_game(1, currentGame, depthLevel);
            return;
        }
        else{
            System.out.println("Error!! Check Commands");
            return;
        }
    } 
			
    else if( !game_mode.equalsIgnoreCase( "one-move" ) )
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      
        return;
    }

    /////////////   one-move mode ///////////
    // get the output file name
    String output = args[2].toString();				// the output game file
    
    System.out.print("\nMaxConnect-4 game\n");
    System.out.print("game state before move:\n");
    
    //print the current game board
    currentGame.printGameBoard();
    // print the current scores
      System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                         ", Player2 = " + currentGame.getScore( 2 ) +"\n " );
    
    // ****************** this chunk of code makes the computer play
    if( currentGame.getPieceCount() < 42 ) 
    {
        int current_player = currentGame.getCurrentTurn();
	// AI play - random play
	playColumn = calculon.findBestPlay(currentGame, depthLevel);
	
	// play the piece
	currentGame.playPiece( playColumn );
        	
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() 
                           + ": Player " + current_player
                           + ", column " + playColumn+1);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();
    
        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                           ", Player2 = " + currentGame.getScore( 2 ) +"\n " );
        
        currentGame.printGameBoardToFile( output );
    } 
    else 
    {
	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    }
	
    //************************** end computer play
			
    
    return;
    
} // end of main()
	
  /**
   * This method is used when to exit the program prematurly.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
    
    //this is the implementation of the interactive mode
    private static void play_game(int first_payer, GameBoard gamePlay, int depthLevel)
    {
        AiPlayer calculon = new AiPlayer();
        int random_counter=0;
        
        //  variables to keep up with the game
        int playColumn = 99;                //  the players choice of column to play
        boolean playMade = false;            //  set to true once a play has been made
        while(gamePlay.getPieceCount() < 42 )
        {
        System.out.print("\nMaxConnect-4 game\n");
        System.out.print("game state before move:\n");
        //print the current game board
        gamePlay.printGameBoard();
        // print the current scores
        System.out.println( "Score: Player 1 = " + gamePlay.getScore( 1 ) +
                           ", Player2 = " + gamePlay.getScore( 2 ) + "\n " );
        if (first_payer==1)
        {
            int current_player = 1;
            int colInput = 99;
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Enter Your Move (Col:1-7): ");
                String input = sc.next();
                try {
                    colInput = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException ne) {
                    System.out.println("This is not a number");
                    System.out.print("Enter Your Move (Col:1-7): ");
                }
            }
            while(colInput==8||(!gamePlay.isValidPlay( colInput-1 )))
            {
                System.out.println("Move Invalid try Again!!");
                sc = new Scanner(System.in);
                while (true) {
                    System.out.print("Enter Your Move (Col:1-7): ");
                    String input = sc.next();
                    try {
                        colInput = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException ne) {
                        System.out.println("This is not a number");
                        System.out.print("Enter Your Move (Col:1-7): ");
                    }
                }
                }
            gamePlay.playPiece2( colInput-1);
            //gamePlay.ChangeCurrentTurn(gamePlay);
            playMade=true;
            play_game(0, gamePlay, depthLevel);
            
            
        }
        // ****************** this chunk of code makes the computer play
        if( gamePlay.getPieceCount() < 42 )
        {
            
            int current_player = gamePlay.getCurrentTurn();
            // AI play - random play
            playColumn = calculon.findBestPlay(gamePlay, depthLevel);
            
            // play the piece
            gamePlay.playPiece2( playColumn );
            
            // display the current game board
            System.out.println("move " + gamePlay.getPieceCount()
                               + ": Player " + current_player
                               + ", column " + (playColumn+1));
            System.out.print("game state after move:\n");
            gamePlay.printGameBoard();
            
            // print the current scores
            System.out.println( "Score: Player 1 = " + gamePlay.getScore( 1 ) +
                               ", Player2 = " + gamePlay.getScore( 2 ) + "\n " );
            //gamePlay.ChangeCurrentTurn(gamePlay);
            play_game(1, gamePlay, depthLevel);
            
        }
        else
        {
             int current_player = gamePlay.getCurrentTurn();
            System.out.println("move " + gamePlay.getPieceCount()
                               + ": Player " + current_player
                               + ", column " + (playColumn+1));
            System.out.print("gamedasdsds state after move:\n");
            gamePlay.printGameBoard();
            System.out.println( "Score: Player 1 = " + gamePlay.getScore( 1 ) +
                               ", Player2 = " + gamePlay.getScore( 2 ) + "\n " );
            System.out.println("\naaI can't play.\nThe Board is Full\n\nGame Over");
           // gamePlay
          
        }
        
        //************************** end computer play
        
        }
        int current_player = gamePlay.getCurrentTurn();
        System.out.println("--------------------------------------");
        System.out.println("\tThe Board is Full\n\tGame Over");
        System.out.println("--------------------------------------");
        System.out.println("Last move: " + gamePlay.getPieceCount()
                           + " by Player " + current_player
                           + " at column " + (playColumn+1));
        gamePlay.printGameBoard();
        System.out.println("--------------------------------------");
        System.out.println( "Score: Player 1 = " + gamePlay.getScore( 1 ) +
                           ", Player2 = " + gamePlay.getScore( 2 ) + "\n " );
        if (gamePlay.getScore( 1 )>gamePlay.getScore( 2))
        {
            System.out.println("---------------PLAYER 1 WON-------------");
        }
        else if (gamePlay.getScore( 1 )<gamePlay.getScore( 2))
        {
            System.out.println("---------------PLAYER 2 WON-------------");
        }
        else if (gamePlay.getScore( 1 )<gamePlay.getScore( 2))
        {
            System.out.println("-------------------DRAW-----------------");
        }
        System.out.println("--------------------------------------");
        System.exit(1);
       
    }

} // end of class connectFour
