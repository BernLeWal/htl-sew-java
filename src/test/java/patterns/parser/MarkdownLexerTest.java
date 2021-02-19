package patterns.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownLexerTest {
    private static List<MarkdownToken> lexer(String s) {
        MarkdownLexer lexer = new MarkdownLexer(false);
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
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.CRLF) );
        assertEquals( expected, lexer("\n") );
        assertEquals( expected, lexer("\r") );
        assertEquals( expected, lexer("\r\n") );
        assertEquals( expected, lexer("\n\r") );
    }

    @Test
    void testSimpleTokens_DoubleCRLF() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.CRLF),
                new MarkdownToken(MarkdownTokenType.CRLF) );
        assertEquals( expected, lexer("\n\n") );
        assertEquals( expected, lexer("\r\n\r\n") );
        assertEquals( expected, lexer("\n\r\n\r") );
        assertEquals( expected, lexer("\r\n\n") );
        assertEquals( expected, lexer("\n\r\n") );
        assertEquals( expected, lexer("\n\n\r") );
    }

    @Test
    void testSimpleTokens_TextCRLF() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.CRLF) );
        assertEquals( expected, lexer("Text\n") );
    }

    @Test
    void testSimpleTokens_DoubleTextCRLF() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "First"),
                new MarkdownToken(MarkdownTokenType.CRLF),
                new MarkdownToken(MarkdownTokenType.T, "Second")
        );
        assertEquals( expected, lexer("First\nSecond") );
    }

    @Test
    void testSimpleTokens_DoubleTextDoubleCRLF() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "First"),
                new MarkdownToken(MarkdownTokenType.CRLF),
                new MarkdownToken(MarkdownTokenType.T, "Second"),
                new MarkdownToken(MarkdownTokenType.CRLF)
        );
        assertEquals( expected, lexer("First\nSecond\n") );
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
    void testSimpleTokens_IndentH1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"#"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" # Heading") );
        assertEquals( expected, lexer(" #Heading") );
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
    void testSimpleTokens_IndentH2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"##"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" ## Heading") );
        assertEquals( expected, lexer(" ##Heading") );
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
    void testSimpleTokens_IndentH3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"###"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" ### Heading") );
        assertEquals( expected, lexer(" ###Heading") );
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
    void testSimpleTokens_IndentH4() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"####"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" #### Heading") );
        assertEquals( expected, lexer(" ####Heading") );
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
    void testSimpleTokens_IndentH5() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"#####"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" ##### Heading") );
        assertEquals( expected, lexer(" #####Heading") );
    }

    @Test
    void testSimpleTokens_H6() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.H,"######"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer("###### Heading") );
        assertEquals( expected, lexer("######Heading") );
    }

    @Test
    void testSimpleTokens_IndentH6() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.H,"######"),
                new MarkdownToken(MarkdownTokenType.T, "Heading") );
        assertEquals( expected, lexer(" ###### Heading") );
        assertEquals( expected, lexer(" ######Heading") );
    }

    @Test
    void testSimpleTokens_Quote1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.QUOTE,">"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(">Quote") );
        assertEquals( expected, lexer("> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.QUOTE,">"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(" >Quote") );
        assertEquals( expected, lexer(" > Quote") );
    }

    @Test
    void testSimpleTokens_Quote2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.QUOTE,">>"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(">>Quote") );
        assertEquals( expected, lexer(">> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.QUOTE,">>"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(" >>Quote") );
        assertEquals( expected, lexer(" >> Quote") );
    }

    @Test
    void testSimpleTokens_Quote3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.QUOTE,">>>"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(">>>Quote") );
        assertEquals( expected, lexer(">>> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.QUOTE,">>>"),
                new MarkdownToken(MarkdownTokenType.T, "Quote") );
        assertEquals( expected, lexer(" >>>Quote") );
        assertEquals( expected, lexer(" >>> Quote") );
    }

    @Test
    void testSimpleTokens_UnnumberedListItem1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.UL,"-"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer("- List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.UL,"-"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer(" - List Item") );
    }

    @Test
    void testSimpleTokens_UnnumberedListItem2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.UL,"*"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer("* List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.UL,"*"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer(" * List Item") );
    }

    @Test
    void testSimpleTokens_NoUnnumberedListItem1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "-List Item") );
        assertEquals( expected, lexer("-List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.T, "-List Item") );
        assertEquals( expected, lexer(" -List Item") );
    }

    @Test
    void testSimpleTokens_NoUnnumberedListItem2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.EM,"*"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer("*List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.EM,"*"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer(" *List Item") );
    }

}