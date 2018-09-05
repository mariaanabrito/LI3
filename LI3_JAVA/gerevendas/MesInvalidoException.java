/**
 * Classe que representa a exceção enviada quando o mês dado é inválido.
 */
public class MesInvalidoException extends Exception
{
    /**
     * Construtor por parâmetro.
     */
    public MesInvalidoException(String msg)
    {
        super(msg);
    }
}