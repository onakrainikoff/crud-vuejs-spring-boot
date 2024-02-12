package ru.on8off.crud.backend.fixture;

import ru.on8off.crud.backend.controller.dto.KVObjectDto;
import ru.on8off.crud.backend.controller.dto.KVObjectRequest;
import ru.on8off.crud.backend.repository.entity.KVObject;

import java.util.Random;

public class KVObjectFixture {
    private static final Random random = new Random();

    public static KVObject kvObjectEntity() {
        var entity = new KVObject();
        entity.setKey("TEST-KEY-" + random.nextInt());
        entity.setValue("TEST-VALUE-" + random.nextInt());
        return entity;
    }

    public static KVObjectDto kvObjectDto() {
        return KVObjectDto.fromEntity(kvObjectEntity());
    }

    public static KVObjectRequest kvObjectRequest() {
        var request = new KVObjectRequest();
        request.setKey("TEST-KEY-" + random.nextInt());
        request.setValue("TEST-VALUE-" + random.nextInt());
        return request;
    }

}
