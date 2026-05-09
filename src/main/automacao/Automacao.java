package src.main.automacao;

import src.main.controller.*;//DomusControl
import src.main.model.*;//Casa, Dispositivo, Utilizador

import java.io.Serializable;

public class Automacao implements Serializable{
    /**
     * A classe Automacao representa uma automação doméstica que pode ser configurada para executar ações específicas com base em condições predefinidas. Cada automação possui um identificador único, um nome, um estado de ativação, uma condição que determina quando a automação deve ser executada, uma ação que define o que deve ser feito quando a automação é acionada, a última vez que a automação foi executada e o identificador da casa associada à automação. A classe implementa a interface Serializable para permitir a serialização dos objetos de automação, facilitando o armazenamento e a transferência de dados relacionados às automações.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID único da automação, utilizado para identificação e comparação de automações.
     */
    private int id;
    /**
     * Nome da automação, utilizado para identificação e descrição da automação.
     */
    private String nome;
    /**
     * Indica se a automação está ativa ou não. Se a automação estiver ativa, ela pode ser executada quando as condições forem atendidas. Caso contrário, a automação não será executada mesmo que as condições sejam atendidas.
     */
    private boolean ativa;
    /**
     * Condição que determina quando a automação deve ser executada. A condição é uma interface funcional que define um método verificar, que recebe um objeto DomusControl como parâmetro e retorna um booleano indicando se a condição foi atendida ou não. A automação será executada apenas se a condição for atendida e a automação estiver ativa.
     */
    private Condicao condicao;
    /**
     * Ação que define o que deve ser feito quando a automação é acionada. A ação é uma interface funcional que define um método executar, que recebe um objeto DomusControl como parâmetro e realiza as operações necessárias para executar a ação. A ação será executada apenas se a condição for atendida e a automação estiver ativa.
     */
    private Acao acao;

    /**
     * Atributo que armazena a última vez que a automação foi executada, representada como um timestamp em milissegundos. Este atributo pode ser utilizado para evitar execuções repetidas da automação em um curto período de tempo, garantindo que a automação seja executada apenas quando necessário e evitando sobrecarga no sistema.
     */
    private long ultimaExecucao;
    /**
     * ID da casa associada à automação, utilizado para identificar a qual casa a automação pertence. Este atributo é importante para garantir que as automações sejam aplicadas corretamente às casas correspondentes, permitindo que o sistema de automação funcione de maneira eficiente e organizada.
     */
    private int idCasa; 

    /**
     * Construtor da classe Automacao, que inicializa os atributos da automação com os valores fornecidos como parâmetros. O construtor recebe o ID da automação, o nome, o estado de ativação, a condição, a ação e o ID da casa associada à automação. A última execução é inicializada com o valor 0L, indicando que a automação ainda não foi executada.
     * @param id
     * @param nome
     * @param ativa
     * @param condicao
     * @param acao
     * @param idCasa
     */
    public Automacao(int id, String nome, boolean ativa, Condicao condicao, Acao acao, int idCasa) {
        this.id = id;
        this.nome = nome;
        this.ativa = ativa;
        this.condicao = condicao;
        this.acao = acao;
        this.ultimaExecucao = 0L; 
        this.idCasa = idCasa;
    }

    /**
     * Construtor padrão da classe Automacao, que inicializa os atributos da automação com valores padrão. O ID é inicializado com 0, o nome é inicializado como uma string vazia, o estado de ativação é inicializado como false, a condição e a ação são inicializadas como null, a última execução é inicializada com o valor 0L e o ID da casa é inicializado com 0. Este construtor pode ser utilizado para criar uma automação sem fornecer valores específicos, permitindo que os atributos sejam configurados posteriormente por meio dos setters.
     */
    public Automacao() {
        this.id = 0;
        this.nome = "";
        this.ativa = false;
        this.condicao = null;
        this.acao = null;
        this.ultimaExecucao = 0L; 
        this.idCasa = 0;
    }

    /**
     * Construtor de cópia da classe Automacao, que cria uma nova instância de Automacao com os mesmos valores dos atributos da automação fornecida como parâmetro. O construtor recebe um objeto Automacao e copia os valores dos atributos id, nome, ativa, condicao, acao, ultimaExecucao e idCasa para a nova instância. A cópia é feita de forma rasa, o que significa que os objetos referenciados por condicao e acao não são clonados, mas sim compartilhados entre as duas instâncias. Se for necessário criar uma cópia profunda desses objetos, será necessário implementar o método clone() nas classes Condicao e Acao.
     * @param a
     */
    public Automacao(Automacao a) {
        this.id = a.id;
        this.nome = a.nome;
        this.ativa = a.ativa;
        this.condicao = a.condicao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.acao = a.acao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.ultimaExecucao = a.ultimaExecucao;
        this.idCasa = a.idCasa;
    }

    //getters
    /**
     * Método getter para o atributo id da automação, que retorna o valor do ID da automação. O ID é um número inteiro que serve como identificador único para cada automação, permitindo que as automações sejam diferenciadas e gerenciadas de forma eficiente. Este método é utilizado para acessar o ID da automação em outras partes do código, como em operações de comparação, armazenamento ou exibição de informações relacionadas à automação.
     * @return int
     */
    public int getId(){
        return this.id;
    }

