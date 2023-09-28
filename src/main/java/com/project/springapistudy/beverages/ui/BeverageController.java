package com.project.springapistudy.beverages.ui;

import com.project.springapistudy.beverages.application.BeverageService;
import com.project.springapistudy.beverages.application.dto.BeverageCreateRequest;
import com.project.springapistudy.beverages.application.dto.BeverageRetrieveResponse;
import com.project.springapistudy.beverages.application.dto.BeverageUpdateRequest;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/beverage")
@RequiredArgsConstructor
public class BeverageController {

    private final BeverageService beverageService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid BeverageCreateRequest request) {
        var response = beverageService.create(request);

        String currentPathUri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        UriComponents responseUri = UriComponentsBuilder
                .fromUriString(currentPathUri)
                .path("/" + response.getId())
                .build();

        return ResponseEntity.created(responseUri.toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeverageRetrieveResponse> retrieve(@PathVariable("id") final Long id) {
        var response = beverageService.retrieve(id);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") final long id, @RequestBody @Valid BeverageUpdateRequest request) {
        beverageService.update(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        beverageService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
