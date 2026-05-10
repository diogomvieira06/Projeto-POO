package src.main.automacao;
import java.io.Serializable;
import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.DomusControlException;


/**
 * Classe abstrata que representa uma ação a ser executada no sistema de automação residencial. Cada ação tem um nome e pode ser direcionada a uma divisão específica da casa. As ações são implementadas como classes anônimas dentro de métodos estáticos, permitindo a criação de ações pré-definidas para operações comuns, como ligar ou desligar dispositivos, abrir ou fechar cortinas, etc. A classe também implementa o método clone para permitir a criação de cópias das ações com as mesmas propriedades.
 */
public abstract class Acao implements Serializable {
    /**
     * Identificador de versão para a serialização da classe Acao. Este campo é utilizado para garantir a compatibilidade entre diferentes versões da classe durante o processo de serialização e desserialização. O valor é definido como 1L, indicando a primeira versão da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * O nome da ação, que é uma string que descreve a ação a ser executada. Este campo é privado e pode ser acessado ou modificado através dos métodos getNome e setNome. O nome é utilizado para identificar a ação de forma legível, facilitando a compreensão do que a ação faz quando é executada.
     */
    private String nome;

    /**
     * O identificador da divisão alvo para a ação. Este campo é um inteiro que representa o ID da divisão da casa onde a ação deve ser executada. O valor padrão é -1, indicando que a ação não está direcionada a nenhuma divisão específica e deve ser aplicada a todas as divisões da casa. Este campo pode ser modificado através do método setDivisaoAlvo, permitindo que a ação seja direcionada a uma divisão específica quando necessário.
     */
    protected int idDivisaoAlvo = -1;

    /**
     * Construtor da classe Acao que recebe o nome da ação como parâmetro. Este construtor é utilizado para criar uma nova instância de Acao com um nome específico, permitindo que a ação seja identificada de forma clara e legível. O nome é atribuído ao campo privado nome, que pode ser acessado posteriormente através do método getNome.
     * @param nome
     */
    public Acao(String nome) { this.nome = nome; }

    /**
     * Construtor padrão da classe Acao, que inicializa o nome da ação como uma string vazia. Este construtor é utilizado para criar uma nova instância de Acao sem um nome específico, permitindo que o nome seja definido posteriormente através do método setNome. O campo idDivisaoAlvo é inicializado com o valor padrão de -1, indicando que a ação não está direcionada a nenhuma divisão específica.
     */
    public Acao() { this.nome = ""; }

    /**
     * Construtor de cópia da classe Acao, que cria uma nova instância de Acao a partir de outra instância existente. Este construtor é utilizado para criar uma cópia de uma ação existente, mantendo o mesmo nome e o mesmo identificador de divisão alvo. O nome e o idDivisaoAlvo são copiados diretamente da instância fornecida como parâmetro, permitindo que a nova instância seja uma réplica exata da ação original.
     * @param a
     */
    public Acao(Acao a) { this.nome = a.nome; this.idDivisaoAlvo = a.idDivisaoAlvo; }

    /**
     * Método abstrato que deve ser implementado por todas as subclasses de Acao. Este método é responsável por executar a ação no contexto do sistema de automação residencial, utilizando o objeto DomusControl para acessar e manipular os dispositivos e divisões da casa. A implementação deste método deve definir o comportamento específico da ação, como ligar ou desligar dispositivos, abrir ou fechar cortinas, etc., dependendo do tipo de ação que está sendo representada.
     * @param dc
     */
    public abstract void executar(DomusControl dc);

    /**
     * Método abstrato que deve ser implementado por todas as subclasses de Acao para criar uma cópia da ação. Este método é utilizado para permitir a clonagem de ações, criando uma nova instância que é uma réplica exata da ação original, incluindo o nome e o identificador de divisão alvo. A implementação deste método deve retornar uma nova instância de Acao que tenha as mesmas propriedades da ação original, permitindo que a ação seja reutilizada ou modificada sem afetar a instância original.
      * @return Acao
     */
    public abstract Acao clone();

    /**
     * Método getter para o nome da ação. Este método retorna o nome da ação, que é uma string que descreve a ação a ser executada. O nome é utilizado para identificar a ação de forma legível, facilitando a compreensão do que a ação faz quando é executada. O método também possui um setter para permitir a modificação do nome da ação, permitindo que o nome seja atualizado conforme necessário.
     * @return String
     */
    public String getNome() { return nome; }

