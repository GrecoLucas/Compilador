package pt.up.fe.comp2025;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaCalcGenerator extends AJmmVisitor<String, String> {
    private String className;
    private final Map<String, Integer> readCounts = new HashMap<>();
    private final Map<String, Integer> writeCounts = new HashMap<>();

    public JavaCalcGenerator(String className) {
        this.className = className;
    }

    protected void buildVisitor() {
        addVisit("Program", this::dealWithProgram);
        addVisit("Assignment", this::dealWithAssignment);
        addVisit("Integer", this::dealWithLiteral);
        addVisit("Identifier", this::dealWithLiteral);
        addVisit("ExprStmt", this::dealWithExpression);
        addVisit("BinaryOp", this::dealWithBinaryOp);
        addVisit("IP", this::dealWithLiteral);
        addVisit("Parentheses", this::dealWithParentheses);
        addVisit("Comparison", this::dealWithComparison);
        addVisit("LogicalAnd", this::dealWithLogicalAnd);

        // Add here other rules!
    }

    private String dealWithProgram(JmmNode jmmNode, String s) {
        s = (s != null ? s : "");
        String ret = s + "public class " + this.className + " {\n";
        String s2 = s + "\t";
        ret += s2 + "public static void main(String[] args) {\n";
        String s3 = s2 + "\t";
        for (JmmNode child : jmmNode.getChildren()) {
            ret += visit(child, s2 + "\t");
            ret += "\n";
        }
        ret += s2 + "}\n";
        ret += s + "}\n";

        Set<String> keys = this.writeCounts.keySet();
        for (String key : keys) {
            ret += "'" + key + "': " + this.writeCounts.get(key).toString() + " writes and " + this.readCounts.get(key).toString() + " reads\n";
        }
        return ret;
    }

    private String dealWithAssignment(JmmNode jmmNode, String s) {
        this.incrementCounter("writes", jmmNode.get("var"));
        return s + "int " + jmmNode.get("var")
                + " = " + visit(jmmNode.getChildren().get(0), "") + ";";
    }

    private String dealWithLiteral(JmmNode jmmNode, String s) {
        this.incrementCounter("reads", jmmNode.get("value"));
        return s + jmmNode.get("value");

    }

    private String dealWithExpression(JmmNode jmmNode, String s) {
        String exprCode = visit(jmmNode.getChild(0));
        return "System.out.println(" + exprCode + ");";
    }

    private String dealWithBinaryOp(JmmNode jmmNode, String s) {
        String left = visit(jmmNode.getChild(0));
        String right = visit(jmmNode.getChild(1));
        String op = jmmNode.get("op");

        return left + " " + op + " " + right;
    }

    private String dealWithParentheses(JmmNode jmmNode, String s) {
        return "(" + visit(jmmNode.getChild(0)) + ")";
    }

    private String dealWithComparison(JmmNode jmmNode, String s) {
        String left = visit(jmmNode.getChild(0));
        String right = visit(jmmNode.getChild(1));
        return left + " < " + right;
    }

    private String dealWithLogicalAnd(JmmNode jmmNode, String s) {
        String left = visit(jmmNode.getChild(0));
        String right = visit(jmmNode.getChild(1));
        return left + " && " + right;
    }

    private void incrementCounter(String type, String key) {
        if (type.equals("writes")) {
            if (this.writeCounts.containsKey(key)) {
                this.writeCounts.put(key, this.writeCounts.get(key) + 1);
            } else {
                this.writeCounts.put(key, 1);
            }
        } else {
            if (this.readCounts.containsKey(key)) {
                this.readCounts.put(key, this.readCounts.get(key) + 1);
            } else {
                this.readCounts.put(key, 1);
            }
        }
    }
}