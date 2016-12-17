import java.awt.*;
import java.util.Scanner;
import java.util.concurrent.RecursiveAction;
import java.math.*;
import java.lang.Double;

class Start{

    static class ComplexNumber{
        double real;
        double imaginary;

        public ComplexNumber() {}

        public ComplexNumber( double real, double imaginary ) {
            this.real = real;
            this.imaginary = imaginary;
        }

        @Override
        public String toString() {
            if (imaginary >= 0)
                return real + "+" +  imaginary + "i";
            else
                return real + "-" + -imaginary + "i";
        }

        public ComplexNumber Add( ComplexNumber cB ) {
            ComplexNumber sum = new ComplexNumber();

            sum.real = real+ cB.real;
            sum.imaginary = imaginary + cB.imaginary;

            return (sum);
        }
        public ComplexNumber Sub( ComplexNumber cB ) {
            ComplexNumber sub = new ComplexNumber();

            sub.real = real- cB.real;
            sub.imaginary = imaginary - cB.imaginary;

            return (sub);
        }
        public ComplexNumber Mult( ComplexNumber cB ) {
            ComplexNumber prod = new ComplexNumber();

            prod.real = real*cB.real - imaginary*cB.imaginary;
            prod.imaginary = imaginary*cB.real + real*cB.imaginary;

            return (prod);
        }

        public ComplexNumber Div( ComplexNumber cB ) {
            ComplexNumber div = new ComplexNumber();
            double dR, dDen;

            if(Math.abs( cB.real ) >= Math.abs( cB.imaginary )) {
                dR = cB.imaginary/cB.real;
                dDen = cB.real + dR*cB.imaginary;
                div.real = (real + dR*imaginary)/dDen;
                div.imaginary = (imaginary - dR*real)/dDen;
            } else {
                dR = cB.real/cB.imaginary;
                dDen = cB.imaginary + dR*cB.real;
                div.real = (dR*real + imaginary)/dDen;
                div.imaginary = (dR*imaginary - real)/dDen;
            }
            return (div);
        }
    }

    interface Figure {
        double GetArea();
        double GetPerimeter();
    }

    static class Square implements Figure{
        double side;
        Square(){}
        Square(double _side){ side = _side;}

        @Override
        public double GetArea() {
            double area;
            area = side*side;
            return area;
        }

        @Override
        public double GetPerimeter() {
            double perimeter;
            perimeter = side*4;
            return perimeter;
        }
    }

    static class Circle implements Figure{
        double radius;
        Circle(){}
        Circle(double _radius){radius = _radius;}

        @Override
        public double GetArea() {
            double area;
            area = 3.14*radius*radius;
            return area;
        }

        @Override
        public double GetPerimeter() {
            double perimeter;
            perimeter = (double) 2*3.14*radius;
            return perimeter;
        }
    }

    static class Rectangle implements Figure{
        double firstSide;
        double secondSide;
        Rectangle(){}

        Rectangle(double _firstSide, double _secondSide){
            firstSide = _firstSide;
            secondSide = _secondSide;
        }

        @Override
        public double GetArea() {
            double area;
            area = firstSide*secondSide;
            return area;
        }

        @Override
        public double GetPerimeter() {
            double perimeter;
            perimeter = firstSide*2+secondSide*2;
            return perimeter;
        }
    }
    static class Triangle implements Figure{
        double a;
        double b;
        double c;
        Triangle(){}

        Triangle(double _a, double _b, double _c){
            a = _a;
            b = _b;
            c = _c;
        }

        @Override
        public double GetArea() {
            double p;
            p =(a+b+c)/2;
            double area;
            area = Math.sqrt(p*(p-a)*(p-b)*(p-c));
            return area;
        }

        @Override
        public double GetPerimeter() {
            double perimeter;
            perimeter = a+b+c;
            return perimeter;
        }
    }


    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the data (4 positive number: a, b, c, b. Divide it by 'Enter'):\na:");
        double a = scanner.nextDouble();
        System.out.print("b:");
        double b = scanner.nextDouble();
        System.out.print("c:");
        double c = scanner.nextDouble();
        System.out.print("d:");
        double d = scanner.nextDouble();
        ComplexNumber first = new ComplexNumber(a,-b);
        ComplexNumber second = new ComplexNumber(c,d);
        System.out.println();
        System.out.println("(a+bi)+(c+di):");
        System.out.println(first.Add(second).toString());
        System.out.println("(a+bi)-(c+di):");
        System.out.println(first.Sub(second).toString());
        System.out.println("(a+bi)*(c+di):");
        System.out.println(first.Mult(second).toString());
        System.out.println("(a+bi)/(c+di):");
        System.out.println(first.Div(second).toString());
        Square square = new Square(a);
        System.out.println("Square parameter with side :" + a);
        System.out.println("Area: " + square.GetArea());
        System.out.println("Perimeter: " + square.GetPerimeter());
        Rectangle rectangle = new Rectangle(a,b);
        System.out.println("Rectangle parameters with sides " + a + " ; " + b);
        System.out.println("Area: " + rectangle.GetArea());
        System.out.println("Perimeter: " + rectangle.GetPerimeter());
        Circle circle = new Circle(a);
        System.out.println("Circle parameter with radius " + a);
        System.out.println("Area: " + circle.GetArea());
        System.out.println("Perimeter: " + circle.GetPerimeter());
        Triangle triangle = new Triangle(a,b,c);
        System.out.println("Triangle parameters with sides " + a + " ; " + b + " ; " + c);
        if (Double.isNaN(triangle.GetArea())) {
            System.out.println("That triangle couldn't be exist!");
        } else {
            System.out.println("Area: " + triangle.GetArea());
            System.out.println("Perimeter: " + triangle.GetPerimeter());
        }
    }
}