package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.pm.kano.pojo.Story;
import com.example.demo.pm.kano.service.KanoService;
import com.example.demo.pm.kano.service.KanoServiceImpl;
import com.example.demo.pm.kano.util.Rate;
import com.example.demo.pm.kano.util.StoryRateReader;

/**
 * kano输出的测试类
 * 
 * @author leo
 *
 */
public class KanoFileIOOutPut {
	KanoService kanoService = new KanoServiceImpl();
	StoryRateReader storyRateReader = new StoryRateReader();

	@Test
	public void testx() throws FileNotFoundException {
		List<File> files = storyRateReader.getFiles();

		for (File file : files) {
			List<Rate> rateList = storyRateReader.getRateListByFile(file);
			kanoService.add(file.getName().split("\\.")[0], rateList);
		}

		List<Story> storyList = kanoService.getKanoRestult();
		StringBuffer sb = new StringBuffer();
		storyList.forEach(story -> {
			sb.append(story.getName());
			sb.append("\t");
			sb.append(story.getSi());
			sb.append("\t");
			sb.append(story.getDsi());
			sb.append("\n");
		});

		System.out.println(sb.toString());

		kanoService.cleanAll();
	}

}
