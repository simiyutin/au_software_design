package com.simiyutin.au.shell.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity which represents a concept of environment which handles state of a running shell.
 * It can be asked to give variable by its name and to accept new variable by name and value
 */
public class Environment {
    private Map<String, String> map;

    public Environment() {
        this.map = new HashMap<>();
    }

    /**
     * Get value of variable
     * @param var name of requested variable
     * @return value of a requested variable or empty string if such does not exist in environment
     */
    public String get(String var) {

        String res = map.get(var);

        if (res == null) {
            return "";
        } else {
            return res;
        }
    }


    /**
     * Put variable value
     * @param var name of inserted variable
     * @param val value of inserted variable
     */
    public void put(String var, String val) {
        map.put(var, val);
    }
}
