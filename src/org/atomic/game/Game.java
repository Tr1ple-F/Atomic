package org.atomic.game;

import org.atomic.window.Window;

/**
 * Created by Tr1ple-F on the 13.04.2019
 */
public class Game {

    public static void init(){
        Window.init();
    }

    public static void main(){
        Window.update();
        exit();
    }

    public static void exit(){
        Window.exit();
    }

}
