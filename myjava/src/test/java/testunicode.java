import org.junit.Test;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class testunicode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str="a中b";
        char c1='a';
        char c2='中';
        char c3='b';

        byte[] bytes = str.getBytes("unicode");

        System.out.println(bytes);
        for (byte by : bytes) {
            System.out.println(by);

        }


    }

    @Test
    //jvm使用的是unicode编码，每两个字节表示1个字符
    public  void  stringtounicode() throws UnsupportedEncodingException {

        char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        String str="a中b";

        StringBuffer sb = new StringBuffer();
        //byte[] bytes = str.getBytes("unicode");
        for (int i = 0; i <str.length() ; i++) {

            String a="\\u";
            char c = str.charAt(i);
            //unicode编码两个字节表示一个字符
            char c1 = chars[ (int) c >> 12 & 0xf];
            char c2 = chars[ (int) c >> 8 & 0xf];
            char c3 = chars[ (int) c >> 4 & 0xf];
            char c4 = chars[ (int) c >> 0 & 0xf];

            sb.append(a).append(c1).append(c2).append(c3).append(c4);

        }

        sb.toString();
        System.out.println(sb);



    }
  //jvm使用的是unicode编码，每两个字节表示1个字符
    @Test
    public  void md5bukeni() throws NoSuchAlgorithmException {
        char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        String c="a中2";
        byte[] bytes = c.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] digest= md5.digest(bytes);//digest为16个字节的字节数组；
       // System.out.println( new String(digest));
        StringBuffer sb = new StringBuffer();
        
        for (byte b : digest) {
            char c1 = chars[ b >> 4 & 0xf];
            sb.append(c1);
            char c2 = chars[ b >> 4 & 0xf];
            sb.append(c2);
        }

        String s = sb.toString();
        System.out.println(s);

    }
    
    


    @Test
    public void  outallchars() {
        int col =0;
        for (int i = 1; i <0xffff ; i++) {

            System.out.printf("%d:%s\t",i,(char)i);

             col++;
             if (col>10){
                 System.out.println();
                 col=0;


             }

        }

    }



@Test
    public  void  shiliujinzhi() {

       


    }

    @Test
    public  void read8888() throws IOException {

        InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\Users\\Envy\\Desktop\\临时文件\\8888.txt"), "unicode");

        char[] chars = new char[30];

        int len = -1;

        while ((len = isr.read(chars)) != -1) {

            System.out.println(new String(chars));

        }

    }


 @Test
    public void longtobyte(){
        long l=(long)256;
     byte[] bytes = new byte[8];
     for (int i = 0; i <bytes.length ; i++) {
         bytes[i]=(byte)(l>>(56-8*i));
     }
     for (byte b : bytes) {
         System.out.println(b);
     }


     }


