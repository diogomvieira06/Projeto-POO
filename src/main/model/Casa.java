package src.main.model;
import java.io.Serializable;
import java.util.*;
import src.main.Exceptions.DivisaoNaoEncontradaException;

/** 
 * Classe que representa uma casa, contendo um ID, uma alcunha e um conjunto de divisões.
 * A classe é serializável para permitir a persistência dos dados.
 * A utilização de um HashMap para as divisões permite uma busca eficiente por ID.
 * A classe inclui métodos para adicionar, remover e listar divisões, bem como para obter uma divisão específica por ID. A implementação de equals e hashCode baseia-se no ID da casa, garantindo que cada casa seja única.
 */
public class Casa implements Serializable {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Alcunha da casa, utilizada para diferenciar as casas de forma amigável.
     */
    private String alcunha;

    /**
     * ID único da casa, utilizado para identificação e comparação entre casas.
     */
    private int id;

    /**
     * Conjunto de divisões da casa, armazenado em um HashMap para permitir uma busca eficiente por ID. A chave do HashMap é o ID da divisão, e o valor é a própria divisão.
     */
    private HashMap<Integer, Divisao> divisoes; 

    /**
     * Construtor da classe Casa, que inicializa a alcunha, o ID e o conjunto de divisões.
     * @param alcunha
     * @param id
     */
    public Casa(String alcunha, int id) {
        this.alcunha = alcunha;
        this.id = id;
        this.divisoes = new HashMap<>();
    }

    /**
     * Construtor de cópia da classe Casa, que cria uma nova instância com os mesmos valores de alcunha, ID e divisões. 
     * As divisões são copiadas para garantir que a nova casa tenha suas próprias instâncias de divisões.
     * @param c
     */
    public Casa(Casa c) {
        this.alcunha = c.alcunha;
        this.id = c.id;
        this.divisoes = new HashMap<>();
        for (Divisao div : c.divisoes.values()) {
            this.divisoes.put(div.getId(), new Divisao(div));
        }
    }

    /**
     * Construtor vazio da classe Casa, que inicializa a alcunha como uma string vazia, o ID como zero e o conjunto de divisões como um HashMap vazio.
     */
    public Casa() {
        this.alcunha = "";
        this.id = 0;
        this.divisoes = new HashMap<>();
    }

    /**
     * Método getter para a alcunha da casa, que retorna a alcunha atual da casa.
     * @return A alcunha da casa.
     */
    public String getAlcunha() {
        return alcunha;
    }

    /**
     * Método setter para a alcunha da casa, que permite definir uma nova alcunha para a casa.
     * @param alcunha
     */
    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    /**
     * Método getter para o ID da casa, que retorna o ID atual da casa.
     * @return O ID da casa.
     */
    public int getId() {
        return id;
    }

    /**
     * Método setter para o ID da casa, que permite definir um novo ID para a casa. O ID deve ser único para cada casa.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método getter para as divisões da casa, que retorna uma cópia do HashMap de divisões. Isso é feito para evitar que o HashMap original seja alterado diretamente, garantindo a integridade dos dados da casa.
     * @return
     */
    public HashMap<Integer, Divisao> getDivisoes() {
        return new HashMap<>(divisoes);//copia do HashMap para evitar que seja alterado diretamente
    }

    /**
     * Método setter para as divisões da casa, que permite definir um novo conjunto de divisões para a casa. O método recebe um HashMap de divisões e cria uma cópia dele para garantir que o HashMap original não seja alterado diretamente, mantendo a integridade dos dados da casa.
     * @param divisoes
     */
    public void setDivisoes(HashMap<Integer, Divisao> divisoes) {
        this.divisoes = new HashMap<>(divisoes);
    }

    /**
     * Método para obter uma divisão específica por ID. O método recebe o ID da divisão como parâmetro e retorna a divisão correspondente. Se a divisão não for encontrada, uma exceção DivisaoNaoEncontradaException é lançada para indicar que a divisão com o ID especificado não existe na casa.
     * @param idDivisao
     * @return
     */
    public Divisao obterDivisaoPorId(int idDivisao) {
        Divisao d = divisoes.get(idDivisao);
        if (d == null) throw new Divisao.DivisaoNaoEncontradaException();
        return d;
    }

    /**
     * Método para adicionar uma nova divisão à casa. O método recebe uma instância de Divisao como parâmetro e a adiciona ao HashMap de divisões, utilizando o ID da divisão como chave. Isso permite que a divisão seja facilmente acessada posteriormente por meio do seu ID.
     * @param d
     */
    public void adicionarDivisao(Divisao d) {
        this.divisoes.put(d.getId(), d);
    }

    /**
     * Método para remover uma divisão da casa. O método recebe uma instância de Divisao como parâmetro e remove a divisão correspondente do HashMap de divisões, utilizando o ID da divisão como chave. Isso permite que a divisão seja facilmente removida da casa por meio do seu ID.
     * @param d
     */
    public void removerDivisao(Divisao d) {
        this.divisoes.remove(d.getId());
    }

    /**
     * Método para listar todas as divisões da casa. O método percorre o HashMap de divisões e imprime o nome e o ID de cada divisão. Isso permite que o usuário visualize todas as divisões presentes na casa de forma organizada.
     */
    public void listarDivisoes() {
        for (Divisao d : divisoes.values()) {
            System.out.println(d.getNome() + " - ID: " + d.getId());
        }
    }

    /**
     * Método equals para comparar duas instâncias de Casa. O método verifica se as duas instâncias são iguais com base no ID da casa. Se os IDs forem iguais, as casas são consideradas iguais. Caso contrário, elas são consideradas diferentes. Isso garante que cada casa seja única com base no seu ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return id == casa.id;
    }

    /**
     * Método hashCode para gerar um código hash para a instância de Casa. O método utiliza o ID da casa para calcular o código hash, garantindo que casas com o mesmo ID tenham o mesmo código hash. Isso é importante para garantir a consistência entre os métodos equals e hashCode, permitindo que as casas sejam corretamente armazenadas em estruturas de dados como HashSet ou HashMap.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}