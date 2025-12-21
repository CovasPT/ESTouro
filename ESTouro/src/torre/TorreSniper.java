package torre;

import java.awt.Point;
import java.awt.image.BufferedImage;
import bloon.Bloon;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.image.ComponenteSimples;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

public class TorreSniper extends TorreDefault {

	public TorreSniper(BufferedImage img) {
		// O Sniper dispara devagar (60 frames delay), mas tem alcance infinito
		// O '1000' aqui é só um valor base, o override em baixo trata do resto
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
				60, 0, new Point(25, 0), 1000); 
	}

	/**
	 * O Sniper tem alcance "infinito" (cobre o mapa todo).
	 * Fazemos override para garantir que devolve sempre um número gigante.
	 */
	@Override
	public int getRaioAcao() {
		return 9999; 
	}

	@Override
	protected Projetil[] criarProjetil(Point posicao, double angulo, Bloon alvo) {
		Projetil[] projeteis = new Projetil[1];
		
		// Usa a imagem do dardo (ou podes criar uma 'bala.gif' se quiseres)
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/dardo.gif"));
		
		// Cria o projétil: (imagem, angulo, velocidade, dano)
		// Velocidade 50 (muito rápida) para parecer instantâneo
		// Dano 2 (mais forte que o macaco normal)
		projeteis[0] = new Dardo(img, angulo, 50, 2); 
		
		projeteis[0].setPosicao(posicao);
		projeteis[0].setAlcance(3000); // A bala viaja muito longe
		projeteis[0].setMundo(getMundo());
		
		return projeteis;
	}
}