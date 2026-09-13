package cl.untec.springedumanager.service;

import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.model.User;
import cl.untec.springedumanager.repository.StudentRepository;
import cl.untec.springedumanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    public Student registerStudent(String firstName, String lastName, String email, String password) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        Student savedStudent = studentRepository.save(student);

        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("STUDENT");
        user.setStudent(savedStudent);
        userRepository.save(user);

        return savedStudent;
    }
}