package com.apress.progwt.server.util;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * This code is licensed under a Creative Commons Attribution-ShareAlike
 * 2.5 License http:// creativecommons.org/licenses/by-sa/2.5/
 * 
 * This code was modified to add more XSS tests by
 * 
 * @author Jeff Dwyer
 * 
 * Test cases from http://ha.ckers.org/xss.html
 * 
 * 
 * Original Follows:
 * 
 * HTML filtering utility for protecting against XSS (Cross Site
 * Scripting).
 * 
 * This code is licensed under a Creative Commons Attribution-ShareAlike
 * 2.5 License http:// creativecommons.org/licenses/by-sa/2.5/
 * 
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http:// code.iamcal.com/php/lib_filter/
 * 
 * The trickiest part of the translation was handling the differences in
 * regex handling between PHP and Java. These resources were helpful in
 * the process:
 * 
 * http:// java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http:// us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http:// www.regular-expressions.info/modifiers.html
 * 
 * A note on naming conventions: instance variables are prefixed with a
 * "v"; global constants are in all caps.
 * 
 * Sample use: String input = ... String clean = new
 * HTMLInputFilter().filter( input );
 * 
 * If you find bugs or have suggestions on improvement (especially
 * regarding perfomance), please contact me at the email below. The latest
 * version of this source can be found at
 * 
 * http:// josephoconnell.com/java/xss-html-filter/
 * 
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @version 1.0
 */
public class HTMLInputFilterTest extends TestCase {

    private static final boolean STRIP_COMMENTS = true;
    private static final boolean ALWAYS_MAKE_TAGS = true;

    protected HTMLInputFilter vFilter;

    protected void setUp() {
        vFilter = new HTMLInputFilter(true);
    }

    protected void tearDown() {
        vFilter = null;
    }

    private void t(String input, String result) {
        Assert.assertEquals(result, vFilter.filter(input));
    }

    public void test_basics() {
        t("", "");
        t("hello", "hello");
    }

    public void test_balancing_tags() {
        t("<b>hello", "<b>hello</b>");
        t("<b>hello", "<b>hello</b>");
        t("hello<b>", "hello");
        t("hello</b>", "hello");
        t("hello<b/>", "hello");
        t("<b><b><b>hello", "<b><b><b>hello</b></b></b>");
        t("</b><b>", "");
    }

    public void test_end_slashes() {
        t("<img>", "<img />");
        t("<img/>", "<img />");
        t("<b/></b>", "");
    }

    public void test_balancing_angle_brackets() {
        if (ALWAYS_MAKE_TAGS) {
            t("<img src=\"foo\"", "<img src=\"foo\" />");
            t("i>", "");
            t("<img src=\"foo\"/", "<img src=\"foo\" />");
            t(">", "");
            t("foo<b", "foo");
            t("b>foo", "<b>foo</b>");
            t("><b", "");
            t("b><", "");
            t("><b>", "");
        } else {
            t("<img src=\"foo\"", "&lt;img src=\"foo\"");
            t("b>", "b&gt;");
            t("<img src=\"foo\"/", "&lt;img src=\"foo\"/");
            t(">", "&gt;");
            t("foo<b", "foo&lt;b");
            t("b>foo", "b&gt;foo");
            t("><b", "&gt;&lt;b");
            t("b><", "b&gt;&lt;");
            t("><b>", "&gt;");
        }
    }

    public void test_attributes() {
        t("<img src=foo>", "<img src=\"foo\" />");
        t("<img asrc=foo>", "<img />");
        t("<img src=test test>", "<img src=\"test\" />");
    }

    public void test_disallow_script_tags() {
        t("<script>", "");
        if (ALWAYS_MAKE_TAGS) {
            t("<script", "");
        } else {
            t("<script", "&lt;script");
        }
        t("<script/>", "");
        t("</script>", "");
        t("<script woo=yay>", "");
        t("<script woo=\"yay\">", "");
        t("<script woo=\"yay>", "");
        t("<script woo=\"yay<b>", "");
        t("<script<script>>", "");
        t("<<script>script<script>>", "script");
        t("<<script><script>>", "");
        t("<<script>script>>", "");
        t("<<script<script>>", "");
    }

    /**
     * Tests from: http://ha.ckers.org/xss.html
     * 
     * 
     */
    private static final String VALID_HTML = "sa<FONT style=\"BACKGROUND-COLOR: green\" color=yellow>dfs</FONT>df";
    private static final String VALID_HTML_C = "sa<font style=\"BACKGROUND-COLOR: green\" color=\"yellow\">dfs</font>df";

