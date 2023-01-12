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
    public void testPionkiPoDrodze()
    {
        Game game = new Game();
        game.Start(8,false);
        Assert.assertEquals(1,game.pionkiPoDrodze(1,1,3,3,4));
        Assert.assertEquals(4,game.pionkiPoDrodze(1,1,7,7,4));
        Assert.assertEquals(4,game.pionkiPoDrodze(7,7,1,1,1));

    }
}
