package src.main.automacao;
import java.io.Serializable;
import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.DomusControlException;

/**
 * Classe abstrata que representa uma ação a ser executada no sistema de automação residencial.
 * Cada ação tem um nome e pode ser direcionada a uma divisão específica da casa.
 * As ações são implementadas como classes anônimas dentro de métodos estáticos, permitindo a criação de ações pré-definidas para operações comuns, como ligar/desligar luzes, abrir/fechar cortinas, etc. Cada ação implementa o método executar, que define a lógica para realizar a operação desejada utilizando o DomusControl para acessar os dispositivos da casa.
 */
public abstract class Acao implements Serializable {
    /**
     * Serial version UID para garantir a compatibilidade durante a serialização e desserialização da classe Acao. Isso é importante para evitar problemas de incompatibilidade de versões quando objetos da classe Acao são salvos e carregados.
     */
    private static final long serialVersionUID = 1L;
    private String nome;

    /**
     * ID da divisão alvo para a ação. Se for -1, a ação será aplicada a todas as divisões da casa. Caso contrário, a ação será aplicada apenas à divisão com o ID correspondente. Isso permite que as ações sejam direcionadas a divisões específicas, proporcionando maior flexibilidade na automação residencial.
     */
    protected int idDivisaoAlvo = -1;

    /**
     * Construtor da classe Acao que recebe o nome da ação como parâmetro. O nome é utilizado para identificar a ação e pode ser exibido em interfaces de usuário ou logs para facilitar a compreensão do que a ação representa.
     * @param nome
     */
    public Acao(String nome) { this.nome = nome; }

    /**
     * Construtor padrão da classe Acao, que inicializa o nome da ação como uma string vazia. Este construtor é utilizado principalmente para permitir a criação de instâncias de classes anônimas que estendem Acao, onde o nome pode ser definido posteriormente.
     */
    public Acao() { this.nome = ""; }

    /**
     * Construtor de cópia da classe Acao, que cria uma nova instância de Acao com os mesmos atributos (nome e idDivisaoAlvo) de outra instância de Acao fornecida como parâmetro. Este construtor é útil para criar cópias de ações existentes, preservando suas características, o que pode ser necessário em cenários onde as ações precisam ser replicadas ou modificadas sem alterar a original.
     * @param a
     */
    public Acao(Acao a) { this.nome = a.nome; this.idDivisaoAlvo = a.idDivisaoAlvo; }

    /**
     * Método abstrato que deve ser implementado por todas as subclasses de Acao. Este método define a lógica para executar a ação utilizando o DomusControl, que é o controlador principal do sistema de automação residencial. A implementação deste método em cada ação específica determinará como a ação interage com os dispositivos da casa para realizar a operação desejada, como ligar luzes, abrir cortinas, etc.
     * @param dc
     */
    public abstract void executar(DomusControl dc);

    /**
     * Método abstrato que deve ser implementado por todas as subclasses de Acao para criar uma cópia da ação. Este método é essencial para permitir a clonagem de ações, o que pode ser necessário em cenários onde as ações precisam ser replicadas ou modificadas sem alterar a original. A implementação deste método em cada ação específica deve garantir que a nova instância criada seja uma cópia fiel da ação original, incluindo seus atributos e comportamento.
      * @return Acao
     */
    public abstract Acao clone();

    /**
     * Métodos getters e setters para os atributos da classe Acao. O método getNome retorna o nome da ação, enquanto o método setNome permite definir o nome da ação. O método setDivisaoAlvo permite definir o ID da divisão alvo para a ação, permitindo que a ação seja direcionada a uma divisão específica da casa ou a todas as divisões se o ID for -1.
     * @return
     */
    public String getNome() { return nome; }

    /**
     * Método setter para o nome da ação. Este método permite definir o nome da ação, que pode ser utilizado para identificar a ação em interfaces de usuário ou logs. O nome é uma string que descreve a ação e pode ser personalizada para refletir a operação que a ação realiza, como "Ligar Luzes", "Fechar Cortinas", etc.
     * @param nome
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Método setter para o ID da divisão alvo da ação. Este método permite definir o ID da divisão para a qual a ação será aplicada. Se o ID for -1, a ação será aplicada a todas as divisões da casa. Caso contrário, a ação será aplicada apenas à divisão com o ID correspondente. Isso proporciona flexibilidade na automação residencial, permitindo que as ações sejam direcionadas a divisões específicas conforme necessário.
     * @param id
     */
    public void setDivisaoAlvo(int id) { this.idDivisaoAlvo = id; }


