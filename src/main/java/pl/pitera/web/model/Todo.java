package pl.pitera.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Todo {

    private long id;
    @NotBlank(message = "Proszę wprowadzić tytuł")
    private String title;
    @NotBlank(message = "Proszę wprowadzić opis")
    private String description;
    private boolean isDone = false;

}
