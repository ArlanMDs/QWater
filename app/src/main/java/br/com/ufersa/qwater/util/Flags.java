package br.com.ufersa.qwater.util;

public final class Flags {

    // usados para distinguir qual activity foi chamada quando um relatório está sendo armazenado no BD
    public final static int INSERT_SAVE_REPORT = 1, INSERT_SELECT_SOURCE = 2, UPDATE_SAVE_REPORT = 3, UPDATE_SELECT_SOURCE = 4;

    // usados para setar a cor do ícone de aviso
    public final static int OK = 1, CAUTION = 2, ALERT = 3, DANGER = 4;

    // geralmente usados para nomear as tags de bundles e extras
    public final static String GOING_TO = "going_to", CALLING_ACTIVITY = "callingActivity",  REPORT = "report", SOURCE="source", SOURCE_ID = "sourceID", SOURCE_NAME = "sourceName";

    public final static int UPDATE_REPORT = 1, SEE_REPORT = 2, DELETE_REPORT = 3, DELETE_SOURCE = 4, UPDATE_SOURCE = 5, SEE_UPDATED_SOURCE = 6;

    public final static int MAIN_ACTIVITY = 0, SAVE_REPORT_ACTIVITY = 1;



}
