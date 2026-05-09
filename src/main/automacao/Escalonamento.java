package src.main.automacao;
import src.main.controller.*;
import java.io.Serializable;
import java.time.*;

/**
 * Classe que representa um escalonamento para automação, que define um horário específico para a execução de ações em um cenário. O escalonamento pode ser pontual, onde a ação é executada em um horário específico, ou intervalado, onde a ação de início é executada em um horário e a ação de fim é executada em outro horário. A classe inclui atributos para armazenar o ID do escalonamento, o nome, o estado ativo ou inativo, os horários de início e fim, as ações associadas a cada horário, as datas da última execução de cada ação e o ID da casa associada ao escalonamento. O método verificarEExecutar é responsável por verificar se o escalonamento deve ser executado com base no tempo atual simulado e garantir que cada ação seja executada apenas uma vez por dia.
 */
public class Escalonamento implements Serializable{
    /**
     * Classe que representa um escalonamento para automação, que define um horário específico para a execução de ações em um cenário. O escalonamento pode ser pontual, onde a ação é executada em um horário específico, ou intervalado, onde a ação de início é executada em um horário e a ação de fim é executada em outro horário. A classe inclui atributos para armazenar o ID do escalonamento, o nome, o estado ativo ou inativo, os horários de início e fim, as ações associadas a cada horário, as datas da última execução de cada ação e o ID da casa associada ao escalonamento. O método verificarEExecutar é responsável por verificar se o escalonamento deve ser executado com base no tempo atual simulado e garantir que cada ação seja executada apenas uma vez por dia.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID do escalonamento, que é um identificador único para cada escalonamento. O ID é usado para diferenciar os escalonamentos e permitir que sejam referenciados de forma única em operações de criação, edição, exclusão e execução de escalonamentos. O ID pode ser gerado automaticamente pelo sistema ou definido pelo usuário ao criar um novo escalonamento, e deve ser único dentro do contexto da casa para evitar conflitos entre diferentes escalonamentos.
     */
    private int id;

    /**
     * Nome do escalonamento, que é uma descrição textual do escalonamento. O nome é usado para identificar e diferenciar os escalonamentos, permitindo que os usuários possam reconhecer facilmente o propósito de cada escalonamento. O nome pode ser definido pelo usuário ao criar ou editar um escalonamento e deve ser único dentro do contexto da casa para evitar confusões entre diferentes escalonamentos.
     */
    private String nome;

    /**
     * Estado ativo ou inativo do escalonamento, que indica se o escalonamento está habilitado para execução ou não. Um escalonamento ativo é aquele que pode ser verificado e executado com base no tempo atual simulado, enquanto um escalonamento inativo é aquele que não será verificado ou executado, mesmo que o horário atual corresponda ao horário definido no escalonamento. O estado do escalonamento pode ser alterado pelo usuário para ativar ou desativar o escalonamento conforme necessário, permitindo flexibilidade na gestão dos escalonamentos e garantindo que apenas os escalonamentos desejados sejam executados.
     */
    private boolean ativo;

    /**
     * Horário de início do escalonamento, que define o horário específico para a execução da ação de início. O horário de início é representado por um objeto LocalTime, que armazena as horas, minutos e segundos do horário. O horário de início é usado para determinar quando a ação de início deve ser executada, e pode ser definido pelo usuário ao criar ou editar um escalonamento. Se o horário de início for nulo, o escalonamento não será considerado válido e não será executado.
     */
    private LocalTime horaInicio; //horas

    /**
     * Horário de fim do escalonamento, que define o horário específico para a execução da ação de fim. O horário de fim é representado por um objeto LocalTime, que armazena as horas, minutos e segundos do horário. O horário de fim é usado para determinar quando a ação de fim deve ser executada, e pode ser definido pelo usuário ao criar ou editar um escalonamento. Se o horário de fim for nulo, o escalonamento será considerado pontual e apenas a ação de início será executada no horário definido.
     */
    private LocalTime horaFim;

