package src.main.model;


import java.io.Serializable;
import java.util.*;

/**
 * Classe Utilizador que representa um usuário do sistema de casa inteligente. Esta classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido. A classe inclui atributos para o ID do usuário, nome, casas administradas e casas às quais o usuário tem acesso. Os métodos da classe permitem adicionar e remover casas administradas e casas de usuário, verificar se o usuário pode administrar ou usar uma determinada casa, e obter informações sobre o usuário e suas casas associadas.
 */
public class Utilizador implements Serializable {
    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Utilizador. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID do usuário, representado como um número inteiro. Este atributo é importante para identificar de forma única cada usuário no sistema de casa inteligente, permitindo que o sistema gerencie e controle os usuários de maneira eficiente. O ID do usuário pode ser utilizado para associar o usuário a suas casas administradas e casas de usuário, facilitando a gestão das permissões e acessos dentro do sistema.
     */
    private int id;

    /**
     * Nome do usuário, representado como uma string. Este atributo é importante para fornecer uma identificação legível e amigável para os usuários do sistema de casa inteligente, permitindo que eles sejam facilmente reconhecidos e diferenciados uns dos outros. O nome do usuário pode ser utilizado em interfaces de usuário, relatórios e outras partes do sistema onde a identificação do usuário seja necessária, proporcionando uma experiência mais personalizada e intuitiva para os usuários.
     */
    private String nome;

    /**
     * HashMap para armazenar as casas administradas pelo usuário, onde a chave é o ID da casa e o valor é o objeto Casa correspondente. Este atributo é importante para gerenciar as casas que o usuário tem permissão para administrar, permitindo que o sistema de casa inteligente controle as ações e permissões do usuário em relação a essas casas. O HashMap facilita a adição, remoção e consulta das casas administradas pelo usuário, proporcionando uma maneira eficiente de organizar e acessar as informações relacionadas às casas sob a administração do usuário.
     */
    private HashMap<Integer, Casa> casasAdministradas;

    /**
     * HashMap para armazenar as casas às quais o usuário tem acesso, onde a chave é o ID da casa e o valor é o objeto Casa correspondente. Este atributo é importante para gerenciar as casas que o usuário pode usar, permitindo que o sistema de casa inteligente controle as ações e permissões do usuário em relação a essas casas. O HashMap facilita a adição, remoção e consulta das casas de usuário, proporcionando uma maneira eficiente de organizar e acessar as informações relacionadas às casas às quais o usuário tem acesso, independentemente de ser um administrador ou um usuário normal dessas casas.
     */
    private HashMap<Integer, Casa> casasUtilizador;

    /**
     * Construtor para a classe Utilizador, que recebe parâmetros para o ID e nome do usuário. Este construtor é importante para criar objetos da classe Utilizador com atributos específicos, permitindo que o utilizador defina as características do usuário de forma eficiente. O construtor inicializa os HashMaps para as casas administradas e casas de usuário como vazios, preparando o objeto para armazenar as informações relacionadas às casas associadas ao usuário.
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
     * Construtor padrão para a classe Utilizador, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Utilizador sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie um usuário com características padrão. O construtor define o ID do usuário como 0, o nome como uma string vazia, e inicializa os HashMaps para as casas administradas e casas de usuário como vazios, preparando o objeto para armazenar as informações relacionadas às casas associadas ao usuário.
     */
    public Utilizador(){
        this.id = 0;
        this.nome = "";
        this.casasAdministradas = new HashMap<>();
        this.casasUtilizador = new HashMap<>();
    }

