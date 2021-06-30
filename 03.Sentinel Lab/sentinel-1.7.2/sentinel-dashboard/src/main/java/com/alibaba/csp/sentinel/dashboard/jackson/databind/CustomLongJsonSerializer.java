package com.alibaba.csp.sentinel.dashboard.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomLongJsonSerializer extends JsonSerializer<Long> {

	public static final CustomLongJsonSerializer instance = new CustomLongJsonSerializer();

	private static final Long MAX_JS_LONG_VALUE = 9007199254740992l;

	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null)
			return;
		if (value <= MAX_JS_LONG_VALUE) {
			gen.writeNumber(value);
		} else {
			gen.writeString(value.toString());
		}

	}

}