@Test
    public void bytetolong() {

        byte[] bytes = new byte[8];
        //bytes代表的数为256
        for (int i = 0; i <bytes.length ; i++) {

            if(i==6) {
             bytes[i]=1;

            }else {
                bytes[i] = 0;
            }
        }

        long[] longs = new long[8];

//    longs[0] = (((long) bytes[0]) & 0xff )<< 56;
//    longs[1] = (((long) bytes[1]) & 0xff )<< 48;
//    longs[3] = (((long) bytes[3]) & 0xff )<< 32;
//    longs[4] = (((long) bytes[4]) & 0xff )<< 24;
//    longs[5] = (((long) bytes[5]) & 0xff )<< 16;
//    longs[2] = (((long) bytes[2]) & 0xff )<< 40;
//    longs[6] = (((long) bytes[6]) & 0xff )<< 8;
//    longs[7] = (((long) bytes[7]) & 0xff )<< 0;
//


        for (int i = 0; i <bytes.length ; i++) {

             longs[i] = (((long) bytes[i]) & 0xff ) << (8 *(bytes.length-1-i));
        }

        long l = longs[0] | longs[1] | longs[2] | longs[3] | longs[4] | longs[5] | longs[6] | longs[7];
        System.out.println(l);

    }


    @Test
   public  void tongji5yi(){
        // 5,0000,0000/8/1024/1024=59.605m
        //21,4748,3647/8=2,6843,5455.875byte=255.99m;
        byte[] bytes = new byte[270000000];
       int count=0;
        Random r = new Random();
        for (int i = 0; i <100 ; i++) {


            int random = r.nextInt(Integer.MAX_VALUE);
           // int random = r.nextInt(3);

            System.out.println(random);
            int shang = random / 8;

            switch (random % 8) {
                case 0:
                    if ((bytes[shang]>>>7)%2!=1) {
                        bytes[shang] = (byte) (bytes[shang] | 0x80);
                        count++;
                    }
                    break;

                case 1:
                    if ((bytes[shang]>>>6)%2!=1) {
                    bytes[shang] = (byte) (bytes[shang] | 0x40);
                        count++;
                    }
                    break;

                case 2:
                    if ((bytes[shang]>>>5)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x20);
                        count++;
                    }
                    break;

                case 3:
                    if ((bytes[shang]>>>4)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x10);
                          count++;
                      }
                    break;

                case 4:
                    if ((bytes[shang]>>>3)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x08);
                               count++;
                      }
                    break;

                case 5:
                    if ((bytes[shang]>>>2)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x04);
                             count++;
                            }
                    break;

                case 6:
                    if ((bytes[shang]>>>1)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x02);
                            count++; 
                    }
                    break;

                default:
                    if ((bytes[shang]>>>0)%2!=1) {

                        bytes[shang] = (byte) (bytes[shang] | 0x01);
                            count++;
                           }
                    break;


            }
            
            
        }
        System.out.println("不同整数的个数为："+count);


    }


    @Test
    public  void shigezijie() throws IOException {

     //   OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("d:/ceshi/bianma/chakanzijie.txt"),"unicode");
     //   String str="abc";
//
//       osw.write("ab");
//        osw.write("c");
//        osw.close();

//        osw.write((byte)-2);
//        osw.write((byte)-1);
//        osw.write((byte)0);
//        osw.write((byte)97);
//        osw.write((byte)0);
//        osw.write((byte)98);
//        osw.write((byte)-2);
 //       osw.write((byte)-1);
//        osw.write((byte)0);
//        osw.write((byte)99);
 //       osw.write("c");
 //       osw.close();

        FileOutputStream osw = new FileOutputStream("d:/ceshi/bianma/chakanzijie.txt");
        osw.write((byte)-2);
        osw.write((byte)-1);
        osw.write((byte)0);
        osw.write((byte)97);
        osw.write((byte)0);
        osw.write((byte)98);
        osw.write((byte)-2);
        osw.write((byte)-1);
        osw.write((byte)0);
        osw.write((byte)99);
        osw.close();


        InputStreamReader isr = new InputStreamReader(new FileInputStream("d:/ceshi/bianma/chakanzijie.txt"),"unicode");

        char[] chars = new char[30];

        int len=-1;
        while ((len=isr.read(chars))!=-1){

            System.out.println(chars);
        }

    }

    @Test
    public  void  bytequanbianzheng(){

        byte b=-1;
        int i = b & 0xff;
        System.out.println("转换之后的数为："+i);
        System.out.println("该数原本为"+(byte)i);


    }

@Test
    public  void shiliujinzhicunchu(){
        char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    
    int a=1;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i <8 ; i++) {
        int l = (a >> (4 * (8 - 1 - i))) & 0xf;
        char c = chars[l];
        sb.append(c) ;
    }

    String s = sb.toString();
    System.out.println(a + "在内存中的十六进制存储形态：" + s);


}



    @Test
    public  void erjinzhicunchu(){
        String[] strings={"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};

        int a=2;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <8 ; i++) {
            int l = (a >> (4 * (8 - 1 - i))) & 0xf;
            String str = strings[l];
            sb.append(str) ;
        }

        String s = sb.toString();
        System.out.println(a + "在内存中的二进制存储形态：" + s);


    }











}









