package com.accuweaver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
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
    private final static String DIR_NAME = "/Users/robweaver/Downloads/Stuf";
    private final static String OUTPUT_FILE_NAME = "output.xml";
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    /**
     * The HEADING was pulled from an export file from WordPress 3.5.1 - should
     * probably be generated using the same schema that WP uses.
     */
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
            + "\n";
    /**
     * TODO: Make the channel and blog information configurable and/or pull that
     * from the page ...
     */
    private static final String CHANNEL_HEAD = "    <channel>\n"
            + "        <title>AccuWeaver LLC</title>\n"
            + "        <link>http://accuweaver.com</link>\n"
            + "        <description>Just another WordPress site</description>\n"
            + "        <pubDate>Sun, 19 May 2013 22:38:22 +0000</pubDate>\n"
            + "        <language>en-US</language>\n"
            + "        <wp:wxr_version>1.2</wp:wxr_version>\n"
            + "        <wp:base_site_url>http://accuweaver.com</wp:base_site_url>\n"
            + "        <wp:base_blog_url>http://accuweaver.com</wp:base_blog_url>\n"
            + "\n";
    /**
     * TODO: Same thing for the author - either should be an arg for the run, or
     * pulled from the pages ...
     */
    private static final String AUTHOR = "        <wp:author>\n"
            + "            <wp:author_id>1</wp:author_id>\n"
            + "            <wp:author_login>admin</wp:author_login>\n"
            + "            <wp:author_email>rob@accuweaver.com</wp:author_email>\n"
            + "            <wp:author_display_name><![CDATA[admin]]></wp:author_display_name>\n"
            + "            <wp:author_first_name><![CDATA[]]></wp:author_first_name>\n"
            + "            <wp:author_last_name><![CDATA[]]></wp:author_last_name>\n"
            + "        </wp:author>\n"
            + "\n";
    /**
     * TODO: probably should change this to be something that points to this
     * code as the generator ...
     */
    private static final String GENERATOR = "        <generator>http://wordpress.org/?v=3.5.1</generator>\n";

    /**
     * Write the XML for this directory ...
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    private List<String> writeXML(String dirName) throws IOException {
        logger.log(Level.INFO, "hasChildren: {0}", hasChildren(dirName));

        // Array for output
        List<String> output = new ArrayList<>();

        // Add the XML begin
        output.add(XML_HEAD);

        // Add the heading information
        output.add(HEADING);
        output.add(CHANNEL_HEAD);
        output.add(AUTHOR);
        output.add(GENERATOR);

        // Get the files in the top level directory ...
        List<Path> files = getFiles(dirName);

        for (Path file : files) {
            logger.log(Level.INFO, "File ''{0}''", file.toString());
            addFile(file.toString(), output);
        }

        // Get the collection of folders with index.html files in them ...
        List<Path> paths = getBottomBranches(dirName);

        // Loop through those folders ...
        for (Path path : paths) {
            logger.log(Level.INFO, "Path ''{0}''", path.toString());
            addFile(path.toString() + "/index.html", output);
        }

        // Close the channel
        output.add("    </channel>\n"
                + "</rss>");
        return output;
    }

    /**
     * Get the files that end with *.html from the named directory.
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    private List<Path> getFiles(String dirName) throws IOException {
        List<Path> paths = new ArrayList<Path>();
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.html");
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(dirName));
        for (Path path : dirStream) {
            Path name = path.getFileName();
            if (name != null && matcher.matches(name)) {
                if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                    // Add the file to the list ...
                    paths.add(path);
                }
            }
        }
        return paths;
    }
    // Starting post ID
    private int postId = 21;
    // Url data ...
    private String url;
    // Post name
    private String postname;
    // Post title
    private String postTitle;
    // Post date
    private String postDate;
    // Post time ...
    private String postTime;

    /**
     * Main method
     *
     * TODO: add Apache Commons CLI for argument parsing to allow for input of
     * directory and options ...
     *
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ConvertHTMLPost converter = new ConvertHTMLPost();

        // Search through all the index.html files in the folder ...
        List<String> output = converter.writeXML(DIR_NAME);

        converter.writeSmallTextFile(output, OUTPUT_FILE_NAME);
    }

    /**
     * Get the bottom branches for a particular directory.
     *
     * This method looks at a directory, and if it contains no sub-directories
     * AND it contains an index.html file, it returns as a bottom branch.
     *
     * If it has subdirectories, each one of them is also traversed recursively
     * in order to get to the list of all of the bottom-most folders that
     * contain an index.html
     *
     * TODO: add some logic to make sure the index.html really is for a post
     * most likely by reading it and looking for the "entry-title" class.
     *
     * @param dirName Directory to get the branches for
     * @return List of Path objects that are the bottom most branches of the
     * tree.
     * @throws IOException If there are errors such as directory not exists.
     */
    public List<Path> getBottomBranches(String dirName) throws IOException {
        List<Path> returnBottom = new ArrayList<>();

        // Bypass svn and/or git folders
        // maybe should just bypass all dot/hidden folders.
        if (dirName.contains(".svn") || dirName.contains(".git")) {
            return returnBottom;
        }

        // Has children means we need to recurse
        if (hasChildren(dirName)) {
            logger.log(Level.FINEST, "Traversing ''{0}''", dirName);
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(FileSystems.getDefault().getPath(dirName));

            // Loop through the directory contents and look for directories ..
            for (Path path : dirStream) {
                // On each one that is a directory, make the recursive call
                if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                    // Recurse to get the bottom branches of this folder
                    // and add the results to what we'll eventually return
                    returnBottom.addAll(getBottomBranches(path.toString()));
                }
            }
        } else {
            // TODO: probably need to add some logic to the hasIndexFile method
            //       that verifies not only it has an index.html, but that it 
            //       will be a post as expected. 
            if (hasIndexFile(dirName)) {
                logger.log(Level.FINER, "Found an index.html file in ''{0}''", dirName);
                returnBottom.add(FileSystems.getDefault().getPath(dirName));
            } else {
                logger.log(Level.FINER, "No index.html file in ''{0}''", dirName);
            }
        }

        // Return the list of paths that satisfy our logic ..
        return returnBottom;
    }

    /**
     * Check to see if the directory contains an index.html file ...
     *
     * TODO: add some logic to look at the index.html to make sure it is
     * actually for a post and not a page or other type of thing that we find in
     * the cache folders.
     *
     * @param dirName Directory to check
     * @return
     * @throws IOException
     */
    public boolean hasIndexFile(String dirName) {
        DirectoryStream<Path> dirStream;
        FileSystem fs = FileSystems.getDefault();
        Path dir;
        try {
            dir = fs.getPath(dirName);
        } catch (InvalidPathException ex) {
            Logger.getLogger(ConvertHTMLPost.class.getName()).log(Level.SEVERE, "Exception in hasIndexFile, returning false", ex);
            return false;
        }
        try {
            dirStream = Files.newDirectoryStream(dir);
        } catch (IOException ex) {
            Logger.getLogger(ConvertHTMLPost.class.getName()).log(Level.SEVERE, "Exception in hasIndexFile, returning false", ex);
            return false;
        }
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
     * Check if this folder has children ...
     *
     * @param dirName Directory name to check
     * @return true if the folder has any sub-directories
     * @throws IOException If there are any errors, like the directory doesn't
     * exist.
     */
    public boolean hasChildren(String dirName) {
        boolean foundSome = false;
        FileSystem fs = FileSystems.getDefault();
        Path dir;
        try {
            dir = fs.getPath(dirName);
        } catch (InvalidPathException ex) {
            Logger.getLogger(ConvertHTMLPost.class.getName()).log(Level.SEVERE, "Exception in hasChildren, returning false", ex);
            return false;
        }

        DirectoryStream<Path> dirStream;
        try {
            dirStream = Files.newDirectoryStream(dir);
        } catch (IOException ex) {
            Logger.getLogger(ConvertHTMLPost.class.getName()).log(Level.SEVERE, "Exception in hasChildren, returning false", ex);
            return false;
        }

        for (Path path : dirStream) {
            if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                foundSome = true;
                break;
            }
        }
        return foundSome;
    }

    /**
     * This method adds the contents of a file to the output ...
     *
     * TODO: refactor this method have a return value so it can be tested.
     *
     * @param fileName Name of the file to process
     * @param output The array to add the contents of the file to.
     * @throws IOException If there are any errors like file doesn't exist, etc.
     */
    private void addFile(String fileName, List<String> output) throws IOException {
        List<String> contents = this.readSmallTextFile(fileName);

        if (contents.size() > 0) {
            // This will be a loop once I figure out the tree structure ...
            output.add("\n<!-- '" + fileName + "' -->\n");
            output.addAll(addItem(contents));
        }
    }

    /**
     * Adds the values from the list of Strings to as contents of an item
     * element for the article being processed.
     *
     * @param input List containing the contents of an index.html file
     * @return List of strings representing the item
     * @throws IOException
     */
    private List<String> addItem(List<String> input) throws IOException {
        List<String> output = new ArrayList<>();
        output.add("<item>");
        boolean noTitle = true;
        boolean articleLines = false;


        Pattern p = Pattern.compile("<.*href='(.*?)/'.*>");
        Pattern patternTime = Pattern.compile("<time.*datetime=\"(.*?)T(.*?)\\+");

        // TODO: Optimize this code, and maybe add some other checks so that the
        //       other information can be pulled from the page.

        // Loop through the file
        for (String s : input) {

            /**
             * Begin article - this includes some meta data
             * 
             * <article id="post-17" class="post-17 
             *          post type-post 
             *          status-publish 
             *          format-standard 
             *          hentry 
             *          category-restful-web-services 
             *          category-web tag-data 
             *          tag-data-formats 
             *          tag-html 
             *          tag-markup-language 
             *          tag-tools 
             *          tag-uniform-resource-locator 
             *          tag-xhtml 
             *          tag-xml 
             *          content-single ">
             * 
             *  A "page" will look more like:
             * <article id="post-1958" 
             *          class="post-1958 
             *          page 
             *          type-page 
             *          status-publish 
             *          hentry 
             *          content-page">
             */
            if (s.contains("<article")){
                // If we don't see "type-post", then it's not a post
                if (!s.contains("type-post")){
                    break;
                }
                // TODO: this line also contains the tags, categories and post ID
            }
            
            // Skip if we are on meta data ...
            if (s.contains("class=\"entry-meta")) {
                continue;
            }

            // Found canonical - get the post name from here ...
            if (s.contains("canonical")) {
                if (s.contains("http://www.accuweaver.com/ai1ec_event")) {
                    break;
                }
                Matcher m = p.matcher(s);
                m.find();
                this.setUrl(m.group(1));
                this.setPostname(this.getUrl().substring(this.getUrl().lastIndexOf("/") + 1));

                continue;
            }

            // class of entry-date means we have the date and time of post
            if (s.contains("class=\"entry-date")) {
                Matcher matchTime = patternTime.matcher(s);
                matchTime.find();
                //Date
                this.setPostDate(matchTime.group(1));
                // Time
                this.setPostTime(matchTime.group(2));
                continue;
            }

            // Look for the article title
            if (s.contains("class=\"entry-title")) {
                String[] splitText = s.split(">");
                for (String title : splitText) {
                    if (title.contains("</h1")) {
                        setPostTitle(title.substring(0, title.length() - 4));
                        noTitle = false;
                        break;
                    }
                }

                // Add the title if we found one ...
                if (getPostTitle().length() > 0) {
                    output.add("<title>");
                    output.add(getPostTitle());
                    output.add("</title>");
                }

                continue;
            }

            // Look for the entry-content
            if (s.contains("class=\"entry-content")) {
                if (getPostTitle().length() < 1) {
                    break;
                }
                articleLines = true;
                output.add("<content:encoded><![CDATA[\n");
                continue;
            }

            // If we get to footer or zemanta, we are done, so stop ...
            if (s.contains("footer") || s.contains("class=\"zemanta")) {
                break;
            }

            // Check if we're past entry content
            if (articleLines) {
                // Remove the extra url that points to the wp.com servers in
                // order to make the images relative path'd
                // TODO: make this regex more generic in order to make it work
                //       for some other blog ...
                s = s.replaceAll("http://.*?/blog.accuweaver.com/__oneclick_uploads/", "/");
                output.add(s);
            }
        }

        // If we didn't find a title, then we don't really want to add it to
        // our output file.
        if (noTitle) {
            return new ArrayList();
        }

        // Done with the file write the ending
        output.add(getEndContent());
        output.add(getContent());
        output.add("</item>");
        return output;
    }

    /**
     * Get the content String ...
     *
     * @return
     */
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

    /**
     * Get the end content string
     *
     * @return
     */
    private String getEndContent() {
        return "]]></content:encoded>\n";
    }

    /**
     * Create the post ID string - this is sequentially generated ...
     *
     * TODO: Make the postId a command line argument and/or a setting to
     * actually pull this from the post's index.html
     *
     * @param postId
     * @return
     */
    private String getPostIdString(int postId) {
        return "            <wp:post_id>"
                + postId++
                + "</wp:post_id>\n";
    }

    /**
     * Get the post date part of the XML
     *
     * @return
     */
    private String getPostDateString() {
        return "            <wp:post_date>"
                + this.getPostDate()
                + " "
                + this.getPostTime()
                + "</wp:post_date>\n";
    }

    /**
     * Get the post date's GMT string
     *
     * TODO: figure out if we can make this an actual GMT instead of just using
     * the same date we have for the post date/time.
     *
     * @return
     */
    private String getPostDateGMTString() {
        return "            <wp:post_date_gmt>"
                + this.getPostDate()
                + " "
                + this.getPostTime()
                + "</wp:post_date_gmt>\n";
    }

    /**
     * Get the comment status - note this is hard coded right now to say that
     * comments are open.
     *
     * TODO: figure out if we can find out if comments are open from the cache's
     * index.html for this post.
     *
     * @return
     */
    private String getCommentStatusString() {
        return "            <wp:comment_status>open</wp:comment_status>\n";
    }

    /**
     * Get the ping back status
     *
     * TODO: figure out if we can get this value from the index.html file.
     *
     * @return
     */
    private String getPingStatusString() {
        return "            <wp:ping_status>open</wp:ping_status>\n";
    }

    /**
     * Creates the post name XML snippet
     *
     * @return
     */
    private String getPostNameString() {
        return "            <wp:post_name>"
                + this.getPostname()
                + "</wp:post_name>\n";
    }

    /**
     * Returns a string with all of the "fixed" fields
     *
     * TODO: go through these and see if there are fields that can be gleaned
     * from the index.html
     *
     * @return
     */
    private String getFixedStatusStrings() {
        return "            <wp:status>publish</wp:status>\n"
                + "            <wp:post_parent>0</wp:post_parent>\n"
                + "            <wp:menu_order>0</wp:menu_order>\n"
                + "            <wp:post_type>post</wp:post_type>\n"
                + "            <wp:post_password></wp:post_password>\n"
                + "            <wp:is_sticky>0</wp:is_sticky>\n"
                + "            <category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>\n";
    }

    /**
     * Simple method to wrap a string as an item XML snippet.
     *
     * @param item
     * @return
     */
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
        List<String> returnList = new ArrayList<String>();
        try {
            returnList = Files.readAllLines(path, ENCODING);
        } catch (MalformedInputException mie) {
        }
        return returnList;
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
     * @return the postTitle
     */
    public String getPostTitle() {
        return postTitle;
    }

    /**
     * @param postTitle the postTitle to set
     */
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
