/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.session;

import java.util.HashMap;

/**
 *
 * @author ivano
 */
public class Session {
    
    private final HashMap<String,Object> map;
    private static Session instance;
    
    private Session(){
        map = new HashMap<>();
    }
    
    public static Session getInstance(){
        if(instance == null)
            instance = new Session();
        
        return instance;
    }
    
    public void add(String key,Object o){
        map.put(key, o);
    }
    
    public boolean remove(String key){
        if(map.containsKey(key)){
            map.remove(key);
            return true;
        }
        
        return false;
    }
    
    public Object get(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }
        return null;
    }
}
