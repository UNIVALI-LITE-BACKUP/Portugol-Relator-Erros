package br.univali.portugol.relator.erros.inspetores;

/**
 * 
 * @author Luiz Fernando Noschang
 * 
 */
public final class InspetorJVM
{
    private static String informacoesJVM = null;

    public static String getInformacoesJVM()
    {
        if (informacoesJVM == null)
        {
            informacoesJVM = obterInformacoesJVM();
        }

        return informacoesJVM;
    }

    private static String obterInformacoesJVM()
    {
        try
        {
            StringBuilder construtorTexto = new StringBuilder();

            adicionarPropriedade("java.version", construtorTexto);
            adicionarPropriedade("java.vendor", construtorTexto);
            adicionarPropriedade("java.vendor.url", construtorTexto);
            adicionarPropriedade("java.home", construtorTexto);
            adicionarPropriedade("java.vm.specification.version", construtorTexto);
            adicionarPropriedade("java.vm.specification.vendor", construtorTexto);
            adicionarPropriedade("java.vm.specification.name", construtorTexto);
            adicionarPropriedade("java.vm.version", construtorTexto);
            adicionarPropriedade("java.vm.vendor", construtorTexto);
            adicionarPropriedade("java.vm.name", construtorTexto);
            adicionarPropriedade("java.specification.version", construtorTexto);
            adicionarPropriedade("java.specification.vendor", construtorTexto);
            adicionarPropriedade("java.specification.name", construtorTexto);
            adicionarPropriedade("java.class.version", construtorTexto);
            adicionarPropriedade("java.class.path", construtorTexto);
            adicionarPropriedade("java.library.path", construtorTexto);
            adicionarPropriedade("java.io.tmpdir", construtorTexto);
            adicionarPropriedade("java.compiler", construtorTexto);
            adicionarPropriedade("java.ext.dirs", construtorTexto);

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

            construtorTexto.append(propriedade);
            construtorTexto.append(": ");
            construtorTexto.append(valor);
            construtorTexto.append("\n\n");
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
    }
}
