package ca.ualberta.cs.queueunderflow.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.queueunderflow.NetworkBuffer;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.controllers.NetworkController;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.models.QuestionList;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.NetworkManager;
import ca.ualberta.cs.queueunderflow.singletons.User;
import ca.ualberta.cs.queueunderflow.views.WriteReplyDialogFragment;
 
/**
 * The Class AnswerListAdapter.
 * Connects a list of answers to the QAView. 
 * Handles favoriting & adding to the reading list - Not yet implemented. 
 * Handles calling the WriteReplyDialogFragment to be displayed
 * @author group 10
 * @version 1.0
 */
public class AnswerListAdapter extends BaseExpandableListAdapter {
 
	/** The Constant HOME_SCREEN_FRAGMENT. */
	public static final int HOME_SCREEN_FRAGMENT = 1;
	
	/** The Constant FAVORITES_FRAGMENT. */
	public static final int FAVORITES_FRAGMENT = 2;
	
	/** The Constant MY_QUESTIONS_FRAGMENT. */
	public static final int MY_QUESTIONS_FRAGMENT = 3;
	
	/** The Constant READING_LIST_FRAGMENT. */
	public static final int READING_LIST_FRAGMENT = 4;
	
	/** The type answer. */
	private static int TYPE_ANSWER = 1;
	
    /** The activity. */
    private Activity activity;
    
    /** The group layout id. */
    private int groupLayoutID;
    
    /** The child layout id. */
    private int childLayoutID;
    
    /** The answer array. */
    private ArrayList<Answer> answerArray;
    
    /** The from fragment. */
    private int fromFragment;
    
    /** The question position. */
    private int questionPosition;
    
    /**
     * Instantiates a new answer list adapter.
     *
     * @param activity the activity
     * @param answerArray the answer array
     * @param fromFragment the from fragment
     * @param questionPosition the question position
     */
    public AnswerListAdapter(Activity activity, ArrayList<Answer> answerArray, int fromFragment, int questionPosition) {
        super();
        this.activity = activity;
        this.groupLayoutID = R.layout.list_item_answer;
        this.childLayoutID = R.layout.exp_list_item_reply;
        this.answerArray = answerArray;
        this.fromFragment = fromFragment;
        this.questionPosition = questionPosition;
    }
 
    //grabs the reply from the child of the group.
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Reply getChild(int groupPosition, int childPosition) {
        return answerArray.get(groupPosition).getReplyAt(childPosition);
    }
 
    //grabs ChildID. Still needs to be done.
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(childLayoutID, parent, false);
        
         
        TextView replyDisplay = (TextView) view.findViewById(R.id.replyTextView);
        replyDisplay.setText(answerArray.get(groupPosition).getReplyAt(childPosition).getReply());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(answerArray.get(groupPosition).getReplyAt(childPosition).getAuthor());
        
        TextView locationDisplay = (TextView) view.findViewById(R.id.locationTextView);
        TextView nearbyDisplay= (TextView) view.findViewById(R.id.nearbyTextView);
        nearbyDisplay.setTextColor(Color.BLUE);
        
		//If the user decides to use location services, then display location info else do not
        String location= answerArray.get(groupPosition).getReplyAt(childPosition).getLocation();
        String nearby= User.getCity()+", " +User.getCountry();
        if (User.getUseLocation()) {
        	locationDisplay.setText(answerArray.get(groupPosition).getReplyAt(childPosition).getLocation());
        	if (location.equals(nearby)) {
        		nearbyDisplay.setText("Nearby");
        	}
        	else {
        		nearbyDisplay.setText("");
        	}
        }
        else {
        	locationDisplay.setText("");
        	nearbyDisplay.setText("");
        }
        
        
        
