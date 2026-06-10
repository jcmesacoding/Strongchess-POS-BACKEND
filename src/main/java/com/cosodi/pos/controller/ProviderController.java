package com.cosodi.pos.controller;

import com.cosodi.pos.dto.ProviderDTO;
import com.cosodi.pos.entity.Provider;
import com.cosodi.pos.service.IProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final IProviderService providerService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> findAll() {

        List<ProviderDTO> providers =
                providerService.findAll()
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(providers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                convertToDTO(providerService.findById(id))
        );
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @Valid @RequestBody ProviderDTO dto) {

        Provider provider =
                providerService.save(
                        convertToEntity(dto));

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(provider.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderDTO dto) {

        dto.setId(id);

        return ResponseEntity.ok(
                convertToDTO(
                        providerService.update(
                                convertToEntity(dto), id
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        providerService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private ProviderDTO convertToDTO(Provider provider) {
        return modelMapper.map(provider, ProviderDTO.class);
    }

    private Provider convertToEntity(ProviderDTO dto) {
        return modelMapper.map(dto, Provider.class);
    }
}