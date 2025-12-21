package torre.Estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;
import prof.jogos2D.util.DetectorColisoes;

public class AtaqueLonge implements EstrategiaAtaque {

	@Override
	public Bloon escolherAlvo(Torre t, List<Bloon> bloonsAoAlcance) {
		if (bloonsAoAlcance == null || bloonsAoAlcance.isEmpty()) {
			return null;
		}

		Bloon alvo = null;
		double maiorDistancia = -1.0;

		for (Bloon b : bloonsAoAlcance) {
			// Calcula a distância física
			double dist = t.getComponente().getPosicaoCentro()
               .distance(b.getComponente().getPosicaoCentro());

			if (dist > maiorDistancia) {
				maiorDistancia = dist;
				alvo = b;
			}
		}
		return alvo;
	}
}