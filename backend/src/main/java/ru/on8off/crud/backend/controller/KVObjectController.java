package ru.on8off.crud.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ru.on8off.crud.backend.controller.dto.PageDto;
import ru.on8off.crud.backend.controller.dto.PageRequest;
import ru.on8off.crud.backend.controller.dto.KVObjectDto;
import ru.on8off.crud.backend.controller.dto.KVObjectRequest;
import ru.on8off.crud.backend.controller.dto.KVObjectFilterRequest;
import ru.on8off.crud.backend.service.KVObjectService;
import org.springframework.http.HttpStatusCode;

import java.util.stream.Collectors;

@RestController
@Slf4j
public class KVObjectController {
    @Autowired
    private KVObjectService kvObjectService;

    @GetMapping("/kv-objects")
    public PageDto<KVObjectDto> all(PageRequest pageRequest, KVObjectFilterRequest kvObjectFilterRequest){
        log.info("Request GET /kv-object pageRequest={} kvObjectFilterRequest={}", pageRequest, kvObjectFilterRequest);
        var page = kvObjectService.getAll(kvObjectFilterRequest.getFilter(), pageRequest.getPageRequest());
        var result = new PageDto<>(
            page.get().map(KVObjectDto::fromEntity).collect(Collectors.toList()),
            page.getSize(),
            page.getNumber(),
            page.getTotalPages(),
            page.getTotalElements()
        );
        log.info("Response: " + result);
        return result;
    }

    @GetMapping("/kv-object/{id}")
    public KVObjectDto get(@PathVariable Integer id) {
        log.info("Request GET /kv-object/{}", id);
        var kvObject = kvObjectService.get(id);
        if (kvObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "KVObject not found for id= " + id);
        }
        var result = KVObjectDto.fromEntity(kvObject);
        log.info("Response: " + result);
        return result;
    }

    @PostMapping("/kv-object")
    public KVObjectDto post(@Valid @RequestBody KVObjectRequest kvObjectRequest) {
        log.info("Request POST /kv-object kv-object={}", kvObjectRequest);
        var result = KVObjectDto.fromEntity(kvObjectService.save(kvObjectRequest.toEntity()));
        log.info("Response: " + result);
        return result;
    }

    @PutMapping("/kv-object/{id}")
    public KVObjectDto put(@PathVariable Integer id, @Valid @RequestBody KVObjectRequest kvObjectRequest) {
        log.info("Request PATCH /kv-object/{} kv-object={}", kvObjectRequest);
        var oldKVObject = kvObjectService.get(id);
        if (oldKVObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "KVObject not found for id= " + id);
        }
        var kvObject = kvObjectRequest.toEntity();
        kvObject.setId(id);
        kvObject.setDateCreated(oldKVObject.getDateCreated());
        var result = KVObjectDto.fromEntity(kvObjectService.save(kvObject));
        log.info("Response: " + result);
        return result;
    }

    @DeleteMapping("/kv-object/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Request DELETE /kv-object/{}", id);
        var kvObject = kvObjectService.get(id);
        if (kvObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "KVObject not found for id= " + id);
        }
        kvObjectService.delete(id);
        log.info("Response: OK");
    }

}
