package org.sla.secondapplication.model;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public List<MessageModule> modules;


    public ModuleManager(){
        modules = new ArrayList<>();
    }


    public List<MessageModule> getModules() {
        return modules;
    }

    public MessageModule getModule() {
        return modules.get(0);
    }



        public MessageModule getModule(String string){
        for(MessageModule module : modules){
            if(module.getDisplayName().equalsIgnoreCase(string)){
                return module;
            }
        }
        return null;
    }
}
