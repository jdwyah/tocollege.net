package com.apress.progwt.client.domain.commands;

import junit.framework.TestCase;

import com.apress.progwt.client.domain.Application;
import com.apress.progwt.client.exception.SiteException;

public class SaveApplicationCommandTest extends TestCase {

    private static final String TITLE = "testtitle1";
    private static final String TEXT = "test text";
    private static final String XSS_STRING = "<b>f</b>oo<IMG SRC=javascript:alert('XSS')>";
    private static final String XSS_FIXED = "<b>f</b>oo<img src=\"#alert(\" />";
    private static final String XSS_ESCAPED = "&lt;b&gt;f&lt;/b&gt;oo&lt;IMG SRC=javascript:alert('XSS')&gt;";

    private static final String VALID_HTML = "sa<FONT style=\"BACKGROUND-COLOR: green\" color=yellow>dfs</FONT>df";
    private static final String VALID_HTML_C = "sa<font style=\"BACKGROUND-COLOR: green\" color=\"yellow\">dfs</font>df";

    public void testExecute() throws SiteException {

        Application a = new Application();
        a.setNotes(VALID_HTML);
        a.getPros().add(TITLE);

        SaveApplicationCommand command = new SaveApplicationCommand(a);

        MockCommandService commandService = new MockCommandService(
                command);

        assertNull(command.getToSave());

        command.execute(commandService);

        Application saved = command.getToSave();
        assertEquals(TITLE, saved.getPros().get(0));
        assertEquals(VALID_HTML_C, saved.getNotes());
        assertEquals(0, saved.getCons().size());

    }

    public void testExecuteXSS() throws SiteException {

        Application a = new Application();
        a.setNotes(XSS_STRING);
        a.getPros().add(XSS_STRING);

        SaveApplicationCommand command = new SaveApplicationCommand(a);

        MockCommandService commandService = new MockCommandService(
                command);

        assertNull(command.getToSave());

        command.execute(commandService);

        Application saved = command.getToSave();
        assertEquals(XSS_ESCAPED, saved.getPros().get(0));
        assertEquals(XSS_FIXED, saved.getNotes());
        assertEquals(0, saved.getCons().size());

    }

}
