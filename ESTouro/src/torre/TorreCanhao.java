package torre;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.*;
import prof.jogos2D.util.DetectorColisoes;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.BombaImpacto;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre canhão. Esta torre dispara uma bomba que
 * explode ao contato com os bloons. Só dispara quando tem bloons dentro do seu
 * raio de ação e atira para o bloon de acordo com o seu modo de ataque
 */
public class TorreCanhao extends TorreDefault {

	public TorreCanhao(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(50, 50), img, 2, 4, 2),
				30, 0, new Point(25, 0), 120);
	}

	@Override
	protected Projetil[] criarProjetil(Point posicao, double angulo, Bloon alvo) {
		Projetil p[] = new Projetil[1];
		
		// Carrega a imagem da bomba
		ComponenteVisual img = new ComponenteSimples(ImageLoader.getLoader().getImage("data/torres/bomba.gif"));
		
		// Cria o objeto BombaImpacto
		p[0] = new BombaImpacto(img, angulo, 12, 2, getMundo());
		
		// Configurações iniciais
		p[0].setPosicao(posicao);
		p[0].setAlcance(getRaioAcao() + 20);
		
		return p;
	}

	@Override
	public String getIdentificadorSave() {
		return "canhao";
	}
}
