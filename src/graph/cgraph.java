
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
import java.util.Map;
import java.util.Random;

/**
 *
 * @author andrey
 */
/*class Solve{
    ArrayList<Integer> Colors;
    int Value;

    Solve() {
        this.Colors = new ArrayList();
        this.Value = 0;
    }
   
}*/

public class cgraph {
    private Graphics g;
    private Graphics g1;
    //private ArrayList<Point> points;
    private ArrayList<Circle> points;
    private ArrayList<Line> lines;
    //private ArrayList<ArrayList<Integer>> matr;
    private int[][] matr = new int[100][100];
    private int mincolor;
    private Color[] color={Color.green,Color.red,Color.blue,Color.yellow,Color.magenta,Color.orange,Color.pink,Color.cyan};
    private int[] colormas;
    private int maxc;
    private int minnum;
    private int[] gr;
    int n =3;
    int size;
    public static final int generations = 33;
    public static int populationsize = 9999;
    public static int startsize = populationsize;
    public static final double crossingindex = 0.5;
    public static final double mutationchomosome = 0.5;
    public static final double mutationgen = 0.5;
    public static int maxcolor = 8;
    private int[][] m;
    private int count;
    private Circle[] pnts;
    Color backgr;
    //public Solve BestSolve;
    //private HashSet<Integer> ws;
    
    public cgraph(Graphics g,Graphics g1, Color clr){
        this.g = g;
        this.g1 = g1;
        count =0;
        points = new ArrayList();
        lines = new ArrayList();
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

    public ArrayList<Circle> getPoints() {
        return points;
    }

    public int[][] getMatr() {
        return matr;
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
    
    /*public void addline(Point p1, Point p2){
        int f1,f2;
        Circle tmp1 = new Circle(p1);
        Circle tmp2 = new Circle(p2);
        f1 = points.indexOf(tmp1);
        f2 = points.indexOf(tmp2);
        if (f1!=-1 && f2!=-1){
            matr[f1][f2] = 1;
            matr[f2][f1] = 1;
            g.drawLine(points.get(f1).pnt.x+Circle.rad/2, points.get(f1).pnt.y+Circle.rad/2, points.get(f2).pnt.x+Circle.rad/2, points.get(f2).pnt.y+Circle.rad/2);
            lines.add(new Line(new Point(points.get(f1).pnt.x+Circle.rad/2,points.get(f1).pnt.y+Circle.rad/2),new Point(points.get(f2).pnt.x+Circle.rad/2,points.get(f2).pnt.y+Circle.rad/2)));
        }
    }*/
   
   public int Color(int i, int t){
       HashSet<Integer> ws = new HashSet();
       int j=0;
       while (j<=i-1){
           if (matr[i][j]==1)
               ws.add(gr[j]);
           j++;
       }
       j = t;
       do{
           j++;
       }while(ws.contains(j));
       return j;
   } 
   
   public int MaxMin(int c){
       int mc = 0;
       int minn = 0;
       for (int i=0;i<n;i++){
           if (gr[i]>mc){
               mc = gr[i];
               minn = i;
           }               
       }
       maxc = mc;
       return minn;
   }
   
   public int MaxCon(int l, HashSet<Integer> rs){
       int i = l-1;
       int w=0;
       while (i>=0 && rs.contains(i) && w==0){
           if (matr[l][i]==1)
               w=i;
           i--;
       }
       return w;
   }
   
   public void Change(int t){
       HashSet<Integer> ws = new HashSet();
       int q=MaxCon(t,ws);
       int r;
       while (q!=0){
           r = Color(q,gr[q]);
           if (r<maxc)
               for (int j=t+1;j<n;j++)
                   Color(j,r);
           else
               ws.add(q);
           q = MaxCon(t,ws);
       }
   }
   
   private void writeMas() {
        for (int i = 0; i<points.size();i++){
            points.get(i).setColor(g,color[gr[i]-1]);
        }
    }
   
   public void Exact(){
       gr = new int[100]; 
       n = points.size();
       maxc=1;
       minnum=0;
       for(int i=0;i<n;i++){
           gr[i]=Color(i,0);
       }
       int j = 0;
       do{
           minnum = MaxMin(maxc);
           Change(minnum);
       }while(maxc!=gr[minnum]);
       int i=0;
       writeMas();
   /*     //int[] e = new int[100];
        colormas = new int[100];
        HashSet<Integer> b = new HashSet();
        for (int i = 0;i<points.size();i++){
            b.add(i);
            colormas[i]=0;
        }
        mincolor = 5;
        Solve(b,b,0);*/
    }
    /*public void Solve(HashSet<Integer> all, HashSet<Integer> aset, int color){
        if (!aset.isEmpty()){
            if (Independent(aset))
                Paint(all,aset,color);
            for (int i=0;i<points.size();i++){
                if (aset.contains(i)){
                    aset.remove(i);
                    Solve(all,aset,color);
                    aset.add(i);
                }
            }
        }
    }

    private boolean Independent(HashSet<Integer> s) {
        int j,i = 0;
        boolean result = true;
        while (i!=points.size() && result){
            j=0;
            while (i!=j && result){
                if (matr[i][j]==1 && s.contains(i) && s.contains(j))
                    result = false;
                j++;
            }
            i++;
        }
        return result;
    }

    private void Paint(HashSet<Integer> all, HashSet<Integer> b, int color) {
        for (int i=0;i<points.size();i++)
            if (b.contains(i)){
                all.remove(i);
                colormas[i] = color;
            }
        if (!all.isEmpty())
            Solve(all,all,color+1);
        else{
            if (color<mincolor){
                mincolor = color;
                writeMas();
            }
        }            
    }

    private void writeMas() {
        for (int i = 0; i<points.size();i++){
            points.get(i).setColor(g,color[colormas[i]]);
        }
    }*/



}
