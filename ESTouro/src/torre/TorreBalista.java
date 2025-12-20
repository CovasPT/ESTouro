package torre;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

import bloon.Bloon;
import prof.jogos2D.image.*;
import prof.jogos2D.util.ImageLoader;
import torre.projetil.BombaImpacto;
import torre.projetil.Dardo;
import torre.projetil.Projetil;

/**
 * Classe que representa a torre balista. Esta torre dispara 1 dardo enorme e
 * potente na direção especificada pelo jogador.
 */
public class TorreBalista extends TorreDefault {

	/** ponto para onde a balista faz pontaria */
	private Point mira;

	/**
	 * Cria uma balista.
	 * 
	 * @param img imagem da balista
	 */
	public TorreBalista(BufferedImage img) {
		super(new ComponenteMultiAnimado(new Point(), img, 2, 4, 2),
				20, 0, new Point(20, -3), 100);
		setAnguloDisparo(0);
	}

	/**
	 * Define o ângulo de disparo da balista
	 * 
	 * @param angulo o novo ângulo
	 */
	public void setAnguloDisparo(float angulo) {
		getComponente().setAngulo(angulo);
		definirMira(angulo);
	}

	/**
	 * Define a pontaria, isto é, a posição para onde a balusta irá apontar
	 * 
	 * @param angulo angulo do disparo, para poder calcular a área de ataque
	 */
	private void definirMira(double angulo) {
		double cos = Math.cos(angulo);
		double sin = Math.sin(angulo);
		Point centro = getComponente().getPosicaoCentro();
		mira = new Point((int) (centro.x + getRaioAcao() * cos), (int) (centro.y + getRaioAcao() * sin));
	}

	/**
	 * Retorna o ponto para onde a balista irá disparar
	 * 
	 * @return o ponto para onde a balista irá disparar
	 */
	public Point getMira() {
		return mira;
	}

	@Override
	public void setPosicao(Point p) {
		super.setPosicao(p);
		definirMira(getComponente().getAngulo());
	}

	@Override
	public void desenhaRaioAcao(Graphics2D g) {
		Point centro = getComponente().getPosicaoCentro();
		Point mira = getMira();
		Composite oldComp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.BLUE);
		Line2D.Float l = new Line2D.Float(centro, mira);
		g.setStroke(new BasicStroke(20));
		g.draw(l);
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(18));
		g.draw(l);
		g.setComposite(oldComp);
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
	public Torre clone() {
		TorreBalista copia = (TorreBalista) super.clone();
		copia.mira = new Point(mira);
		return copia;
	}


 
}
