/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author andrey
 */
public class genetic {

    //private final ArrayList<Circle> points;
    private final int[][] matr;
    private static final Color[] color = {Color.green, Color.red, Color.blue, Color.yellow, Color.magenta, Color.orange, Color.pink, Color.cyan};
    private static int generations;
    private static int populationsize;
    private static int startsize;
    private static double crossingindex;
    private static double mutationchomosome;
    private static double mutationgen;
    private static final int maxcolor = color.length;
    public static int chromosomesize;
    private Solve BestSolve;
    private BufferedWriter bufferedWriter;

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
    genetic(int size, int[][] matr, int gen, int popul, double ci, double mc, double mg) {
        this.matr = matr;
        generations = gen;
        populationsize = popul;
        startsize = populationsize;
        crossingindex = ci;
        mutationchomosome = mc;
        mutationgen = mg;
        chromosomesize = size;
    }

    public Solve genetic() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("log.txt"), "UTF-8"));
        ArrayList<Solve> population = new ArrayList();
        myInit(population);
        writeLog(population, 0, "Начальная популяция");
        //фитнес функция
        for (int i = 0; i < generations; i++) {
            crossing(population);
            writeLog(population, i + 1, "Скрещивание");
            mutation(population);
            writeLog(population, i + 1, "Мутация");
            population = selection(population);
            writeLog(population, i + 1, "Селекция");
        }
        bufferedWriter.close();
        //BestSolve.paint(points,g);
        /*for (int i = 0; i < BestSolve.Colors.size(); i++) {
            if (points[i] != null) {
                points[i].setColor(g, color[BestSolve.Colors.get(i)]);
            }
        }
        g.setColor(Color.black);*/
        return BestSolve;
    }

    public void writeLog(ArrayList<Solve> population, int i, String str) throws IOException {
        bufferedWriter.write("Поколение" + i + "\n");
        bufferedWriter.write(str + "\n");
        for (int j = 0; j < populationsize; j++) {
            for (int k = 0; k < population.get(j).Colors.size(); k++) {
                bufferedWriter.write(population.get(j).Colors.get(k) + " ");
            }
            bufferedWriter.write("\t");
            bufferedWriter.write("" + population.get(j).Value);
            bufferedWriter.write("\n");
        }
    }

    public void myInit(ArrayList<Solve> population) {
        for (int i = 0; i < populationsize; i++) {
            population.add(randomSolve(chromosomesize));
        }

        int val, imin = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < populationsize; i++) {
            //population.get(i).ValueSolve();
            val = population.get(i).Value;
            if (val < min) {
                imin = i;
                min = val;
            }
        }
        setBestSolve(population.get(imin));
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
            if (rand.nextDouble() < crossingindex) {
                //c.newSolve(population.get(ind1), population.get(ind2));
                newSolve(population, ind1, ind2);
                //population.add(c);
                //populationsize++;
            }
        }
    }

    public void mutation(ArrayList<Solve> population) {
        Random rand = new Random();
        Solve slv;
        ArrayList<Integer> clr;
        for (int i = 0; i < populationsize; i++) {
            if (rand.nextDouble() < mutationchomosome) {
                clr = population.get(i).Colors;
                for (int j = 0; j < clr.size(); j++) {
                    if (rand.nextDouble() < mutationgen) {
                        //рандом до предыдущего цвета
                        //clr.set(j, rand.nextInt(clr.get(j))+1);
                        clr.set(j, rand.nextInt(maxcolor));
                    }
                }
                population.get(i).ValueSolve(matr);
            }
        }
    }

    public ArrayList<Solve> selection(ArrayList<Solve> population) {
        ArrayList<Double> chances = new ArrayList();
        ArrayList<Solve> elitePopulation = new ArrayList();
        Random rand = new Random();
        int val, imin = 0, sumchance = 0;
        int min = Integer.MAX_VALUE;
        int max = BestSolve.Value;
        for (int i = 0; i < populationsize; i++) {
            //population.get(i).ValueSolve();
            val = population.get(i).Value;
            if (val < min) {
                imin = i;
                min = val;
            }
            sumchance += chromosomesize+2 -val; 
        }
        if (population.get(imin).Value < max) {
            this.setBestSolve(population.get(imin));
        }
        for (int i = 0; i < populationsize; i++) {
            chances.add((double) (chromosomesize+2 - population.get(i).Value) / sumchance);
        }
        for (int i = 1; i < chances.size(); i++) {
            chances.set(i, chances.get(i - 1) + chances.get(i));
        }
        //

        for (int i = 0; i < populationsize; i++) {
            double p = rand.nextDouble();
            boolean ok = false;
            int index = 0;
            for (int j = 0; j < chances.size() && !ok; j++) {
                if (p < chances.get(j)) {
                    index = j;
                    ok = true;
                }
            }
            elitePopulation.add(population.get(index));

        }
        return elitePopulation;
        /*for (int i = 0; i < populationsize; i++) {
         if (populationsize > startsize && rand.nextDouble() > chances.get(i)) {
         population.remove(i);
         chances.remove(i);
         populationsize--;
         }
         }*/
    }

    private void newSolve(ArrayList<Solve> population, int ind1, int ind2) {
        Random rand = new Random();
        Solve a = population.get(ind1);
        Solve b = population.get(ind2);
        Solve c = new Solve();
        Solve d = new Solve();
        int j = rand.nextInt(chromosomesize);
        for (int i = 0; i < j; i++) {
            c.Colors.add(a.Colors.get(i));
            d.Colors.add(b.Colors.get(i));
        }
        for (int i = j; i < chromosomesize; i++) {
            c.Colors.add(b.Colors.get(i));
            d.Colors.add(a.Colors.get(i));
        }
        c.ValueSolve(matr);
        d.ValueSolve(matr);
        population.set(ind1, c);
        population.set(ind2, d);
    }

    private void setBestSolve(Solve c) {
        if (BestSolve == null) {
            BestSolve = new Solve();
            for (int i = 0; i < c.Colors.size(); i++) {
                BestSolve.Colors.add(c.Colors.get(i));
            }
            BestSolve.Value = c.Value;
        } else {
            for (int i = 0; i < c.Colors.size(); i++) {
                BestSolve.Colors.set(i, c.Colors.get(i));
            }
            BestSolve.Value = c.Value;
        }

        // BestSolve = c;
    }

    private Solve randomSolve(int n) {
        Solve slv = new Solve();
        Random rand = new Random();
        for (int i = 0; i < chromosomesize; i++) {
            slv.Colors.add(rand.nextInt(maxcolor));
        }
        slv.ValueSolve(matr);
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
