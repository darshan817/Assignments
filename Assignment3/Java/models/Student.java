package Assignment3.Java.models;

public class Student {
    private int id;
    private String name;
    private int classId;
    private double marks;
    private String gender;
    private int age;

    public Student(int id, String name, int classId, double marks, String gender, int age) {
        if (age > 20) {
            throw new IllegalArgumentException("Age must be 20 or below");
        }
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.gender = gender;
        this.age = age;
    }

    public int getId(){ 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }


    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }


    public int getClassId() { 
        return classId; 
    }

    public void setClassId(int classId) { 
        this.classId = classId; 
    }
    public double getMarks() { 
        return marks; 
    }
    public void setMarks(double marks) { 
        this.marks = marks; 
    }
    public String getGender() { 
        return gender; 
    }
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    public int getAge() { 
        return age;
    }
    public void setAge(int age) {
        if (age > 20) { throw new IllegalArgumentException("Age must be 20 or below"); }
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', classId=%d, marks=%.2f, gender='%s', age=%d}",
                id, name, classId, marks, gender, age);
    }

}
