package ru.on8off.crud.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.on8off.crud.backend.repository.ProjectRepository;
import ru.on8off.crud.backend.repository.entity.Project;
import ru.on8off.crud.backend.repository.filter.ProjectsFilter;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public Page<Project> getAll(ProjectsFilter filter, PageRequest pageRequest){
        return projectRepository.findAll(filter, pageRequest);
    }

    @Transactional(readOnly = true)
    public Project get(Integer id){
        return projectRepository.getReferenceById(id);
    }

    @Transactional
    public Project save(Project element){
        return projectRepository.save(element);
    }

    @Transactional
    public void delete(Integer id){
        projectRepository.deleteById(id);
    }
}
