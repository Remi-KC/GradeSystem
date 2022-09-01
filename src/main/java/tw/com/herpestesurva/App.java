package tw.com.herpestesurva;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("中文測試");
        System.out.println("Hello World!");
        GradeSystem system = new GradeSystem();
        system.run(true);

        try {
            String chinese = "a";
            double score = Double.valueOf(chinese);
            System.out.println(score);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}
