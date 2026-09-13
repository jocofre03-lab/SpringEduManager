package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Evaluation;
import cl.untec.springedumanager.service.EvaluationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/evaluations")
    public String listEvaluations(Model model, Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        List<Evaluation> evaluations;
        if (isAdmin) {
            evaluations = evaluationService.getAllEvaluations();
        } else {
            String email = authentication.getName();
            evaluations = evaluationService.getEvaluationsForStudentEmail(email);
        }

        model.addAttribute("evaluationList", evaluations);
        return "evaluations";
    }
}