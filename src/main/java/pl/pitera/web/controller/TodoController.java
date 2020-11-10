package pl.pitera.web.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import pl.pitera.web.model.Todo;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class TodoController {

    String url = "http://localhost:8080/";

    private final RestTemplate restTemplate;

    public TodoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ModelAttribute("todo")
    public Todo todo() {
        return new Todo();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        ResponseEntity<Todo> entity = restTemplate.exchange(url + "todos/" + id,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Todo.class);

        model.addAttribute("todo", entity.getBody());

        return "edit";
    }

    @PostMapping("/update")
    public String updateTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "edit";


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Todo> entity = new HttpEntity<Todo>(todo, headers);
        restTemplate.exchange(url + "todos/" + todo.getId(), HttpMethod.PUT, entity, Todo.class);


        return "redirect:/";
    }


    @GetMapping("/done/{id}")
    public String done(@PathVariable int id) {

        Todo todo = new Todo();
        todo.setId(id);
        todo.setDone(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Todo> entity = new HttpEntity<Todo>(todo, headers);
        restTemplate.exchange(url + "todos/" + id, HttpMethod.PUT, entity, Todo.class);

        return "redirect:/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id) {

        restTemplate.exchange(url + "todos/" + id,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                String.class);

        return "redirect:/";
    }

    @GetMapping("/new")
    public String newTodo() {
        return "new";
    }

    @PostMapping("/saveNew")
    public String newTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "new";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Todo> entity = new HttpEntity<Todo>(todo, headers);
        restTemplate.exchange(url + "todos", HttpMethod.POST, entity, Todo.class);


        return "redirect:/";
    }

}
