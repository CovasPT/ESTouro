package torre;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Objects;

import bloon.Bloon;
import mundo.Mundo;
import prof.jogos2D.image.ComponenteMultiAnimado;
import prof.jogos2D.util.DetectorColisoes;
import torre.Estrategia.AtaquePrimeiro;
import torre.Estrategia.EstrategiaAtaque;
import torre.projetil.Projetil; // Import necessário

public abstract class TorreDefault implements Torre {

	private Mundo mundo;
	private ComponenteMultiAnimado imagem;

	// O modo antigo (int) deixa de ser necessário, mas mantemos por compatibilidade se quiseres
	private int modoAtaque = ATACA_PRIMEIRO; 
	private EstrategiaAtaque estrategia; 
	private int raioAtaque;
	private Point pontoDisparo;

	protected static final int PAUSA_ANIM = 0;
	protected static final int ATAQUE_ANIM = 1;
	private int ritmoDisparo;
	private int proxDisparo;
	private int frameDisparoDelay;

	public TorreDefault(ComponenteMultiAnimado cv, int ritmoDisparo, int delayDisparo, Point pontoDisparo,
			int raioAtaque) {
		imagem = Objects.requireNonNull(cv);
		this.ritmoDisparo = ritmoDisparo;
		this.proxDisparo = 0;
		this.frameDisparoDelay = delayDisparo;
		this.pontoDisparo = Objects.requireNonNull(pontoDisparo);
		this.raioAtaque = raioAtaque;
		this.estrategia = new AtaquePrimeiro(); // Estratégia por defeito
	}

	// --- TEMPLATE METHOD: O ATAQUE GENÉRICO ---
	@Override
	public Projetil[] atacar(List<Bloon> bloons) {
		atualizarCicloDisparo();
		ComponenteMultiAnimado anim = getComponente();

		// 1. Gestão da Animação
		if (anim.getAnim() == ATAQUE_ANIM && anim.numCiclosFeitos() >= 1) {
			anim.setAnim(PAUSA_ANIM);
		}

		// 2. Escolher o Alvo (Usando STRATEGY)
		List<Bloon> alvosPossiveis = getBloonsInRadius(bloons, getComponente().getPosicaoCentro(), getRaioAcao());
		if (alvosPossiveis.isEmpty()) return new Projetil[0];
		
		// Nota: Confirma se a ordem dos argumentos na tua Interface é (List, Torre) ou (Torre, List)
		Bloon alvo = getEstrategia().escolherAlvo(this,alvosPossiveis );
		
		if (alvo == null) return new Projetil[0];
		Point posAlvo = alvo.getComponente().getPosicaoCentro();

		// 3. Rodar a Torre
		double angle = DetectorColisoes.getAngulo(posAlvo, anim.getPosicaoCentro());
		anim.setAngulo(angle);

		// 4. Verificar Cooldown
		sincronizarFrameDisparo(anim);
		if (!podeDisparar()) return new Projetil[0];

		// 5. Calcular Posição do Disparo
		resetTempoDisparar();
		Point centro = getComponente().getPosicaoCentro();
		Point disparo = getPontoDisparo();
		double cosA = Math.cos(angle);
		double senA = Math.sin(angle);
		int px = (int) (disparo.x * cosA - disparo.y * senA);
		int py = (int) (disparo.y * cosA + disparo.x * senA);
		Point pontoFinalDisparo = new Point(centro.x + px, centro.y + py);

		// 6. Criar o Projétil Específico (HOOK / FACTORY METHOD)
		// Aqui a classe pai delega na filha a criação do objeto concreto
		return criarProjetil(pontoFinalDisparo, angle, alvo);
	}

	/**
	 * Método Abstrato (Hook): Cada torre filha deve implementar isto para
	 * devolver a sua Bomba, Dardo ou Míssil específico.
	 */
	protected abstract Projetil[] criarProjetil(Point posicao, double angulo, Bloon alvo);

	// ---------------------------------------------------------

	protected void atualizarCicloDisparo() {
		proxDisparo++;
	}

	protected boolean podeDisparar() {
		return proxDisparo >= ritmoDisparo;
	}

	protected int resetTempoDisparar() {
		return proxDisparo = 0;
	}

	protected void sincronizarFrameDisparo(ComponenteMultiAnimado anim) {
		if (proxDisparo + frameDisparoDelay >= ritmoDisparo) {
			if (anim.getAnim() != ATAQUE_ANIM) {
				anim.setAnim(ATAQUE_ANIM);
				anim.reset();
			}
		}
	}

	protected void setComponente(ComponenteMultiAnimado v) {
		imagem = v;
	}

	@Override
	public void setMundo(Mundo w) {
		mundo = w;
	}

	@Override
	public Mundo getMundo() {
		return mundo;
	}

	@Override
	public ComponenteMultiAnimado getComponente() {
		return imagem;
	}

	@Override
	public void setPosicao(Point p) {
		imagem.setPosicaoCentro(p);
	}

	@Override
	public Point getPontoDisparo() {
		return pontoDisparo;
	}

	@Override
	public void setRaioAcao(int raio) {
		raioAtaque = raio;
	}

	@Override
	public int getRaioAcao() {
		return raioAtaque;
	}

	@Override
	public void desenhar(Graphics2D g) {
		imagem.desenhar(g);
	}

	@Override
	public void desenhaRaioAcao(Graphics2D g) {
		Point p = getComponente().getPosicaoCentro();
		Composite oldComp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g.setColor(Color.WHITE);
		g.fillOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setColor(Color.BLUE);
		g.drawOval(p.x - raioAtaque, p.y - raioAtaque, 2 * raioAtaque, 2 * raioAtaque);
		g.setComposite(oldComp);
	}

	@Override
	public int getModoAtaque() {
		return modoAtaque;
	}
    
    // Mantemos o setModoAtaque apenas para compatibilidade, mas o ideal é usar setEstrategia
    @Override
	public void setModoAtaque(int mode) {
		this.modoAtaque = mode;
        // Podes adicionar aqui um switch temporário se o configurador ainda enviar ints,
        // mas como já mudámos o configurador, isto pode ficar vazio ou depreciado.
	}

	@Override
	public void setEstrategia(EstrategiaAtaque estrategia) {
		this.estrategia = estrategia;
	}

	@Override
	public EstrategiaAtaque getEstrategia() {
		return estrategia;
	}

	protected List<Bloon> getBloonsInRadius(List<Bloon> bloons, Point center, int radius) {
		return bloons.stream().filter(b -> DetectorColisoes.intersectam(b.getBounds(), center, radius)).toList();
	}

	protected List<Bloon> getBloonsInLine(List<Bloon> bloons, Point p1, Point p2) {
		return bloons.stream().filter(b -> b.getBounds().intersectsLine(p1.x, p1.y, p2.x, p2.y)).toList();
	}

	@Override
	public Torre clone() {
		try {
			TorreDefault copia = (TorreDefault) super.clone();
			copia.imagem = imagem.clone();
			copia.mundo = null;
			copia.pontoDisparo = new Point(pontoDisparo);
            // Se a estratégia for stateless, partilhamos. Senão, deveríamos clonar.
            // Para já fica assim.
			return copia;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}