package com.ligw.myioc;

import com.ligw.myioc.beans.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Author: Amo
 * @CreateDate: 2021/4/12
 */
@Configuration
@ComponentScan("com.ligw.myioc")
public class MainStart {
	public static void main(String[] args) {
		ApplicationContext context=new AnnotationConfigApplicationContext(MainStart.class);
		UserServiceImpl bean = context.getBean(UserServiceImpl.class);
		bean.sayHi();
	}
}
