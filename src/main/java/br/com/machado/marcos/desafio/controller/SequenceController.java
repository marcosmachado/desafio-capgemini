package br.com.machado.marcos.desafio.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.machado.marcos.desafio.domain.request.SequenceRequest;
import br.com.machado.marcos.desafio.domain.response.SequenceResponse;
import br.com.machado.marcos.desafio.domain.response.StatsResponse;
import br.com.machado.marcos.desafio.service.SequenceService;

@RestController
public class SequenceController {

   public static final String SEQUENCE = "/sequence";
   public static final String STATS = "/stats";

   @Autowired
   private SequenceService sequenceService;

   @GetMapping(path = "/test", produces = APPLICATION_JSON_VALUE)
   public String test(@RequestParam(value = "name", defaultValue = "World") String name) {
      return "Test OK";
   }


   @PostMapping(path = SEQUENCE, produces = APPLICATION_JSON_VALUE)
   public SequenceResponse sequence(@RequestBody SequenceRequest request) {

      return this.sequenceService.sequence(request);

   }


   @GetMapping(path = STATS, produces = APPLICATION_JSON_VALUE)
   public StatsResponse stats() {
      return this.sequenceService.stats();
   }


}
