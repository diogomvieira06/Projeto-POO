package src.main.automacao;

import src.main.controller.DomusControl;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cenario implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private int id;
	private String nome;
	private int idCasa;
	private List<Acao> acoes;

	public Cenario() {
		this.id = 0;
		this.nome = "";
		this.idCasa = 0;
		this.acoes = new ArrayList<>();
	}

	public Cenario(int id, String nome, int idCasa, List<Acao> acoes) {
		this.id = id;
		this.nome = nome;
		this.idCasa = idCasa;
		this.acoes = new ArrayList<>();
		if (acoes != null) {
			for (Acao acao : acoes) {
				if (acao != null)
					this.acoes.add(acao.clone());
			}
		}
	}

	public Cenario(Cenario c) {
		this(c.id, c.nome, c.idCasa, c.acoes);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdCasa() {
		return idCasa;
	}

	public void setIdCasa(int idCasa) {
		this.idCasa = idCasa;
	}

	public List<Acao> getAcoes() {
		return Collections.unmodifiableList(acoes);
	}

	public void setAcoes(List<Acao> acoes) {
		this.acoes = new ArrayList<>();
		if (acoes != null) {
			for (Acao acao : acoes) {
				if (acao != null)
					this.acoes.add(acao.clone());
			}
		}
	}

	public void adicionarAcao(Acao acao) {
		if (acao != null)
			this.acoes.add(acao.clone());
	}

	public void executar(DomusControl dc) {
		for (Acao acao : acoes) {
			if (acao != null)
				acao.executar(dc);
		}
	}

	/**
	 * Executa todas as ações do cenário, mas restringe o efeito a uma divisão
	 * específica.
	 */
	public void executarNaDivisao(DomusControl dc, int idDivisao) {
		for (Acao acao : acoes) {
			if (acao != null) {
				Acao clonada = acao.clone(); // Clona para não alterar o cenário original permanentemente
				clonada.setDivisaoAlvo(idDivisao);
				clonada.executar(dc);
			}
		}
	}

	public static Cenario sairDeCasa(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesCasa(idCasa),
				Acao.fecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Sair de casa", idCasa, acoes);
	}

	public static Cenario jantarComAmigos(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Jantar com amigos", idCasa, acoes);
	}

	public static Cenario jantarRomantico(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 35),
				Acao.definirCorLampadasCasa(idCasa, "Amarelo Quente"),
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Jantar Romantico", idCasa, acoes);
	}

	public static Cenario cinema(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 10),
				Acao.definirCorLampadasCasa(idCasa, "Azul"),
				Acao.desligarLuzesCasa(idCasa),
				Acao.fecharCortinas(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Cinema", idCasa, acoes);
	}

	public static Cenario estudar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.definirIntensidadeLampadasCasa(idCasa, 100),
				Acao.definirCorLampadasCasa(idCasa, "Branco Frio"),
				Acao.ligarLuzesCasa(idCasa),
				Acao.abrirCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Estudar", idCasa, acoes);
	}

	public static Cenario deitar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.desligarLuzesEFecharCortinas(idCasa),
				Acao.desligarColunaSomCasa(idCasa));
		return new Cenario(id, "Deitar", idCasa, acoes);
	}

	public static Cenario acordar(int id, int idCasa) {
		List<Acao> acoes = List.of(
				Acao.abrirCortinas(idCasa),
				Acao.ligarLuzesCasa(idCasa),
				Acao.ligarColunaSomCasa(idCasa));
		return new Cenario(id, "Acordar", idCasa, acoes);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cenario{")
				.append("id=").append(id)
				.append(", nome='").append(nome).append('\'')
				.append(", idCasa=").append(idCasa)
				.append(", acoes=");

		if (acoes.isEmpty()) {
			sb.append("[]");
		} else {
			sb.append('[');
			for (int i = 0; i < acoes.size(); i++) {
				sb.append(acoes.get(i).getNome());
				if (i < acoes.size() - 1)
					sb.append(" -> ");
			}
			sb.append(']');
		}

		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cenario cenario = (Cenario) o;
		return id == cenario.id;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
}