    /**
     * Método estático que retorna uma instância de Acao para fechar as cortinas de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para fechar as cortinas utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e fecha as cortinas em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao fecharCortinas(int idCasa) {
        return new Acao("Fechar Cortinas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && divisao.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : divisao.getDispositivos().values()) {
                            if (disp instanceof Cortina c) c.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = fecharCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para abrir as cortinas de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para abrir as cortinas utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e abre as cortinas em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao abrirCortinas(int idCasa) {
        return new Acao("Abrir Cortinas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && divisao.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : divisao.getDispositivos().values()) {
                            if (disp instanceof Cortina c) c.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = abrirCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para ligar as luzes de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para ligar as luzes utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e liga as luzes em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao ligarLuzesCasa(int idCasa) {
        return new Acao("Ligar Luzes") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = ligarLuzesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para desligar as luzes de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para desligar as luzes utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e desliga as luzes em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao desligarLuzesCasa(int idCasa) {
        return new Acao("Desligar Luzes") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = desligarLuzesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para definir a intensidade das lâmpadas de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para ajustar a intensidade das lâmpadas utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa e a intensidade desejada como parâmetros, permitindo que a ação seja direcionada à casa correta e configure as lâmpadas com a intensidade especificada. A implementação do método executar percorre as divisões da casa e ajusta a intensidade das lâmpadas em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @param intensidade
     * @return
     */
    public static Acao definirIntensidadeLampadasCasa(int idCasa, int intensidade) {
        return new Acao("Definir Intensidade") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.setIntensidade_Luminosidade(intensidade);
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = definirIntensidadeLampadasCasa(idCasa, intensidade);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para definir a cor das lâmpadas de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para ajustar a cor das lâmpadas utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa e a cor desejada como parâmetros, permitindo que a ação seja direcionada à casa correta e configure as lâmpadas com a cor especificada. A implementação do método executar percorre as divisões da casa e ajusta a cor das lâmpadas em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @param cor
     * @return
     */
    public static Acao definirCorLampadasCasa(int idCasa, String cor) {
        return new Acao("Definir Cor") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.setCor_Luz(cor);
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = definirCorLampadasCasa(idCasa, cor);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para ligar as colunas de som de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para ligar as colunas de som utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e liga as colunas de som em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao ligarColunaSomCasa(int idCasa) {
        return new Acao("Ligar Coluna") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof ColunaSom c) c.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = ligarColunaSomCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para desligar as colunas de som de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para desligar as colunas de som utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar percorre as divisões da casa e desliga as colunas de som em cada divisão, respeitando o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao desligarColunaSomCasa(int idCasa) {
        return new Acao("Desligar Coluna") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof ColunaSom c) c.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = desligarColunaSomCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que retorna uma instância de Acao para desligar as luzes e fechar as cortinas de uma casa específica. A ação é implementada como uma classe anônima que estende Acao e define a lógica para realizar ambas as operações utilizando o DomusControl para acessar os dispositivos da casa. O método recebe o ID da casa como parâmetro, permitindo que a ação seja direcionada à casa correta. A implementação do método executar chama as ações de desligar as luzes e fechar as cortinas, aplicando-as às divisões da casa conforme o ID da divisão alvo se for definido.
     * @param idCasa
     * @return
     */
    public static Acao desligarLuzesEFecharCortinas(int idCasa) {
        return new Acao("Desligar e Fechar") {
            public void executar(DomusControl dc) {
                Acao luzes = desligarLuzesCasa(idCasa).clone();
                luzes.setDivisaoAlvo(idDivisaoAlvo);
                luzes.executar(dc);

                Acao cortinas = fecharCortinas(idCasa).clone();
                cortinas.setDivisaoAlvo(idDivisaoAlvo);
                cortinas.executar(dc);
            }
            public Acao clone() {
                Acao a = desligarLuzesEFecharCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }
}