package torre.Estrategia;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia que ataca a zona com maior densidade de bloons.
 */
public class AtaqueJuntos implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(List<Bloon> bloonsAlcance, Torre t) {
        if (bloonsAlcance == null || bloonsAlcance.isEmpty()) {
            return null;
        }

        // Agrupa bloons por segmentos de caminho (divisão por 20)
        Map<Integer, List<Bloon>> posicoes = bloonsAlcance.stream()
                .collect(Collectors.groupingBy(b -> b.getPosicaoNoCaminho() / 20));
        
        // Encontra o segmento com mais bloons
        int posicaoComMais = Collections.max(posicoes.keySet(),
                (k1, k2) -> posicoes.get(k1).size() - posicoes.get(k2).size());
        
        return posicoes.get(posicaoComMais).get(0);
    }
}