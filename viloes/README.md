## Estrutura do projeto

br.com.avengers                                                                                             <br />                                                                                                       
│                                                                                                           <br />
│──── adapter                                                                                               <br />
│       ├── DTO                                                                                             <br />
│       │   └── VilaoDTO.java     // caso request e response usem o mesmo objeto, se não, implementa separado     <br />
│       ├── in                                                                                              <br />
│       │   ├── web                                                                                         <br />
│       │   │    └── ViloesResource.java                                                                   <br />
│       │   └── messaging                                                                                   <br />
│       │        └── KafkaListner.java          // se fosse necessário ouvir entrada de mensageria          <br />
│       │                                                                                                   <br />
│       └── out                                                                                             <br />
│           ├── persistence                                                                                 <br />
│           │    ├── entity                                                                                 <br />
│           │    │   └── Vilao.java                        // entidade JPA                                <br />
│           │    └── VilaoRepository.java                  // repository do JPA                           <br />
│           │    └── VilaoRepositoryAdapter.java           // implementa o Port e usa o Repository do JPA     <br />
│           │                                                                                               <br />
│           ├── messaging                                                                                   <br />
│           │    └── KafkaProducer.java         // se fosse necessário produzir mensageria                  <br />
│           │                                                                                               <br />
│           └── web     // web client para buscar APIs externas  caso necessário                            <br />
│                └── RestClientAdapter.java                                                                 <br />
│                                                                                                           <br />
├──── domain                                                                                                <br />
│       ├── model                                                                                           <br />
│       │    └──   // Não tinha necessidade de objeto aqui                                                  <br />
│       │          // reaproveitei a entidade JPA                                                           <br />
│       ├── validation                                                                                      <br />
│       │    ├── Validador.java                                                                             <br />
│       │    ├── ValidacaoChain.java          //validações                                                  <br />
│       │    ├── ValidaApelido.java                                                                         <br />
│       │    ├── ValidaNome.java                                                                            <br />
│       │    ├── ValidaDescricao.java                                                                       <br />
│       │    └── ValidaHistoria.java                                                                        <br />
│       │                                                                                                   <br />
│       └── VilaoService.java   <-- @Service AQUI ✔  // projeto mais complexo poderia entrar outras classes aqui      <br />
│                                                                                                           <br />
│──── port                                                                                                  <br />
│       ├── in                                                                                              <br />
│       │    └── AvengerResourcePort.java                                                                   <br />
│       │    └── EventListnerPort.java                  // se for necessário ouvir entrada de mensageria        <br />
│       │                                                                                                   <br />
│       └── out                                                                                             <br />
│            ├── AvengerRepositoryPort.java                                                                 <br />
│            ├── EventPublisherPort.java                                                                    <br />
│            └── ExternalWebClientPort.java                                                                 <br />
│                                                                                                           <br />
├──── security                                                                                              <br />
│       ├── config                                                                                          <br />
│       │     ├── AuthConfigService.java                                                                    <br />
│       │     ├── SecurityConfig.java                                                                       <br />
│       │     ├── StartApplication.java     // coloca usuário inicial na aplicação para primeiro uso        <br />
│       │     └── TokenJWTConfig.java                                                                       <br />
│       │                                                                                                   <br />
│       └── filter                                                                                          <br />
│             └── SecurityFilter.java                                                                       <br />
│                                                                                                           <br />
├── config                                                                                                  <br />
│   ├── KafkaConfig.java                                                                                    <br />
│   ├── SwaggerConfig.java                                                                                  <br />
│   └── WebConfig.java                                                                                      <br />
│                                                                                                           <br />
├── shared                                                                                                  <br />
│    ├── exception                                                                                          <br />
│          ├── GlobalExceptionHandler.java                                                                  <br />
│          ├── NegocioException.java                                                                        <br />
│          └── ResponseError.java                                                                           <br />
│                                                                                                           <br />
└── AvengersApplication.java                                                                                <br /><br />


