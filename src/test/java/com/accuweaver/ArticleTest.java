/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accuweaver;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger logger = Logger.getLogger(ArticleTest.class.getName());
    // Encoding for page
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    // Test file (actual data) ...
    private final static String TEST_FILE_NAME = getRelativeFileName("/data/input/2008/11/19/web-marketing/index.html");
    // input array that we read in from TEST_FILE_NAME ...
    private static List<String> testInput;
    // String representing what we see for a post ...
    private final static String POST_ARTICLE = "<article "
            + "id=\"post-17\" class=\"post-17 post type-post status-publish "
            + "format-standard hentry category-restful-web-services category-web "
            + "tag-data tag-data-formats tag-html tag-markup-language tag-tools "
            + "tag-uniform-resource-locator tag-xhtml tag-xml content-single \">";
    // String representing what we see for a page
    private final static String PAGE_ARTICLE = "<article id=\"post-1958\" "
            + "class=\"post-1958 page type-page status-publish hentry "
            + "content-page\">";

    public ArticleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        testInput = readSmallTextFile(TEST_FILE_NAME);
    }

    /**
     * Read a text file ...
     *
     * @param aFileName
     * @return
     * @throws IOException
     */
    private static List<String> readSmallTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        List<String> returnList = new ArrayList<>();
        try {
            returnList = Files.readAllLines(path, ENCODING);
        } catch (MalformedInputException mie) {
        }
        return returnList;
    }

    /**
     * Convenience method to get the full file system file name for testing
     *
     * @param fileName
     * @return Full path for file in the test folder ...
     */
    private static String getRelativeFileName(String fileName) {
        URL url = ConvertHTMLPostTest.class.getResource(fileName);
        String returnValue = url.getPath();

        if (returnValue.startsWith("/C:")) {
            returnValue = returnValue.substring(1);
        }
        logger.log(Level.INFO, "File ''{0}'' = ''{1}''", new Object[]{fileName, returnValue});
        return returnValue;
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
        List<String> myList = new ArrayList(Arrays.asList("a", "b"));
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
    public void testGetContent() throws IOException {
        System.out.println("getContent");
        Article instance = new Article();
        String expResult = "<wp:post_id>21</wp:post_id>"
                + "<wp:post_date></wp:post_date>"
                + "<wp:post_date_gmt></wp:post_date_gmt>"
                + "<wp:comment_status>open</wp:comment_status>"
                + "<wp:ping_status>open</wp:ping_status>"
                + "<wp:post_name>null</wp:post_name>"
                + "<wp:status>publish</wp:status>"
                + "<wp:post_parent>0</wp:post_parent>"
                + "<wp:menu_order>0</wp:menu_order>"
                + "<wp:post_type>post</wp:post_type>"
                + "<wp:post_password></wp:post_password>"
                + "<wp:is_sticky>0</wp:is_sticky>"
                + "<category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>";
        String result = instance.getContent();
        assertEquals(expResult, result);

        instance = new Article(testInput);

    }

    /**
     * Test of getPostDate method, of class Article.
     */
    @Test
    public void testGetPostDate() {
        System.out.println("getPostDate");
        Article instance = new Article();
        String expResult = null;
        String result = instance.getPostDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of getEndContent method, of class Article.
     */
    @Test
    public void testGetEndContent() {
        System.out.println("getEndContent");
        Article instance = new Article();
        String expResult = "]]></content:encoded>\n";
        String result = instance.getEndContent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPostIdString method, of class Article.
     */
    @Test
    public void testGetPostIdString() {
        System.out.println("getPostIdString");
        int postId = 0;
        Article instance = new Article();
        String expResult = "<wp:post_id>0</wp:post_id>";
        String result = instance.getPostIdString(postId);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPostDateString method, of class Article.
     */
    @Test
    public void testGetPostDateString() {
        System.out.println("getPostDateString");
        Article instance = new Article();
        String expResult = "<wp:post_date></wp:post_date>";
        String result = instance.getPostDateString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPostDateGMTString method, of class Article.
     */
    @Test
    public void testGetPostDateGMTString() {
        System.out.println("getPostDateGMTString");
        Article instance = new Article();
        String expResult = "<wp:post_date_gmt></wp:post_date_gmt>";
        String result = instance.getPostDateGMTString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCommentStatusString method, of class Article.
     */
    @Test
    public void testGetCommentStatusString() {
        System.out.println("getCommentStatusString");
        Article instance = new Article();
        String expResult = "<wp:comment_status>open</wp:comment_status>";
        String result = instance.getCommentStatusString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPingStatusString method, of class Article.
     */
    @Test
    public void testGetPingStatusString() {
        System.out.println("getPingStatusString");
        Article instance = new Article();
        String expResult = "<wp:ping_status>open</wp:ping_status>";
        String result = instance.getPingStatusString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getPostNameString method, of class Article.
     */
    @Test
    public void testGetPostNameString() {
        System.out.println("getPostNameString");
        Article instance = new Article();
        String expResult = "<wp:post_name>null</wp:post_name>";
        String result = instance.getPostNameString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getFixedStatusStrings method, of class Article.
     */
    @Test
    public void testGetFixedStatusStrings() {
        System.out.println("getFixedStatusStrings");
        Article instance = new Article();
        String expResult = "<wp:status>publish</wp:status>"
                + "<wp:post_parent>0</wp:post_parent>"
                + "<wp:menu_order>0</wp:menu_order>"
                + "<wp:post_type>post</wp:post_type>"
                + "<wp:post_password></wp:post_password>"
                + "<wp:is_sticky>0</wp:is_sticky>"
                + "<category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>";
        String result = instance.getFixedStatusStrings();
        assertEquals(expResult, result);

    }

    /**
     * Test of addItem method, of class Article.
     */
    @Test
    public void testAddItem() throws Exception {
        System.out.println("addItem");
        List<String> input = new ArrayList<>();
        input.add("Test");
        input.add("Test2");
        Article instance = new Article();
        String expResult = null;
        String result = instance.addItem(input);
        assertEquals(expResult, result);

        // Now test something more meaningful: use a test file that we know will
        // produce an actual result to see if we get the values set the way we
        // expect ...

        // TODO: fix this up to be a more accurate test using a simpler file

        expResult = "<item><title>Web marketing</title><content:encoded><![CDATA[\n"
                + "		<p>Recently I&#8217;ve entered the world of using "
                + "the web for self <a class=\"zem_slink freebase/en/marketing\" "
                + "title=\"Marketing\" rel=\"wikipedia\" "
                + "href=\"http://en.wikipedia.org/wiki/Marketing\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent',"
                + "'outbound-article','http://en.wikipedia.org']);\">"
                + "marketing</a>.</p><p>I saw a very interesting talk by "
                + "<a title=\"Walter Feigenson\" href=\"http://feigenson.us/\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent',"
                + "'outbound-article','http://feigenson.us']);\" "
                + "target=\"_blank\">Walter Feigenson</a> at the last "
                + "<a title=\"CPC Job Connections\" "
                + "href=\"http://www.jobconnections.org/\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent',"
                + "'outbound-article','http://www.jobconnections.org']);\">"
                + "CPC Job Connections</a> meeting about marketing yourself using "
                + "the web.</p>"
                + "<p><span id=\"more-6\"></span></p><p>I already had a "
                + "<a class=\"zem_slink rdfa\" title=\"LinkedIn\" rel=\"stag:means "
                + "homepage\" href=\"http://www.linkedin.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.linkedin.com']);\">"
                + "LinkedIn</a> profile, and had my resume on a couple different "
                + "places, but his talk convinced me that I ought to do some more. "
                + "So I did the following:</p><ol><li> Set up "
                + "<a href=\"http://reader.google.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://reader.google.com']);\">"
                + "<span class=\"zem_slink rdfa\">Google</span> "
                + "<span class=\"zem_slink rdfa\">reader</span></a>"
                + " so I can see all the web changes in one place.</li>"
                + "<li>Built a profile on <a class=\"zem_slink freebase/en/naymz\" "
                + "title=\"Naymz\" rel=\"homepage\" href=\"http://www.naymz.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.naymz.com']);\">"
                + "Naymz</a> (<a href=\"http://www.naymz.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent',"
                + "'outbound-article','http://www.naymz.com']);\">"
                + "http://www.naymz.com</a>), unclear on exactly what this one "
                + "does.</li>"
                + "<li>Ziki (<a href=\"http://www.ziki.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.ziki.com']);\">"
                + "http://www.ziki.com</a>) &#8211; Signed up, but never got the "
                + "validation email. This is supposed to be a job finding service.</li>"
                + "<li><a class=\"zem_slink rdfa\" title=\"Spokeo\" rel=\"stag:means "
                + "homepage\" href=\"http://www.spokeo.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.spokeo.com']);\">"
                + "Spokeo</a> (<a href=\"http://www.spokeo.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.spokeo.com']);\">http://www.spokeo.com</a>) "
                + "&#8211; Signed up &#8211; not clear on what this site does "
                + "beyond search for names.</li>"
                + "<li><a class=\"zem_slink\" title=\"Ziggs\" rel=\"homepage\" "
                + "href=\"http://www.ziggs.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.ziggs.com']);\">"
                + "Ziggs</a> (<a href=\"http://www.ziggs.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.ziggs.com']);\">"
                + "http://www.ziggs.com</a>) &#8211; Signed up and built profile, "
                + "this one looks interesting.</li>"
                + "</ol><p>Just signing up for these things takes time, getting "
                + "them to be consistent seems like it will be a pain. It reminds "
                + "me of posting your resume to all of the job search sites. Not "
                + "too bad the first time, but then going back to update is going "
                + "to be hard.</p><p>Next thing I did was to add <a class=\"zem_slink "
                + "freebase/en/cross-link\" title=\"Cross-link\" rel=\"wikipedia\" "
                + "href=\"http://en.wikipedia.org/wiki/Cross-link\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://en.wikipedia.org']);\">"
                + "cross links</a> from as many different places as I could to my "
                + "<a class=\"zem_slink rdfa\" title=\"Website\" rel=\"stag:means wikipedia\" "
                + "href=\"http://en.wikipedia.org/wiki/Website\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://en.wikipedia.org']);\">web site</a> "
                + "(http://www.accuweaver.com). This is supposed to help with the "
                + "ranking on the <a class=\"zem_slink rdfa\" title=\"Web search engine\" "
                + "rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Web_search_engine\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://en.wikipedia.org']);\">"
                + "search engines</a>, since the search engines use the assumption "
                + "that if a lot of sites link to you, you must be important.</p>"
                + "<p>I also cleaned up my <a class=\"zem_slink freebase/en/linkedin\" "
                + "title=\"LinkedIn\" rel=\"homepage\" href=\"http://www.linkedin.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.linkedin.com']);\">"
                + "LinkedIn profile</a>, added links, and added my company to the "
                + "Companies part of LinkedIn.Then after all of this, I got hit "
                + "again with the suggestion that I should set up a "
                + "<a class=\"zem_slink rdfa\" title=\"Facebook\" rel=\"stag:means "
                + "homepage\" href=\"http://facebook.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://facebook.com']);\">"
                + "Facebook</a> profile. Walt had mentioned it, but it took hearing "
                + "it a few more times for me to act.  It still seems a bit smarmy, "
                + "and unlikely to be useful as a <a class=\"zem_slink rdfa\" "
                + "title=\"Business networking\" rel=\"stag:means wikipedia\" "
                + "href=\"http://en.wikipedia.org/wiki/Business_networking\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://en.wikipedia.org']);\">business networking</a> "
                + "tool, but we&#8217;ll see.</p><p>Next: Making sure I&#8217;m "
                + "posted on a huge list of sites I got from <a "
                + "href=\"http://www.brightideasandconcepts.com\" "
                + "onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article',"
                + "'http://www.brightideasandconcepts.com']);\">Valerie Colber"
                + "</a> &#8230;</p>]]></content:encoded>\n"
                + "<wp:post_id>21</wp:post_id>"
                + "<wp:post_date>2008-11-19 19:26:34</wp:post_date>"
                + "<wp:post_date_gmt>2008-11-19 19:26:34</wp:post_date_gmt>"
                + "<wp:comment_status>open</wp:comment_status>"
                + "<wp:ping_status>open</wp:ping_status>"
                + "<wp:post_name>web-marketing</wp:post_name>"
                + "<wp:status>publish</wp:status>"
                + "<wp:post_parent>0</wp:post_parent>"
                + "<wp:menu_order>0</wp:menu_order>"
                + "<wp:post_type>post</wp:post_type>"
                + "<wp:post_password></wp:post_password>"
                + "<wp:is_sticky>0</wp:is_sticky>"
                + "<category domain=\"post_tag\" nicename=\"recovered\">"
                + "<![CDATA[Recovered Post]]></category>"
                + "</item>";
        result = instance.addItem(testInput);
        assertEquals(expResult, result);

        // Now test the page (instead of post) to make sure we get nothing 
        // should break before we parse the tags.
        instance = new Article();
        List<String> page = new ArrayList<>();
        page.add(PAGE_ARTICLE);
        instance.addItem(page);
        assertEquals(null, instance.getTags());

        // And then test if we get some real value from the data when we
        // use one that has tags ...
        List<String> post = new ArrayList<>();
        post.add(POST_ARTICLE);
        instance.addItem(post);
        List<String> expectedTags = new ArrayList<>();
        expectedTags.add("data");
        expectedTags.add("data-formats");
        expectedTags.add("html");
        expectedTags.add("markup-language");
        expectedTags.add("tools");
        expectedTags.add("uniform-resource-locator");
        expectedTags.add("xhtml");
        expectedTags.add("xml");
        List<String> results = instance.getTags();
        assertEquals(results.size(), 8);
        Iterator it = results.iterator();
        for (String expected : expectedTags) {
            if (it.hasNext()) {
                result = (String) (it.next());
                if (result.equalsIgnoreCase(expected)) {
                    continue;
                } else {
                    fail("Failed to match '" + expected + "' for result of '" + result + "'");
                    break;
                }
            } else {
                fail("Expected " + expectedTags.size() + " but got " + results.size() + " results");
                break;
            }
        }
    }

    /**
     * Test of wrapItem method, of class Article.
     */
    @Test
    public void testWrapItem() {
        System.out.println("wrapItem");
        String item = "";
        Article instance = new Article();
        String expResult = "<item></item>";
        String result = instance.wrapItem(item);
        assertEquals(expResult, result);

    }
}