package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Evaluation;
import cl.untec.springedumanager.service.EvaluationService;
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
    public String listEvaluations(Model model) {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        model.addAttribute("evaluationList", evaluations);
        return "evaluations";
    }
}