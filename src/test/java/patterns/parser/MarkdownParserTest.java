package patterns.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import patterns.composite.MdDoc;
import patterns.composite.MdHeading;
import patterns.composite.MdParagraph;

public class MarkdownParserTest {
    @Test
    void test_Empty() {
        var expected = new MdDoc(null);
        var actual = MdDoc.parse("");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleParagraph() {
        var expected = new MdDoc(null);
        expected.addChild( new MdParagraph("A single paragraph with text") );
        var actual = MdDoc.parse("A single paragraph with text");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleHeading() {
        var expected = new MdDoc(null);
        expected.addChild( new MdHeading(1, "Heading 1") );
        var actual = MdDoc.parse("# Heading 1");
        assertEquals( expected, actual );
    }
}
