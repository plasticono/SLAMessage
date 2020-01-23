package org.sla.secondapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.UserMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;

import org.sla.secondapplication.holders.MessageListAdapter;
import org.sla.secondapplication.model.MessageModule;
import org.sla.secondapplication.model.ModuleManager;

import java.util.ArrayList;

public class MessageModuleActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private LinearLayoutManager myLayoutManager;

    private MessageModule messageModule;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_module);

        mMessageRecycler = findViewById(R.id.recycler_message_list);
        this.messageModule = MainActivity.getModuleManager().getModule(getIntent().getStringExtra("message"));
        mMessageAdapter = new MessageListAdapter(this,  MainActivity.getModuleManager().getModule((getIntent().getStringExtra("message"))).getMessages());
        myLayoutManager = new LinearLayoutManager(this);
        mMessageRecycler.setLayoutManager(myLayoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        System.out.println(MainActivity.getModuleManager().getModule((getIntent().getStringExtra("message"))).getMessages());
    }

    public void send(View view){
        final EditText editText = findViewById(R.id.edittext_chatbox);
        if(editText.getText().length() > 0){
            OpenChannel.getChannel(MainActivity.messageChannel.getUrl(), new OpenChannel.OpenChannelGetHandler() {
                @Override
                public void onResult(OpenChannel openChannel, SendBirdException e) {
                    if(e != null){
                        return;
                    }

                    openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                        @Override
                        public void onResult(SendBirdException e) {
                            if(e != null){

                            }
                            mMessageRecycler.getAdapter().notifyDataSetChanged();
                        }
                    });
                    openChannel.sendUserMessage(editText.getText().toString(), MainActivity.sendUserMessageHandler);
                    editText.setText("");
                    mMessageAdapter.refresh();
                    mMessageRecycler.swapAdapter(mMessageAdapter, false);
                    mMessageAdapter.notifyDataSetChanged();
                }
            });
        }
        System.out.println(messageModule.getMessages());
    }


}
