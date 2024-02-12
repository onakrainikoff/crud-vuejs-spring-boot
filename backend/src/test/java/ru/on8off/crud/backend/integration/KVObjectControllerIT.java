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
import ru.on8off.crud.backend.controller.dto.KVObjectDto;
import ru.on8off.crud.backend.fixture.KVObjectFixture;
import ru.on8off.crud.backend.repository.KVObjectRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KVObjectControllerIT {
    @LocalServerPort
    private int port;
    private String host = "http://localhost:";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private KVObjectRepository kvObjectRepository;
    private String url;

    @BeforeEach
    public void setup() {
        this.url = host + port;
    }

    @Test
    void testGet() {
        var response = testRestTemplate.getForEntity(url +"/kv-object/" + Integer.MAX_VALUE, KVObjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var kvObjectEntity = kvObjectRepository.save(KVObjectFixture.kvObjectEntity());
        response = testRestTemplate.getForEntity(url +"/kv-object/" + kvObjectEntity.getId(), KVObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var result = response.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(kvObjectEntity.getId(), result.getId());
        Assertions.assertEquals(kvObjectEntity.getKey(), result.getKey());
        Assertions.assertEquals(kvObjectEntity.getValue(), result.getValue());
        Assertions.assertNotNull(kvObjectEntity.getDateCreated());
        Assertions.assertNotNull(kvObjectEntity.getDateUpdated());
        Assertions.assertNotNull(result.getDateCreated());
        Assertions.assertNotNull(result.getDateUpdated());
    }
    
    @Test
    void testDelete() {
        var result = testRestTemplate.exchange(url +"/kv-object/"+ Integer.MAX_VALUE, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(404, result.getStatusCode().value());
        var kvObjectEntity = kvObjectRepository.save(KVObjectFixture.kvObjectEntity());
        result = testRestTemplate.exchange(url +"/kv-object/"+ kvObjectEntity.getId(), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertFalse(kvObjectRepository.findById(kvObjectEntity.getId()).isPresent());
    }

    @Test
    void testPost() {
        var request = KVObjectFixture.kvObjectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/kv-object", HttpMethod.POST, httpEntity, KVObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var kvObject = response.getBody();
        Assertions.assertNotNull(kvObject);
        Assertions.assertNotNull(kvObject.getId());
        Assertions.assertNotNull(kvObject.getDateCreated());
        Assertions.assertNotNull(kvObject.getDateUpdated());
        Assertions.assertEquals(request.getKey(), kvObject.getKey());
        Assertions.assertEquals(request.getValue(), kvObject.getValue());

        request = KVObjectFixture.kvObjectRequest();
        var tooLongKey = IntStream.range(0, 1001).boxed().map(String::valueOf).collect(Collectors.joining(""));
        request.setKey(tooLongKey);
        request.setValue("");
        response = testRestTemplate.postForEntity(url +"/kv-object", request, KVObjectDto.class);
        Assertions.assertEquals(400, response.getStatusCode().value());
    }   

    @Test
    void testPut() {
        var request = KVObjectFixture.kvObjectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/kv-object/"+ Integer.MAX_VALUE, HttpMethod.PUT, httpEntity, KVObjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var kvObjectEntity = kvObjectRepository.save(KVObjectFixture.kvObjectEntity());
        
        response = testRestTemplate.exchange(url +"/kv-object/"+ kvObjectEntity.getId(), HttpMethod.PUT, httpEntity, KVObjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var kvObject = response.getBody();
        Assertions.assertNotNull(kvObject);
        Assertions.assertEquals(kvObjectEntity.getId(), kvObject.getId());
        Assertions.assertEquals(request.getKey(), kvObject.getKey());
        Assertions.assertEquals(request.getValue(), kvObject.getValue());
        
        kvObjectEntity = kvObjectRepository.findById(kvObjectEntity.getId()).orElse(null);
        Assertions.assertNotNull(kvObjectEntity);
        Assertions.assertEquals(request.getKey(), kvObjectEntity.getKey());
        Assertions.assertEquals(request.getValue(), kvObjectEntity.getValue());
    }

    @Test
    void testGetAll(){
        var response = testRestTemplate.getForEntity(url +"/kv-objects", PageDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var kvObjects = List.of(
            KVObjectFixture.kvObjectEntity(),
            KVObjectFixture.kvObjectEntity(),
            KVObjectFixture.kvObjectEntity()
        );
        kvObjectRepository.saveAll(kvObjects);
        response = testRestTemplate.getForEntity(url +"/kv-objects?key=TEST-KEY&pageSize=2&pageNumber=0", PageDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var page = response.getBody();
        Assertions.assertTrue(page.getTotalElements() >= 3);
        Assertions.assertEquals(0, page.getPageNumber());
        Assertions.assertEquals(2, page.getPageSize());
        Assertions.assertEquals(2, page.getContent().size());
    }

}
