package cl.untec.springedumanager.service;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Evaluation;
import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.repository.CourseRepository;
import cl.untec.springedumanager.repository.EvaluationRepository;
import cl.untec.springedumanager.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EvaluationService(EvaluationRepository evaluationRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Evaluation> getAllEvaluations() {
        if (evaluationRepository.count() == 0) {
            Student firstStudent = studentRepository.findAll().stream().findFirst().orElse(null);
            Course javaCourse = courseRepository.findAll().stream()
                    .filter(c -> c.getCode().equals("JAVA-001"))
                    .findFirst()
                    .orElse(null);

            if (firstStudent != null && javaCourse != null) {
                Evaluation evaluation = new Evaluation();
                evaluation.setTitle("Midterm Exam");
                evaluation.setScore(6.5);
                evaluation.setDate(LocalDate.now());
                evaluation.setStudent(firstStudent);
                evaluation.setCourse(javaCourse);
                evaluationRepository.save(evaluation);
            }
        }
        return evaluationRepository.findAll();
    }

    public List<Evaluation> getEvaluationsForStudentEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(evaluationRepository::findByStudent)
                .orElse(Collections.emptyList());
    }
}