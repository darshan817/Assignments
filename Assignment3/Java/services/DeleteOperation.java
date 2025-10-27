package Assignment3.Java.services;

public class DeleteOperation {
    private final Service service;

    public DeleteOperation(Service service) {
        this.service = service;
    }

    public void deleteStudent(int studentId) {
        boolean exists = service.findStudentById(studentId).isPresent();
        if (!exists) {
            System.out.println("No student found with ID " + studentId);
            return;
        }
        service.deleteStudent(studentId);
        System.out.println("Student with ID " + studentId + " deleted successfully.");
    }
}