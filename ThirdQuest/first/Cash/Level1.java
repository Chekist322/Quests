package first.Cash;

import java.util.LinkedList;

/**
 * Created by Phoen on 03.02.2017.
 *Класс кеша первого уровня, содержит список из 10 последних операций и объект кеша второго уровня.
 */
public class Level1 {
    private LinkedList<Element> list;
    private Level2 level2 = new Level2();

    public Level1(){
        list = new LinkedList<>();
        level2 = new Level2();
    }


    public void add(String _firstValue,String _secondValue, String _operator, String _result) {
        Element tmp = new Element(_firstValue, _secondValue, _operator, _result);
        int size = 10;
        if (list.size()== size){
            level2.add(list.get(size -1));
            list.remove(size -1);
            list.addFirst(tmp);
        } else {
            list.addFirst(tmp);
        }
    }

    //Поиск операции в кеше
    public String search(String _firstValue,String _secondValue, String _operator){
        for (int i = 0; i < list.size(); i++) {
            if (_operator.equals("+") || _operator.equals("*")){
                if((list.get(i).firstValue.equals( _firstValue) && list.get(i).secondValue.equals(_secondValue)) ||
                        ((list.get(i).firstValue.equals( _secondValue) && list.get(i).secondValue.equals(_firstValue)))&&
                                list.get(i).operator.equals( _operator)) {
                    list.addFirst(list.get(i));
                    list.remove(i+1);
                    return list.getFirst().result;
                }
            }
            if (list.get(i).firstValue.equals( _firstValue) && list.get(i).secondValue.equals(_secondValue) && list.get(i).operator.equals( _operator)) {
                list.addFirst(list.get(i));
                list.remove(i+1);
                return list.getFirst().result;
            }
        }
        //Поиск операции в кеше второго уровня
        for (int i = 0; i < level2.list.size(); i++) {
            if (list.get(i).firstValue.equals( _firstValue) && list.get(i).secondValue.equals(_secondValue) && list.get(i).operator.equals( _operator)) {
                list.addFirst(level2.get(i));
                level2.list.remove(i+1);
                return level2.get(i).result;
            }
        }
        return "e";
    }

    public void print() {
        System.out.println("Level1");
        for (Element e : list) {
            System.out.print(e.firstValue);
            System.out.print(e.operator);
            System.out.print(e.secondValue + "=");
            System.out.println(e.result);
        }
        level2.print();
    }
}