    /**
     * Ação de início do escalonamento, que é a ação a ser executada no horário de início. A ação de início é representada por um objeto Acao, que encapsula as informações necessárias para executar a ação, como o tipo de ação, os parâmetros e as condições associadas. A ação de início é usada para definir o comportamento do sistema quando o horário de início for atingido, e pode ser definida pelo usuário ao criar ou editar um escalonamento. Se a ação de início for nula, o escalonamento não será considerado válido e não será executado.
     */
    private Acao acaoInicio;

    /**
     * Ação de fim do escalonamento, que é a ação a ser executada no horário de fim. A ação de fim é representada por um objeto Acao, que encapsula as informações necessárias para executar a ação, como o tipo de ação, os parâmetros e as condições associadas. A ação de fim é usada para definir o comportamento do sistema quando o horário de fim for atingido, e pode ser definida pelo usuário ao criar ou editar um escalonamento. Se a ação de fim for nula, o escalonamento será considerado pontual e apenas a ação de início será executada no horário definido.
     */
    private Acao acaoFim;

    /**
     * Data da última execução da ação de início, que é usada para garantir que a ação de início seja executada apenas uma vez por dia. A data da última execução é representada por um objeto LocalDate, que armazena o ano, mês e dia da última execução. Quando a ação de início é executada, a data da última execução é atualizada para a data atual, permitindo que o sistema verifique se a ação de início já foi executada no mesmo dia antes de executá-la novamente.
     */
    private LocalDate ultimaExecucaoInicio; //dia

    /**
     * Data da última execução da ação de fim, que é usada para garantir que a ação de fim seja executada apenas uma vez por dia. A data da última execução é representada por um objeto LocalDate, que armazena o ano, mês e dia da última execução. Quando a ação de fim é executada, a data da última execução é atualizada para a data atual, permitindo que o sistema verifique se a ação de fim já foi executada no mesmo dia antes de executá-la novamente.
     */
    private LocalDate ultimaExecucaoFim;

    /**
     * ID da casa associada ao escalonamento, que é usado para identificar a qual casa o escalonamento pertence. O ID da casa é um identificador único para cada casa no sistema, e é usado para garantir que os escalonamentos sejam associados corretamente às casas correspondentes. O ID da casa pode ser definido pelo usuário ao criar ou editar um escalonamento, e deve ser válido para garantir que o escalonamento possa ser executado corretamente no contexto da casa associada.
     */
    private int idCasa;

