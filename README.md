# Laba8
Лабораторна робота №12: **Колекції. Множина HashSet. Асоціативні масиви Map.**

### 1. Ознайомлення з javadoc:

1. **`Set`**: Це інтерфейс колекції, що представляє множину елементів, де кожен елемент може зустрічатися тільки один раз. У Set немає порядку елементів, і їх не можна індексувати. Він визначає основні операції для роботи з множинами: додавання елементів, перевірка наявності елемента, видалення елементів тощо.
   - **Методи:**
     - `add(E e)`: додає елемент у множину.
     - `remove(Object o)`: видаляє елемент з множини.
     - `contains(Object o)`: перевіряє, чи є елемент у множині.

2. **`HashSet`**: Це конкретна реалізація інтерфейсу `Set`, яка використовує хешування для зберігання елементів. Він не гарантує збереження порядку елементів, але надає швидкий доступ до елементів за допомогою їх хеш-коду.
   - **Методи:**
     - Наслідує всі методи від Set, але працює з елементами на основі їх хеш-кодів, тому пошук елементів у HashSet дуже швидкий.

3. **`Object.equals()` та `Object.hashCode()`**: Ці методи використовуються для порівняння об'єктів та отримання хеш-кодів. Для коректної роботи з колекціями типу `HashSet` потрібно правильно перевизначити ці методи:
   - **`equals(Object obj)`**: перевіряє, чи рівні два об'єкти.
   - **`hashCode()`**: повертає хеш-код об'єкта, який використовує `HashSet` для ефективного пошуку.

4. **`Map`**: Це інтерфейс, що представляє асоціативний масив або словник. У ньому елементи зберігаються у вигляді пар "ключ-значення". Ключі у мапі є унікальними, а значення можуть повторюватися.
   - **Методи:**
     - `put(K key, V value)`: додає пару ключ-значення.
     - `get(Object key)`: отримує значення за ключем.
     - `remove(Object key)`: видаляє пару за ключем.

5. **`HashMap`**: Це реалізація інтерфейсу `Map`, що використовує хешування для зберігання пар "ключ-значення". Як і `HashSet`, він не гарантує збереження порядку елементів, але забезпечує швидкий доступ до значень за допомогою хеш-кодів.

---

### 2. Виконання завдання:

#### Опис предметної області:
Уявимо, що ми розробляємо програму для обліку студентів у університеті. Для цього ми будемо використовувати колекції, щоб зберігати інформацію про студентів, курси та їх оцінки. Студенти будуть зберігатися у множині `Set`, а оцінки — у асоціативних масивах `Map`.

- **`Set` (HashSet)** — ми будемо зберігати список студентів (відповідно, у кожного студента має бути унікальний ID).
- **`Map` (HashMap)** — для кожного студента ми будемо зберігати оцінки з різних курсів у вигляді пар "назва курсу — оцінка".

#### Крок 1: Створення класів:

```java
import java.util.*;

class Student {
    private String name;
    private String studentId;

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
    private Set<Student> students;
    private Map<Student, Map<String, Integer>> studentGrades;

    public University() {
        students = new HashSet<>();
        studentGrades = new HashMap<>();
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
```

#### Пояснення коду:
1. **Клас `Student`**:
   - Кожен студент має унікальний ID (наприклад, "S001"), і ім'я.
   - Перевизначені методи `equals` та `hashCode`, щоб коректно порівнювати студентів у множині `HashSet`.
   - Метод `toString()` для зручного виведення студентів.

2. **Клас `University`**:
   - **`Set<Student> students`** — множина студентів, щоб гарантувати, що кожен студент є унікальним.
   - **`Map<Student, Map<String, Integer>> studentGrades`** — мапа, яка зберігає для кожного студента оцінки по курсу. Ключем є студент, а значенням — мапа курсів і відповідних оцінок.

   - **Методи**:
     - `addStudent(Student student)` — додає студента у множину.
     - `addGrade(Student student, String course, int grade)` — додає оцінку для певного студента.
     - `printStudentGrades()` — виводить усі оцінки студентів.
     - `printTopStudent()` — знаходить студента з найвищим середнім балом.

#### Результат виконання:
```
John Doe (S001) - Grades: {Math=95, Science=90}
Jane Smith (S002) - Grades: {Math=85, Science=92}
Top student: John Doe (S001) with average grade: 92.5
```

### Висновки:
- Використання **`HashSet`** дозволяє забезпечити унікальність студентів.
- Використання **`HashMap`** дозволяє ефективно зберігати оцінки кожного студента.
- Ми реалізували функціональність пошуку студента з найкращими оцінками завдяки використанню асоціативних масивів (`Map`).

Це базова реалізація, і можна додавати додаткові функції, такі як видалення студентів, зміна оцінок та інші.

Якщо у вас
```
Error: Could not find or load main class Main
Caused by: java.lang.NoClassDefFoundError: Main (wrong name: main)
```
Помилка, яку ви отримали — це **`NoClassDefFoundError`**, що вказує на те, що Java не може знайти або завантажити клас `Main`. Причиною цього може бути неправильне ім'я файлу або інші помилки в конфігурації.

### Ось кілька кроків для виправлення цієї проблеми:

1. **Перевірте ім'я файлу**:
   Переконайтеся, що ваш файл називається **`Main.java`**. У Java ім'я класу має співпадати з ім'ям файлу. Тобто, якщо ви маєте клас з іменем `Main`, файл повинен бути названий `Main.java`.

2. **Компіляція**:
   Використовуйте наступні команди для компіляції та запуску:
   
   - Спочатку компілюємо програму:
     ```bash
     javac Main.java
     ```

   - Потім запускаємо програму:
     ```bash
     java Main
     ```

   Переконайтеся, що файл зберігається у правильному каталозі, і що ви перебуваєте в тому ж каталозі при компіляції та запуску.

3. **Неправильний пакет (Package)**:
   Якщо ви використовуєте пакети у вашому коді (наприклад, `package myPackage;` вгорі вашого файлу), тоді структура каталогів повинна відповідати пакету. Переконайтеся, що ви правильно знаходитеся в каталозі, що відповідає пакету.

4. **Інші можливі проблеми**:
   - Якщо клас `Main` знаходиться в іншому файлі або не збірний, перевірте, чи зібрані всі необхідні класи та бібліотеки.
   - Переконайтеся, що у вас немає старих версій файлів у каталозі, які можуть впливати на процес компіляції.

### Простіше:
1. Збережіть файл з ім'ям **`Main.java`**.
2. Виконайте команди:
   ```bash
   javac Main.java
   java Main
   ```

Це має вирішити проблему і правильно виконати програму.
