package org.sla.secondapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.UserMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.sla.secondapplication.model.Message;
import org.sla.secondapplication.model.MessageModule;

import java.util.Date;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        for(MessageModule messageModule : MainActivity.getModuleManager().getModules()) {
            addNewMessage(messageModule);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(ScrollingActivity.this, PopupWindow.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);



        for(int i = 0; i < linearLayout.getChildCount(); i++){
            final LinearLayout view = (LinearLayout) linearLayout.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    for(int i = 0; i < view.getChildCount(); i++){
                        View view1 = view.getChildAt(i);

                        if(view1 instanceof CardView){
                            CardView cardView = (CardView) view1;
                            TextView textView = (TextView) cardView.getChildAt(0);
                            final MessageModule messageModule = MainActivity.getModuleManager().getModule();

                            if(messageModule != null){
                                OpenChannel.getChannel(MainActivity.messageChannel.getUrl(), new OpenChannel.OpenChannelGetHandler() {
                                    @Override
                                    public void onResult(OpenChannel openChannel, SendBirdException e) {
                                        if (e != null) {    // Error.
                                            return;
                                        }

                                        openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                                            @Override
                                            public void onResult(SendBirdException e) {
                                                if (e != null) {    // Error.
                                                    return;
                                                }
                                            }
                                        });
                                        //openChannel.sendUserMessage("Test", MainActivity.sendUserMessageHandler);
                                    }
                                });
                                Intent intent = new Intent(ScrollingActivity.this, MessageModuleActivity.class);
                                intent.putExtra("message", messageModule.getDisplayName());
                                startActivity(intent);
                            }
                        }
                    }
                }
            });
        }
    }



    public void addNewMessage(MessageModule messageModule){
        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        LinearLayout module = (LinearLayout) LayoutInflater.from(MainActivity.getContext()).inflate(R.layout.message_module, null);
        for(int i = 0; i < module.getChildCount(); i++){
            if(module.getChildAt(i) instanceof CardView) {
                CardView cardView = (CardView) module.getChildAt(i);
                for (int a = 0; a < cardView.getChildCount(); a++) {
                    TextView textView = (TextView) cardView.getChildAt(a);
                    if (textView.getText().toString().equalsIgnoreCase("Title")) {
                        textView.setText(messageModule.getDisplayName());
                    }
                    if (textView.getText().toString().equalsIgnoreCase("message")) {
                        //textView.setText(messageModule.getMessages().get((messageModule.getMessages().size() - 1)).getText());
                    }
                }
            }
        }
        linearLayout.addView(module);
    }



}
