package src.automacao;
import java.io.Serializable;

import src.controller.*;//DomusControl

public abstract class Acao implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nome;

    public Acao(String nome) {
        this.nome = nome;
    }

    public Acao() {
        this.nome = "";
    }

    public Acao(Acao a) {
        this.nome = a.nome;
    }

    //getter
    public String getNome() {
        return this.nome;
    }

    //setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract void executar(DomusControl dc); // Método abstrato, cada tipo de ação terá sua própria implementação
    public abstract Acao clone(); // Método para criar uma cópia da ação, necessário para cópias profundas em Automacao


    @Override
    public String toString() {
        return "Acao{" +
                "nome='" + nome + '\'' +
                '}';
    }

}
