package gitrunner.desktop;

import com.formdev.flatlaf.FlatDarculaLaf;
import gitrunner.cli.CLIGit;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        MainWindow mw = new MainWindow(
                new CLIGit(
                        "/home/inigo/codel/biggit-gui",
                        System.out::println,
                System.out));
    }
}
