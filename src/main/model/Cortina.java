package src.main.model;

/**
 * Classe que representa uma cortina, que é um tipo específico de dispositivo. A classe Cortina herda da classe Dispositivo e adiciona um atributo específico para o nível de abertura da cortina. A classe inclui métodos para obter e definir o nível de abertura, bem como para obter o tipo do dispositivo e detalhes específicos relacionados à cortina. A implementação de clone permite criar uma cópia da instância de Cortina, garantindo que as propriedades sejam copiadas corretamente.
 */
public class Cortina extends Dispositivo {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Cortina. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nível de abertura da cortina, representado como um inteiro que varia de 0 a 100. O valor 0 indica que a cortina está completamente fechada, enquanto o valor 100 indica que a cortina está completamente aberta. O nível de abertura é utilizado para controlar a posição da cortina e pode ser ajustado para permitir diferentes níveis de entrada de luz ou privacidade, dependendo das preferências do usuário.
     */
    private int nivel_abertura;

    /**
     * Construtor da classe Cortina, que inicializa os atributos herdados da classe Dispositivo e o atributo específico de nível de abertura da cortina. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de abertura, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui o valor do nível de abertura ao atributo específico da classe Cortina.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo
     * @param nivel
     */
    public Cortina(int id, String marca, String modelo, double consumo, int nivel) {
        super(id, marca, modelo, consumo);
        this.nivel_abertura = nivel;
    }

    /**
     * Construtor vazio da classe Cortina, que inicializa os atributos herdados da classe Dispositivo com valores padrão e o atributo específico de nível de abertura da cortina com zero. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define o nível de abertura como zero, indicando que a cortina está completamente fechada por padrão.
     */
    public Cortina() { super(); this.nivel_abertura = 0; }

    /**
     * Construtor de cópia da classe Cortina, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de nível de abertura da cortina. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia o valor do nível de abertura para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos.
     * @param c
     */
    public Cortina(Cortina c) {
        super(c);
        this.nivel_abertura = c.nivel_abertura;
    }

    /**
     * Método para desligar o dispositivo, que além de chamar o método de desligar da classe base, também define o nível de abertura da cortina como zero, indicando que a cortina está completamente fechada. Isso garante que, ao desligar a cortina, ela seja fechada automaticamente, proporcionando uma experiência de usuário mais intuitiva e consistente com a funcionalidade esperada de uma cortina.
     */
    @Override
    public void desligarDispositivo() {
        super.desligarDispositivo();
        this.nivel_abertura = 0; // Fecha a cortina
    }

    /**
     * Método para ligar o dispositivo, que além de chamar o método de ligar da classe base, também define o nível de abertura da cortina como 100, indicando que a cortina está completamente aberta. Isso garante que, ao ligar a cortina, ela seja aberta automaticamente, proporcionando uma experiência de usuário mais intuitiva e consistente com a funcionalidade esperada de uma cortina.
     */
    @Override
    public void ligarDispositivo() {
        super.ligarDispositivo();
        this.nivel_abertura = 100; // Abre a cortina
    }

    /**
     * Método para obter o estado do dispositivo, que retorna uma string indicando se a cortina está aberta ou fechada, juntamente com o nível de abertura. Se o nível de abertura for zero, o método retorna "FECHADA". Caso contrário, o método retorna "ABERTA" seguido do nível de abertura entre parênteses. Isso permite que o estado da cortina seja representado de forma clara e informativa, fornecendo ao usuário uma indicação visual do status atual da cortina.
     * @return String representando o estado da cortina
     */
    @Override
    public String getEstado() {
        if (this.nivel_abertura == 0) return "FECHADA";
        return "ABERTA (" + this.nivel_abertura + "%)";
    }

    /**
     * Método para criar uma cópia da instância de Cortina, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de nível de abertura da cortina. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     * @return Uma nova instância de Cortina com os mesmos valores dos atributos.
     */
    @Override
    public Cortina clone() { return new Cortina(this); }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "Cortina". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     * @return String representando o tipo do dispositivo
     */
    @Override
    public String getTipo() { return "Cortina"; }

    /**
     * Método getter para o nível de abertura da cortina, que retorna o valor atual do nível de abertura da cortina. O método é utilizado para acessar o valor do nível de abertura de forma controlada, permitindo que outras partes do código obtenham essa informação sem acessar diretamente o atributo. O método retorna um inteiro que representa o nível de abertura, variando de 0 a 100.
     * @return Valor do nível de abertura da cortina
     */
    public int getNivelAbertura() { return nivel_abertura; }

    /**
     * Método setter para o nível de abertura da cortina, que permite definir um novo valor para o nível de abertura da cortina. O método inclui validação para garantir que o valor do nível de abertura esteja dentro de um intervalo (0-100). Se o valor fornecido for menor que zero, o nível de abertura é definido como zero. Se o valor for maior que 100, o nível de abertura é definido como 100. Caso contrário, o valor fornecido é atribuído diretamente ao nível de abertura. Isso garante que o nível de abertura seja sempre mantido dentro dos limites válidos, evitando valores inválidos que poderiam causar comportamentos inesperados na cortina.
     * @param n
     */
    public void setNivelAbertura(int n) { this.nivel_abertura = Math.max(0, Math.min(100, n)); }
}