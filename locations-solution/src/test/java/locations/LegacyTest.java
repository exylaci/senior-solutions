package locations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LegacyTest {

    @Test
    public void testInOnEquator(){
        assertEquals(true,new Location("",0,1).isOnEquator());
    }
}
