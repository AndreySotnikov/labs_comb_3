
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
    //public Solve BestSolve;
    //private HashSet<Integer> ws;
    
    public cgraph(Graphics g){
        this.g = g;
        points = new ArrayList();
        lines = new ArrayList();
        //matr = new ArrayList<ArrayList<Integer>>();
    }

    public ArrayList<Circle> getPoints() {
        return points;
    }

    public int[][] getMatr() {
        return matr;
    }
                 
    
    public void addnode(Point pnt,Color clr){
        Circle c = new Circle(pnt);
        //int k = points.indexOf(c);
        /*if (k!=-1){
            Circle del = points.get(k);
                g.clearRect(points.get(k).pnt.x,points.get(k).pnt.y,points.get(k).rad,points.get(k).rad);
                for (int j=0;j<lines.size();j++){
                    Line ln = lines.get(j);
                    Circle tmp1 = new Circle(ln.p1);
                    Circle tmp2 = new Circle(ln.p2);
                    if (del.equals(tmp1) || del.equals(tmp2)){
                        g.setColor(clr);
                        g.drawLine(ln.p1.x, ln.p1.y, ln.p2.x, ln.p2.y);
                        //g.drawLine(tmp1.pnt.x+Circle.rad/2, tmp1.pnt.y+Circle.rad/2, tmp2.pnt.x+Circle.rad/2, tmp2.pnt.y+Circle.rad/2);
                        g.setColor(Color.black);
                        lines.remove(j);
                    }
                }
                points.remove(del); 
        }
        else{*/
            points.add(c);
            //c.print(g);
        //}
        for (Circle p : points){
            p.print(g);
        }
     /*   boolean OK = true;
        for(int i =0;i<points.size();i++){
            Point p = points.get(i);
            if ((int)p.getX()-15<(int)pnt.getX()+15 && (int)p.getX()-15>(int)pnt.getX()-45 && 
                    (int)p.getY()-15<(int)pnt.getY() && (int)p.getY()+15>(int)pnt.getY()){
                g.clearRect((int)p.getX()-15, (int)p.getY(), 31, 31);
                points.remove(p);
                OK = false;
                for (int j = 0; j < lines.size(); j++){
                    Line ln = lines.get(j);
                    if ((int)p.getX()-15<(int)ln.p1.getX()+15 && (int)ln.p1.getX()-15>(int)ln.p1.getX()-45 && 
                        (int)p.getY()-15<(int)ln.p1.getY() && (int)p.getY()+15>(int)ln.p1.getY() || 
                        (int)p.getX()-15<(int)ln.p2.getX()+15 && (int)ln.p2.getX()-15>(int)ln.p2.getX()-45 && 
                        (int)p.getY()-30<(int)ln.p2.getY() && (int)p.getY()+30>(int)ln.p2.getY()){
                        lines.remove(j);
                        g.setColor(Color.white);
                        g.drawLine((int)ln.p1.getX(), (int)ln.p1.getY(), (int)ln.p2.getX(), (int)ln.p2.getY());
                        g.setColor(Color.black);
                    }
                }
            }
        }
        if (OK){
            points.add(pnt);
            //g.drawOval((int)pnt.getX()-15, (int)pnt.getY(), 30, 30);
            g.fillOval((int)pnt.getX()-15, (int)pnt.getY(), 30, 30);
        }
        //matr.add(new ArrayList());*/
        
    }
    
    public void addline(Point p1, Point p2){
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
        /*boolean find1=false,find2=false;
        int k = 0,m = 0;
        for(int i =0;i<points.size();i++){
            Point p = points.get(i);
            if ((int)p.getX()-15<(int)p1.getX()+15 && (int)p.getX()-15>(int)p1.getX()-45 && 
                    (int)p.getY()-15<(int)p1.getY() && (int)p.getY()+15>(int)p1.getY()){
                find1=true;
                k=i;
            }
        }
        for(int i =0;i<points.size();i++){
            Point p = points.get(i);
            if ((int)p.getX()-15<(int)p2.getX()+15 && (int)p.getX()-15>(int)p2.getX()-45 && 
                    (int)p.getY()-15<(int)p2.getY() && (int)p.getY()+15>(int)p2.getY()){
                find2=true;
                m=i;
            }
        }
        if (find1 && find2){
            g.drawLine((int)points.get(k).getX(), (int)points.get(k).getY()+15, (int)points.get(m).getX(), (int)points.get(m).getY()+15);
            lines.add(new Line(new Point((int)points.get(k).getX(),(int)points.get(k).getY()+15),new Point((int)points.get(m).getX(),(int)points.get(m).getY()+15)));
        }
        //g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());*/
    }
   
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
