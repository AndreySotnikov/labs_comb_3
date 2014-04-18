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
import java.util.Random;

/**
 *
 * @author andrey
 */
public class genetic {

    class Solve {

        ArrayList<Integer> Colors;
        int Value;
        
        Solve() {
            this.Colors = new ArrayList();
            this.Value = 0;
        }

        public void newSolve(Solve a, Solve b) {
            Random rand = new Random();
            int j = rand.nextInt(chromosomesize);
            for (int i = 0; i < j; i++) {
                this.Colors.add(a.Colors.get(i));
            }
            for (int i = j; i < chromosomesize; i++) {
                this.Colors.add(b.Colors.get(i));
            }
        }

        public boolean checkSolve() {
            ArrayList<Integer> ws = new ArrayList();
            for (int i = 0; i < chromosomesize; i++) {
                for (int j = 0; j < chromosomesize; j++) {
                    if (j != i && matr[i][j] == 1) {
                        ws.add(j);
                    }
                }
                for (int k = 0; k < ws.size(); k++) {
                    if (i != ws.get(k) && Colors.get(i) == Colors.get(ws.get(k))) {
                        return false;
                    }
                }
                ws.clear();
            }
            return true;
        }

        public void ValueSolve() {
            if (checkSolve()) {
                ArrayList<Integer> Colors = this.Colors;
                HashSet<Integer> tmp = new HashSet();
                for (int i = 0; i < Colors.size(); i++) {
                    tmp.add(Colors.get(i));
                }
                this.Value = tmp.size();
            } else {
                this.Value = chromosomesize + 1;
            }
        }

    }

    //private final ArrayList<Circle> points;
    private final Circle[] points;
    private final int[][] matr;
    private static final Color[] color = {Color.green, Color.red, Color.blue, Color.yellow, Color.magenta, Color.orange, Color.pink, Color.cyan};
    private static int generations = 33;
    private static int populationsize = 9999;
    private static int startsize;
    private static double crossingindex = 0.5;
    private static double mutationchomosome = 0.5;
    private static double mutationgen = 0.5;
    private static final int maxcolor = color.length;
    public static int chromosomesize;
    private final Graphics g;
    private Solve BestSolve;

    /*genetic(ArrayList<Circle> points, int[][] matr, int gen, int popul, double ci, double mc, double mg, Graphics g) {
        //this.points = points;
        this.matr = matr;
        generations = gen;
        populationsize = popul;
        startsize = populationsize;
        crossingindex = ci;
        mutationchomosome = mc;
        mutationgen = mg;
        chromosomesize = points.size();
        this.g = g;
    }*/
    
    genetic(int size,Circle[] points, int[][] matr, int gen, int popul, double ci, double mc, double mg, Graphics g) {
        this.points = points;
        this.matr = matr;
        generations = gen;
        populationsize = popul;
        startsize = populationsize;
        crossingindex = ci;
        mutationchomosome = mc;
        mutationgen = mg;
        chromosomesize = size;
        this.g = g;
    }

    public int genetic() {
        ArrayList<Solve> population = new ArrayList();
        myInit(population);
        for (int i = 0; i < generations; i++) {
            crossing(population);
            mutation(population);
            selection(population);
        }
        for (int i = 0; i < BestSolve.Colors.size(); i++) {
            if (points[i]!=null)
                points[i].setColor(g, color[BestSolve.Colors.get(i)]);
        }
        g.setColor(Color.black);
        /*for (int i = 0; i < BestSolve.Colors.size(); i++) {
            points.get(i).setColor(g, color[BestSolve.Colors.get(i)]);
        }*/
        return BestSolve.Value;
    }
    
    public void myInit(ArrayList<Solve> population) {
        for (int i = 0; i < populationsize; i++) {
            population.add(randomSolve(chromosomesize));
        }
        setBestSolve(population.get(0));
    }
    
    public void crossing(ArrayList<Solve> population) {
        ArrayList<Integer> tmp = new ArrayList();
        Random rand = new Random();
        int ind1, ind2;
        for (int i = 0; i < populationsize; i++) {
            tmp.add(i);
        }
        int k = populationsize;
        while (k >= 2) {
            ind1 = getNum(tmp, k);
            k--;
            ind2 = getNum(tmp, k);
            k--;
            Solve c = new Solve();
            if (rand.nextDouble() < crossingindex) {
                c.newSolve(population.get(ind1), population.get(ind2));
                population.add(c);
                populationsize++;
            }
        }
    }    
    
    public void mutation(ArrayList<Solve> population) {
        Random rand = new Random();
        Solve slv;
        ArrayList<Integer> clr;
        for (int i = 0; i < populationsize; i++) {
            slv = population.get(i);
            clr = slv.Colors;
            if (rand.nextDouble() < mutationchomosome) {
                for (int j = 0; j < clr.size(); j++) {
                    if (rand.nextDouble() < mutationgen) {
                        clr.set(j, rand.nextInt(maxcolor));
                    }
                }
            }
        }
    }    

    public void selection(ArrayList<Solve> population) {
        ArrayList<Double> chances = new ArrayList();
        Random rand = new Random();
        int val, imin = 0, sumchance = 0;
        int min = Integer.MAX_VALUE;
        int max = BestSolve.Value;
        for (int i = 0; i < populationsize; i++) {
            population.get(i).ValueSolve();
            val = population.get(i).Value;
            if (val < min) {
                imin = i;
                min = val;
            }
            sumchance += val;
        }
        if (population.get(imin).Value < max) {
            this.setBestSolve(population.get(imin));
        }
        for (int i = 0; i < populationsize; i++) {
            chances.add((double) population.get(i).Value / sumchance);
        }
        for (int i = 0; i < populationsize; i++) {
            if (populationsize > startsize && rand.nextDouble() > chances.get(i)) {
                population.remove(i);
                chances.remove(i);
                populationsize--;
            }
        }
    }

    private void setBestSolve(Solve c) {
        BestSolve = c;
    }

    private Solve randomSolve(int n) {
        Solve slv = new Solve();
        Random rand = new Random();
        for (int i = 0; i < chromosomesize; i++) {
            slv.Colors.add(rand.nextInt(maxcolor));
        }
        slv.ValueSolve();
        return slv;
    }

    private int getNum(ArrayList<Integer> tmp, int k) {
        Random rand = new Random();
        int t = rand.nextInt(k);
        int m = tmp.get(t);
        tmp.set(t, tmp.get(k - 1));
        return m;
    }
}
