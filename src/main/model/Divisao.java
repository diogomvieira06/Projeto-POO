package src.main.model;

import java.io.Serializable;
import java.util.*;
import src.main.Exceptions.DispositivoNaoEncontradoException;

/**
 * Classe Divisao que representa uma divisão ou ambiente dentro de uma casa inteligente. Esta classe inclui atributos para o nome da divisão, um identificador único e uma coleção de dispositivos associados à divisão. A classe fornece métodos para obter e definir os atributos, bem como métodos para adicionar, remover e listar os dispositivos presentes na divisão. A classe é serializável, permitindo que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido.
 */
public class Divisao implements Serializable {

    /**
     * Atributo serialVersionUID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Divisao. Este atributo é importante para evitar problemas de incompatibilidade de classes durante o processo de serialização, garantindo que os objetos possam ser corretamente convertidos em um formato que pode ser armazenado ou transmitido, e posteriormente reconstruídos sem perda de dados ou erros de classe. O valor do serialVersionUID é definido como 1L, indicando a versão inicial da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Atributos para o nome da divisão, um identificador único e uma coleção de dispositivos associados à divisão. O nome da divisão é representado como uma string, o identificador é representado como um número inteiro e a coleção de dispositivos é representada como um HashMap que associa os IDs dos dispositivos aos objetos Dispositivo correspondentes. Esses atributos são importantes para definir as características da divisão dentro do sistema de casa inteligente, permitindo que o utilizador identifique a divisão pelo nome, gerencie os dispositivos associados à divisão usando seus IDs e acesse as informações dos dispositivos de forma eficiente.
     */
    private String nome;

    /**
     * Atributo para o identificador único da divisão, representado como um número inteiro. Este atributo é importante para garantir que cada divisão dentro do sistema de casa inteligente tenha um identificador exclusivo, permitindo que o sistema identifique e diferencie as divisões de forma eficiente. O identificador pode ser usado para acessar e gerenciar as divisões dentro do sistema, facilitando a organização e o controle dos ambientes da casa inteligente.
     */
    private int id;

    /**
     * Atributo para a coleção de dispositivos associados à divisão, representado como um HashMap que associa os IDs dos dispositivos aos objetos Dispositivo correspondentes. Este atributo é importante para gerenciar os dispositivos presentes na divisão, permitindo que o utilizador adicione, remova e acesse os dispositivos de forma eficiente usando seus IDs. O HashMap proporciona uma estrutura de dados eficiente para armazenar e recuperar os dispositivos associados à divisão, facilitando a organização e o controle dos dispositivos dentro do sistema de casa inteligente.
     */
    private HashMap<Integer, Dispositivo> dispositivos;

    /**
     * Exceção personalizada para indicar que uma divisão não foi encontrada. Esta exceção é lançada quando o sistema tenta acessar ou gerenciar uma divisão que não existe dentro do sistema de casa inteligente, permitindo que o utilizador saiba que a divisão em questão não foi localizada. A classe DivisaoNaoEncontradaException estende a classe RuntimeException, o que significa que é uma exceção não verificada e pode ser lançada durante a execução do programa sem a necessidade de ser declarada ou capturada explicitamente.
     */
    public static class DivisaoNaoEncontradaException extends RuntimeException {
        public DivisaoNaoEncontradaException() {
            super("Divisão não encontrada");
        }

        public DivisaoNaoEncontradaException(String message) {
            super(message);
        }
    }


    /**
     * Exceção personalizada para indicar que um dispositivo não foi encontrado. Esta exceção é lançada quando o sistema tenta acessar ou gerenciar um dispositivo que não existe dentro da divisão, permitindo que o utilizador saiba que o dispositivo em questão não foi localizado. A classe DispositivoNaoEncontradoException estende a classe RuntimeException, o que significa que é uma exceção não verificada e pode ser lançada durante a execução do programa sem a necessidade de ser declarada ou capturada explicitamente.
     */
    public static class DispositivoNaoEncontradoException
            extends src.main.Exceptions.DispositivoNaoEncontradoException {
        public DispositivoNaoEncontradoException() {
            super();
        }

        public DispositivoNaoEncontradoException(String message) {
            super(message);
        }
    }

    /**
     * Construtor para a classe Divisao, que recebe parâmetros para o nome da divisão e o identificador único. Este construtor é importante para criar objetos da classe Divisao com atributos específicos, permitindo que o utilizador defina as características da divisão de forma eficiente. O construtor inicializa os atributos nome e id com base nos valores fornecidos como parâmetros, e também inicializa a coleção de dispositivos como um HashMap vazio, pronto para armazenar os dispositivos associados à divisão.
     * @param nome
     * @param id
     */
    public Divisao(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.dispositivos = new HashMap<>();
    }

