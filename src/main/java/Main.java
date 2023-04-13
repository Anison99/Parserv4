import org.antlr.v4.runtime.*;
import java.io.*;
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

        // parsowanie wprowadzonego fragmentu
        SolidityParser parser = new SolidityParser(new CommonTokenStream(new SolidityLexer(CharStreams.fromString(input))));
        SolidityParser.SourceUnitContext tree = parser.sourceUnit();

        // tworzenie instancji klasy dziedziczącej po SolidityBaseVisitor i przetwarzanie drzewa AST
        MySolidityVisitor visitor = new MySolidityVisitor();
        String code = (String) visitor.visit(tree);

        // zapisywanie kodu do pliku
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/GeneratedContract.sol"));
        writer.write(code);
        writer.close();

        // kompilacja kodu Solidity
        String[] command = {"solc", "--bin", "src/main/java/GeneratedContract.sol"};
        Process process = new ProcessBuilder(command).start();
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
