package src.main.model;


/**
 * Classe Tomada que representa um dispositivo de tomada na casa inteligente. Esta classe herda da classe Dispositivo e não adiciona atributos específicos, mas inclui métodos para clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido. O método getDetalhesEspecificos retorna informações adicionais sobre o consumo por hora da tomada quando ela está ligada, proporcionando uma maneira eficiente de monitorar o consumo de energia do dispositivo.
 */
public class Tomada extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Tomada. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor para a classe Tomada, que recebe parâmetros para o ID, marca, modelo e consumo por hora em Wh. Este construtor é importante para criar objetos da classe Tomada com atributos específicos, permitindo que o utilizador defina as características do dispositivo de tomada de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, garantindo que as características do dispositivo sejam corretamente configuradas com base nos parâmetros fornecidos.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     */
    public Tomada(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
    }

    /**
     * Construtor padrão para a classe Tomada, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Tomada sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de tomada com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, garantindo que as características do dispositivo sejam configuradas com valores padrão.
     */
    public Tomada() {
        super();
    }

    /**
     * Construtor de cópia para a classe Tomada, que recebe um objeto Tomada como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Tomada, permitindo que o utilizador duplique um dispositivo de tomada com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, garantindo que as características do dispositivo sejam preservadas na nova instância.
     * @param t
     */
    public Tomada(Tomada t) {
        super(t); // Chama o construtor de cópia da classe base
    }


    /**
     * Método para obter o tipo do dispositivo, que retorna uma string representando o tipo do dispositivo. Este método é importante para permitir que o sistema de casa inteligente identifique o tipo específico de dispositivo representado pela classe Tomada, facilitando a gestão e o controle dos dispositivos dentro do sistema. O método retorna a string "Tomada" para indicar que o dispositivo é uma tomada, permitindo que o sistema reconheça e diferencie os dispositivos com base em seus tipos específicos.
      * @return O tipo do dispositivo.
     */
    @Override
    public String getTipo() {
        return "Tomada";
    }

    /**
     * Método para clonar o objeto da classe Tomada, que cria e retorna uma nova instância da classe Tomada com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de objetos da classe Tomada de forma eficiente, garantindo que as características do dispositivo sejam preservadas na nova instância. O método utiliza o construtor de cópia da classe Tomada para criar a nova instância, passando o objeto atual como parâmetro para garantir que todos os atributos sejam copiados corretamente.
      * @return Uma nova instância da classe Tomada com os mesmos atributos do objeto original.
     */
    @Override
    public Tomada clone() {
        return new Tomada(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo de tomada, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de tomada, permitindo que ele saiba qual é o consumo por hora da tomada quando ela está ligada. O método retorna uma string indicando o consumo por hora em Wh quando a tomada está ligada, proporcionando uma maneira eficiente de monitorar o consumo de energia do dispositivo e ajustar o comportamento dos dispositivos na casa inteligente de acordo com essas informações.
      * @return Detalhes específicos do dispositivo de tomada.
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Consumo por Hora: " + this.getConsumo_Por_Hora_Wh() + "Wh";
        else return "";
    }
}