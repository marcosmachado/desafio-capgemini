package br.com.machado.marcos.desafio.domain.response;


import java.io.Serializable;

import javax.management.ConstructorParameters;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SequenceResponse implements Serializable {
   /** Retorna se a sequencia de letras é válida ou não*/
   @NonNull
   private Boolean is_valid;

}
