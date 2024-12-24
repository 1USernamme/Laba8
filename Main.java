package lab8;

import java.util.*;

class Student {
    private final String name;
    private final String studentId;

    public Student(String name, String studentId) {
        this.name = name;

        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ")";
    }
}

class University {
    private final Set<Student> students;
    private final Map<Student, Map<String, Integer>> studentGrades = new HashMap<>();

    public University() {
        students = new HashSet<>();
    }

    public void addStudent(Student student) {
        students.add(student);
        studentGrades.putIfAbsent(student, new HashMap<>());
    }

    public void addGrade(Student student, String course, int grade) {
        if (studentGrades.containsKey(student)) {
            studentGrades.get(student).put(course, grade);
        } else {
            System.out.println("Student not found!");
        }
    }

    public void printStudentGrades() {
        for (Student student : students) {
            System.out.println(student + " - Grades: " + studentGrades.get(student));
        }
    }

    public void printTopStudent() {
        Student topStudent = null;
        double maxAverage = 0;

        for (Student student : students) {
            Map<String, Integer> grades = studentGrades.get(student);
            double average = grades.values().stream().mapToInt(Integer::intValue).average().orElse(0);

            if (average > maxAverage) {
                maxAverage = average;
                topStudent = student;
            }
        }

        if (topStudent != null) {
            System.out.println("Top student: " + topStudent + " with average grade: " + maxAverage);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        University university = new University();

        Student student1 = new Student("John Doe", "S001");
        Student student2 = new Student("Jane Smith", "S002");

        university.addStudent(student1);
        university.addStudent(student2);

        university.addGrade(student1, "Math", 95);
        university.addGrade(student1, "Science", 90);

        university.addGrade(student2, "Math", 85);
        university.addGrade(student2, "Science", 92);

        university.printStudentGrades();
        university.printTopStudent();
    }
}
