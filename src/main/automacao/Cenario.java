package src.main.automacao;

import src.main.controller.DomusControl;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A classe Cenario representa um cenário de automação residencial, que é uma coleção de ações que podem ser executadas em conjunto para criar uma experiência personalizada para os usuários. Cada cenário tem um ID único, um nome descritivo, um ID da casa associada e uma lista de ações que compõem o cenário. A classe Cenario implementa a interface Serializable para permitir que os objetos sejam serializados e desserializados, facilitando o armazenamento e a transferência de cenários entre diferentes partes do sistema. A classe inclui métodos para adicionar ações ao cenário, executar as ações em um ambiente DomusControl e criar cenários pré-definidos para situações comuns, como sair de casa, jantar com amigos, jantar romântico, cinema, estudar, deitar e acordar.
 */
public class Cenario implements Serializable {
	/**
	 * Atributo serialVersionUID para garantir a compatibilidade de versões durante a serialização e desserialização dos objetos da classe Cenario. Este atributo é utilizado para identificar a versão da classe durante o processo de serialização, permitindo que objetos serializados com uma versão específica da classe possam ser desserializados corretamente mesmo que a classe tenha sido modificada posteriormente. O valor do serialVersionUID é definido como 1L, indicando que esta é a primeira versão da classe Cenario.
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID único do cenário, utilizado para identificar o cenário de forma única dentro do sistema. Este ID é importante para operações de comparação, armazenamento e recuperação de cenários, garantindo que cada cenário possa ser referenciado de maneira consistente e sem ambiguidades.
	 */
	private int id;
	/**
	 * Nome do cenário, que é uma descrição textual do cenário para facilitar a identificação e compreensão do propósito do cenário pelos usuários. O nome deve ser descritivo o suficiente para transmitir a ideia geral do cenário, como "Sair de casa", "Jantar com amigos" ou "Cinema", permitindo que os usuários escolham o cenário apropriado para suas necessidades.
	 */
	private String nome;
	/**
	 * ID da casa associada ao cenário, que é utilizado para vincular o cenário a uma casa específica dentro do sistema de automação residencial. Este ID é importante para garantir que as ações do cenário sejam executadas na casa correta, especialmente em sistemas que gerenciam múltiplas casas ou ambientes. O ID da casa permite que o cenário seja aplicado apenas aos dispositivos e configurações relevantes para aquela casa, evitando interferências indesejadas em outras casas ou ambientes.
	 */
	private int idCasa;
	/**
	 * Lista de ações que compõem o cenário, onde cada ação representa uma operação específica a ser executada em um dispositivo ou configuração da casa. As ações são armazenadas em uma lista para permitir a execução sequencial das ações quando o cenário é ativado. Cada ação na lista deve ser cuidadosamente definida para garantir que o cenário funcione conforme o esperado, criando a experiência desejada para os usuários. A lista de ações pode incluir operações como ligar ou desligar luzes, abrir ou fechar cortinas, ajustar a intensidade ou cor das lâmpadas, e controlar outros dispositivos conectados à casa.
	 */
	private List<Acao> acoes;

	/**
	 * Construtor padrão da classe Cenario, que inicializa os atributos do cenário com valores padrão. O ID é definido como 0, o nome é uma string vazia, o ID da casa é definido como 0 e a lista de ações é inicializada como uma lista vazia. Este construtor é útil para criar cenários básicos que podem ser configurados posteriormente, permitindo que os usuários adicionem ações e personalizem o cenário conforme necessário.
	 */
	public Cenario() {
		this.id = 0;
		this.nome = "";
		this.idCasa = 0;
		this.acoes = new ArrayList<>();
	}

	/**
	 * Construtor parametrizado da classe Cenario, que permite criar um cenário com valores específicos para os atributos. O construtor recebe o ID, o nome, o ID da casa e a lista de ações como parâmetros. A lista de ações é copiada para garantir que as ações do cenário sejam independentes das ações passadas como parâmetro, evitando efeitos colaterais indesejados. Se a lista de ações passada como parâmetro for null, a lista de ações do cenário será inicializada como uma lista vazia.
	 * @param id
	 * @param nome
	 * @param idCasa
	 * @param acoes
	 */
	public Cenario(int id, String nome, int idCasa, List<Acao> acoes) {
		this.id = id;
		this.nome = nome;
		this.idCasa = idCasa;
		this.acoes = new ArrayList<>();
		if (acoes != null) {
			for (Acao acao : acoes) {
				if (acao != null)
					this.acoes.add(acao.clone());
			}
		}
	}

	/**
	 * Construtor de cópia da classe Cenario, que cria um novo cenário com os mesmos valores dos atributos do cenário passado como parâmetro. Este construtor é útil para criar uma cópia de um cenário existente, permitindo que as alterações feitas na nova instância não afetem o cenário original. A lista de ações é copiada para garantir que as ações do novo cenário sejam independentes das ações do cenário original, evitando efeitos colaterais indesejados.
	 * @param c
	 */
	public Cenario(Cenario c) {
		this(c.id, c.nome, c.idCasa, c.acoes);
	}

