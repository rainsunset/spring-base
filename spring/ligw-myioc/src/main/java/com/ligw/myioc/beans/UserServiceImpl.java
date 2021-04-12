package com.ligw.myioc.beans;

import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: Amo
 * @CreateDate: 2021/4/12
 */
@Service
public class UserServiceImpl {
	public void sayHi(){
		System.out.println("Hello Spring！");
	}
}