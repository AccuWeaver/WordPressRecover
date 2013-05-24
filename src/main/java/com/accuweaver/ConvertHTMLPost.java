package com.accuweaver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to recover WordPress blog posts by walking through a tree of files from
 * SuperCache
 *
 * @author robweaver
 */
public class ConvertHTMLPost {

    private static final Logger logger = Logger.getLogger(ConvertHTMLPost.class.getName());
    private final static String DIR_NAME = "/Users/robweaver/NetBeansProjects/WordPressRecover/target/test-classes/data/input/2008";
    private final static String OUTPUT_FILE_NAME = "output.xml";
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private final static String HEADING = "<!-- This is a WordPress eXtended RSS file generated by WordPress as an export of your site. -->\n"
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
            + "\n"
            + "    <channel>\n"
            + "        <title>AccuWeaver LLC</title>\n"
            + "        <link>http://accuweaver.com</link>\n"
            + "        <description>Just another WordPress site</description>\n"
            + "        <pubDate>Sun, 19 May 2013 22:38:22 +0000</pubDate>\n"
            + "        <language>en-US</language>\n"
            + "        <wp:wxr_version>1.2</wp:wxr_version>\n"
            + "        <wp:base_site_url>http://accuweaver.com</wp:base_site_url>\n"
            + "        <wp:base_blog_url>http://accuweaver.com</wp:base_blog_url>\n"
            + "\n"
            + "        <wp:author>\n"
            + "            <wp:author_id>1</wp:author_id>\n"
            + "            <wp:author_login>admin</wp:author_login>\n"
            + "            <wp:author_email>rob@accuweaver.com</wp:author_email>\n"
            + "            <wp:author_display_name><![CDATA[admin]]></wp:author_display_name>\n"
            + "            <wp:author_first_name><![CDATA[]]></wp:author_first_name>\n"
            + "            <wp:author_last_name><![CDATA[]]></wp:author_last_name>\n"
            + "        </wp:author>\n"
            + "\n"
            + "\n"
            + "        <generator>http://wordpress.org/?v=3.5.1</generator>\n";

    /**
     * Write the XML for this directory ...
     * 
     * @param dirName
     * @return
     * @throws IOException 
     */
    public List<String> writeXML(String dirName) throws IOException {
        logger.log(Level.INFO, "hasChildren: {0}", hasChildren(dirName));
        List<String> output = new ArrayList<>();
        output.add(XML_HEAD);
        output.add(HEADING);
        List<Path> paths = getBottomBranches(dirName);
        for (Path path : paths) {
            logger.log(Level.INFO, "Path ''{0}''", path.toString());
            addFile(path.toString() + "/index.html", output, output);
        }
        // Close the channel
        output.add("    </channel>\n"
                + "</rss>");
        return output;
    }
    private int postId = 21;
    private String url;
    private String postname;
    private String postDate;
    private String postTime;

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ConvertHTMLPost converter = new ConvertHTMLPost();

        List<String> output = converter.writeXML(DIR_NAME);


