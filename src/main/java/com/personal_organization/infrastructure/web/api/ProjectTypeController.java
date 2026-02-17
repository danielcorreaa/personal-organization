package com.personal_organization.infrastructure.web.api;
import com.personal_organization.domain.projecttype.ProjectType;
import com.personal_organization.infrastructure.web.dto.ProjectTypeDto;
import com.personal_organization.infrastructure.web.mapper.ProjectTypeMapper;
import com.personal_organization.infrastructure.web.service.ProjectTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/project-types")
@Tag(
        name = "Project Types",
        description = "Gerenciamento dos tipos de projeto (configurações globais)"
)
public class ProjectTypeController {

    private final ProjectTypeService service;

    public ProjectTypeController(ProjectTypeService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar ProjectType",
            description = "Cria um novo tipo de projeto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "ProjectType criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProjectTypeDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProjectTypeDto> create(
            @RequestBody ProjectTypeDto dto
    ) {
        ProjectType domain = ProjectTypeMapper.toDomain(dto);
        ProjectType saved = service.save(domain);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProjectTypeMapper.toDto(saved));
    }

    @Operation(
            summary = "Salvar ProjectTypes em lote",
            description = "Cria ou atualiza múltiplos ProjectTypes de uma vez"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Batch salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/batch")
    public ResponseEntity<Void> saveBatch(
            @RequestBody List<ProjectTypeDto> dtos
    ) {
        List<ProjectType> domains = dtos.stream()
                .map(ProjectTypeMapper::toDomain)
                .toList();

        service.saveBatch(domains);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Atualizar ProjectType",
            description = "Atualiza todas as informações de um ProjectType existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProjectType atualizado",
                    content = @Content(schema = @Schema(implementation = ProjectTypeDto.class))),
            @ApiResponse(responseCode = "404", description = "ProjectType não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectTypeDto> update(
            @PathVariable String id,
            @RequestBody ProjectTypeDto dto
    ) {
        ProjectType domain = ProjectTypeMapper.toDomain(dto);
        ProjectType updated = service.update(id, domain);

        return ResponseEntity.ok(ProjectTypeMapper.toDto(updated));
    }

    @Operation(
            summary = "Ativar / Desativar ProjectType",
            description = "Atualiza apenas o campo active do ProjectType"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status atualizado"),
            @ApiResponse(responseCode = "404", description = "ProjectType não encontrado")
    })
    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> updateActive(
            @PathVariable String id,
            @RequestParam boolean active
    ) {
        service.updateActive(id, active);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Buscar ProjectType por ID",
            description = "Retorna um ProjectType específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ProjectType encontrado",
                    content = @Content(schema = @Schema(implementation = ProjectTypeDto.class))),
            @ApiResponse(responseCode = "404", description = "ProjectType não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectTypeDto> getById(
            @PathVariable String id
    ) {
        ProjectType projectType = service.getById(id);
        return ResponseEntity.ok(ProjectTypeMapper.toDto(projectType));
    }


    @Operation(
            summary = "Listar ProjectTypes",
            description = "Lista todos os tipos de projeto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProjectTypeDto>> listAll() {
        List<ProjectTypeDto> result = service.listAll().stream()
                .map(ProjectTypeMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }


    @Operation(
            summary = "Remover ProjectType",
            description = "Remove um ProjectType pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "ProjectType removido"),
            @ApiResponse(responseCode = "404", description = "ProjectType não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
