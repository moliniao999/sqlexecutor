import utils.DBUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-28 14:38
 **/
public class Main {

    private String filePath;
    private static int clientNum = 2;
    private static List<Client> clients = new ArrayList<>();

    private static List<List<String>/*sql list*/> sqlCache = new ArrayList<>();

    private static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) {
        String path = "/Users/weili/IdeaProjects/home/sqlexecutor/sql";
        path = "sql";
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径


        initSqlCache(path);
        initClients();
        new RandomSqlExecutor(clients).execute();
    }

    private static void initSqlCache(String path) {
        List<File> files = SqlFileReader.getListFiles(path);
        files.forEach(file -> {
            System.out.println(file.getAbsolutePath());
            List<String> sqls = SqlFileReader.readFileIntoList(file.getAbsolutePath());
            sqlCache.add(sqls);
        });
    }

    public static void initClients(){
        cyclicBarrier = new CyclicBarrier(clientNum);
        int mod;
        for (int i = 1; i <= clientNum; i++) {
            mod = i % sqlCache.size();
            List<String> sqls = sqlCache.get(mod);
            Client client = new Client("client" + i, DBUtils.openConnection(), sqls, cyclicBarrier);
            clients.add(client);
            System.out.println("client = " + i + ", mod = " + mod + " sqls = " + sqls.toString());
        }

    }


    public static List<List<String>> getSqlCache() {
        return sqlCache;
    }

    public static CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }
}
