package src.main.model;

/**
 * Classe SensorAgua que representa um dispositivo de sensor de água na casa inteligente. Esta classe herda da classe Dispositivo e adiciona um atributo específico para indicar se está chovendo ou não. A classe inclui métodos para obter e definir o estado de chuva, bem como métodos para clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class SensorAgua extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe SensorAgua. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo para indicar se está chovendo ou não, representado como um valor booleano. Este atributo é importante para fornecer informações sobre as condições climáticas, permitindo que o sistema de casa inteligente tome decisões com base no estado do tempo. O valor do atributo emChuva pode ser verdadeiro (true) para indicar que está chovendo, ou falso (false) para indicar que não está chovendo, proporcionando uma maneira eficiente de monitorar as condições climáticas e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
     */
    private boolean emChuva;


    /**
     * Construtor padrão para a classe SensorAgua, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe SensorAgua sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de sensor de água com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define o atributo emChuva como false, indicando que inicialmente não está chovendo.
      * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public SensorAgua() {
        super();
        this.emChuva = false;
    }

    /**
     * Construtor para a classe SensorAgua, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e estado de chuva. Este construtor é importante para criar objetos da classe SensorAgua com atributos específicos, permitindo que o utilizador defina as características do dispositivo de sensor de água de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o atributo emChuva com base no valor fornecido como parâmetro.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public SensorAgua(int id, String marca, String modelo, double consumo) {
        this(id, marca, modelo, consumo, 0, false);
    }

    /**
     * Construtor para a classe SensorAgua, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh, nível de água e estado de chuva. Este construtor é importante para criar objetos da classe SensorAgua com atributos específicos, permitindo que o utilizador defina as características do dispositivo de sensor de água de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o atributo emChuva com base no valor fornecido como parâmetro.
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
     * Construtor de cópia para a classe SensorAgua, que recebe um objeto SensorAgua como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe SensorAgua, permitindo que o utilizador duplique um dispositivo de sensor de água com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia o estado de chuva do objeto fornecido como parâmetro.
     * @param s
     */
    public SensorAgua(SensorAgua s) {
        super(s);
        this.emChuva = s.emChuva;
    }

    /**
     * Método getter para o estado de chuva, que retorna o valor atual do atributo emChuva. Este método é importante para permitir que o utilizador acesse o estado atual do dispositivo de sensor de água, permitindo que ele saiba se está chovendo ou não. O método retorna um valor booleano, onde true indica que está chovendo e false indica que não está chovendo, proporcionando uma maneira eficiente de monitorar as condições climáticas e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
     * @return O estado de chuva do dispositivo de sensor de água.
     */
    public boolean isEmChuva() {
        return emChuva;
    }


    /**
     * Método setter para o estado de chuva, que permite definir o valor do atributo emChuva. Este método é importante para permitir que o utilizador ajuste o estado do dispositivo de sensor de água de acordo com as condições climáticas, garantindo que o sistema de casa inteligente possa tomar decisões informadas com base no estado do tempo. O método recebe um valor booleano como parâmetro, onde true indica que está chovendo e false indica que não está chovendo, permitindo que o utilizador atualize o estado de chuva do dispositivo de forma eficiente e intuitiva.
     * @param emChuva
     */
    public void setEmChuva(boolean emChuva) {
        this.emChuva = emChuva;
    }


    //metodos
    /**
     * Método para verificar se está chovendo, que retorna o valor do atributo emChuva. Este método é importante para permitir que o sistema de casa inteligente acesse o estado atual do dispositivo de sensor de água, permitindo que ele saiba se está chovendo ou não. O método retorna um valor booleano, onde true indica que está chovendo e false indica que não está chovendo, proporcionando uma maneira eficiente de monitorar as condições climáticas e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
      * @return O estado de chuva do dispositivo de sensor de água.
     */
    public boolean estaAChover(){
        return this.emChuva;
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string representando o tipo do dispositivo. Este método é importante para permitir que o sistema de casa inteligente identifique o tipo específico de dispositivo representado pela classe SensorAgua, facilitando a gestão e o controle dos dispositivos dentro do sistema. O método retorna a string "SensorAgua" para indicar que o dispositivo é um sensor de água, permitindo que o sistema reconheça e diferencie os dispositivos com base em seus tipos específicos.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo(){
        return "SensorAgua";
    }

    /**
     * Método para clonar o objeto da classe SensorAgua, que cria e retorna uma nova instância da classe SensorAgua com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de objetos da classe SensorAgua de forma eficiente, garantindo que as características do dispositivo sejam preservadas na nova instância. O método utiliza o construtor de cópia da classe SensorAgua para criar a nova instância, passando o objeto atual como parâmetro para garantir que todos os atributos sejam copiados corretamente.
      * @return Uma nova instância da classe SensorAgua com os mesmos atributos do objeto original.
     */
    @Override
    public SensorAgua clone(){
        return new SensorAgua(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo de sensor de água, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de sensor de água, permitindo que ele saiba se está chovendo ou não. O método retorna uma string indicando o estado de chuva atual, onde "Sim" indica que está chovendo e "Não" indica que não está chovendo, proporcionando uma maneira eficiente de monitorar as condições climáticas e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
      * @return Detalhes específicos do dispositivo de sensor de água.
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Chuva: " + (emChuva ? "Sim": "Não");
    }
}
