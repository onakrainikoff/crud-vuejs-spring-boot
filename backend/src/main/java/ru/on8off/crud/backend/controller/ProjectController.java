package ru.on8off.crud.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.on8off.crud.backend.controller.dto.ProjectDto;
import ru.on8off.crud.backend.repository.filter.ProjectsFilter;
import ru.on8off.crud.backend.service.ProjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    private static Integer PAGE_SIZE = 50;

    @GetMapping
    public List<ProjectDto> all(
        @RequestParam(required = false) Integer pageNumber,
        @RequestParam(required = false) Integer pageSize,
        
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String sortOrder,

        @RequestParam(required = false) String code,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) ZonedDateTime dateCreatedFrom,
        @RequestParam(required = false) ZonedDateTime dateCreatedTo,
        @RequestParam(required = false) ZonedDateTime dateUpdatedFrom,
        @RequestParam(required = false) ZonedDateTime dateUpdatedTo
    ){
        log.info(
            "Request GET /?pageNumber={}&pageSize={}&sortBy={}&sortOrder{}&code={}&name={}&dateCreatedFrom={}&dateCreatedTo={}&dateUpdatedFrom={}&dateUpdatedTo={}",
            pageNumber,
            pageSize,
            sortBy,
            sortOrder,
            code,
            name,
            dateCreatedFrom,
            dateCreatedTo,
            dateUpdatedFrom,
            dateUpdatedTo
        );
        var filter = new ProjectsFilter(code, name, dateCreatedFrom, dateCreatedTo, dateUpdatedFrom, dateUpdatedTo);
        
        pageNumber = Objects.requireNonNullElse(pageNumber, 0);
        pageSize= Objects.requireNonNullElse(pageSize, PAGE_SIZE);
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        
        if(sortBy != null && !sortBy.isEmpty()){
            var direction = "DESC".equals(sortOrder) ? Direction.DESC : Direction.ASC;
            pageRequest = pageRequest.withSort(direction, sortBy);
        }
        
        var result = projectService.getAll(filter, pageRequest).stream().map(ProjectDto::fromEntity).collect(Collectors.toList());
        log.info("Response: " + result);
        return result;
    }

    @GetMapping("/{id}")
    public ProjectDto get(@PathVariable Integer id){
        log.info("Request GET /{}", id);
        var result = ProjectDto.fromEntity(projectService.get(id));
        log.info("Response: " + result);
        return result;
    }


    @PostMapping
    public ProjectDto post(@RequestBody ProjectDto projectDto){
        log.info("Request POST / projectDto={}", projectDto);
        var result = ProjectDto.fromEntity(projectService.save(projectDto.toEntity(projectDto)));
        log.info("Response: " + result);
        return result;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        log.info("Request DELETE /{}", id);
        projectService.delete(id);
        log.info("Response: OK");
    }

}
