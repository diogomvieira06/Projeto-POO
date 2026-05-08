package src.main.model;

import java.io.Serializable;
import java.util.*;

/**
 * Classe que representa um utilizador, contendo um ID, um nome e conjuntos de casas administradas e casas do utilizador. A classe é serializável para permitir a persistência dos dados. A utilização de HashMaps para as casas permite uma busca eficiente por ID. A classe inclui métodos para adicionar, remover e listar casas, bem como para verificar se um utilizador pode administrar ou usar uma dada casa. A implementação de equals e hashCode baseia-se no ID do utilizador, garantindo que cada utilizador seja único.
 */
public class Utilizador implements Serializable {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Utilizador. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID único do utilizador, utilizado para identificação e comparação entre utilizadores.
     */
    private int id;

    /**
     * Nome do utilizador, utilizado para identificar o utilizador de forma amigável.
     */
    private String nome;

    /**
     * Conjunto de casas administradas pelo utilizador, armazenado em um HashMap para permitir uma busca eficiente por ID. A chave do HashMap é o ID da casa, e o valor é a própria casa. Esse conjunto representa as casas para as quais o utilizador tem privilégios de administração, permitindo que ele gerencie as configurações e os dispositivos dessas casas.
     */
    private HashMap<Integer, Casa> casasAdministradas;

    /**
     * Conjunto de casas do utilizador, armazenado em um HashMap para permitir uma busca eficiente por ID. A chave do HashMap é o ID da casa, e o valor é a própria casa. Esse conjunto representa as casas às quais o utilizador tem acesso, seja como administrador ou como usuário normal, permitindo que ele interaja com os dispositivos e as configurações dessas casas de acordo com seus privilégios.
     */
    private HashMap<Integer, Casa> casasUtilizador;

    /**
     * Construtor da classe Utilizador, que inicializa o ID, o nome e os conjuntos de casas administradas e casas do utilizador. O construtor recebe parâmetros para o ID e o nome, e os atribui aos respectivos atributos da classe. Os conjuntos de casas são inicializados como HashMaps vazios, prontos para armazenar as casas associadas ao utilizador.
     * @param id
     * @param nome
     */
    public Utilizador(int id, String nome){
        this.id = id;
        this.nome = nome;
        this.casasAdministradas = new HashMap<>();
        this.casasUtilizador = new HashMap<>();
    }

    /**
     * Construtor vazio da classe Utilizador, que inicializa o ID como zero, o nome como uma string vazia e os conjuntos de casas administradas e casas do utilizador como HashMaps vazios. Esse construtor é útil para criar instâncias de Utilizador com valores padrão, permitindo que os atributos sejam configurados posteriormente por meio dos métodos setters ou outros métodos de manipulação de dados.
     */
    public Utilizador(){
        this.id = 0;
        this.nome = "";
        this.casasAdministradas = new HashMap<>();
        this.casasUtilizador = new HashMap<>();
    }

    /**
     * Construtor de cópia da classe Utilizador, que cria uma nova instância com os mesmos valores de ID, nome e conjuntos de casas administradas e casas do utilizador. Os conjuntos de casas são copiados para garantir que a nova instância tenha suas próprias instâncias de casas, permitindo que as alterações em uma instância não afetem a outra. Esse construtor é útil para criar cópias independentes de um Utilizador existente, preservando os dados e as associações de casas.
      * @param u
      */
    public Utilizador(Utilizador u){
        this.id = u.id;
        this.nome = u.nome;
        this.casasAdministradas = new HashMap<>(u.casasAdministradas);
        this.casasUtilizador = new HashMap<>(u.casasUtilizador);
    }

    //getters
    /**
     * Método getter para o ID do utilizador, que retorna o valor atual do ID do utilizador. O método é utilizado para acessar o valor do ID de forma controlada, permitindo que os usuários obtenham as informações sobre a identificação do utilizador.
     * @return
     */
    public int getId(){
        return this.id;
    }