    /**
     * Construtor de cópia para a classe Divisao, que recebe um objeto Divisao como parâmetro e cria uma nova instância com os mesmos atributos. Este construtor é importante para criar cópias de objetos da classe Divisao, permitindo que o utilizador duplique uma divisão com as mesmas características de forma eficiente. O construtor copia o nome e o identificador da divisão do objeto fornecido como parâmetro, e também cria uma nova coleção de dispositivos copiando os dispositivos do objeto original usando o método clone para garantir que os objetos Dispositivo sejam duplicados corretamente.
     * @param d
     */
    public Divisao(Divisao d) {
        this.nome = d.nome;
        this.id = d.id;
        this.dispositivos = new HashMap<>();
        for (Dispositivo disp : d.dispositivos.values()) {
            this.dispositivos.put(disp.getId(), disp.clone());// clone
        }
    }

    /**
     * Construtor padrão para a classe Divisao, que inicializa os atributos com valores padrão. Este construtor é importante para criar objetos da classe Divisao sem a necessidade de fornecer parâmetros específicos, permitindo que o utilizador crie uma divisão com características padrão. O construtor define o nome da divisão como uma string vazia, o identificador como 0 e a coleção de dispositivos como um HashMap vazio, pronto para armazenar os dispositivos associados à divisão.
     */
    public Divisao() {
        this.nome = "";
        this.id = 0;
        this.dispositivos = new HashMap<>();
    }

    /**
     * Construtor para a classe Divisao, que recebe parâmetros para o nome da divisão, o identificador único e a coleção de dispositivos associados à divisão. Este construtor é importante para criar objetos da classe Divisao com atributos específicos, permitindo que o utilizador defina as características da divisão de forma eficiente. O construtor inicializa os atributos nome, id e dispositivos com base nos valores fornecidos como parâmetros, permitindo que o utilizador crie uma divisão com um conjunto específico de dispositivos associados.
     * @return O tipo do dispositivo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método setter para o nome da divisão, que permite definir o valor do nome. Este método é importante para permitir que o utilizador atribua um nome à divisão, fornecendo uma identificação clara e significativa para a divisão dentro do sistema de casa inteligente. O método recebe uma string como parâmetro e define o atributo nome com base no valor fornecido.
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método para obter o identificador único da divisão, que retorna um valor inteiro indicando o ID da divisão. Este método é importante para fornecer ao utilizador informações sobre o identificador exclusivo da divisão, permitindo que ele saiba qual é o ID associado à divisão em questão dentro do sistema de casa inteligente. O método retorna o valor do atributo id, que pode ser definido usando o método setter correspondente.
     * @return O identificador único da divisão.
     */
    public int getId() {
        return id;
    }

    /**
     * Método setter para o identificador único da divisão, que permite definir o valor do ID. Este método é importante para permitir que o utilizador atribua um identificador único à divisão, facilitando a gestão e o controle das divisões dentro do sistema de casa inteligente. O método recebe um número inteiro como parâmetro e define o atributo id com base no valor fornecido.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método para obter a coleção de dispositivos associados à divisão, que retorna um HashMap contendo os dispositivos presentes na divisão. Este método é importante para fornecer ao utilizador acesso à lista de dispositivos associados à divisão, permitindo que ele saiba quais dispositivos estão presentes na divisão e possa gerenciá-los de forma eficiente. O método retorna uma nova instância do HashMap contendo os dispositivos, garantindo que a coleção original seja protegida contra modificações externas.
     * @return A coleção de dispositivos associados à divisão.
     */
    public HashMap<Integer, Dispositivo> getDispositivos() {
        return new HashMap<>(dispositivos);
    }

    /**
     * Método setter para a coleção de dispositivos associados à divisão, que permite definir o conjunto de dispositivos presentes na divisão. Este método é importante para permitir que o utilizador atribua um conjunto específico de dispositivos à divisão, facilitando a gestão e o controle dos dispositivos dentro do sistema de casa inteligente. O método recebe um HashMap como parâmetro e define o atributo dispositivos com base no valor fornecido, criando uma nova instância do HashMap para garantir que a coleção original seja protegida contra modificações externas.
     * @param dispositivos
     */
    public void setDispositivos(HashMap<Integer, Dispositivo> dispositivos) {
        this.dispositivos = new HashMap<>(dispositivos);
    }

