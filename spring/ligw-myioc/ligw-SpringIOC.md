# Inversion of Control(控制反转)  
设计理念:解决层与层、类与类之间的耦合(不用写很多new Bean())  
IOC容器：管理Bean


###问题  
SpringIOC的加载过程(源码)  
Bean的生命周期(源码)  
循环依赖  
内置后置PostProcessor处理器  
监听器原理-Lister  
什么是beanFactory?BeanFactory与ApplicationContext的区别  
Spring的扩展接口及调用时机  
IOC容器加载过程深度剖析  
Beanfactory与FactoryBean的区别  
BeanDefinition  
Spring源码编译过程  

**加载bean(将Class加载到IOC容器中成为一个Bean)的方式:**   
1、配置xml/@注解/JavaConfig  
2、加载spring容器/上下文  
xml：new  ClassPathXmlApplicationContent（xml）  
@注解：new AnnotationConfigApplicationContext（config.class）  
JavaConfig:  
3、getBean() 

**BeanFactory**  
既可以生产Bean,也可以获取Bean  
Spring顶层核心接口  
使用简单工厂模式，根据传入的一个唯一标识（名字/类型）来依据beanDefination来创建Bean，是传入前创建还是传入后创建要视情况而定。

**BeanDefination（Bean定义）**  
Spring顶层核心接口。  
图纸,封装了生产bean的一切原料。  
xml和注解bean被统一读取为BeanDefinition  
对应容器：BeanDefinitionMapm


**BeanDefinitionRegistry**  
负责注册bean到beanDefinitionMap  

**ApplicationContent**  

**BeandefinitionReader**  
读取配置类、xml

**BeanDefinitionSacnner**  
扫描 

顺序(非严格)：读取配置类-扫描-注册

**BeanFactory与ApplicationContext的区别**  
共同：都有生产Bean的能力。ApplicationContext实现了BeanFatory。都可以作为容器。  
区别：BeanFctory不能扫描包，只能去注册BeanDefination。ApplicationContext包括Beanfactory所有能力。
除了在一些特殊场景，如资源受限的设备上运行的内嵌应用。还有就是beanFactory没有实现一些扩展点。  
BeanFctory只有一个职能：依据BeanDefinition生产bean  

Do|BeanFactory|ApplicationContext
---|---|---
Bean实例化/装配|YES|YES
集成的生命周期管理|NO|YES
自动注册BeanPostProcessor|NO|YES
自动注册BeanFactoryPostProcessor|NO|YES
便利的MessageSource访问(国际化)|NO|YES
内置ApplicationEvent发布机制|NO|YES


**Bean的生命周期**  
1、实例化（beanDefination里封装了bean的class，利用反射（spring控制）/工厂方法（@Bean），空壳，）  
2、注入/填充属性。@autowired  @value（会有循环依赖的问题 用三级缓存解决）  
3、初始化 initMethod、destory（已成型）  
4、初始化后：一堆aways...  
5、put到容器map（beanDefinationMap/单例池/一级缓存）里 key：name，value：bean的实例  
Bean生命周期期间会调用9次BeanPostProcessoer  

**AOP实现原理**  
AOP为了与IOC解藕，所以在bean初始化完成后的BeanPostProcessor实现AOP  
AOP的两种实现方式：CglibAopProxy/JdkDynamicAopProxy  

**TODO aways的使用场景？**  
BeanDefinicationPostprocessor使用场景？  

**ApplicationContext扩展(Spring的扩展点)**  
1、修改beanDefination：实现接口BeanFactoryPostProcessor（Bean工厂的后置处理器），
获取beanDefunation，然后就可以修改了  
2、注册BeanDefination：实现接口BeanDefinationRegistryPostProcessor(Bean定义注册的后置处理器)，
获取BeanDefinationRegistry，然后就可以注册了    

**Spring的扩展点对spring生态很重要**  
BeanPostProcessor也是在ApplicationContext里注册的  
@AutoWired @Value AOP都是基于BeanPostProcessor来执行的  
类注册成BeanDedination是通过BeanFactoryPostProcessor来实现解藕，动态可插拔  

Spring上下文 ：AnnotarionConfigApplicationContext（配置类）  
配置类配置扫包路径，注解。扫描到ioc容器里后就可以用getBean获取实例  

**加载IOC容器常见的两种方法：**
源码-用xml去加载上下文的方式-耦合  
源码-配置类-用注解去加载上下文的方式-BeanFactoryPostProcessor  
两者不同：将不同的上下文注册成BeanDefination的方法不一样  

