/*
 * Copyright 2008 Jeff Dwyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.apress.progwt.server.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.compass.core.converter.ConversionException;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;

/**
 * NOTE: Unused. Created during some confusion over the role of compass
 * Converters vs Analyzers
 * 
 * 
 * @author Jeff Dwyer
 * 
 */
public class HTMLAnalyzer extends StandardAnalyzer {
    private static final Logger log = Logger
            .getLogger(HTMLAnalyzer.class);

    @Override
    public TokenStream tokenStream(String name, Reader reader) {

        if (log.isDebugEnabled()) {
            TokenStream ts = super.tokenStream(name,
                    htmlReaderFromReader(reader));
            Token t;
            if (log.isDebugEnabled()) {
                try {
                    while ((t = ts.next()) != null) {
                        log.debug("token: " + t);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return super.tokenStream(name, htmlReaderFromReader(reader));
    }

    private Reader htmlReaderFromReader(Reader reader)
            throws ConversionException {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(reader);

            String s;
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s);
            }

            Lexer l = new Lexer(stringBuilder.toString());
            Parser parser = new Parser(l);
            StringBean sb = new StringBean();

            parser.visitAllNodesWith(sb);

            String ret = sb.getStrings();
            return new StringReader(ret);
        } catch (ParserException e) {
            log.warn("Conversion Exception: " + e);
            throw new ConversionException(e.getMessage());
        } catch (IOException e2) {
            log.warn("Conversion Exception: " + e2);
            throw new ConversionException(e2.getMessage());
        }

    }
}
