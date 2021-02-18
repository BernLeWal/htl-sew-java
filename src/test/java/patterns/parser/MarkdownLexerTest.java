package patterns.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownLexerTest {

    private static List<MarkdownToken> lexer(String s) {
        MarkdownLexer lexer = new MarkdownLexer();
        lexer.open( new ByteArrayInputStream(s.getBytes()) );
        lexer.run();
        return lexer.getTokens();
    }

    @Test
    void testSingleToken_Text() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.T,"Hello World!") );
        var actual = lexer("Hello World!");
        assertEquals( expected, actual );
    }

    @Test
    void testSingleToken_CRLF() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.CRLF,"") );
        var actual = lexer("\n");
        assertEquals( expected, actual );
    }


    @Test
    void testSimpleTokens_H1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"#"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("# Heading") );
        assertEquals( expected, lexer("#Heading") );
    }

    @Test
    void testSimpleTokens_H2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"##"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("## Heading") );
        assertEquals( expected, lexer("##Heading") );
    }

    @Test
    void testSimpleTokens_H3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"###"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("### Heading") );
        assertEquals( expected, lexer("###Heading") );
    }

    @Test
    void testSimpleTokens_H4() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"####"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("#### Heading") );
        assertEquals( expected, lexer("####Heading") );
    }

    @Test
    void testSimpleTokens_H5() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"#####"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("##### Heading") );
        assertEquals( expected, lexer("#####Heading") );
    }

    @Test
    void testSimpleTokens_H6() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"######"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("###### Heading") );
        assertEquals( expected, lexer("######Heading") );
    }

}