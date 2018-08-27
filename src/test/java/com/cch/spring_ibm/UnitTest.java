package com.cch.spring_ibm;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
@Transactional(transactionManager = "transactionManager", readOnly = true)
public class UnitTest {
	@BeforeClass
	public static void befClass() {
		System.out.println("@BeforeClass");
	}
	@Before
	public void bef() {
		System.out.println("@Before");
	}
	@After
	public void aft() {
		System.out.println("@After");
		System.out.println("");
	}
	@AfterClass
	public static void afterClass() {
		System.out.println("@AfterClass");
	}
	
	@Test
	public void test() {
		System.out.println("test()");
	}

}
