package br.sapiens.domain.models;

import br.sapiens.domain.enums.CursosEnum;

public class Disciplina
{
    public int id;
    public String descricao;
    public CursosEnum curso;

    public Disciplina(int id, String descricao, CursosEnum curso) {
        this.id = id;
        this.descricao = descricao;
        this.curso = curso;
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
}
