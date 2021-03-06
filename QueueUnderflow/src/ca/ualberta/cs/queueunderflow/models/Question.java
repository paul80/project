package ca.ualberta.cs.queueunderflow.models;

import android.text.format.DateFormat;
import ca.ualberta.cs.queueunderflow.singletons.User;


/**
 * The Class Question.
 * A model that will hold all of the information pertinent to a question (via inheritance from GenericResponse) and
 * Contains an AnswerList to add & get answers to the question.
 * @author group 10
 * @version 1.0
 */
public class Question extends GenericResponse {

	
	/** The answer list. */
	protected AnswerList answerList;
	
	public boolean hasUserUpvoted()
	{
            return User.alreadyUpvotedQuestion(this.uniqueID);
	}
	
	/**
	 * Instantiates a new question.
	 *
	 * @param questionName the question name
	 * @param author the author
	 */
	public Question(String questionName, String author) {
		super(questionName, author);
		this.answerList = new AnswerList();
	}
	
	/**
	 * Gets the answer list.
	 *
	 * @return the answer list
	 */
	public AnswerList getAnswerList () {
		return this.answerList;
	}
	
	/**
	 * Adds the specified answer.
	 *
	 * @param answer the answer
	 */
	public void addAnswer(Answer answer) {
		answerList.add(answer);
	}
	
	/**
	 * Gets the answer list size.
	 *
	 * @return the answer list size
	 */
	public int getAnswerListSize() {
		return answerList.size();
	}
	
	/**
	 * Sets the answer list.
	 *
	 * @param newAnswerList the new answer list
	 */
	public void setAnswerList(AnswerList newAnswerList) {
		this.answerList=newAnswerList;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		Question response = (Question) o;
		if (response == null) {
			return false;
		}
		if (response.getDate() == null) {
			return false;
		}
		if (this.date == null) {
			return false;
		}
		
		if (!response.getLocation().equals(this.location)) {
			return false;
		}
		
		if (response.getStringID().equals(this.getStringID())) {
			return true;
		}
		
		if (!DateFormat.format("MMM dd, yyyy", response.getDate()).equals(DateFormat.format("MMM dd, yyyy", this.date))) {
			return false;
		}
		else if (response.getUpvotes() != this.upvote || response.hasPicture() != this.hasPicture) {
			return false;
		}
		else if (response.getIsFav() != this.isFav || response.getIsInReadingList() != this.isInReadingList) {
			return false;
		}
		else if (!response.getAnswerList().equals(this.answerList) || !response.getReplies().equals(this.replies)) {
			return false;
		}
		return true;
	}
	
	
	
}