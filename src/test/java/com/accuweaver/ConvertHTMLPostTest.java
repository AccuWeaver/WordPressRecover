/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accuweaver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    private final static String DIR_NAME = "/Users/robweaver/NetBeansProjects/WordPressRecover/target/test-classes/data/input/2008/11";
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
     * @throws Exception 
     */
    @Test
    public void testGetBottomBranches() throws Exception {
        System.out.println("getBottomBranches");

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
     * @throws Exception 
     */
    @Test
    public void testHasChildren() throws Exception {
        System.out.println("hasChildren");

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
     * @throws Exception 
     */
    @Test
    public void testReadSmallTextFile() throws Exception {
        System.out.println("readSmallTextFile");
        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));
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
     * @throws Exception 
     */
    @Test
    public void testWriteSmallTextFile() throws Exception {
        System.out.println("writeSmallTextFile");
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
     * Test of getUrl method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        String expResult = "http://www.accuweaver/";
        instance.setUrl(expResult);
        String result = instance.getUrl();
        assertEquals(expResult, result);

    }

    /**
     * Test of setUrl method, of class ConvertHTMLPost.
     */
    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "http://www.google.com";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.setUrl(url);
        assertEquals(url, instance.getUrl());
    }

    /**
     * Test of getPostname method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetPostname() {
        System.out.println("getPostname");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        String expResult = "My Post Name";
        instance.setPostname(expResult);
        String result = instance.getPostname();
        assertEquals(expResult, result);


    }

    /**
     * Test of setPostname method, of class ConvertHTMLPost.
     */
    @Test
    public void testSetPostname() {
        System.out.println("setPostname");
        String postname = "My Post";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.setPostname(postname);
        assertEquals(postname, instance.getPostname());

    }

    /**
     * Test of getPostDate method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetPostDate() {
        System.out.println("getPostDate");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        String expResult = "2013-01-04";
        instance.setPostDate(expResult);
        String result = instance.getPostDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPostDate method, of class ConvertHTMLPost.
     */
    @Test
    public void testSetPostDate() {
        System.out.println("setPostDate");
        String postDate = "";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.setPostDate(postDate);
        assertEquals(postDate, instance.getPostDate());
    }

    /**
     * Test of getPostTime method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetPostTime() {
        System.out.println("getPostTime");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        String expResult = "12:20:21";
        instance.setPostTime(expResult);
        String result = instance.getPostTime();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPostTime method, of class ConvertHTMLPost.
     */
    @Test
    public void testSetPostTime() {
        System.out.println("setPostTime");
        String postTime = "22:22:22";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.setPostTime(postTime);
        assertEquals(postTime, instance.getPostTime());
    }

    /**
     * Convenience method to get the full file system file name for testing
     *
     * @param fileName
     * @return Full path for file in the test folder ...
     */
    public static String getRelativeFileName(String fileName) {
        return ConvertHTMLPostTest.class.getClass().getResource(fileName).getFile();
    }

    /**
     * Test of hasIndexFile method, of class ConvertHTMLPost.
     * @throws Exception 
     */
    @Test
    public void testHasIndexFile() throws Exception {
        System.out.println("hasIndexFile");
        ConvertHTMLPost instance = new ConvertHTMLPost();
        boolean result = instance.hasIndexFile(getRelativeFileName("/data/input"));
        assertFalse(result);

        assertTrue(instance.hasIndexFile(DIR_WITH_CHILDREN));

    }
}