public class Main {
    public static void main(String[] args) {
        boolean console = false;

        if(console)
            new ConsoleApp();
        else
            new GuiApp();
    }
}