	/**
	 * Método getter para o ID do cenário, que retorna o ID único do cenário. Este método é utilizado para acessar o ID do cenário em outras partes do código, como em operações de comparação, armazenamento e recuperação de cenários, garantindo que cada cenário possa ser referenciado de maneira consistente e sem ambiguidades.
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter para o ID do cenário, que define o ID único do cenário. Este método é utilizado para atribuir um ID ao cenário em outras partes do código, como em operações de criação de novos cenários ou em interfaces de usuário onde o ID do cenário é controlado. O ID deve ser único para cada cenário para garantir que os cenários possam ser referenciados de maneira consistente e sem ambiguidades.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método getter para o nome do cenário, que retorna o nome descritivo do cenário. Este método é utilizado para acessar o nome do cenário em outras partes do código, como em operações de exibição de informações sobre o cenário em interfaces de usuário ou para fins de depuração e registro de atividades relacionadas ao cenário.
	 * @return String
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método setter para o nome do cenário, que define o nome descritivo do cenário. Este método é utilizado para atribuir um nome ao cenário em outras partes do código, como em operações de criação de novos cenários ou em interfaces de usuário onde o nome do cenário é controlado. O nome deve ser descritivo o suficiente para transmitir a ideia geral do cenário, como "Sair de casa", "Jantar com amigos" ou "Cinema", permitindo que os usuários escolham o cenário apropriado para suas necessidades.
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método getter para o ID da casa associada ao cenário, que retorna o ID da casa vinculada ao cenário. Este método é utilizado para acessar o ID da casa do cenário em outras partes do código, como em operações de execução das ações do cenário em um ambiente DomusControl, garantindo que as ações sejam aplicadas na casa correta.
	 * @return int
	 */
	public int getIdCasa() {
		return idCasa;
	}

	/**
	 * Método setter para o ID da casa associada ao cenário, que define o ID da casa vinculada ao cenário. Este método é utilizado para atribuir um ID de casa ao cenário em outras partes do código, como em operações de criação de novos cenários ou em interfaces de usuário onde o ID da casa do cenário é controlado. O ID da casa permite que o cenário seja aplicado apenas aos dispositivos e configurações relevantes para aquela casa, evitando interferências indesejadas em outras casas ou ambientes.
	 * @param idCasa
	 */
	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	/**
	 * Método getter para a lista de ações do cenário, que retorna uma lista imutável das ações que compõem o cenário. Este método é utilizado para acessar as ações do cenário em outras partes do código, como em operações de execução das ações do cenário em um ambiente DomusControl ou para fins de exibição de informações sobre as ações do cenário em interfaces de usuário. A lista retornada é imutável para garantir que as ações do cenário não possam ser modificadas diretamente, preservando a integridade do cenário.
	 * @return List<Acao>
	 */
	public List<Acao> getAcoes() {
		return Collections.unmodifiableList(acoes);
	}

	/**
	 * Método setter para a lista de ações do cenário, que define as ações que compõem o cenário. Este método é utilizado para atribuir uma lista de ações ao cenário em outras partes do código, como em operações de criação de novos cenários ou em interfaces de usuário onde as ações do cenário são controladas. A lista de ações é copiada para garantir que as ações do cenário sejam independentes das ações passadas como parâmetro, evitando efeitos colaterais indesejados. Se a lista de ações passada como parâmetro for null, a lista de ações do cenário será inicializada como uma lista vazia.
	 * @param acoes
	 */
	public void setAcoes(List<Acao> acoes) {
		this.acoes = new ArrayList<>();
		if (acoes != null) {
			for (Acao acao : acoes) {
				if (acao != null)
					this.acoes.add(acao.clone());
			}
		}
	}

	/**
	 * Método para adicionar uma ação ao cenário, que recebe uma ação como parâmetro e a adiciona à lista de ações do cenário. Este método é utilizado para construir o cenário de forma incremental, permitindo que as ações sejam adicionadas uma a uma conforme necessário. A ação passada como parâmetro é clonada para garantir que a ação do cenário seja independente da ação passada como parâmetro, evitando efeitos colaterais indesejados.
	 * @param acao
	 */
	public void adicionarAcao(Acao acao) {
		if (acao != null)
			this.acoes.add(acao.clone());
	}

	/**
	 * Método para executar todas as ações do cenário, que recebe um objeto DomusControl como parâmetro e executa cada ação da lista de ações do cenário utilizando o método executar da classe Acao. Este método é utilizado para ativar o cenário em um ambiente DomusControl, aplicando as ações definidas no cenário aos dispositivos e configurações da casa associada ao cenário. Cada ação é executada sequencialmente, garantindo que o cenário funcione conforme o esperado, criando a experiência desejada para os usuários.
	 * @param dc
	 */
	public void executar(DomusControl dc) {
		for (Acao acao : acoes) {
			if (acao != null)
				acao.executar(dc);
		}
	}

