package com.blu.reactive;

import junit.framework.TestCase;

/**
 * Created by shamim on 11/07/15.
 */
public class HelloWorldCommandTest extends TestCase {
    public void testCommand(){
        new HelloWorldCommand().execute();
    }
}
