package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.pm.kano.pojo.Story;
import com.example.demo.pm.kano.pojo.StoryFactory;
import com.example.demo.pm.kano.util.Rate;

public class StoryTest {

	@Test
	public void testx() {
		List<Rate> rateList = new ArrayList<>();
		rateList.add(Rate.A);
		rateList.add(Rate.I);
		rateList.add(Rate.M);
		rateList.add(Rate.O);
		rateList.add(Rate.Q);
		rateList.add(Rate.R);
		rateList.add(Rate.A);
		rateList.add(Rate.A);
		rateList.add(Rate.I);
		rateList.add(Rate.A);
		rateList.add(Rate.O);
		rateList.add(Rate.A);
		rateList.add(Rate.M);
		rateList.add(Rate.A);
		Story story = new StoryFactory().build("testStory",rateList);

		System.out.println(story);
	}
}
