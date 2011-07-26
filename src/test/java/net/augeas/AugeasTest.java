package net.augeas;

import junit.framework.TestCase;

import java.util.List;

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

    // TODO find some way to make this test more portable!!
    public void testSpan() {
        Augeas aug = new Augeas();
        aug.set("/augeas/span","enable");
        aug.rm("/files");
        aug.load();

        SpanResult got = aug.span("/files/etc/passwd[1]");
        assertEquals("/etc/passwd", got.getFilename());
        assertEquals(0, got.getValueStart());
        assertEquals(0, got.getValueEnd());
        assertEquals(0, got.getSpanStart());
        assertTrue(got.getSpanEnd() > 0);
        assertEquals(0, got.getLabelStart());
        assertEquals(0, got.getLabelEnd());
    }

    /* test enable span on load */
    public void testSpanLoad() {
        Augeas aug = new Augeas(Augeas.AUG_ENABLE_SPAN);
        SpanResult got = aug.span("/files/etc/passwd[1]");
        assertEquals("/etc/passwd", got.getFilename());
    }

    /* test error code in the case span is disabled */
    public void testSpanError() {
        Augeas aug = new Augeas();
        aug.setRaiseExceptions(false);
        SpanResult span = aug.span("/files/etc/passwd");
        AugeasErrorCode lastError = aug.lastError();
        assertEquals(AugeasErrorCode.NO_SPAN, lastError);
    }

}
