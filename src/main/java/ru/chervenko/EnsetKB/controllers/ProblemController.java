package ru.chervenko.EnsetKB.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.chervenko.EnsetKB.dto.ProblemDTO;
import ru.chervenko.EnsetKB.models.Problem;
import ru.chervenko.EnsetKB.services.ProblemService;

@Controller
@RequestMapping("/problems")
public class ProblemController {
    private final ProblemService problemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProblemController(ProblemService problemService, ModelMapper modelMapper) {
        this.problemService = problemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("problems", problemService.findAll());
        return "problems/index";
    }

    @GetMapping("/id")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("problem", problemService.findById(id));
        return "problems/show";
    }
    @GetMapping("/new")
    public String newProblem(@ModelAttribute("problem") @Valid ProblemDTO problemDTO){
        return "problems/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("problem") @Valid ProblemDTO problemDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "problems/new";
        problemService.save(convertToProblem(problemDTO));
        return "redirect:/problems";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", problemService.findById(id));
        return "problems/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("problem") ProblemDTO problemDTO,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "problems/edit";
        problemService.update(id, convertToProblem(problemDTO));
        return "redirect:/problems";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        problemService.delete(id);
        return "redirect:/problems";
    }

    private Problem convertToProblem(ProblemDTO problemDTO){
        return modelMapper.map(problemDTO, Problem.class);
    }


}
