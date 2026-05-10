package src.main.model;

/**
 * Classe Cortina que representa um dispositivo de cortina na casa inteligente. Esta classe herda da classe Dispositivo e adiciona um atributo específico para o nível de abertura da cortina. A classe inclui métodos para obter e definir o nível de abertura, bem como métodos para ligar e desligar a cortina, que ajustam o nível de abertura de acordo com o estado do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class Cortina extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Cortina. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo para o nível de abertura da cortina, representado como um número inteiro. Este atributo é importante para controlar o estado da cortina, permitindo que o utilizador ajuste o nível de abertura de acordo com suas preferências. O valor do nível de abertura pode variar entre 0 (cortina completamente fechada) e 100 (cortina completamente aberta), garantindo que o controle da cortina seja realizado de forma eficiente e intuitiva para o utilizador.
     */
    private int nivel_abertura;

    /**
     * Construtor para a classe Cortina, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e nível de abertura. Este construtor é importante para criar objetos da classe Cortina com atributos específicos, permitindo que o utilizador defina as características do dispositivo de cortina de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define o nível de abertura com base no valor fornecido como parâmetro.
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
     * Construtor padrão para a classe Cortina, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Cortina sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de cortina com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define o nível de abertura como 0, indicando que a cortina está inicialmente fechada.
     */
    public Cortina() { super(); this.nivel_abertura = 0; }

    /**
     * Construtor de cópia para a classe Cortina, que recebe um objeto Cortina como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Cortina, permitindo que o utilizador duplique um dispositivo de cortina com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia o nível de abertura do objeto fornecido como parâmetro.
     * @param c
     */
    public Cortina(Cortina c) {
        super(c);
        this.nivel_abertura = c.nivel_abertura;
    }

    /**
     * Método para desligar a cortina, que ajusta o nível de abertura para 0 (cortina completamente fechada) quando o dispositivo é desligado. Este método é importante para garantir que a cortina seja fechada automaticamente quando o dispositivo for desligado, proporcionando conveniência e eficiência para o utilizador. O método chama o método desligarDispositivo da classe base Dispositivo para realizar as ações comuns de desligamento, e em seguida define o nível de abertura como 0 para fechar a cortina.
     */
    @Override
    public void desligarDispositivo() {
        super.desligarDispositivo();
        this.nivel_abertura = 0; // Fecha a cortina
    }

    /**
     * Método para ligar a cortina, que ajusta o nível de abertura para 100 (cortina completamente aberta) quando o dispositivo é ligado. Este método é importante para garantir que a cortina seja aberta automaticamente quando o dispositivo for ligado, proporcionando conveniência e eficiência para o utilizador. O método chama o método ligarDispositivo da classe base Dispositivo para realizar as ações comuns de ligação, e em seguida define o nível de abertura como 100 para abrir a cortina.
     */
    @Override
    public void ligarDispositivo() {
        super.ligarDispositivo();
        this.nivel_abertura = 100; // Abre a cortina
    }

    /**
     * Método para obter o estado da cortina, que retorna uma string indicando se a cortina está fechada ou aberta, juntamente com o nível de abertura em porcentagem. Este método é importante para fornecer ao utilizador informações claras sobre o estado atual da cortina, permitindo que ele saiba se a cortina está completamente fechada, completamente aberta ou em um estado intermediário. Se o nível de abertura for 0, o método retorna "FECHADA". Caso contrário, o método retorna "ABERTA" seguido do nível de abertura em porcentagem entre parênteses.
      * @return O estado da cortina.
     */
    @Override
    public String getEstado() {
        if (this.nivel_abertura == 0) return "FECHADA";
        return "ABERTA (" + this.nivel_abertura + "%)";
    }

    /**
     * Atributo para a intensidade do volume da coluna de som, representado como um número inteiro. Este atributo é importante para controlar o nível de volume do dispositivo de coluna de som, permitindo que o utilizador ajuste o volume de acordo com suas preferências. O valor da intensidade do volume pode variar entre 0 (mudo) e 100 (volume máximo), garantindo que o controle do volume seja realizado de forma eficiente e intuitiva para o utilizador.
     */
    @Override
    public Cortina clone() { return new Cortina(this); }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "Cortina". Este método é importante para identificar o tipo específico do dispositivo dentro do sistema de casa inteligente, permitindo que o utilizador saiba que o dispositivo em questão é uma cortina. O método retorna a string "Cortina" para indicar claramente o tipo do dispositivo.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo() { return "Cortina"; }

    /**
     * Método para obter detalhes específicos do dispositivo de cortina, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de cortina, permitindo que ele saiba o nível de abertura configurado quando a cortina estiver aberta. Se o estado do dispositivo for "ABERTA", o método retorna uma string indicando o nível de abertura atual em porcentagem. Caso contrário, se a cortina estiver fechada, o método retorna uma string vazia, indicando que não há detalhes específicos a serem exibidos.
      * @return Detalhes específicos do dispositivo de cortina.
     */
    public int getNivelAbertura() { return nivel_abertura; }

    /**
     * Método setter para o nível de abertura da cortina, que permite definir o valor do nível de abertura. Este método é importante para permitir que o utilizador ajuste o nível de abertura do dispositivo de cortina de acordo com suas preferências, garantindo que o controle da cortina seja realizado de forma eficiente e intuitiva. O método recebe um número inteiro como parâmetro e verifica se o valor está dentro do intervalo permitido (0 a 100). Se o valor for menor que 0, o nível de abertura é definido como 0 (cortina completamente fechada). Se o valor for maior que 100, o nível de abertura é definido como 100 (cortina completamente aberta). Caso contrário, o nível de abertura é definido com base no valor fornecido como parâmetro.
     * @param n
     */
    public void setNivelAbertura(int n) { this.nivel_abertura = Math.max(0, Math.min(100, n)); }
}