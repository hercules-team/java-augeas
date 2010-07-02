package net.augeas;

import java.util.List;

import junit.framework.TestCase;

public class AugeasTest extends TestCase {

    public void testBasics() {
        Augeas aug = new Augeas(Augeas.SAVE_NEWFILE);
        assertEquals("newfile", "newfile", aug.get("/augeas/save"));
        assertNotNull("/augeas/root", aug.get("/augeas/root"));
        assertTrue("/augeas/root exists", aug.exists("/augeas/root"));
        String node = "/java/test/node";
        assertEquals("set", 0, aug.set(node, "JarJarBinks"));
        assertEquals("Retrieve JarJarBinks", "JarJarBinks", aug.get(node));
        String nodeBase = "/java/test";
        String nodeSub = "n*";
        assertEquals("set", 1, aug.setMany(nodeBase, nodeSub, "JarJarBinks2"));
        List<String> matches = aug.match("/*");
        assertTrue("/augeas", matches.contains("/augeas"));
        assertTrue("/java", matches.contains("/java"));
        aug.close();
    }

    public void testErrorAPI() {
        Augeas aug = new Augeas();
        List<String> matches = aug.match("/JarJarBinks");
        assertTrue("No Error", aug.lastError() == AugeasErrorCode.NO_ERROR);
        assertEquals("No error", aug.lastErrorMessage());

        // Turn off exceptions, and try and query the last failure
        aug.setRaiseExceptions(false);
        aug.get("SOME INVALID GOOFY PATH");
        assertTrue("No Error", aug.lastError() == AugeasErrorCode.PATH_ERROR);
        assertEquals("Invalid path expression", aug.lastErrorMessage());
        assertNotNull(aug.lastMinorErrorMessage());
        assertNotNull(aug.lastErrorDetails());
    }

    public void testLoad() {
        Augeas aug = new Augeas(Augeas.NO_LOAD);
        assertTrue("Empty", aug.match("/files/etc/*").isEmpty());
        aug.clearTransforms();
        aug.load();
        assertTrue("Empty2", aug.match("/files/etc/*").isEmpty());
    }

    public void testMove() {
        Augeas aug = new Augeas("/dev/null", null, 0);
        aug.set("/a/b", "value");
        aug.move("/a/b", "/x/y");
        assertEquals("value", "value", aug.get("/x/y"));
        aug.close();
    }

    public void testSomeFailures() {
        Augeas aug = new Augeas();
        List<String> matches = aug.match("/JarJarBinks");
        assertTrue("empty", matches.isEmpty());
        assertNull("bad Get", aug.get("/Some/Bad/Path"));
    }

    public void testUseAfterClose() {
        Augeas aug = new Augeas();
        aug.close();
        try {
            aug.match("/*");
            fail("An exception should be thrown");
        } catch (AugeasException e) {
            // Good.
        }
    }
}
