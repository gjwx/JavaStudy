package signature.kit;

/**
 */
public class SignatureRuntimeException extends RuntimeException{

    private long code;

    public SignatureRuntimeException(long code, String message){
        super("signature system" + " : " + message);
        this.code = code;
    }

    public SignatureRuntimeException(long code, String message, Throwable cause){
        super("signature system" + " : " + message, cause);
        this.code = code;
    }

    public SignatureRuntimeException(Throwable cause){
        super(cause);
    }

    public long getCode(){
        return code;
    }
}
