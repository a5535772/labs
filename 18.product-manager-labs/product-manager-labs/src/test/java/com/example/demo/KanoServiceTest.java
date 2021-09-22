package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.pm.kano.service.KanoService;
import com.example.demo.pm.kano.service.KanoServiceImpl;
import com.example.demo.pm.kano.util.Rate;

public class KanoServiceTest {
	KanoService kanoService = new KanoServiceImpl();

	@Test
	public void testx() {
		List<Rate> storyRateList1 = getRateList1();
		List<Rate> storyRateList2 = getRateList2();
		List<Rate> storyRateList3 = getRateList3();

		kanoService.add("story1", storyRateList1);
		kanoService.add("story2", storyRateList2);
		kanoService.add("story3", storyRateList3);
		
		System.out.println(kanoService.getKanoRestult());
		
		kanoService.cleanAll();
	}

	private List<Rate> getRateList1() {
		List<Rate> storyRateList1 = new ArrayList<>();
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.Q);
		storyRateList1.add(Rate.R);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.M);
		storyRateList1.add(Rate.A);
		return storyRateList1;
	}

	private List<Rate> getRateList2() {
		List<Rate> storyRateList1 = new ArrayList<>();
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.M);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.M);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.R);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.A);
		return storyRateList1;
	}

	private List<Rate> getRateList3() {
		List<Rate> storyRateList1 = new ArrayList<>();
		storyRateList1.add(Rate.R);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.M);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.R);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.A);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.I);
		storyRateList1.add(Rate.O);
		storyRateList1.add(Rate.A);
		return storyRateList1;
	}
}