    /**
     * Método para obter um dispositivo específico por seu ID, que retorna o objeto Dispositivo correspondente ao ID fornecido. Este método é importante para permitir que o utilizador acesse um dispositivo específico dentro da divisão usando seu identificador único, facilitando a gestão e o controle dos dispositivos dentro do sistema de casa inteligente. O método verifica se o dispositivo com o ID fornecido existe na coleção de dispositivos e retorna o objeto correspondente. Se o dispositivo não for encontrado, o método lança uma exceção personalizada DispositivoNaoEncontradoException para indicar que o dispositivo em questão não foi localizado.
     * @param idDispositivo
     * @return O dispositivo correspondente ao ID fornecido.
      * @throws DispositivoNaoEncontradoException se o dispositivo com o ID fornecido não for encontrado na divisão.
     */
    public Dispositivo obterDispositivoPorId(int idDispositivo) {
        Dispositivo d = dispositivos.get(idDispositivo);
        if (d == null)
            throw new Divisao.DispositivoNaoEncontradoException();// lança a exceção personalizada se o dispositivo não for encontrado
        return d;
    }

    /**
     * Método para adicionar um dispositivo à divisão, que recebe um objeto Dispositivo como parâmetro e o adiciona à coleção de dispositivos associados à divisão. Este método é importante para permitir que o utilizador adicione novos dispositivos à divisão, facilitando a expansão e a personalização do sistema de casa inteligente. O método utiliza o ID do dispositivo como chave para armazenar o objeto Dispositivo na coleção de dispositivos, garantindo que cada dispositivo seja identificado de forma única dentro da divisão.
     * @param d
     */
    public void adicionarDispositivo(Dispositivo d) {
        this.dispositivos.put(d.getId(), d);
    }

    /**
     * Método para remover um dispositivo da divisão, que recebe um objeto Dispositivo como parâmetro e o remove da coleção de dispositivos associados à divisão. Este método é importante para permitir que o utilizador remova dispositivos indesejados ou obsoletos da divisão, facilitando a gestão e o controle dos dispositivos dentro do sistema de casa inteligente. O método utiliza o ID do dispositivo para identificar e remover o objeto Dispositivo correspondente da coleção de dispositivos, garantindo que a remoção seja realizada de forma eficiente e precisa.
     * @param d
     */
    public void removerDispositivo(Dispositivo d) {
        this.dispositivos.remove(d.getId());
    }

    /**
     * Método para listar os dispositivos presentes na divisão, que imprime no console as informações de cada dispositivo associado à divisão. Este método é importante para fornecer ao utilizador uma visão geral dos dispositivos presentes na divisão, permitindo que ele saiba quais dispositivos estão associados à divisão e possa gerenciá-los de forma eficiente. O método percorre a coleção de dispositivos e imprime o tipo, marca, modelo e ID de cada dispositivo, proporcionando uma descrição clara e concisa dos dispositivos presentes na divisão.
     */
    public void listarDispositivos() {
        for (Dispositivo d : dispositivos.values()) {
            System.out.println(d.getTipo() + " - " + d.getMarca() + " " + d.getModelo() + " , ID -> " + d.getId());// nao
                                                                                                                   // esta
                                                                                                                   // a
                                                                                                                   // aparecer
                                                                                                                   // o
                                                                                                                   // id(texto),porque
        }
    }

    /**
     * Método para comparar dois objetos da classe Divisao, que verifica se os objetos são iguais com base no valor do ID. Este método é importante para garantir que a comparação entre objetos da classe Divisao seja realizada de forma eficiente e precisa, permitindo que o sistema de casa inteligente identifique corretamente se duas divisões são iguais ou diferentes com base em seus identificadores únicos. O método verifica se os objetos são do mesmo tipo e se possuem o mesmo valor de ID para determinar se eles são considerados iguais.
      * @param o
      * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Divisao divisao = (Divisao) o;
        return id == divisao.id;
    }

    /**
     * Método para calcular o código hash do objeto da classe Divisao, que retorna um valor inteiro baseado no valor do ID. Este método é importante para garantir que os objetos da classe Divisao possam ser usados de forma eficiente em estruturas de dados que dependem de códigos hash, como tabelas hash ou conjuntos. O método utiliza a função hashCode da classe Integer para calcular o código hash com base no valor do ID, garantindo que objetos com o mesmo ID tenham o mesmo código hash.
      * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}