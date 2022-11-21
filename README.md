# Desafio Capgemini

## Descrição
Este microserviço provê uma API para dentificar se uma sequência de letras é válida.


### Funcionalidades
- Verifica uma sequência válida
- Estatísticas 

## Requisitos
- Java 11
- Gradle 7.5.1
- Mongodb


### Banco de dados
- Baixar uma imagem do mongodb para docker no link https://hub.docker.com/_/mongo


## ✒️ Autor

* **Marcos Machado de souza** - [marcosmachado](https://github.com/marcosmachado?tab=repositories)



### Verifica uma sequência válida [POST]

+ Request (application/json)

    + Headers

              Auto generated headers

    + Body
      
              {
                "letters": ["DUHBHB", "DUBUHD", "UBUUHU", "BHBDHH", "DDDDUB", "UDBDUH"]
              }

+ Response 200 (application/json)

    + Body

            {
              "is_valid": true
            }
    + 
+ Response 422 (application/json)
    Quando a sequência informada já está cadastrada, o código de retorno é 422.
  + Headers

        Auto generated headers
        
  + Body

        Sequência já cadastrada

+ Response 422 (application/json)
  Quando a sequência contém caracteres diferentes de (B, U, D, H), o código de retorno é 422.
    + Headers

          Auto generated headers

    + Body

          A sequencia só pode conter os caracteres (B, U, D, H)

### Estatísticas [GET]

+ Request (application/json)

    + Headers

              Auto generated headers

    + Body
      
      
+ Response 200 (application/json)

    + Body

            {
              "count_valid": 40,
              "count_invalid": 60:
              "ratio": 0.4
            }



