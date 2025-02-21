package pt.up.fe.comp2025;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;

public class JavaCalcGenerator extends AJmmVisitor<String, String> {
    private String className;

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

        // Add here other rules!
    }

    private String dealWithProgram(JmmNode jmmNode, String s) {
        s = (s != null ? s : "");
        String ret = s + "public class " + this.className + " {\n";
        String s2 = s + "\t";
        ret += s2 + "public static void main(String[] args) {\n";
        String s3 = s2 + "\t";
        for (JmmNode child : jmmNode.getChildren()) {
            ret += s3 + visit(child);
            ret += "\n";
        }
        ret += s2 + "}\n";
        ret += s + "}\n";
        return ret;
    }

    private String dealWithAssignment(JmmNode jmmNode, String s) {
        return "int " + jmmNode.get("var")
                + " = " + jmmNode.get("value")
                + ";";
    }

    private String dealWithLiteral(JmmNode jmmNode, String s) {
        return jmmNode.get("value");
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
}