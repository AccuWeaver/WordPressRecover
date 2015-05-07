# WordPressRecover

Code to recover WP blog entries from SuperCache

## In case you kill your WordPress content in the DB

Read the blog post for more specifics, but basically this code lets you build a WordPress site back up from the contents of
a SuperCache file folders.

Simple Java executable that reads a set of folders for blog posts and converts them into an XML format suitable for import
by the WordPress importer.

Currently the code can be run from the command line and the directory locations are hard-coded to the location on my local 
machine, so either you have to modify the DIR_NAME variable in the ConvertHTMLPost.java source file, or modify the main method
in that same file to take arguments (I have a TODO to add Apache Commons CLI in order to be able to set the properties of
the class from the command line).

Read more at http://www.accuweaver.com/2013/05/27/wordpres-recovery
