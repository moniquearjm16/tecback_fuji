package br.com.fujideia.iesp.tecback.controller;

import br.com.fujideia.iesp.tecback.model.dto.DiretorDTO;
import br.com.fujideia.iesp.tecback.service.DiretorService; // Supondo que você tenha criado DiretorService
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diretores")
@RequiredArgsConstructor
@Slf4j
public class DiretorController {

    private final DiretorService diretorService; // Serviço para Diretor

    @GetMapping
    public ResponseEntity<List<DiretorDTO>> listarTodos() {
        log.info("Chamando listarTodos no DiretorController");
        List<DiretorDTO> diretores = diretorService.listarTodos();
        return ResponseEntity.ok(diretores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiretorDTO> buscarPorId(@PathVariable Long id) {
        log.info("Chamando buscarPorId no DiretorController com id: {}", id);
        Optional<DiretorDTO> diretor = diretorService.buscarPorId(id);
        return diretor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DiretorDTO> criarDiretor(@RequestBody DiretorDTO diretorDTO) {
        log.info("Chamando criarDiretor no DiretorController com dados: {}", diretorDTO);
        DiretorDTO diretorCriado = diretorService.criarDiretor(diretorDTO);
        return ResponseEntity.status(201).body(diretorCriado); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiretorDTO> atualizarDiretor(@PathVariable Long id, @RequestBody DiretorDTO diretorDTO) {
        log.info("Chamando atualizarDiretor no DiretorController com id: {} e dados: {}", id, diretorDTO);
        Optional<DiretorDTO> diretorAtualizado = diretorService.atualizarDiretor(id, diretorDTO);
        return diretorAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDiretor(@PathVariable Long id) {
        log.info("Chamando deletarDiretor no DiretorController com id: {}", id);
        boolean deletado = diretorService.deletarDiretor(id);
        if (deletado) {
            log.info("Diretor com id: {} deletado com sucesso", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Tentativa de deletar diretor com id: {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }
}
