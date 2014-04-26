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
public class exact {
    int[][] matr;
    int size;
    int[] ss;
    ArrayList<HashSet<Integer>> sets;
    ArrayList<HashSet<Integer>>  a;
    ArrayList<Integer> bestSolve = new ArrayList();
    ArrayList<Integer> Solve = new ArrayList();
    HashSet<Integer> nodes;
    Solve vec;
    
    exact(int N, int[][] m){
        this.sets = new ArrayList();
        nodes = new HashSet();
        for (int i = 0;i < N; i++)
            nodes.add(i);
        matr = m;
        size = N;
        ss = new int[size];
        
    }
    
    public void formGraph() {
        a = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            a.add(new HashSet<Integer>());
            for (int j = 0; j < size; j++) {
             
                if (i != j && matr[i][j] == 1) {

                    a.get(i).add(j);
                }
            }
        }
    }
    
    public void Find(int k, HashSet<Integer> qp, HashSet<Integer> qm){
        HashSet<Integer> gg = new HashSet();
        int delt;
        if (qp.isEmpty() && qm.isEmpty()){
            HashSet<Integer> tmp = new HashSet();
            for (int i = 0; i < k;i++)
                tmp.add(ss[i]);
            sets.add(tmp);
            return;
        }
        int i=0;
        if (!qm.isEmpty()){
            delt = size;
            for (int j = 0;j < size; j++){
                if (qm.contains(j)){
                    HashSet<Integer> tmp = new HashSet();
                    tmp.addAll(a.get(j));
                    tmp.retainAll(qp);
                    if (tmp.size()<delt){
                        i = j;
                        delt = tmp.size();
                    }
                    HashSet<Integer> tmp2 = new HashSet();
                    tmp2.addAll(a.get(i));
                    gg.retainAll(qp);
                }
                    
            }
        }else
            gg.addAll(qp);
        i=0;
        while (i<size){
            if (gg.contains(i)){
                ss[k] = i;
                HashSet<Integer> s1 = new HashSet();
                HashSet<Integer> s2 = new HashSet();
                s1.addAll(qp);
                s2.addAll(qm);
                s1.removeAll(a.get(i));
                s1.remove(i);
                s2.removeAll(a.get(i));
                s2.remove(i);
                Find(k+1,s1,s2);
                qp.remove(i);
                qm.add(i);
                gg = new HashSet();
                gg.addAll(qp);
                gg.retainAll(a.get(i));
            }
            i++;
        }
    }
    
    public void backtrack(int i) {
        if (i>=sets.size())
            return;
        if (Solve.size() < bestSolve.size()) {
            Solve.add(i);
            if (!Check(Solve)) {
                backtrack(i + 1);
            } else {
                if (Solve.size() <= bestSolve.size()) {
                    bestSolve = new ArrayList();
                    for (int s : Solve) {
                        bestSolve.add(s);
                    }
                }
            }
            Solve.remove((Object) i);
        }
        if (!Check(Solve)) {
            backtrack(i + 1);
        } else {
            if (Solve.size() <= bestSolve.size()) {
                bestSolve = new ArrayList();
                for (int s : Solve) {
                    bestSolve.add(s);
                }
            }
        }
    }
    
    /*public void backtrack(int i){
        if (i >= sets.size()+1)
            return;
        if (Check(Solve)){
            if (Solve.size()<bestSolve.size() || bestSolve.isEmpty()){
                bestSolve = new ArrayList();
                for (int s : Solve)
                    bestSolve.add(s);
            }
        }else{
            for (int j = 1; j < sets.size();j++){
                Solve.add(i);
                backtrack(i+j);
                Solve.remove((Object)i);
            }
        }    
    }*/
    
    public boolean Check(ArrayList<Integer> slv){
        HashSet<Integer> tmp = new HashSet();
        for (int i : slv){
            tmp.addAll(sets.get(i));
        }
        return nodes.equals(tmp);
    }
    
    public void formSolve(){
        int col = 0;
        vec = new Solve();
        for (int i = 0; i < size; i++)
            vec.Colors.add(0);
        for (int a : bestSolve){
            HashSet<Integer> set = sets.get(a);
            for (int i = 0; i < size; i++)
                if (set.contains(i))
                    vec.Colors.set(i, col);
            col++;
        }
    }
    
    public Solve ExactAlg(){       
        formGraph();
        HashSet<Integer> qp = new HashSet();
        HashSet<Integer> qm = new HashSet();
        for (int i = 0; i <size;i++)
            qp.add(i);
        Find(0,qp,qm);
        for (HashSet<Integer> mas : sets){
            for (int el : mas){
                System.out.print(el + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
        //for (int i = 0; i < sets.size();i++)
          //  backtrack(i);
        for (int i = 0;i<sets.size();i++)
            bestSolve.add(i);
        backtrack(0);
        formSolve();
        return vec;
    }
    /*int[][] matr;
    int N;
    int maxcolor = 8;
    //ArrayList<Integer> Solve;
    //ArrayList<Integer> bestSolve;
    //int[] Colr={0,1,2,3,4,5,6,7};
    Circle[] points;
    Graphics g;
    private final Color[] color = {Color.green, Color.red, Color.blue, Color.yellow, Color.magenta, Color.orange, Color.pink, Color.cyan};

    exact(int N, int[][] m, Circle[] points,Graphics g) {
        this.matr = m;
        this.N = N;
        //Solve = new ArrayList();
        this.points = points;
        this.g = g;
    }
    
    //public int maxClr;
    public int[] nodecolors;
    public int[] bestNodecolors;
    

    


    public void visit(int i,int col){
       if (i==N){
            for (int j=0;j<this.nodecolors.length;j++)
                this.bestNodecolors[j] = nodecolors[j];
        }else{
            for (int c = 0;c<col;c++){
                if (Check(i,c)){
                    int tmp = nodecolors[i];
                    nodecolors[i] = c;
                    visit(i+1,col);
                    nodecolors[i] = tmp;
                }
            }
        }
    }
    
    public boolean Check(int i,int col){
        for (int j = 0; j < N; j++) {
                if (i!=j && matr[i][j] == 1) {
                    if (nodecolors[j]==col)
                        return false;
                }
        }
        return true;
    }
    
    public int Correct(){
        HashSet<Integer> ws = new HashSet();
            for (int k = 0; k < bestNodecolors.length; k++) {
                if (bestNodecolors[k] == -1) {
                    return -1;
                }else
                    ws.add(bestNodecolors[k]);
            }
            return ws.size();
    }
    
    public int ExactAlg() {
        nodecolors = new int[N];
        bestNodecolors = new int[N];
        for (int i = 0; i < N; i++) {
            nodecolors[i] = -1;
            bestNodecolors[i] = -1;
        }
        int value=-1;
        for (int i = 1; i < maxcolor && value==-1; i++) {
            visit(0, i);
            value = Correct();
        }
        for (int i = 0; i < bestNodecolors.length; i++) {
            if (points[i] != null) {
                points[i].setColor(g, color[bestNodecolors[i]%8]);
            }
        }
        g.setColor(Color.black);
        return value;
    }*/
    
    /*public static final int maxN = 10;
    int[][] bl;
    int[][] a;
    int[][] a1;
    int N;
    boolean[] sBetter;
    int pBetter;
    boolean[] s;
    int p;
    boolean[] r;
    int price[];
    
    public exact(int N,int[][] a){
        this.N = N;
        this.a = a;
        bl = new int[maxN][maxN+1];
        price = new int[maxN];
        sBetter = new boolean[maxN];
        s = new boolean[maxN];
        r = new boolean[maxN];
        a1 = new int[maxN][maxN];
    }
    
    
    public void Sort(){
        
    }
    
    public void Press(int i, int j){
        int k;
        k = j;
        while (bl[i][k]!=0){
            bl[i][k] = bl[i][k+1];
            k++;
        }
    }
    
    public void Blocs(){
        for (int i = 1;i < maxN;i++)
            for (int j = 1;j < maxN+1;j++)
                bl[i][j] = 0;
        for (int i = 1;i <= N;i++)
            bl[1][i] = i;
        for (int i = 1; i <= N-1; i++){
            int j = 1;
            int cnt = 0;
            while (bl[i][j]!=0){
                if (a1[i][bl[i][j]]==0){
                    cnt++;
                    bl[i+1][cnt] = bl[i][j];
                    Press(i,j);
                    j--;
                }
                j++;
            }
        }
        Sort();
    }
    
    public void Include(int k){
        p = p + price[k];
        s[k] = true;
        for (int j = 1; j <= N; j++){
            if (a1[j][k]==1)
                r[j] = true;
        }
    }
    
    public void Exclude(int k){
        p = p - price[k];
        s[k] = false;
        for (int j = 1; j <= N; j++){
            if (a1[j][k]==1 && r[j])
                r[j] = false;
        }        
    }
    
    public boolean Result(){
        int j = 1;
        while (j <= N && r[j])
            j++;
        return j==N+1;
    }
    
    public boolean Cross(int k){
        int j = 1;
        while (j<=N && !(r[j] && (a1[j][k]==1)))
            j++;
        return j==N+1;
    }
    
    public void Find (int bloc, int jnd){
        if (Result()){
            if (p<pBetter){
                pBetter = p;
                sBetter = s;
            }
        }else
            if (bl[bloc][jnd]==0)
                return;
            else{
                if (Cross(bl[bloc][jnd])){
                    Include(bl[bloc][jnd]);
                    Find(bloc+1,1);
                    Exclude(bl[bloc][jnd]);
                }else
                    if (r[bloc])
                        Find(bloc+1,1);
                    else
                        Find(bloc,jnd+1);
            }
    }
    
    public void Init(){
        for (int i = 1; i<=N;i++)
            for (int j = 1; j<=N;j++)
                a1[i][j] = a[i-1][j-1];
        for (int i = 1; i <= N; i++){
            a1[i][i] = 1;
        }
        for (int i = 0; i < maxN;i++)
            price[i] = 1;
        pBetter = Integer.MAX_VALUE;
    }
    
    public void Print(){
        
    }
    
    public void ExactAlg(){
        Init();
        Blocs();
        Find(1,1);
        Print();
    }*/
}
