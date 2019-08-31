package com.lm; /**
 * @program: sqlexecutor
 * @description:
 * @author: weili
 * @create: 2019-08-31 09:09
 **/

import com.lm.listener.SqlExecutorListener;
import com.lm.util.DBUtils;

import javax.swing.*;

public class TestFrameStarter extends JFrame {


    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        //启动
        new TestFrameStarter().startUI();
    }

    public void startUI() {
        ExecutorFrame executorFrame = new ExecutorFrame().invoke();
        JTextField driver = executorFrame.getDriver();
        JTextField url = executorFrame.getUrl();
        JTextField username = executorFrame.getUsername();
        JTextField password = executorFrame.getPassword();
        JTextField threadNum = executorFrame.getThreadNum();
        JTextField loopNum = executorFrame.getLoopNum();
        JTextField loopDelayTime = executorFrame.getLoopDelayTime();
        JTextField sqlpath = executorFrame.getSqlpath();
        JButton submitBtn = executorFrame.getSubmitBtn();
        JButton closeBtn = executorFrame.getCloseBtn();
        JTextArea output = executorFrame.getOutput();

        //添加sql执行监听事件,监听提交按钮
        SqlExecutorListener sqlExecutorListener = new SqlExecutorListener(driver, url, username, password, threadNum, loopNum, loopDelayTime, sqlpath, output);
        submitBtn.addActionListener(sqlExecutorListener);
        //关闭
        closeBtn.addActionListener(e -> System.exit(0));
    }

    private class ExecutorFrame {
        private JTextField driver;
        private JTextField url;
        private JTextField username;
        private JTextField password;
        private JTextField threadNum;
        private JTextField loopNum;
        private JTextField loopDelayTime;
        private JTextField sqlpath;
        private JButton submitBtn;
        private JButton closeBtn;
        private JTextArea output;

        public JTextField getDriver() {
            return driver;
        }

        public JTextField getUrl() {
            return url;
        }

        public JTextField getUsername() {
            return username;
        }

        public JTextField getPassword() {
            return password;
        }

        public JTextField getThreadNum() {
            return threadNum;
        }

        public JTextField getLoopNum() {
            return loopNum;
        }

        public JTextField getLoopDelayTime() {
            return loopDelayTime;
        }

        public JTextField getSqlpath() {
            return sqlpath;
        }

        public JButton getSubmitBtn() {
            return submitBtn;
        }

        public JButton getCloseBtn() {
            return closeBtn;
        }

        public JTextArea getOutput() {
            return output;
        }

        public ExecutorFrame invoke() {
            setLocation(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel contPanel = new JPanel();
            //设置顶部提示文字和主窗体的宽，高，x值，y值
            setTitle("sql乱序执行框架");
            setBounds(400, 200, 800, 600);
            contPanel.setLayout(null);

            JLabel jdbc = new JLabel("JDBC Connection Configuration：");
            jdbc.setBounds(20, 40, 400, 20);

            //driver
            JLabel labdriver = new JLabel("driver：");
            labdriver.setBounds(20, 80, 200, 20);
            driver = new JTextField();
            driver.setBounds(150, 80, 300, 20);
            driver.setText(DBUtils.DEFAULT_DRIVER);

            //url
            JLabel laburl = new JLabel("url：");
            laburl.setBounds(20, 100, 200, 20);
            url = new JTextField();
            url.setBounds(150, 100, 300, 20);
            url.setText(DBUtils.DEFAULT_URL);
            //username
            JLabel labusername = new JLabel("username：");
            labusername.setBounds(20, 120, 200, 20);
            username = new JTextField();
            username.setBounds(150, 120, 300, 20);
            username.setText(DBUtils.DEFAULT_USERNAME);
            //password
            JLabel labpassword = new JLabel("password：");
            labpassword.setBounds(20, 140, 200, 20);
            password = new JTextField();
            password.setBounds(150, 140, 300, 20);
            password.setText(DBUtils.DEFAULT_PASSWORD);

            JLabel testplan = new JLabel("test plan：");
            testplan.setBounds(20, 180, 400, 20);

            //threadNum
            JLabel labthreadNum = new JLabel("threadNum：");
            labthreadNum.setBounds(20, 220, 200, 20);
            threadNum = new JTextField();
            threadNum.setBounds(150, 220, 300, 20);
            threadNum.setText("2");
            //loopNum
            JLabel labloopNum = new JLabel("loopNum：");
            labloopNum.setBounds(20, 240, 200, 20);
            loopNum = new JTextField();
            loopNum.setBounds(150, 240, 300, 20);
            loopNum.setText("5");
            //loopDelayTime
            JLabel labloopDelayTime = new JLabel("loopDelayTime：");
            labloopDelayTime.setBounds(20, 260, 200, 20);
            loopDelayTime = new JTextField();
            loopDelayTime.setBounds(150, 260, 300, 20);
            loopDelayTime.setText("50");
            //sql path
            JLabel labsqlpath = new JLabel("sqlpath：");
            labsqlpath.setBounds(20, 280, 200, 20);
            sqlpath = new JTextField();
            sqlpath.setBounds(150, 280, 300, 20);

            //将组件添加到容器cp中
            contPanel.add(jdbc);
            contPanel.add(labdriver);
            contPanel.add(driver);
            contPanel.add(laburl);
            contPanel.add(url);
            contPanel.add(labusername);
            contPanel.add(username);
            contPanel.add(labpassword);
            contPanel.add(password);

            contPanel.add(testplan);
            contPanel.add(labthreadNum);
            contPanel.add(threadNum);
            contPanel.add(labloopNum);
            contPanel.add(loopNum);
            contPanel.add(labloopDelayTime);
            contPanel.add(loopDelayTime);
            contPanel.add(labsqlpath);
            contPanel.add(sqlpath);

            //执行按钮
            submitBtn = new JButton("执行");
            submitBtn.setBounds(80, 320, 60, 18);
            contPanel.add(submitBtn);


            //关闭按钮
            closeBtn = new JButton();
            closeBtn.setText("关闭");
            closeBtn.setBounds(200, 320, 60, 18);
            contPanel.add(closeBtn);

            //添加输出区域
            output = new JTextArea();
            JScrollPane scroll = new JScrollPane(output);
            scroll.setBounds(20, 350, 600, 200);
            contPanel.add(scroll);

            //分别设置水平和垂直滚动条自动出现 
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            //分别设置水平和垂直滚动条总是出现 
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            //分别设置水平和垂直滚动条总是隐藏
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            setContentPane(contPanel);
            setVisible(true);
            return this;
        }
    }
}
