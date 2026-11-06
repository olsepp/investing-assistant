package com.investing.assistant.holding;

import com.investing.assistant.holding.dto.CreateHoldingRequest;
import com.investing.assistant.holding.dto.HoldingResponse;
import com.investing.assistant.holding.dto.UpdateHoldingRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/** HTTP endpoints for holdings. Only translates between HTTP and the service; no business logic here. */
@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping
    public List<HoldingResponse> list() {
        return holdingService.findAll();
    }

    @GetMapping("/{id}")
    public HoldingResponse get(@PathVariable Long id) {
        return holdingService.findById(id);
    }

    /** Returns 201 Created with a Location header pointing to the new holding. */
    @PostMapping
    public ResponseEntity<HoldingResponse> create(@Valid @RequestBody CreateHoldingRequest request) {
        HoldingResponse created = holdingService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public HoldingResponse update(@PathVariable Long id, @Valid @RequestBody UpdateHoldingRequest request) {
        return holdingService.update(id, request);
    }

    /** Returns 204 No Content. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        holdingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