    /**
     * Construtor de cópia para a classe Utilizador, que recebe um objeto Utilizador como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Utilizador, permitindo que o utilizador duplique um usuário com as mesmas características de forma eficiente. O construtor copia o ID e o nome do usuário diretamente, e cria novos HashMaps para as casas administradas e casas de usuário, copiando os conteúdos dos HashMaps do objeto fornecido como parâmetro para garantir que as informações relacionadas às casas associadas ao usuário sejam preservadas na nova instância.
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
     * Método para obter o ID do usuário, que retorna o valor do atributo id. Este método é importante para permitir que outras partes do sistema de casa inteligente acessem o ID do usuário de forma controlada, garantindo que as informações relacionadas ao usuário sejam acessadas de maneira eficiente e segura. O método retorna o ID do usuário como um número inteiro, permitindo que ele seja utilizado para identificar de forma única o usuário dentro do sistema.
     * @return O ID do usuário.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Método para obter o nome do usuário, que retorna o valor do atributo nome. Este método é importante para permitir que outras partes do sistema de casa inteligente acessem o nome do usuário de forma controlada, garantindo que as informações relacionadas ao usuário sejam acessadas de maneira eficiente e segura. O método retorna o nome do usuário como uma string, permitindo que ele seja utilizado para identificar de forma legível e amigável o usuário dentro do sistema.
     * @return O nome do usuário.
     */
    public String getNome(){
        return this.nome;
    }

    /**
     * Método para obter as casas administradas pelo usuário, que retorna um HashMap contendo as casas administradas. Este método é importante para permitir que outras partes do sistema de casa inteligente acessem as informações sobre as casas que o usuário tem permissão para administrar, garantindo que as informações relacionadas às casas associadas ao usuário sejam acessadas de maneira eficiente e segura. O método retorna um novo HashMap contendo as casas administradas pelo usuário, garantindo que a estrutura interna do objeto seja protegida contra modificações externas.
     * @return Um HashMap contendo as casas administradas pelo usuário.
     */
    public HashMap<Integer, Casa> getCasasAdministradas(){
        return new HashMap<>(casasAdministradas);
    }

    /**
     * Método para obter as casas às quais o usuário tem acesso, que retorna um HashMap contendo as casas de usuário. Este método é importante para permitir que outras partes do sistema de casa inteligente acessem as informações sobre as casas que o usuário pode usar, garantindo que as informações relacionadas às casas associadas ao usuário sejam acessadas de maneira eficiente e segura. O método retorna um novo HashMap contendo as casas às quais o usuário tem acesso, garantindo que a estrutura interna do objeto seja protegida contra modificações externas.
     * @return Um HashMap contendo as casas às quais o usuário tem acesso.
     */
    public HashMap<Integer, Casa> getCasasUtilizador(){
        return new HashMap<>(casasUtilizador);
    }

    //setters
    /**
     * Método para definir o ID do usuário, que recebe um número inteiro como parâmetro e atribui esse valor ao atributo id. Este método é importante para permitir que outras partes do sistema de casa inteligente modifiquem o ID do usuário de forma controlada, garantindo que as informações relacionadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um número inteiro representando o novo ID do usuário e o atribui ao atributo id, permitindo que o usuário seja identificado de forma única dentro do sistema.
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Método para definir o nome do usuário, que recebe uma string como parâmetro e atribui esse valor ao atributo nome. Este método é importante para permitir que outras partes do sistema de casa inteligente modifiquem o nome do usuário de forma controlada, garantindo que as informações relacionadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe uma string representando o novo nome do usuário e o atribui ao atributo nome, permitindo que o usuário seja identificado de forma legível e amigável dentro do sistema.
      * @param nome
     */
    public void setNome(String n){
        this.nome = n;
    }

    /**
     * Método para definir as casas administradas pelo usuário, que recebe um HashMap contendo as casas administradas e atribui uma cópia desse HashMap ao atributo casasAdministradas. Este método é importante para permitir que outras partes do sistema de casa inteligente modifiquem as informações sobre as casas que o usuário tem permissão para administrar de forma controlada, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um HashMap onde a chave é o ID da casa e o valor é o objeto Casa correspondente, e atribui uma nova cópia desse HashMap ao atributo casasAdministradas, garantindo que a estrutura interna do objeto seja protegida contra modificações externas.
     * @param casasAdministradas
     */
    public void setCasasAdministradas(HashMap<Integer, Casa> casasAdministradas){
        this.casasAdministradas = new HashMap<>(casasAdministradas);
    }

