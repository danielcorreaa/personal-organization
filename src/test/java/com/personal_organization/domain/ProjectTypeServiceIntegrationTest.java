package com.personal_organization.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.mongo.repository.ProjectTypeMongoRepository;
import com.personal_organization.infrastructure.web.dto.ProjectTypeDto;
import com.personal_organization.infrastructure.web.mapper.ProjectTypeMapper;
import com.personal_organization.infrastructure.web.service.ProjectTypeService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

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
    private ProjectTypeService service;

    @Autowired
    private ProjectTypeMongoRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanAndLoad() throws Exception {
        repository.deleteAll();

        String json = getJson();

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

    private static @NotNull String getJson() {
        return """
                [
                   {
                     "id": "mudanca",
                     "label": "Mudança",
                     "subtitle": "Planejar transição de lar",
                     "icon": "fa-truck-ramp-box",
                     "colorClass": "blue",
                     "titleLabel": "Descrição da Mudança",
                     "active": true,
                     "extraFields": [
                       {
                         "id": "origem",
                         "label": "Origem",
                         "type": "category",
                         "options": [
                           {
                             "id": "origem_imovel",
                             "label": "Origem Imovel"
                           }
                
                         ]
                       },
                       {
                         "id": "destino",
                         "label": "Destino",
                         "type": "category",
                         "options": [
                           {
                             "id": "destino_imovel",
                             "label": "Destino Imovel"
                           }         \s
                         ]
                       }
                     ]
                   },
                
                   {
                     "id": "viagem",
                     "label": "Viagem",
                     "subtitle": "Planejar novo destino",
                     "icon": "fa-plane-departure",
                     "colorClass": "emerald",
                     "titleLabel": "Destino",
                     "active": true,
                
                     "extraFields": [
                       {
                         "id": "gastos",
                         "label": "Categorias de Gastos",
                         "type": "category",
                         "options": [
                           {
                             "id": "lazer",
                             "label": "Lazer"
                           },
                           {
                             "id": "hospedagem",
                             "label": "Hospedagem"
                           },
                           {
                             "id": "transporte",
                             "label": "Transporte"
                           },
                           {
                             "id": "alimentacao",
                             "label": "Alimentação"
                           }
                         ]
                       }
                     ]
                   },
                
                   {
                     "id": "reforma",
                     "label": "Reforma",
                     "subtitle": "Gerenciar obras e melhorias",
                     "icon": "fa-hammer",
                     "colorClass": "orange",
                     "titleLabel": "Cômodo ou Projeto",
                     "active": false,
                
                     "extraFields": [
                       {
                         "id": "gastos",
                         "label": "Tipos de Gastos",
                         "type": "category",
                         "options": [
                           {
                             "id": "material",
                             "label": "Material de Construção"
                           },
                           {
                             "id": "mao_obra",
                             "label": "Mão de Obra"
                           }
                         ]
                       }
                     ]
                   }
                 ]
                """;
    }

}
