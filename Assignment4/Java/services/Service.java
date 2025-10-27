package Assignment4.Java.services;

import java.util.*; import java.util.stream.Collectors;
import Assignment4.Java.models.*;

public class Service {
    private final DataStorage storage; private List<Student> students; private List<Address> addresses; private List<SchoolClass> classes;
    public Service(){this("Assignment4/Java/data");}
    public Service(String d){storage=new DataStorage(d);students=new ArrayList<>(storage.loadStudents());addresses=new ArrayList<>(storage.loadAddresses());classes=new ArrayList<>(storage.loadClasses());}
    public void addStudent(Student s){if(students.stream().anyMatch(x->x.getId()==s.getId()))throw new IllegalArgumentException("Duplicate");students.add(s);storage.saveStudents(students);}
    public void addAddress(Address a){addresses.add(a);storage.saveAddresses(addresses);} public void addClass(SchoolClass c){classes.add(c);storage.saveClasses(classes);}
    public List<Student> getStudents(){return Collections.unmodifiableList(students);} public List<Address> getAddresses(){return Collections.unmodifiableList(addresses);} public List<SchoolClass> getClasses(){return Collections.unmodifiableList(classes);}
    public Optional<Student> findStudentById(int id){return students.stream().filter(s->s.getId()==id).findFirst();}
    public void deleteStudent(int id){students=students.stream().filter(s->s.getId()!=id).collect(Collectors.toList());addresses=addresses.stream().filter(a->a.getStudentId()!=id).collect(Collectors.toList());storage.saveStudents(students);storage.saveAddresses(addresses);}
}
