package src.main.automacao;

import src.main.controller.*;//DomusControl
import src.main.model.*;//Casa, Dispositivo, Utilizador

import java.io.Serializable;

/**
 * A classe Automacao representa uma automação configurável que pode ser ativada ou desativada, associada a uma condição e a uma ação. Ela é serializável para permitir a persistência do estado das automações. A classe inclui atributos para identificar a automação, seu nome, estado de ativação, condição associada, ação a ser executada, última execução e o ID da casa associada. Além disso, fornece métodos para ativar/desativar a automação, verificar se deve ser executada com base na condição e executar a ação correspondente utilizando o DomusControl para acessar os dispositivos da casa.
 */
public class Automacao implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private boolean ativa;
    private Condicao condicao;//deixar assim para ja
    private Acao acao;//deixar assim para ja
    private long ultimaExecucao;
    private int idCasa; // ID da casa associada à automação

    /**
     * Construtor da classe Automacao que inicializa os atributos com os valores fornecidos. O ID é um identificador único para a automação, o nome é uma descrição legível, o estado de ativação indica se a automação está ativa ou não, a condição define quando a automação deve ser executada, a ação especifica o que deve ser feito quando a automação é acionada, e o ID da casa associa a automação a uma casa específica. A última execução é inicializada como 0L, indicando que a automação ainda não foi executada.
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
     * Construtor padrão da classe Automacao que inicializa os atributos com valores padrão. O ID é definido como 0, o nome é uma string vazia, o estado de ativação é falso, a condição e a ação são nulas, a última execução é definida como 0L, e o ID da casa é definido como 0. Este construtor pode ser utilizado para criar uma automação sem fornecer detalhes específicos, permitindo que os atributos sejam configurados posteriormente por meio dos setters.
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
     * Construtor de cópia da classe Automacao que cria uma nova instância com os mesmos valores dos atributos de outra instância de Automacao fornecida como parâmetro. Este construtor é útil para criar uma cópia de uma automação existente, permitindo que as alterações na nova instância não afetem a original. No entanto, a cópia dos objetos Condicao e Acao é feita de forma rasa, o que significa que ambos os objetos referenciados na nova instância apontarão para os mesmos objetos na memória. Se for necessário uma cópia profunda, seria necessário implementar o método clone() em Condicao e Acao para garantir que as cópias sejam independentes.
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
     * Método getter para o atributo ID da automação. Este método retorna o valor do ID, que é um identificador único para a automação. O ID pode ser utilizado para diferenciar entre diferentes automações, especialmente quando armazenadas em uma coleção ou banco de dados. O método é simples e direto, permitindo que outras partes do código acessem o ID da automação de forma segura, sem expor diretamente o campo privado.
     * @return ID da automação
     */
    public int getId(){
        return this.id;
    }

    /**
     * Método getter para o atributo nome da automação. Este método retorna o valor do nome, que é uma descrição legível da automação. O nome pode ser utilizado para identificar a automação de forma mais amigável para os usuários, facilitando a compreensão do propósito da automação. Assim como o método getId(), este método é simples e direto, permitindo que outras partes do código acessem o nome da automação de forma segura, sem expor diretamente o campo privado.
     * @return nome da automação
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Método getter para o atributo ativa da automação. Este método retorna o valor do estado de ativação, que indica se a automação está ativa ou não. O estado de ativação é crucial para determinar se a automação deve ser considerada para execução quando as condições associadas forem atendidas. Se o método retornar true, significa que a automação está ativa e pode ser executada; se retornar false, a automação está desativada e não será executada mesmo que as condições sejam atendidas. Este método é essencial para controlar o comportamento da automação no sistema.
     * @return valor booleano indicando se a automação está ativa ou não
     */
    public boolean isAtiva(){
        return this.ativa;
    }

    /**
     * Método getter para o atributo condicao da automação. Este método retorna a instância de Condicao associada à automação, que define as condições sob as quais a automação deve ser executada. A condição é um componente fundamental da automação, pois determina quando a ação associada deve ser acionada. O método permite que outras partes do código acessem a condição da automação de forma segura, sem expor diretamente o campo privado. A condição pode ser composta por uma ou mais regras que precisam ser verificadas para que a automação seja considerada para execução.
     * @return condicao
     */
    public Condicao getCondicao(){
        return this.condicao;
    }

    /**
     * Método getter para o atributo acao da automação. Este método retorna a instância de Acao associada à automação, que define a ação a ser executada quando as condições associadas forem atendidas. A ação é o componente que especifica o que deve ser feito quando a automação é acionada, como ligar uma luz, ajustar a temperatura ou enviar uma notificação. O método permite que outras partes do código acessem a ação da automação de forma segura, sem expor diretamente o campo privado. A ação pode ser composta por uma ou mais operações que serão realizadas quando a automação for executada.
     * @return acao
     */
    public Acao getAcao(){
        return this.acao;
    }

    /**
     * Método getter para o atributo idCasa da automação. Este método retorna o valor do ID da casa associada à automação, que é um identificador que vincula a automação a uma casa específica no sistema. O ID da casa é importante para garantir que a automação seja aplicada apenas aos dispositivos e condições relevantes para aquela casa, evitando interferências entre automações de diferentes casas. O método permite que outras partes do código acessem o ID da casa de forma segura, sem expor diretamente o campo privado. O ID da casa pode ser utilizado para filtrar automações ao exibi-las para os usuários ou ao processá-las para execução.
     * @return ID da casa associada à automação
     */
    public int getIdCasa() {
        return this.idCasa;
    }
    

    //setters
    /**
     * Método setter para o atributo ID da automação. Este método permite definir o valor do ID, que é um identificador único para a automação. O ID pode ser utilizado para diferenciar entre diferentes automações, especialmente quando armazenadas em uma coleção ou banco de dados. O método é simples e direto, permitindo que outras partes do código definam o ID da automação de forma segura, sem expor diretamente o campo privado. É importante garantir que o ID seja único para evitar conflitos entre automações.
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Método setter para o atributo nome da automação. Este método permite definir o valor do nome, que é uma descrição legível da automação. O nome pode ser utilizado para identificar a automação de forma mais amigável para os usuários, facilitando a compreensão do propósito da automação. Assim como o método setId(), este método é simples e direto, permitindo que outras partes do código definam o nome da automação de forma segura, sem expor diretamente o campo privado. É importante escolher um nome descritivo para facilitar a identificação da automação pelos usuários.
     * @param nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Método setter para o atributo ativa da automação. Este método permite definir o valor do estado de ativação, que indica se a automação está ativa ou não. O estado de ativação é crucial para determinar se a automação deve ser considerada para execução quando as condições associadas forem atendidas. Se o método receber true, a automação será ativada e poderá ser executada; se receber false, a automação será desativada e não será executada mesmo que as condições sejam atendidas. Este método é essencial para controlar o comportamento da automação no sistema, permitindo que os usuários ou o sistema ativem ou desativem automações conforme necessário.
     * @param a
     */
    public void setAtiva(boolean a){
        this.ativa = a;
    }

    /**
     * Método setter para o atributo condicao da automação. Este método permite definir a instância de Condicao associada à automação, que define as condições sob as quais a automação deve ser executada. A condição é um componente fundamental da automação, pois determina quando a ação associada deve ser acionada. O método permite que outras partes do código definam a condição da automação de forma segura, sem expor diretamente o campo privado. A condição pode ser composta por uma ou mais regras que precisam ser verificadas para que a automação seja considerada para execução.
     * @param c
     */
    public void setCondicao(Condicao c){
        this.condicao = c;
    }

    /**
     * Método setter para o atributo acao da automação. Este método permite definir a instância de Acao associada à automação, que define a ação a ser executada quando as condições associadas forem atendidas. A ação é o componente que especifica o que deve ser feito quando a automação é acionada, como ligar uma luz, ajustar a temperatura ou enviar uma notificação. O método permite que outras partes do código definam a ação da automação de forma segura, sem expor diretamente o campo privado. A ação pode ser composta por uma ou mais operações que serão realizadas quando a automação for executada.
     * @param a
     */
    public void setAcao(Acao a){
        this.acao = a;
    }

    /**
     * Método setter para o atributo idCasa da automação. Este método permite definir o valor do ID da casa associada à automação, que é um identificador que vincula a automação a uma casa específica no sistema. O ID da casa é importante para garantir que a automação seja aplicada apenas aos dispositivos e condições relevantes para aquela casa, evitando interferências entre automações de diferentes casas. O método permite que outras partes do código definam o ID da casa de forma segura, sem expor diretamente o campo privado. O ID da casa pode ser utilizado para filtrar automações ao exibi-las para os usuários ou ao processá-las para execução.
     * @param idCasa
     */
    public void setIdCasa(int idCasa){
        this.idCasa = idCasa;
    }

    //metodos
    /**
     * Método para ativar a automação. Este método define o estado de ativação da automação como verdadeiro, indicando que a automação está ativa e pode ser considerada para execução quando as condições associadas forem atendidas. Ativar a automação permite que ela seja acionada automaticamente pelo sistema quando as condições definidas forem verificadas, ou manualmente pelos usuários. Este método é essencial para controlar o comportamento da automação, permitindo que os usuários ou o sistema ativem a automação conforme necessário.
     */
    public void ativar(){
        this.ativa = true;
    }

    /**
     * Método para desativar a automação. Este método define o estado de ativação da automação como falso, indicando que a automação está desativada e não será considerada para execução mesmo que as condições associadas sejam atendidas. Desativar a automação impede que ela seja acionada automaticamente pelo sistema ou manualmente pelos usuários, o que pode ser útil em situações onde a automação não é mais necessária ou quando se deseja evitar interferências com outras automações. Este método é essencial para controlar o comportamento da automação, permitindo que os usuários ou o sistema desativem a automação conforme necessário.
     */
    public void desativar(){
        this.ativa = false;
    }

    // Verifica se a automação deve ser executada com base na condição e no estado de ativação
    /**
     * Método para verificar se a automação deve ser executada. Este método retorna true se a automação estiver ativa, se a condição associada não for nula e se a condição for verificada com sucesso utilizando o DomusControl fornecido como parâmetro. Caso contrário, retorna false. Este método é crucial para determinar se a automação deve ser acionada, garantindo que apenas automações ativas e com condições válidas sejam consideradas para execução. A verificação da condição é realizada por meio do método verificar() da classe Condicao, que avalia as regras definidas para a automação com base no estado atual dos dispositivos e condições do sistema.
     * @param dc
     * @return true se a automação deve ser executada, false caso contrário
     */
    public boolean deveExecutar(DomusControl dc){
        return this.ativa && this.condicao != null && this.condicao.verificar(dc);
    }

    // Retorna true se a automação foi executada, false caso contrário
    /**
     * Método para executar a automação. Este método verifica primeiro se a automação deve ser executada utilizando o método deveExecutar() com o DomusControl fornecido como parâmetro. Se a automação não deve ser executada ou se a ação associada for nula, o método retorna false, indicando que a automação não foi executada. Caso contrário, o método chama o método executar() da classe Acao para realizar a ação definida para a automação, atualiza o atributo ultimaExecucao com o timestamp atual e retorna true, indicando que a automação foi executada com sucesso. Este método é essencial para acionar as ações associadas às automações quando as condições forem atendidas, garantindo que as automações sejam processadas corretamente pelo sistema.
     * @param dc
     * @return
     */
    public boolean executar(DomusControl dc){
        if(!deveExecutar(dc) || this.acao == null) return false;
        this.acao.executar(dc);
        this.ultimaExecucao = System.currentTimeMillis();
        return true;
    }

    /**
     * Método getter para o atributo ultimaExecucao da automação. Este método retorna o valor do timestamp da última execução da automação, que é atualizado sempre que a automação é executada com sucesso. O timestamp é representado como um valor long, que corresponde ao número de milissegundos desde a época (1º de janeiro de 1970). Este método pode ser utilizado para monitorar quando a automação foi executada pela última vez, o que pode ser útil para fins de registro, depuração ou para implementar lógica adicional baseada no tempo desde a última execução.
     * @return
     */
    public long getUltimaExecucao() {
        return this.ultimaExecucao;
    }

    /**
     * Método para criar uma cópia da automação. Este método retorna uma nova instância de Automacao que é uma cópia da instância atual, utilizando o construtor de cópia definido na classe. A cópia é feita de forma rasa, o que significa que os objetos Condicao e Acao referenciados na nova instância apontarão para os mesmos objetos na memória. Se for necessário uma cópia profunda, seria necessário implementar o método clone() em Condicao e Acao para garantir que as cópias sejam independentes. 
     */
    @Override
    public Automacao clone() {
        return new Automacao(this);
    }

    /**
     * Método para representar a automação como uma string. Este método retorna uma representação em formato de string da automação, incluindo o ID, nome, estado de ativação e o timestamp da última execução. A representação é formatada de forma legível, facilitando a compreensão das informações principais da automação. Este método é útil para fins de depuração, registro ou para exibir informações sobre a automação em interfaces de usuário ou logs do sistema.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automacao automacao = (Automacao) o;
        return id == automacao.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
}