    /**
     * Método getter para o atributo nome da automação, que retorna o nome da automação como uma string. O nome é utilizado para identificar e descrever a automação de forma mais amigável e compreensível para os usuários, facilitando a gestão e a organização das automações no sistema. Este método é utilizado para acessar o nome da automação em outras partes do código, como em operações de exibição de informações ou em interfaces de usuário onde o nome da automação é apresentado.
     * @return String
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Método getter para o atributo ativa da automação, que retorna um booleano indicando se a automação está ativa ou não. Se a automação estiver ativa, ela pode ser executada quando as condições forem atendidas. Caso contrário, a automação não será executada mesmo que as condições sejam atendidas. Este método é utilizado para verificar o estado de ativação da automação em outras partes do código, como em operações de execução da automação ou em interfaces de usuário onde o estado de ativação é apresentado.
     * @return boolean
     */
    public boolean isAtiva(){
        return this.ativa;
    }

    /**
     * Método getter para o atributo condicao da automação, que retorna a condição associada à automação. A condição é uma interface funcional que define um método verificar, que recebe um objeto DomusControl como parâmetro e retorna um booleano indicando se a condição foi atendida ou não. A automação será executada apenas se a condição for atendida e a automação estiver ativa. Este método é utilizado para acessar a condição da automação em outras partes do código, como em operações de execução da automação ou em interfaces de usuário onde as condições associadas às automações são apresentadas.
     * @return Condicao
     */
    public Condicao getCondicao(){
        return this.condicao;
    }

    /**
     * Método getter para o atributo acao da automação, que retorna a ação associada à automação. A ação é uma interface funcional que define um método executar, que recebe um objeto DomusControl como parâmetro e realiza as operações necessárias para executar a ação. A ação será executada apenas se a condição for atendida e a automação estiver ativa. Este método é utilizado para acessar a ação da automação em outras partes do código, como em operações de execução da automação ou em interfaces de usuário onde as ações associadas às automações são apresentadas.
     * @return Acao
     */
    public Acao getAcao(){
        return this.acao;
    }

    /**
     * Método getter para o atributo idCasa da automação, que retorna o ID da casa associada à automação. O ID da casa é um número inteiro que serve para identificar a qual casa a automação pertence, permitindo que as automações sejam aplicadas corretamente às casas correspondentes. Este método é utilizado para acessar o ID da casa associada à automação em outras partes do código, como em operações de execução da automação ou em interfaces de usuário onde as informações sobre as casas associadas às automações são apresentadas.
     * @return int
     */
    public int getIdCasa() {
        return this.idCasa;
    }
    

    //setters
    /**
     * Método setter para o atributo id da automação, que define o valor do ID da automação. O ID é um número inteiro que serve como identificador único para cada automação, permitindo que as automações sejam diferenciadas e gerenciadas de forma eficiente. Este método é utilizado para configurar o ID da automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde o ID da automação é definido.
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Método setter para o atributo nome da automação, que define o nome da automação como uma string. O nome é utilizado para identificar e descrever a automação de forma mais amigável e compreensível para os usuários, facilitando a gestão e a organização das automações no sistema. Este método é utilizado para configurar o nome da automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde o nome da automação é definido.
     * @param nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Método setter para o atributo ativa da automação, que define o estado de ativação da automação. Se a automação estiver ativa, ela pode ser executada quando as condições forem atendidas. Caso contrário, a automação não será executada mesmo que as condições sejam atendidas. Este método é utilizado para configurar o estado de ativação da automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde o estado de ativação da automação é definido.
     * @param a
     */
    public void setAtiva(boolean a){
        this.ativa = a;
    }

    /**
     * Método setter para o atributo condicao da automação, que define a condição associada à automação. A condição é uma interface funcional que define um método verificar, que recebe um objeto DomusControl como parâmetro e retorna um booleano indicando se a condição foi atendida ou não. A automação será executada apenas se a condição for atendida e a automação estiver ativa. Este método é utilizado para configurar a condição da automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde as condições associadas às automações são definidas.
     * @param c
     */
    public void setCondicao(Condicao c){
        this.condicao = c;
    }

    /**
     * Método setter para o atributo acao da automação, que define a ação associada à automação. A ação é uma interface funcional que define um método executar, que recebe um objeto DomusControl como parâmetro e realiza as operações necessárias para executar a ação. A ação será executada apenas se a condição for atendida e a automação estiver ativa. Este método é utilizado para configurar a ação da automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde as ações associadas às automações são definidas.
     * @param a
     */
    public void setAcao(Acao a){
        this.acao = a;
    }

    /**
     * Método setter para o atributo idCasa da automação, que define o ID da casa associada à automação. O ID da casa é um número inteiro que serve para identificar a qual casa a automação pertence, permitindo que as automações sejam aplicadas corretamente às casas correspondentes. Este método é utilizado para configurar o ID da casa associada à automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde as informações sobre as casas associadas às automações são definidas.
     * @param idCasa
     */
    public void setIdCasa(int idCasa){
        this.idCasa = idCasa;
    }

