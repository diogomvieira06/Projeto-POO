package src.main.model;

/**
 * Classe que representa um portão de garagem, que é um tipo específico de dispositivo. A classe PortaoGaragem herda da classe Dispositivo e adiciona um atributo específico para o nível de abertura do portão. A classe inclui métodos para obter e definir o nível de abertura, bem como para obter o tipo do dispositivo e detalhes específicos relacionados ao portão de garagem. A implementação de clone permite criar uma cópia da instância de PortaoGaragem, garantindo que as propriedades sejam copiadas corretamente.
 */
public class PortaoGaragem extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private int nivel_abertura;

    /**
     * Construtor da classe PortaoGaragem, que inicializa os atributos herdados da classe Dispositivo e o atributo específico de nível de abertura do portão. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de abertura, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui o valor do nível de abertura ao atributo específico da classe PortaoGaragem.
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
     * Construtor vazio da classe PortaoGaragem, que inicializa os atributos herdados da classe Dispositivo com valores padrão e o atributo específico de nível de abertura do portão com zero. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define o nível de abertura como zero, indicando que o portão está completamente fechado por padrão.
     */
    public PortaoGaragem() {
        super();
        this.nivel_abertura = 0;
    }

    /**
     * Construtor de cópia da classe PortaoGaragem, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de nível de abertura do portão. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia o valor do nível de abertura para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos.
     * @param p
     */
    public PortaoGaragem(PortaoGaragem p) {
        super(p); // Chama o construtor de cópia da classe base
        this.nivel_abertura = p.nivel_abertura;
    }

    /**
     * Método getter para o nível de abertura, que retorna o valor atual do nível de abertura do portão de garagem. O método é utilizado para acessar o valor do nível de abertura de forma controlada, permitindo que os usuários obtenham as informações sobre a posição atual do portão.
     * @return
     */
    public int getNivelAbertura() {
        return nivel_abertura;
    }

    /**
     * Método setter para o nível de abertura, que permite definir um novo valor para o nível de abertura do portão de garagem. O método inclui validação para garantir que o valor do nível de abertura esteja dentro de um intervalo (0-100). Se o valor fornecido for menor que zero, o nível de abertura é definido como zero. Se o valor for maior que 100, o nível de abertura é definido como 100. Caso contrário, o valor fornecido é atribuído diretamente ao nível de abertura. Isso garante que o nível de abertura seja mantido dentro de limites aceitáveis, evitando valores inválidos que possam causar comportamentos indesejados no portão.
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
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "PortaoGaragem". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     * @return String representando o tipo do dispositivo
     */
    @Override
    public String getTipo() {
        return "PortaoGaragem";
    }

    /**
     * Método para criar uma cópia da instância de PortaoGaragem, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de nível de abertura do portão. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     * @return Uma nova instância de PortaoGaragem com os mesmos valores dos atributos
     */
    @Override
    public PortaoGaragem clone() {
        return new PortaoGaragem(this);
    }

    /**
     * Método para obter detalhes específicos relacionados ao portão de garagem, que retorna uma string contendo informações adicionais sobre o portão de garagem. Se o portão de garagem estiver aberto, o método retorna uma string indicando o nível de abertura do portão. Caso contrário, o método retorna uma string indicando que o portão está fechado. Isso permite que os detalhes específicos sejam exibidos de forma relevante, fornecendo informações adicionais ao usuário com base no estado atual do portão de garagem.
     * @return String representando os detalhes específicos do portão de garagem
     */
    @Override
    public String getDetalhesEspecificos(){
        return " | Nível de Abertura: " + this.nivel_abertura + "%";
    }
}