package br.com.ufersa.qwater.util;

public final class Flags {

    // usados para distinguir qual activity foi chamada quando um relatório está sendo armazenado no BD
    public final static int INSERT_SELECT_DATE = 1, INSERT_SELECT_SOURCE = 2, UPDATE_SELECT_DATE = 3, UPDATE_SELECT_SOURCE = 4;

    // usados para setar a cor do ícone de aviso
    public final static int OK = 1, CAUTION = 2, ALERT = 3, DANGER = 4;

    // geralmente usados para nomear as tags de bundles e extras
    public final static String GOING_TO = "going_to";
    public final static String CALLING_ACTIVITY = "callingActivity";
    public final static int UPDATE = 1;
    public final static String REPORT = "report";

    public final static int MAIN_ACTIVITY = 0, ANALYZE_REPORT_ACTIVITY = 1;



}