    private static final String VALID_HTML_2 = "<P><STRONG>bold</STRONG></P>"
            + "<P>it<EM>ali</EM>c</P>"
            + "<P>un<U>der</U>line</P>"
            + "<P>s<SUB>u</SUB>b</P>"
            + "<P>su<SUP>pe</SUP>r</P>"
            + "<P>left</P>"
            + "<P align=center>center</P>"
            + "<P align=right>right</P>"
            + "<P><STRIKE>strike</STRIKE></P>"
            + "<BLOCKQUOTE dir=ltr style=\"MARGIN-RIGHT: 0px\">"
            + "<P>indent</P></BLOCKQUOTE>" + "<P>&nbsp;</P>";
    private static final String VALID_HTML_2_C = "<p><strong>bold</strong></p><p>it<em>ali</em>c</p><p>un<u>der</u>line</p><p>s<sub>u</sub>b</p><p>su<sup>pe</sup>r</p><p>left</p><p align=\"center\">center</p><p align=\"right\">right</p><p><strike>strike</strike></p><blockquote style=\"MARGIN-RIGHT: 0px\" dir=\"ltr\"><p>indent</p></blockquote><p>&nbsp;</p>";

    private static final String VALID_HTML_3 = "<HR id=null>"
            + "</P>"
            + "<OL>"
            + "<LI>one</LI>"
            + "<LI>two</LI>"
            + "<LI>three</LI></OL>"
            + "<UL>"
            + "<LI>aaaa</LI>"
            + "<LI>bbb</LI>"
            + "<LI>ccc</LI></UL>"
            + "<P>link to <A href=\"http://google.com\">google</A></P>"
            + "<P><FONT style=\"BACKGROUND-COLOR: yellow\">background</FONT></P>"
            + "<P><FONT color=red>foreground</FONT></P>"
            + "<P><FONT face=Arial>font</FONT></P>"
            + "<P><FONT size=6>size</FONT></P>"
            + "<P><FONT size=6><FONT color=green>m<FONT face=\"Courier New\"><U>an</U></FONT>y t<SUB>hing</SUB>s at</FONT><FONT color=yellow> <EM>the <FONT size=7>same</FONT></EM> time</FONT></FONT></P>"
            + "<P><FONT style=\"BACKGROUND-COLOR: #ffff00\"></FONT>&nbsp;</P>"
            + "<P><FONT style=\"BACKGROUND-COLOR: #ffff00\"></FONT>&nbsp;</P>"
            + "<P>&nbsp;</P>" + "<P>&nbsp;</P>";
    private static final String VALID_HTML_3_C = "<ol><li>one</li><li>two</li><li>three</li></ol><ul><li>aaaa</li><li>bbb</li><li>ccc</li></ul><p>link to <a href=\"http://google.com\">google</a></p><p><font style=\"BACKGROUND-COLOR: yellow\">background</font></p><p><font color=\"red\">foreground</font></p><p><font>font</font></p><p><font>size</font></p><p><font><font color=\"green\">m<font><u>an</u></font>y t<sub>hing</sub>s at</font><font color=\"yellow\"> <em>the <font>same</font></em> time</font></font></p><p><font style=\"BACKGROUND-COLOR: #ffff00\"></font>&nbsp;</p><p><font style=\"BACKGROUND-COLOR: #ffff00\"></font>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>";

    private void e(String input) {
        t(input, input);
    }

    /**
     * test the ToCollege.net extensions
     */
    public void testExtensions() {
        e("<p><strong>bold</strong></p>");
        e("<p>it<em>ali</em>c</p>");
        e("<p>un<u>der</u>line</p>");
        e("<p>s<sub>u</sub>b</p>");
        e("<p>su<sup>pe</sup>r</p>");
        e("<p>left</p>");
        e("<p align=\"center\">center</p>");
        e("<p align=\"right\">right</p>");
        e("<p><strike>strike</strike></p>");
        e("<blockquote>foo</blockquote>");
        e("<blockquote dir=\"ltr\" style=\"margin-right: 0px\">foo</blockquote>");
        e("<blockquote dir=\"ltr\" style=\"margin-right: 0px\">f<b>o</b>o</blockquote>");

        // e("&nbsp;");
        e("<p>&nbsp;</p>");
        t(
                "<BLOCKQUOTE dir=ltr style=\"MARGIN-RIGHT: 0px\"><P>indent</P></BLOCKQUOTE>"
                        + "<P>&nbsp;</P>",
                "<blockquote style=\"MARGIN-RIGHT: 0px\" dir=\"ltr\"><p>indent</p></blockquote><p>&nbsp;</p>");

        t(VALID_HTML, VALID_HTML_C);
        t(VALID_HTML_2, VALID_HTML_2_C);
        t(VALID_HTML_3, VALID_HTML_3_C);

    }

