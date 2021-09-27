package example.AcervoObrasEAutores.exceptions;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ExcecaoErro extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	static final String QUANTIDADE_INVALIDA = "Quantidade de caracteres é inválida";
	static final String NAO_CADASTRADO = "Não encontrado";

	ExcecaoErro() {
	   
	}
	
	public static ResponseEntity<?> erroQuantidadeInvalida() {
	     return new ResponseEntity<CustomErrorType>(new CustomErrorType(ExcecaoErro.QUANTIDADE_INVALIDA),
	                HttpStatus.CONFLICT);
	 }
	
    public static ResponseEntity<CustomErrorType> erroNaoEncontrado() {
        return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ExcecaoErro.NAO_CADASTRADO)),
                HttpStatus.BAD_REQUEST);
    }
	
}
