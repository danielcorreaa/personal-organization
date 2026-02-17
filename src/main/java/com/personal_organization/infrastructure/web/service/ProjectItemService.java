package com.personal_organization.infrastructure.web.service;

import com.personal_organization.application.exception.NotFoundException;
import com.personal_organization.domain.project.ProjectItem;
import com.personal_organization.domain.valueobject.UserId;
import com.personal_organization.infrastructure.mongo.entity.ProjectItemDocument;
import com.personal_organization.infrastructure.mongo.mapper.ProjectItemDocumentMapper;
import com.personal_organization.infrastructure.mongo.repository.ProjectItemMongoRepository;
import com.personal_organization.infrastructure.web.dto.ProjectItemCreateDto;
import com.personal_organization.infrastructure.web.dto.UpdateCompletedDto;
import com.personal_organization.infrastructure.web.dto.UpdatePriceDto;
import com.personal_organization.infrastructure.web.mapper.ProjectItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectItemService {

    private final ProjectItemMongoRepository repository;

    public ProjectItemService(ProjectItemMongoRepository repository) {
        this.repository = repository;
    }

    public ProjectItem create(String projectId, UserId userId,ProjectItemCreateDto dto) {
        ProjectItem domain = ProjectItemMapper.toDomain(projectId, userId, dto);
        ProjectItemDocument document = ProjectItemDocumentMapper.toDocument(domain);

        ProjectItemDocument save = repository.save(document);

        return ProjectItemDocumentMapper.toDomain(save);
    }

    public List<ProjectItem> listByProject(String projectId) {
        return repository.findByProjectId(projectId)
                .stream()
                .map(ProjectItemDocumentMapper::toDomain)
                .toList();
    }

    public void deleteByProject(String projectId) {
        repository.deleteAllByProjectId(projectId);
    }

    public ProjectItem updatePrice(String itemId, UpdatePriceDto dto) {

        var document = repository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        var projectItem =  ProjectItemDocumentMapper.toDomain(document);

        projectItem.changeValue(dto.value());

        ProjectItemDocument response = repository.save(ProjectItemDocumentMapper.toDocument(projectItem));

        return ProjectItemDocumentMapper.toDomain(response);
    }

    public ProjectItem updateCompleted(String itemId, UpdateCompletedDto dto) {

        var document = repository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        var projectItem =  ProjectItemDocumentMapper.toDomain(document);

        if(dto.completed()){
            projectItem.markAsCompleted();
        } else {
            projectItem.markAsNoCompleted();
        }
        ProjectItemDocument response = repository.save(ProjectItemDocumentMapper.toDocument(projectItem));

        return ProjectItemDocumentMapper.toDomain(response);
    }


    public void deleteById(String itemId) {
        repository.deleteById(itemId);
    }
}
