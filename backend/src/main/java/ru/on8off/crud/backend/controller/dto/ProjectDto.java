package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.Project;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDto {
    private Integer id;
    private String code;
    private String name;
    private String description;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateUpdated;


    public static ProjectDto fromEntity(Project entity){
        if(entity == null) {
            return null;
        }
        var dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDateUpdated(entity.getDateUpdated());
        return dto;
    }
}
