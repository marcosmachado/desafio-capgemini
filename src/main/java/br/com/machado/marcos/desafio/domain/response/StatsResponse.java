package br.com.machado.marcos.desafio.domain.response;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StatsResponse {
   @NonNull
   private Integer count_valid;
   @NonNull
   private Integer count_invalid;
   @NonNull
   private Double ratio;
}
