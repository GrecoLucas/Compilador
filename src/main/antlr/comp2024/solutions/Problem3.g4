grammar Problem3;

@header {
    package pt.up.fe.comp2024;
}

// Define token for email 
EMAIL: [a-zA-Z0-9] ([a-zA-Z0-9] | '-' | '_')* '@' (([a-zA-Z0-9] | '-' | '_')+ '.')+ ('org'| 'com' | 'gov' | 'pt' | 'es');

// Define token for email list
EMAIL_LIST: EMAIL (WS* ',' WS* EMAIL)*;


// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

// If there is no grammar rule, ANTLR does not compile
// Adding this dummy rule
program: EOF;