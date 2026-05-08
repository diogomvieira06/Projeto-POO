package src.main.automacao;
import src.main.controller.*;
import java.io.Serializable;
import java.time.*;

/**
 * A classe Escalonamento representa um agendamento de ações automáticas em uma casa inteligente. Ela contém informações sobre o horário de início e fim do escalonamento, as ações a serem executadas no início e no fim, e o estado de ativação do escalonamento. A classe também possui métodos para verificar e executar as ações com base no tempo atual simulado, garantindo que cada ação seja executada apenas uma vez por dia. O escalonamento pode ser configurado para ser pontual (apenas ação de início) ou intervalado (ação de início e ação de fim). Esta classe é fundamental para a implementação de automações baseadas em horários específicos na casa inteligente.
 */
public class Escalonamento implements Serializable{
    /**
     * Atributo serialVersionUID para garantir a compatibilidade de versões durante a serialização da classe Escalonamento. Este atributo é utilizado para identificar a versão da classe durante o processo de serialização e desserialização, garantindo que os objetos possam ser corretamente convertidos em bytes e reconstruídos posteriormente, mesmo que a classe tenha sido modificada entre as versões. O valor 1L é uma convenção comum para indicar a primeira versão da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID único do escalonamento, utilizado para identificar e gerenciar diferentes escalonamentos na casa inteligente.
     */
    private int id;
    
    /**
     * Nome do escalonamento, utilizado para facilitar a identificação e organização dos escalonamentos na interface do usuário ou em registros de automação.
     */
    private String nome;

    /**
     * Indica se o escalonamento está ativo ou não. Um escalonamento ativo é aquele que será verificado e executado com base no tempo atual simulado, enquanto um escalonamento inativo não terá suas ações executadas, mesmo que as condições de tempo sejam atendidas.
     */
    private boolean ativo;

    /**
     * Hora de início do escalonamento, representada como um objeto LocalTime. Esta hora é utilizada para determinar quando a ação de início deve ser executada. Se o horário atual simulado for igual ou posterior a esta hora, e a ação de início ainda não tiver sido executada no dia atual, a ação de início será executada.
     */
    private LocalTime horaInicio; //horas

    /**
     * Hora de fim do escalonamento, representada como um objeto LocalTime. Esta hora é utilizada para determinar quando a ação de fim deve ser executada em um escalonamento intervalado. Se o horário atual simulado for igual ou posterior a esta hora, e a ação de fim ainda não tiver sido executada no dia atual, e a ação de início já tiver sido executada no mesmo dia, a ação de fim será executada. Se este atributo for nulo, o escalonamento é considerado pontual e não terá uma ação de fim associada.
     */
    private LocalTime horaFim;

    /**
     * Ação a ser executada no início do escalonamento. Esta ação é representada por um objeto da classe Acao, que encapsula as operações a serem realizadas quando o escalonamento for ativado. A ação de início é obrigatória para que o escalonamento seja considerado válido, e será executada quando as condições de tempo forem atendidas.
     */
    private Acao acaoInicio;

    /**
     * Ação a ser executada no fim do escalonamento. Esta ação é representada por um objeto da classe Acao, que encapsula as operações a serem realizadas quando o escalonamento chegar ao seu horário de fim. A ação de fim é opcional e só será executada em escalonamentos intervalados (aqueles que possuem um horário de fim definido). Para que a ação de fim seja executada, o horário atual simulado deve ser igual ou posterior ao horário de fim, a ação de início deve ter sido executada no mesmo dia, e a ação de fim ainda não deve ter sido executada no dia atual.
     */
    private Acao acaoFim;

    /**
     * Atributos para rastrear a última execução das ações de início e fim do escalonamento. Estes atributos são do tipo LocalDate e são utilizados para garantir que cada ação seja executada apenas uma vez por dia. Quando uma ação é executada, o respectivo atributo é atualizado com a data atual, permitindo que o método de verificação e execução do escalonamento determine se a ação já foi executada no dia atual ou não. Isso é fundamental para evitar execuções repetidas das mesmas ações em um curto período de tempo, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     */
    private LocalDate ultimaExecucaoInicio; //dia

    /**
     * Atributo para rastrear a última execução da ação de fim do escalonamento. Este atributo é do tipo LocalDate e é utilizado para garantir que a ação de fim seja executada apenas uma vez por dia. Quando a ação de fim é executada, este atributo é atualizado com a data atual, permitindo que o método de verificação e execução do escalonamento determine se a ação de fim já foi executada no dia atual ou não. Isso é especialmente importante em escalonamentos intervalados, onde a ação de fim só deve ser executada se a ação de início já tiver sido executada no mesmo dia, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     */
    private LocalDate ultimaExecucaoFim;

