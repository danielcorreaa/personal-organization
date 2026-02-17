package com.personal_organization.infrastructure.web.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_organization.TestUtils;
import com.personal_organization.application.exception.NotFoundException;
import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.project.ProjectItem;
import com.personal_organization.domain.valueobject.UserId;
import com.personal_organization.infrastructure.mongo.repository.ProjectMongoRepository;
import com.personal_organization.infrastructure.web.dto.*;
import com.personal_organization.infrastructure.web.mapper.ProjectMapper;
import com.personal_organization.infrastructure.web.mapper.ProjectTypeMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class ProjectItemServiceTest {

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
    ProjectItemService projectItemService;
    @Autowired
    ProjectTypeService projectTypeService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectMongoRepository projectMongoRepository;

    String projectId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() throws Exception {
        projectMongoRepository.deleteAll();
        loadProjectTypes();
    }

    @Test
    @DisplayName("Deve criar um ProjectItem com sucesso e retornar o domínio")
    void create_ShouldReturnProjectItem_WhenSuccessful() {
        UserId userId = new UserId("user-456"); // Ajuste conforme seu objeto UserId
        ProjectItemCreateDto dto = new ProjectItemCreateDto(
                "Novo Item",
                new BigDecimal("150.00"),
                "categoria-id",
                "Descrição do item"
        );

        ProjectItem result = projectItemService.create(projectId, userId, dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(projectId, result.getProjectId().value());
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.value(), result.getValue().getAmount());
    }

    @Test
    void listByProject_ShouldReturnListOfDomainItems() {
        UserId userId = new UserId("user-456"); // Ajuste conforme seu objeto UserId
        ProjectItemCreateDto dto = new ProjectItemCreateDto(
                "Novo Item",
                new BigDecimal("150.00"),
                "categoria-id",
                "Descrição do item"
        );

        projectItemService.create(projectId, userId, dto);

        List<ProjectItem> result = projectItemService.listByProject(projectId);

        assertEquals(1, result.size());
        assertEquals(projectId, result.get(0).getProjectId().value());
    }

    @Test
    void deleteByProject_ShouldInvokeRepositoryDeletion() {

        projectItemService.deleteByProject(projectId);
        List<ProjectItem> result = projectItemService.listByProject(projectId);
        assertEquals(0, result.size());

    }

    @Test
    void updatePrice_ShouldUpdateValueAndReturnDomain() {

        UserId userId = new UserId("user-456"); // Ajuste conforme seu objeto UserId
        ProjectItemCreateDto dtoCreate = new ProjectItemCreateDto(
                "Novo Item",
                new BigDecimal("150.00"),
                "categoria-id",
                "Descrição do item"
        );

        var save = projectItemService.create(projectId, userId, dtoCreate);
        // Arrange
        String itemId = save.getId();

        UpdatePriceDto dtoUpdate = new UpdatePriceDto(new BigDecimal("250.00"));

        ProjectItem result = projectItemService.updatePrice(itemId, dtoUpdate);

        assertEquals(new BigDecimal("250.00"), result.getValue().getAmount());
    }

    @Test
    void updatePrice_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundException.class, () ->
                projectItemService.updatePrice("any", new UpdatePriceDto(BigDecimal.ONE))
        );
    }
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void updateCompleted_ShouldToggleStatus(boolean status) {
        // Arrange
        UserId userId = new UserId("user-456"); // Ajuste conforme seu objeto UserId
        ProjectItemCreateDto dtoCreate = new ProjectItemCreateDto(
                "Novo Item",
                new BigDecimal("150.00"),
                "categoria-id",
                "Descrição do item"
        );

        var save = projectItemService.create(projectId, userId, dtoCreate);
        // Arrange
        String itemId = save.getId();
        UpdateCompletedDto dtoUpdate = new UpdateCompletedDto(status);

        // Act
        ProjectItem result = projectItemService.updateCompleted(itemId, dtoUpdate);

        // Assert
        assertEquals(status, result.isCompleted());
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

        ProjectCreateDto dto = new ProjectCreateDto(
                "mudanca",
                "casa nova",
                LocalDate.of(2026, 2, 12),
                BigDecimal.TEN,
                "ACTIVE"
        );
        Project saved = projectService.create(ProjectMapper.toDomain(dto, "anonymousUser"));

        projectId = saved.getId().toString();

    }

}