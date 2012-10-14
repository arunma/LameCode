/**
 * Only basic arithmetic operators are supported. However, could easily be
 * extended to support any binary operators
 *
 * This program is not an infix to postfix converter.  However, this program does the following
 *
 * 1) Evaluates a non-parenthesized infix expression and evaluates the final value
 * 2) Does not support parentheses
 *
 * @author Arun Manivannan
 */



public class InfixToPostfix1 {


    public static final String OPERATORS="+-/*"    //ignore the "(" as an operator
    public static final Map OPERATOR_LEVELS= ["*":1, "/":1, "+":2,"-":2]

    public static void main(String[] args) {

        String inputExpression="9-5+5*0+3"
        //String inputExpression="1+1+1+1+1+1+1+1+1+1-1+1+1+1+1+1*0"
        //String inputExpression="4*4+4*4+4-4*4"
        InfixToPostfix1 ifixToPfix=new InfixToPostfix1()
        ifixToPfix.infixToValue(inputExpression);
    }

    def infixToValue(String inputExpression) {
        ArrayDeque<String> expressionStack = new ArrayDeque<String>()
        ArrayDeque<Long> valueStack = new ArrayDeque<Long>() //to hold only non-decimal values

        String eachNumber="" //to hold multiple digit values. lame idea, i know, but can't think of anything else at 2 AM

        int totalCharInInput=inputExpression.length()

        inputExpression.each { eachChar->
            totalCharInInput--

            if (eachChar.trim()==""){
                //do nothing
            }
            else if (isValidOperator(eachChar)){


                valueStack.push(Long.parseLong(eachNumber)) //push the till now accumulated number to the value stack
                eachNumber=""


                if (expressionStack.isEmpty()){
                    expressionStack.push(eachChar)
                }
                //if the current operator has higher precedence than the first item in the stack, then it is fine.
                //Just insert the incoming
                else if (getHigherPrecedenceOperator(eachChar, expressionStack.peek())==eachChar){
                    expressionStack.push(eachChar)
                }

                //if the current operator is lesser, then the previous higher precedence expression have to be
                //evaluated. Else, we would be making wrong calculations while popping off for final evaluation
                else{

                    //the current operator has higher precedence. Evaluate expression
                    evaluateAndPushValueToValueStack(valueStack, expressionStack)

                    //after evaluation of the higher pair, don't forget to insert the current character into the expression stack
                    expressionStack.push(eachChar)

                }



            }
            else if (eachChar.isNumber()){
                eachNumber+=eachChar

                if (totalCharInInput==0){
                    valueStack.push(Long.parseLong(eachNumber)) //the last element
                }
            }

            /*else {
                //other input (alphabets and special chars) are treated as garbage
            }*/

        }

        while (!expressionStack.empty){
            evaluateAndPushValueToValueStack(valueStack,expressionStack)
        }
        println ("Final value : "+valueStack)
    }


    private void evaluateAndPushValueToValueStack(ArrayDeque<Long> valueStack, ArrayDeque<String> expressionStack) {

        Long firstOperand=valueStack.pop()
        Long secondOperand=valueStack.pop()
        println ("Value stack : "+firstOperand+ ":"+ secondOperand )
        Long evaluatedValue = this.evaluate(secondOperand, firstOperand, expressionStack.pop())  //intermediate result
        valueStack.push(evaluatedValue)
    }

    Long evaluate(Long firstOperand, Long secondOperand, String operator) {
        Long returnValue
        switch (operator) {
            case "+" :
                returnValue=firstOperand+secondOperand
                break
            case "-" :
                returnValue=firstOperand-secondOperand
                break;
            case "*":
                returnValue=firstOperand*secondOperand
                break
            case "/":

                if (secondOperand==0){      //cheating death by zero
                    returnValue=0
                }
                else{
                    returnValue=firstOperand/secondOperand
                }
                break;

        }

    }

    boolean isValidOperator(String input) {

        OPERATORS.contains(input)?true:false

    }

    String getHigherPrecedenceOperator(String firstOperator, String secondOperator){

        OPERATOR_LEVELS[firstOperator]<OPERATOR_LEVELS[secondOperator]?firstOperator:secondOperator

    }


}
