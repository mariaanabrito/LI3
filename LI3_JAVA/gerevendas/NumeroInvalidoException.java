/**
 * Classe que representa a exceção enviada quando o número dado é inválido.
 */
public class NumeroInvalidoException extends Exception
{
    /**
     * Construtor por parâmetro.
     */
    public NumeroInvalidoException (String msg)
    {
        super(msg);
    }
}