package src.main.model;

/**
 * Classe que representa um sensor de luz, que é um tipo específico de dispositivo. A classe SensorLuz herda da classe Dispositivo e adiciona atributos específicos para o nível de luz e um limiar para considerar que é noite. A classe inclui métodos para obter e definir o nível de luz e o limiar de noite, bem como para obter o tipo do dispositivo e detalhes específicos relacionados ao sensor de luz. A implementação de clone permite criar uma cópia da instância de SensorLuz, garantindo que as propriedades sejam copiadas corretamente. Além disso, a classe inclui métodos para verificar se a luminosidade está baixa com base no limiar definido.
 */
public class SensorLuz extends Dispositivo {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe SensorLuz. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nível de luz, representado como um valor double que indica a intensidade da luz em lux. O nível de luz é utilizado para monitorar as condições de iluminação no ambiente e pode ser ajustado para refletir mudanças na luminosidade. Esse atributo é importante para dispositivos que dependem da luz ambiente, como lâmpadas inteligentes, permitindo que eles ajustem seu comportamento com base nas condições de iluminação detectadas pelo sensor de luz.
     */
    private double nivelLuz; // lux

    /**
     * Limiar para considerar que é noite, representado como um valor double que indica o nível de luz em lux abaixo do qual o ambiente é considerado como estando em condições de noite. O limiar de noite é utilizado para determinar quando a luminosidade está baixa o suficiente para ser considerada noite, permitindo que dispositivos que dependem da luz ambiente ajustem seu comportamento de acordo com as condições de iluminação detectadas pelo sensor de luz. Por exemplo, uma lâmpada inteligente pode acender automaticamente quando o nível de luz estiver abaixo do limiar de noite, proporcionando uma experiência de usuário mais intuitiva e confortável.
     */
    private double limiarNoite;//para considerar q e noite

    /**
     * Construtor vazio da classe SensorLuz, que inicializa os atributos herdados da classe Dispositivo com valores padrão e os atributos específicos de nível de luz e limiar de noite com valores padrão. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define o nível de luz como zero, indicando que não há luz detectada por padrão, e o limiar de noite como 30 lux, indicando que o ambiente é considerado como estando em condições de noite quando o nível de luz estiver abaixo desse valor. Esses valores padrão permitem que a instância do SensorLuz seja criada com configurações iniciais razoáveis, facilitando seu uso em situações onde as condições de iluminação precisam ser monitoradas.
     */
    public SensorLuz() {
        super();
        this.nivelLuz = 0.0;
        this.limiarNoite = 30.0; // Exemplo de limiar para considerar que é noite
    }

    /**
     * Construtor da classe SensorLuz, que inicializa os atributos herdados da classe Dispositivo e os atributos específicos de nível de luz e limiar de noite. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh, nível de luz e limiar de noite, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui os valores do nível de luz e limiar de noite aos atributos específicos da classe SensorLuz. Isso permite que a instância do SensorLuz seja criada com configurações personalizadas, facilitando seu uso em situações onde as condições de iluminação precisam ser monitoradas com base em requisitos específicos.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     */
    public SensorLuz(int id, String marca, String modelo, double consumo) {
        this(id, marca, modelo, consumo, 0.0);
    }

    /**
     * Construtor da classe SensorLuz, que inicializa os atributos herdados da classe Dispositivo e os atributos específicos de nível de luz e limiar de noite. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh, nível de luz e limiar de noite, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui os valores do nível de luz e limiar de noite aos atributos específicos da classe SensorLuz. Isso permite que a instância do SensorLuz seja criada com configurações personalizadas, facilitando seu uso em situações onde as condições de iluminação precisam ser monitoradas com base em requisitos específicos.
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
     * Construtor de cópia da classe SensorLuz, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e dos atributos específicos de nível de luz e limiar de noite. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia os valores do nível de luz e limiar de noite para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos, facilitando a criação de cópias do SensorLuz com as mesmas configurações.
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
     * Método getter para o nível de luz, que retorna o valor atual do atributo nivelLuz. O método é utilizado para acessar o valor do nível de luz de forma controlada, permitindo que os usuários obtenham as informações sobre a intensidade da luz detectada pelo sensor de luz. O valor retornado é representado em lux, fornecendo uma medida quantitativa da luminosidade no ambiente monitorado pelo sensor.
     * @return Valor do nível de luz em lux.
     */
    public double getNivelLuz() {
        return nivelLuz;
    }

