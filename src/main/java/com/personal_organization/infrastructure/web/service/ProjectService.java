package com.personal_organization.infrastructure.web.service;

import com.personal_organization.application.exception.NotFoundException;
import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.project.ProjectItem;
import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.mongo.entity.ProjectDocument;
import com.personal_organization.infrastructure.mongo.mapper.ProjectDocumentMapper;
import com.personal_organization.infrastructure.mongo.repository.ProjectMongoRepository;
import com.personal_organization.infrastructure.web.dto.UpdateStatusProjectDto;
import com.personal_organization.infrastructure.web.mapper.ProjectItemMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectMongoRepository repository;
    private final ProjectTypeService projectTypeService;
    public ProjectService(ProjectMongoRepository repository, ProjectTypeService projectTypeService) {
        this.repository = repository;
        this.projectTypeService = projectTypeService;
    }

    public Project create(Project project) {
        ProjectType byType = projectTypeService.findByType(project.getType());
        project.getSelectedCategories().addAll(
                byType.getExtraFields().stream()
                        .flatMap(extraField -> extraField.getOptions().stream()
                                .map(categoryOption ->
                                        new SelectedCategory(
                                                categoryOption.getId(),
                                                categoryOption.getLabel()))
                        )
                        .collect(Collectors.toCollection(ArrayList::new))
        );

        ProjectDocument projectDocument = ProjectDocumentMapper.toDocument(project);


        ProjectDocument save = repository.save(projectDocument);
        return ProjectDocumentMapper.toDomain(save);
    }

    public Project getById(String id, String userId) {
        return ProjectDocumentMapper.toDomain(repository.findByIdAndUserId(id, userId).orElseThrow(() ->
                    new NotFoundException("Project not found")));
    }
    

    public List<Project> listByUserAndActive(String userId, String status) {
        return repository.findAllByUserIdAndStatus(userId, status)
                .stream().map(ProjectDocumentMapper::toDomain).toList();
    }

    public Project update(String id, Project project) {
        ProjectDocument projectDocument =  repository.findByIdAndUserId(id, project.getUserId().value()).orElseThrow(() ->
                new NotFoundException("Project not found"));
        Optional.ofNullable(project.getDate()).ifPresent(projectDocument::setDate);
        Optional.ofNullable(project.getBudget()).ifPresent(budget ->
                projectDocument.setBudget(budget.getAmount()));
        Optional.ofNullable(project.getStatus()).ifPresent(status ->
                projectDocument.setStatus(status.toString()));
        projectDocument.setSelectedCategories(new ArrayList<>());
        Optional.ofNullable(project.getTitle()).ifPresent(projectDocument::setTitle);


        return create(ProjectDocumentMapper.toDomain(projectDocument));
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }


    public Project updateStatus(String projectId, UpdateStatusProjectDto dto, String userId) {
        Project byId = getById(projectId, userId);
        byId.changeStatus(dto.status());
        var resp =  ProjectDocumentMapper.toDocument(byId);
        repository.save(resp);
        return ProjectDocumentMapper.toDomain(resp);
    }
}
