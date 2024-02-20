public class Echo implements Command {

    @Override
    public String execute(String input) {
        // TODO Auto-generated method stub
        System.out.println("Echo: " + input);
        return "+"+input+"\r\n";
    }
    
}