    /**
     * test our vulnerability to a number of the tricks at
     * http://ha.ckers.org/xss.html
     */
    public void testXSS() {

        nxxs("<SCRIPT SRC=http://ha.ckers.org/xss.js></SCRIPT>");
        nxxs("<IMG SRC=\"javascript:alert('XSS');\">");
        nxxs("<IMG SRC=javascript:alert('XSS')>");
        nxxs("<IMG SRC=JaVaScRiPt:alert('XSS')>");
        nxxs("<IMG SRC=javascript:alert(&quot;XSS&quot;)>");
        nxxs("<IMG SRC=`javascript:alert(\"RSnake says, 'XSS'\")`>");
        nxxs("<IMG \"\"\"><SCRIPT>alert(\"XSS\")</SCRIPT>\">");
        nxxs("<IMG SRC=javascript:alert(String.fromCharCode(88,83,83))>");
        nxxs("<IMG SRC=&#106;&#97;&#118;&#97;&#115;&#99;&#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101;&#114;&#116;&#40;&#39;&#88;&#83;&#83;&#39;&#41;>");
        nxxs("<IMG SRC=\"jav\tascript:alert('XSS');\">");

        t("<BASE HREF=\"javascript:alert('XSS');// \">", "");
        t(
                "<OBJECT TYPE=\"text/x-scriptlet\" DATA=\"http:// ha.ckers.org/scriptlet.html\"></OBJECT>",
                "");
        t("<!--[if gte IE 4]><SCRIPT>alert('XSS');</SCRIPT><![endif]-->",
                "");

        t("<TABLE BACKGROUND=\"javascript:alert('XSS')\">", "");
        t("<TABLE><TD BACKGROUND=\"javascript:alert('XSS')\">", "");
        t(
                "<DIV STYLE=\"background-image: url(javascript:alert('XSS'))\">",
                "");
        t(
                "<FRAMESET><FRAME SRC=\"javascript:alert('XSS');\"></FRAMESET>",
                "");
        t("<IFRAME SRC=\"javascript:alert('XSS');\"></IFRAME>", "");
        t(
                "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=data:text/html;base64,PHNjcmlwdD5hbGVydCgnWFNTJyk8L3NjcmlwdD4K\">",
                "");
        t(
                "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">",
                "");

        t("<IMG SRC='vbscript:msgbox(\"XSS\")'>",
                "<img src=\"#msgbox(\"XSS\")\" />");
        nxxs("<IMG SRC='vbscript:msgbox(\"XSS\")");

        t("<XSS STYLE=\"behavior: url(xss.htc);\">", "");
        t("<IMG LOWSRC=\"javascript:alert('XSS')\">", "<img />");
        t("<BGSOUND SRC=\"javascript:alert('XSS');\">", "");
        t("<BR SIZE=\"&{alert('XSS')}\">", "");
        t("<BODY ONLOAD=alert('XSS')>", "");

        t("<IMG SRC=\"hello\" onclick=\"\">", "<img src=\"hello\" />");

        t("<img src=\"http://eve.com/noRealLeakData?foo=foo\" />",
                "<img src=\"http://eve.com/noRealLeakData?foo=foo\" />");

    }

    /**
     * NOTE: a weak test, but if there are no j's at all it's not
     * terrible..
     * 
     * @param input
     */
    private void nxxs(String input) {
        String result = vFilter.filter(input);
        assertFalse(result.contains("java"));
        assertFalse(result.toLowerCase().contains("java"));
        assertFalse(result.toLowerCase().contains("j"));
    }

    public void test_protocols() {
        t("<a href=\"http://foo\">bar</a>",
                "<a href=\"http://foo\">bar</a>");
        // we don't allow ftp. t("<a href=\"ftp://foo\">bar</a>", "<a
        // href=\"ftp://foo\">bar</a>");
        t("<a href=\"mailto:foo\">bar</a>",
                "<a href=\"mailto:foo\">bar</a>");
        t("<a href=\"javascript:foo\">bar</a>",
                "<a href=\"#foo\">bar</a>");
        t("<a href=\"java script:foo\">bar</a>",
                "<a href=\"#foo\">bar</a>");
        t("<a href=\"java\tscript:foo\">bar</a>",
                "<a href=\"#foo\">bar</a>");
        t("<a href=\"java\nscript:foo\">bar</a>",
                "<a href=\"#foo\">bar</a>");
        t("<a href=\"java" + HTMLInputFilter.chr(1)
                + "script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
        t("<a href=\"jscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
        t("<a href=\"vbscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
        t("<a href=\"view-source:foo\">bar</a>",
                "<a href=\"#foo\">bar</a>");
    }

    public void test_self_closing_tags() {
        t("<img src=\"a\">", "<img src=\"a\" />");
        t("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
        t("</img>", "");
    }

    public void test_comments() {
        if (STRIP_COMMENTS) {
            t("<!-- a<b --->", "");
        } else {
            t("<!-- a<b --->", "<!-- a&lt;b --->");
        }
    }

}
