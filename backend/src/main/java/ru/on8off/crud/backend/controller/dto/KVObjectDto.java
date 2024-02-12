package ru.on8off.crud.backend.controller.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.KVObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KVObjectDto {
    private Integer id;
    private String key;
    private String value;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateUpdated;


    public static KVObjectDto fromEntity(KVObject entity){
        if(entity == null) {
            return null;
        }
        var dto = new KVObjectDto();
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setValue(entity.getValue());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDateUpdated(entity.getDateUpdated());
        return dto;
    }
}
