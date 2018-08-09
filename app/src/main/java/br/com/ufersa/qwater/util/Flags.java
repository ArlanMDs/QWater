package br.com.ufersa.qwater.util;

public class Flags {

    // usados para distinguir qual activity foi chamada quando um relatório está sendo armazenado no BD
    private final static int REQUEST_CODE_SELECT_DATE = 1, REQUEST_CODE_SELECT_SOURCE = 2;

    // usados para setar a cor do ícone de aviso
    private final static int OK = 1, CAUTION = 2, ALERT = 3, DANGER = 4;

    // usados para diferenciar qual activity chamou a AnalyzeReport
    private final static int INSERT = 1, UPDATE = 2;

}
