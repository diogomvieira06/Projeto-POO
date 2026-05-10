package src.main.model;

import java.io.Serializable;
import java.util.*;
import src.main.Exceptions.DivisaoNaoEncontradaException;

/**
 * Classe que representa uma casa, contendo um nome (alcunha), um ID único e um conjunto de divisões. A casa é a entidade central do sistema, onde as divisões e os dispositivos estão organizados. A classe implementa Serializable para permitir a persistência dos dados. 
 */
public class Casa implements Serializable {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização dos objetos da classe Casa. Este valor deve ser alterado se houver mudanças significativas na estrutura da classe que possam afetar a compatibilidade dos objetos serializados.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Alcunha da casa, que é um nome dado pelo utilizador para diferenciar as casas. Este campo é importante para a identificação amigável da casa, especialmente quando o utilizador tem várias casas cadastradas no sistema.
     */
    private String alcunha; 
    /**
     * ID único da casa, que é um número inteiro utilizado para identificar a casa de forma única no sistema. Este ID é fundamental para operações de busca, atualização e remoção de casas, garantindo que cada casa possa ser referenciada de maneira precisa e eficiente.
     */
    private int id;

    /**
     * Conjunto de divisões da casa, representado por um HashMap onde a chave é o ID da divisão e o valor é o objeto Divisao correspondente. O uso de um HashMap permite uma busca eficiente por ID, facilitando a gestão das divisões dentro da casa. Cada divisão pode conter dispositivos e outras informações relevantes para a organização da casa.
     */
    private HashMap<Integer, Divisao> divisoes; 

    /**
     * Construtor da classe Casa, que inicializa a alcunha, o ID e o conjunto de divisões. A alcunha e o ID são fornecidos como parâmetros, enquanto o conjunto de divisões é inicializado como um HashMap vazio. Este construtor é utilizado para criar uma nova casa com as informações básicas necessárias para sua identificação e organização.
     * @param alcunha
     * @param id
     */
    public Casa(String alcunha, int id) {
        this.alcunha = alcunha;
        this.id = id;
        this.divisoes = new HashMap<>();
    }

    /**
     * Construtor de cópia da classe Casa, que cria uma nova instância de Casa a partir de outra instância existente. Este construtor copia a alcunha e o ID da casa original, e também cria um novo HashMap para as divisões, copiando cada divisão individualmente para garantir que as divisões da nova casa sejam independentes das divisões da casa original. Este construtor é útil para criar uma nova casa com as mesmas características de uma casa existente, sem compartilhar referências às mesmas divisões.
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
     * Construtor padrão da classe Casa, que inicializa a alcunha como uma string vazia, o ID como zero e o conjunto de divisões como um HashMap vazio. Este construtor é utilizado para criar uma nova casa sem informações pré-definidas, permitindo que o utilizador defina a alcunha, o ID e as divisões posteriormente.
     */
    public Casa() {
        this.alcunha = "";
        this.id = 0;
        this.divisoes = new HashMap<>();
    }

    /**
     * Método getter para a alcunha da casa, que retorna o nome dado pelo utilizador para a casa. Este método é importante para a identificação amigável da casa, permitindo que o utilizador veja e utilize a alcunha em vez do ID para referenciar a casa.
     * @return A alcunha da casa.
     */
    public String getAlcunha() {
        return alcunha;
    }

    /**
     * Método setter para a alcunha da casa, que permite definir ou atualizar o nome dado pelo utilizador para a casa. Este método é importante para a identificação amigável da casa, permitindo que o utilizador personalize a alcunha de acordo com suas preferências.
     * @param alcunha
     */
    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    /**
     * Método getter para o ID da casa, que retorna o número inteiro utilizado para identificar a casa de forma única no sistema. Este método é fundamental para operações de busca, atualização e remoção de casas, garantindo que cada casa possa ser referenciada de maneira precisa e eficiente.
     * @return O ID da casa.
     */
    public int getId() {
        return id;
    }

