# spring5-base
spring5源码解读  
[Spring Framework 官网](https://spring.io/projects/spring-framework)  
[Spring Framework Github Wiki-English](https://github.com/spring-projects/spring-framework/blob/master/CONTRIBUTING.md)    
[Spring Framework Github 中文文档](https://github.com/DocsHome/spring-docs/blob/master/SUMMARY.md)  
[Spring Framework 源码-v5.2.13.RELEASE](https://github.com/spring-projects/spring-framework/tree/v5.2.13.RELEASE)


###Spring体系架构
####Spring体系架构(基于4.*)
    
####Spring Framework (5.3.5)  

模块|模块组成
---|---
Core| IoC Container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP.
Testing|Mock Objects, TestContext Framework, Spring MVC Test, WebTestClient.
Data Access|Transactions, DAO Support, JDBC, R2DBC, O/R Mapping, XML Marshalling.
Web Servlet|Spring MVC, WebSocket, SockJS, STOMP Messaging.
Web Reactive|Spring WebFlux, WebClient, WebSocket, RSocket.
Integration|Remoting, JMS, JCA, JMX, Email, Tasks, Scheduling, Caching.
Languages|Kotlin, Groovy, Dynamic Languages.


###打开方式
idea import file build.gradle which in ./spring/  
import as a geadle project  
execute script test environment ok  
```shell script
# win
gradlew :spring-oxm:compileTestJava
```
models which start with ligw- is spec Medel  

###SpringIOC
> Inversion of Control(控制反转)  
> Dependency Injection(DI依赖注入)是实现方式

###SpringAOP
> Aspect-oriented Programming(面向切面编程)
> AOP、Aspectj、SpringAop

###Spring声明式事务