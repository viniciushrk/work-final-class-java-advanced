package br.sapiens.domain.models;

import br.sapiens.domain.enums.PeriodosEnum;

import java.util.Optional;

public class Matricula {

    private int id;
    private int alunoId;

    private int disciplinaId;

    private PeriodosEnum periodo;

    private Aluno aluno;
    private Disciplina disciplina;

    public Matricula (int alunoId, int disciplinaId, PeriodosEnum periodo)
    {
        this.alunoId = alunoId;
        this.disciplinaId = disciplinaId;
        this.periodo = periodo;
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

    public void setDisciplinaId(int disciplonaId) {
        this.disciplinaId = disciplonaId;
    }

    public PeriodosEnum getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodosEnum periodo) {
        this.periodo = periodo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
