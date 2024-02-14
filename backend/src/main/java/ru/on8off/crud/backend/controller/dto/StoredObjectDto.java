package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.StoredObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoredObjectDto {
    private Integer id;
    private String name;
    private String value;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateUpdated;


    public static StoredObjectDto fromEntity(StoredObject entity){
        if(entity == null) {
            return null;
        }
        var dto = new StoredObjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDateUpdated(entity.getDateUpdated());
        return dto;
    }
}
