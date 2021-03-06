package ca.ualberta.cs.queueunderflow.views;

import ca.ualberta.cs.queueunderflow.R;
import ca.ualberta.cs.queueunderflow.R.id;
import ca.ualberta.cs.queueunderflow.R.layout;
import ca.ualberta.cs.queueunderflow.R.string;
import ca.ualberta.cs.queueunderflow.controllers.WriteReplyController;
import ca.ualberta.cs.queueunderflow.models.Answer;
import ca.ualberta.cs.queueunderflow.models.Question;
import ca.ualberta.cs.queueunderflow.singletons.ListHandler;
import ca.ualberta.cs.queueunderflow.singletons.User;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * The Class WriteReplyDialogFragment.
 * Handles getting user input to add a reply
 * @author group 10
 * @version 1.0
 */
public class WriteReplyDialogFragment extends DialogFragment {
	
	/** The controller. */
	WriteReplyController controller;
	
    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        final View view = inflater.inflate(R.layout.write_reply_dialog_fragment, null);
        builder.setView(view);
       
        controller = new WriteReplyController(getActivity(), view);
        
        TextView authorText= (TextView) view.findViewById(R.id.authorText);
        authorText.setText(User.getUserName());
        
        builder.setPositiveButton("Reply", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				controller.addReply(getArguments());
			}
		});
        
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
             
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 
            }
        });
         
        return builder.create();
    }

}