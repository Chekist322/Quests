package first.Cash;

/**
 * Created by Phoen on 03.02.2017.
 * Класс элемента кеша
 */
class Element {
    String firstValue;
    String secondValue;
    String operator;
    String result;

    Element(String _firstValue,String _secondValue, String _operator, String _result){
        firstValue = _firstValue;
        secondValue = _secondValue;
        operator = _operator;
        result = _result;
    }
}
