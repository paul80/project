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
 
/*
 * AnswerListAdapter class which inherits functions from BaseExpandableListAdapter
 * 
 */
public class AnswerListAdapter extends BaseExpandableListAdapter {
 
	private static int TYPE_ANSWER = 1;
	
    private Activity activity;
    private int groupLayoutID;
    private int childLayoutID;
    private ArrayList<Answer> answerArray;
    private int fromFragment;
    private int questionPosition;
    
    //creates the AnswerListAdapter
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
    @Override
    public Reply getChild(int groupPosition, int childPosition) {
        return answerArray.get(groupPosition).getReplyAt(childPosition);
    }
 
    //grabs ChildID. Still needs to be done.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }
 
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
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return answerArray.get(groupPosition).getSizeReplies();
    }
 
    @Override
    public Answer getGroup(int groupPosition) {
        return answerArray.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return answerArray.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
 
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
        answerDisplay.setText(answerArray.get(groupPosition).getAnswer());
         
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
					answerArray.get(groupPosition).upvoteAnswer();
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
 
    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }
 
}