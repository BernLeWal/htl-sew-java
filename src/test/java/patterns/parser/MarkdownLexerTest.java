package patterns.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownLexerTest {
    private static List<MarkdownToken> lexer(String s) {
        return new MarkdownLexer(false).tokenize( s );
    }

    @Test
    void test_Empty() {
        var expected = new ArrayList<MarkdownToken>();
        var actual = lexer("");
        assertEquals( expected, actual );
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
    void testSingleToken_Indent1() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.INDENT," ") );
        var actual = lexer(" ");
        assertEquals( expected, actual );
    }

    @Test
    void testSingleToken_Indent2() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.INDENT,"  ") );
        var actual = lexer("  ");
        assertEquals( expected, actual );
    }

    @Test
    void testSingleToken_IndentTab1() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.INDENT,"\t") );
        var actual = lexer("\t");
        assertEquals( expected, actual );
    }

    @Test
    void testSingleToken_IndentTab2() {
        var expected = Arrays.asList( new MarkdownToken(MarkdownTokenType.INDENT,"\t\t") );
        var actual = lexer("\t\t");
        assertEquals( expected, actual );
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
    void testSimpleTokens_NoLineBreak1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text "),
                new MarkdownToken(MarkdownTokenType.CRLF)
        );
        assertEquals( expected, lexer( "Text \n") );
    }

    @Test
    void testSimpleTokens_NoLineBreak2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text ")
        );
        assertEquals( expected, lexer( "Text ") );
    }

    @Test
    void testSimpleTokens_NoLineBreak3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "First  Second")
        );
        assertEquals( expected, lexer( "First  Second") );
    }

    @Test
    void testSimpleTokens_LineBreak1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "  ")
        );
        assertEquals( expected, lexer( "Text  \n") );
    }

    @Test
    void testSimpleTokens_LineBreak2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "   ")
        );
        assertEquals( expected, lexer( "Text   \n") );
    }

    @Test
    void testSimpleTokens_LineBreak3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "    ")
        );
        assertEquals( expected, lexer( "Text    \n") );
    }

    @Test
    void testSimpleTokens_LineBreak1b() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "  ")
        );
        assertEquals( expected, lexer( "Text  \r\n") );
    }

    @Test
    void testSimpleTokens_LineBreak2b() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "   ")
        );
        assertEquals( expected, lexer( "Text   \r\n") );
    }

    @Test
    void testSimpleTokens_LineBreak3b() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.BR, "    ")
        );
        assertEquals( expected, lexer( "Text    \r\n") );
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
    void testSimpleTokens_HtmlTagHR() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.HTML, "<hr>")
        );
        assertEquals( expected, lexer("<hr>") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Text"),
                new MarkdownToken(MarkdownTokenType.HTML, "<hr>")
        );
        assertEquals( expected, lexer("Text<hr>") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.HTML, "<hr>"),
                new MarkdownToken(MarkdownTokenType.T, "Text")
        );
        assertEquals( expected, lexer("<hr>Text") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "Front"),
                new MarkdownToken(MarkdownTokenType.HTML, "<hr>"),
                new MarkdownToken(MarkdownTokenType.T, "Back")
        );
        assertEquals( expected, lexer("Front<hr>Back") );
    }

    @Test
    void testSimpleTokens_HtmlTagBR() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.HTML, "<br/>")
        );
        assertEquals( expected, lexer("<br/>") );
    }

    @Test
    void testSimpleTokent_NoHtmlTag1() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "The result of 9<10 is true")
        );
        assertEquals( expected, lexer("The result of 9<10 is true") );
    }

    @Test
    void testSimpleTokent_NoHtmlTag2() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "The result of 10 < 11 is true"),
                new MarkdownToken(MarkdownTokenType.CRLF)
        );
        assertEquals( expected, lexer("The result of 10 < 11 is true\n") );
    }

    @Test
    void testSimpleTokent_NoHtmlTag3() {
        final String longText = "Lorem ipsum < dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, longText)
        );
        assertEquals( expected, lexer(longText) );
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
    void testSimpleTokens_UnnumberedListItem3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.UL,"+"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer("+ List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.UL,"+"),
                new MarkdownToken(MarkdownTokenType.T, "List Item") );
        assertEquals( expected, lexer(" + List Item") );
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

    @Test
    void testSimpleTokens_NoUnnumberedListItem3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.T, "+List Item") );
        assertEquals( expected, lexer("+List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem3() {
        var expected = Arrays.asList(
                new MarkdownToken(MarkdownTokenType.INDENT, " "),
                new MarkdownToken(MarkdownTokenType.T, "+List Item") );
        assertEquals( expected, lexer(" +List Item") );
    }

}