package ca.ualberta.cs.queueunderflow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;



public class Question {
	protected String questionName;
	protected AnswerList answerList;
	protected ArrayList <Reply> questionReplies;
	protected String author;
	protected int upvote;
	protected boolean hasPicture;
<<<<<<< Updated upstream
	protected Picture image;
	
=======
	protected Timestamp timestamp;
>>>>>>> Stashed changes
	
	public Question (String questionName, AnswerList answerList, ArrayList<Reply> question_replies, String author, int upvote
			,boolean hasPicture,Picture image) {
		this.questionName=questionName;
		this.answerList= answerList;
		this.questionReplies= new ArrayList<Reply> ();
		this.author=author;
		this.upvote=upvote;
		this.hasPicture=hasPicture;
<<<<<<< Updated upstream
		this.image=image;
	
=======
		this.timestamp = new Timestamp((new Date()).getTime());
>>>>>>> Stashed changes
	}
	
	public void setAuthor(String author) {
		this.author=author;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getQuestion() {
		return this.questionName;
	}
	
	public AnswerList getAnswerList () {
		return this.answerList;
	}
	public void addAnswer(Answer answer) {
		answerList.add(answer);
	}
	
	public int getAnswerListSize() {
		return answerList.size();
	}
	
	public void addQuestionReply(Reply reply) {
		questionReplies.add(reply);
	}
	
	public ArrayList<Reply> getReplies() {
		return this.questionReplies;
		
	}
	
	public int getSizeReplies() {
		return questionReplies.size();
	}
	
	public void upvoteQuestion() {
		upvote+=1;
	}
	
	public int getUpvotes() {
		return upvote;
	}
	public boolean hasPicture() {
		return this.hasPicture;
	}
	
<<<<<<< Updated upstream
	public void setPicture (Picture pic) {
		this.image= pic;
	}
	
	public Picture getPicture() {
		return this.image;
=======
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
>>>>>>> Stashed changes
	}
	
}