    /**
     * Construtor da classe Escalonamento, que inicializa os atributos do escalonamento com os valores fornecidos. O construtor recebe parâmetros para o ID do escalonamento, o nome, o estado ativo ou inativo, os horários de início e fim, as ações associadas a cada horário e o ID da casa associada ao escalonamento. Os atributos de última execução são inicialmente definidos como nulos, indicando que as ações ainda não foram executadas. Este construtor é usado para criar novos escalonamentos com as informações fornecidas pelo usuário ou pelo sistema.
     * @param id
     * @param nome
     * @param ativo
     * @param horaInicio
     * @param horaFim
     * @param acaoInicio
     * @param acaoFim
     * @param idCasa
     */
    public Escalonamento(int id, String nome, boolean ativo, LocalTime horaInicio, LocalTime horaFim, Acao acaoInicio, Acao acaoFim, int idCasa) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.acaoInicio = acaoInicio;
        this.acaoFim = acaoFim;
        this.ultimaExecucaoInicio = null; // Inicialmente sem execução
        this.ultimaExecucaoFim = null; // Inicialmente sem execução
        this.idCasa = idCasa;
    }

    /**
     * Construtor padrão da classe Escalonamento, que inicializa os atributos do escalonamento com valores padrão. O ID é definido como 0, o nome é uma string vazia, o estado ativo é definido como false, os horários de início e fim são definidos como nulos, as ações de início e fim são definidas como nulas, as datas de última execução são definidas como nulas e o ID da casa é definido como 0. Este construtor é usado para criar um escalonamento com valores padrão, que pode ser posteriormente configurado com as informações desejadas pelo usuário ou pelo sistema.
     */
    public Escalonamento(){
        this.id = 0;
        this.nome = "";
        this.ativo = false;
        this.horaInicio = null;
        this.horaFim = null;
        this.acaoInicio = null;
        this.acaoFim = null;
        this.ultimaExecucaoInicio = null; // Inicialmente sem execução
        this.ultimaExecucaoFim = null; // Inicialmente sem execução
        this.idCasa = 0;
    }

    /**
     * Construtor de cópia da classe Escalonamento, que cria um novo escalonamento com os mesmos atributos de um escalonamento existente. O construtor recebe um objeto Escalonamento como parâmetro e copia os valores dos atributos do escalonamento fornecido para o novo escalonamento. As ações de início e fim são clonadas para garantir que o novo escalonamento tenha suas próprias instâncias das ações, evitando interferências entre os escalonamentos. Este construtor é usado para criar uma cópia de um escalonamento existente, permitindo que seja modificado ou utilizado sem afetar o escalonamento original.
     * @param e
     */
    public Escalonamento(Escalonamento e){
        this.id = e.id;
        this.nome = e.nome;
        this.ativo = e.ativo;
        this.horaInicio = e.horaInicio;
        this.horaFim = e.horaFim;
        this.acaoInicio = e.acaoInicio != null ? e.acaoInicio.clone() : null; // Clona a ação de início, se não for nula
        this.acaoFim = e.acaoFim != null ? e.acaoFim.clone() : null;
        this.ultimaExecucaoInicio = e.ultimaExecucaoInicio;
        this.ultimaExecucaoFim = e.ultimaExecucaoFim;
        this.idCasa = e.idCasa;
    }

    //getters
    /**
     * Getter para o ID do escalonamento, que retorna o valor do ID do escalonamento. O ID é um identificador único para cada escalonamento, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getId é usado para obter o ID do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento.
     */
    public int getId(){return this.id;}

    /**
     * Getter para o nome do escalonamento, que retorna o valor do nome do escalonamento. O nome é uma descrição textual do escalonamento, e este método permite acessar esse valor para fins de identificação, exibição ou referência em outras partes do sistema. O método getNome é usado para obter o nome do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento por meio de seu nome.
     * @return String
     */
    public String getNome(){return this.nome;}

    /**
     * Getter para o ID da casa associada ao escalonamento, que retorna o valor do ID da casa associada ao escalonamento. O ID da casa é usado para identificar a qual casa o escalonamento pertence, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getIdCasa é usado para obter o ID da casa associada ao escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento no contexto da casa associada.
     * @return int
     */
    public int getIdCasa(){return this.idCasa;}

    /**
     * Getter para o estado ativo do escalonamento, que retorna um booleano indicando se o escalonamento está ativo ou inativo. O estado ativo é usado para determinar se o escalonamento deve ser verificado e executado com base no tempo atual simulado, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método isAtivo é usado para obter o estado ativo do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em seu estado ativo.
     * @return boolean
     */
    public boolean isAtivo(){return this.ativo;}

    /**
     * getter para o horário de início do escalonamento, que retorna o valor do horário de início do escalonamento. O horário de início é usado para determinar quando a ação de início deve ser executada, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getHoraInicio é usado para obter o horário de início do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em seu horário de início.
     * @return LocalTime
     */
    public LocalTime getHoraInicio(){return this.horaInicio;}

    /**
     * Getter para o horário de fim do escalonamento, que retorna o valor do horário de fim do escalonamento. O horário de fim é usado para determinar quando a ação de fim deve ser executada, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getHoraFim é usado para obter o horário de fim do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em seu horário de fim.
     * @return LocalTime
     */
    public LocalTime getHoraFim(){return this.horaFim;}

    /**
     * Getter para a ação de início do escalonamento, que retorna o valor da ação de início do escalonamento. A ação de início é usada para definir o comportamento do sistema quando o horário de início for atingido, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getAcaoInicio é usado para obter a ação de início do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em sua ação de início. O método getAcaoFim é usado para obter a ação de fim do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em sua ação de fim.
     * @return Acao
     */
    public Acao getAcaoInicio(){return this.acaoInicio;}

    /**
     * Getter para a ação de fim do escalonamento, que retorna o valor da ação de fim do escalonamento. A ação de fim é usada para definir o comportamento do sistema quando o horário de fim for atingido, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getAcaoFim é usado para obter a ação de fim do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em sua ação de fim. O método getAcaoInicio é usado para obter a ação de início do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base em sua ação de início.
     * @return Acao
     */
    public Acao getAcaoFim(){return this.acaoFim;}

    /**
     * Getter para a data da última execução da ação de início do escalonamento, que retorna o valor da data da última execução da ação de início do escalonamento. A data da última execução é usada para garantir que a ação de início seja executada apenas uma vez por dia, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getUltimaExecucaoInicio é usado para obter a data da última execução da ação de início do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base na data da última execução de sua ação de início. O método getUltimaExecucaoFim é usado para obter a data da última execução da ação de fim do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base na data da última execução de sua ação de fim.
     * @return LocalDate
     */
    public LocalDate getUltimaExecucaoInicio(){return this.ultimaExecucaoInicio;}

    /**
     * Getter para a data da última execução da ação de fim do escalonamento, que retorna o valor da data da última execução da ação de fim do escalonamento. A data da última execução é usada para garantir que a ação de fim seja executada apenas uma vez por dia, e este método permite acessar esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método getUltimaExecucaoFim é usado para obter a data da última execução da ação de fim do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base na data da última execução de sua ação de fim. O método getUltimaExecucaoInicio é usado para obter a data da última execução da ação de início do escalonamento quando necessário, como ao exibir informações sobre o escalonamento ou ao realizar operações que requerem a identificação do escalonamento com base na data da última execução de sua ação de início.
     * @return LocalDate
     */
    public LocalDate getUltimaExecucaoFim(){return this.ultimaExecucaoFim;}

    //setters
    /**
     * Setter para o ID do escalonamento, que define o valor do ID do escalonamento. O ID é um identificador único para cada escalonamento, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setId é usado para definir o ID do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setNome é usado para definir o nome do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAtivo é usado para definir o estado ativo do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     * Setter para o nome do escalonamento, que define o valor do nome do escalonamento. O nome é uma descrição textual do escalonamento, e este método permite definir esse valor para fins de identificação, exibição ou referência em outras partes do sistema. O método setNome é usado para definir o nome do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setId é usado para definir o ID do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAtivo é usado para definir o estado ativo do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param nome
     */
    public void setNome(String nome){this.nome = nome;}

    /**
     * Setter para o estado ativo do escalonamento, que define o valor do estado ativo do escalonamento. O estado ativo é usado para determinar se o escalonamento deve ser verificado e executado com base no tempo atual simulado, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setAtivo é usado para definir o estado ativo do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setId é usado para definir o ID do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setNome é usado para definir o nome do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param ativo
     */
    public void setAtivo(boolean ativo){this.ativo = ativo;}
    /**
     * Setters para os horários de início e fim, ações de início e fim, datas de última execução e ID da casa associada ao escalonamento, que definem os valores desses atributos do escalonamento. Esses métodos permitem configurar o escalonamento com as informações desejadas pelo usuário ou pelo sistema, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     */
    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}

    /**
     * Setter para o horário de fim do escalonamento, que define o valor do horário de fim do escalonamento. O horário de fim é usado para determinar quando a ação de fim deve ser executada, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param horaFim
     */
    public void setHoraFim(LocalTime horaFim){this.horaFim = horaFim;}

    /**
     * Setters para as ações de início e fim do escalonamento, que definem os valores das ações de início e fim do escalonamento. As ações de início e fim são usadas para definir o comportamento do sistema quando os horários de início e fim forem atingidos, e estes métodos permitem definir esses valores para fins de identificação, comparação ou referência em outras partes do sistema. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param acaoInicio
     */
    public void setAcaoInicio(Acao acaoInicio){this.acaoInicio = acaoInicio;}

    /**
     * Setter para a ação de fim do escalonamento, que define o valor da ação de fim do escalonamento. A ação de fim é usada para definir o comportamento do sistema quando o horário de fim for atingido, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param acaoFim
     */
    public void setAcaoFim(Acao acaoFim){this.acaoFim = acaoFim;}

    /**
     * Setter para a data da última execução da ação de início do escalonamento, que define o valor da data da última execução da ação de início do escalonamento. A data da última execução é usada para garantir que a ação de início seja executada apenas uma vez por dia, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param ultimaExecucaoInicio
     */
    public void setUltimaExecucaoInicio(LocalDate ultimaExecucaoInicio){this.ultimaExecucaoInicio = ultimaExecucaoInicio;}

    /**
     * Setter para a data da última execução da ação de fim do escalonamento, que define o valor da data da última execução da ação de fim do escalonamento. A data da última execução é usada para garantir que a ação de fim seja executada apenas uma vez por dia, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param ultimaExecucaoFim
     */
    public void setUltimaExecucaoFim(LocalDate ultimaExecucaoFim){this.ultimaExecucaoFim = ultimaExecucaoFim;}

    /**
     * Setter para o ID da casa associada ao escalonamento, que define o valor do ID da casa associada ao escalonamento. O ID da casa é usado para identificar a qual casa o escalonamento pertence, e este método permite definir esse valor para fins de identificação, comparação ou referência em outras partes do sistema. O método setIdCasa é usado para definir o ID da casa associada ao escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setId é usado para definir o ID do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setNome é usado para definir o nome do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAtivo é usado para definir o estado ativo do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraInicio é usado para definir o horário de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setHoraFim é usado para definir o horário de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoInicio é usado para definir a ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setAcaoFim é usado para definir a ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoInicio é usado para definir a data da última execução da ação de início do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente. O método setUltimaExecucaoFim é usado para definir a data da última execução da ação de fim do escalonamento quando necessário, como ao criar um novo escalonamento ou ao editar um escalonamento existente.
     * @param idCasa
     */
    public void setIdCasa(int idCasa){this.idCasa = idCasa;}


    /**
     * Métodos para ativar e desativar o escalonamento, que definem o estado ativo do escalonamento. O método ativar define o estado ativo como true, indicando que o escalonamento está ativo e deve ser verificado e executado com base no tempo atual simulado. O método desativar define o estado ativo como false, indicando que o escalonamento está inativo e não deve ser verificado ou executado. Esses métodos permitem controlar o estado do escalonamento de forma simples, facilitando a ativação ou desativação do escalonamento conforme necessário pelo usuário ou pelo sistema.
     */
    public void ativar(){
        this.ativo = true;
    }

    /**
     * Método para desativar o escalonamento, que define o estado ativo do escalonamento como false. Quando um escalonamento é desativado, ele não será verificado ou executado com base no tempo atual simulado, mesmo que os horários de início e fim sejam atingidos. Este método é útil para permitir que os usuários ou o sistema controlem quando um escalonamento deve estar ativo ou inativo, sem a necessidade de excluir o escalonamento ou modificar seus horários e ações associadas. Ao desativar um escalonamento, ele permanece no sistema, mas não interfere nas operações diárias até que seja ativado novamente.
     */
    public void desativar(){
        this.ativo = false;
    }

    /**
     * Método para verificar se o escalonamento é do tipo intervalo, que retorna um booleano indicando se o escalonamento é do tipo intervalo ou pontual. O método isIntervalo verifica se o horário de fim do escalonamento é nulo ou não. Se o horário de fim for nulo, o método retorna false, indicando que o escalonamento é do tipo pontual, ou seja, ele só tem um horário de início e uma ação associada a esse horário. Se o horário de fim não for nulo, o método retorna true, indicando que o escalonamento é do tipo intervalo, ou seja, ele tem um horário de início e um horário de fim, com ações associadas a ambos os horários. Este método é útil para determinar como o escalonamento deve ser verificado e executado com base em seus horários e ações associadas.
     * @return boolean
     */
    public boolean isIntervalo(){
        return horaFim != null;
    }

    /**
     * Verifica e executa o escalonamento com base no tempo atual simulado.
     * Garante que cada ação só dispara uma vez por dia.
     */
    public void verificarEExecutar(DomusControl dc, LocalTime horaAtual, LocalDate dataAtual){
        if(!ativo || horaInicio == null || acaoInicio == null)return; // Se não estiver ativo ou faltar hora ou ação, não faz nada
    

        if(!isIntervalo()){
            //pontual, executa apenas a ação de início
            if(!horaAtual.isBefore(horaInicio) && (ultimaExecucaoInicio == null || ultimaExecucaoInicio.isBefore(dataAtual))){
                acaoInicio.executar(dc);
                ultimaExecucaoInicio = dataAtual; // Atualiza a última execução para hoje
            }
        } else {
            LocalDate ultimaInicioAntes = this.ultimaExecucaoInicio;
            //intervalo
            if(!horaAtual.isBefore(horaInicio) && (ultimaExecucaoInicio == null || ultimaExecucaoInicio.isBefore(dataAtual))){
                acaoInicio.executar(dc);
                ultimaExecucaoInicio = dataAtual; // Atualiza a última execução para hoje
            }

            if(acaoFim != null && !horaAtual.isBefore(horaFim) &&(ultimaExecucaoFim == null || ultimaExecucaoFim.isBefore(dataAtual)) &&
                    (ultimaInicioAntes != null && !ultimaInicioAntes.isBefore(dataAtual))){ // Garante que a ação de fim só execute se a de início já tiver sido executada hoje
                acaoFim.executar(dc);
                ultimaExecucaoFim = dataAtual; // Atualiza a última execução para hoje
            }
        }
    }

    /**
     * Método para criar uma cópia do escalonamento, que retorna um novo objeto Escalonamento com os mesmos atributos do escalonamento original. O método clone utiliza o construtor de cópia da classe Escalonamento para criar uma nova instância do escalonamento, garantindo que as ações de início e fim sejam clonadas para evitar interferências entre os escalonamentos. Este método é útil para criar uma cópia de um escalonamento existente, permitindo que seja modificado ou utilizado sem afetar o escalonamento original.
     */
    @Override
    public Escalonamento clone(){
        return new Escalonamento(this);
    }

    /**
     * Método para representar o escalonamento como uma string, que retorna uma representação textual do escalonamento. A representação inclui o ID, nome, estado ativo e os horários de início e fim do escalonamento, indicando se é um escalonamento do tipo intervalo ou pontual. Este método é útil para exibir informações sobre o escalonamento de forma legível, como ao listar os escalonamentos disponíveis ou ao exibir detalhes de um escalonamento específico.
      * @return String
     */
    @Override
    public String toString() {
        String tipo = isIntervalo() ? "Intervalo [" + horaInicio + " - " + horaFim + "]"
                                    : "Pontual [" + horaInicio + "]";
        return "Escalonamento{id=" + id + ", nome='" + nome + "', ativo=" + ativo + ", " + tipo + "}";
    }

}
