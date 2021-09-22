package com.example.demo.pm.kano.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.pm.kano.pojo.Story;
import com.example.demo.pm.kano.pojo.StoryFactory;
import com.example.demo.pm.kano.util.Rate;

public class KanoServiceImpl implements KanoService {
	private ConcurrentHashMap<String, Story> storyMap = new ConcurrentHashMap<>();

	StoryFactory factory = new StoryFactory();

	@Override
	public void add(String storyName, List<Rate> rateList) {
		storyMap.put(storyName, factory.build(storyName, rateList));
	}

	@Override
	public void cleanAll() {
		storyMap.clear();
	}

	@Override
	public List<Story> getKanoRestult() {
		List<Story> resultList = new ArrayList<>();
		Double totalSI = 0d;
		Double totalDSI = 0d;
		int count = 0;

		for (Story story : storyMap.values()) {
			resultList.add(story);
			totalSI += story.getSi();
			totalDSI += story.getDsi();
			count++;
		}

		resultList.add(new Story("base-line", totalSI / count, totalDSI / count));
		return resultList;
	}

}
