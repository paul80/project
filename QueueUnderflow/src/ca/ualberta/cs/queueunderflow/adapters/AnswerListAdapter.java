package ca.ualberta.cs.queueunderflow.adapters;

import java.util.ArrayList;

import ca.ualberta.cs.queueunderflow.ListHandler;
import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.User;
import ca.ualberta.cs.queueunderflow.R.id;
import ca.ualberta.cs.queueunderflow.R.layout;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Reply;
import ca.ualberta.cs.queueunderflow.views.WriteReplyDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
// TODO: Auto-generated Javadoc
/*
 * AnswerListAdapter class which inherits functions from BaseExpandableListAdapter
 * 
 */
/**
 * The Class AnswerListAdapter.
 */
public class AnswerListAdapter extends BaseExpandableListAdapter {
 
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
    
    //creates the AnswerListAdapter
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
         
        // Should Replies have a date displayed? Or is it not necessary?
        //TextView dateDisplay = (TextView) view.findViewById(R.id.dateTextView);
        //dateDisplay.setText(DateFormat.format("MMM dd, yyyy", answerArray.get(groupPosition).getReplyAt(childPosition).getDate()));
         
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
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
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
         
        TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
        upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
         
        ImageButton upvoteBtn = (ImageButton) view.findViewById(R.id.upvoteBtn);
        upvoteBtn.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
            	User user= ListHandler.getUser();
                //answerArray.get(groupPosition).upvoteAnswer();
            	Answer answer= answerArray.get(groupPosition);
    			if (user.alreadyUpvotedAnswer(answer)) {
					Toast.makeText(activity.getApplicationContext(), "Answer was already upvoted", Toast.LENGTH_SHORT).show();
				}
				else {
					user.addUpvotedAnswer(answerArray.get(groupPosition));
					answerArray.get(groupPosition).upvoteResponse();
					TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
					upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
				}
            	/*
                TextView upvoteDisplay = (TextView) view.findViewById(R.id.upvoteDisplay);
                upvoteDisplay.setText(Integer.toString(answerArray.get(groupPosition).getUpvotes()));
                */
            }
        });
         
        if (answerArray.get(groupPosition).hasPicture() == true) {
            ImageButton hasPictureIcon = (ImageButton) view.findViewById(R.id.hasPictureIcon);
            hasPictureIcon.setVisibility(0);
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
        
        return view;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
 
    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }
 
}