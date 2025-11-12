package Assignment4.Java.models;

public class Student {
    private int id;
    private String name;
    private int classId;
    private double marks;
    private String gender;
    private int age;

    public Student(int id, String name, int classId, double marks, String gender, int age) {
        if (age > 20)
            throw new IllegalArgumentException("Age must be 20 or below");
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassId() {
        return classId;
    }

    public double getMarks() {
        return marks;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', classId=%d, marks=%.2f, gender='%s', age=%d}",
                id, name, classId, marks, gender, age);
    }
}
