package com.personal_organization.infrastructure.web.api;

import com.personal_organization.domain.project.Project;
import com.personal_organization.infrastructure.web.dto.*;
import com.personal_organization.infrastructure.web.mapper.ProjectItemMapper;
import com.personal_organization.infrastructure.web.mapper.ProjectMapper;
import com.personal_organization.infrastructure.web.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Gerenciamento de projetos do usuário")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo projeto")
    public ResponseEntity<ProjectResponseDto> create( @RequestBody ProjectCreateDto dto ) {
        String userId = getAuthenticatedUserId();
        Project domain = ProjectMapper.toDomain(dto, userId);
        Project response = projectService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectMapper.toResponse(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID")
    public ResponseEntity<ProjectResponseDto> getById(@PathVariable String id) {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(ProjectMapper.toResponse(projectService.getById(id, userId)));
    }


    @GetMapping("")
    @Operation(summary = "Listar projetos do usuário")
    public ResponseEntity<List<ProjectResponseDto>> list(@RequestParam String status) {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(projectService.
                listByUserAndActive(userId, status).stream().map(ProjectMapper::toResponse).toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar projeto")
    public ResponseEntity<ProjectResponseDto> update( @PathVariable String id,
            @RequestBody ProjectCreateDto dto ) {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(ProjectMapper
                .toResponse(projectService.update(id, ProjectMapper.toDomain(dto, userId))));
    }

    @Operation(summary = "Atualizar Statys")
    @PatchMapping("/{projectId}/status")
    public ResponseEntity<ProjectResponseDto> updateStatus(@PathVariable String projectId,
                                                              @RequestBody UpdateStatusProjectDto dto) {
        String userId = getAuthenticatedUserId();
        return ResponseEntity.ok(ProjectMapper.toResponse(projectService.updateStatus(projectId, dto, userId)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover projeto")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return principal != null ? principal.toString() : "anonymousUser";
    }
}

