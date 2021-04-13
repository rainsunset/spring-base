package com.ligw.myioc.beans;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: Amo
 * @CreateDate: 2021/4/12
 */
@Component
public class Person {
	public void sayHi(){
		System.out.println("Hello Spring！");
	}
}