import org.antlr.v4.runtime.*;
import java.io.*;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

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

        // Inicjalizacja połączenia z węzłem Ethereum
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8545")); // Adres węzła Ethereum

        // Dołączenie klasy implementującej listener do parsera
        SolidityBaseListener listener = new SolidityBaseListener();
        ParseTreeWalker.DEFAULT.walk(listener, parserContract.sourceUnit());
    }
}
