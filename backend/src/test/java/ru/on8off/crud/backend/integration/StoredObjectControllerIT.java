package ru.on8off.crud.backend.integration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import ru.on8off.crud.backend.controller.dto.PageDto;
import ru.on8off.crud.backend.controller.dto.StoredObjectDto;
import ru.on8off.crud.backend.fixture.StoredObjectFixture;
import ru.on8off.crud.backend.repository.StoredObjectRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoredObjectControllerIT {
    @LocalServerPort
    private int port;
    private String host = "http://localhost:";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StoredObjectRepository storedObjectRepository;
    private String url;

    @BeforeEach
    public void setup() {
        this.url = host + port;
    }

    @Test
    void testGet() {
        var response = testRestTemplate.getForEntity(url +"/stored-object/" + Integer.MAX_VALUE, StoredObjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var storedObjectEntity = storedObjectRepository.save(StoredObjectFixture.storedObjectEntity());
        response = testRestTemplate.getForEntity(url +"/stored-object/" + storedObjectEntity.getId(), StoredObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var result = response.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(storedObjectEntity.getId(), result.getId());
        Assertions.assertEquals(storedObjectEntity.getName(), result.getName());
        Assertions.assertEquals(storedObjectEntity.getValue(), result.getValue());
        Assertions.assertNotNull(storedObjectEntity.getDateCreated());
        Assertions.assertNotNull(storedObjectEntity.getDateUpdated());
        Assertions.assertNotNull(result.getDateCreated());
        Assertions.assertNotNull(result.getDateUpdated());
    }
    
    @Test
    void testDelete() {
        var result = testRestTemplate.exchange(url +"/stored-object/"+ Integer.MAX_VALUE, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(404, result.getStatusCode().value());
        var storedObjectEntity = storedObjectRepository.save(StoredObjectFixture.storedObjectEntity());
        result = testRestTemplate.exchange(url +"/stored-object/"+ storedObjectEntity.getId(), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertFalse(storedObjectRepository.findById(storedObjectEntity.getId()).isPresent());
    }

    @Test
    void testPost() {
        var request = StoredObjectFixture.storedObjectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/stored-object", HttpMethod.POST, httpEntity, StoredObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var storedObject = response.getBody();
        Assertions.assertNotNull(storedObject);
        Assertions.assertNotNull(storedObject.getId());
        Assertions.assertNotNull(storedObject.getDateCreated());
        Assertions.assertNotNull(storedObject.getDateUpdated());
        Assertions.assertEquals(request.getName(), storedObject.getName());
        Assertions.assertEquals(request.getValue(), storedObject.getValue());

        request = StoredObjectFixture.storedObjectRequest();
        var tooLongKey = IntStream.range(0, 1001).boxed().map(String::valueOf).collect(Collectors.joining(""));
        request.setName(tooLongKey);
        request.setValue("");
        response = testRestTemplate.postForEntity(url +"/stored-object", request, StoredObjectDto.class);
        Assertions.assertEquals(400, response.getStatusCode().value());
    }   

    @Test
    void testPut() {
        var request = StoredObjectFixture.storedObjectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/stored-object/"+ Integer.MAX_VALUE, HttpMethod.PUT, httpEntity, StoredObjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var storedObjectEntity = storedObjectRepository.save(StoredObjectFixture.storedObjectEntity());
        
        response = testRestTemplate.exchange(url +"/stored-object/"+ storedObjectEntity.getId(), HttpMethod.PUT, httpEntity, StoredObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var storedObject = response.getBody();
        Assertions.assertNotNull(storedObject);
        Assertions.assertEquals(storedObjectEntity.getId(), storedObject.getId());
        Assertions.assertEquals(request.getName(), storedObject.getName());
        Assertions.assertEquals(request.getValue(), storedObject.getValue());
        
        storedObjectEntity = storedObjectRepository.findById(storedObjectEntity.getId()).orElse(null);
        Assertions.assertNotNull(storedObjectEntity);
        Assertions.assertEquals(request.getName(), storedObjectEntity.getName());
        Assertions.assertEquals(request.getValue(), storedObjectEntity.getValue());
    }

    @Test
    void testGetAll(){
        var response = testRestTemplate.getForEntity(url +"/stored-objects", PageDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var storedObjects = List.of(
            StoredObjectFixture.storedObjectEntity(),
            StoredObjectFixture.storedObjectEntity(),
            StoredObjectFixture.storedObjectEntity()
        );
        storedObjectRepository.saveAll(storedObjects);
        response = testRestTemplate.getForEntity(url +"/stored-objects?key=TEST-KEY&pageSize=2&pageNumber=0", PageDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var page = response.getBody();
        Assertions.assertTrue(page.getTotalElements() >= 3);
        Assertions.assertEquals(0, page.getPageNumber());
        Assertions.assertEquals(2, page.getPageSize());
        Assertions.assertEquals(2, page.getContent().size());
    }

}
