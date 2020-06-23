public interface authServ {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}