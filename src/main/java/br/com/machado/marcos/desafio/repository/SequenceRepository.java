package br.com.machado.marcos.desafio.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.machado.marcos.desafio.model.Sequence;


public interface SequenceRepository extends MongoRepository<Sequence, Integer> {

    public Sequence findBySequence(String sequence);

}

