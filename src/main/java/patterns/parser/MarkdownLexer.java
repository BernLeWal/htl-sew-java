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
    // lexer configuration:
    private final boolean isCreateCRLFEofToken;

    // intermediate variables during lexing:
    private InputStream input;
    private ArrayList<MarkdownToken> tokens;

    // construction:
    public MarkdownLexer() {
        isCreateCRLFEofToken = true;
    }

    public MarkdownLexer(boolean isCreateCRLFEofToken) {
        this.isCreateCRLFEofToken = isCreateCRLFEofToken;
    }

    // getters:
    public ArrayList<MarkdownToken> getTokens() {
        return tokens;
    }

    // operations:
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
                char character = (char) reader.read();
                isCharAtLineStart = isNextCharAtLineStart;
                isNextCharAtLineStart = false;

                boolean checkCharacterAgain;
                do {
                    checkCharacterAgain = false;

                    if (character == (char) -1) {
                        eof = true;
                        if (isCreateCRLFEofToken)
                            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF, "EOF"));
                    } else if (isCharAtLineStart ) {
                        if (character == ' ') {
                            StringBuilder space = new StringBuilder();
                            do {
                                space.append(character);
                                character = (char) reader.read();
                            } while (character == ' ');
                            tokens.add(new MarkdownToken(MarkdownTokenType.INDENT, space.toString()));
                        }
                        if (character == '#') {
                            StringBuilder heading = new StringBuilder();
                            do {
                                heading.append(character);
                                character = (char) reader.read();
                            } while (character == '#');
                            tokens.add(new MarkdownToken(MarkdownTokenType.H, heading.toString()));
                            checkCharacterAgain = (character != ' ');
                        } else if (character == '-' || character == '*') {
                            if (tryReadNextChar(reader, ' ') )
                                tokens.add(new MarkdownToken(MarkdownTokenType.UL, character));
                            else
                                checkCharacterAgain = true;
                        } else if (character == '>') {
                            StringBuilder quote = new StringBuilder();
                            do {
                                quote.append(character);
                                character = (char) reader.read();
                            } while (character == '>');
                            tokens.add(new MarkdownToken(MarkdownTokenType.QUOTE, quote.toString()));
                            checkCharacterAgain = (character != ' ');
                        } else
                            checkCharacterAgain = true;
                    } else {
                        // !isCharAtLineStart
                        if (character == ' ') {
                            StringBuilder space = new StringBuilder();
                            do {
                                space.append(character);
                                character = (char) reader.read();
                            } while (character == ' ');
                            if (space.length() > 1) {
                                tryCreateTextToken(currentText);
                                tokens.add(new MarkdownToken(MarkdownTokenType.BR, space.toString()));
                            } else
                                currentText.append(' ');
                            checkCharacterAgain = true;
                        }
                        else
                            checkCharacterAgain = true;
                    }
                    //
                    if( checkCharacterAgain ) {
                        checkCharacterAgain = false;
                        if (character == '\r') {
                            tryCreateTextToken(currentText);
                            tryReadNextChar(reader, '\n');
                            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF));
                            isNextCharAtLineStart = true;
                        } else if (character == '\n') {
                            tryCreateTextToken(currentText);
                            tryReadNextChar(reader, '\r');
                            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF));
                            isNextCharAtLineStart = true;
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
                    }
                } while (checkCharacterAgain && !eof);
            }
            tryCreateTextToken(currentText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // helpers:
    private boolean tryCreateTextToken(StringBuilder currentText) {
        if (!currentText.isEmpty()) {
            tokens.add(new MarkdownToken(MarkdownTokenType.T, currentText.toString()));
            currentText.setLength(0);
            return true;
        }
        return false;
    }

    private boolean tryReadNextChar(BufferedReader reader, char c) throws IOException {
        reader.mark(1);
        char lineFeed = (char) reader.read();
        if (lineFeed != c) {
            reader.reset(); // equivalent for peek (in C#)
            return false;
        }
        return true;
    }


    // main application:
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
