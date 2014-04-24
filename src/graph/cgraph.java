
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
import java.util.HashSet;

/**
 *
 * @author andrey
 */


public class cgraph {
    private Graphics g;
    private Graphics g1;
    private int[][] m;
    private int count;
    private Circle[] pnts;
    private Color backgr;
    
    public cgraph(Graphics g,Graphics g1, Color clr){
        this.g = g;
        this.g1 = g1;
        count =0;
        m = new int[100][100];
        pnts = new Circle[100];
        backgr = clr;
    }

    public int getCount() {
        return count;
    }

    public int[][] getM() {
        return m;
    }

    public Circle[] getPnts() {
        return pnts;
    }
                 
    public void addnode(Point pnt){
        Circle c = new Circle(pnt);
        if (deleteNode(pnt)==-1){
            boolean result = false;
            for (int i=0;i<count && result;i++)
                if (pnts[i]==null){
                    pnts[i] = c;
                    result =true;
                }
            if (!result)
                pnts[count] = c;
            count ++;
        }
        for (Circle pnt1 : pnts) {
            if (pnt1 != null) {
                pnt1.print(g);
                pnt1.print(g1);
            }
        }
    }

    public int deleteNode(Point pnt) {
        Circle c = new Circle(pnt);
        int k = -1;
        for (int i = 0; i < pnts.length; i++) {
            if (c.equals(pnts[i])) {
                k = i;
            }
        }
        if (k != -1) {
            g.clearRect(pnts[k].pnt.x, pnts[k].pnt.y, pnts[k].rad, pnts[k].rad);
            g1.clearRect(pnts[k].pnt.x, pnts[k].pnt.y, pnts[k].rad, pnts[k].rad);
            for (int i = 0; i < 100; i++) {
                if (m[i][k] == 1) {
                    removeRoad(i, k);
                }

            }
            pnts[k] = null;
        }
        for (Circle pnt1 : pnts) {
            if (pnt1 != null) {
                pnt1.print(g);
                pnt1.print(g1);
            }
        }
        return k;
    }
    
    public void deleteRoad(Point p1,Point p2){
        Circle tmp1 = new Circle(p1);
        Circle tmp2 = new Circle(p2);
        int r1=-2,r2=-2;
        for (int i=0;i<pnts.length;i++)
            if (tmp1.equals(pnts[i]))
                r1 = i;
        for (int i=0;i<pnts.length;i++)
            if (tmp2.equals(pnts[i]))
                r2 = i; 
        if (r1!=-2 && r2!=-2)
            removeRoad(r1,r2);
        for (Circle pnt1 : pnts) {
            if (pnt1 != null) {
                pnt1.print(g);
                pnt1.print(g1);
            }
        }        
    }
    
    private void removeRoad(int r1, int r2){
        m[r1][r2] = -1;
        m[r2][r1] = -1;
        g.setColor(backgr);
        g1.setColor(backgr);
        Circle tmp1 = pnts[r1];
        Circle tmp2 = pnts[r2];       
        g.drawLine(tmp1.pnt.x+Circle.rad/2, tmp1.pnt.y+Circle.rad/2, tmp2.pnt.x+Circle.rad/2, tmp2.pnt.y+Circle.rad/2);
        g1.drawLine(tmp1.pnt.x+Circle.rad/2, tmp1.pnt.y+Circle.rad/2, tmp2.pnt.x+Circle.rad/2, tmp2.pnt.y+Circle.rad/2);
        g.setColor(Color.black);
        g1.setColor(Color.black);
    }
    
    public void addline(Point p1,Point p2){
        int f1=-2,f2=-2;
        Circle tmp1 = new Circle(p1);
        Circle tmp2 = new Circle(p2);
        for (int i=0;i<pnts.length;i++)
            if (tmp1.equals(pnts[i]))
                f1=i;
        for (int i=0;i<pnts.length;i++)
            if (tmp2.equals(pnts[i]))
                f2=i;        
        if (f1>-1 && f2>-1 && f1!=f2){
            m[f1][f2] = 1;
            m[f2][f1] = 1;
            g.drawLine(pnts[f1].pnt.x+Circle.rad/2, pnts[f1].pnt.y+Circle.rad/2, pnts[f2].pnt.x+Circle.rad/2, pnts[f2].pnt.y+Circle.rad/2);
            g1.drawLine(pnts[f1].pnt.x+Circle.rad/2, pnts[f1].pnt.y+Circle.rad/2, pnts[f2].pnt.x+Circle.rad/2, pnts[f2].pnt.y+Circle.rad/2);
        }
    }  
}
