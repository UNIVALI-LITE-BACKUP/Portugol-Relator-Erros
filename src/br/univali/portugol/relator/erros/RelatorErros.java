package br.univali.portugol.relator.erros;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import br.univali.portugol.relator.erros.inspetores.InspetorJVM;
import br.univali.portugol.relator.erros.inspetores.InspetorMacAddress;
import br.univali.portugol.relator.erros.inspetores.InspetorSO;

/**
 * 
 * @author Luiz Fernando Noschang
 * 
 */
public final class RelatorErros
{
    private static final SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Properties propriedades;

    public void inicializar(String aplicativo, String versao)
    {
        try
        {
            propriedades = new Properties();
            propriedades.load(getClass().getResourceAsStream("/portugol-relator-erros.properties"));

            propriedades.put("erro.aplicativo", aplicativo);
            propriedades.put("erro.versao", versao);
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
    }

    public void relatarErro(Exception erro, String algoritmo)
    {
        try
        {
            enviarDados(obterDados(erro, algoritmo));
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
    }

    private String obterDados(Exception erro, String algoritmo) throws UnsupportedEncodingException
    {
        StringBuilder construtorTexto = new StringBuilder();

        construtorTexto.append("foo=foo");
        construtorTexto.append(criarDado("autenticacao.usuario", propriedades.getProperty("autenticacao.usuario")));
        construtorTexto.append(criarDado("autenticacao.senha", propriedades.getProperty("autenticacao.senha")));
        construtorTexto.append(criarDado("erro.aplicativo", propriedades.getProperty("erro.aplicativo")));
        construtorTexto.append(criarDado("erro.versao", propriedades.getProperty("erro.versao")));
        construtorTexto.append(criarDado("erro.mensagem", erro.getMessage()));
        construtorTexto.append(criarDado("erro.classe", erro.getClass().getCanonicalName()));
        construtorTexto.append(criarDado("erro.stacktrace", obterStackTrace(erro)));
        construtorTexto.append(criarDado("erro.algoritmo", algoritmo));
        construtorTexto.append(criarDado("erro.data", formatadorData.format(Calendar.getInstance().getTime())));
        construtorTexto.append(criarDado("erro.informacoesSO", InspetorSO.getInformacoesSO()));
        construtorTexto.append(criarDado("erro.informacoesJVM", InspetorJVM.getInformacoesJVM()));
        construtorTexto.append(criarDado("erro.macAddress", InspetorMacAddress.getMacAddress()));

        return construtorTexto.toString();
    }

    private String criarDado(String chave, String valor) throws UnsupportedEncodingException
    {
        StringBuilder construtorTexto = new StringBuilder();

        if (valor == null)
        {
            valor = "null";
        }

        construtorTexto.append("&");
        construtorTexto.append(URLEncoder.encode(chave, "ISO-8859-1"));
        construtorTexto.append("=");
        construtorTexto.append(URLEncoder.encode(valor, "ISO-8859-1"));

        return construtorTexto.toString();
    }

    private void enviarDados(String dados)
    {
        HttpURLConnection conexaoHttp = null;
        DataOutputStream writer = null;

        try
        {
            conexaoHttp = (HttpURLConnection) new URL(propriedades.getProperty("relator.url")).openConnection();
            conexaoHttp.setDoOutput(true);
            conexaoHttp.setDoInput(true);
            conexaoHttp.setInstanceFollowRedirects(false);
            conexaoHttp.setRequestMethod("POST");
            conexaoHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conexaoHttp.setRequestProperty("charset", "ISO-8859-1");
            conexaoHttp.setRequestProperty("Content-Length", Integer.toString(dados.getBytes().length));
            conexaoHttp.setUseCaches(false);

            writer = new DataOutputStream(conexaoHttp.getOutputStream());
            writer.writeBytes(dados);
            writer.flush();
            writer.close();

            System.out.println(conexaoHttp.getResponseCode());
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
        finally
        {
        }
    }

    private String obterStackTrace(Exception erro)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        erro.printStackTrace(pw);

        return sw.toString();
    }
}