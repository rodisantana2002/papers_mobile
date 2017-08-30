package com.rhcloud.papers.helpers.generic;

/**
 * Created by Rodolfo on 24/07/2017
 */

public class hlpConstants {
    public static final String TYPE_REST_PUT = "PUT";
    public static final String TYPE_REST_POST = "POST";
    public static final String TYPE_REST_DELETE = "DELETE";
    public static final String TYPE_REST_GET = "GET";

    public static final String URL_BASE = "http://papers-santana2002.rhcloud.com/services/";
    public static final String URL_PESSOA = "autor/";
    public static final String URL_AUTENTICATION = "autentication/";
    public static final String URL_USUARIO = "usuario/";
    public static final String URL_DOCUMENTO = "documento/";
    public static final String URL_DESTINO = "destino/";
    public static final String URL_TIPO_DOCUMENTO = "tipodocumento/";
    public static final String URL_DOCUMENTOS_PESSOAS = "docspessoas/";
    public static final String URL_FAVORITOS = "favoritos/";
    public static final String URL_HISTORICO = "historico/";
    public static final String URL_FILA_SUBMISSAO = "submissoes/";

    public static final String MSG_400 = "A requisição que foi enviada estava malformatada ou incorreta e por isso sua requisição falhou, recomendamentos checar o JSON que está sendo enviado";
    public static final String MSG_404 = "A transação ou recurso desejado não existe em nossos registros";
    public static final String MSG_401 = "Usuário ou Senha incorretos.";
    public static final String MSG_500 = "Ocorreu um erro interno no sistema do gateway, entre em contato com o suporte do sistema";
    public static final String MSG_503 = "O sistema esta inativo, aguarde alguns minutos e tente novamente acessar o sistema";
    public static final String MSG_IOE = "O sistema esta sem acesso a Internet no momento";
    public static final String MSG_ERRO_JSON = "Erro ao ler JSON";
    public static final String MSG_WAIT = "Processando Solicitação...";

    public static final String MSG_NENHUM_USUARIO = "Nenhum Usuário registrado";
    public static final String MSG_NENHUM_DOCUMENTO = "Nenhum Documento registrado";
    public static final String MSG_NENHUM_TIPO_DOCUMENTO = "Nenhum Tipo de Documento registrado";
    public static final String MSG_NENHUM_DESTINO = "Nenhum Destino registrado";
    public static final String MSG_NENHUM_FAVORITO = "Nenhum Favorito registrado";
    public static final String MSG_NENHUM_HISTORICO = "Nenhum Histórico registrado";
    public static final String MSG_NENHUM_SUBMISSAO = "Nenhuma Submissão registrada";

    public static final String MYPREFERENCES = "MyPrefs";
    public static final String PREF_EMAIL = "emailKey";
    public static final String PREF_SENHA = "senhaKey";
    public static final String PREF_TOKEN = "tokenKey";
    public static final String PREF_ID = "idKey";
    public static final String PREF_PRIMEIRO_NAME = "primeiroNameKey";
    public static final String PREF_SEGUNDO_NAME = "segundoNameKey";
    public static final String PREF_FOTO= "fotoKey";
    public static final String PREF_ULTACESSO= "ultAcessoKey";


    public static final String MSG_CONFIRMA_OPERACAO = "Confirma a Operação?";

    public static final String BTN_OK = "OK";
    public static final String BTN_CONFIRMA = "Confirma";
    public static final String BTN_CANCELA = "Cancela";
}
