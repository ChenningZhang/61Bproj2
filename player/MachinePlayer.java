/* MachinePlayer.java */

package player;
import list.*;
import java.util.Random;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    protected int playerColor;
    protected int playerDepth;
    protected GameBoard myBoard;

    // Creates a machine player with the given color.  Color is either 0 (black)
    // or 1 (white).  (White has the first move.)
    public MachinePlayer(int color) {
        playerColor = color;
        playerDepth = 2;
        myBoard = new GameBoard();
    }

    // Creates a machine player with the given color and search depth.  Color is
    // either 0 (black) or 1 (white).  (White has the first move.)
    public MachinePlayer(int color, int searchDepth) {
        playerColor = color;
        playerDepth = searchDepth;
        myBoard = new GameBoard();
    }

    public GameBoard myBoard() {
	return myBoard;
    }

    // Returns a new move by "this" player.  Internally records the move (updates
    // the internal game board) as a move by "this" player.
    public Move chooseMove() {
        /*
        Move m = new Move();
        DList validMoves = myBoard.allValidMoves(playerColor);
        Random index = new Random();
        int i = index.nextInt(validMoves.length());
        int start = 0;
        try {
        DListNode curr = (DListNode)validMoves.front();
        while (start < i) {
            curr = (DListNode)curr.next();
            start ++;
        }
        m = (Move)curr.item();
        } catch (InvalidNodeException e) { }
        */
        Best chosenMove = this.minimax(playerColor, -100, 100, playerDepth);
        Move m = chosenMove.move;
        this.forceMove(m);
        return m;
    }

    // If the Move m is legal, records the move as a move by the opponent
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method allows your opponents to inform you of their moves.
    public boolean opponentMove(Move m) {
        int opColor;
        if (playerColor == 0) {
            opColor = 1;
        } else {
            opColor = 0;
        }
        if (myBoard.VALID(m, opColor)) {
            if (m.moveKind == 1) {
                myBoard.addChip(m.x1, m.y1, opColor);
            }
            else if (m.moveKind == 2) {
                myBoard.moveChip(m.x1, m.y1, m.x2, m.y2, opColor);
            }
            return true;
        }
        return false;
    }

    // If the Move m is legal, records the move as a move by "this" player
    // (updates the internal game board) and returns true.  If the move is
    // illegal, returns false without modifying the internal state of "this"
    // player.  This method is used to help set up "Network problems" for your
    // player to solve.
    public boolean forceMove(Move m) {
	if (myBoard.VALID(m, playerColor)) {
	    // ADDmoves
	    if (m.moveKind == 1) {
		myBoard.addChip(m.x1, m.y1, playerColor);
		//STEPmoves
	    } else if (m.moveKind == 2) {
		myBoard.moveChip(m.x1, m.y1, m.x2, m.y2, playerColor);
	    }
	    return true;
	}
	return false;
    }

    public Best minimax(int color, int alpha, int beta, int searchDepth) {
        // make a new class Best
        Best myBest = new Best();
        Best reply;
        int opColor;
        if (color == 1) {
            opColor = 0;
        } else {
            opColor = 1;
        }
        // base case or has a network already
        if (myBoard.findNetwork(0).length() != 0 || myBoard.findNetwork(1).length() != 0 || searchDepth == 0) {
            myBest.score = myBoard.EvalFunc(color);
        }

        // if it's COMPUTER's turn
        if (this.playerColor == color) {
            myBest.score = alpha;
            // if it's OPPONENT's turn
        } else {
            myBest.score = beta;
        }
        // assign any legal move
        //myBest.move = this.myBoard.allValidMoves(color).front().item();
        // for each legal move
        try {
            DListNode curr = (DListNode)myBoard.allValidMoves(color).front();
            while (curr.isValidNode()) {
                Move m = (Move)curr.item();

                // perform m
                if (color == playerColor) {
                    this.forceMove(m);
                } else {
                    this.opponentMove(m);
                }

                //reply
                if (this.playerColor == color) {
                    reply = minimax(opColor, alpha, beta, searchDepth-1);
                } else {
                    reply = minimax(playerColor, alpha, beta, searchDepth-1);
                }
                //UNDO MOVE (HOW??????)  -- separate cases: delete from blackChips/whiteChips on myBoard
                myBoard.UndoMove(m);

                if (this.playerColor == color && reply.score > myBest.score) {
                    myBest.move = m;
                    myBest.score = reply.score;
                    alpha = reply.score;
                } else if (this.playerColor != color && reply.score < myBest.score) {
                    myBest.move = m;
                    myBest.score = reply.score;
                    beta = reply.score;
                }
                if (alpha > beta) {return myBest; }
                curr = (DListNode)curr.next();
            }
        } catch (InvalidNodeException e) { }
        return myBest;
    }
    
}
