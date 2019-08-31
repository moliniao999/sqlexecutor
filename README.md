# sqlexecutor
 

## 描述
在测试 TiDB 事务时，需要多个客户端乱序执行 SQL。

以两个客户端的情况为例。首先启动两个 TiDB 客户端，客户端分别读取对应的 sql 文件后，以交错顺序执行 SQL 文件中的语句。要求穷举所有执行顺序。
example：
给定 sql1.txt、sql2.txt, 内容如下：

sql1.txt:

```sql
 update X set a=5 where id=1;
 update X set a=6 where id=2;
```


sql2.txt
```sql
 update X set a=8 where id=8
```


启动客户端 client1 读取 sql1.txt、client2 读取 sql2.txt。假设 client1 先执行第一条 sql 语句，client2 执行第一条，client1 再执行第二条。则执行顺序是：

```sql
client1：update X set a=5 where id=1;
client1：update X set a=6 where id=2;
client2：update X set a=8 where id=8;
```

对这个 case，穷举所有可能，意味着执行顺序必须包含以下三种情况：

情况 1：
```sql
client1：update X set a=5 where id=1;
client1：update X set a=6 where id=2;
client2：update X set a=8 where id=8;
```

情况 2：
```sql
client1：update X set a=5 where id=1;
client2：update X set a=8 where id=8;
client1：update X set a=6 where id=2;
```

情况 3：
```sql
client2：update X set a=8 where id=8;
client1：update X set a=5 where id=1;
client1：update X set a=6 where id=2;
```

## 要求
1. 写程序模拟多个客户端实现上述功能
2. 良好的代码设计，可读性，可维护性，可扩展性。
3. 以上可以在单机实现，用 VM 或者 Docker 启动 TiDB 集群不限


## 运行项目
1.启动数据库mysql或tidb,确保sql文件中的表在数据库中存在.
2.采用java语言实现,需要java 1.8 运行环境，启动执行:

 ``` 
  $ java -jar bin/sqlexecutor-1.0-SNAPSHOT.jar 
```


## 实现

采用比较简单的方式实现上述功能，并发启动多个客户端线程依次获取对应sql执行数据库操作，主要利用CPU调度线程的随机性来实现sql执行的随机性。
几个关键参数：
 - threadNum       执行线程数,模拟客户端。
 - loopNum         循环次数，多次循环可覆盖所有执行顺序
 - loopDelayTime   循环间隔时间,单位毫秒。
 - sqlPath         sql文件存放路径,例如/sql目录下可存放多个sql文件。只会加载后缀为txt,sql的文件类型.
组件：
* TestFrameStarter，程序入口，初始化swing窗口，用户录入必要的参数.
* SqlExecutorListener , swing组件基于事件驱动，监听测试执行事件，具体参见actionPerformed()方法
* StandardSqlExecutor， 根据测试计划,启动客户端线程(每个客户端依次对应每个sql文件的内容),利用线程池执行任务，避免过多的创建和销毁，执行完成后返回
* ClientThread ， 客户端线程，执行具体的sql，每个客户端对应一个sql文件
* DBUtils负责管理DB连接, 采用druid数据库连接池
* SqlFileReader，负责读取文件内容
* TestPlan 定义执行计划

目前实现比较简陋，可改进和扩展的地方还很多
