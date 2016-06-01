package funcional;

public class Matriz {

    public void printMatriz(int matriz[][]) {
        if (matriz == null) {
            System.out.println("MATRIZ VACIA");
            return;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public void printMatriz(double matriz[][]) {
        if (matriz == null) {
            System.out.println("MATRIZ VACIA");
            return;
        }
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public double[][] sacarSubMatriz(double matriz[][], double x, int y) {
        double submatriz[][] = new double[matriz.length - 1][matriz.length - 1];
        int i, j, cur_x = 0, cur_y = 0;
        for (i = 0; i < matriz.length; i++) {
            if (i != x) {
                cur_y = 0;
                for (j = 0; j < matriz.length; j++) {
                    if (j != y) {
                        submatriz[cur_x][cur_y] = matriz[i][j];
                        cur_y++;
                    }
                }
                cur_x++;
            }
        }
        return submatriz;
    }

    public double calcularDeterminante(double matriz[][]) {
        double deter = 0;
        int i, mult = 1;
        matriz = cuadrar(matriz);
        if (matriz.length > 2) {
            for (i = 0; i < matriz.length; i++) {
                deter += mult * matriz[i][0] * calcularDeterminante(sacarSubMatriz(matriz, i, 0));
                mult *= -1;
            }
            return deter;
        } else {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }
    }

    public int[][] sumar(int matriz1[][], int matriz2[][]) {
        int resultado[][] = null;
        if (matriz1.length == matriz2.length && matriz1[0].length == matriz2[0].length) {
            resultado = new int[matriz1.length][matriz1[0].length];
            for (int i = 0; i < matriz1.length; i++) {
                for (int j = 0; j < matriz1[i].length; j++) {
                    int suma = matriz1[i][j] + matriz2[i][j];
                    resultado[i][j] = suma;
                }
            }
        } else {
            System.out.println("LAS DIMENSIONES DE LAS MATRICEZ SON IMCOMPATIBLES PARA SUMARLAS");
        }
        return resultado;
    }

    public double[] sumar(double v1[], double v2[]) {
        double r[] = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            r[i] = v1[i] + v2[i];
        }
        return r;
    }

    public int[][] restar(int matriz1[][], int matriz2[][]) {
        int resultado[][] = null;
        if (matriz1.length == matriz2.length && matriz1[0].length == matriz2[0].length) {
            resultado = new int[matriz1.length][matriz1[0].length];
            for (int i = 0; i < matriz1.length; i++) {
                for (int j = 0; j < matriz1[i].length; j++) {
                    int resta = matriz1[i][j] - matriz2[i][j];
                    resultado[i][j] = resta;
                }
            }
        } else {
            System.out.println("LAS DIMENSIONES DE LAS MATRICEZ SON IMCOMPATIBLES PARA RESTARLAS");
        }
        return resultado;
    }

    public double[] restar(double v1[], double v2[]) {
        double r[] = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            r[i] = v1[i] - v2[i];
        }
        return r;
    }

    public double[][] multiplicar(double f, double matriz[][]) {
        double resultado[][] = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                resultado[i][j] = matriz[i][j] * f;
            }
        }
        return resultado;
    }

    public double[][] multiplicar(float matriz1[][], double matriz2[][]) {
        double resultado[][] = null;
        if (matriz1[0].length == matriz2.length) {
            resultado = new double[matriz1.length][matriz2[0].length];
            for (int i = 0; i < matriz1.length; i++) {
                for (int j = 0; j < matriz2[0].length; j++) {
                    double total = 0;
                    for (int k = 0; k < matriz2.length; k++) {
                        total = total + matriz1[i][k] * matriz2[k][j];
                    }
                    resultado[i][j] = total;
                }
            }
        } else {
            System.out.println("LAS DIMENSIONES DE LAS MATRICEZ SON INCOMPATIBLES PARA MULTIPLICARLAS");
        }
        return resultado;
    }

    public double[] multiplicar(double vector[], double matriz[][]) {
        double resultado[] = new double[matriz[0].length];
        if (vector.length == matriz.length) {
            int contador = 0;
            for (int i = 0; i < matriz[0].length; i++) {
                double total = 0;
                for (int j = 0; j < vector.length; j++) {
                    total = total + vector[j] * matriz[j][i];
                }
                resultado[contador] = total;
                contador++;
            }
        } else {
            System.out.println("LAS DIMENSIONES DE LAS MATRICEZ SON INCOMPATIBLES PARA MULTIPLICARLAS");
        }
        return resultado;
    }

    public double[][] pasarADouble(int matriz[][]) {
        double m[][] = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                m[i][j] = (double) matriz[i][j];
            }
        }
        return m;
    }

    public double[] pasarADouble(int matriz[]) {
        double m[] = new double[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            m[i] = (double) matriz[i];
        }
        return m;
    }

    public double[][] matrizCofactores(double[][] matriz) {
        double[][] nm = new double[matriz.length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                double[][] det = new double[matriz.length - 1][matriz.length - 1];
                double detValor;
                for (int k = 0; k < matriz.length; k++) {
                    if (k != i) {
                        for (int l = 0; l < matriz.length; l++) {
                            if (l != j) {
                                int indice1 = k < i ? k : k - 1;
                                int indice2 = l < j ? l : l - 1;
                                det[indice1][indice2] = matriz[k][l];
                            }
                        }
                    }
                }
                detValor = calcularDeterminante(det);
                nm[i][j] = detValor * (double) Math.pow(-1, i + j + 2);
            }
        }
        return nm;
    }


    public double[][] cuadrar(double[][] matriz) {
        if (matriz.length == matriz[0].length) {
            return matriz;
        } else {
            int filas = matriz.length;
            int columnas = matriz[0].length;
            double resultado[][] = new double[1][1];
            if (filas > columnas) {
                int c = filas - columnas;
                resultado = new double[filas][columnas + c];
            } else {
                int f = columnas - filas;
                resultado = new double[filas + f][columnas];
            }
            for (int i = 0; i < resultado.length; i++) {
                for (int j = 0; j < resultado[i].length; j++) {
                    if (i < filas && j < columnas) {
                        resultado[i][j] = matriz[i][j];
                    } else {
                        resultado[i][j] = 0;
                    }
                }
            }
            return resultado;
        }
    }

    public double[][] matrizAdjunta(double[][] matriz) {
        return matrizTranspuesta(matrizCofactores(matriz));
    }

    public double[][] matrizTranspuesta(double[][] matriz) {
        double[][] nuevam = new double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                nuevam[i][j] = matriz[j][i];
            }
        }
        return nuevam;
    }

    public double[] Gauss_Jordan1(double m[][], double[] r) {
        for (int i = 0; i < r.length; i++) {
            double d, c = 0;
            d = m[i][i];
            if (d != 0) {
                for (int s = 0; s < r.length; s++) {
                    m[i][s] = ((m[i][s]) / d);
                }
                r[i] = (((r[i]) / d));
                for (int x = 0; x <= r.length - 1; x++) {
                    if (i != x) {
                        c = m[x][i];
                        for (int y = 0; y <= r.length - 1; y++) {
                            m[x][y] = m[x][y] - c * m[i][y];
                        }
                        r[x] = (r[x] - c * r[i]);
                    }
                }
            }
        }
        return r;
    }

    public double[][] invertirMatriz(double m[][]) {
        double r[][] = new double[1][1];
        try {
            r = new double[m[0].length][m.length];
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    r[i][j] = m[j][i];
                }
            }
            return r;
        } catch (Exception e) {
            System.out.println("error");
        }
        return r;
    }
    
    public boolean compararVectores(double vector1[],double vector2[]){        
        if(vector1.length != vector2.length) return false;
        int i=0;
        for(double numero : vector1){
            if(numero != vector2[i])
                return false;
            i++;
        }
        return true;
    }
    
    public void printVector(double[] vector){
        System.out.print("( ");
        for(double numero : vector)
            System.out.print(numero + " ");
        System.out.println(" )");
    }
    
    public int[] pasarAEntero(double[] vector){
        int vectorInt[]=new int[vector.length];
        for(int i=0;i < vector.length;i++){
            vectorInt[i]=(int) vector[i];
        }
        return vectorInt;
    }
}
