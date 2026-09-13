package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Practice;
import cl.untec.springedumanager.service.PracticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PracticeController {

    private final PracticeService practiceService;

    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping("/practices")
    public String listPractices(Model model) {
        List<Practice> practices = practiceService.getAllPractices();
        model.addAttribute("practiceList", practices);
        return "practices";
    }
}