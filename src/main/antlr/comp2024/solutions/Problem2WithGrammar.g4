grammar Problem2WithGrammar;

@header {
    package pt.up.fe.comp2024;
}

// Define tokens for instructions, registers, and immediate values
INT_12BIT   : '0' | '-2048' | ('-'? ([1-9] | [1-9][0-9] | [1-9][0-9][0-9] | '1'[0-9][0-9][0-9] | '20'[0-3][0-9] | '204'[0-7])); // Range -2048 to 2047
COMMA       : ',';
LPAREN      : '(';
RPAREN      : ')';
COLON       : ':';

// Define a token that represents a register (x0 - x32)
REGISTER    : 'x'([0-9] | '1'[0-9] | '2'[0-9] | '3'[0-2]);

// Define tokens for instructions
LW : 'lw';
SW : 'sw';
ADD : 'add';
ADDI : 'addi';

// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

inst: lw | sw | add | addi;

lw: LW type1;

sw: SW type1;

add: ADD type2;

addi: ADDI type2;

type1: REGISTER COMMA INT_12BIT LPAREN REGISTER RPAREN;

type2: REGISTER COMMA REGISTER COMMA INT_12BIT;