public class njiejuzheng {
    public static void main(String[] args) {
//        int arow=3;
//        int acol=3;
//        int bcol=4;
//        int[][] a = new int[arow][acol];
//        int[][] b=new int[acol][bcol];
//        int[][] c=new int[arow][bcol];


      int[][] a= {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}};
      int[][] b={{3,3,3,3},{4,4,4,4},{5,5,5,5}};
      int[][] c=new int[3][4];

        /**
         * aij  bjk  cik
         * cik=ai0*b0k+ai1*b1k+ai2.b2k
         * 求cik时 ai 行不变 bk 列不变，变的是a的列，b的行   重要
         *
         */

        for (int i = 0; i <a.length ; i++) {
            for (int k = 0; k<b[1].length ; k++) {
                c[i][k]=0;
                for (int j = 0; j <a[1].length ; j++) {
                    c[i][k]+=a[i][j]*b[j][k];
                }
               // System.out.println(c[i][k]);

            }
        }
        
        //遍历二维数组c中的元素
        for (int i = 0; i <c.length ; i++) {
            for (int k = 0; k <c[i].length ; k++) {
                System.out.print(c[i][k]+"\t");
            }
            System.out.println();
        }
    }

}
