package ro.usv.rf;

import java.util.Arrays;

public class DistanceMatrix {
    private double[][] matDist;
    public DistanceMatrix(double[][] pattern){
        matDist=new double[pattern.length][pattern.length];
        for(int i=0;i<pattern.length;i++){
            for(int j=0;j<pattern.length;j++)
                matDist[i][j]=DistanceUtils.distEuclid(pattern[i],pattern[j] );
        }
    }
    @Override
    public String toString() {
        String afisare=new String();
        for(int i=0;i<matDist.length;i++){
            for(int j=0;j<matDist.length;j++)
                afisare+=matDist[i][j]+" ";
            afisare+=("\n");
        }
        return afisare;
    }
    double[][] neighbors(int i){
        double [][] vecini=new double[2][matDist.length];
        double [][] sortat=new double[2][matDist.length];
            for(int k=0;k< matDist.length;k++) {
                vecini[0][k] = k;
                vecini[1][k] =matDist[i][k];
            }
            double aux;
            for(int j=0;j<matDist.length;j++){
                for(int k=0;k< matDist.length;k++)
                    if(vecini[1][k]>vecini[1][j]){
                        aux=vecini[1][k];
                        vecini[1][k]=vecini[1][j];
                        vecini[1][j]=aux;
                        aux=vecini[0][k];
                        vecini[0][k]=vecini[0][j];
                        vecini[0][j]=aux;
                    }
            }

            return vecini;
        }

}
