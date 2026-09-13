package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Evaluation;
import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.repository.CourseRepository;
import cl.untec.springedumanager.repository.EvaluationRepository;
import cl.untec.springedumanager.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EvaluationController {

    private final EvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EvaluationController(EvaluationRepository evaluationRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/evaluations")
    public String listEvaluations(Model model) {

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

        List<Evaluation> evaluations = evaluationRepository.findAll();
        model.addAttribute("evaluationList", evaluations);
        return "evaluations";
    }
}