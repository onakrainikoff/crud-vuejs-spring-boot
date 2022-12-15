package ru.on8off.crud.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectRequest{
    @NotBlank
    @Size(max = 50)
    private String code;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 250)
    private String description;


    public Project toEntity(){
        var entity = new Project();
        entity.setCode(this.getCode());
        entity.setName(this.getName());
        entity.setDescription(this.getDescription());
        return entity;
    }
}
