package Assignment3.Java.models;


public class SchoolClass {
    private int id;
    private String name;

    public SchoolClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    @Override
    public String toString() {
        return String.format("SchoolClass{id=%d, name='%s'}", id, name);
    }
}

