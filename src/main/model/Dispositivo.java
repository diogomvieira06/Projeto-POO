package src.main.model;
import java.io.Serializable;

/**
 * Classe abstrata que representa um dispositivo genérico, contendo atributos comuns a todos os tipos de dispositivos, como ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. A classe inclui métodos para ligar e desligar o dispositivo, adicionar tempo de uso, obter o estado do dispositivo e acessar os atributos. A implementação de clone é abstrata, permitindo que as subclasses criem cópias de suas instâncias. A classe também inclui métodos para obter o tipo do dispositivo e detalhes específicos relacionados ao dispositivo, que podem ser sobrescritos pelas subclasses para fornecer informações adicionais.
 */
public abstract class Dispositivo implements Serializable {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Dispositivo. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID único do dispositivo, utilizado para identificação e comparação entre dispositivos. O ID é um número inteiro que deve ser atribuído de forma única a cada dispositivo para garantir que cada dispositivo possa ser identificado de maneira distinta.
     */
    private int id;

    /**
     * Marca do dispositivo, representada como uma string. A marca é utilizada para identificar o fabricante ou a marca do dispositivo, fornecendo informações adicionais sobre a origem e a qualidade do dispositivo.
     */
    private String marca;
    
    /**
     * Modelo do dispositivo, representado como uma string. O modelo é utilizado para identificar a versão ou o tipo específico do dispositivo, fornecendo informações adicionais sobre as características e funcionalidades do dispositivo.
     */
    private String modelo;

    /**
     * Consumo por hora do dispositivo em Wh, representado como um número de ponto flutuante. O consumo por hora é utilizado para calcular o consumo total de energia do dispositivo com base no tempo de uso, permitindo que os usuários monitorem e gerenciem o consumo de energia de seus dispositivos de forma eficiente.
     */
    private double consumo_Por_Hora_Wh;

    /**
     * Estado do dispositivo, representado por um enum interno chamado Estado, que pode ser LIGADO ou DESLIGADO. O estado é utilizado para indicar se o dispositivo está atualmente em uso (ligado) ou não (desligado), permitindo que os usuários saibam o status atual do dispositivo e possam controlar seu uso de forma adequada.
     */
    private enum Estado { LIGADO, DESLIGADO }

    /**
     * Estado atual do dispositivo, representado por uma variável do tipo Estado. O estado é utilizado para armazenar o status atual do dispositivo, indicando se ele está ligado ou desligado, e é atualizado pelos métodos de ligar e desligar o dispositivo para refletir as mudanças no status do dispositivo.
     */
    private Estado estado;

    /**
     * Número de ativações do dispositivo, representado como um inteiro. O número de ativações é utilizado para contar quantas vezes o dispositivo foi ligado, fornecendo informações sobre a frequência de uso do dispositivo e permitindo que os usuários monitorem o uso do dispositivo ao longo do tempo.
     */
    private int numAtivacoes = 0;

    /**
     * Tempo de uso do dispositivo em horas, representado como um número de ponto flutuante. O tempo de uso é utilizado para calcular o consumo total de energia do dispositivo com base no consumo por hora, permitindo que os usuários monitorem e gerenciem o consumo de energia de seus dispositivos de forma eficiente. O tempo de uso é atualizado pelo método adicionarTempoUso, que adiciona o tempo especificado ao tempo total de uso do dispositivo.
     */
    private double tempoUsoHoras = 0;

