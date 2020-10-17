package org.github.labcabrera.hodei.notifications.service.telegram;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnProperty("app.notifications.telegram.token")
@Slf4j
public class TelegramClient implements Consumer<String> {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${app.notifications.telegram.chat-id}")
	private String chatId;

	@Value("${app.notifications.telegram.url}")
	private String url;

	@Override
	public void accept(String message) {
		try {
			log.debug("Sending notification {} ({})", message, chatId);
			String json = String.format("{\"chat_id\":\"%s\",\"text\":\"%s\"}", chatId, message);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> request = new HttpEntity<>(json, headers);
			ResponseEntity<String> responseJson = restTemplate.postForEntity(url, request, String.class);
			Assert.isTrue(HttpStatus.OK.equals(responseJson.getStatusCode()), "Invalid status code: " + responseJson.getStatusCode());
		}
		catch (Exception ex) {
			log.error("Telegram comunnication failure: {}", ex);
		}
	}
}
