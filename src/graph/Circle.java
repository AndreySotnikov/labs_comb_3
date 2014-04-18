/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author andrey
 */
public class Circle {
    public static final int rad = 60;
    public Point pnt;
    //public ArrayList<Integer> roads;
    
    Circle (Point pnt){
        this.pnt = new Point();
        this.pnt.x = pnt.x - rad/2;
        this.pnt.y = pnt.y - rad/4;
        //this.roads = new ArrayList();
        //this.pnt = pnt;
    }
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Circle){
            Circle o = (Circle)obj;
            if (pnt.x-rad/2<o.pnt.x && pnt.x+rad/2>o.pnt.x && pnt.y-rad/2<o.pnt.y && pnt.y+rad/2>o.pnt.y || 
                    pnt.x-rad/2>o.pnt.x && pnt.x+rad/2<o.pnt.x && pnt.y-rad/2>o.pnt.y && pnt.y+rad/2<o.pnt.y)
            return true;
            else 
                return false;
        }else
            return false;
    }
    
    public void print(Graphics g){
        g.fillOval(pnt.x, pnt.y, rad, rad);
    }

    void setColor(Graphics g,Color clr) {
        g.setColor(clr);
        print(g);
    }
}
