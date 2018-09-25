package dongtaidaili;

public class welcomserviceimpl implements  welcomservice,welcomservice2 {
    @Override
    public void sayhello(String msg) {
        System.out.println("hello: "+msg );
    }


    @Override
    public void sayhello2(String msg) {
        System.out.println("你好: "+msg);

    }
}
