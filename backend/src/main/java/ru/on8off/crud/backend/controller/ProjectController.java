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
import ru.on8off.crud.backend.controller.dto.ProjectDto;
import ru.on8off.crud.backend.controller.dto.ProjectRequest;
import ru.on8off.crud.backend.controller.dto.ProjectsFilterRequest;
import ru.on8off.crud.backend.service.ProjectService;
import org.springframework.http.HttpStatusCode;

import java.util.stream.Collectors;

@RestController
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public PageDto<ProjectDto> all(PageRequest pageRequest, ProjectsFilterRequest projectsFilterRequest){
        log.info("Request GET /project pageRequest={} projectsFilterRequest={}", pageRequest, projectsFilterRequest);
        var page = projectService.getAll(projectsFilterRequest.getFilter(), pageRequest.getPageRequest());
        var result = new PageDto<>(
            page.get().map(ProjectDto::fromEntity).collect(Collectors.toList()),
            page.getSize(),
            page.getNumber(),
            page.getTotalPages(),
            page.getTotalElements()
        );
        log.info("Response: " + result);
        return result;
    }

    @GetMapping("/project/{id}")
    public ProjectDto get(@PathVariable Integer id) {
        log.info("Request GET /{}", id);
        var project = projectService.get(id);
        if (project == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Project not found for id= " + id);
        }
        var result = ProjectDto.fromEntity(project);
        log.info("Response: " + result);
        return result;
    }

    @PostMapping("/project")
    public ProjectDto post(@Valid @RequestBody ProjectRequest projectRequest) {
        log.info("Request POST /project project={}", projectRequest);
        var result = ProjectDto.fromEntity(projectService.save(projectRequest.toEntity()));
        log.info("Response: " + result);
        return result;
    }

    @PutMapping("/project/{id}")
    public ProjectDto put(@PathVariable Integer id, @Valid @RequestBody ProjectRequest projectRequest) {
        log.info("Request PATCH /project/{} project={}", projectRequest);
        var oldProject = projectService.get(id);
        if (oldProject == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Project not found for id= " + id);
        }
        var project = projectRequest.toEntity();
        project.setId(id);
        project.setDateCreated(oldProject.getDateCreated());
        var result = ProjectDto.fromEntity(projectService.save(project));
        log.info("Response: " + result);
        return result;
    }

    @DeleteMapping("/project/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Request DELETE /project/{}", id);
        var project = projectService.get(id);
        if (project == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Project not found for id= " + id);
        }
        projectService.delete(id);
        log.info("Response: OK");
    }

}