    /**
     * Método getter para o limiar de noite, que retorna o valor atual do atributo limiarNoite. O método é utilizado para acessar o valor do limiar de noite de forma controlada, permitindo que os usuários obtenham as informações sobre o nível de luz em lux abaixo do qual o ambiente é considerado como estando em condições de noite. O valor retornado é representado em lux, fornecendo uma medida quantitativa do limiar utilizado para determinar quando a luminosidade está baixa o suficiente para ser considerada noite.
     * @return Valor do limiar de noite em lux.
     */
    public double getLimiarNoite() {
        return limiarNoite;
    }

    //setters
    /**
     * Método setter para o nível de luz, que permite definir um novo valor para o atributo nivelLuz. O método recebe um valor double representando o nível de luz em lux, e atribui esse valor ao atributo nivelLuz. Isso permite que os usuários atualizem as informações sobre a intensidade da luz detectada pelo sensor de luz, refletindo mudanças nas condições de iluminação do ambiente monitorado.
     * @param n
     */
    public void setNivelLuz(double n){
        this.nivelLuz = n;
    }

    /**
     * Método setter para o limiar de noite, que permite definir um novo valor para o atributo limiarNoite. O método recebe um valor double representando o limiar de noite em lux, e atribui esse valor ao atributo limiarNoite. Isso permite que os usuários atualizem as informações sobre o limiar utilizado para determinar quando a luminosidade está baixa o suficiente para ser considerada noite, proporcionando flexibilidade na configuração do SensorLuz com base em requisitos específicos de monitoramento de iluminação.
     * @param l
     */
    public void setLimiarNoite(double l) {
        this.limiarNoite = l;
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "SensorLuz". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     */
    @Override
    public String getTipo(){
        return "SensorLuz";
    }

    /**
     * Método para criar uma cópia da instância de SensorLuz, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e dos atributos específicos de nível de luz e limiar de noite. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     */
    @Override
    public SensorLuz clone(){
        return new SensorLuz(this);
    }

    /**
     * Método para obter detalhes específicos relacionados ao sensor de luz, que retorna uma string contendo informações adicionais sobre o nível de luz e o limiar de noite. O método inclui o valor do nível de luz em lux e o limiar de noite em lux, fornecendo informações relevantes sobre as condições de iluminação monitoradas pelo sensor de luz. Isso permite que os detalhes específicos sejam exibidos de forma relevante, fornecendo informações adicionais ao usuário com base no estado atual do sensor de luz.
     * @return String representando os detalhes específicos do sensor de luz
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Luz: " + this.nivelLuz + " lux (limiar: " + this.limiarNoite + ")";
    }

    //metodos
    /**
     * Método para verificar se a luminosidade está baixa, que retorna um valor booleano indicando se o nível de luz detectado pelo sensor de luz está abaixo do limiar de noite. O método compara o valor do atributo nivelLuz com o valor do atributo limiarNoite e retorna true se o nível de luz estiver abaixo do limiar, indicando que a luminosidade está baixa, ou false caso contrário. Isso permite que os usuários saibam se as condições de iluminação estão adequadas para serem consideradas como noite, facilitando a tomada de decisões com base nas informações fornecidas pelo sensor de luz.
     * @return Valor booleano indicando se a luminosidade está baixa ou não.
     */
    public boolean isLuminosidadeBaixa(){
        return this.nivelLuz < this.limiarNoite;
    }

    /**
     * Método para definir o estado de luminosidade baixa, que permite configurar o nível de luz do sensor de luz para refletir se a luminosidade está baixa ou não. O método recebe um valor booleano indicando se a luminosidade está baixa, e ajusta o valor do atributo nivelLuz com base nesse valor. Se a luminosidade estiver baixa (baixa for true), o método define o nível de luz como um valor abaixo do limiar de noite, indicando que as condições de iluminação são adequadas para serem consideradas como noite. Caso contrário, o método define o nível de luz como um valor acima do limiar de noite, indicando que as condições de iluminação não são adequadas para serem consideradas como noite. Isso permite que os usuários configurem o estado do sensor de luz de forma intuitiva, facilitando a adaptação do comportamento do dispositivo com base nas condições de iluminação detectadas.
     * @param baixa
     */
    public void setLuminosidadeBaixa(boolean baixa) {
        if (baixa) {
            this.nivelLuz = this.limiarNoite - 10;
        } else {
            this.nivelLuz = this.limiarNoite + 10;
        }
    }
    
}
