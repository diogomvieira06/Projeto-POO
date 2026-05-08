package src.main.model;


/**
 * Classe que representa uma coluna de som, que é um tipo específico de dispositivo. A classe ColunaSom herda da classe Dispositivo e adiciona um atributo específico para a intensidade do volume. A classe inclui métodos para obter e definir a intensidade do volume, bem como para obter o tipo do dispositivo e detalhes específicos relacionados à coluna de som. A implementação de clone permite criar uma cópia da instância de ColunaSom, garantindo que as propriedades sejam copiadas corretamente.
 */
public class ColunaSom extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private int intensidade_Volume;

    /**
     * Construtor da classe ColunaSom, que inicializa os atributos herdados da classe Dispositivo e o atributo específico de intensidade do volume. O construtor recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e intensidade do volume, e os atribui aos respectivos atributos da classe.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     * @param intensidade_Volume
     */
    public ColunaSom(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int intensidade_Volume) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.intensidade_Volume = intensidade_Volume;
    }

    /**
     * Construtor vazio da classe ColunaSom, que inicializa os atributos herdados da classe Dispositivo com valores padrão e o atributo específico de intensidade do volume com zero. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente.
     */
    public ColunaSom() {
        super();
        this.intensidade_Volume = 0;
    }

    /**
     * Construtor de cópia da classe ColunaSom, que cria uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de intensidade do volume. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia o valor da intensidade do volume para a nova instância.
     * @param c
     */
    public ColunaSom(ColunaSom c) {
        super(c); // Chama o construtor de cópia da classe base
        this.intensidade_Volume = c.intensidade_Volume;
    }

    /**
     * Método getter para a intensidade do volume, que retorna o valor atual da intensidade do volume da coluna de som. O método é utilizado para acessar o valor da intensidade do volume de forma controlada.
     * @return
     */
    public int getIntensidadeVolume() {
        return intensidade_Volume;
    }

    /**
     * Método setter para a intensidade do volume, que permite definir um novo valor para a intensidade do volume da coluna de som. O método inclui validação para garantir que o valor da intensidade do volume esteja dentro de um intervalo(0-100). Se o valor fornecido for menor que zero, a intensidade do volume é definida como zero. Se o valor for maior que 100, a intensidade do volume é definida como 100. Caso contrário, o valor fornecido é atribuído diretamente à intensidade do volume.
     * @param intensidade_Volume
     */
    public void setIntensidadeVolume(int intensidade_Volume) {
        if (intensidade_Volume < 0) {
            this.intensidade_Volume = 0; // Define a intensidade mínima como 0
        } else if (intensidade_Volume > 100) {
            this.intensidade_Volume = 100; // Define a intensidade máxima como 100
        } else {
            this.intensidade_Volume = intensidade_Volume;
        }
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "ColunaSom". Esse método é utilizado para identificar o tipo específico de dispositivo em situações onde é necessário diferenciar entre diferentes tipos de dispositivos.
     */
    @Override
    public String getTipo() {
        return "ColunaSom";
    }

    /**
     * Método para criar uma cópia da instância de ColunaSom, que retorna uma nova instância com os mesmos valores dos atributos herdados da classe Dispositivo e do atributo específico de intensidade do volume. O método utiliza o construtor de cópia para garantir que os valores sejam copiados corretamente, permitindo que a nova instância seja independente da original.
     */
    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
    }

    /**
     * Método para obter detalhes específicos relacionados à coluna de som, que retorna uma string contendo informações adicionais sobre a coluna de som. Se a coluna de som estiver ligada, o método retorna uma string indicando o volume atual da coluna de som. Caso contrário, o método retorna uma string vazia. Isso permite que os detalhes específicos sejam exibidos apenas quando a coluna de som estiver em uso, fornecendo informações relevantes ao usuário.
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Volume: " + this.intensidade_Volume + "%";
        else return "";
    }
}