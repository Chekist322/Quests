package first;


/**
 * Created by Phoen on 02.02.2017.
 *
 */
class Arithmetic {



    static String Calculate(String operator, double value, double stackValue) {
        double result = 0;
        switch (operator) {
            case "+":
                result = value + stackValue;
                break;
            case "-":
                result = value - stackValue;
                break;
            case "/":
                result = value / stackValue;
                break;
            case "*":
                result = value * stackValue;
                break;
            case "=":
                return String.valueOf(value);
        }
        Calc.level1.add(Double.toString(value), Double.toString(stackValue), operator, Double.toString(result));
        return Double.toString(result);
    }
}
