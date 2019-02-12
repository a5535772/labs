package com.leo.labs.qps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.leo.labs.qps.controller.HelloController;

@RunWith(MockitoJUnitRunner.class)
public class QpsJunitTest {

	@InjectMocks
	HelloController helloController;
	

	@Test
	public void will_get_1_succ_when_not_sleep() throws Exception {
		List<String> list = new ArrayList<>();
		
		list.add(helloController.helloLimited());
		list.add(helloController.helloLimited());
		list.add(helloController.helloLimited());
		
		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Fail);
		assertThat(list.get(2)).isEqualTo(HelloController.Fail);
	}
	
	@Test
	public void will_get_2_succ_when_sleep_1_second_after_first_succ() throws Exception {
		List<String> list = new ArrayList<>();
		
		list.add(helloController.helloLimited());
		Thread.sleep(1000l);
		list.add(helloController.helloLimited());
		list.add(helloController.helloLimited());
		
		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Succ);
		assertThat(list.get(2)).isEqualTo(HelloController.Fail);
	}	
	
	@Test
	public void will_get_3_succ_when_sleep_2_second_after_first_succ() throws Exception {
		List<String> list = new ArrayList<>();
		
		list.add(helloController.helloLimited());
		Thread.sleep(2000l);
		list.add(helloController.helloLimited());
		list.add(helloController.helloLimited());
		
		assertThat(list.get(0)).isEqualTo(HelloController.Succ);
		assertThat(list.get(1)).isEqualTo(HelloController.Succ);
		assertThat(list.get(2)).isEqualTo(HelloController.Succ);
	}	
}