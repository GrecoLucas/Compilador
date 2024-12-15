grammar Problem2;

@header {
    package pt.up.fe.comp2024;
}

// Define tokens for instructions, registers, and immediate values
INT         : [1-9] [0-9]*;
ZERO        : '0';
NEG         : '-';
COMMA       : ',';
LPAREN      : '(';
RPAREN      : ')';
COLON       : ':';

// Define a token that represents a register (x0 - x32)
REGISTER    : 'x'([0-9] | '1'[0-9] | '2'[0-9] | '3'[0-2]);

// Define tokens for instructions
LW          : 'lw' WS+ REGISTER COMMA WS+ (NEG? INT | ZERO) LPAREN REGISTER RPAREN;
SW          : 'sw' WS+ REGISTER COMMA WS+ (NEG? INT | ZERO) LPAREN REGISTER RPAREN;
ADD         : 'add' WS+ REGISTER COMMA WS+ REGISTER COMMA WS+ REGISTER;
ADDI        : 'addi' WS+ REGISTER COMMA WS+ REGISTER COMMA WS+ INT;


// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

// If there is no grammar rule, ANTLR does not compile
// Adding this dummy rule
program: EOF;

