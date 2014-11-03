package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.AnswerList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnswerListSerializer implements JsonSerializer<AnswerList> {

	@Override
	public JsonElement serialize(AnswerList answerList, Type arg1,
			JsonSerializationContext serialization_context) {
		JsonArray jsonArray= new JsonArray();
		ArrayList <Answer> serialize= answerList.getAnswerList();
		for (int i=0; i<serialize.size(); i++) {
			Answer answer= serialize.get(i);
			
			final GsonBuilder gsonBuilder2 = new GsonBuilder();
		    gsonBuilder2.registerTypeAdapter(Answer.class, new AnswerSerializer());
		    final Gson gson2 = gsonBuilder2.create();
		    final JsonElement json2=gson2.toJsonTree(answer);
		    jsonArray.add(json2);
		}
		return jsonArray;
	}

}