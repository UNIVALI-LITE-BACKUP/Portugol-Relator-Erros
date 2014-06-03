package br.univali.portugol.relator.erros.inspetores;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 
 * @author Luiz Fernando Noschang
 *
 */
public final class InspetorMacAddress
{
    private static String macAddress = null;

    public static String getMacAddress()
    {
        if (macAddress == null)
        {
            macAddress = obterMacAddress();
        }

        return macAddress;
    }

    private static String obterMacAddress()
    {
        try
        {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface interfaceRede = NetworkInterface.getByInetAddress(ip);

            if (interfaceRede != null)
            {
                byte[] mac = interfaceRede.getHardwareAddress();

                if (mac != null)
                {
                    StringBuilder construtorTexto = new StringBuilder();

                    for (int i = 0; i < mac.length; i++)
                    {
                        construtorTexto.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }

                    return construtorTexto.toString();
                }
            }
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }

        return null;
    }
}
