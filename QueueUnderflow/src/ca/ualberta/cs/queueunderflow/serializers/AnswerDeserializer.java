package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Reply;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class AnswerDeserializer implements JsonDeserializer<Answer> {

	@Override
	public Answer deserialize(JsonElement jsonAnswer, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		
		final JsonObject answer= jsonAnswer.getAsJsonObject();
	    final JsonElement jsonContent = answer.get("answerName");
	    final String content = jsonContent.getAsString();
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    final Gson gson = gsonBuilder.create();
	    
	    final String author= answer.get("author").getAsString();
	    final int upvote= answer.get("upvote").getAsInt();
	    final boolean hasPicture= answer.get("hasPicture").getAsBoolean();
	    
	    final int picture= answer.get("picture").getAsInt();
	    Picture new_picture=new Picture(picture);
	    
		Type listType = new TypeToken<ArrayList<Reply>>() {}.getType();
	    ArrayList<Reply> replyList= new Gson().fromJson(answer.get("answerReplies"), listType);
	    Gson gson2=gsonBuilder.create();
	   
	    String date= answer.get("date").getAsString();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm a");
		Date converted= new Date();
		try {
			converted = formatter.parse(date);
			//System.out.println(date);
			//System.out.println(formatter.format(date));
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}

	    Answer deserialized_answer= new Answer(content, author);
	    deserialized_answer.setReplyArray(replyList);
	    deserialized_answer.setUpvotes(upvote);
	    deserialized_answer.setPicture(new_picture);
	    deserialized_answer.setDate(converted);
	    deserialized_answer.sethasPicture(hasPicture);
	    return deserialized_answer;
	}

}
