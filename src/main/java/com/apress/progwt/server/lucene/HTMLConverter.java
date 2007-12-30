package com.apress.progwt.server.lucene;

import org.apache.log4j.Logger;
import org.compass.core.converter.ConversionException;
import org.compass.core.converter.basic.AbstractBasicConverter;
import org.compass.core.mapping.ResourcePropertyMapping;
import org.compass.core.marshall.MarshallingContext;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;

public class HTMLConverter extends AbstractBasicConverter {
    private static final Logger log = Logger
            .getLogger(HTMLConverter.class);

    // public Object fromString(String htmlString,
    // ResourcePropertyMapping arg1) throws ConversionException {
    //
    // StringReader reader = new StringReader(htmlString);
    // HTMLParser parser = new HTMLParser(reader);
    //
    // StringBuilder sb = new StringBuilder();
    // String thisLine;
    // try {
    // BufferedReader parsedReader = new BufferedReader(parser
    // .getReader());
    // while ((thisLine = parsedReader.readLine()) != null) { // while
    // // loop
    // // begins
    // // here
    // sb.append(thisLine);
    // } // end while
    //
    // return sb.toString();
    // } catch (IOException e) {
    // throw new ConversionException(e.getMessage());
    // }
    // }
    //
    // @Override
    // public String toString(Object o,
    // ResourcePropertyMapping resourcePropertyMapping) {
    // StringReader reader = new StringReader((String) o);
    // HTMLParser parser = new HTMLParser(reader);
    //
    // StringBuilder sb = new StringBuilder();
    // String thisLine;
    // try {
    // BufferedReader parsedReader = new BufferedReader(parser
    // .getReader());
    // while ((thisLine = parsedReader.readLine()) != null) { // while
    // // loop
    // // begins
    // // here
    // sb.append(thisLine);
    // } // end while
    //
    // return sb.toString();
    // } catch (IOException e) {
    // throw new ConversionException(e.getMessage());
    // }
    // }
    //
    @Override
    protected Object doFromString(String htmlString,
            ResourcePropertyMapping propertyMapping,
            MarshallingContext arg2) throws ConversionException {
        return fromString(htmlString, propertyMapping);
    }

    @Override
    public Object fromString(String str,
            ResourcePropertyMapping resourcePropertyMapping)
            throws ConversionException {
        return str;
    }

    @Override
    protected String toString(Object o,
            ResourcePropertyMapping resourcePropertyMapping,
            MarshallingContext context) {
        String str = (String) o;
        Lexer l = new Lexer(str);
        Parser parser = new Parser(l);
        StringBean sb = new StringBean();

        try {
            parser.visitAllNodesWith(sb);
        } catch (ParserException e) {
            log.warn("RETURNING ORIG VAL: " + str);
            return str;
        }
        String ret = sb.getStrings();
        log.debug("RETURNING STRIPPED: " + ret);
        return ret;
    }

}
