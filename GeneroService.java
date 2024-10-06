package br.com.fujideia.iesp.tecback.service;

import br.com.fujideia.iesp.tecback.model.Genero;
import br.com.fujideia.iesp.tecback.model.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class GeneroService {

    private final GeneroRepository generoRepository;

    public List<GeneroDTO> listarTodos() {
        return generoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<GeneroDTO> buscarPorId(Long id) {
        return generoRepository.findById(id)
                .map(this::convertToDTO);
    }

    public GeneroDTO criarGenero(@Valid GeneroDTO generoDTO) {
        Genero genero = convertToEntity(generoDTO);
        return convertToDTO(generoRepository.save(genero));
    }

    public Optional<GeneroDTO> atualizarGenero(Long id, @Valid GeneroDTO generoDTO) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNome(generoDTO.getDescricao());
            return convertToDTO(generoRepository.save(genero));
        });
    }

    public boolean deletarGenero(Long id) {
        if (generoRepository.existsById(id)) {
            generoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private GeneroDTO convertToDTO(Genero genero) {
        return new GeneroDTO(genero.getId(), genero.getNome());
    }

    private Genero convertToEntity(GeneroDTO generoDTO) {
        Genero genero = new Genero();
        genero.setId(generoDTO.getId());
        genero.setNome(generoDTO.getDescricao());
        return genero;
    }
}
