package torre.Estrategia;

import java.util.List;
import bloon.Bloon;
import torre.Torre;

public class AtaqueForte implements EstrategiaAtaque {

	@Override
	public Bloon escolherAlvo(Torre t, List<Bloon> bloonsAoAlcance) {
		if (bloonsAoAlcance == null || bloonsAoAlcance.isEmpty()) {
			return null;
		}

		Bloon alvo = null;
		int maiorForca = -1;

		for (Bloon b : bloonsAoAlcance) {
			// Escolhe quem tem mais vida (getResistencia)
			if (b.getResistencia() > maiorForca) {
				maiorForca = b.getResistencia();
				alvo = b;
			}
		}
		
		// Fallback: se não encontrar (ex: lista vazia), devolve o primeiro disponível
		if (alvo == null && !bloonsAoAlcance.isEmpty()) {
			return bloonsAoAlcance.get(0);
		}
		
		return alvo;
	}
}