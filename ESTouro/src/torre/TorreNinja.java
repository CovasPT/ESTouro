package torre;

import java.awt.Point;
import java.awt.image.BufferedImage;
import bloon.Bloon;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.image.ComponenteSimples;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.Dardo; // A Ninja usa Dardos (Shurikens)
import torre.projetil.Projetil;

public class TorreNinja extends TorreDefault {

	public TorreNinja(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
				10, 0, new Point(25, 0), 150); // Ritmo rápido (10)
	}

	@Override
	protected Projetil[] criarProjetil(Point posicao, double angulo, Bloon alvo) {
		// A Ninja lança 2 shurikens de cada vez
		Projetil[] projeteis = new Projetil[2];
		
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/dardo.gif"));
		
		// Shuriken 1: Ligeiramente para a esquerda (-0.1 radianos)
		projeteis[0] = new Dardo(img, angulo - 0.1, 15, 3);
		projeteis[0].setPosicao(posicao);
		projeteis[0].setAlcance(getRaioAcao() + 30);

		// Shuriken 2: Ligeiramente para a direita (+0.1 radianos)
		// Precisamos de uma nova imagem para o segundo objeto visual
		ComponenteVisual img2 = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/dardo.gif"));
		
		projeteis[1] = new Dardo(img2, angulo + 0.1, 15, 3);
		projeteis[1].setPosicao(posicao);
		projeteis[1].setAlcance(getRaioAcao() + 30);
		
		return projeteis;
	}
}