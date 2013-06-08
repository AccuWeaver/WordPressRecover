package com.accuweaver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to represent and parse an article from the page ...
 *
 * @author rweaver
 */
public class Article {

    // Logging
    private static final Logger logger = Logger.getLogger(Article.class.getName());
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
    // Contents
    private String postContents;
    // Comment status - default to "open"
    private String commentStatus = "open";
    // Ping status - default to "open"
    private String pingStatus = "open";
    // Tags
    private List<String> tags;
    // Categories
    private List<String> categories;

    /**
     * Constructor ...
     *
     * @param fileContents - array of data to be parsed ...
     * @throws IOException
     */
    public Article(List<String> fileContents) throws IOException {
        this.postContents = readItem(fileContents);
    }

    /**
     * Default constructor
     */
    public Article() {
    }

    /**
     * Get the content String ...
     *
     * @return
     */
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPostIdString(postId++));
        sb.append(getPostDateString());
        sb.append(getPostDateGMTString());
        sb.append(getCommentStatusString());
        sb.append(getPingStatusString());
        sb.append(getPostNameString());
        sb.append(getPostMetaData());
        return sb.toString();
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

    /**
     * Get the end content string
     *
     * @return
     */
    public String getEndContent() {
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
    public String getPostIdString(int postId) {
        return "<wp:post_id>"
                + postId
                + "</wp:post_id>";
    }

    /**
     * Get the post date part of the XML
     *
     * @return
     */
    public String getPostDateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<wp:post_date>");
        if (this.getPostDate() != null) {
            sb.append(this.getPostDate());
            sb.append(" ");
            sb.append(this.getPostTime());
        }
        sb.append("</wp:post_date>");
        return sb.toString();
    }

    /**
     * Get the post date's GMT string
     *
     * TODO: figure out if we can make this an actual GMT instead of just using
     * the same date we have for the post date/time.
     *
     * @return
     */
    public String getPostDateGMTString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<wp:post_date_gmt>");
        if (this.getPostDate() != null) {
            sb.append(this.getPostDate());
            sb.append(" ");
            sb.append(this.getPostTime());
        }
        sb.append("</wp:post_date_gmt>");
        return sb.toString();
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
    public String getCommentStatusString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<wp:comment_status>");
        sb.append(getCommentStatus());
        sb.append("</wp:comment_status>");
        return sb.toString();
    }

    /**
     * Get the ping back status
     *
     * TODO: figure out if we can get this value from the index.html file.
     *
     * @return
     */
    public String getPingStatusString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<wp:ping_status>");
        sb.append(getPingStatus());
        sb.append("</wp:ping_status>");
        return sb.toString();
    }

    /**
     * Creates the post name XML snippet
     *
     * @return
     */
    public String getPostNameString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<wp:post_name>");
        sb.append(getPostname());
        sb.append("</wp:post_name>");
        return sb.toString();
    }

    /**
     * Returns a string with all of the "fixed" fields
     *
     * TODO: go through these and see if there are fields that can be gleaned
     * from the index.html
     *
     * @return
     */
    public String getPostMetaData() {

        StringBuilder sb = new StringBuilder();
        sb.append("<wp:status>publish</wp:status>");
        sb.append("<wp:post_parent>0</wp:post_parent>");
        sb.append("<wp:menu_order>0</wp:menu_order>");
        sb.append("<wp:post_type>post</wp:post_type>");
        sb.append("<wp:post_password></wp:post_password>");
        sb.append("<wp:is_sticky>0</wp:is_sticky>");
        if (categories != null) {
            sb.append(getPostMetaDataCategories());
        }
        sb.append("<category domain=\"category\" nicename=\"recovered\"><![CDATA[Recovered Post]]></category>");
        if (tags != null) {
            sb.append(getPostMetaDataTags());
        }
        sb.append("<category domain=\"post_tag\" nicename=\"recovered\"><![CDATA[recovered]]></category>");
        return sb.toString();
    }

    /**
     * Adds the values from the list of Strings to as contents of an item
     * element for the article being processed.
     *
     * @param input List containing the contents of an index.html file
     * @return List of strings representing the item
     * @throws IOException
     */
    private String readItem(List<String> input) throws IOException {
        return addItem(input);
    }

    /**
     * Adds the values from the list of Strings to as contents of an item
     * element for the article being processed.
     *
     * @param input List containing the contents of an index.html file
     * @return List of strings representing the item
     * @throws IOException
     */
    public String addItem(List<String> input) throws IOException {
        StringBuilder sb = new StringBuilder();

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
             * <article id="post-17" class="post-17 post type-post
             * status-publish format-standard hentry
             * category-restful-web-services category-web tag-data
             * tag-data-formats tag-html tag-markup-language tag-tools
             * tag-uniform-resource-locator tag-xhtml tag-xml content-single ">
             *
             * A "page" will look more like:
             * <article id="post-1958" class="post-1958 page type-page
             * status-publish hentry content-page">
             */
            if (s.contains("<article")) {
                // If we don't see "type-post", then it's not a post
                if (!s.contains("type-post")) {
                    break;
                }
                // Parse the tags from this line
                parseTags(s);
                // Parse out the post ID
                parsePostId(s);
                // Parse out the categories from this line
                parseCategories(s);

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
                if (!noTitle) {
                    sb.append("<title>");
                    sb.append(getPostTitle());
                    sb.append("</title>");
                }

                continue;
            }

            // Look for the entry-content
            if (s.contains("class=\"entry-content")) {
                if (getPostTitle().length() < 1) {
                    break;
                }
                articleLines = true;
                sb.append("<content:encoded><![CDATA[\n");
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
                sb.append(s);
            }
        }

        // If we didn't find a title, then we don't really want to add it to
        // our output file.
        if (noTitle) {
            return null;
        }

        // Done with the file write the ending
        sb.append(getEndContent());
        sb.append(getContent());

        return wrapItem(sb.toString());
    }

    /**
     * Simple method to wrap a string as an item XML snippet.
     *
     * @param item
     * @return
     */
    public String wrapItem(String item) {
        return "<item>"
                + item
                + "</item>";
    }

    /**
     * @return the postContents
     */
    public String getPostContents() {
        return postContents;
    }

    /**
     * @param postContents the postContents to set
     */
    public void setPostContents(String postContents) {
        this.postContents = postContents;
    }

    private String getCommentStatus() {
        return commentStatus;
    }

    private String getPingStatus() {
        return pingStatus;
    }

    /**
     * @return the tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Parse the tags from a line ...
     *
     * @param s
     * @return
     */
    public List<String> parseTags(String s) {
        if (s.contains("tag-")) {
            String[] firstTag = s.split(" tag-");
            // Remove the content after the tags ...
            firstTag[firstTag.length - 1] = firstTag[firstTag.length - 1].split(" content-")[0];
            // Copy everything but the first one ...
            firstTag = Arrays.copyOfRange(firstTag, 1, firstTag.length);
            // Initialize the array ...
            tags = new ArrayList<>(firstTag.length);
            tags.addAll(Arrays.asList(firstTag));
        }
        return tags;
    }

    /**
     *
     * @param s
     * @return
     */
    public int parsePostId(String s) {
        if (s.contains("id=\"")) {
            String[] postSplit = s.split("id=\"post-", 2);
            postSplit = postSplit[1].split("\"", 2);
            try {
                this.postId = Integer.parseInt(postSplit[0]);
            } catch (NumberFormatException nfe) {
                logger.log(Level.WARNING, s, nfe);
            }
        }
        return this.postId;
    }

    /**
     * Get the categories
     *
     * @return Array of Strings representing the categories ...
     *
     */
    List<String> getCategories() {
        return this.categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Parse out the categories from the line ..
     *
     * @param s String to parse the categories from ...
     */
    private void parseCategories(String s) {
        if (s.contains("category-")) {
            String[] firstCategory = s.split(" category-");
            // Remove the content after the categories ...
            firstCategory[firstCategory.length - 1] = firstCategory[firstCategory.length - 1].split(" tag-")[0];
            // Copy everything but the first one ...
            firstCategory = Arrays.copyOfRange(firstCategory, 1, firstCategory.length);
            // Initialize the array ...
            categories = new ArrayList<>(firstCategory.length);
            categories.addAll(Arrays.asList(firstCategory));
        }
    }

    /**
     * Format the categories into the appropriate meta data strings
     *
     * @return
     */
    private String getPostMetaDataCategories() {

        StringBuilder sb = new StringBuilder();
        for (String s : categories) {
            sb.append("<category domain=\"category\" nicename=\"");
            sb.append(s);
            sb.append("\"><![CDATA[");
            sb.append(s);
            sb.append("]]></category>");
        }
        return sb.toString();
    }

    /**
     * Return the formatted test strings ...
     * 
     * @return 
     */
    private Object getPostMetaDataTags() {
        StringBuilder sb = new StringBuilder();
        for (String s : tags) {
            sb.append("<category domain=\"post_tag\" nicename=\"");
            sb.append(s);
            sb.append("\"><![CDATA[");
            sb.append(s);
            sb.append("]]></category>");
        }
        return sb.toString();
    }
}