        return view;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return answerArray.get(groupPosition).getSizeReplies();
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Answer getGroup(int groupPosition) {
        return answerArray.get(groupPosition);
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        return answerArray.size();
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view;
        if (answerArray.get(groupPosition).hasPicture()) {
            view = inflater.inflate(R.layout.list_item_answer_picture, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imagePreview);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            view = inflater.inflate(groupLayoutID, parent, false);
        }
         
        TextView answerDisplay = (TextView) view.findViewById(R.id.answerTextView);
        answerDisplay.setText(answerArray.get(groupPosition).getName());
         
        TextView authorDisplay = (TextView) view.findViewById(R.id.authorTextView);
        authorDisplay.setText(answerArray.get(groupPosition).getAuthor());
         
        TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        dateDisplay.setText(DateFormat.format("MMM dd, yyyy", answerArray.get(groupPosition).getDate()));
         
		TextView locationDisplay = (TextView) view.findViewById(R.id.locationTextView);
		TextView nearbyDisplay= (TextView) view.findViewById(R.id.nearbyTextView);
		nearbyDisplay.setTextColor(Color.BLUE);
		String location= answerArray.get(groupPosition).getLocation();
		String nearby= User.getCity()+", "+User.getCountry();

		//If the user decides to use location services, then display location info else do not
		if (User.getUseLocation()) {
			locationDisplay.setText(answerArray.get(groupPosition).getLocation());
			if (location.equals(nearby)) {
				nearbyDisplay.setText("Nearby");
			}
			else {
				nearbyDisplay.setText("");
			}	
		}
		else {
			locationDisplay.setText("");
			nearbyDisplay.setText("");
			
		}
        TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
        upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
        
        ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
        upvoteBtn.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
            	//User user= ListHandler.getUser();
            	Answer answer= answerArray.get(groupPosition);
    			if (User.alreadyUpvotedAnswer(answer.getID())) {
					Toast.makeText(activity.getApplicationContext(), "Answer was already upvoted", Toast.LENGTH_SHORT).show();
				}
				else {
					User.addUpvotedAnswer(answerArray.get(groupPosition).getID());
					
					// To mimic
					answerArray.get(groupPosition).upvoteResponse();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
					
					QuestionList questionList = findQuestionList();
					Question question = questionList.get(questionPosition);
					
					NetworkManager networkManager = NetworkManager.getInstance();
					if (!networkManager.isOnline(activity.getApplicationContext()) ) {
						NetworkBuffer networkBuffer = networkManager.getNetworkBuffer();			
						networkBuffer.addAUpvote(question.getStringID(), answerArray.get(groupPosition).getStringID());
						return;
					}
					
					NetworkController  networkController = new NetworkController();
					networkController.upvoteAnswer(question.getStringID(), answerArray.get(groupPosition).getStringID());
					

				}

            }
        });
		
        if (answerArray.get(groupPosition).hasPicture() == true) {

			ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
			hasPictureIcon.setVisibility(0);
			ImageView imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
			String encodedImage= answerArray.get(groupPosition).getEncodedImage();
			byte [] imageBytes= Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
			imagePreview.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length));
        }
		
		
        ImageButton replyBtn = (ImageButton) view.findViewById(R.id.replyBtn);
        replyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				// Passing info about which questionList it's from, questionPosition & questionAnswer & the type (we're replying to an Answer) so we know where to add the reply to
				Bundle args = new Bundle();
				args.putInt("fromFragment", fromFragment);
				args.putInt("questionPosition", questionPosition);
				args.putInt("answerPosition", groupPosition);

				args.putInt("type", TYPE_ANSWER);
				
				// Create & display reply dialog + attach arguments
				DialogFragment replyDialog = new WriteReplyDialogFragment();
				replyDialog.setArguments(args);
				replyDialog.show(activity.getFragmentManager(), null);
				
			}
		});
        
        CheckBox favBtn = (CheckBox) view.findViewById(R.id.favBtn);
        favBtn.setChecked(answerArray.get(groupPosition).getIsFav());
        favBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				QuestionList tempList = findQuestionList();
				Question tempQuestion = tempList.get(questionPosition);
				if(!ListHandler.isInFavs(tempQuestion.getStringID())) {
					answerArray.get(groupPosition).setIsFav(true);
					notifyDataSetChanged();
					
					tempQuestion.setIsFav(true);
					ListHandler.getFavsList().add(tempQuestion);
					tempList.set(tempList.getIndexFromID(tempQuestion.getID()), tempQuestion);
					
					int index = ListHandler.getMyQsList().getIndexFromID(tempQuestion.getID());
					if (index != -1) {
						ListHandler.getMyQsList().get(index).setIsFav(true);
						ListHandler.getMyQsList().notifyViews();
						System.out.println(Boolean.toString(ListHandler.getMyQsList().getFromStringID(tempQuestion.getStringID()).getIsFav()));
					}
				}
				else {
					answerArray.get(groupPosition).setIsFav(false);
					notifyDataSetChanged();
					Toast.makeText(activity, "Already favorited!", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        CheckBox readingListBtn = (CheckBox) view.findViewById(R.id.offlineBtn);
        readingListBtn.setChecked(answerArray.get(groupPosition).getIsInReadingList());
        readingListBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {				
				QuestionList tempList = findQuestionList();
				Question tempQuestion = tempList.get(questionPosition);
				if(!ListHandler.isInReadingList(tempQuestion.getStringID())) {
					answerArray.get(groupPosition).setIsInReadingList(true);
					notifyDataSetChanged();
					
					tempQuestion.setIsInReadingList(true);
					ListHandler.getReadingList().add(tempQuestion);
					tempList.set(tempList.getIndexFromID(tempQuestion.getID()), tempQuestion);
					
					int index = ListHandler.getMyQsList().getIndexFromID(tempQuestion.getID());
					if (index != -1) {
						ListHandler.getMyQsList().get(index).setIsInReadingList(true);
						ListHandler.getMyQsList().notifyViews();
						System.out.println(Boolean.toString(ListHandler.getMyQsList().getFromStringID(tempQuestion.getStringID()).getIsInReadingList()));
					}
				}
				else {
					answerArray.get(groupPosition).setIsInReadingList(false);
					notifyDataSetChanged();
					Toast.makeText(activity, "Already added to the reading list!", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        return view;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
	/**
	 * Find question list.
	 *
	 * @return the question list
	 */
	private QuestionList findQuestionList() {
		switch (fromFragment) {
		case (HOME_SCREEN_FRAGMENT):
			return ListHandler.getMasterQList();
		case (FAVORITES_FRAGMENT):
			return ListHandler.getFavsList();
		case (READING_LIST_FRAGMENT):
			return ListHandler.getReadingList();
		case (MY_QUESTIONS_FRAGMENT):
			return ListHandler.getMyQsList();
		}
		return null;
	}
 
}