    /**
     * Método setter para o ID da casa, que permite definir ou atualizar o número inteiro utilizado para identificar a casa de forma única no sistema. Este método é fundamental para operações de busca, atualização e remoção de casas, garantindo que cada casa possa ser referenciada de maneira precisa e eficiente. É importante garantir que o ID seja único para cada casa para evitar conflitos no sistema.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método getter para o conjunto de divisões da casa, que retorna um HashMap contendo as divisões organizadas por ID. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador acesse e manipule as divisões de forma eficiente. O método retorna uma cópia do HashMap para evitar que seja alterado diretamente, garantindo a integridade dos dados da casa.
     * @return O conjunto de divisões da casa.
     */
    public HashMap<Integer, Divisao> getDivisoes() {
        return new HashMap<>(divisoes);// copia do HashMap para evitar que seja alterado diretamente
    }

    /**
     * Método setter para o conjunto de divisões da casa, que permite definir ou atualizar o HashMap contendo as divisões organizadas por ID. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador defina ou atualize as divisões de forma eficiente. O método recebe um HashMap como parâmetro e cria uma cópia do mesmo para garantir que o HashMap original não seja alterado diretamente, mantendo a integridade dos dados da casa.
     * @param divisoes
     */
    public void setDivisoes(HashMap<Integer, Divisao> divisoes) {
        this.divisoes = new HashMap<>(divisoes);
    }

    /**
     * Método para obter uma divisão da casa por seu ID, que recebe um número inteiro como parâmetro e retorna a divisão correspondente. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador acesse uma divisão específica de forma eficiente. Se a divisão com o ID fornecido não for encontrada, o método lança uma exceção DivisaoNaoEncontradaException para indicar que a divisão não existe na casa.
     * @param idDivisao 
     * @return A divisão correspondente ao ID fornecido.
     */
    public Divisao obterDivisaoPorId(int idDivisao) {
        Divisao d = divisoes.get(idDivisao);
        if (d == null)
            throw new Divisao.DivisaoNaoEncontradaException();
        return d;
    }

    /**
     * Método para adicionar uma divisão à casa, que recebe um objeto Divisao como parâmetro e o adiciona ao HashMap de divisões da casa. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador adicione novas divisões de forma eficiente. O método utiliza o ID da divisão como chave no HashMap para garantir que cada divisão seja identificada de maneira única dentro da casa.
     * @param d
     */
    public void adicionarDivisao(Divisao d) {
        this.divisoes.put(d.getId(), d);
    }

    /**
     * Método para remover uma divisão da casa, que recebe um objeto Divisao como parâmetro e o remove do HashMap de divisões da casa. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador remova divisões de forma eficiente. O método utiliza o ID da divisão para localizar e remover a divisão do HashMap, garantindo que a divisão seja removida corretamente da casa.
     * @param d
     */
    public void removerDivisao(Divisao d) {
        this.divisoes.remove(d.getId());
    }

    /**
     * Método para listar as divisões da casa, que percorre o HashMap de divisões e imprime o nome e o ID de cada divisão. Este método é importante para a gestão das divisões dentro da casa, permitindo que o utilizador visualize as divisões existentes de forma clara e organizada. O método utiliza um loop para iterar sobre os valores do HashMap, garantindo que todas as divisões sejam listadas corretamente.
     */
    public void listarDivisoes() {
        for (Divisao d : divisoes.values()) {
            System.out.println(d.getNome() + " - ID: " + d.getId());
        }
    }

    /**
     * Método equals para a classe Casa, que compara dois objetos Casa para determinar se são iguais. O método verifica se os objetos são a mesma instância, se o objeto passado como parâmetro é nulo ou de uma classe diferente, e finalmente compara os IDs das casas para determinar se são iguais. Este método é importante para garantir que as operações de comparação entre casas sejam realizadas de forma correta e eficiente, permitindo que o sistema identifique corretamente as casas com base em seus IDs únicos.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Casa casa = (Casa) o;
        return id == casa.id;
    }

    /**
     * Método hashCode para a classe Casa, que retorna um código hash baseado no ID da casa. Este método é importante para garantir que as operações de hash, como a inserção em coleções baseadas em hash (por exemplo, HashMap), sejam realizadas de forma correta e eficiente. O método utiliza o método hashCode da classe Integer para gerar um código hash a partir do ID da casa, garantindo que casas com IDs iguais tenham o mesmo código hash.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}