package com.example.demo.pm.kano.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoryRateReader {
	public List<File> getFiles() {
		String filePath = this.getClass().getClassLoader().getResource("storys").getPath();
		File[] files = new File(filePath).listFiles();
		List<File> fileList = new ArrayList<>();

		for (File file : files) {
			if (file.isFile()) {
				fileList.add(file);
			}
		}

		return fileList;
	}

	public List<Rate> getRateListByFile(File file) throws FileNotFoundException {
		if (!file.isFile()) {
			throw new RuntimeException("file should not be a directory");
		}

		List<Rate> result = new ArrayList<>();

		Scanner scanner = new Scanner(file);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			Rate rate = Rate.valueOf(line);
			if (rate != null) {
				result.add(rate);
			}
		}
		scanner.close();
		return result;
	}

}
