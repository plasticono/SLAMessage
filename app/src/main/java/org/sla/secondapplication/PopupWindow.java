package org.sla.secondapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import org.sla.secondapplication.model.Message;
import org.sla.secondapplication.model.MessageModule;

import java.util.Date;

public class PopupWindow extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


    }

    public void createMessage(View view){
        EditText name = findViewById(R.id.editText);
        EditText message = findViewById(R.id.editText3);
        if(name.getText().toString().length() > 0){
            if(message.getText().toString().length() > 0){
                //continue
                MessageModule messageModule = new MessageModule(name.getText().toString());
                //Message message1 = new Message(message.getText().toString(), new Date());
                //messageModule.getMessages().add(message1);
                Intent intent = new Intent(this, ScrollingActivity.class);
                startActivity(intent);
            }
        }


    }

}