    /**
     * Método para definir as casas às quais o usuário tem acesso, que recebe um HashMap contendo as casas de usuário e atribui uma cópia desse HashMap ao atributo casasUtilizador. Este método é importante para permitir que outras partes do sistema de casa inteligente modifiquem as informações sobre as casas que o usuário pode usar de forma controlada, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um HashMap onde a chave é o ID da casa e o valor é o objeto Casa correspondente, e atribui uma nova cópia desse HashMap ao atributo casasUtilizador, garantindo que a estrutura interna do objeto seja protegida contra modificações externas.
     * @param casasUtilizador
     */
    public void setCasasUtilizador(HashMap<Integer, Casa> casasUtilizador){
        this.casasUtilizador = new HashMap<>(casasUtilizador);
    }



    //adicionar casa administrada
    /**
     * Método para adicionar uma casa administrada ao usuário, que recebe um objeto Casa como parâmetro e adiciona essa casa ao HashMap de casas administradas do usuário. Este método é importante para permitir que outras partes do sistema de casa inteligente associem uma casa ao usuário como administrador, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um objeto Casa e o adiciona ao HashMap casasAdministradas usando o ID da casa como chave, e também adiciona a casa ao HashMap casasUtilizador para garantir que o usuário tenha acesso à casa mesmo que seja apenas um administrador.
     * @param c
     */
    public void adicionarCasaAdministrada(Casa c){
        this.casasAdministradas.put(c.getId(), c);
        this.casasUtilizador.put(c.getId(), c); //um utilizador que é administrador de uma casa também é um utilizador dessa casa
    }

    //adicionar casa utilizador
    /**
     * Método para adicionar uma casa de usuário ao usuário, que recebe um objeto Casa como parâmetro e adiciona essa casa ao HashMap de casas de usuário do usuário. Este método é importante para permitir que outras partes do sistema de casa inteligente associem uma casa ao usuário como um usuário normal, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um objeto Casa e o adiciona ao HashMap casasUtilizador usando o ID da casa como chave, permitindo que o usuário tenha acesso à casa mesmo que não seja um administrador dessa casa.
     * @param c
     */
    public void adicionarCasaUtilizador(Casa c){
        this.casasUtilizador.put(c.getId(), c);
    }

    //remover casa administrada
    /**
     * Método para remover uma casa administrada do usuário, que recebe um objeto Casa como parâmetro e remove essa casa do HashMap de casas administradas do usuário. Este método é importante para permitir que outras partes do sistema de casa inteligente desassocie uma casa do usuário como administrador, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um objeto Casa e remove a casa do HashMap casasAdministradas usando o ID da casa como chave, e também remove a casa do HashMap casasUtilizador para garantir que o usuário não tenha mais acesso à casa se ela for removida como administrador.
     * @param c
     */
    public void removerCasaAdministrada(Casa c){
        this.casasAdministradas.remove(c.getId());
    }

    //remover casa Utilizador
    /**
     * Método para remover uma casa de usuário do usuário, que recebe um objeto Casa como parâmetro e remove essa casa do HashMap de casas de usuário do usuário. Este método é importante para permitir que outras partes do sistema de casa inteligente desassocie uma casa do usuário como um usuário normal, garantindo que as informações relacionadas às casas associadas ao usuário sejam atualizadas de maneira eficiente e segura. O método recebe um objeto Casa e remove a casa do HashMap casasUtilizador usando o ID da casa como chave, e também verifica se a casa está presente no HashMap casasAdministradas para garantir que o usuário não seja mais um administrador dessa casa, removendo-a do HashMap casasAdministradas se necessário.
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
     * Método para verificar se o usuário pode administrar uma determinada casa, que recebe um objeto Casa como parâmetro e retorna um valor booleano indicando se o usuário tem permissão para administrar essa casa. Este método é importante para permitir que outras partes do sistema de casa inteligente verifiquem as permissões do usuário em relação a uma casa específica, garantindo que as ações e controles relacionados à administração da casa sejam realizados de maneira eficiente e segura. O método verifica se o ID da casa está presente no HashMap casasAdministradas do usuário e retorna true se o usuário for um administrador dessa casa, ou false caso contrário.
     * @param c
     * @return
     */
    public boolean podeAdministrarCasa(Casa c){
        return this.casasAdministradas.containsKey(c.getId());
    }

