package gitrunner.desktop;

import com.formdev.flatlaf.FlatDarculaLaf;
import inigo.gitgui.git.cli.CLIGit;

import javax.swing.*;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MainWindow mw = new MainWindow(new CLIGit("/home/inigo/codel/biggit-gui", new Consumer<String>() {
            @Override
            public void accept(String s) {
              System.out.println(s);
            }
        }));
    }
}
