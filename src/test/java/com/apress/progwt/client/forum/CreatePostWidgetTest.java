package com.apress.progwt.client.forum;

import junit.framework.TestCase;

public class CreatePostWidgetTest extends TestCase {

    public void testMakeReplyFromString() {

        CreatePostWidget cpw = new CreatePostWidget();

        assertEquals("&gt;asdfidfj d<br>", cpw
                .makeReplyFromString("asdfidfj d"));

        assertEquals(
                "&gt;aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkk<br>",
                cpw
                        .makeReplyFromString("aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiijjjjjkkkkk"));

        assertEquals(
                "&gt;aaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffggggaaaaabb<br>&gt;bbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccc<br>&gt;cdddddeeeeefffffgggg<br>",
                cpw
                        .makeReplyFromString("aaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffggggaaaaabbbbbcccccdddddeeeeefffffgggg"));
        assertEquals(
                "&gt;aaaaabb<sdf>bb</sdf>bcccccdddddeeeeefffffgggg<br>",
                cpw
                        .makeReplyFromString("aaaaabb<sdf>bb</sdf>bcccccdddddeeeeefffffgggg"));
        assertEquals(
                "&gt; instead of a bunch of methods that all work on the current selection how a<br>&gt;bout a getSelection method that returns a Range and then a series of method<br>&gt;s to operate on a Range. How does one programatically set the current selec<br>&gt;tion? @Sandy I started a RR about selections and ranges a while back.And in<br>&gt;stead of a bunch of methods that all work on the current selection how abou<br>&gt;t a getSelection method that returns a Range and then a series of methods t<br>&gt;o operate on a Range. How does one programatically set the current selectio<br>&gt;n? @Sandy I started a RR about selections and ranges a while back.And inste<br>&gt;ad of a bunch of methods that all work on the current selection how about a<br>&gt; getSelection method that returns a Range and then a series of methods to o<br>&gt;perate on a Range. How <br>",
                cpw
                        .makeReplyFromString(" instead of a bunch of methods that all work on the current selection how about a getSelection method that returns a Range and then a series of methods to operate on a Range. How does one programatically set the current selection? @Sandy I started a RR about selections and ranges a while back.And instead of a bunch of methods that all work on the current selection how about a getSelection method that returns a Range and then a series of methods to operate on a Range. How does one programatically set the current selection? @Sandy I started a RR about selections and ranges a while back.And instead of a bunch of methods that all work on the current selection how about a getSelection method that returns a Range and then a series of methods to operate on a Range. How "));

    }
}
