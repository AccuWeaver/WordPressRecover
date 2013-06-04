/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accuweaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rweaver
 */
public class ArticleTest {

    public ArticleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getUrl method, of class Article.
     */
    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        Article instance = new Article();
        String expResult = "http://www.accuweaver/";
        instance.setUrl(expResult);
        String result = instance.getUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUrl method, of class Article.
     */
    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "http://www.google.com";
        Article instance = new Article();
        instance.setUrl(url);
        assertEquals(url, instance.getUrl());
    }

    /**
     * Test of getPostname method, of class Article.
     */
    @Test
    public void testGetPostname() {
        System.out.println("getPostname");
        Article instance = new Article();
        String expResult = "My Post Name";
        instance.setPostname(expResult);
        String result = instance.getPostname();
        assertEquals(expResult, result);

    }

    /**
     * Test of setPostname method, of class Article.
     */
    @Test
    public void testSetPostname() {
        System.out.println("setPostname");
        String postname = "My Post";
        Article instance = new Article();
        instance.setPostname(postname);
        assertEquals(postname, instance.getPostname());

    }

     /**
     * Test of setPostDate method, of class Article.
     */
    @Test
    public void testSetPostDate() {
        System.out.println("setPostDate");
        String postDate = "";
        Article instance = new Article();
        instance.setPostDate(postDate);
        assertEquals(postDate, instance.getPostDate());
    }

    /**
     * Test of getPostTime method, of class Article.
     */
    @Test
    public void testGetPostTime() {
        System.out.println("getPostTime");
        Article instance = new Article();
        String expResult = "12:20:21";
        instance.setPostTime(expResult);
        String result = instance.getPostTime();
        assertEquals(expResult, result);

    }


    /**
     * Test of setPostTime method, of class Article.
     */
    @Test
    public void testSetPostTime() {
        System.out.println("setPostTime");
        String postTime = "22:22:22";
        Article instance = new Article();
        instance.setPostTime(postTime);
        assertEquals(postTime, instance.getPostTime());
    }


    /**
     * Test of getPostTitle method, of class Article.
     */
    @Test
    public void testGetPostTitle() {
        System.out.println("getPostTitle");
        Article instance = new Article();
        String expResult = "This is my title";
        instance.setPostTitle(expResult);
        String result = instance.getPostTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPostTitle method, of class Article.
     */
    @Test
    public void testSetPostTitle() {
        System.out.println("setPostTitle");
        String postTitle = "This is another title";
        Article instance = new Article();
        instance.setPostTitle(postTitle);
        assertEquals(postTitle, instance.getPostTitle());
    }

    /**
     * Test of getPostContents method, of class Article.
     */
    @Test
    public void testGetPostContents() throws IOException {
        System.out.println("getPostContents");
        List<String> myList = new ArrayList(Arrays.asList("a","b"));
        Article instance = new Article(myList);
        String expResult = null;
        String result = instance.getPostContents();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setPostContents method, of class Article.
     */
    @Test
    public void testSetPostContents() {
        System.out.println("setPostContents");
        String postContents = "Here are some contents";
        Article instance = new Article();
        instance.setPostContents(postContents);
        assertEquals(postContents, instance.getPostContents());
    }

    /**
     * Test of getContent method, of class Article.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostDate method, of class Article.
     */
    @Test
    public void testGetPostDate() {
        System.out.println("getPostDate");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPostDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEndContent method, of class Article.
     */
    @Test
    public void testGetEndContent() {
        System.out.println("getEndContent");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getEndContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostIdString method, of class Article.
     */
    @Test
    public void testGetPostIdString() {
        System.out.println("getPostIdString");
        int postId = 0;
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPostIdString(postId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostDateString method, of class Article.
     */
    @Test
    public void testGetPostDateString() {
        System.out.println("getPostDateString");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPostDateString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostDateGMTString method, of class Article.
     */
    @Test
    public void testGetPostDateGMTString() {
        System.out.println("getPostDateGMTString");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPostDateGMTString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommentStatusString method, of class Article.
     */
    @Test
    public void testGetCommentStatusString() {
        System.out.println("getCommentStatusString");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getCommentStatusString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPingStatusString method, of class Article.
     */
    @Test
    public void testGetPingStatusString() {
        System.out.println("getPingStatusString");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPingStatusString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostNameString method, of class Article.
     */
    @Test
    public void testGetPostNameString() {
        System.out.println("getPostNameString");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getPostNameString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFixedStatusStrings method, of class Article.
     */
    @Test
    public void testGetFixedStatusStrings() {
        System.out.println("getFixedStatusStrings");
        Article instance = new Article();
        String expResult = "";
        String result = instance.getFixedStatusStrings();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addItem method, of class Article.
     */
    @Test
    public void testAddItem() throws Exception {
        System.out.println("addItem");
        List<String> input = null;
        Article instance = new Article();
        String expResult = "";
        String result = instance.addItem(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wrapItem method, of class Article.
     */
    @Test
    public void testWrapItem() {
        System.out.println("wrapItem");
        String item = "";
        Article instance = new Article();
        String expResult = "";
        String result = instance.wrapItem(item);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}