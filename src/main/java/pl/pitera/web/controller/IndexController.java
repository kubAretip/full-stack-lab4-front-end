package pl.pitera.web.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import pl.pitera.web.model.Todo;

import java.util.List;

@Controller
public class IndexController {

    String url = "http://localhost:8080/";

    private final RestTemplate restTemplate;

    public IndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {

        ResponseEntity<List<Todo>> entity = restTemplate.exchange(url + "todos",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Todo>>() {
                });

        var todoList = entity.getBody();

        model.addAttribute("todoList", todoList);

        return "index";
    }


}