    /**
     * Método setter para o nome da ação. Este método permite modificar o nome da ação, que é uma string que descreve a ação a ser executada. O nome é utilizado para identificar a ação de forma legível, facilitando a compreensão do que a ação faz quando é executada. O método recebe uma string como parâmetro e atribui esse valor ao campo privado nome, permitindo que o nome da ação seja atualizado conforme necessário.
     * @param nome
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Método setter para o identificador da divisão alvo da ação. Este método permite definir a divisão específica da casa onde a ação deve ser executada, utilizando um inteiro que representa o ID da divisão. O valor padrão é -1, indicando que a ação não está direcionada a nenhuma divisão específica e deve ser aplicada a todas as divisões da casa. Ao chamar este método com um ID de divisão específico, a ação será direcionada apenas para essa divisão, permitindo um controle mais granular sobre onde a ação será executada.
     * @param id
     */
    public void setDivisaoAlvo(int id) { this.idDivisaoAlvo = id; }


    /**
     * Método estático que cria uma ação para fechar as cortinas em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e fechar as cortinas (desligar os dispositivos do tipo Cortina) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para abrir as cortinas em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e abrir as cortinas (ligar os dispositivos do tipo Cortina) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para ligar as luzes em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e ligar as luzes (ligar os dispositivos do tipo Lampada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para desligar as luzes em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e desligar as luzes (desligar os dispositivos do tipo Lampada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para definir a intensidade das luzes em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e definir a intensidade das luzes (ajustar o nível de luminosidade dos dispositivos do tipo Lampada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @param intensidade
     * @return Acao
     */
    public static Acao definirIntensidadeLampadasCasa(int idCasa, int intensidade) {
        return new Acao("Definir Intensidade") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            // Removido: intensidade não existe mais
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
     * Método estático que cria uma ação para definir a cor das luzes em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e definir a cor das luzes (ajustar a cor dos dispositivos do tipo Lampada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @param cor
     * @return Acao
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
     * Método estático que cria uma ação para ligar as colunas de som em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e ligar as colunas de som (ligar os dispositivos do tipo ColunaSom) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para desligar as colunas de som em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e desligar as colunas de som (desligar os dispositivos do tipo ColunaSom) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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
     * Método estático que cria uma ação para ligar as tomadas em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e ligar as tomadas (ligar os dispositivos do tipo Tomada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
     */
    public static Acao ligarTomadasCasa(int idCasa) {
        return new Acao("Ligar Tomadas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Tomada t) t.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = ligarTomadasCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que cria uma ação para desligar as tomadas em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e desligar as tomadas (desligar os dispositivos do tipo Tomada) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
     */
    public static Acao desligarTomadasCasa(int idCasa) {
        return new Acao("Desligar Tomadas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Tomada t) t.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = desligarTomadasCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que cria uma ação para abrir os portões de garagem em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e abrir os portões de garagem (ligar os dispositivos do tipo PortaoGaragem) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
     */
    public static Acao abrirPortoesCasa(int idCasa) {
        return new Acao("Abrir Portões") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof PortaoGaragem p) p.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = abrirPortoesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que cria uma ação para fechar os portões de garagem em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e fechar os portões de garagem (desligar os dispositivos do tipo PortaoGaragem) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
     */
    public static Acao fecharPortoesCasa(int idCasa) {
        return new Acao("Fechar Portões") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof PortaoGaragem p) p.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = fecharPortoesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    /**
     * Método estático que cria uma ação para desligar as luzes e fechar as cortinas em uma casa específica. Este método retorna uma nova instância de Acao que, quando executada, irá iterar sobre todas as divisões da casa e desligar as luzes (desligar os dispositivos do tipo Lampada) e fechar as cortinas (desligar os dispositivos do tipo Cortina) em cada divisão. O método utiliza o objeto DomusControl para acessar a casa e suas divisões, e verifica se a ação deve ser aplicada a todas as divisões ou apenas a uma divisão específica, dependendo do valor do campo idDivisaoAlvo. A ação também implementa o método clone para permitir a criação de cópias da ação com as mesmas propriedades.
     * @param idCasa
     * @return Acao
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