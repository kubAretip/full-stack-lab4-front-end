package pl.pitera.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
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

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public IndexController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {

        ResponseEntity<List<Todo>> entity = restTemplate.exchange(apiUrl + "todos",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Todo>>() {
                });

        var todoList = entity.getBody();

        model.addAttribute("todoList", todoList);

        return "index";
    }


}
