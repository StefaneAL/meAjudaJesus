package example.AcervoObrasEAutores.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import example.AcervoObrasEAutores.domain.Autores;
import example.AcervoObrasEAutores.domain.Obras;
import example.AcervoObrasEAutores.exceptions.ExcecaoErro;
import example.AcervoObrasEAutores.repositories.AutoresRepository;
import example.AcervoObrasEAutores.repositories.ObrasRepository;
import example.AcervoObrasEAutores.services.AutoresService;
import example.AcervoObrasEAutores.services.ObrasService;

/**
 * @author Grupo 1 : Débora, Larissa, Maia, Roberta, Stefane, Viviane
 * @since Planejamento, Sprint 2 da apliacação
 * @version 1.4
 * 
 * 
 * Controlador REST com os endpoints requisitando os serviços da classe Service 
 * */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class AutoresController {

	/***/
	@Autowired
	private AutoresService autoresService;
	
	@Autowired 
    private AutoresRepository repositorio;
	
    public AutoresController(AutoresService repository) {
        this.autoresService = repository;
     }
	
	/***/
	@GetMapping("/listarAutores")
	public List<Autores> listarTodosOsAutores(){
	    return autoresService.listarTodos(); 
	}

    @GetMapping("/consultarAutor/{id}")
    public ResponseEntity<?> consultarAutor(@PathVariable Long id) {
    	Optional<Autores> autor = autoresService.consultaAutor(id);
    	
    	if(!autor.isPresent()) {
    		return ExcecaoErro.erroNaoEncontrado();
    	}
    	
    	return new ResponseEntity<Autores>(autor.get(), HttpStatus.OK);
    }
    
    /***/
    @PostMapping("/inserirAutor")
    public ResponseEntity<?> inserirAutor(@RequestBody Autores autor) {
    	
    	if(autor.getNome().length() > 120) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    	}
    		autoresService.insereAutor(autor);
    		return new ResponseEntity<>("Autor inserido com sucesso!", HttpStatus.OK);
    }
    
    /***/
    @DeleteMapping("/excluirAutor/{id}")
    public ResponseEntity<?> excluirAutor(@PathVariable Long id) {
    	autoresService.excluiAutor(id);
    	return new ResponseEntity<>("Obra excluída com sucesso!", HttpStatus.OK);
    } 
    
    /***/
    @PutMapping("/alterarAutor/{id}")
    public ResponseEntity<?> alterarAutor(@PathVariable Long id, @RequestBody Autores novoAutor) {
    	
    	if(novoAutor.getNome().length() > 120) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    	}
    		autoresService.alteraAutor(id, novoAutor);
    		return new ResponseEntity<>("Autor alterado com sucesso!", HttpStatus.OK);
    }
    
    @GetMapping("/autoresObras/{id}")
    public List<Obras> listarObrasDoAutor(@PathVariable Long id){
        return autoresService.listaObrasDoAutor(id); 
    }
    
}
