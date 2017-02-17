package first.cache;

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

    public void add(Element element){
        Element tmp = new Element(element);
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
    public String search(String firstValue,String secondValue, String operator){
        for (int i = 0; i < list.size(); i++) {
            if (operator.equals("+") || operator.equals("*")){
                if(comparePlusMulty(firstValue, secondValue, operator, i)) {
                    list.addFirst(list.get(i));
                    list.remove(i+1);
                    return list.getFirst().result;
                }
            }
            if (compare(firstValue, secondValue, operator, i)) {
                list.addFirst(list.get(i));
                list.remove(i+1);
                return list.getFirst().result;
            }
        }
        //Поиск операции в кеше второго уровня
        for (int i = 0; i < level2.list.size(); i++) {
            if (compareLevel2(firstValue, secondValue, operator, i)) {
                list.addFirst(level2.get(i));
                level2.list.remove(i+1);
                return level2.get(i).result;
            }
        }
        return "e";
    }

    private boolean compare(String firstValue,String secondValue, String operator, int i){
        if (list.get(i).firstValue.equals( firstValue) && list.get(i).secondValue.equals(secondValue) && list.get(i).operator.equals( operator)){
            return true;
        }else{
            return false;
        }
    }

    private boolean compareLevel2(String firstValue,String secondValue, String operator, int i) {
        if (level2.list.get(i).firstValue.equals( firstValue) && level2.list.get(i).secondValue.equals(secondValue) && level2.list.get(i).operator.equals( operator)){
            return true;
        }else{
            return false;
        }
    }

    private boolean comparePlusMulty(String firstValue,String secondValue, String operator, int i){
        if ((list.get(i).firstValue.equals( firstValue) && list.get(i).secondValue.equals(secondValue)) ||
                ((list.get(i).firstValue.equals( secondValue) && list.get(i).secondValue.equals(firstValue)))&&
                        list.get(i).operator.equals( operator)){
            return true;
        }else{
            return false;
        }
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
