# Hadoop Mapreduce
本项目演示最基本的Hadoop Mapreduce demo。项目使用的hadoop版本为官方当前最新版本`2.7.3`，理论上应该能在2.0以上版本运行，未来更高版本不保证一定能运行，请参考新版本迁移文档更新代码。使用的开发环境为`Ubuntu 16.04 LTS`，理论上应该能在所有的Linux发行版运行，运行环境为java 8。

## 下载hadoop
从官方下载站点进行[下载](http://www.apache.org/dyn/closer.cgi/hadoop/common/)。

Hadoop有以下三种运行模式:
- 单节点模式: 此为官方版本hadoop压缩包默认的模式。即并不运行任何HDFS与YARN服务，仅作为普通java一次性任务程序运行。仅供开发环境使用。
- 伪分布式: 此模式为`CDH`版本软件包安装时的默认模式。即将YARN服务与HDFS服务完全运行于同一个节点。此模式下已经满足其他绝大多数hadoop组件(如HBase, Spark等)的运行模式。由于既不具备高可用，也不具备高性能，顾不建议产品环境使用。
- 真分布式: 此模式下，HDFS各个实例以及YARN的各个实例运行于不同的节点上，共同组成hadoop集群。稳定性和性能大大提高，但有一定的运维成本。产品环境推荐使用。

基本的Hadoop Mapreduce任务在单节点模式即可运行。下载hadoop压缩包后任意位置解压即可。

## 编译代码并运行demo程序
在项目目录下使用以下命令编译代码为jar包:

    ./gradlew jar

快速运行demo效果:
```bash
mkdir input
cp build.gradle input
/path/to/hadoop_install_path/bin/hadoop jar WordCount build/libs/hadoop.jar input output
/path/to/hadoop_install_path/bin/hadoop jar LineCount build/libs/hadoop.jar input output2
```

最后在`output`和`output2`目录下看运行结果。

