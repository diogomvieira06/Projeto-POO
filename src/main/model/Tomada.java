package src.main.model;


/**
 * Classe que representa uma tomada, que é um tipo específico de dispositivo. A classe Tomada herda da classe Dispositivo e não adiciona atributos específicos, mas pode incluir métodos para obter o tipo do dispositivo e detalhes específicos relacionados à tomada. A implementação de clone permite criar uma cópia da instância de Tomada, garantindo que as propriedades sejam copiadas corretamente.
 */
public class Tomada extends Dispositivo {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Tomada. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da classe Tomada, que inicializa os atributos herdados da classe Dispositivo. O construtor recebe parâmetros para o ID, marca, modelo e consumo por hora em Wh, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     */
    public Tomada(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
    }

    /**
     * Construtor vazio da classe Tomada, que inicializa os atributos herdados da classe Dispositivo com valores padrão. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente.
     */
    public Tomada() {
        super();
    }

    /**
     * Construtor de cópia da classe Tomada, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, permitindo que a nova instância seja independente da original, com seus próprios valores para os atributos.
     * @param t
     */
    public Tomada(Tomada t) {
        super(t); // Chama o construtor de cópia da classe base
    }


    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "Tomada". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     */
    @Override
    public String getTipo() {
        return "Tomada";
    }

    /**
     * Método para criar uma cópia da instância de Tomada, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     */
    @Override
    public Tomada clone() {
        return new Tomada(this);
    }

    /**
     * Método para obter detalhes específicos relacionados à tomada, que retorna uma string contendo informações adicionais sobre o consumo por hora em Wh. O método inclui o valor do consumo por hora em Wh, fornecendo informações relevantes sobre o consumo de energia da tomada quando ela está ligada. Isso permite que os detalhes específicos sejam exibidos de forma relevante, fornecendo informações adicionais ao usuário com base no estado atual da tomada.
     * @return String representando os detalhes específicos da tomada
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Consumo por Hora: " + this.getConsumo_Por_Hora_Wh() + "Wh";
        else return "";
    }
}