package first.cache;

/**
 * Created by Phoen on 03.02.2017.
 * Класс элемента кеша
 */
public class Element {
    String firstValue;
    String secondValue;
    String operator;
    public String result;

    public Element(String firstValue, String secondValue, String operator, String result){
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.operator = operator;
        this.result = result;
    }
    Element(Element element){
        firstValue = element.firstValue;
        secondValue = element.secondValue;
        operator = element.operator;
        result = element.result;
    }
}
