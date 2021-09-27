package example.AcervoObrasEAutores.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.AcervoObrasEAutores.domain.Autores;
import example.AcervoObrasEAutores.domain.Obras;
import example.AcervoObrasEAutores.repositories.AutoresRepository;
import example.AcervoObrasEAutores.repositories.ObrasRepository;


/**
 * @author Grupo 1 : Débora, Larissa, Maia, Roberta, Stefane, Viviane
 * @since Execução, Sprint 3 da apliacação
 * @version 1.4
 * 
 *  
 * Os serviçoes requisitam e enviam para os repositórios
 * */
@Service
public class AutoresService {
	
	/**Dependência */
	@Autowired 
	private AutoresRepository autoresRepository;
	
	@Autowired 
	private ObrasRepository obrasRepository; 

    public AutoresService(AutoresRepository repository) {
        this.autoresRepository = repository;
     }
    
	public List<Autores> listarTodos() {
		return autoresRepository.findAll();
	}

	public Optional<Autores> consultaAutor(Long id) {
		return autoresRepository.findById(id);
	}

	public Autores insereAutor(Autores autor) {
					
		return autoresRepository.save(autor);
	}

	public void excluiAutor(Long id) {
		autoresRepository.deleteById(id);
	}

	public Autores alteraAutor(Long id, Autores novoAutor) {
		
		return autoresRepository.findById(id).map(autor -> {
			        autor.setNome(novoAutor.getNome());
			        autor.setPaisOrigem(novoAutor.getPaisOrigem());
			        autor.setCpf(novoAutor.getCpf());
			        autor.setDataNasc(novoAutor.getDataNasc());
			        return autoresRepository.save(autor);
			      })
			      .orElseGet(() -> {
			        novoAutor.setId(id);
			        return autoresRepository.save(novoAutor);
			      });
	}
	
	public List<Obras> listaObrasDoAutor(Long id) {
        List<Obras> todasAsObras = obrasRepository.findAll();
        List<Obras> obrasDoAutor = new ArrayList<>();

        for(Obras obra: todasAsObras) {
            List<Autores> autoresDaObra = obra.getAutores();

            for(Autores autor : autoresDaObra) {
                if(autor.getId() == id) {
                    obrasDoAutor.add(obra);
                }
            }
        }

        return obrasDoAutor;
    }

}
