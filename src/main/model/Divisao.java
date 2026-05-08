package src.main.model;
import java.io.Serializable;
import java.util.*;
import src.main.Exceptions.DispositivoNaoEncontradoException;

/**
 * Classe que representa uma divisão em uma casa, contendo um nome, um ID e um conjunto de dispositivos. A classe é serializável para permitir a persistência dos dados. A utilização de um HashMap para os dispositivos permite uma busca eficiente por ID. A classe inclui métodos para adicionar, remover e listar dispositivos, bem como para obter um dispositivo específico por ID. A implementação de equals e hashCode baseia-se no ID da divisão, garantindo que cada divisão seja única.
 */
public class Divisao implements Serializable {

    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização. O valor é definido como 1L, indicando que esta é a primeira versão da classe Divisao. A utilização de um serialVersionUID é importante para evitar problemas de compatibilidade ao serializar e desserializar objetos, garantindo que as versões da classe sejam compatíveis entre si.
    */
    private static final long serialVersionUID = 1L;

    /**
     * Nome da divisão, utilizado para identificar a divisão de forma amigável. O nome é uma string que pode ser atribuída e modificada conforme necessário para diferenciar as divisões dentro de uma casa.
     */
    private String nome;

    /**
     * ID único da divisão, utilizado para identificação e comparação entre divisões. O ID é um inteiro que deve ser atribuído de forma única a cada divisão para garantir que cada divisão possa ser identificada de maneira distinta, especialmente ao comparar divisões ou ao armazená-las em estruturas de dados.
     */
    private int id;

    /**
     * Conjunto de dispositivos presentes na divisão, armazenado em um HashMap para permitir uma busca eficiente por ID. A chave do HashMap é o ID do dispositivo, e o valor é a própria instância do dispositivo. Isso permite que os dispositivos sejam facilmente acessados, adicionados ou removidos da divisão com base em seu ID.
     */
    private HashMap<Integer, Dispositivo> dispositivos;

    // Inner exception classes for backward compatibility with tests
    /**
     * Exceção personalizada para indicar que uma divisão não foi encontrada. Esta classe estende a classe DivisaoNaoEncontradaException definida no pacote src.main.Exceptions, permitindo que seja lançada quando uma divisão específica não puder ser localizada em uma casa. A implementação inclui construtores para criar a exceção com ou sem uma mensagem personalizada, facilitando a identificação do erro durante a depuração e o tratamento de exceções.
     */
    public static class DivisaoNaoEncontradaException extends src.main.Exceptions.DivisaoNaoEncontradaException {
        public DivisaoNaoEncontradaException() { super(); }
        public DivisaoNaoEncontradaException(String message) { super(message); }
    }

    /**
     * Exceção personalizada para indicar que um dispositivo não foi encontrado em uma divisão. Esta classe estende a classe DispositivoNaoEncontradoException definida no pacote src.main.Exceptions, permitindo que seja lançada quando um dispositivo específico não puder ser localizado em uma divisão. A implementação inclui construtores para criar a exceção com ou sem uma mensagem personalizada, facilitando a identificação do erro durante a depuração e o tratamento de exceções.
     */
    public static class DispositivoNaoEncontradoException extends src.main.Exceptions.DispositivoNaoEncontradoException {
        public DispositivoNaoEncontradoException() { super(); }
        public DispositivoNaoEncontradoException(String message) { super(message); }
    }

    /**
     * Construtor da classe Divisao, que inicializa os atributos nome, id e o conjunto de dispositivos. O construtor recebe parâmetros para o nome e o ID da divisão, e os atribui aos respectivos atributos da classe. O conjunto de dispositivos é inicializado como um HashMap vazio, pronto para armazenar os dispositivos que serão adicionados à divisão.
     * @param nome Nome da divisão
     * @param id ID único da divisão
     */
    public Divisao(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.dispositivos = new HashMap<>();
    }

    /**
     * Construtor de cópia da classe Divisao, que cria uma nova instância com os mesmos valores de nome, ID e dispositivos. As instâncias dos dispositivos são clonadas para garantir que a nova divisão tenha suas próprias instâncias de dispositivos, evitando que alterações em uma divisão afetem a outra. O construtor chama o construtor de cópia da classe base para garantir que os atributos herdados sejam copiados corretamente, e depois copia o valor do nome e do ID para a nova instância.
     * @param d
     */
    public Divisao(Divisao d) {
        this.nome = d.nome;
        this.id = d.id;
        this.dispositivos = new HashMap<>();
        for (Dispositivo disp : d.dispositivos.values()) {
            this.dispositivos.put(disp.getId(), disp.clone());//clone
        }
    }

    /**
     * Construtor vazio da classe Divisao, que inicializa os atributos nome como uma string vazia, ID como zero e o conjunto de dispositivos como um HashMap vazio. O construtor chama o construtor vazio da classe base para garantir que os atributos herdados sejam inicializados corretamente, e depois define o nome como uma string vazia e o ID como zero, indicando que a divisão está em um estado inicial sem informações específicas.
     */
    public Divisao() {
        this.nome = "";
        this.id = 0;
        this.dispositivos = new HashMap<>();
    }

    /**
     * Método getter para o nome da divisão, que retorna o valor atual do nome da divisão. O método é utilizado para acessar o valor do nome de forma controlada, permitindo que os usuários obtenham o nome da divisão quando necessário para identificação ou exibição de informações relacionadas à divisão.
     * @return Nome da divisão
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método setter para o nome da divisão, que permite definir um novo valor para o nome da divisão. O método é utilizado para atualizar o valor do nome de forma controlada, garantindo que o nome seja atribuído corretamente e fornecendo uma maneira de modificar a identificação da divisão conforme necessário.
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método getter para o ID da divisão, que retorna o valor atual do ID da divisão. O método é utilizado para acessar o valor do ID de forma controlada, permitindo que os usuários obtenham o ID da divisão quando necessário para identificação ou comparação entre divisões.
     * @return ID da divisão
     */
    public int getId() {
        return id;
    }

