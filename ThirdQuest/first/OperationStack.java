package first;

/**
 * Created by Phoen on 02.02.2017.
 *
 */
class OperationStack {
    private static final int MAX_PRIORITY = Integer.MAX_VALUE;
    private static final int MIN_PRIORITY = Integer.MIN_VALUE;

    private class Element {
        String value;//число
        String operator;//операция
        Element next;//следующий элемент
        Element prev;//предыдущий элемент
        int priority;//приоритет

        Element() {
            this.value = null;
            this.operator = null;
            this.priority = 0;
            this.next = null;
            this.prev = null;
        }
    }

    //----------------------------------------------
    private Element head;//начало списка
    private Element tail;//конец списка


    //----------------------------------------------
    OperationStack() {
        head = null;
        tail = null;
    }

    void pushValue(String value) {
        if (head == null) {
            head = new Element();
            tail = head;
            head.value = value;
        } else {
            tail.value = value;
        }
        tail.next = new Element();

    }
    void pushOperator(String operator)
    {
        tail.operator = operator;
        if (operator.equals("*") | operator.equals("/")) {
            tail.priority = MAX_PRIORITY;
        }
        else{
            tail.priority = MIN_PRIORITY;
        }
    }

    void push(){
        if (head == null) {
            head = new Element();
            tail = head;
        }else {
            tail = tail.next;
        }
    }

    boolean isEmpty(){
        if (head == null){
            return true;
        }else {
            return false;
        }
    }



    String getResult() {
        Element current = head;
        String result;
        //флаг для обозначения, что мы уже прошлись по все длине выражения один раз
        boolean flag = false;
        while (true){
            if (current.priority == MAX_PRIORITY) {
                if (!Calc.level1.search(current.value, current.next.value, current.operator).equals("e")) {
                    result = Calc.level1.search(current.value, current.next.value, current.operator);
                    current.operator = current.next.operator;
                    current.value = result;
                    current.priority = current.next.priority;
                    current.next = current.next.next;
                } else{
                    result = Arithmetic.Calculate(current.operator, Double.valueOf(current.value), Double.valueOf(current.next.value));
                    current.operator = current.next.operator;
                    current.value = result;
                    current.priority = current.next.priority;
                    current.next = current.next.next;
            }
            }
            else if(current.operator.equals("=")){
                flag = true;
                current = head;
            }
            else{
                if (!flag){
                    current = current.next;
                }else{
                    if (!Calc.level1.search(current.value, current.next.value, current.operator).equals("e")) {
                        result = Calc.level1.search(current.value, current.next.value, current.operator);
                        current.operator = current.next.operator;
                        current.value = result;
                        current.priority = current.next.priority;
                        current.next = current.next.next;
                    } else{
                        result = Arithmetic.Calculate(current.operator, Double.valueOf(current.value), Double.valueOf(current.next.value));
                        current.operator = current.next.operator;
                        current.value = result;
                        current.priority = current.next.priority;
                        current.next = current.next.next;
                    }
                }
            }
            if (flag && current.operator.equals("=")){
                return current.value;
            }
        }

    }



    String getStack(){
        Element current = head;
        String stackContent = "";
        do{
            stackContent += String.valueOf(current.value);
            if (current.operator != null){
                stackContent += String.valueOf(current.operator);
            }
            if (current.next != null) {
                current = current.next;
            }
        } while (current.next != null);
        return stackContent;
    }

    void clear(){
        head=null;
        tail=null;
    }
}
