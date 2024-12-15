grammar IPV4;

@header {
    package pt.up.fe.comp2024;
}

IP: IP_NUMBER '.' IP_NUMBER '.' IP_NUMBER '.' IP_NUMBER ;

IP_NUMBER : [0-9] | [0-9] [0-9] | '1' [0-9] [0-9] | '2' [0-4] [0-9] | '2' '5' [0-5];

// If we define INTEGER before IP_NUMBER, single integers will be considered INTEGER tokens, instead of IP_NUMBER tokens
//INTEGER : [0-9] ;

// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

// If there is no grammar rule, ANTLR does not compile
// Adding this dummy rule
program: EOF;
