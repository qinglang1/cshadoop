import java.util.Objects;

public class person implements Comparable{

    private double height ;
    private double weight ;
    private int age ;
    private String blood;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public person() {
    }

    public person(double height, double weight, int age, String blood) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.blood = blood;
    }



    @Override
    public int hashCode() {

      int a1=((byte)height&0xff)<<24;
      int a2=((byte)weight&0xff)<<16;
      int a3=((byte)age&0xff)<<8;
      int a4=((byte)blood.hashCode()&0xff)<<0;
      return a1|a2|a3|a4;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        person other = (person) o;
        if ( (this.hashCode()^other.hashCode())==0){
            return  true;
        }
        return false;

    }

    @Override
    public int compareTo(Object o) {
        person other = (person) o;
         double r1=height-other.height;
         double r2=(r1==0) ? weight-other.weight:r1;
         double r3=(r2==0) ? age-other.age:r2;
         double r4=(r3==0) ? blood.hashCode()-other.blood.hashCode():r3;
         return (int)r4;
    }
}
