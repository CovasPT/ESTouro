package torre.Estrategia;


import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Interface Strategy para seleção de alvos das torres.
 */
public interface EstrategiaAtaque {

    /**
     * Escolhe um alvo da lista de bloons ao alcance.
     * @param bloonsAlcance Lista de bloons disponíveis.
     * @param t A torre que está a atacar.
     * @return O Bloon alvo ou null.
     */
   Bloon escolherAlvo(Torre t, List<Bloon> bloonsAoAlcance);
}