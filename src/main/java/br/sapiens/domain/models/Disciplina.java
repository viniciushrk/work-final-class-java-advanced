package br.sapiens.domain.models;

import br.sapiens.domain.enums.CursosEnum;
import br.sapiens.domain.enums.PeriodosEnum;

public class Disciplina
{
    public int id;
    public String descricao;

    public CursosEnum curso;
    public PeriodosEnum periodos;

    public Disciplina(String descricao, CursosEnum curso, PeriodosEnum periodos) {
        this.descricao = descricao;
        this.periodos = periodos;
        this.curso = curso;
    }

    public Disciplina(int id, String descricao, CursosEnum curso, PeriodosEnum periodos) {
        this.id = id;
        this.descricao = descricao;
        this.curso = curso;
        this.periodos = periodos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CursosEnum getCurso() {
        return curso;
    }

    public void setCurso(CursosEnum curso) {
        this.curso = curso;
    }

    public PeriodosEnum getPeriodos() {
        return periodos;
    }

    public void setPeriodos(PeriodosEnum periodos) {
        this.periodos = periodos;
    }

}
