package foxgem;

public class MainRunner {

    public static void main(String[] args) {
        new Service("test", 8080);
        new Service("test", 8081);
        new Consumer("test");
    }

}
