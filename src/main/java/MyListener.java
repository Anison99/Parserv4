import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.web3j.protocol.Web3j;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Arrays;

public class MyListener extends SolidityBaseListener {
    private int indentLevel;
    private SolidityParser parserContract;

    public MyListener(SolidityParser parserContract) {
        this.indentLevel = 0;
        this.parserContract = parserContract;
    }

    public MyListener(Web3j web3, String toString) {
    }

    @Override
    public void enterFunctionCall(SolidityParser.FunctionCallContext ctx) {
        String functionName = "";
        if (ctx.identifier() != null) {
            functionName = ctx.identifier().getText();
        }
        System.out.println("Function call: " + functionName);
        drawTree(ctx);
    }

    private void drawTree(ParserRuleContext ctx) {
        JFrame frame = new JFrame("AST");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(parserContract.getRuleNames()), ctx);
        viewer.setScale(1.5); // Set the scale of the tree viewer
        viewer.open(); // Open the tree viewer in a separate window
    }

    @Override
    public void exitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        System.out.println("Wywołano funkcję " + ctx.identifier().getText());
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
