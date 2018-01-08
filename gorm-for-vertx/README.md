# gorm-for-vertx-eample

vertx中使用[GORM](http://gorm.grails.org/latest/hibernate/manual/index.html)的参考范例。

demo运行:

    ./gradlew run

运行测试:

    ./gradlew test

## 关键点说明

整个项目代码参考了官方文档中[Using GORM for Hibernate Outside Grails](http://gorm.grails.org/latest/hibernate/manual/index.html#outsideGrails)部分的内容，以及[gorm-outside-grails](https://github.com/grails-samples/gorm-outside-grails)项目源码。不过官方文档在这一部分表述的内容非常少，这里做下简单的总结。

### Domain Class声明的时候需要实现`org.grails.datastore.gorm.GormEntity<D>`接口，并且加上`@grails.gorm.annotation.Entity`注解

这个在官方文档的Demo就有参考，很容易看出来:

```groovy
@Entity
class Person implements GormEntity<Person> {
    String firstName
    String lastName
    static constraints = {
        firstName blank:false
        lastName blank:false
    }
}
```

### Domain和Service只能使用Groovy编写

由于`org.grails.datastore.gorm.GormEntity<D>`这个接口使用了`trait`关键字，无法在java代码中编译，如果将Service使用java代码编写，会看到类似于下面这样的编译错误

```text
domain/Person.java:27: 错误: 找不到符号
@org.codehaus.groovy.transform.trait.Traits.Implemented() public  D removeFrom(java.lang.String param0, java.lang.Object param1) { return null;}
                                                                  ^
  符号:   类 D
  位置: 类 Person

```

并且不能对Domain和Service使用`@CompileStatic`注解

### 调用`HibernateDatastore`

这里的参考内容就更少，官方文档只是提到了需要在程序启动逻辑中调用`HibernateDatastore`，但没有具体的范例怎么去调用。

在StackOverFlow论坛中，找到了一种办法，就是在Service中使用`@grails.gorm.transactions.Transactional`注解，这样就会自动生成调用`HibernateDatastore`的代码，才能使用Domain Class，否则将看到如下的错误：

```text
org.hibernate.HibernateException: No Session found for current thread
        at org.grails.orm.hibernate.GrailsSessionContext.currentSession(GrailsSessionContext.java:117)
        at org.hibernate.internal.SessionFactoryImpl.getCurrentSession(SessionFactoryImpl.java:714)
        at org.grails.orm.hibernate.cfg.GrailsHibernateUtil.setObjectToReadWrite(GrailsHibernateUtil.java:328)
        at org.grails.orm.hibernate.HibernateGormInstanceApi.setObjectToReadWrite(HibernateGormInstanceApi.groovy:154)
        at org.grails.orm.hibernate.AbstractHibernateGormInstanceApi.save(AbstractHibernateGormInstanceApi.groovy:137)
        at org.grails.datastore.gorm.GormEntity$Trait$Helper.save(GormEntity.groovy:151)
        at org.grails.datastore.gorm.GormEntity$Trait$Helper$save.call(Unknown Source)
        at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:133)
        at domain.Person.save(Person.groovy)
        at domain.Person.save(Person.groovy)
        at org.grails.datastore.gorm.GormEntity$save.call(Unknown Source)
        at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:125)
        at com.shifudao.example.Gorm4Vertx.start(Gorm4Vertx.groovy:14)
        at io.vertx.core.AbstractVerticle.start(AbstractVerticle.java:111)
        at io.vertx.core.impl.DeploymentManager.lambda$doDeploy$10(DeploymentManager.java:481)
        at io.vertx.core.impl.ContextImpl.lambda$wrapTask$2(ContextImpl.java:344)
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163)
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:403)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463)
        at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858)
        at java.lang.Thread.run(Thread.java:748)
```

于是代码看起来像这样:

```groovy
package com.shifudao.example

import domain.Person
import grails.gorm.transactions.Transactional
import io.vertx.core.AbstractVerticle
import org.grails.orm.hibernate.HibernateDatastore

@Transactional
class Gorm4Vertx extends AbstractVerticle {
    private static final HibernateDatastore datastore = new HibernateDatastore(Person)

    @Override
    void start() {
        Person person = new Person(firstName: "Feng", lastName: "Yu")
        person.save(flush: true)
        println(Person.getAll())
        vertx.close()
    }
}
```

### GORM配置

官方文档提供了几种方案：

- Spring Boot应用中，直接配置在`application.yml`
- 非Spring Boot应用中，Pass一个Map或`PropertyResolver`接口的实例给`org.grails.orm.hibernate.HibernateDatastore`

Pass一个Map配置参考如下:

```groovy
import org.grails.orm.hibernate.HibernateDatastore
Map configuration = [
    'hibernate.hbm2ddl.auto':'create-drop',
    'dataSource.url':'jdbc:h2:mem:myDB'
]
HibernateDatastore datastore = new HibernateDatastore( configuration, Person)
```

> 更完整的GORM配置信息参考官方文档: http://gorm.grails.org/latest/hibernate/manual/index.html#configuration