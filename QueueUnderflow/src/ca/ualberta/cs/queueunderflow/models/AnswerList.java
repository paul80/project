package ca.ualberta.cs.queueunderflow.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/*
 Sets the Answerlist so we can have answers to questions.
 */

/**
 * The Class AnswerList.
 */
public class AnswerList {

/** The answer list. */
protected ArrayList <Answer> answerList;
    
    // sets a new arraylist
	/**
     * Instantiates a new answer list.
     */
    public AnswerList () {
		answerList= new ArrayList<Answer>();
	}
	
	/**
	 * Adds the answer to the beginning of the list
	 *
	 * @param answer the answer
	 */
	public void add (Answer answer) {
		// Add at position 0, so that freshest A is always first
		answerList.add(0, answer);	
	}
	
	//returns the size of answerlist. So you can keep track of number of answers
	/**
	 * Size.
	 *
	 * @return the number of answers in the list
	 */
	public int size() {
		return answerList.size();
	}
	
	//grabs the answer
	/**
	 * Gets the answer at the specified location in the list
	 *
	 * @param i the index of the answer to return
	 * @return the answer at the specified index
	 */
	public Answer getAnswer(int i) {
		return answerList.get(i);
	}
	
	// returns the arraylist of answers
	/**
	 * Gets the answer list.
	 *
	 * @return the answer list
	 */
	public ArrayList <Answer> getAnswerList() {
		return answerList;
	}

	/**
	 * Sets the answer list.
	 *
	 * @param newAnswers the new answer list
	 */
	public void setAnswerList(ArrayList<Answer> newAnswers) {
		this.answerList=newAnswers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		AnswerList aList = (AnswerList) o;
		
		if (aList.size() != answerList.size()) {
			return false;
		}
		
		for (int i = 0; i < answerList.size(); i++) {
			if (!aList.getAnswer(i).equals(answerList.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Sort by.
	 *
	 * @param method the method
	 */
	public void sortBy(String method) {
		
		// Sort by date
		Comparator<Answer> leastRecentComparator = new Comparator<Answer>() {

			@Override
			public int compare(Answer lhs, Answer rhs) {
				return (lhs.getDate().compareTo(rhs.getDate()));
			
			}
		};
		
		Comparator<Answer> mostRecentComparator = new Comparator<Answer>() {
			@Override
			public int compare(Answer lhs, Answer rhs) {
				return (lhs.getDate().compareTo(rhs.getDate())) * -1;	
				
				}
		};
		
		// Sort by upvote
		Comparator <Answer> mostUpvotesComparator = new Comparator<Answer>() {
			@Override
			public int compare(Answer lhs, Answer rhs) {
				return (lhs.getUpvotes() - rhs.getUpvotes())*-1;
			}
		};
		
		if (method.equals("most recent")) {
			Collections.sort(answerList, mostRecentComparator);
		}
		else if (method.equals("least recent")) {
			Collections.sort(answerList, leastRecentComparator);
		}
		else if (method == "most upvotes") {
			Collections.sort(answerList, mostUpvotesComparator);
		}
	}
	
	
}
