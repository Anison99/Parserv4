import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.antlr.v4.runtime.*;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyListener extends SolidityBaseListener {
    private Web3j web3j;
    private String contractAddress; // adres smart kontraktu
    private int indentLevel;
    private SolidityParser parserContract;
    private ParserRuleContext tree;
    public MyListener(Web3j web3j, String contractAddress, SolidityParser parserContract) {
        this.web3j = web3j;
        this.contractAddress = contractAddress;
        this.indentLevel = 0;
        this.parserContract = parserContract;
    }

    @Override
    public void enterFunctionCall(SolidityParser.FunctionCallContext ctx) {
        String functionName = "";
        if (ctx.identifier() != null) {
            functionName = ctx.identifier().getText();
        }
        System.out.println("Function call: " + functionName);
        drawTree(ctx, parserContract);
    }

    private void drawTree(ParserRuleContext ctx, SolidityParser parser) {
        JFrame frame = new JFrame("AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(
                parser.getRuleNames()), ctx);
        viewer.setScale(1.5);   // Set the scale of the tree viewer
        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }


    @Override
    public void exitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        System.out.println("Wywołano funkcję " + ctx.identifier().getText() + " na adresie " + contractAddress);
    }

    @Override
    public void enterSourceUnit(SolidityParser.SourceUnitContext ctx) {
        System.out.println("Source Unit:");
        indentLevel++;
    }

    @Override
    public void exitSourceUnit(SolidityParser.SourceUnitContext ctx) {
        indentLevel--;
    }

    @Override
    public void exitContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        indentLevel--;
    }

    @Override
    public void exitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        indentLevel--;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        System.out.println(indent() + "Terminal: " + node.getText());
    }

    private String indent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }


}