    /**
     * Método getter para o nome do utilizador, que retorna o valor atual do nome do utilizador. O método é utilizado para acessar o valor do nome de forma controlada, permitindo que os usuários obtenham as informações sobre a identificação amigável do utilizador.
     * @return
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Método getter para as casas administradas pelo utilizador, que retorna um HashMap contendo as casas administradas. O método cria uma nova instância de HashMap com os mesmos valores das casas administradas para garantir que a coleção retornada seja independente da coleção interna da classe, permitindo que os usuários obtenham as informações sobre as casas administradas sem afetar diretamente os dados internos do utilizador.
     * @return HashMap contendo as casas administradas pelo utilizador.
     */
    public HashMap<Integer, Casa> getCasasAdministradas(){
        return new HashMap<>(casasAdministradas);
    }

    /**
     * Método getter para as casas do utilizador, que retorna um HashMap contendo as casas às quais o utilizador tem acesso. O método cria uma nova instância de HashMap com os mesmos valores das casas do utilizador para garantir que a coleção retornada seja independente da coleção interna da classe, permitindo que os usuários obtenham as informações sobre as casas do utilizador sem afetar diretamente os dados internos do utilizador.
     * @return HashMap contendo as casas do utilizador.
     */
    public HashMap<Integer, Casa> getCasasUtilizador(){
        return new HashMap<>(casasUtilizador);
    }

    //setters
    /**
     * Método setter para o ID do utilizador, que permite definir um novo valor para o ID do utilizador. O método recebe um valor inteiro representando o novo ID e atribui esse valor ao atributo id da classe. Isso permite que os usuários atualizem a identificação do utilizador de forma controlada, garantindo que o ID seja mantido de acordo com as necessidades do sistema.
      * @param id
      */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Método setter para o nome do utilizador, que permite definir um novo valor para o nome do utilizador. O método recebe uma string representando o novo nome e atribui esse valor ao atributo nome da classe. Isso permite que os usuários atualizem a identificação amigável do utilizador de forma controlada, garantindo que o nome seja mantido de acordo com as preferências do usuário ou as necessidades do sistema.
      * @param nome
      */
    public void setNome(String n){
        this.nome = n;
    }

    /**
     * Método setter para as casas administradas pelo utilizador, que permite definir um novo conjunto de casas administradas. O método recebe um HashMap contendo as novas casas administradas e cria uma nova instância de HashMap com os mesmos valores para garantir que a coleção interna da classe seja independente da coleção fornecida como parâmetro, permitindo que os usuários atualizem as informações sobre as casas administradas sem afetar diretamente os dados internos do utilizador.
     * @param casasAdministradas
     */
    public void setCasasAdministradas(HashMap<Integer, Casa> casasAdministradas){
        this.casasAdministradas = new HashMap<>(casasAdministradas);
    }


    /**
     * Método setter para as casas do utilizador, que permite definir um novo conjunto de casas às quais o utilizador tem acesso. O método recebe um HashMap contendo as novas casas do utilizador e cria uma nova instância de HashMap com os mesmos valores para garantir que a coleção interna da classe seja independente da coleção fornecida como parâmetro, permitindo que os usuários atualizem as informações sobre as casas do utilizador sem afetar diretamente os dados internos do utilizador.
      * @param casasUtilizador
     */
    public void setCasasUtilizador(HashMap<Integer, Casa> casasUtilizador){
        this.casasUtilizador = new HashMap<>(casasUtilizador);
    }



    //adicionar casa administrada
    /**
     * Método para adicionar uma casa administrada ao utilizador, que permite associar uma nova casa ao conjunto de casas administradas pelo utilizador. O método recebe uma instância de Casa como parâmetro e adiciona essa casa ao HashMap de casas administradas, utilizando o ID da casa como chave. Além disso, o método também adiciona a mesma casa ao HashMap de casas do utilizador, garantindo que o utilizador tenha acesso à casa que está administrando. Isso permite que os usuários gerenciem as casas administradas de forma eficiente, mantendo as associações corretas entre as casas e os privilégios do utilizador.
     * @param c
     */
    public void adicionarCasaAdministrada(Casa c){
        this.casasAdministradas.put(c.getId(), c);
        this.casasUtilizador.put(c.getId(), c); //um utilizador que é administrador de uma casa também é um utilizador dessa casa
    }

