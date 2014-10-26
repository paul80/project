package ca.ualberta.cs.queueunderflow.test;

import ca.ualberta.cs.queueunderflow.Picture;
import ca.ualberta.cs.queueunderflow.Question;
import ca.ualberta.cs.queueunderflow.QuestionList;
import ca.ualberta.cs.queueunderflow.User;
import junit.framework.TestCase;


public class UseCase4 extends TestCase
{
	//Use CASE 4 (incorporates user stories 4 and 7)
	public void testAskQuestion() {
		
		User me= new User();
		me.setUserName("Me");
		
		//Add a question to the list
		QuestionList questionList= new QuestionList();
		String questionName= "How does this work?";
		Question questionTest= new Question(questionName,me.getUserName());
		Picture pic= new Picture(32);
		questionTest.setPicture(pic);
		
		questionList.add(questionTest);
		assertTrue("Question List isn't empty", questionList.size()==1);
		
		//Exception: Where the question entered is whitespaces only
		String questionName2= "        ";
		int flag = 0; // indicates if the whitespace exception is caught & handled
		try {
			Question anotherQuestion = new Question(questionName2, me.getUserName());
			questionList.add(anotherQuestion);
		} catch (IllegalArgumentException e) {
			flag = 1;
		}
		assertTrue("Whitespace username exception caught & handled", flag == 1);
		assertTrue("Whitespace only question is not added to the questionList", questionList.size() == 1);
		
		/*Exception: Where there is no online connectivity
		 * 
		 By default, there is no network connectivity (questionList connectivity is initialized to be 0)
		 because this is a mock test, not the actual implementation.
		 */
		assertFalse("No network connectivity, push online later.",questionList.pushOnline());
		
		
	}
}
