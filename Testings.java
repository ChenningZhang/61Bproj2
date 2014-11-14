import list.*;
import player.*;

public class Testings {
    //a list of moves
    public static String toString(DList lst) {
        String result = "";
	try {
	    DListNode curr = (DListNode)lst.front();
	    while(curr.isValidNode()) {
                if (((Move)curr.item()).moveKind == 1) {
                    result = result + " [ ADD " + ((Move)curr.item()).x1 + " "
                        + ((Move)curr.item()).y1 + " ]";
                } else {
                    result = result + " [ STEP " + ((Move)curr.item()).x1 + " "
                        + ((Move)curr.item()).y1 + " "+ ((Move)curr.item()).x2
                        + " " + ((Move)curr.item()).y2 + " ]";
                }
                curr = (DListNode)curr.next();
	    }
	} catch (InvalidNodeException e) { }
        return result;
    }
    //a list of chips
    public static String neighborToString(DList lst) {
        String result = "";
	try {
	    DListNode curr = (DListNode)(lst.front());
	    while (curr.isValidNode()) {
		result = result + ((Chip)(curr.item())).toString();
		curr = ((DListNode)curr.next());
	    }
	} catch (InvalidNodeException e) { }
        return result;
    }

    public static void TestValidMove() {
        MachinePlayer myPlayer = new MachinePlayer(0);
        Move m1 = new Move (0,0);
        Move m2 = new Move (7,0);
        Move m3 = new Move (0,7);
        Move m4 = new Move (7,7);

        //corner test
        if (myPlayer.myBoard().VALID(m1,0) || myPlayer.myBoard().VALID(m2,0) || myPlayer.myBoard().VALID(m3,0) || myPlayer.myBoard().VALID(m4,0)) {
            System.out.println("corner shouldnt be valid!!");
        } else {
            System.out.println("corner tests succeed.");
        }

        //opposite-color test
        for(int i = 1; i < 7; i++) {
            Move m = new Move(0, i);
            Move n = new Move(7, i);
            Move p = new Move(i, 0);
            Move q = new Move(i, 7);
            if (myPlayer.myBoard().VALID(m,0) || myPlayer.myBoard().VALID(n, 0)) {
                System.out.println("black tries to place in white goal area!");
            } else if (myPlayer.myBoard().VALID(p,1) || myPlayer.myBoard().VALID(q,1)) {
                System.out.println("white tries to place in black goal area!");
            } else {
                System.out.println("opposite-color tests succeed.");
            }
        }

        Move m5 = new Move (3, 0);
        //white should go first
        if (myPlayer.myBoard().VALID(m5,0)) {
            System.out.println("white should go first!");
        } else {
            System.out.println("white-first test succeed.");
        }

        Move n1 = new Move (0, 3);
        //Before making any moves, lets see what are the valid moves(TEST FOR VALID MOVES LIST)
        DList lst00 = myPlayer.myBoard().allValidMoves(0);
        DList lst01 = myPlayer.myBoard().allValidMoves(1);
        System.out.println("These are black's valid moves: \n");
        System.out.println(toString(lst00));
        System.out.println("These are white's valid moves: \n");
        System.out.println(toString(lst01));

        myPlayer.opponentMove(n1);  //white's first move

        DList lst10 = myPlayer.myBoard().allValidMoves(0);
        DList lst11 = myPlayer.myBoard().allValidMoves(1);
        System.out.println("These are black's valid moves, after white's first move: \n");
        System.out.println(toString(lst10));
        System.out.println("These are white's valid moves, after white's first move: \n");
        System.out.println(toString(lst11));

        myPlayer.forceMove(m5);    //black's first move
        //print out the board along w/ black/white valid list
        System.out.println(myPlayer.myBoard().toString());
        DList lst20 = myPlayer.myBoard().allValidMoves(0);
        DList lst21 = myPlayer.myBoard().allValidMoves(1);
        System.out.println("These are black's valid moves: \n");
        System.out.println(toString(lst20));
        System.out.println("These are white's valid moves: \n");
        System.out.println(toString(lst21));

        //Test1: cant place in a place already been occupied
        if (myPlayer.myBoard().VALID(m5,0)) {
            System.out.println("black tries to move to a place that was occupied!!");
        } else {
            System.out.println("Test1 succeed.");
        }

        myPlayer.opponentMove(m5);
        if (myPlayer.myBoard().VALID(m5,1)) {
            System.out.println("black tries to move to a place that was occupied!!");
        } else {
            System.out.println("Test1-1 succeed.");
        }

        //Test2: cant have two neighbors
        Move m6 = new Move(2,1);
        myPlayer.forceMove(m6);   //black's first move
        Move m7 = new Move(2,4);
        myPlayer.forceMove(m7);
        Move m8 = new Move(1,6);
        Move m9 = new Move(5,2);
        Move m10 = new Move(5,3);
        Move m11 = new Move(6,5);
        Move m12 = new Move(4,7);
        Move t7 = new Move(1,0);
        Move t8 = new Move(2,0);
        Move t9 = new Move(1,1);
        Move t10 = new Move(3,1);

        myPlayer.forceMove(m8);
        myPlayer.forceMove(m9);
        myPlayer.forceMove(m10);
        myPlayer.forceMove(m11);
        myPlayer.forceMove(m12);

        //print out the board along w/ black/white valid list
        System.out.println(myPlayer.myBoard().toString());
        DList lst30 = myPlayer.myBoard().allValidMoves(0);
        DList lst31 = myPlayer.myBoard().allValidMoves(1);
        System.out.println("These are black's valid moves, after black's a bunch of moves: \n");
        System.out.println(toString(lst30));
        System.out.println("These are white's valid moves, after black's a bunch of moves: \n");
        System.out.println(toString(lst31));

        Move tt = new Move (4,0);
        if (myPlayer.myBoard().VALID(tt,0)) {
            System.out.println("move(4,0) is valid at this point.");
        }

        ////////////
        Move tt1 = new Move(1,0);
        if (myPlayer.myBoard().VALID(tt1,0)) {
            System.out.println("move(1,0) is valid at this point.");
        }
	DList lst = myPlayer.myBoard().adjacent(myPlayer.myBoard().getChip(1,0), 0);
	System.out.println(neighborToString(lst));

	DList lst000 = myPlayer.myBoard().adjacent(myPlayer.myBoard().getChip(2,1),0);
	System.out.println(neighborToString(lst00));

	Move tt2 = new Move(1,7);
	DList lst2 = myPlayer.myBoard().adjacent(myPlayer.myBoard().getChip(1,7), 0);
	try {
	    DListNode curr = (DListNode)(lst2.front());
	    while (curr.isValidNode()) {
		System.out.println(((Chip)(curr.item())).toString());
		curr = ((DListNode)curr.next());
	    }
	} catch (InvalidNodeException e) { }

        if (myPlayer.myBoard().VALID(t7,0)) {
            System.out.println("Too many neighbors. t7");
        } else {
            System.out.println("Test2 succeed.");
        }
        if (myPlayer.myBoard().VALID(t8,0)) {
            System.out.println("Too many neighbors. t8");
        } else {
            System.out.println("Test2 succeed.");
        }
        if (myPlayer.myBoard().VALID(t9,0)) {
            System.out.println("Too many neighbors. t9");
        } else {
            System.out.println("Test2 succeed.");
        }
        if (myPlayer.myBoard().VALID(t10,0)) {
            System.out.println("Too many neighbors. t10");
        } else {
            System.out.println("Test2 succeed.");
        }
        for (int i = 1; i < 4; i++) {
            Move m = new Move(i,2);
            if (myPlayer.myBoard().VALID(m,0)) {
                System.out.println("Too many neighbors.");
            } else {
                System.out.println("Test2 succeed.");
            }
        }

        Move s1 = new Move(3,0,4,0);
        if (myPlayer.myBoard().VALID(s1,0)) {
            System.out.println("stepMoves are not allowed until 10 chips used up.");
        }

	System.out.println("/////////////CONNECTION TESTS//////////////");
	Chip c1 = myPlayer.myBoard().getChip(3,0);
	DList l = myPlayer.myBoard().findConnections(c1);
	System.out.println(neighborToString(l));

	Chip c2 = myPlayer.myBoard().getChip(2,1);
	DList l1 = myPlayer.myBoard().findConnections(c2);
	System.out.println(neighborToString(l1));

	Chip c3 = myPlayer.myBoard().getChip(5,2);
	DList l2 = myPlayer.myBoard().findConnections(c3);
	System.out.println(neighborToString(l2));

	Chip c4 = myPlayer.myBoard().getChip(5,3);
	DList l3 = myPlayer.myBoard().findConnections(c4);
	System.out.println(neighborToString(l3));
    }

    public static void TestWhichOneIsNotWorking() {
        MachinePlayer myPlayer = new MachinePlayer(0);
        Move w1 = new Move(0,3);
        myPlayer.opponentMove(w1);
        Move m1 = new Move(6,0);
        Move m2 = new Move(6,5);
        Move m3 = new Move(5,5);
        Move m4 = new Move(3,3);
        Move m5 = new Move(3,5);
        Move m6 = new Move(5,7);
        myPlayer.forceMove(m1);
        myPlayer.forceMove(m2);
        myPlayer.forceMove(m3);
        myPlayer.forceMove(m4);
        myPlayer.forceMove(m5);
        myPlayer.forceMove(m6);
        System.out.println(myPlayer.myBoard().toString());
        DList blackNetwork = myPlayer.myBoard().findNetwork(0);
        System.out.println(neighborToString(blackNetwork));
    }

    public static void main(String[] args) {
        TestValidMove();
        TestWhichOneIsNotWorking();
    }

}
