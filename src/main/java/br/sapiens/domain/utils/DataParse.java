package br.sapiens.domain.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DataParse {
    public Date parse(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date parse(java.sql.Date sqlDate){
        return sqlDate;
    }

    public java.sql.Date parse(Date date){
        return new java.sql.Date(date.getTime());
    }
}
