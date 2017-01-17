## 快速入门

- 配置hbase-site.xml.（若brew安装, 则在/usr/local/Cellar/hbase/1.1.2/libexec/conf/hbase-site.xml）

~~~
<configuration>
    <property>
        <name>hbase.rootdir</name>
        <value>hbase数据目录</value>
    </property>
    <property>
        <name>hbase.zookeeper.property.dataDir</name>
        <value>ZK数据目录</value>
    </property>
    <property>
        <name>hbase.zookeeper.quorum</name>
        <value>localhost</value>  <-- 若有多个值时, 形如: 173.16.250.10:2181,173.16.250.13:2181,173.16.250.14:2181（注意,之间没有空格!!）
    </property>
</configuration>
~~~

- 启动:
  - start-hbase.sh
- HBase Shell
  - 进入: habse shell
  - 表的CRUD
    - list, 列出所有表
    - drop '表名', 删除表. <-- 删除前, 先disable
    - enable/disable '表名', 使能表
    - create '表名', 'CF名', 'CF名' ..., 创建表 <-- 创建时指定CF
  - 数据的CRUD
    - put '表名', 'row-key', 'CF名:属性', '属性值', 插入数据 <-- 一次一个
    - scan '表名', 浏览
    - get '表名', 'row-key', 获取某行数据
    - delete '表名', 'row-key', 'CF名:属性', 删除记录

## 程序例子

这个例子包含AsynchHBaseClient和HBase两个例子, 但由于所引用的Guava版本的问题, 这两个依赖无法同时打开.

故在运行时, 请将相应的另一个依赖给注释掉并把另一个文件改名以避免编译问题.

运行命令:

~~~
gradle run.
~~~
