package ca.ualberta.cs.queueunderflow.serializers;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Date;

import ca.ualberta.cs.queueunderflow.models.AnswerList;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.models.ReplyList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * The Class QuestionDeserializer.
 * @author group 10
 * @version 1.0
 */
public class QuestionDeserializer implements JsonDeserializer<Question> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Question deserialize(JsonElement jsonObject, Type arg1,
			JsonDeserializationContext context) throws JsonParseException {
		
		final JsonObject jsonQuestion= jsonObject.getAsJsonObject();
		
		final JsonElement jsonContent= jsonQuestion.get("questionName");
	    final String question_content = jsonContent.getAsString();
	    
	    final String author= jsonQuestion.get("author").getAsString();
	    final int upvote= jsonQuestion.get("upvote").getAsInt();
	    final boolean hasPicture= jsonQuestion.get("hasPicture").getAsBoolean();
	    final boolean inReadingList= jsonQuestion.get("isInReadingList").getAsBoolean();
	    final boolean isFavorite= jsonQuestion.get("isFav").getAsBoolean();
	    
	    final GsonBuilder gsonBuilder = new GsonBuilder();
	    
	    Gson gson2=gsonBuilder.create();
		   
	    JsonElement date= jsonQuestion.get("date");
	    Date converted= context.deserialize(date, Date.class);

		
	
		
		JsonElement jsonAnswerList= jsonQuestion.get("answerList");
		JsonElement jsonReplyList= jsonQuestion.get("questionReplies");


	    AnswerList answerList= context.deserialize(jsonAnswerList, AnswerList.class);
	    ReplyList replyList= context.deserialize(jsonReplyList,ReplyList.class);
	    String uniqueID= jsonQuestion.get("uniqueID").getAsString();

	    String imagePath= jsonQuestion.get("imagePath").getAsString();
		String encodedImage= jsonQuestion.get("encodedImage").getAsString();
		
		String location=jsonQuestion.get("location").getAsString();
		
		Question deserialized= new Question("Name","Author");
		deserialized.setName(question_content);
		deserialized.setAuthor(author);
		deserialized.setHasPicture(hasPicture);
		deserialized.setReplyList(replyList);
		deserialized.setDate(converted);
		deserialized.setUpvotes(upvote);
		deserialized.setIsFav(isFavorite);
		deserialized.setIsInReadingList(inReadingList);
		deserialized.setAnswerList(answerList);
		deserialized.setStringID(uniqueID);
		deserialized.setLocation(location);
		if (!imagePath.equals("none")) {
			deserialized.setImagePath(imagePath);

		}
		if (!encodedImage.equals("none")) {
			deserialized.setEncodedImage(encodedImage);
		}
		return deserialized;

	}

}
