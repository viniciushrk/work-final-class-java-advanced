package br.sapiens.domain.models;

import br.sapiens.domain.enums.CursosEnum;

import java.util.Date;
import java.util.List;

public class Aluno {
    public int id;
    public String nome;
    public Date dataNascimento;
    public CursosEnum curso;

    public Aluno(String nome, Date dataNascimento, CursosEnum curso) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
//        this.matricula = matricula;
    }

    public Aluno(int id, String nome, Date dataNascimento, CursosEnum curso) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.curso = curso;
//        this.matricula = matricula;
    }

    private List<Matricula> matricula;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public CursosEnum getCursosEnum() {
        return curso;
    }

    public void setCursoEnum(CursosEnum curso) {
        this.curso = curso;
    }

    public java.sql.Date getDataNascimentoSql() {
        return new java.sql.Date(dataNascimento.getTime());
    }

//    public List<Matricula> getMatricula() {
//        return matricula;
//    }
//
//    public void setMatricula(List<Matricula> matricula) {
//        this.matricula = matricula;
//    }
}
