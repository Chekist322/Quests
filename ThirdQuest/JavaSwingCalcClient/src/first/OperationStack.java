package first;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import first.cache.*;

/**
 * Created by Phoen on 02.02.2017.
 *
 */
class OperationStack implements Serializable {
    private static final int MAX_PRIORITY = Integer.MAX_VALUE;
    private static final int MIN_PRIORITY = Integer.MIN_VALUE;

    DataOutputStream output = Calc.output;
    DataInputStream input = Calc.input;

    private class stackElement {
        String value;//число
        String operator;//операция
        stackElement next;//следующий элемент
        stackElement prev;//предыдущий элемент
        int priority;//приоритет

        stackElement() {
            this.value = null;
            this.operator = null;
            this.priority = 0;
            this.next = null;
            this.prev = null;
        }
    }

    //----------------------------------------------
    private stackElement head;//начало списка
    private stackElement tail;//конец списка


    //----------------------------------------------
    OperationStack() {
        head = null;
        tail = null;
    }

    void pushValue(String value) {
        if (head == null) {
            head = new stackElement();
            tail = head;
            head.value = value;
        } else {
            tail.value = value;
        }
        tail.next = new stackElement();

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
            head = new stackElement();
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



    String getResult() throws IOException, ClassNotFoundException {

        stackElement current = head;


        String result;
        //флаг для обозначения, что мы уже прошлись по все длине выражения один раз
        boolean flag = false;
        while (true){
            if (current.priority == MAX_PRIORITY) {

                result = arithmetic(current);
                current.operator = current.next.operator;
                current.value = result;
                current.priority = current.next.priority;
                current.next = current.next.next;
            }
            else if(current.operator.equals("=")){
                flag = true;
                current = head;
            }
            else{
                if (!flag){
                    current = current.next;
                }else{
                    result = arithmetic(current);
                    current.operator = current.next.operator;
                    current.value = result;
                    current.priority = current.next.priority;
                    current.next = current.next.next;
                }
            }
            if (flag && current.operator.equals("=")){
                return current.value;
            }
        }

    }

    public String arithmetic(stackElement current) throws IOException, ClassNotFoundException {


        String result;
        if (!Calc.level1.search(current.value, current.next.value, current.operator).equals("e")) {
            result = Calc.level1.search(current.value, current.next.value, current.operator);
        } else{
            output.writeBoolean(true);
            output.writeUTF(current.operator);
            output.writeDouble(Double.parseDouble(current.value));
            output.writeDouble(Double.parseDouble(current.next.value));

            result = input.readUTF();
            Calc.level1.add(new Element(current.value, current.next.value, current.operator, result));
        }

        return result;
    }



    String getStack(){
        stackElement current = head;
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
