package first.Cash;

import java.util.ArrayList;

/**
 * Created by Phoen on 03.02.2017.
 */
public class Level2 {
    public ArrayList<Element> list;
    private int size = 10;

    Level2(){
        list = new ArrayList<>();
    }


    public void add(Element element) {
        if (list.size() == size){
            list.clear();
        }else{
            list.add(element);
        }
    }


    public Element get(int ID) {
        return list.get(ID);
    }

    public void print() {
        System.out.println("Level2");
        for (Element e : list) {
            System.out.print(e.firstValue);
            System.out.print(e.operator);
            System.out.print(e.secondValue + "=");
            System.out.println(e.result);
        }
    }
}
