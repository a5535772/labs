package com.example.demo.pm.kano.pojo;

import java.util.List;

import com.example.demo.pm.kano.util.Rate;

public class StoryFactory {
	public Story build(String storyName,List<Rate> rateList) {
		double countOfA = 0;
		double countOfO = 0;
		double countOfM = 0;
		double countOfI = 0;

		for (Rate rate : rateList) {
			switch (rate) {
			case A:
				countOfA++;
				break;
			case O:
				countOfO++;
				break;
			case M:
				countOfM++;
				break;
			case I:
				countOfI++;
				break;
			default:
				break;
			}
		}

		Story story = new Story(storyName,getSI(countOfA, countOfO, countOfM, countOfI),
				getDSI(countOfA, countOfO, countOfM, countOfI));
		return story;
	}

	private double getSI(double a, double o, double m, double i) {
		return (a + o) / (a + o + m + i);
	}

	private double getDSI(double a, double o, double m, double i) {
		return (m + o) / (a + o + m + i);
	}

}
