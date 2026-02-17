package com.personal_organization.infrastructure.web.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_organization.MongoTestConfig;
import com.personal_organization.TestUtils;
import com.personal_organization.domain.project.Project;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.mongo.repository.ProjectTypeMongoRepository;
import com.personal_organization.infrastructure.web.dto.ProjectCreateDto;
import com.personal_organization.infrastructure.web.dto.ProjectTypeDto;
import com.personal_organization.infrastructure.web.mapper.ProjectMapper;
import com.personal_organization.infrastructure.web.mapper.ProjectTypeMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class ProjectTypeServiceIT {

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
    ProjectTypeService service;

    @Autowired
    ProjectTypeMongoRepository repository;

    final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanAndLoad() throws Exception {
        repository.deleteAll();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("project-types.json");
        String json = TestUtils.toString(is);

        List<ProjectTypeDto> list = objectMapper.readValue(
                json,
                new TypeReference<List<ProjectTypeDto>>() {}
        );
        var all = list.stream().map(ProjectTypeMapper::toDomain).toList();
        service.saveBatch(all);

    }


    @Test
    void shouldListAll() {
        List<ProjectType> all = service.listAll();
        assertThat(all).hasSize(3);
    }

    @Test
    void shouldFindById() {
        ProjectType mudanca = service.getById("mudanca");
        assertThat(mudanca.getLabel()).isEqualTo("Mudança");
    }

    @Test
    void shouldUpdatePartial() {

        ProjectType update = new ProjectType(
                null,
                "Mudança Atualizada",
                null,
                null,
                null,
                null,
                null,
                null
        );

        ProjectType updated = service.update("mudanca", update);

        assertThat(updated.getLabel()).isEqualTo("Mudança Atualizada");
        assertThat(updated.getSubtitle()).isEqualTo("Planejar transição de lar");
    }

    @Test
    void shouldDeleteById() {
        service.deleteById("viagem");
        assertThat(service.listAll()).hasSize(2);
    }

    @Test
    void shouldUpdateActive() {
        service.updateActive("reforma", true);
        ProjectType reforma = service.getById("reforma");
        assertThat(reforma.getActive()).isTrue();
    }
}
