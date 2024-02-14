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
import ru.on8off.crud.backend.controller.dto.PageRequestDto;
import ru.on8off.crud.backend.controller.dto.StoredObjectDto;
import ru.on8off.crud.backend.controller.dto.StoredObjectRequestDto;
import ru.on8off.crud.backend.controller.dto.StoredObjectFilterRequestDto;
import ru.on8off.crud.backend.service.StoredObjectService;
import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;

@RestController
@Slf4j
public class StoredObjectController {
    @Autowired
    private StoredObjectService storedObjectService;

    @GetMapping("/stored-objects")
    public PageDto<StoredObjectDto> all(PageRequestDto pageRequest, StoredObjectFilterRequestDto storedObjectFilterRequest){
        log.info("Request GET /stored-object pageRequest={} storedObjectFilterRequest={}", pageRequest, storedObjectFilterRequest);
        var page = storedObjectService.getAll(storedObjectFilterRequest.toStoredObjectFilter(), pageRequest.toPageRequest());
        var result = PageDto.fromPage(page, StoredObjectDto::fromEntity);
        log.info("Response: " + result);
        return result;
    }

    @GetMapping("/stored-object/{id}")
    public StoredObjectDto get(@PathVariable Integer id) {
        log.info("Request GET /stored-object/{}", id);
        var storedObject = storedObjectService.get(id);
        if (storedObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "StoredObject not found for id= " + id);
        }
        var result = StoredObjectDto.fromEntity(storedObject);
        log.info("Response: " + result);
        return result;
    }

    @PostMapping("/stored-object")
    public StoredObjectDto post(@Valid @RequestBody StoredObjectRequestDto storedObjectRequest) {
        log.info("Request POST /stored-object stored-object={}", storedObjectRequest);
        var result = StoredObjectDto.fromEntity(storedObjectService.save(storedObjectRequest.toEntity()));
        log.info("Response: " + result);
        return result;
    }

    @PutMapping("/stored-object/{id}")
    public StoredObjectDto put(@PathVariable Integer id, @Valid @RequestBody StoredObjectRequestDto storedObjectRequest) {
        log.info("Request PUT /stored-object/{} stored-object={}", storedObjectRequest);
        var oldStoredObject = storedObjectService.get(id);
        if (oldStoredObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "StoredObject not found for id= " + id);
        }
        var storedObject = storedObjectRequest.toEntity();
        storedObject.setId(id);
        storedObject.setDateCreated(oldStoredObject.getDateCreated());
        storedObject.setDateUpdated(ZonedDateTime.now());
        var result = StoredObjectDto.fromEntity(storedObjectService.save(storedObject));
        log.info("Response: " + result);
        return result;
    }

    @DeleteMapping("/stored-object/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Request DELETE /stored-object/{}", id);
        var storedObject = storedObjectService.get(id);
        if (storedObject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "StoredObject not found for id= " + id);
        }
        storedObjectService.delete(id);
        log.info("Response: OK");
    }

}
