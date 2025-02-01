package pt.up.fe.comp2025;

import org.antlr.v4.runtime.*;
import org.junit.Test;
import pt.up.fe.specs.util.SpecsSystem;

import pt.up.fe.comp2025.solutions.*;

import java.util.function.Function;

public class Launcher {

    // EOF in ANTLR is of kind -1
    private static final int EOF = -1;

    @Test
    public void fullCalculatorExample() {

        // Example code
        var code = "1 - 1 + 3 * 10 / 2";

        // Convert code string into a character stream
        var input = new ANTLRInputStream(code);

        // Transform characters into tokens using the lexer
        var lex = new CalculatorLexer(input);

        // Get first token
        var token = lex.nextToken();

        // While token is not the last one (EOF), print token and get next one
        while (token.getType() != CalculatorLexer.EOF) {
            System.out.println(token);
            token = lex.nextToken();
        }

    }

    @Test
    public void calculator() {

        System.out.println("Testing calculator");

        // Will tokenize the expected elements
        printTokens("1 - 1 + 3 * 10 / 2", CalculatorLexer::new);
        /*
        [@-1,0:0='1',<1>,1:0]
        [@-1,2:2='-',<3>,1:2]
        [@-1,4:4='1',<1>,1:4]
        [@-1,6:6='+',<2>,1:6]
        [@-1,8:8='3',<1>,1:8]
        [@-1,10:10='*',<4>,1:10]
        [@-1,12:13='10',<1>,1:12]
        [@-1,15:15='/',<5>,1:15]
        [@-1,17:17='2',<1>,1:17]
         */


        // However, it is not imposing any order
        printTokens("+ + + 1  3 10", CalculatorLexer::new);
        /*
        [@-1,0:0='+',<2>,1:0]
        [@-1,2:2='+',<2>,1:2]
        [@-1,4:4='+',<2>,1:4]
        [@-1,6:6='1',<1>,1:6]
        [@-1,9:9='3',<1>,1:9]
        [@-1,11:12='10',<1>,1:11]
         */
    }

    @Test
    public void ipv4() /**problem1 */
    {

        System.out.println("Testing Problem1");

        // Will be interpreted as a single IP token
        printTokens("11.111.200.255", IPV4Lexer::new);
        /*
        [@-1,0:11='11.1.200.255',<1>,1:0]
         */

        // Will tokenize as "25" and "6", since 256 is not a valid token
        printTokens("256", IPV4Lexer::new);
        /*
        [@-1,0:1='25',<2>,1:0]
        [@-1,2:2='6',<2>,1:2]
         */

    }

    /**
     * DOCUMENTATION
     */
    @Test
    public void ipv4_relaxed() /**problem1 relaxed version */
    {

        System.out.println("Testing Problem1 Relaxed");

        // Example code
        var code = "10.1.2.255";

        // Convert code string into a character stream
        var input = new ANTLRInputStream(code);

        // Transform characters into tokens using the lexer
        var lex = new IPV4RelaxedLexer(input);

        // Get first token
        var token = lex.nextToken();

        // While token is not the last one (EOF), print token and get next one
        while (token.getType() != IPV4RelaxedLexer.EOF) {
            System.out.println(token);

            // If Integer
            if (token.getType() == IPV4RelaxedLexer.INTEGER) {
                // Convert to Integer and check value
                var value = Integer.parseInt(token.getText());

                if (value < 0 || value > 255) {
                    throw new RuntimeException("Invalid IP number, is not between 0 and 255: " + value);
                }
            }

            token = lex.nextToken();
        }

        // Output
        /*
        [@-1,0:1='10',<2>,1:0]
        [@-1,2:2='.',<1>,1:2]
        [@-1,3:3='1',<2>,1:3]
        [@-1,4:4='.',<1>,1:4]
        [@-1,5:5='2',<2>,1:5]
        [@-1,6:6='.',<1>,1:6]
        [@-1,7:9='256',<2>,1:7]

        java.lang.RuntimeException: Invalid IP number, is not between 0 and 255: 256
         */
    }


    @Test
    public void problem2() {
        System.out.println("Testing problem2");

        printTokens("lw x10, -1(x32)", Problem2Lexer::new); //#OK
        printTokens("lw x1, 0(x2)", Problem2Lexer::new);//#OK
        printTokens("lw x10, 1(x32)", Problem2Lexer::new); //#OK

        printTokens("sw x10, 0(x2)", Problem2Lexer::new);//#OK
        printTokens("sw x32, -9(x2)", Problem2Lexer::new);//#OK

        printTokens("add x1, x2, x0", Problem2Lexer::new);//#OK
        printTokens("addi x1, x1, 1", Problem2Lexer::new);//#OK


        printTokens("lw x33, 0(x2)", Problem2Lexer::new);//#ERRO
        printTokens("sw 0(x3), x2", Problem2Lexer::new);//#ERRO
        printTokens("addi x1, x2, x0", Problem2Lexer::new);//#ERRO
        printTokens("add x1, x1, 1", Problem2Lexer::new);//#ERRO


    }

