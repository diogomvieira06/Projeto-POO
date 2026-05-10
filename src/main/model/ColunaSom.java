package src.main.model;


/**
 * Classe ColunaSom que representa um dispositivo de coluna de som na casa inteligente. Esta classe herda da classe Dispositivo e adiciona um atributo específico para a intensidade do volume. A classe inclui métodos para obter e definir a intensidade do volume, bem como métodos para clonar o objeto e obter detalhes específicos do dispositivo. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class ColunaSom extends Dispositivo {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe ColunaSom. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributo para a intensidade do volume da coluna de som, representado como um número inteiro. Este atributo é importante para controlar o nível de volume da coluna de som, permitindo que o utilizador ajuste a intensidade do som de acordo com suas preferências. O valor da intensidade do volume pode variar entre 0 (mudo) e 100 (volume máximo), garantindo que o controle do volume seja realizado de forma eficiente e intuitiva para o utilizador.
     */
    private int intensidade_Volume;

    /**
     * Construtor para a classe ColunaSom, que recebe parâmetros para o ID, marca, modelo, consumo por hora em Wh e intensidade do volume. Este construtor é importante para criar objetos da classe ColunaSom com atributos específicos, permitindo que o utilizador defina as características do dispositivo de coluna de som de forma eficiente. O construtor chama o construtor da classe base Dispositivo para inicializar os atributos comuns, e em seguida define a intensidade do volume com base no valor fornecido como parâmetro.
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
     * Construtor padrão para a classe ColunaSom, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe ColunaSom sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo de coluna de som com características padrão. O construtor chama o construtor padrão da classe base Dispositivo para inicializar os atributos comuns, e define a intensidade do volume como 0, indicando que o dispositivo está inicialmente mudo.
     */
    public ColunaSom() {
        super();
        this.intensidade_Volume = 0;
    }

    /**
     * Construtor de cópia para a classe ColunaSom, que recebe um objeto ColunaSom como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe ColunaSom, permitindo que o utilizador duplique um dispositivo de coluna de som com as mesmas características de forma eficiente. O construtor chama o construtor de cópia da classe base Dispositivo para copiar os atributos comuns, e em seguida copia a intensidade do volume do objeto fornecido como parâmetro.
     * @param c
     */
    public ColunaSom(ColunaSom c) {
        super(c); // Chama o construtor de cópia da classe base
        this.intensidade_Volume = c.intensidade_Volume;
    }

    /**
     * Método getter para a intensidade do volume da coluna de som, que retorna o valor atual da intensidade do volume. Este método é importante para permitir que o utilizador acesse o nível de volume atual do dispositivo de coluna de som, permitindo que ele saiba qual é a intensidade do som configurada no momento. O método retorna um número inteiro representando a intensidade do volume, que pode variar entre 0 (mudo) e 100 (volume máximo).
     * @return A intensidade do volume da coluna de som.
     */
    public int getIntensidadeVolume() {
        return intensidade_Volume;
    }

    /**
     * Método setter para a intensidade do volume da coluna de som, que permite definir o valor da intensidade do volume. Este método é importante para permitir que o utilizador ajuste o nível de volume do dispositivo de coluna de som de acordo com suas preferências, garantindo que o controle do volume seja realizado de forma eficiente e intuitiva. O método recebe um número inteiro como parâmetro e verifica se o valor está dentro do intervalo permitido (0 a 100). Se o valor for menor que 0, a intensidade do volume é definida como 0 (mudo). Se o valor for maior que 100, a intensidade do volume é definida como 100 (volume máximo). Caso contrário, a intensidade do volume é definida com base no valor fornecido como parâmetro.
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
     * Método para obter o tipo do dispositivo, que retorna uma string indicando que o tipo do dispositivo é "ColunaSom". Este método é importante para identificar o tipo específico do dispositivo dentro do sistema de casa inteligente, permitindo que o utilizador saiba que o dispositivo em questão é uma coluna de som. O método retorna a string "ColunaSom" para indicar claramente o tipo do dispositivo.
     */
    @Override
    public String getTipo() {
        return "ColunaSom";
    }

    /**
     * Método para clonar o objeto ColunaSom, que cria e retorna uma nova instância da classe ColunaSom com os mesmos atributos do objeto original. Este método é importante para permitir que o utilizador crie cópias de dispositivos de coluna de som de forma eficiente, garantindo que as características do dispositivo sejam mantidas na cópia. O método utiliza o construtor de cópia da classe ColunaSom para criar a nova instância, garantindo que todos os atributos sejam copiados corretamente.
     */
    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
    }

    /**
     * Método para obter detalhes específicos do dispositivo de coluna de som, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo de coluna de som, permitindo que ele saiba o nível de volume configurado quando o dispositivo estiver ligado. Se o estado do dispositivo for "LIGADO", o método retorna uma string indicando o volume atual em porcentagem. Caso contrário, se o dispositivo estiver desligado, o método retorna uma string vazia, indicando que não há detalhes específicos a serem exibidos.
      * @return Detalhes específicos do dispositivo de coluna de som.
     */
    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Volume: " + this.intensidade_Volume + "%";
        else return "";
    }
}