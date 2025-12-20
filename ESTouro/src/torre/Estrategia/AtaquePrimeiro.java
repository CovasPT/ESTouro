package torre.Estrategia;


import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia que seleciona o bloon que está mais avançado no caminho.
 */
public class AtaquePrimeiro implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloonsAoAlcance) {
        if (bloonsAoAlcance == null || bloonsAoAlcance.isEmpty()) {
            return null;
        }
        // Retorna o bloon com maior posição no caminho
        return bloonsAoAlcance.stream()
                .max((b1, b2) -> Integer.compare(b1.getPosicaoNoCaminho(), b2.getPosicaoNoCaminho()))
                .orElse(null);
    }
}