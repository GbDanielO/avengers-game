package br.com.avengers.domain.validation;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.shared.ResponseError;

import java.util.List;

/**
 * Essa é uma forma que usa mais a estrutura do Spring para implementar
 * o Chain of Responsability, por exemplo, não tem a classe abstrata e não tem
 * o próximo objeto da cadeia dentro de cada nó (cria-se uma lista e o Spring injeta todos).
 * Aqui as implementações que precisam de ordem devem ser anotadas com @Order(n)
 * e as demais que não precisam estar ordenadas por mais que façam parte da cadeia
 * é só não colocar a anotação.
 * Particularmente eu prefiro o outro modo (CoR clássico), acho mais explícito e claro.
 */
public interface Validador {
    void valida(Vilao vilao, List<ResponseError> erros);
}
