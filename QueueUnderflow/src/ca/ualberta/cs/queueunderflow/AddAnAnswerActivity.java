package ca.ualberta.cs.queueunderflow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter.AuthorityEntry;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddAnAnswerActivity extends Activity
{
	public static final int HOME_SCREEN_FRAGMENT = 1;
	public static final int FAVORITES_FRAGMENT = 2;
	public static final int READING_LIST_FRAGMENT = 3;
	public static final int MY_QUESTIONS_FRAGMENT = 4;
	AnswerListAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_an_answer);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		final EditText answerText = (EditText) findViewById(R.id.AddAnswerEditText);
		Button addBtn = (Button) findViewById(R.id.AddAnAnswerButton);
		
		Intent intent = getIntent();
		
		/* Get the position of the question and the fragment we clicked from */
		
		final int position = intent.getIntExtra("question_position", -1); 	// -1 is the default value if nothing was retrieved
		final int fromFragment = intent.getIntExtra("fromFragment", -1);
		
		QuestionList questionList=ListHandler.getMasterQList();

		//Get the question list associated with a specific fragment
		switch (fromFragment) {
			case (HOME_SCREEN_FRAGMENT):
				questionList = ListHandler.getMasterQList();
				break;
			case (FAVORITES_FRAGMENT):
				questionList = ListHandler.getFavsList();
				break;
			case (READING_LIST_FRAGMENT):
				 questionList = ListHandler.getReadingList();
				break;
			case (MY_QUESTIONS_FRAGMENT):
				questionList = ListHandler.getMyQsList();
				break;
			}
		
		// Get the question selected
		final Question question = questionList.get(position);
		
		// Set up the display to display question and expandable listview of answers
		TextView questionDisplay = (TextView) findViewById(R.id.questionTextView);
		questionDisplay.setText(question.getQuestion());
		
		TextView authorDisplay = (TextView) findViewById(R.id.authorTextView);
		authorDisplay.setText(questionList.get(0).getAuthor());
		
		TextView dateDisplay = (TextView) findViewById(R.id.dateTextView);
		dateDisplay.setText(DateFormat.format("MMM dd, yyyy", question.getDate()));
		
		final TextView upvoteDisplay = (TextView) findViewById(R.id.upvoteDisplay);
		upvoteDisplay.setText(Integer.toString(question.getUpvotes()));
		
		ImageButton upvoteBtn = (ImageButton) findViewById(R.id.upvoteBtn);
		upvoteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				question.upvoteQuestion();
				upvoteDisplay.setText(Integer.toString(question.getUpvotes()));
			}
		});
		
		if (question.hasPicture() == true) {
			ImageButton hasPictureIcon = (ImageButton) findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(0);
		}
		
        ExpandableListView answersExpListView = (ExpandableListView) findViewById(R.id.answersExpListView);
        adapter = new AnswerListAdapter(this, question.getAnswerList().getAnswerList(), 1, position);
        answersExpListView.setAdapter(adapter);
        
        // This hides that expand arrow on the answer item
        answersExpListView.setGroupIndicator(null);
		
		
		addBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{				
				try {
					
					/* What was done previously
					Answer newAnswer = new Answer(answerText.getText().toString(),User.getUserName());
					QuestionList homeScreenList = ListHandler.getMasterQList();
					Question question= homeScreenList.get(position);
					question.addAnswer(newAnswer);
					homeScreenList.set(position, question);
					QuestionList myQuestionsList = ListHandler.getMyQsList();
					myQuestionsList.set(position,question);
					finish();
					*/
					
					QuestionList myQuestionList= ListHandler.getMasterQList();
					QuestionList homeScreenList = ListHandler.getMasterQList();
					
					switch (fromFragment) {
						case (HOME_SCREEN_FRAGMENT):
							myQuestionList = ListHandler.getMasterQList();
							break;
						case (FAVORITES_FRAGMENT):
							myQuestionList = ListHandler.getFavsList();
							break;
						case (READING_LIST_FRAGMENT):
							 myQuestionList = ListHandler.getReadingList();
							break;
						case (MY_QUESTIONS_FRAGMENT):
							myQuestionList = ListHandler.getMyQsList();
							break;
						}
					
					//Add the answer to the questionlist you came from and the master questionlist
					if (!(myQuestionList.equals(homeScreenList))){
						Answer newAnswer = new Answer(answerText.getText().toString(),User.getUserName());
						Question question2= myQuestionList.get(position);
						question2.addAnswer(newAnswer);
						myQuestionList.set(position,question);
						
						int index= homeScreenList.questionIndex(question2);
						homeScreenList.set(index, question2);
						finish();
					}
					else {
						Answer newAnswer = new Answer(answerText.getText().toString(),User.getUserName());
						Question question2= myQuestionList.get(position);
						question2.addAnswer(newAnswer);
						myQuestionList.set(position,question);
						finish();
					}
					

					
				} catch (IllegalArgumentException e) {
						
						Toast.makeText(getApplicationContext(), "Invalid answer. Please re-enter a answer.", Toast.LENGTH_SHORT).show();
					}
							
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_an_answer, menu);
		return true;
	}

}
