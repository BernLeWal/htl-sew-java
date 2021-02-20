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
    private boolean isCreateCRLFEofToken;

    // intermediate variables during lexing:
    private BufferedReader reader;
    private char currentChar;
    private boolean checkCurrentCharAgain;
    private StringBuilder currentText;
    private boolean isNextCharAtLineStart;
    private ArrayList<MarkdownToken> tokens;

    // construction:
    public MarkdownLexer() {
        isCreateCRLFEofToken = true;
    }

    public MarkdownLexer(boolean isCreateCRLFEofToken) {
        this.isCreateCRLFEofToken = isCreateCRLFEofToken;
    }

    // getters & setters:
    public boolean isCreateCRLFEofToken() {
        return isCreateCRLFEofToken;
    }

    public void setCreateCRLFEofToken(boolean createCRLFEofToken) {
        isCreateCRLFEofToken = createCRLFEofToken;
    }

    public ArrayList<MarkdownToken> getTokens() {
        return tokens;
    }

    // operations:
    public ArrayList<MarkdownToken> tokenize(InputStream input) {
        doTokenize(input);
        return tokens;
    }

    public ArrayList<MarkdownToken> tokenize(URI uri) throws IOException {
        return tokenize(uri.toURL().openStream());
    }

    public ArrayList<MarkdownToken> tokenize(String text) {
        return tokenize(new ByteArrayInputStream(text.getBytes()));
    }


    private void doTokenize(InputStream input) {
        tokens = new ArrayList<>();

        try (BufferedReader reader =new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8))){
            this.reader = reader;
            currentText = new StringBuilder();
            isNextCharAtLineStart = true;
            boolean isCharAtLineStart = false;
            boolean eof = false;
            while (!eof) {
                currentChar = readChar();
                isCharAtLineStart = isNextCharAtLineStart;
                isNextCharAtLineStart = false;

                do {
                    checkCurrentCharAgain = false;

                    if (currentChar == (char) -1) {
                        eof = true;
                        if (isCreateCRLFEofToken)
                            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF, "EOF"));
                    } else if (isCharAtLineStart) {
                        boolean tokenCreated = tryTokenIndent();    // ' '
                        tokenCreated |= tryToken('#', MarkdownTokenType.H);          // '#'
                        tokenCreated |= tryTokenUnnumberedList();   // '-', '*'
                        tokenCreated |= tryToken('>', MarkdownTokenType.QUOTE);            // '>'
                        if( !tokenCreated )
                            checkCurrentCharAgain = true;
                    } else {
                        // !isCharAtLineStart
                        boolean tokenCreated = tryTokenBreak();     // ' '
                        if (!tokenCreated)
                            checkCurrentCharAgain = true;
                    }
                    //
                    if (checkCurrentCharAgain) {
                        checkCurrentCharAgain = false;
                        boolean tokenCreated = tryTokenCRLF();      // '\n', '\r'
                        tokenCreated |= tryTokenHtmlTag();          // '<'...'>'
                        tokenCreated |= tryTokenEmphasis();         // '*', '_'
                        if (!tokenCreated)
                            currentText.append(currentChar);
                    }
                } while (checkCurrentCharAgain && !eof);
            }
            tryCreateTextToken(currentText);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private boolean tryCreateTextToken(StringBuilder currentText) {
        if (!currentText.isEmpty()) {
            tokens.add(new MarkdownToken(MarkdownTokenType.T, currentText.toString()));
            currentText.setLength(0);
            return true;
        }
        return false;
    }

    private boolean tryToken(char findChar, MarkdownTokenType tokenType) {
        if (currentChar == findChar) {
            StringBuilder heading = new StringBuilder();
            do {
                heading.append(currentChar);
                currentChar = readChar();
            } while (currentChar == findChar);
            tokens.add(new MarkdownToken(tokenType, heading.toString()));
            checkCurrentCharAgain = (currentChar != ' ');
            return true;
        }
        return false;
    }

    private boolean tryTokenCRLF() {
        if (currentChar == '\r') {
            tryCreateTextToken(currentText);
            tryReadNextChar('\n');
            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF));
            isNextCharAtLineStart = true;
            return true;
        } else if (currentChar == '\n') {
            tryCreateTextToken(currentText);
            tryReadNextChar('\r');
            tokens.add(new MarkdownToken(MarkdownTokenType.CRLF));
            isNextCharAtLineStart = true;
            return true;
        }
        return false;
    }

    private boolean tryTokenIndent() {
        if (currentChar == ' ' || currentChar == '\t') {
            StringBuilder space = new StringBuilder();
            do {
                space.append(currentChar);
                currentChar = readChar();
            } while (currentChar == ' ' || currentChar == '\t');
            tokens.add(new MarkdownToken(MarkdownTokenType.INDENT, space.toString()));
            return true;
        }
        return false;
    }

    private boolean tryTokenUnnumberedList() {
        if (currentChar == '-' || currentChar == '*') {
            if (tryReadNextChar(' '))
                tokens.add(new MarkdownToken(MarkdownTokenType.UL, currentChar));
            else
                checkCurrentCharAgain = true;
            return true;
        }
        return false;
    }

    private boolean tryTokenBreak() {
        if (currentChar == ' ') {
            StringBuilder space = new StringBuilder();
            do {
                space.append(currentChar);
                currentChar = readChar();
            } while (currentChar == ' ');
            if (space.length() > 1) {
                tryCreateTextToken(currentText);
                tokens.add(new MarkdownToken(MarkdownTokenType.BR, space.toString()));
            } else
                currentText.append(' ');
            checkCurrentCharAgain = true;
            return true;
        }
        return false;
    }

    private boolean tryTokenEmphasis() {
        if (currentChar == '*' || currentChar == '_') {
            tryCreateTextToken(currentText);
            StringBuilder emphasis = new StringBuilder();
            do {
                emphasis.append(currentChar);
                currentChar = readChar();
            } while (currentChar == '*' || currentChar == '_');
            tokens.add(new MarkdownToken(MarkdownTokenType.EM, emphasis.toString()));
            checkCurrentCharAgain = true;
            return true;
        }
        return false;
    }

    private boolean tryTokenHtmlTag() {
        if (currentChar == '<') {
            tryCreateTextToken(currentText);
            tokens.add(new MarkdownToken(MarkdownTokenType.LT));
            return true;
        } else if (currentChar == '>') {
            tryCreateTextToken(currentText);
            tokens.add(new MarkdownToken(MarkdownTokenType.GT));
            return true;
        } else
            return false;
    }


    // helpers:
    private char readChar() {
        try {
            return (char)reader.read();
        } catch (IOException e) {
            return (char)-1;
        }
    }

    private boolean tryReadNextChar(char c) {
        try {
            reader.mark(1);
            char lineFeed = readChar();
            if (lineFeed != c) {
                reader.reset(); // equivalent for peek (in C#)
                return false;
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }


    // main application:
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("MarkdownLexer Demo");

        String filename = "/patterns/sample.md";
        System.out.println("Lexing file: " + filename);
        MarkdownLexer lexer = new MarkdownLexer();
        lexer.tokenize(lexer.getClass().getResource(filename).toURI());
        System.out.println();

        System.out.println("Tokens: " + lexer.getTokens().size());
        lexer.getTokens().forEach(System.out::println);
    }

}
