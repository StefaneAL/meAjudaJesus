package example.AcervoObrasEAutores.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import example.AcervoObrasEAutores.domain.Obras;
import example.AcervoObrasEAutores.exceptions.ExcecaoErro;
import example.AcervoObrasEAutores.repositories.ObrasRepository;
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
public class ObrasController {

	/***/
	@Autowired
	private ObrasService obrasService;
	
	@Autowired 
    private ObrasRepository repositorio;

    public ObrasController(ObrasService repository) {
       this.obrasService = repository;
    }
	
	/***/
	@GetMapping("/listarObras")
    public List<Obras> listarTodasAsObras(){
        return obrasService.listaTodas();
    }

    @GetMapping("/consultarObra/{id}")
    public ResponseEntity<?> consultarObra(@PathVariable Long id) {
    	Optional<Obras> obra = obrasService.consultaObra(id);
    	
    	if(!obra.isPresent()) {
    		return ExcecaoErro.erroNaoEncontrado();
    	}
    	
    	return new ResponseEntity<Obras>(obra.get(), HttpStatus.OK);
    }

    /***/
    @PostMapping("/inserirObra/{idAutores}")
    public ResponseEntity<?> inserirObra(@PathVariable List<Long> idAutores, @RequestBody Obras obra) {
    	
    	if(obra.getNome().length() > 240) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    		
    	}if(obra.getDescricao().length() > 240) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    		
    	}
    		obrasService.insereObra(obra, idAutores);
    		return new ResponseEntity<>("Obra inserida com sucesso!", HttpStatus.OK);
    		 
        
    }

    /***/
    @DeleteMapping("/excluirObra/{id}")
    public ResponseEntity<?> excluirObra(@PathVariable Long id) {
    	obrasService.excluiObra(id);
    	return new ResponseEntity<>("Obra excluída com sucesso!", HttpStatus.OK);
    }
    
    /***/
    @PutMapping("/alterarObra/{id}")
    public ResponseEntity<?> alterarObra(@PathVariable Long id, @RequestBody Obras novaObra) {
    	
    	Obras obra = obrasService.alteraObra(id, novaObra);
    	
    	if(!obra.isPresent()) {
    		return ExcecaoErro.erroNaoEncontrado();
    		
    	}if(obra.getNome().length() > 240) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    		
    	}if(obra.getDescricao().length() > 240) {
    		return ExcecaoErro.erroQuantidadeInvalida();
    		
    	}
    		obrasService.alteraObra(id, novaObra);
    		return new ResponseEntity<>("Obra alterada com sucesso!", HttpStatus.OK);
    	
    }
	
}