    @Test
    public void problem3() {

        System.out.println("Testing problem3");

        printTokens("janeDoe@gmail.com", Problem3Lexer::new); //#OK
        // [@-1,0:16='janeDoe@gmail.com',<5>,1:0]

        printTokens("jane_doe@gmail.com", Problem3Lexer::new); //#OK
        // [@-1,0:17='jane_doe@gmail.com',<5>,1:0]

        printTokens("jane-doe@gmail.com", Problem3Lexer::new); //#OK
        // [@-1,0:17='jane-doe@gmail.com',<5>,1:0]

        printTokens("janeDoe@gmail.com, johnDoe@gmail.com", Problem3Lexer::new); //#OK
        // [@-1,0:16='janeDoe@gmail.com',<5>,1:0]

        printTokens("up10999@fe.up.pt", Problem3Lexer::new); //#OK
        // [@-1,0:15='up10999@fe.up.pt',<5>,1:0]

        printTokens("janeDoe", Problem3Lexer::new); //#ERROR
        // line 1:0 token recognition error at: 'janeDoe'

        // cannot tokenize the "_" but tokenizes the rest
        printTokens("_janeDoe@gmail.com", Problem3Lexer::new); //#ERROR
        //line 1:0 token recognition error at: '_'
        //[@-1,1:17='janeDoe@gmail.com',<1>,1:1]
    }


    void printTokens(String code, Function<CharStream, Lexer> lexerProvider) {
        System.out.println("Tokenizing: '" + code + "'\n");
        // Convert code string into a character stream
        var input = new ANTLRInputStream(code);

        // Transform characters into tokens using the lexer
        var lex = lexerProvider.apply(input);

        var token = lex.nextToken();

        // -1 is EOF
        while (token.getType() != -1) {
            System.out.println(token);
            token = lex.nextToken();
        }

        System.out.println("\n");
    }

    void parse(String code, Function<CharStream, Lexer> lexerProvider, Function<TokenStream, Parser> parserProvider, String topRule) {
        var lex = lexerProvider.apply(new ANTLRInputStream(code));
        // Wrap lexer around a token stream
        var tokens = new CommonTokenStream(lex);

        // Transforms tokens into a parse tree
        var parser = parserProvider.apply(tokens);

        // Call parser
        SpecsSystem.invoke(parser, topRule);
    }

    /*
    public static void main(String[] args) {
         Setups console logging and other things
        //SpecsSystem.programStandardInit();

        // Will tokenize the expected elements
        var code = "1 - 1 + 3 * 10 / 2";

        // However, it is not imposing any order
        var code2 = "+ + + 1  3 10";


    }
    */


    @Test
    public void problem1WithGrammar() /**problem1 */
    {

        System.out.println("Testing Problem1 with grammar");

        // Will be interpreted as a single IP token
        printTokens("11.111.200.255", Problem1WithGrammarLexer::new);
        /*
        [@-1,0:11='11.1.200.255',<1>,1:0]
         */

        // Will tokenize as "25" and "6", since 256 is not a valid token
        printTokens("256", Problem1WithGrammarLexer::new);
        /*
        [@-1,0:1='25',<2>,1:0]
        [@-1,2:2='6',<2>,1:2]
         */

    }

    @Test
    public void problem2WithGrammar() {

        System.out.println("Testing problem2");

        parse("lw x10, -1(x32)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK
        parse("lw x1, 0(x2)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK
        parse("lw x10, 1(x32)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK

        parse("sw x10, 0(x2)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK
        parse("sw x32, -9(x2)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK

        parse("add x1, x2, x0", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK
        parse("addi x1, x1, 1", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#OK


        parse("lw x33, 0(x2)", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#ERRO
        parse("sw 0(x3), x2", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#ERRO
        parse("addi x1, x2, x0", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#ERRO
        parse("add x1, x1, 1", Problem2WithGrammarLexer::new, Problem2WithGrammarParser::new, "inst"); //#ERRO


    }


    @Test
    public void problem3WithGrammar() {

        System.out.println("Testing problem3");


        parse("janeDoe@gmail.com", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#OK
        // [@-1,0:16='janeDoe@gmail.com',<5>,1:0]

        parse("jane_doe@gmail.com", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#OK
        // [@-1,0:17='jane_doe@gmail.com',<5>,1:0]

        parse("jane-doe@gmail.com", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#OK
        // [@-1,0:17='jane-doe@gmail.com',<5>,1:0]

        parse("janeDoe@gmail.com, johnDoe@gmail.com", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#OK
        // [@-1,0:16='janeDoe@gmail.com',<5>,1:0]

        parse("up10999@fe.up.pt", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#OK
        // [@-1,0:15='up10999@fe.up.pt',<5>,1:0]

        parse("janeDoe", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#ERROR
        // line 1:0 token recognition error at: 'janeDoe'

        // cannot tokenize the "_" but tokenizes the rest
        parse("_janeDoe@gmail.com", Problem3WithGrammarLexer::new, Problem3WithGrammarParser::new, "emailList"); //#ERROR
        //line 1:0 token recognition error at: '_'
        //[@-1,1:17='janeDoe@gmail.com',<1>,1:1]
    }

}
