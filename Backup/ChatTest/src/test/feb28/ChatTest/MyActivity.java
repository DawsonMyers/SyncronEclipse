package test.feb28.ChatTest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MyActivity extends Activity {

	/**
	 * Called when the activity is first created.
	 */
	private ArrayList<String> messages = new ArrayList<String>();
	private Handler           mHandler = new Handler();

	private EditText recipient;
	private EditText textMessage;
	private ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		recipient = (EditText) this.findViewById(R.id.toET);
		textMessage = (EditText) this.findViewById(R.id.chatET);
		listview = (ListView) this.findViewById(R.id.listMessages);
		setListAdapter();

		// Set a listener to send a chat text message
		Button send = (Button) this.findViewById(R.id.sendBtn);
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String to = recipient.getText().toString();
				String text = textMessage.getText().toString();
				Log.i("XMPPChatDemoActivity ", "Sending text " + text + " to " + to);

				messages.add(to);
				messages.add(text);
				setListAdapter();
			}
		});
	}
			private void setListAdapter() {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listitem, messages);
				listview.setAdapter(adapter);
			}

		}