## Dados de vingadores para cadastrar
{
"nick": "Homem de Ferro",
"person": "Tony Stark",
"description": "Gênio, bilionário, playboy e filantropo com uma armadura tecnológica de ponta.",
"history": "Após ser sequestrado, Tony Stark constrói uma armadura para escapar, decidindo usar sua tecnologia para proteger o mundo como um Vingador.",
"idade": 48
}
<br />
{
"nick": "Capitão América",
"person": "Steve Rogers",
"description": "O primeiro vingador, possuidor de força sobre-humana e um escudo de Vibranium inquebrável.",
"history": "Um soldado franzino da Segunda Guerra que foi transformado pelo soro do super-soldado e congelado no gelo por décadas antes de despertar nos dias atuais.",
"idade": 105
}
<br />
{
"nick": "Thor",
"person": "Thor Odinson",
"description": "Deus do Trovão e herdeiro do trono de Asgard, mestre do martelo Mjolnir.",
"history": "Banido para a Terra por seu pai para aprender humildade, Thor tornou-se um dos protetores mais poderosos do universo e da humanidade.",
"idade": 1500
}
<br />
{
"nick": "Hulk",
"person": "Bruce Banner",
"description": "Cientista brilhante que se transforma em um monstro verde de força ilimitada quando enfurecido.",
"history": "Após ser exposto a uma explosão de radiação gama em um experimento, Banner passou a viver em uma luta constante contra seu alter ego destrutivo.",
"idade": 49
}
<br />
{
"nick": "Viúva Negra",
"person": "Natasha Romanoff",
"description": "Espia russa de elite, mestre em artes marciais e infiltração.",
"history": "Treinada desde a infância no Quarto Vermelho, Natasha desertou da KGB para se juntar à S.H.I.E.L.D. e buscar redenção por seu passado.",
"idade": 35
}
<br />
{
"nick": "Gavião Arqueiro",
"person": "Clint Barton",
"description": "O melhor arqueiro do mundo, com precisão infalível e flechas especiais.",
"history": "Um agente altamente treinado da S.H.I.E.L.D. que utiliza suas habilidades excepcionais de combate e estratégia para lutar ao lado de seres superpoderosos.",
"idade": 41
}

## Outros

[
{
"id": 7,
"nick": "Homem-Aranha",
"person": "Peter Parker",
"description": "Jovem herói com habilidades aracnídeas, agilidade sobre-humana e sentido de perigo.",
"history": "Picado por uma aranha geneticamente modificada, o jovem do Queens aprendeu que com grandes poderes vêm grandes responsabilidades.",
"idade": 21
},
{
"id": 8,
"nick": "Nick Fury",
"person": "Nicholas Joseph Fury",
"description": "O estrategista mestre e ex-diretor da S.H.I.E.L.D. que reuniu os Vingadores.",
"history": "Um veterano de espionagem que dedicou sua vida a proteger a Terra de ameaças internas e extraterrestres, operando nas sombras.",
"idade": 74
},
{
"id": 9,
"nick": "Falcão",
"person": "Sam Wilson",
"description": "Ex-paraquedista militar que utiliza asas mecânicas de alta tecnologia para voo e combate.",
"history": "Aliado fiel do Capitão América, Sam provou seu valor no campo de batalha e eventualmente assumiu o manto do escudo para carregar o legado de Steve Rogers.",
"idade": 44
},
{
"id": 10,
"nick": "Pantera Negra",
"person": "T'Challa",
"description": "Rei de Wakanda, possui sentidos aguçados e força aprimorada pela Erva Coração.",
"history": "Após a morte de seu pai, T'Challa assumiu o trono e o manto do Pantera Negra, usando a tecnologia avançada de Vibranium para proteger seu povo e o mundo.",
"idade": 40
},
{
"id": 11,
"nick": "Máquina de Combate",
"person": "James Rhodes",
"description": "Oficial da Força Aérea que opera uma armadura de combate pesadamente armada.",
"history": "Melhor amigo de Tony Stark, o Coronel Rhodes adaptou uma das armaduras do Homem de Ferro para servir como um tanque voador a serviço das forças armadas.",
"idade": 54
},
{
"id": 12,
"nick": "Homem-Formiga",
"person": "Scott Lang",
"description": "Especialista em invasões que utiliza partículas Pym para alterar seu tamanho físico.",
"history": "Um ex-presidiário que buscava redenção e foi escolhido por Hank Pym para herdar o traje que permite encolher a níveis subatômicos ou crescer gigantescamente.",
"idade": 45
},
{
"id": 13,
"nick": "Vespa",
"person": "Hope van Dyne",
"description": "Heroína com capacidade de voo, encolhimento e disparos de energia bioelétrica.",
"history": "Filha de Hank Pym e Janet van Dyne, Hope aperfeiçoou o treinamento de combate e tecnologia para se tornar uma parceira essencial e líder estratégica.",
"idade": 42
},
{
"id": 14,
"nick": "Capitã Marvel",
"person": "Carol Danvers",
"description": "Uma das heroínas mais poderosas do universo, capaz de voar e manipular energia cósmica.",
"history": "Ex-piloto da Força Aérea que ganhou poderes após a explosão de um motor movido pelo Tesseract, passando décadas protegendo outros planetas antes de retornar à Terra.",
"idade": 63
}
]