    //ver se um utilizador tem acesso a uma dada casa
    /**
     * Método para verificar se o usuário tem acesso a uma determinada casa, que recebe um objeto Casa como parâmetro e retorna um valor booleano indicando se o usuário tem permissão para usar essa casa. Este método é importante para permitir que outras partes do sistema de casa inteligente verifiquem as permissões do usuário em relação a uma casa específica, garantindo que as ações e controles relacionados ao uso da casa sejam realizados de maneira eficiente e segura. O método verifica se o ID da casa está presente no HashMap casasUtilizador do usuário e retorna true se o usuário tiver acesso a essa casa, ou false caso contrário.
     * @param c
     * @return boolean indicando se o usuário tem acesso à casa.
     */
    public boolean podeUsarCasa(Casa c){
        return (this.casasUtilizador.containsKey(c.getId()) || this.casasAdministradas.containsKey(c.getId()));//um utilizador pode usar uma casa se for um utilizador ou um administrador dessa casa
    }


    /**
     * Método toString para a classe Utilizador, que retorna uma string representando as informações do usuário. Este método é importante para fornecer uma representação legível e amigável do objeto Utilizador, permitindo que as informações relacionadas ao usuário sejam facilmente compreendidas e exibidas em interfaces de usuário, relatórios e outras partes do sistema de casa inteligente. O método retorna uma string contendo o ID do usuário, o nome do usuário, o número de casas administradas e o número de casas às quais o usuário tem acesso, proporcionando uma visão geral das características e associações do usuário dentro do sistema.
      * @return Uma string representando as informações do usuário.
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
     * Método para verificar se o usuário é um administrador de uma determinada casa, que recebe um objeto Casa como parâmetro e retorna um valor booleano indicando se o usuário é um administrador dessa casa. Este método é importante para permitir que outras partes do sistema de casa inteligente verifiquem se o usuário tem permissões de administração em relação a uma casa específica, garantindo que as ações e controles relacionados à administração da casa sejam realizados de maneira eficiente e segura. O método verifica se o ID da casa está presente no HashMap casasAdministradas do usuário e retorna true se o usuário for um administrador dessa casa, ou false caso contrário.
     * @param c
     * @return boolean indicando se o usuário é um administrador da casa.
     */
    public boolean serAdmin(Casa c) { // Se este utilizador é administrador da casa c
        return this.casasAdministradas.containsKey(c.getId());
    }

    /**
     * Método para verificar se o usuário é um usuário normal de uma determinada casa, que recebe um objeto Casa como parâmetro e retorna um valor booleano indicando se o usuário é um usuário normal dessa casa. Este método é importante para permitir que outras partes do sistema de casa inteligente verifiquem se o usuário tem permissões de uso em relação a uma casa específica, garantindo que as ações e controles relacionados ao uso da casa sejam realizados de maneira eficiente e segura. O método verifica se o ID da casa está presente no HashMap casasUtilizador do usuário e retorna true se o usuário for um usuário normal dessa casa (ou seja, tem acesso à casa mas não é um administrador), ou false caso contrário.
     * @param c
     * @return boolean indicando se o usuário é um usuário normal da casa.
     */
    public boolean serUtilizador(Casa c) { // Se este utilizador é um utilizador normal da casa c (não administrador)
        return this.casasUtilizador.containsKey(c.getId()) && !this.serAdmin(c);
    }

    /**
     * Método equals para a classe Utilizador, que compara o objeto atual com outro objeto para verificar se são iguais. Este método é importante para permitir que outras partes do sistema de casa inteligente comparem objetos da classe Utilizador de forma eficiente e segura, garantindo que as comparações sejam realizadas com base em critérios específicos. O método verifica se os objetos são do mesmo tipo e compara os IDs dos usuários para determinar se eles são considerados iguais, retornando true se os IDs forem iguais e false caso contrário.
      * @param o
      * @return boolean indicando se os objetos são iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return id == that.id;
    }

    /**
     * Método hashCode para a classe Utilizador, que retorna um valor hash para o objeto. Este método é importante para permitir que objetos da classe Utilizador sejam usados de forma eficiente em estruturas de dados baseadas em hash, como HashMap e HashSet, garantindo que os objetos possam ser armazenados e recuperados de maneira eficiente. O método retorna o valor hash do ID do usuário, garantindo que objetos com o mesmo ID tenham o mesmo valor hash, o que é consistente com a implementação do método equals.
      * @return Um valor hash para o objeto Utilizador.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}