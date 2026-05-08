package src.main.automacao;

import src.main.controller.DomusControl;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A classe Cenario representa um cenário de automação que pode ser executado em uma casa inteligente. Um cenário é composto por um conjunto de ações que são realizadas quando o cenário é ativado. Cada cenário tem um ID único, um nome descritivo, um ID da casa associada e uma lista de ações a serem executadas. A classe implementa Serializable para permitir a persistência dos cenários em arquivos ou bancos de dados. O método executar() é responsável por executar todas as ações do cenário utilizando o DomusControl, enquanto o método executarNaDivisao() permite restringir a execução das ações a uma divisão específica da casa. A classe também inclui métodos estáticos para criar cenários pré-definidos, como "Sair de casa", "Jantar com amigos", "Jantar Romantico", "Cinema", "Estudar", "Deitar" e "Acordar".
 */
public class Cenario implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * ID único do cenário, utilizado para identificar e referenciar o cenário no sistema de automação. 
	 */
	private int id;

	/**
	 * Nome descritivo do cenário, utilizado para identificar e diferenciar o cenário de outros cenários no sistema de automação. O nome é importante para fornecer uma descrição legível e compreensível do cenário, facilitando a identificação e a seleção do cenário pelos usuários.
	 */
	private String nome;

	/**
	 * ID da casa associada ao cenário, utilizado para vincular o cenário a uma casa específica no sistema de automação. O ID da casa é importante para garantir que o cenário seja aplicado apenas aos dispositivos e condições relevantes para aquela casa, evitando interferências entre cenários de diferentes casas.
	 */
	private int idCasa;

	/**
	 * Lista de ações associadas ao cenário, que são as ações a serem executadas quando o cenário for ativado. A lista de ações é importante para definir o comportamento do cenário, permitindo que os usuários ou o sistema especifiquem quais ações devem ser realizadas quando o cenário for acionado.
	 */
	private List<Acao> acoes;

	/**
	 * Construtor padrão para a classe Cenario. Este construtor inicializa um cenário com valores padrão, onde o ID é definido como 0, o nome é uma string vazia, o ID da casa é 0 e a lista de ações é inicializada como uma lista vazia. Este construtor pode ser utilizado para criar um cenário vazio que pode ser preenchido posteriormente com informações específicas, como um nome descritivo, um ID de casa válido e uma lista de ações a serem executadas quando o cenário for ativado.
	 */
	public Cenario() {
		this.id = 0;
		this.nome = "";
		this.idCasa = 0;
		this.acoes = new ArrayList<>();
	}

	/**
	 * Construtor parametrizado para a classe Cenario. Este construtor permite criar um cenário com um ID específico, um nome descritivo, um ID de casa associado e uma lista de ações a serem executadas. O construtor recebe os parâmetros id, nome, idCasa e acoes, e inicializa os atributos correspondentes da classe. A lista de ações é copiada para garantir que o cenário tenha sua própria lista independente, evitando que alterações na lista original afetem o cenário criado. Este construtor é útil para criar cenários personalizados com informações específicas desde o momento da criação.
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
	 * Construtor de cópia para a classe Cenario. Este construtor cria um novo cenário copiando os atributos de um cenário existente fornecido como parâmetro. O construtor recebe um objeto Cenario e inicializa os atributos do novo cenário com os valores correspondentes do cenário original. A lista de ações é copiada para garantir que o novo cenário tenha sua própria lista independente, evitando que alterações na lista original afetem o novo cenário criado. Este construtor é útil para criar uma cópia de um cenário existente, permitindo que o novo cenário seja modificado sem afetar o cenário original.
	 * @param c
	 */
	public Cenario(Cenario c) {
		this(c.id, c.nome, c.idCasa, c.acoes);
	}

	/**
	 * Método getter para o atributo id do cenário. Este método retorna o valor do ID do cenário, que é um identificador único utilizado para distinguir este cenário de outros cenários no sistema. O ID é importante para gerenciar e referenciar cenários de forma eficiente, permitindo que os usuários ou o sistema acessem, modifiquem ou excluam cenários específicos com base em seu ID. Este método é essencial para obter o ID do cenário quando necessário, como ao exibir informações sobre o cenário ou ao realizar operações que requerem a identificação do cenário.
	 * @return o ID do cenário
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter para o atributo id do cenário. Este método permite definir o valor do ID do cenário, que é um identificador único utilizado para distinguir este cenário de outros cenários no sistema. O ID é importante para gerenciar e referenciar cenários de forma eficiente, permitindo que os usuários ou o sistema acessem, modifiquem ou excluam cenários específicos com base em seu ID. Este método é essencial para definir o ID do cenário quando necessário, como ao criar um novo cenário ou ao modificar o ID de um cenário existente.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método getter para o atributo nome do cenário. Este método retorna o valor do nome do cenário, que é uma string descritiva utilizada para identificar e diferenciar este cenário de outros cenários no sistema. O nome é importante para fornecer uma descrição legível e compreensível do cenário, facilitando a identificação e a seleção do cenário pelos usuários. Este método é essencial para obter o nome do cenário quando necessário, como ao exibir informações sobre o cenário ou ao permitir que os usuários escolham um cenário específico com base em seu nome.
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método setter para o atributo nome do cenário. Este método permite definir o valor do nome do cenário, que é uma string descritiva utilizada para identificar e diferenciar este cenário de outros cenários no sistema. O nome é importante para fornecer uma descrição legível e compreensível do cenário, facilitando a identificação e a seleção do cenário pelos usuários. Este método é essencial para definir o nome do cenário quando necessário, como ao criar um novo cenário ou ao modificar o nome de um cenário existente.
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método getter para o atributo idCasa do cenário. Este método retorna o valor do ID da casa associada ao cenário, que é um identificador utilizado para vincular este cenário a uma casa específica no sistema. O ID da casa é importante para garantir que o cenário seja aplicado apenas aos dispositivos e condições relevantes para aquela casa, evitando interferências entre cenários de diferentes casas. Este método é essencial para obter o ID da casa associada ao cenário quando necessário, como ao exibir informações sobre o cenário ou ao filtrar cenários com base na casa associada.
	 */
	public int getIdCasa() {
		return idCasa;
	}

	/**
	 * Método setter para o atributo idCasa do cenário. Este método permite definir o valor do ID da casa associada ao cenário, que é um identificador utilizado para vincular este cenário a uma casa específica no sistema. O ID da casa é importante para garantir que o cenário seja aplicado apenas aos dispositivos e condições relevantes para aquela casa, evitando interferências entre cenários de diferentes casas. Este método é essencial para definir o ID da casa associada ao cenário quando necessário, como ao criar um novo cenário ou ao modificar o ID da casa de um cenário existente.
	 * @param idCasa
	 */
	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	/**
	 * Método getter para o atributo acoes do cenário. Este método retorna uma lista imutável de ações associadas ao cenário, que são as ações a serem executadas quando o cenário for ativado. A lista de ações é importante para definir o comportamento do cenário, permitindo que os usuários ou o sistema especifiquem quais ações devem ser realizadas quando o cenário for acionado. Este método é essencial para obter a lista de ações associada ao cenário quando necessário, como ao exibir informações sobre o cenário ou ao executar as ações do cenário utilizando o DomusControl.
	 * @return uma lista imutável de ações associadas ao cenário
	 */
	public List<Acao> getAcoes() {
		return Collections.unmodifiableList(acoes);
	}

	/**
	 * Método setter para o atributo acoes do cenário. Este método permite definir a lista de ações associadas ao cenário, que são as ações a serem executadas quando o cenário for ativado. A lista de ações é importante para definir o comportamento do cenário, permitindo que os usuários ou o sistema especifiquem quais ações devem ser realizadas quando o cenário for acionado. Este método é essencial para definir a lista de ações associada ao cenário quando necessário, como ao criar um novo cenário ou ao modificar as ações de um cenário existente. O método realiza uma cópia da lista de ações fornecida para garantir que o cenário tenha sua própria lista independente, evitando que alterações na lista original afetem o cenário.
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
	 * Método para adicionar uma ação à lista de ações do cenário. Este método recebe um objeto Acao como parâmetro e adiciona uma cópia da ação à lista de ações do cenário. A adição de ações é importante para definir o comportamento do cenário, permitindo que os usuários ou o sistema especifiquem quais ações devem ser realizadas quando o cenário for acionado. Este método é essencial para adicionar ações ao cenário de forma incremental, permitindo que as ações sejam adicionadas uma a uma conforme necessário. O método realiza uma cópia da ação fornecida para garantir que o cenário tenha sua própria instância da ação, evitando que alterações na ação original afetem o cenário.
	 * @param acao
	 */
	public void adicionarAcao(Acao acao) {
		if (acao != null)
			this.acoes.add(acao.clone());
	}

	/**
	 * Método para executar todas as ações do cenário utilizando o DomusControl fornecido como parâmetro. Este método itera sobre a lista de ações do cenário e chama o método executar() de cada ação, passando o DomusControl como argumento. A execução das ações é importante para realizar as operações definidas no cenário quando ele for ativado, permitindo que os usuários ou o sistema acione as ações associadas ao cenário de forma automática. Este método é essencial para acionar as ações do cenário utilizando o DomusControl, garantindo que as operações definidas nas ações sejam realizadas corretamente quando o cenário for executado.
	 * O método verifica se cada ação é diferente de null antes de executá-la para evitar possíveis NullPointerExceptions. Se uma ação for null, ela será ignorada e a execução continuará com as próximas ações na lista.
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
	 * Método estático para criar um cenário pré-definido de "Sair de casa". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Sair de casa" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Sair de casa" são: desligar as luzes da casa, fechar as cortinas e desligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando saem de casa.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
	 */
	public static Cenario sairDeCasa(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesCasa(idCasa),
				Acao.fecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Sair de casa", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Jantar com amigos". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Jantar com amigos" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Jantar com amigos" são: ligar as luzes da casa, abrir as cortinas e ligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando recebem amigos para jantar.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
	 */
	public static Cenario jantarComAmigos(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Jantar com amigos", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Jantar Romantico". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Jantar Romantico" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Jantar Romantico" são: definir a intensidade das lâmpadas da casa para 35%, definir a cor das lâmpadas para "Amarelo Quente", ligar as luzes da casa, abrir as cortinas e ligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando desejam criar um ambiente romântico para um jantar especial.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
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
	 * Método estático para criar um cenário pré-definido de "Cinema". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Cinema" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Cinema" são: definir a intensidade das lâmpadas da casa para 10%, definir a cor das lâmpadas para "Azul", desligar as luzes da casa, fechar as cortinas e ligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando desejam criar um ambiente adequado para assistir a filmes ou programas de TV.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
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
	 * Método estático para criar um cenário pré-definido de "Estudar". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Estudar" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Estudar" são: definir a intensidade das lâmpadas da casa para 100%, definir a cor das lâmpadas para "Branco Frio", ligar as luzes da casa, abrir as cortinas e desligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando desejam criar um ambiente adequado para estudar ou trabalhar.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
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
	 * Método estático para criar um cenário pré-definido de "Deitar". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Deitar" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Deitar" são: desligar as luzes da casa, fechar as cortinas e desligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando desejam criar um ambiente adequado para dormir ou descansar.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
	 */
	public static Cenario deitar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesEFecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Deitar", idCasa, acoes);
	}

	/**
	 * Método estático para criar um cenário pré-definido de "Acordar". Este método recebe um ID e um ID de casa como parâmetros e retorna um novo cenário com o nome "Acordar" e uma lista de ações específicas para esse cenário. As ações incluídas no cenário "Acordar" são: abrir as cortinas, ligar as luzes da casa e ligar a coluna de som da casa. Este método é útil para criar rapidamente um cenário comum que pode ser utilizado pelos usuários para configurar suas casas inteligentes de forma eficiente quando desejam criar um ambiente adequado para acordar ou começar o dia.
	 * O método utiliza a classe Acao para criar as ações específicas do cenário, garantindo que as ações sejam definidas corretamente com base no ID da casa fornecido. O cenário criado é retornado como um objeto Cenario, que pode ser adicionado ao sistema de automação para ser utilizado pelos usuários.
	 * @param id
	 * @param idCasa
	 * @return
	 */
	public static Cenario acordar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.abrirCortinas(idCasa),
				Acao.ligarLuzesCasa(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Acordar", idCasa, acoes);
	}

	/**
	 * Método para representar o cenário como uma string. Este método retorna uma representação em formato de string do cenário, incluindo o ID, nome, ID da casa e a lista de ações associadas ao cenário. A representação é formatada de forma legível, facilitando a compreensão das informações principais do cenário. Este método é útil para fins de depuração, registro ou para exibir informações sobre o cenário em interfaces de usuário ou logs do sistema.
	 * A representação da lista de ações é feita de forma simplificada, exibindo apenas os nomes das ações em sequência, separados por " -> ". Se a lista de ações estiver vazia, será exibida como "[]".
	 * @return uma string representando o cenário
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
	 * Método para comparar dois objetos Cenario. Este método verifica se o objeto fornecido como parâmetro é igual ao cenário atual, comparando os IDs dos cenários. Se os IDs forem iguais, os cenários são considerados iguais. Este método é importante para garantir que a comparação entre cenários seja feita de forma consistente e eficiente, permitindo que os usuários ou o sistema verifiquem se dois cenários são equivalentes com base em seus IDs. O método também verifica se o objeto fornecido é do tipo Cenario antes de realizar a comparação, garantindo que a comparação seja feita apenas entre objetos do mesmo tipo.
	 * O método hashCode() é implementado para garantir que objetos iguais tenham o mesmo código de hash, o que é importante para o funcionamento correto de coleções baseadas em hash, como HashSet ou HashMap, onde a igualdade dos objetos é determinada pelo método equals() e o código de hash é utilizado para organizar os objetos na coleção.
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
	 * Método para calcular o código de hash do cenário. Este método retorna um código de hash baseado no ID do cenário, garantindo que objetos iguais (com o mesmo ID) tenham o mesmo código de hash. O código de hash é importante para o funcionamento correto de coleções baseadas em hash, como HashSet ou HashMap, onde a igualdade dos objetos é determinada pelo método equals() e o código de hash é utilizado para organizar os objetos na coleção. Este método é essencial para garantir que a implementação do método equals() seja consistente com a implementação do método hashCode(), seguindo as regras gerais de contrato entre esses dois métodos.
	 */
	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
}
