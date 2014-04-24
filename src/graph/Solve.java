/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author andrey
 */
public class Solve {

    private static final Color[] color = {Color.green, Color.red, Color.blue, Color.yellow, Color.magenta, Color.orange, Color.pink, Color.cyan};
    ArrayList<Integer> Colors;
    int Value;

    Solve() {
        this.Colors = new ArrayList();
        this.Value = 0;
    }

    public boolean checkSolve(int[][] matr) {
        for (int i = 0; i < Colors.size(); i++) {
            for (int j = 0; j < Colors.size(); j++) {
                if (j != i && matr[i][j] == 1) {
                    if (Colors.get(i) == Colors.get(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void ValueSolve(int[][] matr) {
        if (checkSolve(matr)) {
            ArrayList<Integer> Colors = this.Colors;
            HashSet<Integer> tmp = new HashSet();
            for (int i = 0; i < Colors.size(); i++) {
                tmp.add(Colors.get(i));
            }
            this.Value = tmp.size();
        } else {
            this.Value = Colors.size() + 1;
        }
    }

    public void paint(Circle[] points, Graphics g) {
        for (int i = 0; i < this.Colors.size(); i++) {
            if (points[i] != null) {
                points[i].setColor(g, color[this.Colors.get(i)]);
            }
        }
        g.setColor(Color.black);
    }
}
