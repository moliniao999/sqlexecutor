import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 13:31
 **/
public class RandomSqlExecutor implements SqlExecutor{


    private List<Client> clients;

    private ExecutorService executorService;




    public RandomSqlExecutor(List<Client> clients) {
        this.clients = clients;
        this.executorService = Executors.newFixedThreadPool(clients.size());
    }

    public void execute() {

        for (Client client : clients) {
            executorService.execute(client);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CyclicBarrier重用");


        for (Client client : clients) {
            executorService.execute(client);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CyclicBarrier重用");


        for (Client client : clients) {
            executorService.execute(client);
        }


        executorService.shutdown();
    }
}
