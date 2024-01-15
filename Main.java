import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread clientThread = new Thread(() -> { //vlákno pro odesílání zpráv
            try {
                Client client = new Client("10.0.0.1", 8000); //ip a port s kým chcete komunikovat na jakym portu
                String message;
                BufferedReader consoleInputStream = new BufferedReader(new InputStreamReader(System.in));

                do {
                    message = consoleInputStream.readLine();
                    client.sendMessage(message);
                } while (!message.equalsIgnoreCase("exit")); //exit ukončuje komunikaci

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread serverThread = new Thread(() -> { //vlákno pro příjem
            try {
                Server server = new Server(8010); //port na kterém budu příjmat
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start(); //spuštění vláken
        clientThread.start();

        serverThread.join();
        clientThread.join();
    }
}