    /**
     * ID da casa associada ao escalonamento. Este atributo é utilizado para vincular o escalonamento a uma casa específica, permitindo que as automações sejam organizadas e gerenciadas de acordo com a casa à qual pertencem. O ID da casa é fundamental para garantir que as ações do escalonamento sejam executadas no contexto correto, especialmente em cenários onde há múltiplas casas inteligentes sendo gerenciadas pelo sistema.
     */
    private int idCasa;

    /**
     * Construtor da classe Escalonamento, que inicializa os atributos do escalonamento com os valores fornecidos como parâmetros. Este construtor é utilizado para criar um novo escalonamento com as informações necessárias para sua configuração e execução. Os parâmetros incluem o ID do escalonamento, o nome, o estado de ativação, os horários de início e fim, as ações a serem executadas no início e no fim, e o ID da casa associada ao escalonamento. Os atributos de última execução são inicialmente definidos como nulos, indicando que as ações ainda não foram executadas.
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
     * Construtor padrão da classe Escalonamento, que inicializa os atributos do escalonamento com valores padrão. Este construtor é utilizado para criar um novo escalonamento sem fornecer informações específicas, permitindo que os atributos sejam configurados posteriormente por meio dos métodos setters. Os atributos são inicializados com valores padrão, como ID 0, nome vazio, estado de ativação falso, horários de início e fim nulos, ações de início e fim nulas, e ID da casa 0. Os atributos de última execução também são inicialmente definidos como nulos, indicando que as ações ainda não foram executadas.
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
     * Construtor de cópia da classe Escalonamento, que cria um novo escalonamento com os mesmos atributos de outro escalonamento fornecido como parâmetro. Este construtor é utilizado para criar uma cópia de um escalonamento existente, permitindo que as informações sejam duplicadas sem afetar o escalonamento original. Os atributos são copiados diretamente do escalonamento fornecido, e as ações de início e fim são clonadas para garantir que as referências sejam independentes entre o escalonamento original e a cópia. Os atributos de última execução também são copiados, mantendo o histórico de execuções do escalonamento original na cópia.
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
     * Método getter para o ID do escalonamento. Este método retorna o valor do atributo ID, que é um número inteiro utilizado para identificar de forma única cada escalonamento na casa inteligente. O ID é fundamental para a gestão e organização dos escalonamentos, permitindo que sejam referenciados e manipulados de maneira eficiente no sistema de automação.
     * @return ID do escalonamento
     */
    public int getId(){return this.id;}

    /**
     * Método getter para o nome do escalonamento. Este método retorna o valor do atributo nome, que é uma string utilizada para facilitar a identificação e organização dos escalonamentos na interface do usuário ou em registros de automação. O nome é importante para que os usuários possam reconhecer rapidamente a finalidade de cada escalonamento, especialmente quando há múltiplos escalonamentos configurados na casa inteligente.
     * @return Nome do escalonamento
     */
    public String getNome(){return this.nome;}

    /**
     * Método getter para o ID da casa associada ao escalonamento. Este método retorna o valor do atributo idCasa, que é um número inteiro utilizado para vincular o escalonamento a uma casa específica. O ID da casa é fundamental para garantir que as ações do escalonamento sejam executadas no contexto correto, especialmente em cenários onde há múltiplas casas inteligentes sendo gerenciadas pelo sistema.
     * @return ID da casa associada ao escalonamento
     */
    public int getIdCasa(){return this.idCasa;}

    /**
     * Método getter para o estado de ativação do escalonamento. Este método retorna o valor do atributo ativo, que é um booleano indicando se o escalonamento está ativo ou não. Um escalonamento ativo é aquele que será verificado e executado com base no tempo atual simulado, enquanto um escalonamento inativo não terá suas ações executadas, mesmo que as condições de tempo sejam atendidas. Este método é essencial para controlar o comportamento do escalonamento e permitir que os usuários possam ativar ou desativar as automações conforme necessário.
     * @return Estado de ativação do escalonamento
     */
    public boolean isAtivo(){return this.ativo;}

