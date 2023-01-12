package warcaby;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for our program
 */
public class AppTest 
{
    /**
     *
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
        game.board[3][3] = 1;
        game.board[5][3] = 1;
        game.board[4][6] = 0;
        Assert.assertEquals(1,game.maxKill(2,2,2,0));
        //System.out.println(game.maxKill(4,2,2,0));
        //    Assert.assertEquals(3,game.maxKill(4,2,2,0));
        game.allKill(2);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                //System.out.printf(Integer.toString(game.board[j][i]) + " ");
                System.out.printf(Integer.toString(game.pomBoard[j][i]) + " ");
            }
            System.out.println();
        }
    }
    public void testAllKill(){
        Game game = new Game();
        game.Start(8,false);
        game.allKill(2);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                //   System.out.printf(Integer.toString(game.board[j][i]) + " ");
                //   System.out.printf(Integer.toString(game.pomBoard[j][i]) + " ");
            }
            System.out.println();
        }
    }
}
