package src.main.model;
import java.io.Serializable;
import java.util.*;
import src.main.Exceptions.DispositivoNaoEncontradoException;

public class Divisao implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private int id;
    //private ArrayList<Dispositivo> dispositivos;
    private HashMap<Integer, Dispositivo> dispositivos;

    // Inner exception classes for backward compatibility with tests
    public static class DivisaoNaoEncontradaException extends src.main.Exceptions.DivisaoNaoEncontradaException {
        public DivisaoNaoEncontradaException() { super(); }
        public DivisaoNaoEncontradaException(String message) { super(message); }
    }

    public static class DispositivoNaoEncontradoException extends src.main.Exceptions.DispositivoNaoEncontradoException {
        public DispositivoNaoEncontradoException() { super(); }
        public DispositivoNaoEncontradoException(String message) { super(message); }
    }

    public Divisao(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.dispositivos = new HashMap<>();
    }
    public Divisao(Divisao d) {
        this.nome = d.nome;
        this.id = d.id;
        this.dispositivos = new HashMap<>();
        for (Dispositivo disp : d.dispositivos.values()) {
            this.dispositivos.put(disp.getId(), disp.clone());//clone
        }
    }
    public Divisao() {
        this.nome = "";
        this.id = 0;
        this.dispositivos = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Dispositivo> getDispositivos() {
        return new HashMap<>(dispositivos);
    }
    public void setDispositivos(HashMap<Integer, Dispositivo> dispositivos) {
        this.dispositivos = new HashMap<>(dispositivos);
    }

    public Dispositivo obterDispositivoPorId(int idDispositivo) {
        Dispositivo d = dispositivos.get(idDispositivo);
        if (d == null) throw new Divisao.DispositivoNaoEncontradoException();
        return d;
    }

    public void adicionarDispositivo(Dispositivo d) {
        this.dispositivos.put(d.getId(), d);
    }
    public void removerDispositivo(Dispositivo d) {
        this.dispositivos.remove(d.getId());
    }

    public void listarDispositivos() {
        for (Dispositivo d : dispositivos.values()) {
            System.out.println(d.getTipo() + " - " + d.getMarca() + " " + d.getModelo() + " , ID -> " + d.getId());//nao esta a aparecer o id(texto),porque
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return id == divisao.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}