    /**
     * Métodos getters para os horários de início e fim do escalonamento, as ações de início e fim, e as datas da última execução das ações. Estes métodos retornam os valores dos respectivos atributos, permitindo que outras partes do sistema possam acessar essas informações para verificar o estado do escalonamento, exibir detalhes na interface do usuário ou realizar operações de automação com base nesses dados. Os horários de início e fim são essenciais para determinar quando as ações devem ser executadas, enquanto as ações de início e fim encapsulam as operações a serem realizadas. As datas da última execução são fundamentais para garantir que as ações sejam executadas apenas uma vez por dia.
     * @return Horários de início e fim, ações de início e fim, e datas da última execução das ações
     */
    public LocalTime getHoraInicio(){return this.horaInicio;}

    /**
     * Método getter para o horário de fim do escalonamento. Este método retorna o valor do atributo horaFim, que é um objeto LocalTime utilizado para determinar quando a ação de fim deve ser executada em um escalonamento intervalado. Se o horário atual simulado for igual ou posterior a esta hora, e a ação de fim ainda não tiver sido executada no dia atual, e a ação de início já tiver sido executada no mesmo dia, a ação de fim será executada. Se este atributo for nulo, o escalonamento é considerado pontual e não terá uma ação de fim associada.
     * @return Horário de fim do escalonamento
     */
    public LocalTime getHoraFim(){return this.horaFim;}

    /**
     * Métodos getters para as ações de início e fim do escalonamento. Estes métodos retornam os valores dos atributos acaoInicio e acaoFim, que são objetos da classe Acao encapsulando as operações a serem realizadas quando o escalonamento for ativado. A ação de início é obrigatória para que o escalonamento seja considerado válido, e será executada quando as condições de tempo forem atendidas. A ação de fim é opcional e só será executada em escalonamentos intervalados (aqueles que possuem um horário de fim definido). Para que a ação de fim seja executada, o horário atual simulado deve ser igual ou posterior ao horário de fim, a ação de início deve ter sido executada no mesmo dia, e a ação de fim ainda não deve ter sido executada no dia atual.
     * @return Ações de início e fim do escalonamento
     */
    public Acao getAcaoInicio(){return this.acaoInicio;}

    /**
     * Método getter para a ação de fim do escalonamento. Este método retorna o valor do atributo acaoFim, que é um objeto da classe Acao encapsulando as operações a serem realizadas quando o escalonamento chegar ao seu horário de fim. A ação de fim é opcional e só será executada em escalonamentos intervalados (aqueles que possuem um horário de fim definido). Para que a ação de fim seja executada, o horário atual simulado deve ser igual ou posterior ao horário de fim, a ação de início deve ter sido executada no mesmo dia, e a ação de fim ainda não deve ter sido executada no dia atual.
     * @return Ação de fim do escalonamento
     */
    public Acao getAcaoFim(){return this.acaoFim;}

    /**
     * Métodos getters para as datas da última execução das ações de início e fim do escalonamento. Estes métodos retornam os valores dos atributos ultimaExecucaoInicio e ultimaExecucaoFim, que são objetos LocalDate utilizados para rastrear a última vez que as ações de início e fim foram executadas, respectivamente. Esses atributos são fundamentais para garantir que cada ação seja executada apenas uma vez por dia, permitindo que o método de verificação e execução do escalonamento determine se a ação já foi executada no dia atual ou não. Isso é essencial para evitar execuções repetidas das mesmas ações em um curto período de tempo, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     * @return Datas da última execução das ações de início e fim do escalonamento
     */
    public LocalDate getUltimaExecucaoInicio(){return this.ultimaExecucaoInicio;}

    /**
     * Método getter para a data da última execução da ação de fim do escalonamento. Este método retorna o valor do atributo ultimaExecucaoFim, que é um objeto LocalDate utilizado para rastrear a última vez que a ação de fim foi executada. Este atributo é fundamental para garantir que a ação de fim seja executada apenas uma vez por dia, permitindo que o método de verificação e execução do escalonamento determine se a ação de fim já foi executada no dia atual ou não. Isso é especialmente importante em escalonamentos intervalados, onde a ação de fim só deve ser executada se a ação de início já tiver sido executada no mesmo dia, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     * @return Data da última execução da ação de fim do escalonamento
     */
    public LocalDate getUltimaExecucaoFim(){return this.ultimaExecucaoFim;}

    //setters

    /**
     * Métodos setters para os atributos do escalonamento, permitindo que as informações sejam configuradas ou atualizadas após a criação do objeto. Estes métodos incluem setters para o ID, nome, estado de ativação, horários de início e fim, ações de início e fim, datas da última execução das ações, e ID da casa associada ao escalonamento. Os setters são essenciais para permitir que os usuários possam modificar as configurações do escalonamento conforme necessário, adaptando as automações às suas necessidades e preferências ao longo do tempo.
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     * Método setter para o nome do escalonamento. Este método recebe uma string como parâmetro e atribui esse valor ao atributo nome do escalonamento. O nome é utilizado para facilitar a identificação e organização dos escalonamentos na interface do usuário ou em registros de automação, permitindo que os usuários possam reconhecer rapidamente a finalidade de cada escalonamento, especialmente quando há múltiplos escalonamentos configurados na casa inteligente.
     * @param nome
     */
    public void setNome(String nome){this.nome = nome;}

