package src.main.model;

/**
 * Classe que representa um sensor de água, que é um tipo específico de dispositivo. A classe SensorAgua herda da classe Dispositivo e adiciona um atributo específico para indicar se está chovendo ou não. A classe inclui métodos para obter e definir o estado de chuva, bem como para obter o tipo do dispositivo e detalhes específicos relacionados ao sensor de água. A implementação de clone permite criar uma cópia da instância de SensorAgua, garantindo que as propriedades sejam copiadas corretamente.
 */
public class SensorAgua extends Dispositivo {
    private static final long serialVersionUID = 1L;

    /**
     * Atributo booleano que indica se está chovendo ou não. O valor true indica que está chovendo, enquanto o valor false indica que não está chovendo. Esse atributo é utilizado para monitorar as condições climáticas e pode ser utilizado para acionar ações específicas em outros dispositivos, como fechar janelas ou ativar sistemas de irrigação, dependendo do estado do sensor de água.
     */
    private boolean emChuva;


    /**
     * Construtor vazio da classe SensorAgua, que inicializa os atributos herdados da classe Dispositivo com valores padrão e o atributo específico de estado de chuva com false. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define o estado de chuva como false, indicando que não está chovendo por padrão.
     */
    public SensorAgua() {
        super();
        this.emChuva = false;
    }

    /**
     * Construtor da classe SensorAgua, que inicializa os atributos herdados da classe Dispositivo e o atributo específico de estado de chuva. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e estado de chuva, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui o valor do estado de chuva ao atributo específico da classe SensorAgua.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public SensorAgua(int id, String marca, String modelo, double consumo) {
        this(id, marca, modelo, consumo, 0, false);
    }

    /**
     * Construtor da classe SensorAgua, que inicializa os atributos herdados da classe Dispositivo e os atributos específicos de nível de água e estado de chuva. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh, nível de água e estado de chuva, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui os valores do nível de água e estado de chuva aos atributos específicos da classe SensorAgua.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     * @param nivelAgua
     * @param emChuva
     */
    public SensorAgua(int id, String marca, String modelo, double consumo, double nivelAgua, boolean emChuva) {
        super(id, marca, modelo, consumo);
        this.emChuva = emChuva;
    }

    /**
     * Construtor de cópia da classe SensorAgua, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de estado de chuva. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia o valor do estado de chuva para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos.
     * @param s
     */
    public SensorAgua(SensorAgua s) {
        super(s);// Chama o construtor de cópia da classe base para copiar os atributos herdados
        this.emChuva = s.emChuva;
    }

    //getters
    /**
     * Método getter para o estado de chuva, que retorna o valor atual do atributo emChuva. O método é utilizado para acessar o valor do estado de chuva de forma controlada, permitindo que os usuários obtenham as informações sobre as condições climáticas monitoradas pelo sensor de água.
     * @return Valor booleano indicando se está chovendo ou não.
     */
    public boolean isEmChuva() {
        return emChuva;
    }

    //setters
    /**
     * Método setter para o estado de chuva, que permite definir um novo valor para o atributo emChuva. O método recebe um valor booleano indicando se está chovendo ou não, e atribui esse valor ao atributo emChuva. Isso permite que os usuários atualizem as informações sobre as condições climáticas monitoradas pelo sensor de água, refletindo mudanças no ambiente de forma precisa.
     * @param emChuva
     */
    public void setEmChuva(boolean emChuva) {
        this.emChuva = emChuva;
    }


    //metodos
    /**
     * Método para verificar se está chovendo, que retorna o valor do atributo emChuva. O método é utilizado para obter informações sobre as condições climáticas monitoradas pelo sensor de água, permitindo que os usuários saibam se está chovendo ou não com base no estado atual do sensor.
     * @return Valor booleano indicando se está chovendo ou não.
     */
    public boolean estaAChover(){
        return this.emChuva;
    }

    /**
     * Método para desligar o dispositivo, que além de chamar o método de desligar da classe base, também define o estado de chuva como false, indicando que não está chovendo. Isso garante que, ao desligar o sensor de água, ele seja configurado para um estado padrão onde não está chovendo, proporcionando uma experiência de usuário mais intuitiva e consistente com a funcionalidade esperada de um sensor de água.
     */
    @Override
    public String getTipo(){
        return "SensorAgua";
    }

    /**
     * Método para criar uma cópia da instância de SensorAgua, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de estado de chuva. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     * @return Uma nova instância de SensorAgua com os mesmos valores dos atributos
     */
    @Override
    public SensorAgua clone(){
        return new SensorAgua(this);
    }

    /**
     * Método para obter detalhes específicos relacionados ao sensor de água, que retorna uma string contendo informações adicionais sobre o estado de chuva. O método verifica o valor do atributo emChuva e retorna uma string indicando se está chovendo ou não. Isso permite que os detalhes específicos sejam exibidos de forma relevante, fornecendo informações adicionais ao usuário com base no estado atual do sensor de água.
     * @return String representando os detalhes específicos do sensor de água
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Chuva: " + (emChuva ? "Sim": "Não");
    }
}
