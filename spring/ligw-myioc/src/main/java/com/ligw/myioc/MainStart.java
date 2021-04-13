package com.ligw.myioc;

import com.ligw.myioc.beans.Person;
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
@ComponentScan(basePackages = {"com.ligw.myioc"})
public class MainStart {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(MainStart.class);
		Person person1 = context.getBean(Person.class);
		person1.sayHi();

		Person person2 = (Person) context.getBean("person");
		person2.sayHi();

		System.out.println(person1 == person2);

	}
}
