package br.univali.portugol.relator.erros.inspetores;

/**
 * 
 * @author Luiz Fernando Noschang
 * 
 */
public final class InspetorSO
{
    private static String informacoesSO = null;

    public static String getInformacoesSO()
    {
        if (informacoesSO == null)
        {
            informacoesSO = obterInformacoesSO();
        }

        return informacoesSO;
    }

    private static String obterInformacoesSO()
    {
        try
        {
            StringBuilder construtorTexto = new StringBuilder();

            adicionarPropriedade("os.name", construtorTexto);
            adicionarPropriedade("os.arch", construtorTexto);
            adicionarPropriedade("os.version", construtorTexto);
            adicionarPropriedade("file.separator", construtorTexto);
            adicionarPropriedade("path.separator", construtorTexto);
            adicionarPropriedade("line.separator", construtorTexto);
            adicionarPropriedade("user.name", construtorTexto);
            adicionarPropriedade("user.home", construtorTexto);
            adicionarPropriedade("user.dir", construtorTexto);

            return construtorTexto.toString();
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
        return null;
    }

    private static void adicionarPropriedade(String propriedade, StringBuilder construtorTexto)
    {
        try
        {
            String valor = System.getProperty(propriedade);

            if (valor != null)
            {
                construtorTexto.append(propriedade);
                construtorTexto.append(": ");
                construtorTexto.append(valor);
                construtorTexto.append("\n\n");
            }
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
    }
}
