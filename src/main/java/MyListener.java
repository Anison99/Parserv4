import com.kenai.jffi.Function;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyListener extends SolidityBaseListener {
    private Web3j web3j;
    private String contractAddress; // adres smart kontraktu

    public MyListener(Web3j web3j, String contractAddress) {
        super();
        this.web3j = web3j;
        this.contractAddress = contractAddress;
    }

    public MyListener(Web3j web3) {
        super();
    }

    @Override
    public void enterFunctionCall(SolidityParser.FunctionCallContext ctx) {
        String functionName = ctx.identifier().getText();
        List<Expression> arguments = new ArrayList<>();

        if (ctx.expressionList() != null) {
            for (SolidityParser.ExpressionContext expressionContext : ctx.expressionList().expression()) {
                Expression argument = new Expression(expressionContext.getText());
                arguments.add(argument);
            }
        }

        Transaction transaction = Transaction.createFunctionCallTransaction(
                null, // nonce
                null, // gas price
                null, // gas limit
                contractAddress,
                FunctionEncoder.encode(new Function(functionName, arguments, Collections.emptyList())), // funkcja i jej argumenty
                null // wartość do przesłania
        );

        // reszta kodu metody
    }

    // reszta kodu klasy Listener
}
