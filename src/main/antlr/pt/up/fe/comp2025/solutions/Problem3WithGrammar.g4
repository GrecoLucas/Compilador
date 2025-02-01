grammar Problem3WithGrammar;

@header {
    package pt.up.fe.comp2025.solutions;
}

// Define tokens for email
NAME: [a-zA-Z0-9] ([a-zA-Z0-9] | '-' | '_')* ;
AT: '@' ;
DOMAIN: (([a-zA-Z0-9] | '-' | '_')+ '.')+ ('org'| 'com' | 'gov' | 'pt' | 'es') ;

COMMA: ',' ;

// Ignore white space
WS : [ \t\n\r\f]+ -> skip ;

emailList: email (COMMA email)* ;

email: NAME AT DOMAIN;