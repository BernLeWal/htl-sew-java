package patterns.parser;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * MarkdownLexer
 * <p>
 * The Syntax of Markdown see: https://www.markdownguide.org/basic-syntax
 */
public class MarkdownLexer {
    private InputStream input;
    private ArrayList<MarkdownToken> tokens;

    public ArrayList<MarkdownToken> getTokens() {
        return tokens;
    }

    public void open(URI uri) throws IOException {
        input = uri.toURL().openStream();
    }

    public void open(InputStream input) {
        this.input = input;
    }

    public void run() {
        tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder currentText = new StringBuilder();
            boolean isNextCharAtLineStart = true;
            boolean isCharAtLineStart = false;
            boolean eof = false;
            while (!eof) {
                int i = reader.read();
                if (i < 0) {
                    eof = true;
                    break;
                }
                char character = (char) i;
                isCharAtLineStart = isNextCharAtLineStart;
                isNextCharAtLineStart = false;

                boolean checkCharacterAgain;
                do {
                    checkCharacterAgain = false;

                    if (character == '\r' || character == '\n') {
                        tryCreateTextToken(currentText);
                        peek(reader, '\n');
                        tokens.add(new MarkdownToken(MarkdownTokenType.CRLF));
                        isNextCharAtLineStart = true;
                    } else if (character == '#' && isCharAtLineStart) {
                        tryCreateTextToken(currentText);
                        StringBuilder heading = new StringBuilder();
                        do {
                            heading.append(character);
                            character = (char) reader.read();
                        } while (character == '#');
                        tokens.add(new MarkdownToken(MarkdownTokenType.H, heading.toString()));
                        checkCharacterAgain = (character != ' ');
                    } else if (character == ' ') {
                        StringBuilder space = new StringBuilder();
                        do {
                            space.append(character);
                            character = (char) reader.read();
                        } while (character == ' ');
                        if (isCharAtLineStart)
                            tokens.add(new MarkdownToken(MarkdownTokenType.INDENT, space.toString()));
                        else if (space.length() > 1) {
                            tryCreateTextToken(currentText);
                            tokens.add(new MarkdownToken(MarkdownTokenType.BR, space.toString()));
                        } else
                            currentText.append(' ');
                        checkCharacterAgain = true;
                    } else if (character == '<') {
                        tryCreateTextToken(currentText);
                        tokens.add(new MarkdownToken(MarkdownTokenType.LT));
                    } else if (character == '>') {
                        tryCreateTextToken(currentText);
                        tokens.add(new MarkdownToken(MarkdownTokenType.GT));
                    } else if (character == '*' || character == '_') {
                        tryCreateTextToken(currentText);
                        StringBuilder emphasis = new StringBuilder();
                        do {
                            emphasis.append(character);
                            character = (char) reader.read();
                        } while (character == '*' || character == '_');
                        tokens.add(new MarkdownToken(MarkdownTokenType.EM, emphasis.toString()));
                        checkCharacterAgain = true;
                    } else {
                        currentText.append(character);
                    }
                } while (checkCharacterAgain);
            }
            tryCreateTextToken(currentText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryCreateTextToken(StringBuilder currentText) {
        if (!currentText.isEmpty()) {
            tokens.add(new MarkdownToken(MarkdownTokenType.T, currentText.toString()));
            currentText.setLength(0);
        }
    }

    private boolean peek(BufferedReader reader, char c) throws IOException {
        reader.mark(1);
        char lineFeed = (char) reader.read();
        if (lineFeed != c) {
            reader.reset(); // equivalent for peek (in C#)
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("MarkdownLexer Demo");

        String filename = "/patterns/sample.md";
        System.out.println("Lexing file: " + filename);
        MarkdownLexer lexer = new MarkdownLexer();
        lexer.open(lexer.getClass().getResource(filename).toURI());
        lexer.run();
        System.out.println();

        System.out.println("Tokens: " + lexer.getTokens().size());
        lexer.getTokens().forEach(System.out::println);
    }

}
