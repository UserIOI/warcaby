package warcaby;

import org.junit.Assert;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * Unit test for our program
 */
public class AppTest 
{
    /**
     * Testy dla funckji z Game
     */
    @Test
    public void testPionkiPoDrodze() {
        Game game = new Game();
        game.Start(8,false);
        Assert.assertEquals(1,game.pionkiPoDrodze(1,1,3,3,4));
        Assert.assertEquals(4,game.pionkiPoDrodze(1,1,7,7,4));
        Assert.assertEquals(4,game.pionkiPoDrodze(7,7,1,1,1));

    }
    @Test
    public void testMaxKill(){
        Game game = new Game();
        game.Start(8,false);
        game.board[5][3] = 1;
        game.board[4][6] = 0;
        Assert.assertEquals(0,game.maxKill(2,2,2,0));
        Assert.assertEquals(3,game.maxKill(4,2,2,0));
    }
    @Test
    public void testMaxKillDamki(){
        Game game = new Game();
        game.Start(8,false);
        game.board[2][6] = 0;
        Assert.assertEquals(2,game.maxKillDamki(6,2,2,0));
    }
    @Test
    public void testAllKill(){
        Game game = new Game();
        game.Start(8,false);
        game.board[5][3] = 1;
        game.board[4][6] = 0;
        game.board[1][3] = 1;
        game.allKill(2);
        Assert.assertEquals(3,game.pomBoard[0][2]);
        Assert.assertEquals(1,game.pomBoard[2][2]);
        Assert.assertEquals(3,game.pomBoard[4][2]);
        Assert.assertEquals(1,game.pomBoard[6][2]);
//        for(int i=0;i<8;i++){
//            for(int j=0;j<8;j++){
//                System.out.printf(Integer.toString(game.pomBoard[j][i]) + " ");
//            }
//            System.out.println();
//        }
    }
    @Test
    public void testMovePawn() {
        Game game = new Game();
        game.new Player(1);
        game.new Player(2);
        game.board[2][4] = 2;
        Assert.assertEquals(true,game.move(game.currentPlayer, 1,5,3,3));
    }
    @Test
    public void testMoveQueen(){
        Game game = new Game();
        game.new Player(1);
        game.new Player(2);
        game.board[2][6] = 0;
        game.board[6][2] = 20;
        game.currentPlayer = game.currentPlayer.opponent;
        Assert.assertEquals(false,game.move(game.currentPlayer, 6,2,5,3));
        Assert.assertEquals(true,game.move(game.currentPlayer, 6,2,2,6));
    }
}