    /**
     * Construtor da classe Dispositivo, que inicializa os atributos do dispositivo com os valores fornecidos como parâmetros. O construtor recebe parâmetros para o ID, marca, modelo e consumo por hora em Wh, e os atribui aos respectivos atributos da classe. O estado do dispositivo é inicializado como DESLIGADO por padrão, e o número de ativações e o tempo de uso são inicializados como zero.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     */
    public Dispositivo(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo_Por_Hora_Wh = consumo_Por_Hora_Wh;
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Construtor vazio da classe Dispositivo, que inicializa os atributos do dispositivo com valores padrão. O ID é inicializado como zero, a marca e o modelo são inicializados como strings vazias, o consumo por hora em Wh é inicializado como zero, o estado do dispositivo é inicializado como DESLIGADO, e o número de ativações e o tempo de uso são inicializados como zero. Este construtor permite criar uma instância de Dispositivo sem fornecer valores específicos para os atributos, utilizando os valores padrão definidos na classe.
     */
    public Dispositivo() {
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.consumo_Por_Hora_Wh = 0.0;
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Construtor de cópia da classe Dispositivo, que cria uma nova instância com os mesmos valores dos atributos do dispositivo fornecido como parâmetro. O construtor recebe um objeto do tipo Dispositivo e copia os valores dos atributos ID, marca, modelo, consumo por hora em Wh, estado, número de ativações e tempo de uso para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos, enquanto mantém as mesmas características do dispositivo original.
     * @param d
     */
    public Dispositivo(Dispositivo d) {
        this.id = d.id;
        this.marca = d.marca;
        this.modelo = d.modelo;
        this.consumo_Por_Hora_Wh = d.consumo_Por_Hora_Wh;
        this.estado = d.estado;
        this.numAtivacoes = d.numAtivacoes;
        this.tempoUsoHoras = d.tempoUsoHoras;
    }

    /**
     * Método para verificar se o dispositivo está ligado, que retorna um valor booleano indicando se o estado do dispositivo é LIGADO. O método é utilizado para verificar o status atual do dispositivo, permitindo que os usuários saibam se o dispositivo está em uso ou não, e pode ser utilizado em outros métodos para controlar o comportamento do dispositivo com base em seu estado.
     * @return true se o dispositivo estiver ligado, false caso contrário.
     */
    public boolean isLigado() {
        return this.estado == Estado.LIGADO;
    }

    /**
     * Método para ligar o dispositivo, que verifica se o dispositivo está atualmente desligado e, se estiver, incrementa o número de ativações e altera o estado do dispositivo para LIGADO. O método garante que o número de ativações seja atualizado apenas quando o dispositivo for ligado, evitando contagens incorretas de ativações quando o dispositivo já estiver ligado.
     */
    public void ligarDispositivo() {
        if (this.estado != Estado.LIGADO) {
            this.numAtivacoes++;
            this.estado = Estado.LIGADO;
        }
    }

    /**
     * Método para desligar o dispositivo, que altera o estado do dispositivo para DESLIGADO. O método é utilizado para controlar o status do dispositivo, permitindo que os usuários desliguem o dispositivo quando não estiver em uso, contribuindo para a economia de energia e a gestão eficiente dos dispositivos.
     */
    public void desligarDispositivo() {
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Método para adicionar tempo de uso ao dispositivo, que recebe um valor em horas e adiciona esse valor ao tempo total de uso do dispositivo. O método verifica se o dispositivo está ligado antes de adicionar o tempo, garantindo que o tempo de uso seja atualizado apenas quando o dispositivo estiver em uso, evitando contagens incorretas de tempo quando o dispositivo estiver desligado.
     * @param horas
     */ 
    public void adicionarTempoUso(double horas) {
        if (this.isLigado()) this.tempoUsoHoras += horas;
    }

    /**
     * Método para obter o estado do dispositivo, que retorna uma string indicando se o dispositivo está ligado ou desligado. O método verifica o estado do dispositivo e retorna "LIGADO" se o estado for LIGADO, ou "DESLIGADO" caso contrário. Isso permite que o estado do dispositivo seja representado de forma clara e informativa, fornecendo ao usuário uma indicação visual do status atual do dispositivo.
     * @return String representando o estado do dispositivo
     */
    public String getEstado() {
        return estado.name();
    }

    /**
     * Método getter para o ID do dispositivo, que retorna o valor atual do ID do dispositivo. O método é utilizado para acessar o valor do ID de forma controlada, permitindo que os usuários obtenham o ID do dispositivo quando necessário.
     * @return ID do dispositivo
     */
    public int getId() { return id; }

    /**
     * Método setter para o ID do dispositivo, que permite definir um novo valor para o ID do dispositivo. O método é utilizado para atualizar o valor do ID de forma controlada, garantindo que o ID seja atribuído corretamente e evitando possíveis conflitos de IDs entre dispositivos.
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Método getter para a marca do dispositivo, que retorna o valor atual da marca do dispositivo. O método é utilizado para acessar o valor da marca de forma controlada, permitindo que os usuários obtenham a marca do dispositivo quando necessário.
     * @return Marca do dispositivo
     */
    public String getMarca() { return marca; }

    /**
     * Método setter para a marca do dispositivo, que permite definir um novo valor para a marca do dispositivo. O método é utilizado para atualizar o valor da marca de forma controlada, garantindo que a marca seja atribuída corretamente e fornecendo informações adicionais sobre o fabricante ou a origem do dispositivo.
     * @param marca
     */
    public void setMarca(String marca) { this.marca = marca; }

    /**
     * Método getter para o modelo do dispositivo, que retorna o valor atual do modelo do dispositivo. O método é utilizado para acessar o valor do modelo de forma controlada, permitindo que os usuários obtenham o modelo do dispositivo quando necessário.
     * @return
     */
    public String getModelo() { return modelo; }

    /**
     * Método setter para o modelo do dispositivo, que permite definir um novo valor para o modelo do dispositivo. O método é utilizado para atualizar o valor do modelo de forma controlada, garantindo que o modelo seja atribuído corretamente e fornecendo informações adicionais sobre a versão ou tipo específico do dispositivo.
     * @param modelo
     */
    public void setModelo(String modelo) { this.modelo = modelo; }

    /**
     * Método getter para o consumo por hora em Wh do dispositivo, que retorna o valor atual do consumo por hora do dispositivo. O método é utilizado para acessar o valor do consumo por hora de forma controlada, permitindo que os usuários obtenham essa informação quando necessário para monitorar e gerenciar o consumo de energia do dispositivo.
     * @return
     */
    public double getConsumo_Por_Hora_Wh() { return consumo_Por_Hora_Wh; }

    /**
     * Método setter para o consumo por hora em Wh do dispositivo, que permite definir um novo valor para o consumo por hora do dispositivo. O método é utilizado para atualizar o valor do consumo por hora de forma controlada, garantindo que o consumo seja atribuído corretamente e fornecendo informações adicionais sobre a eficiência energética do dispositivo.
     * @param consumo
     */
    public void setConsumo_Por_Hora_Wh(double consumo) { this.consumo_Por_Hora_Wh = consumo; }

    /**
     * Método para obter o número de ativações do dispositivo, que retorna o valor atual do número de ativações. O método é utilizado para acessar o valor do número de ativações de forma controlada, permitindo que os usuários obtenham essa informação quando necessário para monitorar a frequência de uso do dispositivo.
     * @return
     */
    public int getNumAtivacoes() { return numAtivacoes; }

    /**
     * Método para obter o tempo de uso do dispositivo em horas, que retorna o valor atual do tempo de uso. O método é utilizado para acessar o valor do tempo de uso de forma controlada, permitindo que os usuários obtenham essa informação quando necessário para monitorar e gerenciar o consumo de energia do dispositivo.
     * @return Tempo de uso do dispositivo em horas
     */
    public double getTempoUsoHoras() { return tempoUsoHoras; }

    /**
     * Método abstrato para criar uma cópia da instância de Dispositivo, que deve ser implementado pelas subclasses para retornar uma nova instância com os mesmos valores dos atributos. O método é utilizado para permitir que as subclasses criem cópias de suas instâncias de forma consistente, garantindo que os valores dos atributos sejam copiados corretamente e permitindo que as novas instâncias sejam independentes das originais.
      * @return Uma nova instância de Dispositivo com os mesmos valores dos atributos.
     */
    public abstract Dispositivo clone();

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando o tipo específico do dispositivo. O método é utilizado para identificar o tipo de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     * @return String representando o tipo do dispositivo
     */
    public abstract String getTipo();

    /**
     * Método para obter detalhes específicos relacionados ao dispositivo, que retorna uma string contendo informações adicionais sobre o dispositivo. O método é utilizado para fornecer detalhes específicos de cada tipo de dispositivo, permitindo que as subclasses sobrescrevam esse método para fornecer informações relevantes e específicas sobre o dispositivo em questão.
     * @return String contendo detalhes específicos relacionados ao dispositivo
     */
    public String getDetalhesEspecificos() { return ""; }

    /** 
     * Método para determinar se o estado base do dispositivo deve ser mostrado, que retorna um valor booleano indicando se o estado base do dispositivo deve ser exibido. O método é utilizado para controlar a exibição do estado base do dispositivo em situações onde pode ser necessário ocultar ou mostrar essa informação, permitindo que as subclasses sobrescrevam esse método para fornecer comportamento específico em relação à exibição do estado base.
      * @return true se o estado base do dispositivo deve ser mostrado, false caso contrário.
     */
    public boolean mostrarEstadoBase() { return true; }

    /**
     * Método para comparar dois objetos do tipo Dispositivo, que verifica se os objetos são iguais com base no valor do ID. O método primeiro verifica se os objetos são a mesma instância, retornando true se forem. Em seguida, verifica se o objeto fornecido é nulo ou se pertence a uma classe diferente, retornando false nesses casos. Por fim, o método compara os IDs dos dois objetos e retorna true se forem iguais, indicando que os dispositivos são considerados iguais com base em seu ID.
      * @param o Objeto a ser comparado com a instância atual.
      * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Dispositivo d = (Dispositivo) o;
        return this.id == d.id;
    }

    /**
     * Método para calcular o hash code do dispositivo, que retorna um valor inteiro baseado no ID do dispositivo. O método utiliza a função hashCode da classe Integer para calcular o hash code com base no valor do ID, garantindo que dispositivos com o mesmo ID tenham o mesmo hash code, o que é importante para a correta funcionalidade de estruturas de dados baseadas em hash, como HashMap e HashSet.
      * @return Hash code do dispositivo baseado no ID.
     */
    @Override
    public int hashCode() { return Integer.hashCode(this.id); }
}