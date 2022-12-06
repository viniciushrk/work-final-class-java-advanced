package br.sapiens.domain.dtos;

import br.sapiens.domain.enums.PeriodosEnum;
import br.sapiens.domain.models.Aluno;
import br.sapiens.domain.models.Disciplina;

public class MatriculaDto {
    private int id;
    private int alunoId;

    private int disciplinaId;

    private PeriodosEnum periodo;

    private String nomeAluno;

    private String descricaoDisciplina;

    public MatriculaDto(int id, int alunoId, int disciplinaId, PeriodosEnum periodo, String nomeAluno, String descricaoDisciplina) {
        this.id = id;
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.periodo = periodo;
        this.nomeAluno = nomeAluno;
        this.descricaoDisciplina = descricaoDisciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public PeriodosEnum getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodosEnum periodo) {
        this.periodo = periodo;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getDescricaoDisciplina() {
        return descricaoDisciplina;
    }

    public void setDescricaoDisciplina(String descricaoDisciplina) {
        this.descricaoDisciplina = descricaoDisciplina;
    }
}
