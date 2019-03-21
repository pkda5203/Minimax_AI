
/**
 *
 * Author: Prabesh Khadka
*/
import java.util.*;



/* This code adapted from instructor proved code
 the adapted code can represent and generate board states
 save/load the game state to and from files in the desired format
 and count the score
 */
public class AiPlayer 
{
    int MAX;
    int MAX_DEPTH;
    int CHILD;
    double MAX_CHILD;
    int ACTION;
    
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    public AiPlayer() 
    {
	// nothing to do here
    }

    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like
     * to play in.
     */
    public int findBestPlay( GameBoard currentGame, int depthLevel )
    {
	// start random play code
    int playChoice;
    int newQ=Minimax(currentGame, depthLevel);
    playChoice=newQ;
	
	while( !currentGame.isValidPlay( playChoice ) )
	    playChoice = newQ;
	
	
	return playChoice;
    }
    
    public int Minimax( GameBoard currentGame, int depthLevel)
    {
        MAX= currentGame.whoseTurn();
        MAX_DEPTH= currentGame.getPieceCount()+depthLevel;
        CHILD=currentGame.getPieceCount()+1;
        MAX_CHILD=-999999;
        ACTION=-999999;
        GameBoard temp= new GameBoard(" ");
        temp.copyFunc(temp,currentGame);

        double v = MaxValue(temp, Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        return ACTION;
        
    }
    
    public double MaxValue(GameBoard state, double alpha, double beta)
    {
        if (state.getPieceCount()==42)  //terminal test
        {
            if (MAX==1)
            {

                return (double) (state.getScore(1)-state.getScore(2));
            }
            if (MAX==2)
            {

                return (double) (state.getScore(2)-state.getScore(1));
            }
        }
        //eval function is calculated as following;
        // (4-in-rows of min^4 + 3-in-rows of min^3 + 2-in-rows of min^2) -(4-in-rows of max^4 + 3-in-rows of max^3 + 2-in-rows of max^2)
        if (state.getPieceCount()==MAX_DEPTH)
        {
            if (MAX==1)
            {
                return (double) ((( Math.pow(state.getScore( 2),4)) +(Math.pow(state.get3Score( 2 ),3))+( Math.pow(state.get2Score( 2),2)))-(( Math.pow(state.getScore( 1),4)) +(Math.pow(state.get3Score( 1 ),3))+( Math.pow(state.get2Score( 1),2))));
                
            }
            if (MAX==2)
            {
                return (double) ((( Math.pow(state.getScore( 1),4)) +(Math.pow(state.get3Score( 1 ),3))+( Math.pow(state.get2Score( 1),2)))-(( Math.pow(state.getScore( 2),4)) +(Math.pow(state.get3Score(2),3))+( Math.pow(state.get2Score( 2),2))));
                
            }

        }
        double v = Double.NEGATIVE_INFINITY;
        for( int k = 0; k < 7; k++ )
        {
            if(state.isValidPlay(k))
            {
                GameBoard temp1= new GameBoard(" ");
                temp1.copyFunc(temp1,state);
                temp1.playPiece( k );
                double res;
                res= MinValue(temp1,alpha,beta);
                v = Math.max(v,res);
                if (temp1.getPieceCount()==CHILD && v>MAX_CHILD)
                {
                    MAX_CHILD=v;
                    ACTION=k;
                }
                if (v>=beta)
                {
                    return v;
                }
                alpha= Math.max(alpha,v);
            }
        }
        return v;
        
    }
    
    
    
    public double MinValue(GameBoard state1, double alpha, double beta)
    {
        if (state1.getPieceCount()==42)
        {
            
            if (MAX==1)
            {
                return (double) (state1.getScore(1)-state1.getScore(2));
            }
            if (MAX==2)
            {
                
                return (double) (state1.getScore(2)-state1.getScore(1));
            }
        }
        //eval function is calculated as following;
        // (4-in-rows of min^4 + 3-in-rows of min^3 + 2-in-rows of min^2) -(4-in-rows of max^4 + 3-in-rows of max^3 + 2-in-rows of max^2)
        if (state1.getPieceCount()==MAX_DEPTH)
        {
            if (MAX==1)
            {
                return (double) ((( Math.pow(state1.getScore( 2),4)) +(Math.pow(state1.get3Score( 2 ),3))+( Math.pow(state1.get2Score( 2),2)))-(( Math.pow(state1.getScore( 1),4)) +(Math.pow(state1.get3Score( 1 ),3))+( Math.pow(state1.get2Score( 1),2))));
                
            }
            if (MAX==2)
            {
                return (double) ((( Math.pow(state1.getScore( 1),4)) +(Math.pow(state1.get3Score( 1 ),3))+( Math.pow(state1.get2Score( 1),2)))-(( Math.pow(state1.getScore( 2),4)) +(Math.pow(state1.get3Score(2),3))+( Math.pow(state1.get2Score( 2),2))));
                
            }
            
        }
        double v = Double.POSITIVE_INFINITY;
        for( int k = 0; k < 7; k++ )
        {
            if(state1.isValidPlay(k))
            {
                GameBoard temp2= new GameBoard(" ");
                temp2.copyFunc(temp2,state1);
                temp2.playPiece( k );
                //temp2.printGameBoard();
                double res1;
                res1= MaxValue(temp2,alpha,beta);
                v = Math.min(v,res1);
                if (v<=alpha)
                {
                    return v;
                }
                beta= Math.min(beta,v);
            }
        }
        return v;
        
    }
    
    
}
