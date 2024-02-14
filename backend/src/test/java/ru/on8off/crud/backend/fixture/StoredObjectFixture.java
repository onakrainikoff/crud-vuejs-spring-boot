package ru.on8off.crud.backend.fixture;

import ru.on8off.crud.backend.controller.dto.StoredObjectDto;
import ru.on8off.crud.backend.controller.dto.StoredObjectRequestDto;
import ru.on8off.crud.backend.repository.entity.StoredObject;

import java.util.Random;

public class StoredObjectFixture {
    private static final Random random = new Random();

    public static StoredObject storedObjectEntity() {
        var entity = new StoredObject();
        entity.setName("TEST-KEY-" + random.nextInt());
        entity.setValue("TEST-VALUE-" + random.nextInt());
        return entity;
    }

    public static StoredObjectDto storedObjectDto() {
        return StoredObjectDto.fromEntity(storedObjectEntity());
    }

    public static StoredObjectRequestDto storedObjectRequest() {
        var request = new StoredObjectRequestDto();
        request.setName("TEST-KEY-" + random.nextInt());
        request.setValue("TEST-VALUE-" + random.nextInt());
        return request;
    }

}
