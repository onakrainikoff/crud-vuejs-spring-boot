package ru.on8off.crud.backend.integration;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import ru.on8off.crud.backend.controller.dto.ProjectDto;
import ru.on8off.crud.backend.fixture.ProjectFixture;
import ru.on8off.crud.backend.repository.ProjectRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerIT {
    @LocalServerPort
    private int port;
    private String host = "http://localhost:";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProjectRepository projectRepository;
    private String url;

    @BeforeEach
    public void setup() {
        this.url = host + port;
    }

    @Test
    void testGet() {
        var response = testRestTemplate.getForEntity(url +"/project/" + Integer.MAX_VALUE, ProjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var projectEntity = projectRepository.save(ProjectFixture.projectEntity());
        response = testRestTemplate.getForEntity(url +"/project/" + projectEntity.getId(), ProjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var result = response.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(projectEntity.getId(), result.getId());
        Assertions.assertEquals(projectEntity.getCode(), result.getCode());
        Assertions.assertEquals(projectEntity.getColor(), result.getColor());
        Assertions.assertEquals(projectEntity.getName(), result.getName());
        Assertions.assertEquals(projectEntity.getDescription(), result.getDescription());
        Assertions.assertEquals(projectEntity.getDateCreated(), result.getDateCreated().withZoneSameInstant(projectEntity.getDateCreated().getZone()));
        Assertions.assertEquals(projectEntity.getDateUpdated(), result.getDateUpdated().withZoneSameInstant(projectEntity.getDateUpdated().getZone()));
    }
    
    @Test
    void testDelete() {
        var result = testRestTemplate.exchange(url +"/project/"+ Integer.MAX_VALUE, HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(404, result.getStatusCode().value());
        var projectEntity = projectRepository.save(ProjectFixture.projectEntity());
        result = testRestTemplate.exchange(url +"/project/"+ projectEntity.getId(), HttpMethod.DELETE, null, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertFalse(projectRepository.findById(projectEntity.getId()).isPresent());
    }

    @Test
    void testPost() {
        var request = ProjectFixture.projectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/project", HttpMethod.POST, httpEntity, ProjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var project = response.getBody();
        Assertions.assertNotNull(project);
        Assertions.assertNotNull(project.getId());
        Assertions.assertNotNull(project.getDateCreated());
        Assertions.assertNotNull(project.getDateUpdated());
        Assertions.assertEquals(request.getCode(), project.getCode());
        Assertions.assertEquals(request.getColor(), project.getColor());
        Assertions.assertEquals(request.getName(), project.getName());
        Assertions.assertEquals(request.getDescription(), project.getDescription());

        request = ProjectFixture.projectRequest();
        request.setColor("test");
        request.setCode("0123456901234569012345690123456901234569");
        request.setName("");
        response = testRestTemplate.postForEntity(url +"/project", request, ProjectDto.class);
        Assertions.assertEquals(400, response.getStatusCode().value());
    }   

    @Test
    void testPut() {
        var request = ProjectFixture.projectRequest();
        var httpEntity = new HttpEntity<>(request);
        var response = testRestTemplate.exchange(url +"/project/"+ Integer.MAX_VALUE, HttpMethod.PUT, httpEntity, ProjectDto.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
        var projectEntity = projectRepository.save(ProjectFixture.projectEntity());
        
        response = testRestTemplate.exchange(url +"/project/"+ projectEntity.getId(), HttpMethod.PUT, httpEntity, ProjectDto.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var project = response.getBody();
        Assertions.assertNotNull(project);
        Assertions.assertEquals(projectEntity.getId(), project.getId());
        Assertions.assertEquals(request.getCode(), project.getCode());
        Assertions.assertEquals(request.getColor(), project.getColor());
        Assertions.assertEquals(request.getName(), project.getName());
        Assertions.assertEquals(request.getDescription(), project.getDescription());
        
        projectEntity = projectRepository.findById(projectEntity.getId()).orElse(null);
        Assertions.assertNotNull(projectEntity);
        Assertions.assertEquals(request.getCode(), projectEntity.getCode());
        Assertions.assertEquals(request.getColor(), projectEntity.getColor());
        Assertions.assertEquals(request.getName(), projectEntity.getName());
        Assertions.assertEquals(request.getDescription(), projectEntity.getDescription());
    }

    @Test
    void testGetAll(){
        var response = testRestTemplate.getForEntity(url +"/projects", ProjectDto[].class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        
        var projects = List.of(
            ProjectFixture.projectEntity(),
            ProjectFixture.projectEntity(),
            ProjectFixture.projectEntity()
        );
        projectRepository.saveAll(projects);
        response = testRestTemplate.getForEntity(url +"/projects?name=TEST-NAME", ProjectDto[].class);
        Assertions.assertEquals(200, response.getStatusCode().value());
        var result = List.of(response.getBody());
        Assertions.assertEquals(3, result.size());
    }

}
