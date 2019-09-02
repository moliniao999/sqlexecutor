package com.lm.listener;

import com.lm.config.Context;
import com.lm.config.SqlTestPlan;
import com.lm.exception.SqlExecutorException;
import com.lm.executor.SqlExecutor;
import com.lm.executor.StandardSqlExecutor;
import com.lm.util.DBUtils;
import com.lm.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-31 11:20
 **/
public class SqlExecutorListener implements ActionListener {

    private static Logger log = LoggerFactory.getLogger(SqlExecutorListener.class);

    //sql执行器
    private SqlExecutor executor;

    private final JTextField username;
    private final JTextField password;
    private final JTextField threadNum;
    private final JTextField loopNum;
    private final JTextField loopDelayTime;
    private final JTextField sqlPath;
    private final JTextField url;
    private final JTextField driver;
    private JTextArea output;


    public SqlExecutorListener(JTextField driver, JTextField url, JTextField username, JTextField password, JTextField threadNum, JTextField loopNum, JTextField loopDelayTime, JTextField sqlPath, JTextArea output) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.threadNum = threadNum;
        this.loopNum = loopNum;
        this.loopDelayTime = loopDelayTime;
        this.sqlPath = sqlPath;
        this.output = output;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            output.setText("");
            //参数校验
            if (!checkParams()) {
                return;
            }
            int tn = Integer.parseInt(threadNum.getText().trim());
            int ldt = Integer.parseInt(loopDelayTime.getText().trim());
            int ln = Integer.parseInt(loopNum.getText().trim());

            //初始化数据源
            DBUtils.config(driver.getText().trim(), url.getText().trim(), username.getText().trim(), password.getText().trim());
            //设置执行计划
            Context<SqlTestPlan> context = new Context<>(setTestPlan(tn, ln, ldt, sqlPath.getText().trim()));
            //执行任务
            executor = new StandardSqlExecutor();
            executor.configure(context);
            Result result = executor.execute();
            //返回执行结果
            setOutPut(result);

        } catch (SqlExecutorException e1) {
            output.setText(e1.toString());
            return;
        } catch (NumberFormatException ne) {
            output.setText("输入不合法");
            return;
        } catch (Exception e2) {
            log.error("服务器错误", e2);
            output.setText("服务器错误," + e2.getMessage());
            return;
        }

    }

    private void setOutPut(Result result) {
        Map<String/*thread name*/, String/*result*/> data = (Map<String, String>) result.getData();
        StringBuffer sb = new StringBuffer();
        if (data != null && !data.isEmpty()) {
            data.forEach((k,v) ->{
                sb.append(k + ":" + v+"\n");
            });
        }
        output.setText("执行结束:"+"\n"+sb.toString());
    }

    private SqlTestPlan setTestPlan(int tn, int ln, int ldt, String path) {
        SqlTestPlan sqlTestPlan = new SqlTestPlan();
        if (tn > 0) {
            sqlTestPlan.setThreadNum(tn);
        }
        if (ln > 0) {
            sqlTestPlan.setLoopNum(ln);
        }
        if (ldt > 0) {
            sqlTestPlan.setLoopDelayTime(ldt);
        }
        if (path.length() != 0) {
            sqlTestPlan.setPath(path);
        }
        return sqlTestPlan;
    }

    private boolean checkParams() {
        if (driver.getText().trim().length() == 0) {
            output.setText("driver不能为空");
            return false;
        }
        if (url.getText().trim().length() == 0) {
            output.setText("url不能为空");
            return false;
        }
        if (username.getText().trim().length() == 0) {
            output.setText("username不能为空");
            return false;
        }
        if (sqlPath.getText().trim().length() == 0) {
            output.setText("请指定sql文件存放路径");
            return false;
        }
        return true;
    }


}
