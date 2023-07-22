package ru.chervenko.EnsetKB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chervenko.EnsetKB.model.Problem;
import ru.chervenko.EnsetKB.service.ProblemService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final ProblemService problemService;

    @Autowired
    public SearchController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping()
    public String search(@RequestParam(name = "request", required = false) String request,
                         @RequestParam(name = "page", defaultValue = "1") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size,
                         Model model) {
        Page<Problem> problemListPage = problemService.findByNameContains(request, PageRequest.of(page - 1, size));
        model.addAttribute("problems", problemListPage.getContent());
        model.addAttribute("problemListPageSize", problemListPage.getSize());
        model.addAttribute("currentPageNumber", problemListPage.getNumber() + 1);
        model.addAttribute("totalPages", problemListPage.getTotalPages());
        model.addAttribute("request", request);

        int totalPages = problemListPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "search/result";
    }
}
