package bloon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import mundo.Caminho;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteVisual;

/**
 * DECORATOR PATTERN:
 * Classe abstrata que implementa a interface Bloon e mantém uma referência
 * para um objeto Bloon. Todos os pedidos são reencaminhados para esse objeto.
 * As subclasses (como BloonArmadura) só precisam de reescrever o que querem mudar.
 */
public abstract class BloonDecorator implements Bloon {

	protected Bloon bloonDecorado; // O bloon que está "lá dentro"

	public BloonDecorator(Bloon b) {
		this.bloonDecorado = b;
	}

	// --- MÉTODOS QUE MUDAM O COMPORTAMENTO (Override nas filhas) ---
	
	@Override
	public void mover() {
		bloonDecorado.mover();
	}

	@Override
	public void desenhar(Graphics2D g) {
		bloonDecorado.desenhar(g);
	}

	@Override
	public int pop(int estrago) {
		return bloonDecorado.pop(estrago);
	}

	@Override
	public void explode(int estrago) {
		bloonDecorado.explode(estrago);
	}

	// --- MÉTODOS DELEGADOS (Apenas repassam a chamada) ---

	@Override public ComponenteVisual getComponente() { return bloonDecorado.getComponente(); }
	@Override public ComponenteVisual getPopComponente() { return bloonDecorado.getPopComponente(); }
	@Override public void setCaminho(Caminho rua) { bloonDecorado.setCaminho(rua); }
	@Override public Caminho getCaminho() { return bloonDecorado.getCaminho(); }
	@Override public int getPosicaoNoCaminho() { return bloonDecorado.getPosicaoNoCaminho(); }
	@Override public void setPosicaoNoCaminho(int pos) { bloonDecorado.setPosicaoNoCaminho(pos); }
	@Override public void setVelocidade(float veloc) { bloonDecorado.setVelocidade(veloc); }
	@Override public float getVelocidade() { return bloonDecorado.getVelocidade(); }
	@Override public void setMundo(Mundo w) { bloonDecorado.setMundo(w); }
	@Override public Mundo getMundo() { return bloonDecorado.getMundo(); }
	@Override public void setPosicao(Point p) { bloonDecorado.setPosicao(p); }
	@Override public Rectangle getBounds() { return bloonDecorado.getBounds(); }
	@Override public int getResistencia() { return bloonDecorado.getResistencia(); }
	@Override public int getValor() { return bloonDecorado.getValor(); }
	@Override public void setValor(int val) { bloonDecorado.setValor(val); }
	@Override public void addBloonObserver(BloonObserver bo) { bloonDecorado.addBloonObserver(bo); }
	@Override public void removeBloonObserver(BloonObserver bo) { bloonDecorado.removeBloonObserver(bo); }

	// --- PROTOTYPE (Clonar o decorator E o decorado) ---
	@Override
	public Bloon clone() {
		try {
			BloonDecorator copia = (BloonDecorator) super.clone();
			// Importante: Clonar também o bloon que está lá dentro!
			copia.bloonDecorado = this.bloonDecorado.clone();
			return copia;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}