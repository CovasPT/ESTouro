package game;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import torre.Torre;
import torre.Estrategia.EstrategiaAtaque;
import torre.Estrategia.AtaquePrimeiro;
import torre.Estrategia.AtaqueUltimo;
import torre.Estrategia.AtaquePerto;
import torre.Estrategia.AtaqueJuntos;
import torre.Estrategia.AtaqueLonge;
import torre.Estrategia.AtaqueForte;
// Importar as futuras estratégias quando as criares:
// import torre.AtaqueLonge;
// import torre.AtaqueForte;

public class ConfiguradorTorres extends JPanel {

	private static final long serialVersionUID = 1L;

	private ButtonGroup btGrp = new ButtonGroup();
	private Torre escolhida;

	// Mapeia a CLASSE da estratégia ao botão (ex: AtaquePrimeiro.class -> Botão1)
	private Map<Class<? extends EstrategiaAtaque>, JToggleButton> botoes = new HashMap<>();

	public ConfiguradorTorres() {
		super();
		setupInterface();
	}

	private void setupInterface() {
		JPanel painelAtaques = new JPanel();
		painelAtaques.setBorder(new TitledBorder("Modo de Ataque"));
		painelAtaques.setBounds(new Rectangle(2, 5, 145, 90));
		
		criarBotoesAtaques(painelAtaques);
		
		this.setLayout(null);
		this.setBounds(new Rectangle(0, 0, 140, 200));
		this.add(painelAtaques);
	}

	private void criarBotoesAtaques(JPanel painelAtaques) {
		// Passamos agora instâncias das estratégias
		painelAtaques.add(criarBotaoAtaque("Primeiro", new AtaquePrimeiro()));
		painelAtaques.add(criarBotaoAtaque("Último", new AtaqueUltimo()));
		painelAtaques.add(criarBotaoAtaque("Perto", new AtaquePerto()));
		painelAtaques.add(criarBotaoAtaque("Juntos", new AtaqueJuntos()));
		painelAtaques.add(criarBotaoAtaque("Longe", new AtaqueLonge()));
		painelAtaques.add(criarBotaoAtaque("Forte", new AtaqueForte()));
	}

	/**
	 * Cria um botão e associa-o a uma Estratégia específica.
	 */
	private JToggleButton criarBotaoAtaque(String texto, EstrategiaAtaque estrategia) {
		JToggleButton button = new JToggleButton(texto);
		button.setPreferredSize(new Dimension(60, 18));
		button.setMargin(new Insets(0, 0, 0, 0));
		
		// AQUI ESTÁ A MAGIA DO PATTERN:
		// Ao clicar, injetamos a estratégia diretamente na torre.
		// Usamos estrategia.getClass().newInstance() ou criamos um clone se a estratégia tiver estado.
		// Como as nossas estratégias são "Stateless" (sem memória), podemos passar uma nova instância.
		button.addActionListener(e -> {
			if (escolhida != null) {
				// Opção A: Criar nova instância (mais seguro se tiver estado interno)
				try {
					escolhida.setEstrategia(estrategia.getClass().getDeclaredConstructor().newInstance());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				// Opção B (Se for Stateless): escolhida.setEstrategia(estrategia);
			}
		});

		btGrp.add(button);
		// Guardamos no mapa usando a CLASSE como chave
		botoes.put(estrategia.getClass(), button);
		
		return button;
	}

	/**
	 * Atualiza o painel quando selecionamos uma torre no jogo.
	 */
	public void setSelecionada(Torre t) {
		escolhida = t;

		if (t.getEstrategia() != null) {
			// Verifica qual a classe da estratégia da torre e ativa o botão correspondente
			JToggleButton bt = botoes.get(t.getEstrategia().getClass());
			if (bt != null) {
				bt.setSelected(true);
			}
		} else {
			// Fallback se não tiver estratégia
			if (!botoes.isEmpty()) {
				// Tenta selecionar o botão do "Primeiro" por defeito
				JToggleButton btDefault = botoes.get(AtaquePrimeiro.class);
				if(btDefault != null) btDefault.setSelected(true);
			}
		}
	}
}