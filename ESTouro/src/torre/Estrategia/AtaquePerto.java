package torre.Estrategia;


import java.awt.Point;
import java.util.List;
import bloon.Bloon;
import torre.Torre;

/**
 * Estratégia que seleciona o bloon fisicamente mais próximo da torre.
 */
public class AtaquePerto implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(Torre t, List<Bloon> bloonsAoAlcance) {
        if (bloonsAoAlcance == null || bloonsAoAlcance.isEmpty()) {
            return null;
        }
        Point centroTorre = t.getComponente().getPosicaoCentro();
        
        return bloonsAoAlcance.stream()
                .min((b1, b2) -> Double.compare(b1.getComponente().getPosicaoCentro().distance(centroTorre), 
                                                b2.getComponente().getPosicaoCentro().distance(centroTorre)))
                .orElse(null);
    }
}