package garin.artemiy.sqlitesimple;

import android.test.AndroidTestCase;
import org.junit.Assert;

/**
 * Author: Artemiy Garin
 * Date: 03.10.13
 */
public class LibraryTest extends AndroidTestCase {

    public void testFunctional() {
        Assert.assertTrue(1 + 1 == 2);
    }

    public void testSpeed() {
        Assert.assertEquals(1, 2);
    }

}
