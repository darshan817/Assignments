package Assignment3.Java.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Assignment3.Java.models.Student;

public class PaginationService {
    private final Service service;

    public PaginationService(Service service) {
        this.service = service;
    }

    public List<Student> getStudents(Map<String, Object> filters, String sortBy, boolean ascending, int start, Integer end) {
        List<Student> list = new ArrayList<>(service.getStudents());

        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                String key = e.getKey();
                Object val = e.getValue();
                list = list.stream().filter(s -> {
                    switch (key) {
                        case "gender": return s.getGender().equals(val);
                        case "age": return s.getAge() == (Integer)val;
                        case "classId": return s.getClassId() == (Integer)val;
                        default: return true;
                    }
                }).collect(Collectors.toList());
            }
        }

        if (sortBy != null) {
            Comparator<Student> cmp;
            switch (sortBy) {
                case "marks": cmp = Comparator.comparingDouble(Student::getMarks); break;
                case "age": cmp = Comparator.comparingInt(Student::getAge); break;
                default: cmp = Comparator.comparingInt(Student::getId); break;
            }
            if (!ascending) cmp = cmp.reversed();
            list.sort(cmp);
        }

        int toIndex = end == null ? list.size() : Math.min(end, list.size());
        start = Math.max(0, start);
        if (start > toIndex) return Collections.emptyList();
        return list.subList(start, toIndex);
    }
}

