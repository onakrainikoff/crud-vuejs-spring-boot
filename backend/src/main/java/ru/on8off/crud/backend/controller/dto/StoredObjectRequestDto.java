package ru.on8off.crud.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.StoredObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StoredObjectRequestDto{
    @NotBlank
    @Size(max = 1000)
    private String name;
    private String value;


    public StoredObject toEntity(){
        var entity = new StoredObject();
        entity.setName(this.getName());
        entity.setValue(this.getValue());
        return entity;
    }
}
