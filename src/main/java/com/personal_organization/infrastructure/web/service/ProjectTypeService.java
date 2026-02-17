package com.personal_organization.infrastructure.web.service;

import com.personal_organization.application.exception.NotFoundException;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.mongo.entity.ProjectTypeDocument;
import com.personal_organization.infrastructure.mongo.mapper.ProjectTypeDocumentMapper;
import com.personal_organization.infrastructure.mongo.repository.ProjectTypeMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTypeService {

    private final ProjectTypeMongoRepository mongoRepository;

    public ProjectTypeService(ProjectTypeMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public ProjectType save(ProjectType item) {
        ProjectTypeDocument doc = ProjectTypeDocumentMapper.toDocument(item);
        return ProjectTypeDocumentMapper.toDomain(mongoRepository.save(doc));
    }

    public List<ProjectType> findByProject(ProjectType project) {
        return mongoRepository.findById(project.getId())
                .stream()
                .map(ProjectTypeDocumentMapper::toDomain)
                .toList();
    }
    public void deleteByProject(ProjectType project) {
        mongoRepository.deleteById(project.getId());
    }

    public List<ProjectType>  saveBatch(List<ProjectType> domains) {
        return domains.stream().map(this::save).toList();
    }

    public ProjectType update(String id, ProjectType domain) {
        ProjectTypeDocument toUpdate =   mongoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("ProjectType not found!"));

        Optional.ofNullable(domain.getLabel()).ifPresent(toUpdate::setLabel);
        Optional.ofNullable(domain.getSubtitle()).ifPresent(toUpdate::setSubtitle);
        Optional.ofNullable(domain.getIcon()).ifPresent(toUpdate::setIcon);
        Optional.ofNullable(domain.getColorClass()).ifPresent(toUpdate::setColorClass);
        Optional.ofNullable(domain.getTitleLabel()).ifPresent(toUpdate::setTitleLabel);
        Optional.ofNullable(domain.getActive()).ifPresent(toUpdate::setActive);


        return ProjectTypeDocumentMapper.toDomain(mongoRepository.save(toUpdate));
    }

    public void updateActive(String id, boolean active) {
        ProjectTypeDocument toUpdate = mongoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("ProjectType not found!"));
        toUpdate.setActive(active);
        mongoRepository.save(toUpdate);
    }

    public ProjectType getById(String id) {
        ProjectTypeDocument find =   mongoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("ProjectType not found!"));

        return ProjectTypeDocumentMapper.toDomain(find);
    }

    public List<ProjectType> listAll() {
        List<ProjectTypeDocument> all = mongoRepository.findAll();
        return all.stream().map(ProjectTypeDocumentMapper::toDomain).toList();
    }

    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }

    public ProjectType findByType(String type) {
        return getById(type);
    }
}
