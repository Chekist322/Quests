package first.cache;

import java.util.ArrayList;

/**
 * Created by Phoen on 03.02.2017.
 *Кеш второго уровня, содержит динамический массив операций, если их больше 10, то очищается
 */
class Level2 {
    ArrayList<Element> list;

    Level2(){
        list = new ArrayList<>();
    }


    void add(Element element) {
        int size = 10;
        if (list.size() == size){
            list.clear();
        }else{
            list.add(element);
        }
    }


    Element get(int ID) {
        return list.get(ID);
    }

    void print() {
        System.out.println("Level2");
        for (Element e : list) {
            System.out.print(e.firstValue);
            System.out.print(e.operator);
            System.out.print(e.secondValue + "=");
            System.out.println(e.result);
        }
    }
}
