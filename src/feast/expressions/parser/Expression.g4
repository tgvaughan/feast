grammar Expression;

// Parser rules:

expression :
        '(' expression ')'                                      # Bracketed
    |   '{' expression (',' expression)* '}'                    # Array
    |   expression '[' expression ']'                           # ArraySubscript
    |   expression ':' expression                               # Sequence
    |   op=(EXP|LOG|SQRT|SUM|THETA|ABS|MIN|MAX|LEN|SORT) '(' expression ')'      # UnaryOp
    |   expression '!'                                          # Factorial
    |<assoc=right> expression '^' expression                    # Exponentiation
    |   '-' expression                                          # Negation
    |   expression op=('*'|'/'|'%') expression                  # MulDiv
    |   expression op=('+'|'-') expression                      # AddSub
    |   expression op=('=='|'!='|'<'|'>'|'<='|'>=') expression  # Equality
    |   expression op=('&&'|'||') expression                    # BooleanOp
    |<assoc=right>   expression '?' expression ':' expression   # IfThenElse
    |   IDENT                                                   # Variable
    |   val=('0' | NZINT | NNFLOAT )                            # Number
    ;

// Lexer rules:

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;
MOD : '%' ;
POW : '^' ;

EXP : 'exp' ;
LOG : 'log' ;
SQRT : 'sqrt' ;
SUM : 'sum' ;
THETA : 'theta' ;
ABS : 'abs' ;
MIN : 'min' ;
MAX : 'max' ;
LEN : 'len' ;
SORT : 'sort' ;

AND : '&&' ;
OR : '||' ;

EQ: '==';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';
NE: '!=';

ZERO : '0' ;
NZINT : NZD D* ;
NNFLOAT : ('0' | NZINT) ('.' D*) ([eE] '-'? D+)? ;
fragment D : [0-9] ;
fragment NZD : [1-9] ;

IDENT : [a-zA-Z_][a-zA-Z_0-9]* ;

COMMENT_SINGLELINE: '//' .*? '\n' -> skip ;
COMMENT_MULTILINE: '/*' .*? '*/' -> skip ;

WHITESPACE : [ \t\r\n]+ -> skip ;