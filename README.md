# ⚔️ Arena de Batalhas — Jogo de Turnos

Bem-vindo ao **Avengers Game**, um jogo de combate por turnos onde estratégia, poder e imprevisibilidade caminham juntos.  
Aqui, nem sempre o mais forte vence — cada batalha é uma história única.

---
## Noticias
- O jogo está completamente funcional (backend) subindo tudo em docker-compose.
- O API Gateway está funcionando
- Próximos passos possíveis (não estão em ordem):
-- Implementar o ServiceDiscovery
-- Implementar o ServiceConfig
-- Implementar segurança
-- Construir frontend
-- Subir na Nuvem

## Arquitetura
<img src="https://github.com/GbDanielO/avengers-game/blob/main/Arquitetura.png" width="50%">

## 👤 Personagens

Cada personagem entra na Arena com os seguintes atributos:

- **Apelido** — Codinome do herói/vilão
- **Habilidade** — Força física e técnica de combate
- **Magia** — Poderes místicos ou sobrenaturais
- **Artefato (opcional)** — Itens lendários como joias, livros ou armas mágicas

Mesmo personagens extremamente poderosos podem ser surpreendidos.

---

## 🔁 Como a Batalha Funciona

A batalha acontece em **turnos**.

Em cada turno:
- Um personagem ataca
- O outro defende
- No turno seguinte, os papéis se invertem

A luta continua até que **um dos personagens fique sem defesa**.

---

## ✨ Magia e Artefatos

Em **todo turno**, **tanto o atacante quanto o defensor** podem tentar usar:

- ✨ **Magia**
- 🧿 **Artefato** (caso possuam)

⚠️ Importante:  
Ter magia ou artefato **não garante** que ele será usado com sucesso naquele turno.

---

## 🎲 Aleatoriedade Inteligente

O jogo simula situações reais de combate, como:

- Ser pego de surpresa
- Não conseguir reagir a tempo
- Errar a execução de uma magia
- Ser desarmado
- Simplesmente decidir não usar

Além disso, quando magia ou artefato são usados, o efeito é **aleatório**:

Eles podem gerar:
- **ATAQUE**
- **DEFESA**
- Ou um efeito que **não ajuda naquela situação**

### Exemplos:
- 🔥 O atacante usa magia, mas ela gera **DEFESA** → não ajuda no ataque
- 🛡️ O defensor ativa um artefato, mas ele gera **ATAQUE** → não ajuda na defesa

Isso torna cada batalha **imprevisível e estratégica**.

---

## ❌ Quando Magia ou Artefato Não São Usados

Se um personagem **não usar magia ou artefato**, o jogo registra um motivo narrativo, como:

- 🫢 **Pego de surpresa**
- ⚔️ **Foi desarmado**
- ❌ **Falhou**
- 🤷 **Não soube usar**
- 🧠 **Não quis usar**

Esses motivos ajudam a contar a história da batalha turno a turno.

---

## 💥 Cálculo do Dano

O dano final de cada turno leva em conta:

- A habilidade do atacante
- Magia e artefatos usados para **ataque**
- Magia e artefatos usados para **defesa**

O resultado pode ser:
- Um ataque devastador
- Um golpe parcialmente bloqueado
- Ou nenhum dano efetivo

---

## 🏁 Fim da Batalha

A batalha termina quando a defesa de um personagem chega a **zero**.

Ao final, o sistema registra:

- 🏆 Vencedor
- 💀 Perdedor
- 📜 Todos os turnos da batalha
- ✨ Uso (ou falha)


## Tecnologias

- Java 17
- Spring Boot 3.5.8
- Maven
- MongoDB
- Postgres
- Redis
- Kafka (com retry, DLQ e re-drive)
- Docker & Docker Compose 

## Arquitetura e Padrões de Projeto e mais

- Microsserviços
- API Gateway
- Port and Adapters
- Chain of Responsability (Duas implementações, uma mais Java puro (API Avengers) e a outra usando mais o Spring(API Viloes)) aplicado nas validações
- Testes Unitários

## Instruções de subida

- Topicos Kafka:

./kafka-topics.sh --create --topic batalha --bootstrap-server http://localhost:9092 --partitions 3 --replication-factor 1<br />
./kafka-topics.sh --create --topic batalha.DLQ --bootstrap-server http://localhost:9092 --partitions 3 --replication-factor 1

- Docker compose usar:

docker compose --env-file ./avenger-dev.env up -d --build

- Intellij Usar:

VM options: -Dspring.profiles.active=dev <br />
Environment Variables: arquivo .env com seus dados de usuário, senha e outras configurações das tecnologias usadas

- Seu .env precisa ter:

APIs

AVENGERS_API_URL=http://api-avengers:8080/avengers-api<br />
VILOES_API_URL=http://api-viloes:8080/viloes-api<br />
<br />
POSTGRES <br />
<br />
POSTGRES_USER=seu_user<br />
POSTGRES_PASSWORD=sua_senha<br />
POSTGRES_HOST=postgres<br />
POSTGRES_PORT=5432<br />
<br />
MONGODB<br />
<br />
MONGO_HOST=mongo<br />
MONGO_PORT=27017<br />
MONGO_DB=avengers-game<br />
MONGO_USER=seu_user<br />
MONGO_PASSWORD=sua_senha<br />
<br />
MONGO_URI=mongodb://${MONGO_USER}:${MONGO_PASSWORD}@${MONGO_HOST}:${MONGO_PORT}/avengers-game?authSource=avengers-game<br /> 
<br />
REDIS<br />
<br />
REDIS_HOST=redis<br />
REDIS_PORT=6379<br />
<br />
KAFKA<br />
<br />
KAFKA_BOOTSTRAP_SERVERS=kafka:9092

<br /><br /><br />

**Desenvolvido por Daniel Oliveira (gbdaniel_rj@hotmail.com)**.