	/**
	 * Executa todas as ações do cenário, mas restringe o efeito a uma divisão
	 * específica.
	 */
	public void executarNaDivisao(DomusControl dc, int idDivisao) {
		for (Acao acao : acoes) {
			if (acao != null) {
				Acao clonada = acao.clone(); // Clona para não alterar o cenário original permanentemente
				clonada.setDivisaoAlvo(idDivisao);
				clonada.executar(dc);
			}
		}
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Sair de casa", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Sair de casa" inclui ações como desligar as luzes da casa, fechar as cortinas e desligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Sair de casa" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario sairDeCasa(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesCasa(idCasa),
				Acao.fecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Sair de casa", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Jantar com amigos", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Jantar com amigos" inclui ações como ligar as luzes da casa, abrir as cortinas e ligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Jantar com amigos" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario jantarComAmigos(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Jantar com amigos", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Jantar romântico", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Jantar romântico" inclui ações como definir a intensidade das lâmpadas da casa para 35%, definir a cor das lâmpadas para "Amarelo Quente", ligar as luzes da casa, abrir as cortinas e ligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Jantar romântico" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario jantarRomantico(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 35),
				Acao.definirCorLampadasCasa(idCasa, "Amarelo Quente"),
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Jantar Romantico", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Cinema", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Cinema" inclui ações como definir a intensidade das lâmpadas da casa para 10%, definir a cor das lâmpadas para "Azul", desligar as luzes da casa, fechar as cortinas e ligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Cinema" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario cinema(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 10),
				Acao.definirCorLampadasCasa(idCasa, "Azul"),
				Acao.desligarLuzesCasa(idCasa),
				Acao.fecharCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Cinema", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Estudar", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Estudar" inclui ações como definir a intensidade das lâmpadas da casa para 100%, definir a cor das lâmpadas para "Branco Frio", ligar as luzes da casa, abrir as cortinas e desligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Estudar" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario estudar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 100),
				Acao.definirCorLampadasCasa(idCasa, "Branco Frio"),
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Estudar", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Deitar", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Deitar" inclui ações como desligar as luzes da casa, fechar as cortinas e desligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Deitar" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario deitar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesEFecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Deitar", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Acordar", que retorna um novo cenário com um conjunto específico de ações associadas a essa situação. O cenário "Acordar" inclui ações como abrir as cortinas, ligar as luzes da casa e ligar a coluna de som da casa. Este método é útil para fornecer aos usuários uma opção rápida e conveniente para configurar um cenário comum, permitindo que eles ativem o cenário "Acordar" com um único comando, em vez de configurar cada ação individualmente.
	 * @param id 
	 * @param idCasa
	 * @return Cenario
	 */
	public static Cenario acordar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.abrirCortinas(idCasa),
				Acao.ligarLuzesCasa(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Acordar", idCasa, acoes);
	}
	
	/**
	 * Método para representar o cenário como uma string, que retorna uma representação textual dos atributos do cenário. O método utiliza um StringBuilder para construir a string de forma eficiente, incluindo o ID, o nome, o ID da casa e a lista de ações do cenário. A lista de ações é representada de forma personalizada, mostrando apenas os nomes das ações e separando-as por " -> " para indicar a sequência de execução das ações no cenário. Esta representação é útil para exibir informações sobre o cenário em interfaces de usuário ou para fins de depuração e registro de atividades relacionadas ao cenário.
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cenario{")
				.append("id=").append(id)
				.append(", nome='").append(nome).append('\'')
				.append(", idCasa=").append(idCasa)
				.append(", acoes=");

		if (acoes.isEmpty()) {
			sb.append("[]");
		} else {
			sb.append('[');
			for (int i = 0; i < acoes.size(); i++) {
				sb.append(acoes.get(i).getNome());
				if (i < acoes.size() - 1)
					sb.append(" -> ");
			}
			sb.append(']');
		}

		sb.append('}');
		return sb.toString();
	}

	/**
	 * Método para comparar o cenário com outro objeto, que retorna um booleano indicando se os objetos são iguais ou não. A comparação é feita com base no ID do cenário, que é um identificador único para cada cenário. Se o objeto comparado for a mesma instância do cenário atual, o método retorna true. Se o objeto comparado for null ou de uma classe diferente, o método retorna false. Caso contrário, o método compara os IDs dos dois cenários e retorna true se forem iguais, indicando que os cenários são considerados iguais, ou false caso contrário.
	 * @param o
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cenario cenario = (Cenario) o;
		return id == cenario.id;
	}

	/**
	 * Método para calcular o código hash do cenário, que retorna um valor inteiro representando o código hash do cenário. O código hash é calculado com base no ID do cenário, utilizando o método hashCode da classe Integer para gerar um código hash consistente com a implementação do método equals. Este método é importante para garantir que os cenários possam ser armazenados e recuperados corretamente em estruturas de dados que utilizam códigos hash, como HashMap ou HashSet.
	 * @return int
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
}
