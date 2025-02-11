import java.io.*;
import java.util.*;

abstract class Shape {
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным числом.");
        }
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Круг: радиус = %.2f, площадь = %.2f, периметр = %.2f", radius, calculateArea(), calculatePerimeter());
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными числами.");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return String.format("Прямоугольник: ширина = %.2f, высота = %.2f, площадь = %.2f, периметр = %.2f",
                width, height, calculateArea(), calculatePerimeter());
    }
}

class Square extends Shape {
    private double side;

    public Square(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Сторона квадрата должна быть положительным числом.");
        }
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return Math.pow(side, 2);
    }

    @Override
    public double calculatePerimeter() {
        return 4 * side;
    }

    @Override
    public String toString() {
        return String.format("Квадрат: сторона = %.2f, площадь = %.2f, периметр = %.2f", side, calculateArea(), calculatePerimeter());
    }
}

class Triangle extends Shape {
    private double a, b, c;

    public Triangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Стороны треугольника должны быть положительными.");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Сумма двух сторон должна быть больше третьей.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }

    @Override
    public String toString() {
        return String.format("Треугольник: стороны = %.2f, %.2f, %.2f, площадь = %.2f, периметр = %.2f",
                a, b, c, calculateArea(), calculatePerimeter());
    }
}

public class GeometryProgram {
    public static void main(String[] args) {
        List<Shape> shapes = readShapesFromFile("shapes.txt");

        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }

    private static List<Shape> readShapesFromFile(String filename) {
        List<Shape> shapes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String shapeType = parts[0].toLowerCase();
                switch (shapeType) {
                    case "circle":
                        double radius = Double.parseDouble(parts[1]);
                        shapes.add(new Circle(radius));
                        break;
                    case "rectangle":
                        double width = Double.parseDouble(parts[1]);
                        double height = Double.parseDouble(parts[2]);
                        shapes.add(new Rectangle(width, height));
                        break;
                    case "square":
                        double side = Double.parseDouble(parts[1]);
                        shapes.add(new Square(side));
                        break;
                    case "triangle":
                        double a = Double.parseDouble(parts[1]);
                        double b = Double.parseDouble(parts[2]);
                        double c = Double.parseDouble(parts[3]);
                        shapes.add(new Triangle(a, b, c));
                        break;
                    default:
                        System.out.println("Неизвестная фигура: " + shapeType);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return shapes;
    }
}