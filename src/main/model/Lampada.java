package src.main.model;


/**
 * Classe que representa uma lâmpada, que é um tipo específico de dispositivo. A classe Lampada herda da classe Dispositivo e adiciona atributos específicos para a intensidade da luminosidade e a cor da luz. A classe inclui métodos para obter e definir a intensidade da luminosidade e a cor da luz, bem como para obter o tipo do dispositivo e detalhes específicos relacionados à lâmpada. A implementação de clone permite criar uma cópia da instância de Lampada, garantindo que as propriedades sejam copiadas corretamente.
 */
public class Lampada extends Dispositivo {
    /** 
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Lampada. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Intensidade da luminosidade da lâmpada, representada como um inteiro que varia de 0 a 100. O valor 0 indica que a lâmpada está completamente apagada, enquanto o valor 100 indica que a lâmpada está na sua intensidade máxima. A intensidade da luminosidade é utilizada para controlar o nível de brilho da lâmpada, permitindo que os usuários ajustem a iluminação de acordo com suas preferências e necessidades.
     */
    private int intensidade_Luminosidade;

    /**
     * Cor da luz da lâmpada, representada como uma string. A cor da luz pode ser utilizada para criar diferentes ambientes e atmosferas, permitindo que os usuários escolham a cor que melhor se adequa ao seu gosto ou à ocasião. A cor da luz é um atributo importante para personalizar a experiência de iluminação e pode ser ajustada para criar efeitos visuais específicos ou para atender a necessidades de iluminação específicas.
     */
    private String cor_Luz;

    /**
     * Construtor da classe Lampada, que inicializa os atributos herdados da classe Dispositivo e os atributos específicos de intensidade da luminosidade e cor da luz. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh, intensidade da luminosidade e cor da luz, e os atribui aos respectivos atributos da classe. O construtor chama o construtor da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois atribui os valores dos atributos específicos da classe Lampada.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     * @param intesidade_Luminosidade
     * @param cor_Luz
     */
    public Lampada(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int intesidade_Luminosidade, String cor_Luz) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.intensidade_Luminosidade = intesidade_Luminosidade;
        this.cor_Luz = cor_Luz;
    }

    /**
     * Construtor vazio da classe Lampada, que inicializa os atributos herdados da classe Dispositivo com valores padrão e os atributos específicos de intensidade da luminosidade e cor da luz com valores padrão. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define a intensidade da luminosidade como zero, indicando que a lâmpada está completamente apagada por padrão, e a cor da luz como "Branco", indicando que a lâmpada emite luz branca por padrão.
     */
    public Lampada() {
        super();
        this.intensidade_Luminosidade = 0;
        this.cor_Luz = "Branco"; // Cor padrão da luz
    }

    /**
     * Construtor de cópia da classe Lampada, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e dos atributos específicos de intensidade da luminosidade e cor da luz. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia os valores da intensidade da luminosidade e cor da luz para a nova instância. Isso permite que a nova instância seja independente da original, com seus próprios valores para os atributos.
     * @param l
     */
    public Lampada(Lampada l) {
        super(l); // Chama o construtor de cópia da classe base
        this.intensidade_Luminosidade = l.intensidade_Luminosidade;
        this.cor_Luz = l.cor_Luz;
    }

    /**
     * Método getter para a intensidade da luminosidade, que retorna o valor atual da intensidade da luminosidade da lâmpada. O método é utilizado para acessar o valor da intensidade da luminosidade de forma controlada, permitindo que os usuários obtenham as informações sobre o nível de brilho da lâmpada.
     * @return Valor da intensidade da luminosidade da lâmpada.
     */
    public int getIntesidade_Luminosidade() {
        return intensidade_Luminosidade;
    }

    /**
     * Método setter para a intensidade da luminosidade, que permite definir um novo valor para a intensidade da luminosidade da lâmpada. O método inclui validação para garantir que o valor da intensidade da luminosidade esteja dentro de um intervalo (0-100). Se o valor fornecido for menor que zero, a intensidade da luminosidade é definida como zero. Se o valor for maior que 100, a intensidade da luminosidade é definida como 100. Caso contrário, o valor fornecido é atribuído diretamente à intensidade da luminosidade. Isso garante que a intensidade da luminosidade seja mantida dentro de limites aceitáveis, evitando valores inválidos que possam causar comportamentos indesejados na lâmpada.
     * @param intensidade_Luminosidade
     */
    public void setIntensidade_Luminosidade(int intensidade_Luminosidade) {
        if (intensidade_Luminosidade < 0) {
            this.intensidade_Luminosidade = 0; // Define a intensidade mínima como 0
        } else if (intensidade_Luminosidade > 100) {
            this.intensidade_Luminosidade = 100; // Define a intensidade máxima como 100
        } else {
            this.intensidade_Luminosidade = intensidade_Luminosidade;
        }
    }

    /**
     * Método getter para a cor da luz, que retorna o valor atual da cor da luz da lâmpada. O método é utilizado para acessar o valor da cor da luz de forma controlada, permitindo que os usuários obtenham as informações sobre a cor da luz emitida pela lâmpada.
     * @return Valor da cor da luz da lâmpada.
     */
    public String getCor_Luz() {
        return cor_Luz;
    }

    /**
     * Método setter para a cor da luz, que permite definir um novo valor para a cor da luz da lâmpada. O método recebe uma string representando a nova cor da luz e a atribui ao atributo cor_Luz da classe. Isso permite que os usuários personalizem a cor da luz emitida pela lâmpada de acordo com suas preferências, proporcionando uma experiência de iluminação mais personalizada e adaptável às necessidades individuais.
     * @param cor_Luz
     */
    public void setCor_Luz(String cor_Luz) {
        this.cor_Luz = cor_Luz;
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "Lampada". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos, permitindo que a funcionalidade específica de cada tipo de dispositivo seja aplicada corretamente.
     * @return String representando o tipo do dispositivo
     */
    @Override
    public String getTipo() {
        return "Lampada";
    }

    /**
     * Método para criar uma cópia da instância de Lampada, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e dos atributos específicos de intensidade da luminosidade e cor da luz. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     * @return Uma nova instância de Lampada com os mesmos valores dos atributos.
     */
    @Override
    public Lampada clone() {
        return new Lampada(this);
    }

    /**
     * Método para obter detalhes específicos relacionados à lâmpada, que retorna uma string contendo informações adicionais sobre a lâmpada. Se a lâmpada estiver ligada, o método retorna uma string indicando a intensidade da luminosidade e a cor da luz. Caso contrário, o método retorna uma string indicando apenas a cor da luz. Isso permite que os detalhes específicos sejam exibidos de forma relevante, fornecendo informações adicionais ao usuário com base no estado atual da lâmpada.
     * @return String representando os detalhes específicos da lâmpada
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Intensidade: " + this.intensidade_Luminosidade + "%" + " | Cor: " + this.cor_Luz;
        else return " | Cor: " + this.cor_Luz;
    }
}