    //metodos
    /**
     * Método para ativar a automação, que define o estado de ativação da automação como true. Quando a automação está ativa, ela pode ser executada quando as condições forem atendidas. Este método é utilizado para ativar a automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde o estado de ativação da automação é controlado.
     */
    public void ativar(){
        this.ativa = true;
    }

    /**
     * Método para desativar a automação, que define o estado de ativação da automação como false. Quando a automação está desativada, ela não será executada mesmo que as condições sejam atendidas. Este método é utilizado para desativar a automação em outras partes do código, como em operações de criação de novas automações ou em interfaces de usuário onde o estado de ativação da automação é controlado.
     */
    public void desativar(){
        this.ativa = false;
    }

    // Verifica se a automação deve ser executada com base na condição e no estado de ativação
    /**
     * Método para verificar se a automação deve ser executada, que retorna um booleano indicando se a automação deve ser executada ou não. A automação deve ser executada apenas se estiver ativa e se a condição associada à automação for atendida. O método recebe um objeto DomusControl como parâmetro, que é utilizado para verificar a condição da automação. Se a automação estiver ativa e a condição for atendida, o método retorna true, indicando que a automação deve ser executada. Caso contrário, o método retorna false, indicando que a automação não deve ser executada.
     * @param dc
     * @return boolean
     */
    public boolean deveExecutar(DomusControl dc){
        return this.ativa && this.condicao != null && this.condicao.verificar(dc);
    }

    // Retorna true se a automação foi executada, false caso contrário
    /**
     * Método para executar a automação, que retorna um booleano indicando se a automação foi executada ou não. A automação será executada apenas se a condição associada à automação for atendida e se a automação estiver ativa. O método recebe um objeto DomusControl como parâmetro, que é utilizado para verificar a condição da automação e para executar a ação associada à automação. Se a automação for executada com sucesso, o método atualiza o atributo ultimaExecucao com o timestamp atual em milissegundos e retorna true. Caso contrário, o método retorna false, indicando que a automação não foi executada.
     * @param dc
     * @return boolean
     */
    public boolean executar(DomusControl dc){
        if(!deveExecutar(dc) || this.acao == null) return false;
        this.acao.executar(dc);
        this.ultimaExecucao = System.currentTimeMillis();
        return true;
    }

    /**
     * Método getter para o atributo ultimaExecucao da automação, que retorna a última vez que a automação foi executada como um timestamp em milissegundos. Este método é utilizado para acessar a última execução da automação em outras partes do código, como em operações de verificação de tempo entre execuções ou em interfaces de usuário onde as informações sobre a última execução da automação são apresentadas.
     * @return long 
     */
    public long getUltimaExecucao() {
        return this.ultimaExecucao;
    }

    /**
     * Método para criar uma cópia da automação, que retorna uma nova instância de Automacao com os mesmos valores dos atributos da automação atual. O método utiliza o construtor de cópia da classe Automacao para criar a nova instância, passando a automação atual como parâmetro. A cópia é feita de forma rasa, o que significa que os objetos referenciados por condicao e acao não são clonados, mas sim compartilhados entre as duas instâncias. Se for necessário criar uma cópia profunda desses objetos, será necessário implementar o método clone() nas classes Condicao e Acao.
      * @return Automacao
     */
    @Override
    public Automacao clone() {
        return new Automacao(this);
    }

    /**
     * Método para representar a automação como uma string, que retorna uma representação textual dos atributos da automação. O método utiliza a sintaxe de string formatada para criar uma string que inclui o ID, o nome, o estado de ativação e a última execução da automação. Esta representação é útil para exibir informações sobre a automação em interfaces de usuário ou para fins de depuração e registro de atividades relacionadas à automação.
      * @return String
     */
    @Override
    public String toString(){
        return "Automação{" +
                "id= " + id +
                ", nome= '" + nome + '\'' +
                ", ativa= " + ativa +
                "ultimaExecucao= " + ultimaExecucao +
                '}';
    }

    /**
     * Método para comparar a automação com outro objeto, que retorna um booleano indicando se os objetos são iguais ou não. A comparação é feita com base no ID da automação, que é um identificador único para cada automação. Se o objeto comparado for a mesma instância da automação atual, o método retorna true. Se o objeto comparado for null ou de uma classe diferente, o método retorna false. Caso contrário, o método compara os IDs das duas automações e retorna true se forem iguais, indicando que as automações são consideradas iguais, ou false caso contrário.
      * @param o
      * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automacao automacao = (Automacao) o;
        return id == automacao.id;
    }

    /**
     * Método para calcular o código hash da automação, que retorna um valor inteiro representando o código hash da automação. O código hash é calculado com base no ID da automação, utilizando o método hashCode da classe Integer para gerar um código hash consistente com a implementação do método equals. Este método é importante para garantir que as automações possam ser armazenadas e recuperadas corretamente em estruturas de dados que utilizam códigos hash, como HashMap ou HashSet.
      * @return int
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
}
