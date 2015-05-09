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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author robweaver
 */
public class ConvertHTMLPostTest {

    private static final Logger logger = Logger.getLogger(ConvertHTMLPostTest.class.getName());
    private final static String DIR_NAME = getRelativeFileName("/data/input");
    private static final String INPUT_FILE = DIR_NAME + "/test.txt";
    private final static String DIR_WITH_CHILDREN = getRelativeFileName("/data/input/2008/11");
    private final static String DIR_WITHOUT_CHILDREN = getRelativeFileName("/data/input/2008/11/19/web-marketing");
    private static final String DIR2_WITHOUT_CHILDREN = getRelativeFileName("/data/input/2008/11/21/plaxo-the-service-i-lovehate");
    private static final String OUTPUT_DIR = getRelativeFileName("/data/output/");
    private static final String OUTPUT_XML = OUTPUT_DIR + "/output.xml";
    private static final String EXPECTED_DIR = getRelativeFileName("/data/expected/");
    private static final String EXPECTED_FILE = EXPECTED_DIR + "/test.txt";
    private static final String EXPECTED_XML = EXPECTED_DIR + "/output.xml";

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
     *
     */
    public ConvertHTMLPostTest() {
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
        String dirName = DIR_NAME;
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List<String> expResults = new ArrayList<>();

        // TODO: move all these strings to constants ...
        // XML string
        expResults.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        // Comments and RSS tag ..
        expResults.add("<!-- This is a WordPress eXtended RSS file generated by WordPress as an export of your site. -->\n"
                + "<!-- It contains information about your site's posts, pages, comments, categories, and other content. -->\n"
                + "<!-- You may use this file to transfer that content from one site to another. -->\n"
                + "<!-- This file is not intended to serve as a complete backup of your site. -->\n"
                + "\n"
                + "<!-- To import this information into a WordPress site follow these steps: -->\n"
                + "<!-- 1. Log in to that site as an administrator. -->\n"
                + "<!-- 2. Go to Tools: Import in the WordPress admin panel. -->\n"
                + "<!-- 3. Install the \"WordPress\" importer from the list. -->\n"
                + "<!-- 4. Activate & Run Importer. -->\n"
                + "<!-- 5. Upload this file using the form provided on that page. -->\n"
                + "<!-- 6. You will first be asked to map the authors in this export file to users -->\n"
                + "<!--    on the site. For each author, you may choose to map to an -->\n"
                + "<!--    existing user on the site or to create a new user. -->\n"
                + "<!-- 7. WordPress will then import each of the posts, pages, comments, categories, etc. -->\n"
                + "<!--    contained in this file into your site. -->\n"
                + "\n"
                + "<!-- generator=\"WordPress/3.5.1\" created=\"2013-05-19 22:38\" -->\n"
                + "<rss version=\"2.0\"\n"
                + "     xmlns:excerpt=\"http://wordpress.org/export/1.2/excerpt/\"\n"
                + "     xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n"
                + "     xmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n"
                + "     xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
                + "     xmlns:wp=\"http://wordpress.org/export/1.2/\"\n"
                + ">\n"
                + "\n");
        // Channel, title, etc ...
        expResults.add("    <channel>\n"
                + "        <title>AccuWeaver LLC</title>\n"
                + "        <link>http://accuweaver.com</link>\n"
                + "        <description>Just another WordPress site</description>\n"
                + "        <pubDate>Sun, 19 May 2013 22:38:22 +0000</pubDate>\n"
                + "        <language>en-US</language>\n"
                + "        <wp:wxr_version>1.2</wp:wxr_version>\n"
                + "        <wp:base_site_url>http://accuweaver.com</wp:base_site_url>\n"
                + "        <wp:base_blog_url>http://accuweaver.com</wp:base_blog_url>\n"
                + "\n");
        // Author
        expResults.add("        <wp:author>\n"
                + "            <wp:author_id>1</wp:author_id>\n"
                + "            <wp:author_login>admin</wp:author_login>\n"
                + "            <wp:author_email>rob@accuweaver.com</wp:author_email>\n"
                + "            <wp:author_display_name><![CDATA[admin]]></wp:author_display_name>\n"
                + "            <wp:author_first_name><![CDATA[]]></wp:author_first_name>\n"
                + "            <wp:author_last_name><![CDATA[]]></wp:author_last_name>\n"
                + "        </wp:author>\n"
                + "\n");
        // Generator string ...
        expResults.add("        <generator>http://wordpress.org/?v=3.5.1</generator>\n");

        // file information (where this came from) ..
        expResults.add("\n<!-- '" + DIR_WITHOUT_CHILDREN + "/index.html' -->\n");

        // Post (item)
        expResults.add("<item>"
                + "<title>Web marketing</title>"
                + "<content:encoded><![CDATA[\n"
                + "		<p>Recently I&#8217;ve entered the world of using the web for self <a class=\"zem_slink freebase/en/marketing\" title=\"Marketing\" rel=\"wikipedia\" href=\"http://en.wikipedia.org/wiki/Marketing\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">marketing</a>.</p>"
                + "<p>I saw a very interesting talk by <a title=\"Walter Feigenson\" href=\"http://feigenson.us/\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://feigenson.us']);\" target=\"_blank\">Walter Feigenson</a> at the last <a title=\"CPC Job Connections\" href=\"http://www.jobconnections.org/\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.jobconnections.org']);\">CPC Job Connections</a> meeting about marketing yourself using the web.</p>"
                + "<p><span id=\"more-6\"></span></p>"
                + "<p>I already had a <a class=\"zem_slink rdfa\" title=\"LinkedIn\" rel=\"stag:means homepage\" href=\"http://www.linkedin.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.linkedin.com']);\">LinkedIn</a> profile, and had my resume on a couple different places, but his talk convinced me that I ought to do some more. So I did the following:</p>"
                + "<ol>"
                + "<li> Set up <a href=\"http://reader.google.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://reader.google.com']);\"><span class=\"zem_slink rdfa\">Google</span> <span class=\"zem_slink rdfa\">reader</span></a> so I can see all the web changes in one place.</li>"
                + "<li>Built a profile on <a class=\"zem_slink freebase/en/naymz\" title=\"Naymz\" rel=\"homepage\" href=\"http://www.naymz.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.naymz.com']);\">Naymz</a> (<a href=\"http://www.naymz.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.naymz.com']);\">http://www.naymz.com</a>), unclear on exactly what this one does.</li>"
                + "<li>Ziki (<a href=\"http://www.ziki.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.ziki.com']);\">http://www.ziki.com</a>) &#8211; Signed up, but never got the validation email. This is supposed to be a job finding service.</li>"
                + "<li><a class=\"zem_slink rdfa\" title=\"Spokeo\" rel=\"stag:means homepage\" href=\"http://www.spokeo.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.spokeo.com']);\">Spokeo</a> (<a href=\"http://www.spokeo.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.spokeo.com']);\">http://www.spokeo.com</a>) &#8211; Signed up &#8211; not clear on what this site does beyond search for names.</li>"
                + "<li><a class=\"zem_slink\" title=\"Ziggs\" rel=\"homepage\" href=\"http://www.ziggs.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.ziggs.com']);\">Ziggs</a> (<a href=\"http://www.ziggs.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.ziggs.com']);\">http://www.ziggs.com</a>) &#8211; Signed up and built profile, this one looks interesting.</li>"
                + "</ol>"
                + "<p>Just signing up for these things takes time, getting them to be consistent seems like it will be a pain. It reminds me of posting your resume to all of the job search sites. Not too bad the first time, but then going back to update is going to be hard.</p>"
                + "<p>Next thing I did was to add <a class=\"zem_slink freebase/en/cross-link\" title=\"Cross-link\" rel=\"wikipedia\" href=\"http://en.wikipedia.org/wiki/Cross-link\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">cross links</a> from as many different places as I could to my <a class=\"zem_slink rdfa\" title=\"Website\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Website\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">web site</a> (http://www.accuweaver.com). This is supposed to help with the ranking on the <a class=\"zem_slink rdfa\" title=\"Web search engine\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Web_search_engine\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">search engines</a>, since the search engines use the assumption that if a lot of sites link to you, you must be important.</p>"
                + "<p>I also cleaned up my <a class=\"zem_slink freebase/en/linkedin\" title=\"LinkedIn\" rel=\"homepage\" href=\"http://www.linkedin.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.linkedin.com']);\">LinkedIn profile</a>, added links, and added my company to the Companies part of LinkedIn.Then after all of this, I got hit again with the suggestion that I should set up a <a class=\"zem_slink rdfa\" title=\"Facebook\" rel=\"stag:means homepage\" href=\"http://facebook.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://facebook.com']);\">Facebook</a> profile. Walt had mentioned it, but it took hearing it a few more times for me to act.  It still seems a bit smarmy, and unlikely to be useful as a <a class=\"zem_slink rdfa\" title=\"Business networking\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Business_networking\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">business networking</a> tool, but we&#8217;ll see.</p>"
                + "<p>Next: Making sure I&#8217;m posted on a huge list of sites I got from <a href=\"http://www.brightideasandconcepts.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.brightideasandconcepts.com']);\">Valerie Colber</a> &#8230;</p>]]></content:encoded>\n"
                + "<wp:post_id>6</wp:post_id>"
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
                + "<category domain=\"category\" nicename=\"marketing\"><![CDATA[marketing]]></category>"
                + "<category domain=\"category\" nicename=\"networking\"><![CDATA[networking]]></category>"
                + "<category domain=\"category\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>"
                + "<category domain=\"post_tag\" nicename=\"facebook-2\"><![CDATA[facebook-2]]></category>"
                + "<category domain=\"post_tag\" nicename=\"linkedin\"><![CDATA[linkedin]]></category>"
                + "<category domain=\"post_tag\" nicename=\"marketing\"><![CDATA[marketing]]></category>"
                + "<category domain=\"post_tag\" nicename=\"naymz\"><![CDATA[naymz]]></category>"
                + "<category domain=\"post_tag\" nicename=\"search\"><![CDATA[search]]></category>"
                + "<category domain=\"post_tag\" nicename=\"web-design-and-development\"><![CDATA[web-design-and-development]]></category>"
                + "<category domain=\"post_tag\" nicename=\"web-search-engine\"><![CDATA[web-search-engine]]></category>"
                + "<category domain=\"post_tag\" nicename=\"website\"><![CDATA[website]]></category>"
                + "<category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[recovered]]></category>"
                + "</item>");

        // Second Post file name.
        expResults.add("\n<!-- '" + DIR2_WITHOUT_CHILDREN + "/index.html' -->\n");

        // Second post contents
        expResults.add("<item><title>Plaxo: the service I love/hate</title>"
                + "<content:encoded><![CDATA[\n"
                + "		<p>A couple of days back, I solved a problem I was having with <a class=\"zem_slink rdfa\" title=\"Plaxo\" rel=\"stag:means homepage\" href=\"http://plaxo.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://plaxo.com']);\">Plaxo</a>. For a few weeks, I was unable to connect to any of the Plaxo <a class=\"zem_slink freebase/en/web_server\" title=\"Web server\" rel=\"wikipedia\" href=\"http://en.wikipedia.org/wiki/Web_server\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">web servers</a> from any of my home machines.</p>"
                + "<p><span id=\"more-7\"></span></p>"
                + "<p>Being a fairly knowledgeable network person, I spent hours trying to diagnose the problem. I could get to all other web <a class=\"zem_slink rdfa\" title=\"Website\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Website\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">sites</a>, but not to anything in the plaxo.com domain. Worse, I could resolve, ping and <a class=\"zem_slink freebase/en/traceroute\" title=\"Traceroute\" rel=\"wikipedia\" href=\"http://en.wikipedia.org/wiki/Traceroute\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">traceroute</a> looked fine.</p>"
                + "<p>First I thought it might be something caused by Plaxo being bought by <a class=\"zem_slink rdfa\" title=\"Comcast\" rel=\"stag:means homepage\" href=\"http://comcast.com\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://comcast.com']);\">Comcast</a>. Comcast had just recently been in the news for blocking traffic to keep bandwidth available, so I figured it wasn&#8217;t inconceivable that somebody made a mistake in a <a class=\"zem_slink rdfa\" title=\"Firewall\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Firewall\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">firewall</a> somewhere that was blocking traffic between them and <a class=\"zem_slink rdfa\" title=\"AT&amp;T\" rel=\"stag:means homepage\" href=\"http://www.att.com/\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.att.com']);\">AT&amp;T</a>.</p>"
                + "<p>I sent an email to Plaxo to ask them if their site was up, and called AT&amp;T to see if we could diagnose the problem. AT&amp;T as usual was very nice (and annoying) and started me out with the normal insane steps:</p><ol>"
                + "<li><a class=\"zem_slink freebase/en/traversal_using_relay_nat\" title=\"Traversal Using Relay NAT\" rel=\"wikipedia\" href=\"http://en.wikipedia.org/wiki/Traversal_Using_Relay_NAT\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">Turn</a> off your firewall</li>"
                + "<li>Clear your cache</li>"
                + "<li>Turn off your router</li>"
                + "</ol>"
                + "<p>After getting past all the annoying stuff, I got to their level 2 support, and then to the <a class=\"zem_slink rdfa\" title=\"2Wire\" rel=\"stag:means homepage\" href=\"http://www.2Wire.com/\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.2Wire.com']);\">2Wire</a> support to see if they could find anything with my router that might be causing this. Naturally they found nothing, and everything looked OK.</p>"
                + "<p>So I escalated with Plaxo, calling them on the phone to see if there was anything they could do. There were emails and phone calls back in forth that never solved the problem:</p>"
                + "<ul>"
                + "<li>First call I was told that there was a problem with one of their servers, and that it would be working the next day (not).</li>"
                + "<li>Another call I was told they had found the problem in their <a class=\"zem_slink rdfa\" title=\"Web server\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Web_server\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">web server</a>, and it would be fixed shortly</li>"
                + "<li>I got numerous emails telling me to uninstall the Plaxo software and log in again, which of course didn&#8217;t work since I couldn&#8217;t even get to the web site.</li>"
                + "<li>I had numerous emails diagnosing the problem as a Mac issue, or a <a class=\"zem_slink rdfa\" title=\"Personal computer\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/Personal_computer\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">PC</a> issue, which again it wasn&#8217;t since it was happening on the Mac, <a class=\"zem_slink rdfa\" title=\"iPhone\" rel=\"stag:means homepage\" href=\"http://www.apple.com/iphone\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://www.apple.com']);\">iPhone</a> and PC (and the iPhone doesn&#8217;t even have a Plaxo client).</li></ul>"
                + "<p>Finally at some point, I got a support guy who told me that my <a class=\"zem_slink rdfa\" title=\"IP address\" rel=\"stag:means wikipedia\" href=\"http://en.wikipedia.org/wiki/IP_address\" onclick=\"javascript:_gaq.push(['_trackEvent','outbound-article','http://en.wikipedia.org']);\">IP address</a> was indeed blocked at their server. Now we&#8217;re getting somewhere. But no, it still doesn&#8217;t work.</p>"
                + "<p>Luckily for me this guy is good, so he tells me that there was an old version of the Plaxo client for Mac that their servers were detecting as a bot attack, so if I uninstall that everything should be golden. I do, and lo and behold I can get to Plaxo again &#8230;</p>"
                + "<p>So it appears that Plaxo can be incompatible with itself &#8230;</p>"
                + "<p>I wonder how many people are blocked with the same problem right now.</p>]]></content:encoded>\n"
                + "<wp:post_id>7</wp:post_id>"
                + "<wp:post_date>2008-11-21 19:22:42</wp:post_date>"
                + "<wp:post_date_gmt>2008-11-21 19:22:42</wp:post_date_gmt>"
                + "<wp:comment_status>open</wp:comment_status>"
                + "<wp:ping_status>open</wp:ping_status>"
                + "<wp:post_name>plaxo-the-service-i-lovehate</wp:post_name>"
                + "<wp:status>publish</wp:status>"
                + "<wp:post_parent>0</wp:post_parent>"
                + "<wp:menu_order>0</wp:menu_order>"
                + "<wp:post_type>post</wp:post_type>"
                + "<wp:post_password></wp:post_password>"
                + "<wp:is_sticky>0</wp:is_sticky>"
                + "<category domain=\"category\" nicename=\"web\"><![CDATA[web]]></category>"
                + "<category domain=\"category\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>"
                + "<category domain=\"post_tag\" nicename=\"att\"><![CDATA[att]]></category>"
                + "<category domain=\"post_tag\" nicename=\"comcast\"><![CDATA[comcast]]></category>"
                + "<category domain=\"post_tag\" nicename=\"facebook-2\"><![CDATA[facebook-2]]></category>"
                + "<category domain=\"post_tag\" nicename=\"google\"><![CDATA[google]]></category>"
                + "<category domain=\"post_tag\" nicename=\"iphone-2\"><![CDATA[iphone-2]]></category>"
                + "<category domain=\"post_tag\" nicename=\"linkedin\"><![CDATA[linkedin]]></category>"
                + "<category domain=\"post_tag\" nicename=\"microsoft-outlook\"><![CDATA[microsoft-outlook]]></category>"
                + "<category domain=\"post_tag\" nicename=\"plaxo\"><![CDATA[plaxo]]></category>"
                + "<category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[recovered]]></category>"
                + "</item>");

        // Closing tags for channel and rss
        expResults.add("    </channel>\n"
                + "</rss>");

        List<String> results = instance.writeXML(dirName);
        assertEquals(10, results.size());
        Iterator it = expResults.iterator();
        for (String result : results) {
            if (it.hasNext()) {
                String expResult = (String) it.next();
                assertEquals(expResult, result);
            } else {
                fail("Nothing to compare for '" + result + "' in results");
                continue;
            }
        }
    }

    /**
     * Test of getFiles method, of class ConvertHTMLPost.
     */
    @Test
    public void testGetFiles() throws Exception {
        System.out.println("getFiles");
        String dirName = "";
        ConvertHTMLPost instance = new ConvertHTMLPost();
        List<Path> expResult = new ArrayList<>();
        List<Path> result = instance.getFiles(dirName);
        assertEquals(expResult, result);
    }

    /**
     * Test of addFile method, of class ConvertHTMLPost.
     */
    @Test
    public void testAddFile() throws Exception {
        System.out.println("addFile");
        List<String> output = new ArrayList<>();
        ConvertHTMLPost instance = new ConvertHTMLPost();
        instance.addFile(INPUT_FILE, output);
    }
}
