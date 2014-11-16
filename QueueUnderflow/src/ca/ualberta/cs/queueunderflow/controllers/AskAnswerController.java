package ca.ualberta.cs.queueunderflow.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.LoadSave;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.NetworkManager;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;

// TODO: Auto-generated Javadoc
/**
 * The Class AskAnswerController.
 * The controller that handles adding questions and answers.
 * @author group 10
 * @version 0.5
 * 
 */
public class AskAnswerController {

	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;
	
	/** The activity. */
	private Activity activity;	// This is so we can make Toast messages
	
	/** The online indicator */
	private boolean isOnline;
	
	/** The image path. */
	private String imagePath; // Not used yet
	
	/** The image encoded in base64. */
	private String encodedImage;
	
	private NetworkManager networkManager = NetworkManager.getInstance();
	
	/**
	 * Instantiates a new ask answer controller.
	 *
	 * @param activity the activity
	 */
	public AskAnswerController(Activity activity) {
		this.activity = activity;
		this.isOnline = networkManager.isOnline(activity.getApplicationContext());
	}

	/**
	 * Ask question.
	 *
	 * @param question the question name
	 * @param username the username
	 * @param hasPicture the has picture
	 */
	public void askQuestion(String questionName, String username, int hasPicture) {
		try {
			Question newQuestion = new Question(questionName, username);
			if (hasPicture == View.VISIBLE) {
				try {
					newQuestion.setImagePath(imagePath);
				} catch (IllegalArgumentException e){
					Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
					return;
				}
				
				newQuestion.setHasPicture(true);
				//New here
				//newQuestion.setImagePath(imagePath);
				newQuestion.setEncodedImage(encodedImage);
			}
			
			//if ( !isOnline ) {
			System.out.println("NETWORK CONNECTION --- " + Boolean.toString(networkManager.isOnline(activity.getApplicationContext())));
			if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
				NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
				networkBuffer.addQuestion(newQuestion);
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Question will automatically be pushed online when connected.", Toast.LENGTH_SHORT).show();
				activity.finish();
				return;
			}
			
			// New
			Thread thread = new AddThread(newQuestion);
			thread.start();
			
			QuestionList homeScreenList = ListHandler.getMasterQList();
			homeScreenList.add(newQuestion);
			
			QuestionList myQuestionsList = ListHandler.getMyQsList();
			myQuestionsList.add(newQuestion);
			
			//Mark as unsaved data.
			LoadSave.unsavedChanges = true;
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid question. Please re-enter a question.", Toast.LENGTH_SHORT).show();
		}
	}

	/**
 * Adds the answer.
 *
 * @param fromFragment the from fragment
 * @param position the position
 * @param answerInput the answer input
 */
public void addAnswer(int fromFragment, int position, String answerName, String username, int hasPicture) {
		try {
			Answer newAnswer = new Answer(answerName, User.getUserName());
			if (hasPicture == View.VISIBLE) {
				//Exception check: check if image >64kb
				try {
					newAnswer.setImagePath(imagePath);
				} catch (IllegalArgumentException e){
					Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				newAnswer.setHasPicture(true);
				//New here
				//newAnswer.setImagePath(imagePath);
				newAnswer.setEncodedImage(encodedImage);
			}
			
			QuestionList questionList = findQuestionList(fromFragment);
			Question question = questionList.get(position);
			
			//if ( !isOnline ) {
			System.out.println("NETWORK CONNECTION --- " + Boolean.toString(networkManager.isOnline(activity.getApplicationContext())));
			if ( !networkManager.isOnline(activity.getApplicationContext()) ) {
				NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();
				networkBuffer.addAnswer(question.getID(), newAnswer);
				Toast.makeText(activity.getApplicationContext(), "Not connected to the network. Answer will automatically be pushed online when connected.", Toast.LENGTH_SHORT).show();
				activity.finish();
				return;
			}
			
			question.addAnswer(newAnswer);
			questionList.set(position, question);
			
			activity.finish();
		} catch (IllegalArgumentException e) {
			Toast.makeText(activity.getApplicationContext(), "Invalid answer. Please re-enter an answer.", Toast.LENGTH_SHORT).show();
		}
	}
	
    /**
     * Find question list.
     *
     * @param fromFragment the from fragment
     * @return the question list
     */
    private QuestionList findQuestionList(int fromFragment) {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList();
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList();
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList();
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList();
		default:
			return ListHandler.getMasterQList();
		}
    }

    /**
     * Sets the image path.
     *
     * @param path the new image path
     */
    public void setImagePath(String path) {
    	this.imagePath = path;
    }
    public void setEncodedImage(String encoded) {
    	this.encodedImage=encoded;
    }
    
    
    
	
	class AddThread extends Thread {

		private Question question;
		
		public AddThread(Question question) {
			this.question = question;
		}
		
		@Override
		public void run() {
			networkManager.addQuestion(question);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}


