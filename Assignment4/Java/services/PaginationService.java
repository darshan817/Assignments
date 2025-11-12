package Assignment4.Java.services;

import java.util.*;
import java.util.stream.*;
import Assignment4.Java.models.Student;

public class PaginationService {
    private final Service s;

    public PaginationService(Service s) {
        this.s = s;
    }

    public List<Student> getStudents(Map<String, Object> f, String sort, boolean asc, int start, Integer end) {
        List<Student> list = new ArrayList<>(s.getStudents());
        if (f != null) {
            for (Map.Entry<String, Object> e : f.entrySet()) {
                String k = e.getKey();
                Object v = e.getValue();
                list = list.stream().filter(st -> {
                    switch (k) {
                        case "gender":
                            return st.getGender().equals(v);
                        case "age":
                            return st.getAge() == (Integer) v;
                        case "classId":
                            return st.getClassId() == (Integer) v;
                        default:
                            return true;
                    }
                }).collect(Collectors.toList());
            }
        }
        if (sort != null) {
            Comparator<Student> c;
            switch (sort) {
                case "marks":
                    c = Comparator.comparingDouble(Student::getMarks);
                    break;
                case "age":
                    c = Comparator.comparingInt(Student::getAge);
                    break;
                default:
                    c = Comparator.comparingInt(Student::getId);
            }
            if (!asc)
                c = c.reversed();
            list.sort(c);
        }
        int to = end == null ? list.size() : Math.min(end, list.size());
        start = Math.max(0, start);
        if (start > to)
            return Collections.emptyList();
        return list.subList(start, to);
    }
}
