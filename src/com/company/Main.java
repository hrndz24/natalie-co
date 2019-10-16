package com.company;

import natalie.gui.Natalie;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Natalie natalie = new Natalie();
        natalie.display();
        natalie.setVisible(true);
    }
}
