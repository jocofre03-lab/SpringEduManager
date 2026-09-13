package cl.untec.springedumanager.service;

import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    public Student registerStudent(String firstName, String lastName, String email) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        return studentRepository.save(student);
    }
}
