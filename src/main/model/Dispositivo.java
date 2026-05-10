package src.main.model;
import java.io.Serializable;

/**
 * Classe Dispositivo que representa um dispositivo genérico na casa inteligente. Esta classe é abstrata e serve como base para outros tipos específicos de dispositivos, como ColunaSom e Cortina. A classe inclui atributos comuns a todos os dispositivos, como ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. A classe também inclui métodos para ligar e desligar o dispositivo, adicionar tempo de uso, obter o estado do dispositivo e outros métodos relacionados à funcionalidade básica dos dispositivos na casa inteligente. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public abstract class Dispositivo implements Serializable {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Dispositivo. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private int id;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private String marca;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private String modelo;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private double consumo_Por_Hora_Wh;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private enum Estado { LIGADO, DESLIGADO }

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private Estado estado;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private int numAtivacoes = 0;

    /**
     * Atributos comuns a todos os dispositivos, incluindo ID, marca, modelo, consumo por hora em Wh, estado (ligado ou desligado), número de ativações e tempo de uso em horas. Estes atributos são importantes para representar as características básicas de um dispositivo na casa inteligente, permitindo que o sistema gerencie e controle os dispositivos de forma eficiente. O ID é um identificador único para cada dispositivo, a marca e o modelo fornecem informações sobre o fabricante e o tipo do dispositivo, o consumo por hora em Wh indica a quantidade de energia consumida pelo dispositivo, o estado indica se o dispositivo está ligado ou desligado, o número de ativações registra quantas vezes o dispositivo foi ligado, e o tempo de uso em horas acumula o tempo total que o dispositivo esteve em uso.
     */
    private double tempoUsoHoras = 0;

    /**
     * Construtor para a classe Dispositivo, que recebe parâmetros para o ID, marca, modelo e consumo por hora em Wh. Este construtor é importante para criar objetos da classe Dispositivo com atributos específicos, permitindo que o utilizador defina as características do dispositivo de forma eficiente. O construtor inicializa os atributos com base nos valores fornecidos como parâmetros e define o estado do dispositivo como "DESLIGADO" por padrão.
     * @param id
     * @param marca
     * @param modelo
     * @param consumo_Por_Hora_Wh
     */
    public Dispositivo(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo_Por_Hora_Wh = consumo_Por_Hora_Wh;
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Construtor padrão para a classe Dispositivo, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Dispositivo sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um dispositivo com características padrão. O construtor define o ID como 0, a marca e o modelo como strings vazias, o consumo por hora em Wh como 0.0, e o estado do dispositivo como "DESLIGADO" por padrão.
     */
    public Dispositivo() {
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.consumo_Por_Hora_Wh = 0.0;
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Construtor de cópia para a classe Dispositivo, que recebe um objeto Dispositivo como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Dispositivo, permitindo que o utilizador duplique um dispositivo com as mesmas características de forma eficiente. O construtor copia os valores dos atributos do objeto fornecido como parâmetro para a nova instância, garantindo que a nova instância seja uma réplica exata do objeto original.
     * @param d
     */
    public Dispositivo(Dispositivo d) {
        this.id = d.id;
        this.marca = d.marca;
        this.modelo = d.modelo;
        this.consumo_Por_Hora_Wh = d.consumo_Por_Hora_Wh;
        this.estado = d.estado;
        this.numAtivacoes = d.numAtivacoes;
        this.tempoUsoHoras = d.tempoUsoHoras;
    }

    /**
     * Atributo para a intensidade do volume da coluna de som, representado como um número inteiro. Este atributo é importante para controlar o nível de volume do dispositivo de coluna de som, permitindo que o utilizador ajuste o volume de acordo com suas preferências. O valor da intensidade do volume pode variar entre 0 (mudo) e 100 (volume máximo), garantindo que o controle do volume seja realizado de forma eficiente e intuitiva para o utilizador.
     * @return O estado do dispositivo.
     */
    public boolean isLigado() {
        return this.estado == Estado.LIGADO;
    }

    /**
     * Método para ligar o dispositivo, que ajusta o estado do dispositivo para "LIGADO" e incrementa o número de ativações se o dispositivo estava anteriormente desligado. Este método é importante para controlar o estado do dispositivo, permitindo que o utilizador ligue o dispositivo quando necessário e registre quantas vezes o dispositivo foi ativado. O método verifica se o estado atual do dispositivo é diferente de "LIGADO" antes de realizar as ações de ligação, garantindo que o número de ativações seja incrementado apenas quando o dispositivo for realmente ligado.
     */
    public void ligarDispositivo() {
        if (this.estado != Estado.LIGADO) {
            this.numAtivacoes++;
            this.estado = Estado.LIGADO;
        }
    }

    /**
     * Método para desligar o dispositivo, que ajusta o estado do dispositivo para "DESLIGADO". Este método é importante para controlar o estado do dispositivo, permitindo que o utilizador desligue o dispositivo quando não for necessário, contribuindo para a economia de energia e a eficiência do sistema de casa inteligente. O método define o estado do dispositivo como "DESLIGADO" sem verificar o estado atual, garantindo que o dispositivo seja desligado independentemente de seu estado anterior.
      * @return O estado do dispositivo.
     */
    public void desligarDispositivo() {
        this.estado = Estado.DESLIGADO;
    }

    /**
     * Método para adicionar tempo de uso ao dispositivo, que incrementa o tempo total de uso em horas se o dispositivo estiver ligado. Este método é importante para registrar o tempo total que o dispositivo esteve em uso, permitindo que o sistema de casa inteligente monitore e analise o uso dos dispositivos ao longo do tempo. O método verifica se o dispositivo está ligado antes de adicionar o tempo de uso, garantindo que o tempo seja registrado apenas quando o dispositivo estiver efetivamente em uso.
     * @param horas
     */
    public void adicionarTempoUso(double horas) {
        if (this.isLigado()) this.tempoUsoHoras += horas;
    }

    /**
     * Método para obter o estado do dispositivo, que retorna uma string indicando se o dispositivo está ligado ou desligado. Este método é importante para fornecer ao utilizador informações claras sobre o estado atual do dispositivo, permitindo que ele saiba se o dispositivo está ativo ou inativo. O método retorna a string "LIGADO" se o estado do dispositivo for "LIGADO", e "DESLIGADO" caso contrário.
     * @return O estado do dispositivo.
     */
    public String getEstado() {
        return estado.name();
    }

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando o tipo específico do dispositivo. Este método é importante para identificar o tipo do dispositivo dentro do sistema de casa inteligente, permitindo que o utilizador saiba qual é a categoria ou função do dispositivo em questão. O método é abstrato, o que significa que cada classe derivada deve implementar sua própria versão do método para retornar o tipo específico do dispositivo.
      * @return O tipo do dispositivo.
     */
    public int getId() { return id; }

    /**
     * Método setter para o ID do dispositivo, que permite definir o valor do ID. Este método é importante para permitir que o utilizador atribua um identificador único a cada dispositivo, facilitando a gestão e o controle dos dispositivos dentro do sistema de casa inteligente. O método recebe um número inteiro como parâmetro e define o atributo ID com base no valor fornecido.
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Método para obter a marca do dispositivo, que retorna uma string indicando a marca do dispositivo. Este método é importante para fornecer ao utilizador informações sobre o fabricante do dispositivo, permitindo que ele saiba qual é a marca associada ao dispositivo em questão. O método retorna o valor do atributo marca, que pode ser definido usando o método setter correspondente.
     * @return A marca do dispositivo.
     */
    public String getMarca() { return marca; }

    /**
     * Método setter para a marca do dispositivo, que permite definir o valor da marca. Este método é importante para permitir que o utilizador atribua uma marca ao dispositivo, fornecendo informações sobre o fabricante do dispositivo dentro do sistema de casa inteligente. O método recebe uma string como parâmetro e define o atributo marca com base no valor fornecido.
     * @param marca
     */
    public void setMarca(String marca) { this.marca = marca; }

    /**
     * Método para obter o modelo do dispositivo, que retorna uma string indicando o modelo do dispositivo. Este método é importante para fornecer ao utilizador informações sobre o tipo específico do dispositivo, permitindo que ele saiba qual é o modelo associado ao dispositivo em questão. O método retorna o valor do atributo modelo, que pode ser definido usando o método setter correspondente.
     * @return O modelo do dispositivo.
     */
    public String getModelo() { return modelo; }

    /**
     * Método setter para o modelo do dispositivo, que permite definir o valor do modelo. Este método é importante para permitir que o utilizador atribua um modelo ao dispositivo, fornecendo informações sobre o tipo específico do dispositivo dentro do sistema de casa inteligente. O método recebe uma string como parâmetro e define o atributo modelo com base no valor fornecido.
     * @param modelo
     */
    public void setModelo(String modelo) { this.modelo = modelo; }

    /**
     * Método para obter o consumo por hora em Wh do dispositivo, que retorna um valor numérico indicando a quantidade de energia consumida pelo dispositivo por hora. Este método é importante para fornecer ao utilizador informações sobre o consumo de energia do dispositivo, permitindo que ele saiba quanta energia o dispositivo consome durante seu uso. O método retorna o valor do atributo consumo_Por_Hora_Wh, que pode ser definido usando o método setter correspondente.
     * @return O consumo por hora em Wh do dispositivo.
     */
    public double getConsumo_Por_Hora_Wh() { return consumo_Por_Hora_Wh; }

    /**
     * Método setter para o consumo por hora em Wh do dispositivo, que permite definir o valor do consumo. Este método é importante para permitir que o utilizador atribua um valor de consumo ao dispositivo, fornecendo informações sobre a quantidade de energia consumida pelo dispositivo por hora dentro do sistema de casa inteligente. O método recebe um número decimal como parâmetro e define o atributo consumo_Por_Hora_Wh com base no valor fornecido.
     * @param consumo
     */
    public void setConsumo_Por_Hora_Wh(double consumo) { this.consumo_Por_Hora_Wh = consumo; }

    /**
     * Método para obter o número de ativações do dispositivo, que retorna um valor inteiro indicando quantas vezes o dispositivo foi ligado. Este método é importante para fornecer ao utilizador informações sobre a frequência de uso do dispositivo, permitindo que ele saiba quantas vezes o dispositivo foi ativado dentro do sistema de casa inteligente. O método retorna o valor do atributo numAtivacoes, que é incrementado cada vez que o dispositivo é ligado.
     * @return O número de ativações do dispositivo.
     */
    public int getNumAtivacoes() { return numAtivacoes; }

    /**
     * Método para obter o tempo de uso em horas do dispositivo, que retorna um valor numérico indicando o tempo total que o dispositivo esteve em uso. Este método é importante para fornecer ao utilizador informações sobre a duração do uso do dispositivo, permitindo que ele saiba quanto tempo o dispositivo foi utilizado dentro do sistema de casa inteligente. O método retorna o valor do atributo tempoUsoHoras, que é incrementado cada vez que o método adicionarTempoUso é chamado enquanto o dispositivo estiver ligado.
     * @return O tempo de uso em horas do dispositivo.
     */
    public double getTempoUsoHoras() { return tempoUsoHoras; }

    /**
     * Método para obter detalhes específicos do dispositivo, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo, permitindo que ele saiba detalhes específicos relacionados ao estado do dispositivo dentro do sistema de casa inteligente. O método é abstrato, o que significa que cada classe derivada deve implementar sua própria versão do método para retornar detalhes específicos relacionados ao tipo de dispositivo em questão.
     * @return Detalhes específicos do dispositivo.
     */
    public abstract Dispositivo clone();

    /**
     * Método para obter o tipo do dispositivo, que retorna uma string indicando o tipo específico do dispositivo. Este método é importante para identificar o tipo do dispositivo dentro do sistema de casa inteligente, permitindo que o utilizador saiba qual é a categoria ou função do dispositivo em questão. O método é abstrato, o que significa que cada classe derivada deve implementar sua própria versão do método para retornar o tipo específico do dispositivo.
     * @return O tipo do dispositivo.
     */
    public abstract String getTipo();

    /**
     * Método para obter detalhes específicos do dispositivo, que retorna uma string contendo informações adicionais sobre o estado do dispositivo. Este método é importante para fornecer ao utilizador informações relevantes sobre o dispositivo, permitindo que ele saiba detalhes específicos relacionados ao estado do dispositivo dentro do sistema de casa inteligente. O método é abstrato, o que significa que cada classe derivada deve implementar sua própria versão do método para retornar detalhes específicos relacionados ao tipo de dispositivo em questão.
     * @return Detalhes específicos do dispositivo.
     */
    public String getDetalhesEspecificos() { return ""; }

    /**
     * Método para mostrar o estado base do dispositivo, que retorna um valor booleano indicando se o estado base do dispositivo deve ser exibido. Este método é importante para controlar a exibição do estado base do dispositivo dentro do sistema de casa inteligente, permitindo que o utilizador escolha se deseja ou não mostrar informações básicas sobre o estado do dispositivo. O método retorna true por padrão, indicando que o estado base do dispositivo deve ser exibido, mas pode ser sobrescrito por classes derivadas para alterar esse comportamento conforme necessário.
      * @return O estado base do dispositivo.
     */
    public boolean mostrarEstadoBase() { return true; }

    /**
     * Método para comparar dois objetos da classe Dispositivo, que verifica se os objetos são iguais com base no valor do ID. Este método é importante para garantir que a comparação entre objetos da classe Dispositivo seja realizada de forma eficiente e precisa, permitindo que o sistema de casa inteligente identifique corretamente se dois dispositivos são iguais ou diferentes com base em seus identificadores únicos. O método verifica se os objetos são do mesmo tipo e se possuem o mesmo valor de ID para determinar se eles são considerados iguais.
      * @param o
      * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Dispositivo d = (Dispositivo) o;
        return this.id == d.id;
    }

    /**
     * Método para calcular o código hash do objeto da classe Dispositivo, que retorna um valor inteiro baseado no valor do ID. Este método é importante para garantir que os objetos da classe Dispositivo possam ser usados de forma eficiente em estruturas de dados que dependem de códigos hash, como tabelas hash ou conjuntos. O método utiliza a função hashCode da classe Integer para calcular o código hash com base no valor do ID, garantindo que objetos com o mesmo ID tenham o mesmo código hash.
      * @return O código hash do objeto.
     */
    @Override
    public int hashCode() { return Integer.hashCode(this.id); }
}