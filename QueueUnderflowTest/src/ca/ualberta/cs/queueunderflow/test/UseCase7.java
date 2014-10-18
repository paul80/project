package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Answer;
import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;

public class UseCase7 extends TestCase {
	
	//Use CASE 7(incorporates user story 7)
	public void testAddPhotoFromCamera() {
		User me= new User();
		me.setUserName("Me");
		
		String questionName= "How does this work?";
		Question questionTest= new Question(questionName,me.getUserName());
		Picture pic= new Picture(10);
		questionTest.setPicture(pic);
		
		assertTrue("Question has a picture", questionTest.getPicture()!=null);
		
		String answerName= "Never mind I got it";
		Answer testAnswer= new Answer(answerName,me.getUserName());
		
		testAnswer.setPicture(pic);
		assertTrue("Answer has a picture", testAnswer.getPicture()!=null);
		
		//Exception: Photo is larger than 64 kb
		Picture picture2= new Picture(65);
		assertFalse("image is too large, select another photo", picture2.getSize()<64);
	}
}
