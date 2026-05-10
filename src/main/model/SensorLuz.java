package src.main.model;
/**
 * Classe SensorLuz que representa um dispositivo de sensor de luz na casa inteligente. Esta classe herda da classe Dispositivo e adiciona atributos específicos para o nível de luz e o limiar para considerar que é noite. A classe inclui métodos para obter e definir o nível de luz e o limiar, bem como métodos para verificar se a luminosidade está baixa, clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class SensorLuz extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe SensorLuz. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo para o nível de luz, representado como um número decimal (double) em lux. Este atributo é importante para fornecer informações sobre a intensidade da luz no ambiente, permitindo que o sistema de casa inteligente tome decisões com base no nível de luminosidade. O valor do nível de luz pode variar dependendo das condições do ambiente, e é utilizado para determinar se a luminosidade está baixa ou não, proporcionando uma maneira eficiente de monitorar as condições de iluminação e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
     */
    private double nivelLuz; // lux

    /**
     * Atributo para o limiar de luz para considerar que é noite, representado como um número decimal (double) em lux. Este atributo é importante para definir um ponto de referência para determinar se a luminosidade está baixa o suficiente para ser considerada noite, permitindo que o sistema de casa inteligente tome decisões informadas com base no nível de luz. O valor do limiar de luz pode ser ajustado pelo utilizador para atender às suas preferências e necessidades específicas, proporcionando uma maneira eficiente de monitorar as condições de iluminação e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
     */
    private double limiarNoite;//para considerar q e noite

    /**
     * Construtor padrão para a classe SensorLuz, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe SensorLuz sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de sensor de luz com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define o nível de luz como 0.0 lux, indicando que inicialmente não há luz no ambiente, e define o limiar de luz para considerar que é noite como 30.0 lux, indicando que a luminosidade será considerada baixa quando estiver abaixo desse valor.
     */
    public SensorLuz() {
        super();
        this.nivelLuz = 0.0;
        this.limiarNoite = 30.0; // Exemplo de limiar para considerar que é noite
    }

    /**
     * Construtor para a classe SensorLuz, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de luz. Este construtor é importante para criar objetos da classe SensorLuz com atributos específicos, permitindo que o utilizador defina as características do dispositivo de sensor de luz de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o nível de luz com base no valor fornecido como parâmetro, e define o limiar de luz para considerar que é noite como 30.0 lux, indicando que a luminosidade será considerada baixa quando estiver abaixo desse valor.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public SensorLuz(int id, String marca, String modelo, double consumo) {
        this(id, marca, modelo, consumo, 0.0);
    }

    /**
     * Construtor para a classe SensorLuz, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de luz. Este construtor é importante para criar objetos da classe SensorLuz com atributos específicos, permitindo que o utilizador defina as características do dispositivo de sensor de luz de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o nível de luz com base no valor fornecido como parâmetro, e define o limiar de luz para considerar que é noite como 30.0 lux, indicando que a luminosidade será considerada baixa quando estiver abaixo desse valor.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     * @param nivelLuz
     */
    public SensorLuz(int id, String marca, String modelo, double consumo, double nivelLuz) {
        super(id, marca, modelo, consumo); 
        this.nivelLuz = nivelLuz;
        //this.nivelLuzAnterior = 0.0; // Inicialmente, o nível anterior é 0
        this.limiarNoite = 30.0; // Exemplo de limiar para considerar que é noite
    }

    /**
     * Construtor de cópia para a classe SensorLuz, que recebe um objeto SensorLuz como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe SensorLuz, permitindo que o utilizador duplique um dispositivo de sensor de luz com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia o nível de luz e o limiar de luz para considerar que é noite do objeto fornecido como parâmetro.
     * @param s
     */
    public SensorLuz(SensorLuz s) {
        super(s);
        this.nivelLuz = s.nivelLuz;
        //this.nivelLuzAnterior = s.nivelLuzAnterior;
        this.limiarNoite = s.limiarNoite;
    }

    //getters
    /**
     * Método getter para o nível de luz, que retorna o valor atual do nível de luz em lux. Este método é importante para permitir que o utilizador acesse o estado atual do dispositivo de sensor de luz, permitindo que ele saiba qual é a intensidade da luz no ambiente. O método retorna um número decimal representando o nível de luz em lux, que pode variar dependendo das condições do ambiente, e é utilizado para determinar se a luminosidade está baixa ou não, proporcionando uma maneira eficiente de monitorar as condições de iluminação e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
     * @return O nível de luz do dispositivo de sensor de luz.
     */
    public double getNivelLuz() {
        return nivelLuz;
    }


    /**
     * Método getter para o limiar de luz para considerar que é noite, que retorna o valor atual do limiar de luz em lux. Este método é importante para permitir que o utilizador acesse o estado atual do dispositivo de sensor de luz, permitindo que ele saiba qual é o ponto de referência para determinar se a luminosidade está baixa o suficiente para ser considerada noite. O método retorna um número decimal representando o limiar de luz em lux, que pode ser ajustado pelo utilizador para atender às suas preferências e necessidades específicas, proporcionando uma maneira eficiente de monitorar as condições de iluminação e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
      * @return O limiar de luz para considerar que é noite do dispositivo de sensor de luz.
     */
    public double getLimiarNoite() {
        return limiarNoite;
    }

    //setters
    /**
     * Método setter para o nível de luz, que permite definir o valor do nível de luz em lux. Este método é importante para permitir que o utilizador ajuste o estado do dispositivo de sensor de luz de acordo com as condições do ambiente, garantindo que o sistema de casa inteligente possa tomar decisões informadas com base no nível de luminosidade. O método recebe um número decimal como parâmetro representando o nível de luz em lux, e define o atributo nivelLuz com base no valor fornecido, permitindo que o utilizador atualize o nível de luz do dispositivo de forma eficiente e intuitiva.
     * @param n
     */
    public void setNivelLuz(double n){
        this.nivelLuz = n;
    }

    /**
     * Método setter para o limiar de luz para considerar que é noite, que permite definir o valor do limiar de luz em lux. Este método é importante para permitir que o utilizador ajuste o ponto de referência para determinar se a luminosidade está baixa o suficiente para ser considerada noite, garantindo que o sistema de casa inteligente possa tomar decisões informadas com base no nível de luminosidade. O método recebe um número decimal como parâmetro representando o limiar de luz em lux, e define o atributo limiarNoite com base no valor fornecido, permitindo que o utilizador atualize o limiar de luz do dispositivo de forma eficiente e intuitiva.
      * @param l
     */
    public void setLimiarNoite(double l) {
        this.limiarNoite = l;
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string representando o tipo do dispositivo. Este método é importante para permitir que o sistema de casa inteligente identifique o tipo específico de dispositivo representado pela classe SensorLuz, facilitando a gestão e o controle dos dispositivos dentro do sistema. O método retorna a string "SensorLuz" para indicar que o dispositivo é um sensor de luz, permitindo que o sistema reconheça e diferencie os dispositivos com base em seus tipos específicos.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo(){
        return "SensorLuz";
    }

    /**
     * Método para clonar o objeto da classe SensorLuz, que cria e retorna uma nova instância da classe SensorLuz com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de objetos da classe SensorLuz de forma eficiente, garantindo que as características do dispositivo sejam preservadas na nova instância. O método utiliza o construtor de cópia da classe SensorLuz para criar a nova instância, passando o objeto atual como parâmetro para garantir que todos os atributos sejam copiados corretamente.
      * @return Uma nova instância da classe SensorLuz com os mesmos atributos do objeto original.
     */
    @Override
    public SensorLuz clone(){
        return new SensorLuz(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo de sensor de luz, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de sensor de luz, permitindo que ele saiba qual é a intensidade da luz no ambiente e qual é o limiar para considerar que é noite. O método retorna uma string indicando o nível de luz atual em lux e o limiar de luz para considerar que é noite, proporcionando uma maneira eficiente de monitorar as condições de iluminação e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas condições.
      * @return Detalhes específicos do dispositivo de sensor de luz.
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Luz: " + this.nivelLuz + " lux (limiar: " + this.limiarNoite + ")";
    }

    //metodos
    /**
     * Método para verificar se a luminosidade está baixa, que retorna um valor booleano indicando se o nível de luz está abaixo do limiar para considerar que é noite. Este método é importante para permitir que o sistema de casa inteligente tome decisões informadas com base no nível de luminosidade, permitindo que ele saiba se a luminosidade está baixa o suficiente para ser considerada noite. O método compara o nível de luz atual com o limiar definido e retorna true se o nível de luz estiver abaixo do limiar, indicando que a luminosidade está baixa, ou false caso contrário.
     * @return Um valor booleano indicando se a luminosidade está baixa.
     */
    public boolean isLuminosidadeBaixa(){
        return this.nivelLuz < this.limiarNoite;
    }

    public void setLuminosidadeBaixa(boolean baixa) {
        if (baixa) {
            this.nivelLuz = this.limiarNoite - 10;
        } else {
            this.nivelLuz = this.limiarNoite + 10;
        }
    }
    
}
