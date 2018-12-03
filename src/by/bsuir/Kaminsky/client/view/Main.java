package by.bsuir.Kaminsky.client.view;

import by.bsuir.Kaminsky.client.controller.Controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
	
	private static final int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName("localhost");
        ObjectOutputStream out;
        ObjectInputStream in;
        Controller controller = new Controller();

        System.out.println(addr);
        try (Socket socket = new Socket(addr, PORT)) {
            System.out.println("<create> SOCKET : " + socket);
            out = new ObjectOutputStream (socket.getOutputStream());
            in = new ObjectInputStream (socket.getInputStream());

            while (true){
                //input command
                String command = controller.waitCommand();
                //send command to server
                out.reset();
                out.writeObject(controller.performCommand(command));
                out.flush();
                //get answer from server
                XmlCollection result = (XmlCollection) in.readObject();
                System.out.println(controller.getAnswer(result));
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("<end> CLOSING SOCKET");
        }

    }
}
