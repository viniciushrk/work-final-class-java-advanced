package br.sapiens.domain.enums;

public enum PeriodosEnum {
    PRIMEIRO("1°") , SEGUNDO("2°"), TERCEIRO("3°"), QUARTO("4°");

    public final String periodo;

    PeriodosEnum(String periodo) {
        this.periodo = periodo;
    }
}
