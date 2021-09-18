package LexicalAnalyzer;

// Brian Bredahl

import java.util.Scanner;

public class Lexical {


//Variables
    int charClass;
    char[] lexeme = new char[100];
    char nextChar;
    int lexLen;
    int token;
    int nextToken;
    String currentInput;
    int currentSpot;
    int inputSize;
    int EOF;

    Scanner fileInput;

//Character Class
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int UNKNOWN = 99;

//Token Codes
    static final int INT_LIT = 10;
    static final int IDENT = 11;
    static final int ASSIGN_OP = 20;
    static final int ADD_OP = 21;
    static final int SUB_OP = 22;
    static final int MULT_OP = 23;
    static final int DIV_OP = 24;
    static final int LEFT_PAREN = 25;
    static final int RIGHT_PAREN = 26;

//Main

    public void main(String args[]){
        for(String currArg : args){
            currentInput = currArg;
            inputSize = currArg.length();
            EOF = currArg.charAt(inputSize);
            currentSpot = 0;
            if(currArg == null){
                System.out.println("ERROR - invalid input");
            }else{
                getChar();
                do{
                    lex();
                }while(nextToken != currentInput.charAt(inputSize));
            }
        }
    }

//lookup
    int lookup(char ch){
        addChar();
        switch  (ch) {
              case  '(':
                  nextToken = LEFT_PAREN;
                  break;
              case  ')':
                  nextToken = RIGHT_PAREN;
                  break;
              case  '+':
                  nextToken = ADD_OP;
                  break;
              case  '-':
                  nextToken = SUB_OP;
                  break;
              case  '*':
                  nextToken = MULT_OP;
                  break;
              case  '/':
                  nextToken = DIV_OP;
                  break;
              default:
                  nextToken = 0; // fix to set to end of file
                  break;
          }
        return  nextToken;
    }

//addChar
    void addChar(){
        if(lexLen <=98){
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }else{
            System.out.println("Error - lexeme is too long\n");
        }
    }



//getChar
    void getChar(){
        if((nextChar = currentInput.charAt(currentSpot)) == currentInput.charAt(inputSize)){
            if((nextChar >= 'A' && nextChar <='Z')||(nextChar >= 'a' && nextChar <='z')){
                charClass = LETTER;
            }else if(nextChar >= '0' && nextChar <= '9'){
                charClass = DIGIT;
            }else{
                charClass = UNKNOWN;
            }
        }else{
            charClass = EOF;
        }

    }

//getNonBlank
    void getNonBlank(){
        while(nextChar == ' '){
            getChar();
        }
    }

//lex
    int lex(){
        final int eof = EOF;
        lexLen = 0;
          getNonBlank();
          switch  (charClass) {
              /* Parse identifiers */
              case  LETTER:
                  addChar();
                  getChar();
                  while  (charClass == LETTER || charClass == DIGIT) {
                      addChar();
                      getChar();
                  }
                  nextToken = IDENT;
                  break;

              /* Parse integer literals */
              case  DIGIT:
                  addChar();
                  getChar();
                  while  (charClass == DIGIT) {
                      addChar();
                      getChar();
                  }
                  nextToken = INT_LIT;
                  break;

              /* Parentheses and operators */
              case  UNKNOWN:
                  lookup(nextChar);
                  getChar();
                  break;
          }
          if(charClass == EOF){
                  nextToken = EOF;
                  lexeme[0] = 'E';
                  lexeme[1] = 'O';
                  lexeme[2] = 'F';
                  lexeme[3] = 0;
          }
        return nextToken;
    }
}

