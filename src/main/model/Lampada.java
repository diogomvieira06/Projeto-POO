package src.main.model;


/**
 * Classe Lampada que representa um dispositivo de lâmpada na casa inteligente. Esta classe herda da classe Dispositivo e adiciona um atributo específico para a cor da luz. A classe inclui métodos para obter e definir a cor da luz, bem como métodos para clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class Lampada extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Lampada. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributo para a cor da luz da lâmpada, representado como uma string. Este atributo é importante para controlar a aparência da luz emitida pela lâmpada, permitindo que o utilizador ajuste a cor da luz de acordo com suas preferências e necessidades. O valor da cor da luz pode ser definido como uma string representando a cor desejada, como "Branco", "Amarelo", "Vermelho", entre outras opções, proporcionando flexibilidade e personalização para o utilizador no controle da iluminação da casa inteligente.
     */
    private String cor_Luz;

    /**
     * Construtor para a classe Lampada, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e cor da luz. Este construtor é importante para criar objetos da classe Lampada com atributos específicos, permitindo que o utilizador defina as características do dispositivo de lâmpada de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define a cor da luz com base no valor fornecido como parâmetro.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     * @param cor_Luz
     */
    public Lampada(int id, String marca, String modelo, double consumo_Por_Hora_Wh, String cor_Luz) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.cor_Luz = cor_Luz;
    }

    /**
     * Construtor padrão para a classe Lampada, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Lampada sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de lâmpada com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define a cor da luz como "Branco", indicando que a lâmpada emite uma luz branca por padrão.
     */
    public Lampada() {
        super();
        this.cor_Luz = "Branco"; // Cor padrão da luz
    }

    /**
     * Construtor de cópia para a classe Lampada, que recebe um objeto Lampada como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Lampada, permitindo que o utilizador duplique um dispositivo de lâmpada com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia a cor da luz do objeto fornecido como parâmetro.
     * @param l
     */
    public Lampada(Lampada l) {
        super(l); // Chama o construtor de cópia da classe base
        this.cor_Luz = l.cor_Luz;
    }

    /**
     * Método getter para a cor da luz da lâmpada, que retorna o valor atual da cor da luz. Este método é importante para permitir que o utilizador acesse a cor da luz configurada na lâmpada, permitindo que ele saiba qual é a aparência da luz emitida pelo dispositivo no momento. O método retorna uma string representando a cor da luz, que pode ser personalizada de acordo com as preferências do utilizador.
     * @return A cor da luz da lâmpada.
     */
    public String getCor_Luz() {
        return cor_Luz;
    }

    /**
     * Método setter para a cor da luz da lâmpada, que permite definir o valor da cor da luz. Este método é importante para permitir que o utilizador ajuste a cor da luz emitida pela lâmpada de acordo com suas preferências e necessidades, proporcionando flexibilidade e personalização no controle da iluminação da casa inteligente. O método recebe uma string como parâmetro e define a cor da luz com base no valor fornecido, permitindo que o utilizador escolha entre diferentes opções de cores para a lâmpada.
     * @param cor_Luz
     */
    public void setCor_Luz(String cor_Luz) {
        this.cor_Luz = cor_Luz;
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string representando o tipo do dispositivo. Este método é importante para permitir que o sistema de casa inteligente identifique o tipo específico de dispositivo representado pela classe Lampada, facilitando a gestão e o controle dos dispositivos dentro do sistema. O método retorna a string "Lampada" para indicar que o dispositivo é uma lâmpada, permitindo que o sistema reconheça e diferencie os dispositivos com base em seus tipos específicos.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo() {
        return "Lampada";
    }

    /**
     * Método para clonar o objeto da classe Lampada, que cria e retorna uma nova instância da classe Lampada com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de objetos da classe Lampada de forma eficiente, garantindo que as características do dispositivo sejam preservadas na nova instância. O método utiliza o construtor de cópia da classe Lampada para criar a nova instância, passando o objeto atual como parâmetro para garantir que todos os atributos sejam copiados corretamente.
      * @return Uma nova instância da classe Lampada com os mesmos atributos do objeto original.
     */
    @Override
    public Lampada clone() {
        return new Lampada(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo, que retorna uma string contendo informações adicionais sobre a lâmpada, como a cor da luz. Este método é importante para fornecer ao utilizador informações detalhadas sobre o estado e as características específicas do dispositivo de lâmpada, permitindo que ele tenha uma visão mais completa do dispositivo dentro do sistema de casa inteligente. O método verifica o estado da lâmpada e retorna uma string indicando a cor da luz, independentemente de a lâmpada estar ligada ou desligada, proporcionando informações relevantes sobre a aparência da luz emitida pelo dispositivo.
      * @return Uma string contendo detalhes específicos sobre a lâmpada.
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado())) return " | Cor: " + this.cor_Luz;
        else return " | Cor: " + this.cor_Luz;
    }
}