grammar Javamm;

@header {
    package pt.up.fe.comp2025;
}

INTEGER : [0-9]+ ;
ID : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER : ([0-9] | [1-9][0-9] | '1'[0-9][0-9] | '2'[0-4][0-9] | '25'[0-5]) ;
IP : NUMBER '.' NUMBER '.' NUMBER '.' NUMBER;
WS : [ \t\n\r\f]+ -> skip ;



program
    : statement+ EOF
    ;

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
