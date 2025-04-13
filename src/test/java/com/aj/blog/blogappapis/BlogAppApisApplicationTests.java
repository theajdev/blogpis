package com.aj.blog.blogappapis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aj.AJLogger;
import com.aj.blog.blogappapis.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserRepo userRepo;

	@Test
	public void repoTest() {
		String className = this.userRepo.getClass().getName();
		String packageName = this.userRepo.getClass().getPackageName();
		AJLogger.log("BlogApp", "className: " + className);
		AJLogger.log("BlogApp", "packageName: " + packageName);
	}

}
