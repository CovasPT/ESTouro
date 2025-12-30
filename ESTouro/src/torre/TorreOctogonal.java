package torre;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import game.manipulator.ManipuladorTorre;
import game.manipulator.ManipuladorOcto;
import torre.projetil.BombaImpacto;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre octogonal. Esta torre dispara 8 dardos, um em
 * cada direção dos seus lançadores. Só dispara quando tem bloons dentro do seu
 * raio de ação.
 */
public class TorreOctogonal extends TorreDefault {

	private double baseAngle = 0;

	public TorreOctogonal(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
				20, 6, new Point(0, 0), 100);
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

	/**
	 * Altera o ângulo da octo
	 * 
	 * @param angle o novo ângulo
	 */
	public void setAngle(double angle) {
		getComponente().setAngulo(angle);
		baseAngle = angle;
	}

	@Override
	public ManipuladorTorre criarManipulador() {
		return new ManipuladorOcto(this);
	}

	@Override
	public String getIdentificadorSave() {
		return "octo";
	}
}
