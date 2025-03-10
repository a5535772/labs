/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.ai.example.audio.tts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisResponse;
import jakarta.annotation.PreDestroy;
import org.apache.commons.io.FileUtils;
import reactor.core.publisher.Flux;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@RestController
@RequestMapping("/ai/tts")
public class TTSController implements ApplicationRunner {

	private final SpeechSynthesisModel speechSynthesisModel;

	private static final String TEXT = "白日依山尽，黄河入海流。";

	private static final String FILE_PATH = "spring-ai-alibaba-audio-example/dashscope-audio/src/main/resources/gen/tts";

	public TTSController(SpeechSynthesisModel speechSynthesisModel) {

		this.speechSynthesisModel = speechSynthesisModel;
	}

	@GetMapping
	public void tts() throws IOException {

		SpeechSynthesisResponse response = speechSynthesisModel.call(
				new SpeechSynthesisPrompt(TEXT)
		);

		File file = new File(FILE_PATH + "/output.mp3");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			ByteBuffer byteBuffer = response.getResult().getOutput().getAudio();
			fos.write(byteBuffer.array());
		}
		catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	@GetMapping("/stream")
	public void streamTTS() {

		Flux<SpeechSynthesisResponse> response = speechSynthesisModel.stream(
				new SpeechSynthesisPrompt(TEXT)
		);

		CountDownLatch latch = new CountDownLatch(1);
		File file = new File(FILE_PATH + "/output-stream.mp3");
		try (FileOutputStream fos = new FileOutputStream(file)) {

			response.doFinally(
					signal -> latch.countDown()
			).subscribe(synthesisResponse -> {
				ByteBuffer byteBuffer = synthesisResponse.getResult().getOutput().getAudio();
				byte[] bytes = new byte[byteBuffer.remaining()];
				byteBuffer.get(bytes);
				try {
					fos.write(bytes);
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

			latch.await();
		}
		catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run(ApplicationArguments args) {

		File file = new File(FILE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	@PreDestroy
	public void destroy() throws IOException {

		FileUtils.deleteDirectory(new File(FILE_PATH));
	}

}
