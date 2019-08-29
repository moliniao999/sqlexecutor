/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-29 14:11
 **/
public class SqlExecutorException extends Exception {


    public SqlExecutorException(){

        super();
    }

    public SqlExecutorException(String message){
        super(message);

    }

    public SqlExecutorException(String message, Throwable cause){

        super(message,cause);
    }

    public SqlExecutorException(Throwable cause) {

        super(cause);
    }
}