    //adicionar casa utilizador
    /**
     * Método para adicionar uma casa ao conjunto de casas do utilizador, que permite associar uma nova casa ao conjunto de casas às quais o utilizador tem acesso. O método recebe uma instância de Casa como parâmetro e adiciona essa casa ao HashMap de casas do utilizador, utilizando o ID da casa como chave. Isso permite que os usuários gerenciem as casas do utilizador de forma eficiente, mantendo as associações corretas entre as casas e os privilégios do utilizador, sem necessariamente conceder privilégios de administração.
      * @param c
     */
    public void adicionarCasaUtilizador(Casa c){
        this.casasUtilizador.put(c.getId(), c);
    }

    //remover casa administrada
    /**
     * Método para remover uma casa administrada do utilizador, que permite dissociar uma casa do conjunto de casas administradas pelo utilizador. O método recebe uma instância de Casa como parâmetro e remove a casa correspondente do HashMap de casas administradas, utilizando o ID da casa como chave. Além disso, o método também remove a mesma casa do HashMap de casas do utilizador, garantindo que o utilizador não tenha mais acesso à casa que deixou de administrar. Isso permite que os usuários gerenciem as casas administradas de forma eficiente, mantendo as associações corretas entre as casas e os privilégios do utilizador.
      * @param c
     */
    public void removerCasaAdministrada(Casa c){
        this.casasAdministradas.remove(c.getId());
    }

    //remover casa Utilizador
    /**
     * Método para remover uma casa do conjunto de casas do utilizador, que permite dissociar uma casa do conjunto de casas às quais o utilizador tem acesso. O método recebe uma instância de Casa como parâmetro e remove a casa correspondente do HashMap de casas do utilizador, utilizando o ID da casa como chave. Se a casa também estiver presente no HashMap de casas administradas, o método remove a casa desse HashMap para garantir que o utilizador não tenha mais privilégios de administração sobre a casa. Isso permite que os usuários gerenciem as casas do utilizador de forma eficiente, mantendo as associações corretas entre as casas e os privilégios do utilizador.
     * @param c
     */
    public void removerCasaUtilizador(Casa c){
        if (this.casasAdministradas.containsKey(c.getId())) {//se for administrador, ao remover como utilizador, também remove como administrador
            this.casasAdministradas.remove(c.getId());
        }
        this.casasUtilizador.remove(c.getId());
    }

    //ver se um utilizador pode administrar uma dada casa
    /**
     * Método para verificar se um utilizador pode administrar uma dada casa, que permite determinar se o utilizador tem privilégios de administração sobre a casa especificada. O método recebe uma instância de Casa como parâmetro e verifica se o ID da casa está presente no HashMap de casas administradas do utilizador. Se a casa estiver presente, o método retorna true, indicando que o utilizador pode administrar a casa. Caso contrário, o método retorna false, indicando que o utilizador não tem privilégios de administração sobre a casa. Isso permite que os usuários verifiquem os privilégios de administração de forma eficiente, garantindo que as ações administrativas sejam realizadas apenas por usuários autorizados.
     * @param c
     * @return Valor booleano indicando se o utilizador pode administrar a casa ou não.
     */
    public boolean podeAdministrarCasa(Casa c){
        return this.casasAdministradas.containsKey(c.getId());
    }

    //ver se um utilizador tem acesso a uma dada casa
    /**
     * Método para verificar se um utilizador pode usar uma dada casa, que permite determinar se o utilizador tem acesso à casa especificada, seja como administrador ou como usuário normal. O método recebe uma instância de Casa como parâmetro e verifica se o ID da casa está presente no HashMap de casas do utilizador. Se a casa estiver presente, o método retorna true, indicando que o utilizador pode usar a casa. Caso contrário, o método retorna false, indicando que o utilizador não tem acesso à casa. Isso permite que os usuários verifiquem os privilégios de acesso de forma eficiente, garantindo que as interações com as casas sejam realizadas apenas por usuários autorizados.
     * @param c
     * @return Valor booleano indicando se o utilizador pode usar a casa ou não.
     */
    public boolean podeUsarCasa(Casa c){
        return (this.casasUtilizador.containsKey(c.getId()) || this.casasAdministradas.containsKey(c.getId()));//um utilizador pode usar uma casa se for um utilizador ou um administrador dessa casa
    }


