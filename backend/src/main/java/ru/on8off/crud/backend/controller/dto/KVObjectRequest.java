package ru.on8off.crud.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.on8off.crud.backend.repository.entity.KVObject;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KVObjectRequest{
    @NotBlank
    @Size(max = 1000)
    private String key;
    private String value;


    public KVObject toEntity(){
        var entity = new KVObject();
        entity.setKey(this.getKey());
        entity.setValue(this.getValue());
        return entity;
    }
}
