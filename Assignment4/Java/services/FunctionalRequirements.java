package Assignment4.Java.services;
import java.util.*; import java.util.stream.*; import Assignment4.Java.models.*;
public class FunctionalRequirements {
    private final Service service; public FunctionalRequirements(Service s){this.service=s;}
    public List<Student> studentsByCity(String city){if(city==null)return new ArrayList<>();List<Integer> ids=service.getAddresses().stream().filter(a->city.equals(a.getCity())).map(Address::getStudentId).collect(Collectors.toList());return service.getStudents().stream().filter(s->ids.contains(s.getId())).collect(Collectors.toList());}
    public List<Student> ranking(Integer min,Integer max){return service.getStudents().stream().filter(s->{if(min!=null&&s.getAge()<min)return false;if(max!=null&&s.getAge()>max)return false;return true;}).sorted(Comparator.comparingDouble(Student::getMarks).reversed()).collect(Collectors.toList());}
}
