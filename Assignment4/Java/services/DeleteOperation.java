package Assignment4.Java.services;
public class DeleteOperation {
    private final Service service; public DeleteOperation(Service s){this.service=s;}
    public void deleteStudent(int id){boolean ex=service.findStudentById(id).isPresent();if(!ex){System.out.println("No student found "+id);return;}service.deleteStudent(id);System.out.println("Student deleted "+id);}
}
