package pt.up.fe.comp2025;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.SpecsSystem;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Launcher {

    public static void main(String[] args) {
        // Setups console logging and other things
        SpecsSystem.programStandardInit();

        // Parse arguments as a map with predefined options
        var config = parseArgs(args);

        // Get input file
        File inputFile = new File(config.get("inputFile"));

        // Check if file exists
        if (!inputFile.isFile()) {
            throw new RuntimeException("Expected a path to an existing input file, got '" + inputFile + "'.");
        }

        // Read contents of input file
        String code = SpecsIo.read(inputFile);

        // Convert code string into a character stream
        var input = new ANTLRInputStream(code);
        // Transform characters into tokens using the lexer
        var lex = new JavammLexer(input);
        // Wrap lexer around a token stream
        var tokens = new CommonTokenStream(lex);
        // Transform tokens into a parse tree
        var parser = new JavammParser(tokens);
        ParseTree root = parser.program();


        //show AST in terminal
        System.out.println(root.toStringTree(parser));

        //show AST in GUI
        TreeViewer viewer = new TreeViewer(
                Arrays.asList(parser.getRuleNames()),
                root);
        viewer.open();
    }

    private static Map<String, String> parseArgs(String[] args) {
        SpecsLogs.info("Executing with args: " + Arrays.toString(args));

        // Check if there is at least one argument
        if (args.length != 1) {
            throw new RuntimeException("Expected a single argument, a path to an existing input file.");
        }

        // Create config
        Map<String, String> config = new HashMap<>();
        config.put("inputFile", args[0]);

        return config;
    }

}
