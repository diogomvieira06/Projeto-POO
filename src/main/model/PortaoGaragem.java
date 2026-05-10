package src.main.model;


/**
 * Classe PortaoGaragem que representa um dispositivo de portão de garagem na casa inteligente. Esta classe herda da classe Dispositivo e adiciona um atributo específico para o nível de abertura do portão. A classe inclui métodos para obter e definir o nível de abertura, bem como métodos para clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class PortaoGaragem extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe PortaoGaragem. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo para o nível de abertura do portão de garagem, representado como um número inteiro. Este atributo é importante para controlar o estado do portão de garagem, permitindo que o utilizador ajuste o nível de abertura de acordo com suas preferências. O valor do nível de abertura pode variar entre 0 (portão completamente fechado) e 100 (portão completamente aberto), garantindo que o controle do portão seja realizado de forma eficiente e intuitiva para o utilizador.
     */
    private int nivel_abertura;

    /**
     * Construtor para a classe PortaoGaragem, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de abertura. Este construtor é importante para criar objetos da classe PortaoGaragem com atributos específicos, permitindo que o utilizador defina as características do dispositivo de portão de garagem de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o nível de abertura com base no valor fornecido como parâmetro.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     * @param nivel_abertura
     */
    public PortaoGaragem(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int nivel_abertura) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.nivel_abertura = nivel_abertura;
    }

    /**
     * Construtor padrão para a classe PortaoGaragem, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe PortaoGaragem sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de portão de garagem com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define o nível de abertura como 0, indicando que o portão de garagem está inicialmente fechado.
     */
    public PortaoGaragem() {
        super();
        this.nivel_abertura = 0;
    }

    /**
     * Construtor de cópia para a classe PortaoGaragem, que recebe um objeto PortaoGaragem como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe PortaoGaragem, permitindo que o utilizador duplique um dispositivo de portão de garagem com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia o nível de abertura do objeto fornecido como parâmetro.
     * @param p
     */
    public PortaoGaragem(PortaoGaragem p) {
        super(p); // Chama o construtor de cópia da classe base
        this.nivel_abertura = p.nivel_abertura;
    }

    /**
     * Método getter para o nível de abertura do portão de garagem, que retorna o valor atual do nível de abertura. Este método é importante para permitir que o utilizador acesse o estado atual do dispositivo de portão de garagem, permitindo que ele saiba qual é o nível de abertura configurado no momento. O método retorna um número inteiro representando o nível de abertura, que pode variar entre 0 (portão completamente fechado) e 100 (portão completamente aberto).
     * @return O nível de abertura do portão de garagem.
     */
    public int getNivelAbertura() {
        return nivel_abertura;
    }

    /**
     * Método setter para o nível de abertura do portão de garagem, que permite definir o valor do nível de abertura. Este método é importante para permitir que o utilizador ajuste o estado do dispositivo de portão de garagem de acordo com suas preferências, garantindo que o controle do portão seja realizado de forma eficiente e intuitiva. O método recebe um número inteiro como parâmetro e verifica se o valor está dentro do intervalo permitido (0 a 100). Se o valor for menor que 0, o nível de abertura é definido como 0 (portão completamente fechado). Se o valor for maior que 100, o nível de abertura é definido como 100 (portão completamente aberto). Caso contrário, o nível de abertura é definido com base no valor fornecido como parâmetro.
     * @param nivel_abertura
     */
    public void setNivelAbertura(int nivel_abertura) {
        if(nivel_abertura < 0) {
            this.nivel_abertura = 0; // Define o nível mínimo de abertura como 0
        } else if (nivel_abertura > 100) {
            this.nivel_abertura = 100; // Define o nível máximo de abertura como 100
        } else {
            this.nivel_abertura = nivel_abertura;
        }
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string representando o tipo do dispositivo. Este método é importante para permitir que o sistema de casa inteligente identifique o tipo específico de dispositivo representado pela classe PortaoGaragem, facilitando a gestão e o controle dos dispositivos dentro do sistema. O método retorna a string "PortaoGaragem" para indicar que o dispositivo é um portão de garagem, permitindo que o sistema reconheça e diferencie os dispositivos com base em seus tipos específicos.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo() {
        return "PortaoGaragem";
    }

    /**
     * Método para clonar o objeto da classe PortaoGaragem, que cria e retorna uma nova instância da classe PortaoGaragem com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de objetos da classe PortaoGaragem de forma eficiente, garantindo que as características do dispositivo sejam preservadas na nova instância. O método utiliza o construtor de cópia da classe PortaoGaragem para criar a nova instância, passando o objeto atual como parâmetro para garantir que todos os atributos sejam copiados corretamente.
      * @return Uma nova instância da classe PortaoGaragem com os mesmos atributos do objeto original.
     */
    @Override
    public PortaoGaragem clone() {
        return new PortaoGaragem(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo de portão de garagem, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de portão de garagem, permitindo que ele saiba o nível de abertura configurado quando o portão estiver aberto. Se o estado do dispositivo for "ABERTA", o método retorna uma string indicando o nível de abertura atual em porcentagem. Caso contrário, se o portão estiver fechado, o método retorna uma string vazia, indicando que não há detalhes específicos a serem exibidos.
      * @return Detalhes específicos do dispositivo de portão de garagem.
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Nível de Abertura: " + this.nivel_abertura + "%";
    }
}