import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-29 14:06
 **/
public class TestPlan {

    private String name;
    private  int threadNum = 2;     //线程数
    private int loopNum = 1;       //循环次数
    private int loopDelayTime = 50; //循环间隔时间,单位毫秒

    private List<List<String>/*sql list*/> sqlCache = new ArrayList<>();//存放sql集合


    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }



    public List<List<String>> getSqlCache() {
        return sqlCache;
    }

    public void setSqlCache(List<List<String>> sqlCache) {
        this.sqlCache = sqlCache;
    }

    public int getLoopDelayTime() {
        return loopDelayTime;
    }

    public void setLoopDelayTime(int loopDelayTime) {
        this.loopDelayTime = loopDelayTime;
    }

    /**
     * 加载sql文件数据
     * @param path
     */
    public void initSqlCache(String path) {
        List<File> files = SqlFileReader.getListFiles(path);
        files.forEach(file -> {
            List<String> sqls = SqlFileReader.readFileIntoList(file.getAbsolutePath());
            sqlCache.add(sqls);
        });
    }
}