        converter.writeSmallTextFile(output, OUTPUT_FILE_NAME);
    }

    /**
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public List<Path> getBottomBranches(String dirName) throws IOException {
        List<Path> returnBottom = new ArrayList<>();
        if (dirName.contains(".svn")) {
            return returnBottom;
        }

        // Has children means we need to recurse
        if (hasChildren(dirName)) {
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(dirName));
            for (Path path : dirStream) {
                if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                    // Recurse to get the bottom branches of this folder
                    returnBottom.addAll(getBottomBranches(path.toString()));
                }
            }
        } else {
            // TODO: one more check - we need to verify that there are index.html files
            //       in the directory before we decide this is really a bottom
            if (hasIndexFile(dirName)) {
                logger.finer("Found an index.html file in '" + dirName + "'");
                returnBottom.add(FileSystems.getDefault().getPath(dirName));
            } else {
                logger.finer("No index.html file in '" + dirName + "'");
            }
        }

        // Return the list of paths that satisfy our logic ..
        return returnBottom;
    }

    /**
     * Check to see if the directory contains an index.html file ...
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public boolean hasIndexFile(String dirName) throws IOException {
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(dirName));
        for (Path path : dirStream) {
            if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                if (path.getFileName().endsWith("index.html")) {
                    logger.log(Level.FINE, "Directory ''{0}'' has index.html", dirName);
                    return true;
                }
            }
        }
        logger.log(Level.FINE, "Directory ''{0}'' has no index.html", dirName);
        return false;
    }

    /**
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public boolean hasChildren(String dirName) throws IOException {
        boolean foundSome = false;
        Path dir = FileSystems.getDefault().getPath(dirName);

        DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);

        for (Path path : dirStream) {
            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                foundSome = true;
                break;
            }
        }
        return foundSome;
    }

    private void addFile(String fileName, List<String> input, List<String> output) throws IOException {
        List<String> contents = this.readSmallTextFile(fileName);
        // This will be a loop once I figure out the tree structure ...
        this.addItem(contents, output);
    }

    private void addItem(List<String> input, List<String> output) throws IOException {
        // Open the item ...


        boolean articleLines = false;

        Pattern p = Pattern.compile("<.*href='(.*?)/'.*>");
        Pattern patternTime = Pattern.compile("<time.*datetime=\"(.*?)T(.*?)\\+");

        // Loop through the file
        for (String s : input) {

            if (s.contains("class=\"entry-meta")) {
                continue;
            }

            if (s.contains("canonical")) {
                Matcher m = p.matcher(s);
                m.find();
                this.setUrl(m.group(1));
                this.setPostname(this.getUrl().substring(this.getUrl().lastIndexOf("/") + 1));

                continue;
            }

            if (s.contains("class=\"entry-date")) {
                Matcher matchTime = patternTime.matcher(s);
                matchTime.find();
                //Date
                this.setPostDate(matchTime.group(1));
                // Time
                this.setPostTime(matchTime.group(2));
                continue;
            }

            // Look for the article
            if (s.contains("class=\"entry-title")) {
                output.add("<title>");
                String[] splitText = s.split(">");
                for (String title : splitText) {
                    if (title.contains("</h1")) {
                        output.add(title.substring(0, title.length() - 4));
                        break;
                    }
                }

                output.add("</title>");
                continue;
            }
            // Look for the entry-content
            if (s.contains("class=\"entry-content")) {
                articleLines = true;
                output.add("<content:encoded><![CDATA[[caption id=\"\" align=\"alignright\" width=\"239\"]\n");
                continue;
            }

            if (s.contains("footer") || s.contains("class=\"zemanta")) {
                break;
            }

            // Check if we're past entry content
            if (articleLines) {
                output.add(s);
                output.add("\n");
            }
        }

        // Done with the file write the ending
        output.add(getEndContent());

    }

    private String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPostIdString(postId++));
        sb.append(getPostDateString());
        sb.append(getPostDateString());
        sb.append(getPostDateGMTString());
        sb.append(getCommentStatusString());
        sb.append(getPingStatusString());
        sb.append(getPostNameString());
        sb.append(getFixedStatusStrings());
        return sb.toString();
    }

    private String getEndContent() {
        return "]]></content:encoded>\n";
    }

    private String getPostIdString(int postId) {
        return "            <wp:post_id>"
                + postId++
                + "</wp:post_id>\n";
    }

    private String getPostDateString() {
        return "            <wp:post_date>"
                + this.getPostDate()
                + " "
                + this.getPostTime()
                + "</wp:post_date>\n";
    }

    private String getPostDateGMTString() {
        return "            <wp:post_date_gmt>"
                + this.getPostDate()
                + " "
                + this.getPostTime()
                + "</wp:post_date_gmt>\n";
    }

    private String getCommentStatusString() {
        return "            <wp:comment_status>open</wp:comment_status>\n";
    }

    private String getPingStatusString() {
        return "            <wp:ping_status>open</wp:ping_status>\n";
    }

    private String getPostNameString() {
        return "            <wp:post_name>"
                + "test-name-"
                + this.getPostname()
                + "</wp:post_name>\n";
    }

    private String getFixedStatusStrings() {
        return "            <wp:status>publish</wp:status>\n"
                + "            <wp:post_parent>0</wp:post_parent>\n"
                + "            <wp:menu_order>0</wp:menu_order>\n"
                + "            <wp:post_type>post</wp:post_type>\n"
                + "            <wp:post_password></wp:post_password>\n"
                + "            <wp:is_sticky>0</wp:is_sticky>\n"
                + "            <category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>\n"
                + "            <category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>\n";
    }

    private String wrapItem(String item) {
        return "<item>"
                + item
                + "</item>";
    }

    /**
     * Read a text file ...
     *
     * @param aFileName
     * @return
     * @throws IOException
     */
    List<String> readSmallTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }

    /**
     * Write to a text file ...
     *
     * @param aLines
     * @param aFileName
     * @throws IOException
     */
    void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        Files.write(path, aLines, ENCODING);
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the post name
     *
     * @return the postname
     */
    public String getPostname() {
        return postname;
    }

    /**
     * Set the post name
     *
     * @param postname the postname to set
     */
    public void setPostname(String postname) {
        this.postname = postname;
    }

    /**
     * Get the post date
     *
     * @return the postDate
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * Set the post date
     *
     * @param postDate the postDate to set
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /**
     * Get the post time
     *
     * @return the postTime
     */
    public String getPostTime() {
        return postTime;
    }

    /**
     * Set the post time
     *
     * @param postTime the postTime to set
     */
    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    /**
     * Convenience method to get the full file system file name for testing
     *
     * @param fileName
     * @return Full path for file in the test folder ...
     */
    public static String getRelativeFileName(String fileName) {
        return ConvertHTMLPost.class.getClass().getResource(fileName).getFile();
    }
}
