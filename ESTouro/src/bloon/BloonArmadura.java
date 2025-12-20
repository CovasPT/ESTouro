package bloon;

import java.awt.Graphics2D;
import prof.jogos2D.image.ComponenteSimples;
import prof.jogos2D.image.ComponenteVisual;
import prof.jogos2D.util.ImageLoader;

public class BloonArmadura extends BloonDecorator {

	private int vidaArmadura;
	private ComponenteVisual imgArmadura; 

	public BloonArmadura(Bloon b, int durabilidade) {
		super(b);
		this.vidaArmadura = durabilidade;
		// Carrega a imagem "armadura.png" que vi na tua pasta data/misc
		this.imgArmadura = new ComponenteSimples(ImageLoader.getLoader().getImage("data/misc/armadura.png"));
	}

	@Override
	public void desenhar(Graphics2D g) {
		// 1. Desenha o bloon original por baixo
		super.desenhar(g); 
		
		// 2. Se a armadura ainda existe, desenha-a por cima
		if (vidaArmadura > 0) {
			// Sincroniza a posição da armadura com a do bloon
			imgArmadura.setPosicaoCentro(getComponente().getPosicaoCentro());
			imgArmadura.desenhar(g);
		}
	}

	@Override
	public int pop(int estrago) {
		// Lógica: Se tem armadura, o bloon não sofre dano nenhum.
		// A armadura perde 1 ponto de durabilidade por cada ataque.
		
		if (vidaArmadura > 0) {
			vidaArmadura--; 
			// Retornamos 0 para indicar que o bloon original não sofreu dano.
			// (O projétil bateu na armadura e "morreu" lá).
			return 0; 
		} else {
			// Se a armadura já partiu, o bloon original sofre o dano normal.
			return super.pop(estrago); 
		}
	}
	
	// Nota: Não fazemos override ao explode(), porque a armadura não protege de explosões!
}