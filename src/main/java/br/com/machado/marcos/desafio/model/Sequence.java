package br.com.machado.marcos.desafio.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sequences")
public class Sequence {

    @Id
    private String sequence;
    private boolean isValid;

}
