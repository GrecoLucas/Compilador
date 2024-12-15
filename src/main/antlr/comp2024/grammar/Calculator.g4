grammar Calculator;

@header {
    package pt.up.fe.comp2024;
}

INTEGER : [0-9]+ ;
PLUS: '+' ;
MINUS: '-' ;
MULT: '*' ;
DIV: '/' ;

// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

// If there is no grammar rule, ANTLR does not compile
// Adding this dummy rule
program: EOF;