    /**
     * Método setter para o estado de ativação do escalonamento. Este método recebe um booleano como parâmetro e atribui esse valor ao atributo ativo do escalonamento. Um escalonamento ativo é aquele que será verificado e executado com base no tempo atual simulado, enquanto um escalonamento inativo não terá suas ações executadas, mesmo que as condições de tempo sejam atendidas. Este método é essencial para controlar o comportamento do escalonamento e permitir que os usuários possam ativar ou desativar as automações conforme necessário.
     * @param ativo
     */
    public void setAtivo(boolean ativo){this.ativo = ativo;}

    /**
     * Métodos setters para os horários de início e fim do escalonamento. Estes métodos recebem objetos LocalTime como parâmetros e atribuem esses valores aos atributos horaInicio e horaFim do escalonamento, respectivamente. O horário de início é utilizado para determinar quando a ação de início deve ser executada, enquanto o horário de fim é utilizado para determinar quando a ação de fim deve ser executada em um escalonamento intervalado. Configurar corretamente esses horários é fundamental para garantir que as ações sejam executadas nos momentos desejados, permitindo que as automações na casa inteligente sejam adaptadas às rotinas e preferências dos usuários.
     * @param horaInicio
     */
    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}

    /**
     * Método setter para o horário de fim do escalonamento. Este método recebe um objeto LocalTime como parâmetro e atribui esse valor ao atributo horaFim do escalonamento. O horário de fim é utilizado para determinar quando a ação de fim deve ser executada em um escalonamento intervalado. Se o horário atual simulado for igual ou posterior a esta hora, e a ação de fim ainda não tiver sido executada no dia atual, e a ação de início já tiver sido executada no mesmo dia, a ação de fim será executada. Se este atributo for nulo, o escalonamento é considerado pontual e não terá uma ação de fim associada.
     * @param horaFim
     */
    public void setHoraFim(LocalTime horaFim){this.horaFim = horaFim;}

    /**
     * Métodos setters para as ações de início e fim do escalonamento. Estes métodos recebem objetos da classe Acao como parâmetros e atribuem esses valores aos atributos acaoInicio e acaoFim do escalonamento, respectivamente. A ação de início é obrigatória para que o escalonamento seja considerado válido, e será executada quando as condições de tempo forem atendidas. A ação de fim é opcional e só será executada em escalonamentos intervalados (aqueles que possuem um horário de fim definido). Para que a ação de fim seja executada, o horário atual simulado deve ser igual ou posterior ao horário de fim, a ação de início deve ter sido executada no mesmo dia, e a ação de fim ainda não deve ter sido executada no dia atual.
     * @param acaoInicio
     */
    public void setAcaoInicio(Acao acaoInicio){this.acaoInicio = acaoInicio;}

    /**
     * Método setter para a ação de fim do escalonamento. Este método recebe um objeto da classe Acao como parâmetro e atribui esse valor ao atributo acaoFim do escalonamento. A ação de fim é opcional e só será executada em escalonamentos intervalados (aqueles que possuem um horário de fim definido). Para que a ação de fim seja executada, o horário atual simulado deve ser igual ou posterior ao horário de fim, a ação de início deve ter sido executada no mesmo dia, e a ação de fim ainda não deve ter sido executada no dia atual.
     * @param acaoFim
     */
    public void setAcaoFim(Acao acaoFim){this.acaoFim = acaoFim;}

    /**
     * Métodos setters para as datas da última execução das ações de início e fim do escalonamento. Estes métodos recebem objetos LocalDate como parâmetros e atribuem esses valores aos atributos ultimaExecucaoInicio e ultimaExecucaoFim do escalonamento, respectivamente. Esses atributos são fundamentais para garantir que cada ação seja executada apenas uma vez por dia, permitindo que o método de verificação e execução do escalonamento determine se a ação já foi executada no dia atual ou não. Isso é essencial para evitar execuções repetidas das mesmas ações em um curto período de tempo, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     * @param ultimaExecucaoInicio
     */
    public void setUltimaExecucaoInicio(LocalDate ultimaExecucaoInicio){this.ultimaExecucaoInicio = ultimaExecucaoInicio;}

    /**
     * Método setter para a data da última execução da ação de fim do escalonamento. Este método recebe um objeto LocalDate como parâmetro e atribui esse valor ao atributo ultimaExecucaoFim do escalonamento. Este atributo é fundamental para garantir que a ação de fim seja executada apenas uma vez por dia, permitindo que o método de verificação e execução do escalonamento determine se a ação de fim já foi executada no dia atual ou não. Isso é especialmente importante em escalonamentos intervalados, onde a ação de fim só deve ser executada se a ação de início já tiver sido executada no mesmo dia, garantindo um comportamento mais previsível e controlado das automações na casa inteligente.
     * @param ultimaExecucaoFim
     */
    public void setUltimaExecucaoFim(LocalDate ultimaExecucaoFim){this.ultimaExecucaoFim = ultimaExecucaoFim;}

    /**
     * Método setter para o ID da casa associada ao escalonamento. Este método recebe um número inteiro como parâmetro e atribui esse valor ao atributo idCasa do escalonamento. O ID da casa é fundamental para garantir que as ações do escalonamento sejam executadas no contexto correto, especialmente em cenários onde há múltiplas casas inteligentes sendo gerenciadas pelo sistema. Configurar corretamente o ID da casa permite que as automações sejam organizadas e gerenciadas de acordo com a casa à qual pertencem, garantindo um comportamento mais eficiente e personalizado das automações na casa inteligente.
     * @param idCasa
     */
    public void setIdCasa(int idCasa){this.idCasa = idCasa;}


    /**
     * Métodos para ativar e desativar o escalonamento. O método ativar() define o atributo ativo como true, indicando que o escalonamento está ativo e suas ações serão verificadas e executadas com base no tempo atual simulado. O método desativar() define o atributo ativo como false, indicando que o escalonamento está inativo e suas ações não serão executadas, mesmo que as condições de tempo sejam atendidas. Esses métodos são essenciais para permitir que os usuários possam controlar facilmente o estado do escalonamento, ativando ou desativando as automações conforme necessário para se adequar às suas rotinas e preferências.
     */
    public void ativar(){
        this.ativo = true;
    }

    /**
     * Método para desativar o escalonamento. Este método define o atributo ativo como false, indicando que o escalonamento está inativo e suas ações não serão executadas, mesmo que as condições de tempo sejam atendidas. Desativar um escalonamento é útil quando os usuários desejam pausar temporariamente as automações sem precisar excluir o escalonamento, permitindo que possam reativá-lo posteriormente com facilidade.
     */
    public void desativar(){
        this.ativo = false;
    }

    /**
     * Método para verificar se o escalonamento é do tipo intervalo ou pontual. Este método retorna true se o atributo horaFim for diferente de null, indicando que o escalonamento possui um horário de fim definido e, portanto, é do tipo intervalo. Se o atributo horaFim for null, o método retorna false, indicando que o escalonamento é do tipo pontual, ou seja, possui apenas um horário de início e não tem uma ação de fim associada. Esta distinção é importante para determinar como as ações do escalonamento devem ser verificadas e executadas com base no tempo atual simulado.
     * @return true se o escalonamento for do tipo intervalo, false se for do tipo pontual
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
     * Método para criar uma cópia do escalonamento atual. Este método retorna um novo objeto Escalonamento que é uma cópia exata do escalonamento atual, utilizando o construtor de cópia para garantir que todas as informações sejam duplicadas corretamente. A clonagem de um escalonamento é útil quando os usuários desejam criar um novo escalonamento com as mesmas configurações de um escalonamento existente, permitindo que possam modificar a cópia sem afetar o escalonamento original.
     */
    @Override
    public Escalonamento clone(){
        return new Escalonamento(this);
    }

    /**
     * Método toString para representar o escalonamento como uma string legível. Este método retorna uma string que inclui o ID, nome, estado de ativação, e os horários de início e fim do escalonamento, indicando se é do tipo intervalo ou pontual. A representação em string é útil para exibir informações sobre o escalonamento na interface do usuário ou em registros de automação, permitindo que os usuários possam visualizar rapidamente os detalhes do escalonamento de forma clara e organizada.
     */
    @Override
    public String toString() {
        String tipo = isIntervalo() ? "Intervalo [" + horaInicio + " - " + horaFim + "]"
                                    : "Pontual [" + horaInicio + "]";
        return "Escalonamento{id=" + id + ", nome='" + nome + "', ativo=" + ativo + ", " + tipo + "}";
    }

}
