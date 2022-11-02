package br.sapiens.domain.models;

import br.sapiens.domain.enums.PeriodosEnum;

public class Matricula {

    public int alunoId;
    public int disciplonaId;

    public Disciplina Disciplina;
    public Aluno curso;
    public PeriodosEnum periodos;
}