    /**
     * Método setter para o ID da divisão, que permite definir um novo valor para o ID da divisão. O método é utilizado para atualizar o valor do ID de forma controlada, garantindo que o ID seja atribuído corretamente e fornecendo uma maneira de modificar a identificação da divisão conforme necessário. O ID deve ser único para cada divisão para garantir a correta identificação e comparação entre divisões.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método getter para o conjunto de dispositivos da divisão, que retorna uma cópia do HashMap contendo os dispositivos presentes na divisão. O método é utilizado para acessar o conjunto de dispositivos de forma controlada, permitindo que os usuários obtenham as informações dos dispositivos sem acessar diretamente o atributo. O método retorna um novo HashMap contendo os mesmos dispositivos, garantindo que a estrutura original seja protegida contra modificações externas.
     * @return HashMap contendo os dispositivos da divisão
     */
    public HashMap<Integer, Dispositivo> getDispositivos() {
        return new HashMap<>(dispositivos);
    }

    /**
     * Método setter para o conjunto de dispositivos da divisão, que permite definir um novo conjunto de dispositivos para a divisão. O método recebe um HashMap contendo os dispositivos e cria uma cópia dele para garantir que a estrutura original seja protegida contra modificações externas. Isso permite que os usuários atualizem o conjunto de dispositivos da divisão de forma controlada, garantindo a integridade dos dados da divisão.
     * @param dispositivos
     */
    public void setDispositivos(HashMap<Integer, Dispositivo> dispositivos) {
        this.dispositivos = new HashMap<>(dispositivos);
    }

    /**
     * Método para obter um dispositivo específico por ID, que retorna a instância do dispositivo correspondente ao ID fornecido. O método utiliza o HashMap de dispositivos para realizar uma busca eficiente pelo ID. Se o dispositivo com o ID especificado não for encontrado, o método lança uma exceção personalizada DispositivoNaoEncontradoException, indicando que o dispositivo não está presente na divisão. Isso permite que os usuários obtenham informações sobre um dispositivo específico de forma controlada e tratem adequadamente a situação em que o dispositivo não esteja disponível.
     * @param idDispositivo
     * @return
     */
    public Dispositivo obterDispositivoPorId(int idDispositivo) {
        Dispositivo d = dispositivos.get(idDispositivo);
        if (d == null) throw new Divisao.DispositivoNaoEncontradoException();
        return d;
    }

    /**
     * Método para adicionar um dispositivo à divisão, que recebe uma instância de Dispositivo como parâmetro e a adiciona ao HashMap de dispositivos, utilizando o ID do dispositivo como chave. Isso permite que o dispositivo seja facilmente acessado posteriormente por meio do seu ID. O método é utilizado para atualizar o conjunto de dispositivos da divisão de forma controlada, garantindo que os dispositivos sejam adicionados corretamente e fornecendo uma maneira de expandir a funcionalidade da divisão com novos dispositivos.
     * @param d
     */
    public void adicionarDispositivo(Dispositivo d) {
        this.dispositivos.put(d.getId(), d);
    }

    /**
     * Método para remover um dispositivo da divisão, que recebe uma instância de Dispositivo como parâmetro e remove o dispositivo correspondente do HashMap de dispositivos, utilizando o ID do dispositivo como chave. Isso permite que o dispositivo seja facilmente removido da divisão por meio do seu ID. O método é utilizado para atualizar o conjunto de dispositivos da divisão de forma controlada, garantindo que os dispositivos sejam removidos corretamente e fornecendo uma maneira de gerenciar a funcionalidade da divisão ao eliminar dispositivos que não são mais necessários.
     * @param d
     */
    public void removerDispositivo(Dispositivo d) {
        this.dispositivos.remove(d.getId());
    }

    /**
     * Método para listar os dispositivos presentes na divisão, que percorre o HashMap de dispositivos e imprime o tipo, marca, modelo e ID de cada dispositivo. O método é utilizado para fornecer uma visão geral dos dispositivos presentes na divisão, permitindo que os usuários visualizem as informações relevantes de cada dispositivo de forma organizada. A impressão inclui o tipo do dispositivo, a marca, o modelo e o ID, facilitando a identificação e a diferenciação entre os dispositivos listados.
     */
    public void listarDispositivos() {
        for (Dispositivo d : dispositivos.values()) {
            System.out.println(d.getTipo() + " - " + d.getMarca() + " " + d.getModelo() + " , ID -> " + d.getId());//nao esta a aparecer o id(texto),porque
        }
    }

    /**
     * Método para comparar dois objetos do tipo Divisao, que verifica se os objetos são iguais com base no valor do ID. O método primeiro verifica se os objetos são a mesma instância, retornando true se forem. Em seguida, verifica se o objeto fornecido é nulo ou se pertence a uma classe diferente, retornando false nesses casos. Por fim, o método compara os IDs dos dois objetos e retorna true se forem iguais, indicando que as divisões são consideradas iguais com base em seu ID.
     * @param o Objeto a ser comparado com a instância atual.
     * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return id == divisao.id;
    }

    /**
     * Método para calcular o hash code da divisão, que retorna um valor inteiro baseado no ID da divisão. O método utiliza a função hashCode da classe Integer para calcular o hash code com base no valor do ID, garantindo que divisões com o mesmo ID tenham o mesmo hash code, o que é importante para a correta funcionalidade de estruturas de dados baseadas em hash, como HashMap e HashSet.
     * @return Hash code da divisão baseado no ID.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}