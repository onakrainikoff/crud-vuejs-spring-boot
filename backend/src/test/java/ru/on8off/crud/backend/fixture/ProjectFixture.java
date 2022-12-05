package ru.on8off.crud.backend.fixture;

import ru.on8off.crud.backend.controller.dto.ProjectDto;
import ru.on8off.crud.backend.controller.dto.ProjectRequest;
import ru.on8off.crud.backend.repository.ProjectRepository;
import ru.on8off.crud.backend.repository.entity.Project;


import java.util.Random;

public class ProjectFixture {
    private static final Random random = new Random();
    

    public static Project projectEntity(){
        var entity = new Project();
        entity.setCode("TEST-CODE-" + random.nextInt());
        entity.setName("TEST-NAME-" + random.nextInt());
        entity.setColor("#000000");
        entity.setDescription("TEST-DESCRIPRION-" + random.nextInt());
        return entity;
    }

    public static ProjectDto projectDto(){
        return ProjectDto.fromEntity(projectEntity());
    }

    public static ProjectRequest projectRequest(){
        var request = new ProjectRequest();
        request.setCode("TEST-CODE-" + random.nextInt());
        request.setName("TEST-NAME-" + random.nextInt());
        request.setColor("#000000");
        request.setDescription("TEST-DESCRIPRION-" + random.nextInt());
        return request;
    }

}