    /**
     * Método toString para representar o utilizador como uma string, que retorna uma representação textual do utilizador contendo o ID, o nome e o número de casas administradas e casas do utilizador. O método utiliza a função String.format para formatar a string de saída de forma clara e organizada, facilitando a leitura e a compreensão das informações sobre o utilizador. A representação inclui o número de casas administradas e casas do utilizador para fornecer uma visão geral das associações do utilizador com as casas, sem poluir a saída com detalhes excessivos sobre cada casa.
     * @return String representando o utilizador
     */
    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", casasAdministradas=" + casasAdministradas.size() +  // Mostra só o número para não poluir
                ", casasUtilizador=" + casasUtilizador.size() +
                '}';
    }

    /**
     * Método para verificar se o utilizador é administrador ou usuário de uma dada casa, que permite determinar o nível de acesso do utilizador em relação à casa especificada. O método recebe uma instância de Casa como parâmetro e verifica se o ID da casa está presente no HashMap de casas administradas do utilizador para determinar se ele é um administrador. Se o ID da casa estiver presente no HashMap de casas do utilizador, mas não no HashMap de casas administradas, o método determina que o utilizador é um usuário normal da casa. O método retorna true se o utilizador for um administrador da casa, e false caso contrário. Isso permite que os usuários verifiquem os privilégios de acesso de forma eficiente, garantindo que as ações sejam realizadas de acordo com os privilégios do utilizador.
     * @param c
     * @return
     */
    public boolean serAdmin(Casa c) { // Se este utilizador é administrador da casa c
        return this.casasAdministradas.containsKey(c.getId());
    }

    /**
     * Método para verificar se o utilizador é um usuário normal de uma dada casa, que permite determinar se o utilizador tem acesso à casa especificada sem privilégios de administração. O método recebe uma instância de Casa como parâmetro e verifica se o ID da casa está presente no HashMap de casas do utilizador, mas não no HashMap de casas administradas. Se o ID da casa estiver presente apenas no HashMap de casas do utilizador, o método retorna true, indicando que o utilizador é um usuário normal da casa. Caso contrário, o método retorna false, indicando que o utilizador não tem acesso à casa ou é um administrador da casa. Isso permite que os usuários verifiquem os privilégios de acesso de forma eficiente, garantindo que as interações com as casas sejam realizadas de acordo com os privilégios do utilizador.
     * @param c
     * @return
     */
    public boolean serUtilizador(Casa c) { // Se este utilizador é um utilizador normal da casa c (não administrador)
        return this.casasUtilizador.containsKey(c.getId()) && !this.serAdmin(c);
    }

    /**
     * Método equals para comparar duas instâncias de Utilizador, que verifica se as duas instâncias são iguais com base no ID do utilizador. Se os IDs forem iguais, os utilizadores são considerados iguais. Caso contrário, eles são considerados diferentes. Isso garante que cada utilizador seja único com base no seu ID, permitindo que as comparações entre utilizadores sejam realizadas de forma eficiente e consistente.
      * @param o
      * @return Valor booleano indicando se as duas instâncias de Utilizador são iguais ou não.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return id == that.id;
    }

    /**
     * Método hashCode para gerar um código hash para a instância de Utilizador, que utiliza o ID do utilizador para calcular o código hash. O método retorna o código hash gerado a partir do ID, garantindo que utilizadores com o mesmo ID tenham o mesmo código hash. Isso é importante para garantir a consistência entre os métodos equals e hashCode, permitindo que os utilizadores sejam corretamente armazenados em estruturas de dados como HashSet ou HashMap.
      * @return Código hash gerado a partir do ID do utilizador.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}