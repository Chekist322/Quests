import java.util.Random;
import java.util.Scanner;

class SecondQuest{
/**Класс односвязного списка */
    static class List{
        int data;
        List nextList;
        List(){}
        List(int _data){data = _data;}

        void addNext(int _data){
            nextList = new List(_data);
        }

        static void Print(List list){
            System.out.println(list.data);
            if (list.nextList!=null)
                Print(list.nextList);
        }
    private static Random random = new Random();
    static void Generate(int size, List list) {
        list.data=Math.abs(random.nextInt(20));
        List current;
        current = list;
        for (int i =0; i<size-1; i++) {
            current.nextList = new List(Math.abs(random.nextInt(20)));
            current = current.nextList;
        }


    }
    }

    static class Stack{
        private int mSize;
        private List stackArray[];
        private int top;
        Stack(){
            top=0;
        }
        Stack(int m){
            top = 0;
            this.mSize = m;
            stackArray = new List[mSize];
        }
        void Push(List list){
            stackArray[++top] = list;
        }
        void Pop(){
            top--;
        }
        List ReadTop(){
           return(stackArray[top]);
        }
    }



    static public void main (String[] args){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the data (3 positive numbers,capacity of Lists a,b,c. Divide it by 'Enter'):\na:");
        int a = scanner.nextInt();
        System.out.print("b:");
        int b = scanner.nextInt();
        System.out.print("c:");
        int c = scanner.nextInt();
        List firstList = new List();
        List.Generate(a, firstList);
        System.out.println("First list: ");
        List.Print(firstList);
        List secondList = new List();
        List.Generate(b, secondList);
        System.out.println("Second list: ");
        List.Print(secondList);
        List thirdList = new List();
        System.out.println("Third list: ");
        List.Generate(c, thirdList);
        List.Print(thirdList);
        Stack stack = new Stack(5);
        System.out.println("Including list into stack:");
        System.out.println("------------------------------------------------");
        stack.Push(firstList);
        stack.Push(secondList);
        stack.Push(thirdList);
        System.out.println("Top stack element: ");
        List.Print(stack.ReadTop());
        stack.Pop();
        System.out.println("Top stack element after Pop: ");
        List.Print(stack.ReadTop());
       }
}