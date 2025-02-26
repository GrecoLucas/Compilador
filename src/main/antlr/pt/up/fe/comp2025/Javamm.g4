grammar Javamm;

@header {
    package pt.up.fe.comp2025;
}


WS : [ \t\n\r\f]+ ;
REG : 'x'([0-9]| [1-2][0-9]| '3'[0-2]) ;
JMM : ([0-9] | [A-F])+ ;

program
    : (expression WS)+ EOF
    ;


expression
    : ('lw' | 'sw') WS REG ',' WS JMM'('REG')'
    | 'add' WS REG ',' WS REG ',' WS REG
    | 'addi' WS REG ',' WS REG ',' WS JMM
    ;

/*
statement
    : expression ';' #ExprStmt
    | var=ID '=' value=expression ';' #Assignment
    ;


// mudando a  ordem, muda a prioridade, tem como mudar a prioridade com parenteses, com os parenteses a prioridade é a mesma
expression
    : '(' expression ')' #Parentheses
    | expression op=('*' | '/') expression #BinaryOp
    | expression op=('+' | '-') expression #BinaryOp
    | expression op='<' expression #Comparison
    | expression op='&&' expression #LogicalAnd
    | value=INTEGER #Integer
    | value=ID #Identifier
    | value=IP #Identifier
    ;

*/