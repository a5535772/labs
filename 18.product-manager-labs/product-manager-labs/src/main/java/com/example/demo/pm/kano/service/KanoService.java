package com.example.demo.pm.kano.service;

import java.util.List;

import com.example.demo.pm.kano.pojo.Story;
import com.example.demo.pm.kano.util.Rate;

public interface KanoService {

	void add(String storyName, List<Rate> rateList);

	void cleanAll();

	List<Story> getKanoRestult();
}
