package com.company.Services;

import java.io.IOException;

/**
 * Created by Lua_b on 01.11.2015.
 */
public abstract class Rule {

    abstract public boolean check(String txt, Message msg) throws IOException;

}
