package com.personal_organization.infrastructure.web.api;

;
import com.personal_organization.domain.valueobject.UserId;
import com.personal_organization.infrastructure.web.dto.ProjectItemCreateDto;
import com.personal_organization.infrastructure.web.dto.ProjectItemResponseDto;
import com.personal_organization.infrastructure.web.dto.UpdateCompletedDto;
import com.personal_organization.infrastructure.web.dto.UpdatePriceDto;
import com.personal_organization.infrastructure.web.mapper.ProjectItemMapper;
import com.personal_organization.infrastructure.web.service.ProjectItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects/items")
@CrossOrigin(origins = "*")
@Tag(name = "Project Items", description = "Gerenciamento de itens do projeto")
public class ProjectItemController {

    private final ProjectItemService service;

    public ProjectItemController(ProjectItemService service) {
        this.service = service;
    }

    @Operation(summary = "Criar item do projeto")
    @PostMapping("/{projectId}")
    public ResponseEntity<ProjectItemResponseDto> create(@PathVariable String projectId,
                                                         @RequestBody ProjectItemCreateDto dto) {
        String userId = getAuthenticatedUserId();
        var response = service.create(projectId, new UserId(userId), dto);
        return ResponseEntity.ok(ProjectItemMapper.toResponse(response));
    }

    @Operation(summary = "Listar itens por projeto")
    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectItemResponseDto>> list(@PathVariable String projectId ) {
        List<ProjectItemResponseDto> list = service.listByProject(projectId).stream().map(ProjectItemMapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Excluir todos os itens do projeto")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable String itemId) {
        service.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar preço do item")
    @PatchMapping("/{itemId}/price")
    public ResponseEntity<ProjectItemResponseDto> updatePrice(@PathVariable String itemId,
                                                              @RequestBody UpdatePriceDto dto) {
        return ResponseEntity.ok(ProjectItemMapper.toResponse(service.updatePrice(itemId, dto)));
    }

    @Operation(summary = "Atualizar status completed do item")
    @PatchMapping("/{itemId}/completed")
    public ResponseEntity<ProjectItemResponseDto> updateCompleted(@PathVariable String itemId,
                                                                  @RequestBody UpdateCompletedDto dto) {
        return ResponseEntity.ok(ProjectItemMapper.toResponse(service.updateCompleted(itemId, dto)));
    }

    private String getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return principal != null ? principal.toString() : "anonymousUser";
    }
}
