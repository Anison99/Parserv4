import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.Scanner;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    public static void main(String[] args) throws IOException {
        CharStream inputContract = CharStreams.fromFileName("src/main/java/MyContract.sol");
        SolidityLexer lexer = new SolidityLexer(inputContract);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolidityParser parserContract = new SolidityParser(tokens);

        // Dodanie ANTLRErrorListener
        parserContract.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                System.err.printf("Line %d:%d - %s\n", line, charPositionInLine, msg);
            }
        });

        // pobieranie fragmentu gramatyki Solidity z konsoli
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj fragment gramatyki Solidity: ");
        String input = scanner.nextLine();

        // parsowanie pliku Solidity
        SolidityParser parser = new SolidityParser(new CommonTokenStream(new SolidityLexer(CharStreams.fromString(input))));
        SolidityParser.SourceUnitContext tree = parser.sourceUnit();

    }
}
