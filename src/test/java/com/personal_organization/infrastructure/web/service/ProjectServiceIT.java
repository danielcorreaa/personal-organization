package com.personal_organization.infrastructure.web.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_organization.TestUtils;
import com.personal_organization.application.exception.NotFoundException;
import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.project.ProjectStatus;
import com.personal_organization.domain.project.SelectedCategory;
import com.personal_organization.infrastructure.mongo.repository.ProjectMongoRepository;
import com.personal_organization.infrastructure.mongo.repository.ProjectTypeMongoRepository;
import com.personal_organization.infrastructure.web.dto.ProjectCreateDto;
import com.personal_organization.infrastructure.web.dto.ProjectTypeDto;
import com.personal_organization.infrastructure.web.mapper.ProjectMapper;
import com.personal_organization.infrastructure.web.mapper.ProjectTypeMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class ProjectServiceIT {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:6.0.2"));

    @DynamicPropertySource
    static void overrideMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @BeforeAll
    static void setUp() {
        mongoDBContainer.withReuse(true);
        mongoDBContainer.start();
    }

    @AfterAll
    static void tearDown() {
        mongoDBContainer.stop();
    }

    @Autowired
    ProjectTypeService projectTypeService;


    @Autowired
    ProjectTypeMongoRepository projectTypeMongoRepository;

    final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    ProjectMongoRepository projectRepository;
    @Autowired
    ProjectService projectService;


    @BeforeEach
    void cleanAndLoad() throws Exception {
        projectTypeMongoRepository.deleteAll();
        projectRepository.deleteAll();
        loadProjectTypes();
    }


    private void loadProjectTypes() throws Exception {

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("project-types.json");
        String json = TestUtils.toString(is);
        List<ProjectTypeDto> list = objectMapper.readValue(
                json,
                new TypeReference<List<ProjectTypeDto>>() {}
        );
        var all = list.stream().map(ProjectTypeMapper::toDomain).toList();
        projectTypeService.saveBatch(all);
    }

    @Test
    void shouldCreateProjectAndPopulateCategories() {

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "casa nova",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );
        Project saved = projectService.create(ProjectMapper.toDomain(dto, "anonymousUser"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUserId().value()).isEqualTo("anonymousUser");
        assertThat(saved.getType()).isEqualTo("mudanca");
        assertThat(saved.getTitle()).isEqualTo("casa nova");
        assertThat(saved.getDate()).isEqualTo(LocalDate.of(2026, 2, 12));
        assertThat(saved.getStatus().toString()).isEqualTo("ACTIVE");
        assertThat(saved.getBudget().getAmount()).isEqualTo(BigDecimal.TEN);

        assertThat(saved.getSelectedCategories())
                .extracting(SelectedCategory::getId)
                .containsExactlyInAnyOrder("origem_imovel", "destino_imovel");
    }

    @Test
    void shouldUpdateProjectFields() {

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "casa nova",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );
        Project saved = projectService.create(ProjectMapper.toDomain(dto, "anonymousUser"));

        ProjectCreateDto updateDto = new ProjectCreateDto(
                "mudanca",
                "casa atualizada",
                LocalDate.of(2026, 2, 15),
                BigDecimal.valueOf(50),
                "FINISHED"
        );

        Project updated = projectService.update(saved.getId().value(),
                ProjectMapper.toDomain(updateDto, "anonymousUser"));

        assertThat(updated.getId()).isEqualTo(saved.getId());

        assertThat(updated.getTitle()).isEqualTo("casa atualizada");
        assertThat(updated.getDate()).isEqualTo(LocalDate.of(2026, 2, 15));
        assertThat(updated.getStatus()).isEqualTo(ProjectStatus.FINISHED);
        assertThat(updated.getBudget().getAmount()).isEqualTo(BigDecimal.valueOf(50));

        assertThat(updated.getSelectedCategories())
                .hasSize(2)
                .extracting(SelectedCategory::getId)
                .containsExactlyInAnyOrder("origem_imovel", "destino_imovel");
    }

    @Test
    void shouldFindProjectByIdAndUserId() {

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "casa nova",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );
        Project saved = projectService.create(ProjectMapper.toDomain(dto, "anonymousUser"));

        Project found = projectService.getById(saved.getId().value(),"anonymousUser");
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getTitle()).isEqualTo("casa nova");
        assertThat(found.getUserId().value()).isEqualTo("anonymousUser");
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFound() {

        assertThatThrownBy(() ->
                projectService.getById("id-inexistente", "anonymousUser")
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Project not found");
    }

    @Test
    void shouldListProjectsByUserAndStatus() {

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "ativo 1",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );

       projectService.create(ProjectMapper.toDomain(dto, "dani-test"));

        ProjectCreateDto dto2 = new ProjectCreateDto(
                "mudanca",
                "ativo 2",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );

        projectService.create(ProjectMapper.toDomain(dto2, "dani-test"));
        var result = projectService.listByUserAndActive("dani-test", "ACTIVE");

        // ---------- Assert ----------
        assertThat(result)
                .hasSize(2)
                .extracting(Project::getTitle)
                .containsExactlyInAnyOrder("ativo 1", "ativo 2");
    }

    @Test
    void shouldDeleteProject() {

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "ativo 1",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );

        var created = projectService.create(ProjectMapper.toDomain(dto, "anonymousUser"));

        projectService.deleteById(created.getId().value());


        assertThatThrownBy(() ->
                projectService.getById(created.getId().value(), "anonymousUser")
        ).isInstanceOf(NotFoundException.class);
    }


}
