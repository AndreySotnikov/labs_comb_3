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
    int N;
    int maxcolor = 8;
    ArrayList<Integer> Solve;
    ArrayList<Integer> bestSolve;
    int[] Colr={0,1,2,3,4,5,6,7};
    Circle[] points;
    Graphics g;
    private final Color[] color = {Color.green, Color.red, Color.blue, Color.yellow, Color.magenta, Color.orange, Color.pink, Color.cyan};

    exact(int N, int[][] m, Circle[] points,Graphics g) {
        this.matr = m;
        this.N = N;
        Solve = new ArrayList();
        this.points = points;
        this.g = g;
    }
    
    public int maxClr;
    public int[] nodecolors;
    public int[] bestNodecolors;
    
   public void init() { 
       nodecolors = new int[N];
       bestNodecolors = new int[N];
    maxClr = N+1;
   for (int i = 0; i < N; i++) {
        nodecolors[i] = -1;
        bestNodecolors[i] = i;
   }
    }
    
    
    public void backtrack(int i, int clr) {
        if (i == N ) {
            if (clr < maxClr) {
                maxClr = clr; //запоминаем расстановку
                for (int k = 0; k < N; k++) {
                    bestNodecolors[i-1] = nodecolors[i-1];
                }
            }
        } else {
            int col = 0;
            for (int j=0;j<N;j++){
                if (matr[i][j]==1) {
                    if (nodecolors[j] != -1) {
                        col++;
                    }
                }
            }
            if (col != 0)
              col++;
                    nodecolors[i] = col;
                    
                    
                    backtrack(i+1, col);
                    nodecolors[i] = -1;
            if (col != 0)
              col--;        
            for (int j = 0; j < N; j++) {
                if (matr[i][j] == 1) {
                    if (nodecolors[j] != -1) {
                        col--;
                    }
                }
            }
        }
    }
    
    public int ValueSolve(ArrayList<Integer> slv) {
            if (checkSolve(slv)) {
                //ArrayList<Integer> Colors = this.Colors;
                HashSet<Integer> tmp = new HashSet();
                for (int i = 0; i < slv.size(); i++) {
                    tmp.add(slv.get(i));
                }
                return tmp.size();
            } else {
                return slv.size() + 1;
            }
        }

    public void visit(int i,int col){
        if (i==N){
            for (int j=0;j<this.nodecolors.length;j++)
                this.bestNodecolors[j] = nodecolors[j];
        }else{
            for (int c = 0;c<col;c++){
                if (Check(i,c)){
                    nodecolors[i] = c;
                    visit(i+1,col);
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
        boolean res = false;
        int value=-1;
        for (int i = 1; i < maxcolor && value==-1; i++) {
            visit(0, i);
            value = Correct();
        }
        for (int i = 0; i < bestNodecolors.length; i++) {
            if (points[i] != null) {
                points[i].setColor(g, color[bestNodecolors[i]]);
            }
            /*init();
             //for (int i = 0; i < N;i++)
             backtrack(0, 0);

        
             for (int i = 0; i < bestNodecolors.length; i++) {
             if (points[i] != null) {
             points[i].setColor(g, color[bestNodecolors[i]]);
             }
             }*/
        }
        g.setColor(Color.black);
        return value;
    }
    
    /*public int ExactAlg() {
        int col = 1;
        boolean res = false;
        while (col < maxcolor && !res) {
            res = formSolve(col);
            col++;
        }
        if (bestSolve != null) {
            for (int i = 0; i < bestSolve.size(); i++) {
                if (points[i] != null) {
                    points[i].setColor(g, color[bestSolve.get(i)]);
                }
            }
            HashSet<Integer> tmp = new HashSet();
            for (int k = 0; k < bestSolve.size(); k++) {
                tmp.add(bestSolve.get(k));
            }
            g.setColor(Color.black);
            return tmp.size();
        }

        g.setColor(Color.black);
        return -1;
    }*/
    
    
    /*public boolean formSolve(int col) {
        int i = 0;
        int[] tmp = new int[col];
        for (int j = 0; j < tmp.length - 1; j++) {
            tmp[j] = 1;
        }
        tmp[tmp.length - 1] = N - (tmp.length - 1);

        for (int k = 0; k < tmp.length; k++) {
            for (int m = 0; m < tmp[k]; m++) {
                Solve.add(k);
            }
        }
        getSolve(tmp, Solve, col);
        Solve.clear();
        if (bestSolve != null) {
            return true;
        }*/
        //33211 j=2 sum = 8 n = 10
        //33311 j=2 sum=9 n =10
        //65 j=1 sum = 11 n =10 
        
        //Получить первоначальное сочетание цветов
        /*tmp[0] = N - (tmp.length - 1);
        for (int j = 1; j < tmp.length; j++) {
            tmp[j] = 1;
        }
        if (tryAddSolve(tmp,col))
                return true;
        
        int sum = 0;
        i=0;    
        if (col!=1)
        while (tmp[i]>=1 && i<col){
            tmp[i]--;
            sum=0;
            //подсчет суммы слагаемых
            for (int k = 0;k<=i;k++)
                sum = sum+tmp[i];
            
            for (int j=i+1;j<col-1;j++){
                int val = tmp[j-1];
                //val = N - sum - j;
                sum +=val; 
                
                while (sum>=N-j+1){
                    sum--;
                    val--;        
                }
                tmp[j] = val;    
            }
            tmp[col-1] = N-sum;
            
            
            i++;
            if (i==col-1){
                i=0;
            }
            if (tryAddSolve(tmp,col))
                return true;
        }
        else{
            if (tryAddSolve(tmp,col))
                return true;
        }
        return false;*/

        /*for (int j = tmp.length - 1; j > 0; j--) {
            while (tmp[j] != 1) {
                for (int k = 0; k < tmp.length; k++) {
                    for (int m = 0; m < tmp[k]; m++) {
                        Solve.add(k);
                    }
                }
                getSolve(tmp, Solve, col);
                Solve.clear();
                if (bestSolve != null) {
                    return true;
                }
                tmp[j]--;
                tmp[j - 1]++;
            }
            for (int k = 0; k < tmp.length; k++) {
                for (int m = 0; m < tmp[k]; m++) {
                    Solve.add(k);
                }
            }
            getSolve(tmp, Solve, col);
            Solve.clear();
            if (bestSolve != null) {
                return true;
            }
        }
        return false;*/
   // }
    
    public boolean tryAddSolve(int[] tmp,int col){
        for (int k = 0; k < tmp.length; k++) {
                    for (int m = 0; m < tmp[k]; m++) {
                        Solve.add(k);
                    }
                }
                getSolve(tmp, Solve, col);
                Solve.clear();
                if (bestSolve != null) {
                    return true;
                }
                else return false;
    }
    
    public void getSolve(int[] tmp ,ArrayList<Integer> slv,int col){
        rec(0,Solve,tmp,col);    
    }
    
    public int rec(int pos,ArrayList<Integer> slv,int[] tmp,int col)
{
    if (pos == slv.size())
        if (checkSolve(slv)){
            bestSolve = new ArrayList();
            for (int i = 0; i < slv.size();i++)
            bestSolve.add(slv.get(i));
        }
        //output();
    for (int i=0;i<col;i++)
    {
        if (tmp[i]>0)
        {
            slv.set(pos,Colr[i]);
            tmp[i]--;
            rec(pos+1,slv,tmp,col);
            tmp[i]++;
        }

    }
    return 0;
}

    public boolean checkSolve(ArrayList<Integer> slv) {
        ArrayList<Integer> ws = new ArrayList();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j != i && matr[i][j] == 1) {
                    ws.add(j);
                }
            }
            for (int k = 0; k < ws.size(); k++) {
                if (i != ws.get(k) && slv.get(i) == slv.get(ws.get(k))) {
                    return false;
                }
            }
            ws.clear();
        }
        return true;
    }
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
