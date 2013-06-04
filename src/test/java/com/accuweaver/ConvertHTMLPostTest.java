/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accuweaver;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robweaver
 */
public class ConvertHTMLPostTest {

    private static final Logger logger = Logger.getLogger(ConvertHTMLPostTest.class.getName());
    private final static String DIR_NAME = getRelativeFileName("/data/input");
    private final static String DIR_WITH_CHILDREN = getRelativeFileName("/data/input/2008/11");
    private final static String DIR_WITHOUT_CHILDREN = getRelativeFileName("/data/input/2008/11/19/web-marketing");
    private static final String DIR2_WITHOUT_CHILDREN = getRelativeFileName("/data/input/2008/11/21/plaxo-the-service-i-lovehate");
    private static final String OUTPUT_DIR = getRelativeFileName("/data/output/");
    private static final String INPUT_FILE = getRelativeFileName("/data/input/test.txt");
    private static final String EXPECTED_FILE = getRelativeFileName("/data/expected/test.txt");

    /**
     *
     */
    public ConvertHTMLPostTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getBottomBranches method, of class ConvertHTMLPost.
     *
     * @throws Exception
     */
    @Test
    public void testGetBottomBranches() throws Exception {
        logger.info("getBottomBranches");

        String dirName = DIR_WITH_CHILDREN;
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List<Path> expResult = new ArrayList<>();


        // These are hard coded to my folder, probably should be in a folder in the project to make it all nicely portable.
        addToArray(DIR_WITHOUT_CHILDREN, expResult);
        addToArray(DIR2_WITHOUT_CHILDREN, expResult);

        List<Path> result = instance.getBottomBranches(dirName);
        assertEquals(expResult, result);

        // Test again for good measure ...
        result = instance.getBottomBranches(DIR_NAME);
        assertEquals(expResult, result);


    }

    /**
     * Updates an array of paths for comparison on the getBottomBranches method
     * ...
     *
     * @param pathName
     * @param pathArray
     */
    private void addToArray(String pathName, List<Path> pathArray) {
        pathArray.add(Paths.get(pathName));
    }

    /**
     * Test of hasChildren method, of class ConvertHTMLPost.
     *
     * @throws Exception
     */
    @Test
    public void testHasChildren() throws Exception {
        logger.info("hasChildren");

        // First check with a directory that has children
        String dirName = DIR_WITH_CHILDREN;
        ConvertHTMLPost instance = new ConvertHTMLPost();
        boolean expResult = true;
        boolean result = instance.hasChildren(dirName);
        assertEquals(expResult, result);

        // Now check with a directory that has no children
        dirName = DIR_WITHOUT_CHILDREN;
        expResult = false;
        result = instance.hasChildren(dirName);
        assertEquals(expResult, result);

    }

    /**
     * Test of readSmallTextFile method, of class ConvertHTMLPost.
     *
     * @throws Exception
     */
    @Test
    public void testReadSmallTextFile() throws Exception {
        logger.info("readSmallTextFile");
        logger.log(Level.INFO, "Working Directory = {0}", System.getProperty("user.dir"));
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List<String> expResult = new ArrayList();
        expResult.add("This is a test");
        expResult.add("The quick brown fox ran over the lazy red dog");
        expResult.add("");
        expResult.add("And 1234567890");
        List<String> result = instance.readSmallTextFile(INPUT_FILE);
        assertEquals(expResult, result);

    }

    /**
     * Test of writeSmallTextFile method, of class ConvertHTMLPost.
     *
     * @throws Exception
     */
    @Test
    public void testWriteSmallTextFile() throws Exception {
        logger.info("writeSmallTextFile");
        List<String> aLines = new ArrayList();
        aLines.add("This is a test");
        aLines.add("The quick brown fox ran over the lazy red dog");
        aLines.add("");
        aLines.add("And 1234567890");
        String aFileName = OUTPUT_DIR;
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.writeSmallTextFile(aLines, aFileName + "test.txt");
        assertEquals(
                FileUtils.readFileToString(new File(OUTPUT_DIR + "test.txt"), "utf-8"),
                FileUtils.readFileToString(new File(EXPECTED_FILE), "utf-8"));
    }

    /**
     * Convenience method to get the full file system file name for testing
     *
     * @param fileName
     * @return Full path for file in the test folder ...
     */
    public static String getRelativeFileName(String fileName) {
        URL url = ConvertHTMLPostTest.class.getResource(fileName);
        String returnValue = url.getPath();

        if (returnValue.startsWith("/C:")) {
            returnValue = returnValue.substring(1);
        }
        logger.log(Level.INFO, "File ''{0}'' = ''{1}''", new Object[]{fileName, returnValue});
        return returnValue;
    }

    /**
     * Test of hasIndexFile method, of class ConvertHTMLPost.
     *
     * @throws Exception
     */
    @Test
    public void testHasIndexFile() throws Exception {
        logger.info("hasIndexFile");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        boolean result = instance.hasIndexFile(getRelativeFileName("/data/input"));
        assertFalse(result);

        assertTrue(instance.hasIndexFile(DIR_WITH_CHILDREN));

    }

    /**
     * Test of writeXML method, of class ConvertHTMLPost.
     */
    @Test
    public void testWriteXML() throws Exception {
        System.out.println("writeXML");
        String dirName = "";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List expResult = null;
        List result = instance.writeXML(dirName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFiles method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetFiles() throws Exception {
        System.out.println("getFiles");
        String dirName = "";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List expResult = null;
        List result = instance.getFiles(dirName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class ConvertHTMLPost.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        ConvertHTMLPost.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFile method, of class ConvertHTMLPost.
     */
    @Test
    public void testAddFile() throws Exception {
        System.out.println("addFile");
        String fileName = "";
        List<String> output = null;
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.addFile(fileName, output);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
