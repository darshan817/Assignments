package Assignment4.Java.services;

import java.io.*;
import java.util.*;
import Assignment4.Java.models.*;

/**
 * DataStorage - pure Java file handling without any JSON libraries.
 * Stores data in simple CSV-style text files using OOP concepts.
 */
public class DataStorage {

    private final String baseDir;

    public DataStorage(String baseDir) {
        this.baseDir = baseDir;
        File dir = new File(baseDir);
        if (!dir.exists())
            dir.mkdirs();
    }

    // -------------------- STUDENT --------------------
    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(baseDir, "students.txt");
        if (!file.exists())
            return students;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // id,name,classId,marks,gender,age
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int classId = Integer.parseInt(parts[2]);
                    double marks = Double.parseDouble(parts[3]);
                    String gender = parts[4];
                    int age = Integer.parseInt(parts[5]);
                    students.add(new Student(id, name, classId, marks, gender, age));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading students: " + e.getMessage());
        }
        return students;
    }

    public void saveStudents(List<Student> students) {
        File file = new File(baseDir, "students.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student s : students) {
                bw.write(s.getId() + "," + s.getName() + "," + s.getClassId() + "," +
                        s.getMarks() + "," + s.getGender() + "," + s.getAge());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing students: " + e.getMessage());
        }
    }

    // -------------------- ADDRESS --------------------
    public List<Address> loadAddresses() {
        List<Address> addresses = new ArrayList<>();
        File file = new File(baseDir, "addresses.txt");
        if (!file.exists())
            return addresses;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // id,pincode,city,studentId
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String pincode = parts[1];
                    String city = parts[2];
                    int studentId = Integer.parseInt(parts[3]);
                    addresses.add(new Address(id, pincode, city, studentId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading addresses: " + e.getMessage());
        }
        return addresses;
    }

    public void saveAddresses(List<Address> addresses) {
        File file = new File(baseDir, "addresses.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Address a : addresses) {
                bw.write(a.getId() + "," + a.getPincode() + "," + a.getCity() + "," + a.getStudentId());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing addresses: " + e.getMessage());
        }
    }

    // -------------------- SCHOOL CLASS --------------------
    public List<SchoolClass> loadClasses() {
        List<SchoolClass> classes = new ArrayList<>();
        File file = new File(baseDir, "classes.txt");
        if (!file.exists())
            return classes;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // id,name
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    classes.add(new SchoolClass(id, name));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading classes: " + e.getMessage());
        }
        return classes;
    }

    public void saveClasses(List<SchoolClass> classes) {
        File file = new File(baseDir, "classes.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (SchoolClass c : classes) {
                bw.write(c.getId() + "," + c.getName());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing classes: " + e.getMessage());
        }
    }
}
