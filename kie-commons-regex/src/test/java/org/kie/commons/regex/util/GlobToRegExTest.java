package org.kie.commons.regex.util;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 *
 */
public class GlobToRegExTest {

    @Test
    public void simpleTest() {
        assertEquals( ".*\\.txt", GlobToRegEx.globToRegex( "*.txt" ) );
        assertEquals( "myfile\\.txt", GlobToRegEx.globToRegex( "myfile.txt" ) );
        assertEquals( ".?at\\.txt", GlobToRegEx.globToRegex( "?at.txt" ) );
        assertEquals( "Law.*", GlobToRegEx.globToRegex( "Law*" ) );
        assertEquals( "[CB]at\\.txt", GlobToRegEx.globToRegex( "[CB]at.txt" ) );
        assertEquals( "Law\\*", GlobToRegEx.globToRegex( "Law\\*" ) );
        assertEquals( "", GlobToRegEx.globToRegex( "" ) );
        assertEquals( "/", GlobToRegEx.globToRegex( "/" ) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void simpleNull() {
        GlobToRegEx.globToRegex( null );
    }
}
