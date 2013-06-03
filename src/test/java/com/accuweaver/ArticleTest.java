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
}