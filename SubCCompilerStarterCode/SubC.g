======================================================================================
================================== LEXICAL GRAMMAR ===================================
======================================================================================
letter     -> [A-Za-z]
digit      -> [0-9]
identifier -> letter ( letter | digit )*
digits     -> digit+
number     -> digits(.digits)?
keyWords   -> void | int | double | boolean | if | else | while | return | break | continue | println | false | true
token      -> identifier | number | ; | ( | ) | { | } | = | * | / | + | - | == | != | < | <= | > | >= | , | keyWords | eot

separator  -> # graphic* eol | space | eol | tab
======================================================================================
================================= SYNTACTIC GRAMMAR ==================================
======================================================================================
Program -> (Type identifier (; | ( (Parameters | empty) ) { FunctionBody })* eot
Type -> void | int | double | boolean
Parameters -> Type identifier (empty | , Parameters)
FunctionBody -> (Type identifier ;)* Statements
Statements -> Statement*
Statement -> identifier (( (Arguments | empty) ) | = RHS) ; | if ( Expression ) { Statements } (empty | else { Statements }) | while ( Expression ) { Statements } | return Expression ; | return ; | break ; | continue ; | println ( identifier ) ;  
Arguments -> identifier (empty | , Arguments)
RHS -> ( Expression ) | identifier ( (Arguments | empty) ) 
Expression -> ( Expression ( + | - | * | / | == | != | > | >= | < | <= ) Expression ) | Value  
Value -> identifier | number | false | true
======================================================================================
=================================== AST STRUCTURE ====================================
======================================================================================
Program = Command*
Command = VariableDeclaration | FunctionDeclaration
VariableDeclaration = Type Identifier
FunctionDeclaration = Type Identifier VariableDeclaration* FunctionBody
FunctionBody = VariableDeclaration* Statement*
Statement = CallStatement | AssignStatement | IfStatement | WhileStatement | ReturnStatement | BreakStatement | ContinueStatement | PrintlnStatement
CallStatement = Identifier Identifier*
AssignStatement = Identifier RHS
IfElseStatement = Expression Statement* Statement*
WhileStatement = Expression Statement*
ReturnStatement = Expression
BreakStatement = 
ContinueStatement = 
PrintlnStatement = Identifier
RHS = ExpressionRHS | CallStatementRHS
ExpressionRHS = Expression
CallStatementRHS = CallStatement
Expression = BinaryExpression | UnaryExpression
BinaryExpression = Expression Operator Expression
UnaryExpression = IdentifierUnaryExpression | NumberUnaryExpression | BooleanUnaryExpression
IdentifierUnaryExpression = Identifier
NumberUnaryExpression = Number
BooleanUnaryExpression = Boolean
Terminal = Type | Identifier | Operator | Number | Boolean  
======================================================================================
======================================================================================
======================================================================================