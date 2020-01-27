package org.sla.secondapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sendbird.android.AdminMessage;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.FileMessage;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.SendBird.ConnectHandler;
import com.sendbird.android.UserMessage;

import org.sla.secondapplication.model.MessageModule;
import org.sla.secondapplication.model.ModuleManager;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static Context context;
    private static ModuleManager moduleManager;

    public static OpenChannel messageChannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SendBird.init("9DC419D6-D8D3-4BBC-8578-0FF33587DA2B", getApplicationContext());
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.textView)).setText("Enter the password.");
        context = getApplicationContext();
        moduleManager = new ModuleManager();
    }

    /* View is the object that is being pressed in this instance the login button*/
    public void sendMessage(View view){
        TextView textView = findViewById(R.id.textView);
        EditText username = findViewById(R.id.username);
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if(password.equalsIgnoreCase("password")){
            if(username.getText().length() <= 3){
                textView.setText("Your username isn't long enough!");
                return;
            }
            textView.setText("Password is correct!");
            SendBird.connect(username.getText().toString(), new ConnectHandler(){

                @Override
                public void onConnected(User user, SendBirdException e){
                    if(e != null){
                        //error
                        e.printStackTrace();
                        return;
                    }
                    System.out.println("Connected with user: " + user.getProfileUrl());
                    openChannel();
                }

            });
            //setContentView(R.layout.home_page);
            Intent start = new Intent(MainActivity.this, ScrollingActivity.class);
            startActivity(start);
        }else{
            textView.setText("You have entered the wrong password!");
        }
    }

    public void openChannel() {
        /*OpenChannel.createChannel(new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if (e != null) {    // Error.
                    e.printStackTrace();
                    return;
                }
                messageChannel = openChannel;
                System.out.println("Channel " + openChannel.getUrl() + " created!");
            }
        });*/

        OpenChannel.getChannel("sendbird_open_channel_62294_065d7e0f56315ddfeb0ccc554fbc26969799edc6", new OpenChannel.OpenChannelGetHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                if(e != null){
                    e.printStackTrace();
                    return;
                }
                messageChannel = openChannel;
                System.out.println("Accessed Channel: " + openChannel.getUrl());
            }
        });

        SendBird.addChannelHandler("messages", new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel channel, BaseMessage message) {
                System.out.println(channel.getName() + ": " + message.getMessageId());
                // TODO: Implement what is needed with the contents of the response in the the 'message' parameter.
                if (message instanceof UserMessage) {
                    if (channel.getUrl().equalsIgnoreCase(messageChannel.getUrl())) {
                        moduleManager.modules.get(0).getMessages().add(message);
                        MessageModuleActivity.getInstance().getmMessageAdapter().notifyDataSetChanged();
                    }
                } else if (message instanceof FileMessage) {

                } else if (message instanceof AdminMessage) {

                }
            }

        });

    }

    public static BaseChannel.SendUserMessageHandler sendUserMessageHandler = new BaseChannel.SendUserMessageHandler() {
        @Override
        public void onSent(UserMessage userMessage, SendBirdException e) {
            moduleManager.getModule().getMessages().add(userMessage);
        }
    };



    public void finish() {
        stop();
    }

    private void stop(){
        SendBird.disconnect(new SendBird.DisconnectHandler() {
            @Override
            public void onDisconnected() {
                // A current user is disconnected from SendBird server.
            }
        });
    }



    public static Context getContext(){
        return context;
    }


    public static ModuleManager getModuleManager() {
        return moduleManager;
    }
}