入口：spring上下文的构造方法: new AnnotationConfigApplicationContext(Class<?>... componentClasses) 
0、调用构造器 this（）方法  
a.  调用子类无参构造函数会先调用父类的构造函数GenericApplicationContext。
父类构造函数实例化了一个BeanFactory：DefaultListableBeanFactory。
它是BeanFactory的最底层实现，实现或者继承了很多BeanFactory功能强大，且实现了BeanDefinationRegistory，具有注册Bean定义的能力）  
b. 自身实例化方法-创建一个读取注解的Bean定义reader:将AnnotatedBeanDefinationReader实例化。
主要实现了注册注解配置的后置处理器（AnnotationConfigUtils.registerAnnotationConfigProcessors(BeanDefinitionRegistry this, null);）  
- 为beanFactory注册实现了Order接口的排序器:AnnotationAwareOrderComparator.INSTANCE  
- 为beanFactory注册Autowire的候选解析器 ContextAnnotationAutowireCandidateResolver  
- 注册了很多的内置的后置处理器(只注册Bean定义)，如：  
> ** internalAutowirteAutoWirteAnnotationProcessor ConfigurationClassPostProcessor.class 解析加了@Configuration的配置类和@ComponentScan、@componentScans注解配置的扫描包,解析@Import注解  
> ** internalAutowiredAnnotationProcessor AutowiredAnnotationBeanPostProcessor.class 解析加了@Autowired、@Value的类  
> (JSR-250 support) internalCommonAnnotationProcessor CommonAnnotationBeanPostProcessor.class 处理JSR规范  
> (JPA support) internalPersistenceAnnotationProcessor PersistenceAnnotationBeanPostProcessor.class  
> internalEventListenerProcessor EventListenerMethodProcessor.class 解析加了@EventListener的方法  
> internalEventListenerFactory DefaultEventListenerFactory.class 注册事件监听器工厂  

c. 自身实例化方法-创建BeanDefination扫描器：将ClsssPathBeanDefinitionScanner实例化。
（但是spring默认的扫描包并不是这个scanner对象，而是自己new的一个classPathBeanDefinitionScanner，spring在执行ConfigurationClassPostProcessor时，去扫描包会new一个新的ClassPathBeanDedinationScanner；这里实例化的scanner仅仅是为了可以手动调用AnnotationConfigApplicationContext的scan方法），doscan方法。
1、注册配置类：register（annotatenClass）
reader读取配置类，注册成BeaDefination到beanDefinationMap里
2、IOC容器刷新 refresh（13个方法）
**方法1：** invokeBeanFactoryPostProcessor：调用ConfigurationCllassPostProcess（继承BeanFctoryPostProcessor）来解析编译配置类
方法2：registerBeanPostProcessors：（@Autowired @Value是依赖于BeanPostProcessor）
方法3：initMessageSource：初始化国际化资源处理器
方法4：initApplicationEventMulticaster：事件多播器
方法5：onRefresh（）
方法6：registerListeners（）把事件监听器注册到多播器上
**方法7：** finishBeanFactoryInitialization（BeanFactory）：实例化剩余的单例Bean
7.1：beanFactory.freezeConfiguration（）冻结所有的bean定义
7.2：preInstantiateSingletons（所有beanName）。
生产标准：不是抽象的、是单例的、不是懒加载的
isFactoryBean：若实现了FactoryBean则要实现getObject，并且spring getBean（beanName）获取到的是getObject返回的bean。getBean（%&beanName）返回的依旧是原来的bean。利用了工厂方法的设计模式，可以实现动态的实例化。
循环遍历所有的beanDefination。（debug调试：条件断点）（@dependsOn）（标记正在创建）（第n次调用BeanPostProcessor）（spring创建bean/实例化：creatBeanInstance：方法：反射-无参构造或有参构造/工厂）（自动装配）（循环依赖陈-三级缓存）（属性赋值：原理-）（invokeInitMethods初始化：三个aware's）（再次调用beanPostProcessor，调用一堆aware's，判断是否实现InitializingBean，是否有 @PostConstruct，是否指定initMethod）

方法8：finishRefresh（）：最后刷新容器，发布刷新事件


问题：
beanFactory与FactoeyBean的区别
介绍BeanFactoryPostprocess在spring中的用途
springIOC的加载过程
Bean的生命周期
Spring中有哪些扩展接口及调用时机

cglab动态代理
配置类动态代理：为了避免重复创建内部引用的bean
完整的配置类 不完整的配置类

doProcessConfigurationClass去解析配置类：（解析@ComonentScan、@import、@importResource、@Bean、@PropertuSource）（会自己new一个 ClassPathBeanDefinationScanner）（doScan）
@lookup


 