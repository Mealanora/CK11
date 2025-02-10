import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private int age;
    private String gender;

    public Student() {
    }

    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public String toString() {
        return String.format("Student{name='%s', age=%d, gender='%s'}", this.name, this.age, this.gender);
    }

    public static class Builder {
        private String name;
        private int age;
        private String gender;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Student build() {
            return new Student(this.name, this.age, this.gender);
        }
    }
}

public class StudentProgram{
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Екатерина", 20, "женский"));
        students.add(new Student("Денис", 22, "мужской"));
        students.add(new Student.Builder()
                .setName("Станислав")
                .setAge(22)
                .setGender("мужской")
                .build());
        students.add(new Student.Builder()
                .setName("Кристина")
                .setAge(20)
                .setGender("женский")
                .build());

        String filename = "students.dat";
        writeStudentsToFile(students, filename);
        List<Student> loadedStudents = readStudentsFromFile(filename);

        System.out.println("Студенты мужского пола:");
        loadedStudents.stream()
                .filter(student -> "мужской".equalsIgnoreCase(student.getGender()))
                .forEach(System.out::println);

        System.out.println("\nСтуденты женского пола:");
        loadedStudents.stream()
                .filter(student -> "женский".equalsIgnoreCase(student.getGender()))
                .forEach(System.out::println);
    }

    private static void writeStudentsToFile(List<Student> students, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            System.out.println("Студенты успешно записаны в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Student> readStudentsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при чтении из файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}