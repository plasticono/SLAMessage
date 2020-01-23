package org.sla.secondapplication.model;

import com.sendbird.android.BaseMessage;

import org.sla.secondapplication.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MessageModule {

    private String displayName;
    private List<BaseMessage> messages;
    private boolean unread;


    public MessageModule(String displayName){
        this.displayName = displayName;
        messages = new ArrayList<>();
        MainActivity.getModuleManager().getModules().add(this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<BaseMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<BaseMessage> messages) {
        this.messages = messages;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
