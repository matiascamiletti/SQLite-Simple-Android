package garin.artemiy.sqlitesimple;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Author: Artemiy Garin
 * Date: 02.10.13
 */
public class LibraryTestSuite extends TestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(LibraryTestSuite.class).includeAllPackagesUnderHere().build();
    }

}
