import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class StatePusher {
    public static void main(String[] args) throws IOException {
        final String pluginname = "tpsc"; // plugin id
        final String statename = "tpsc_state"; // state id
        final String[] validstates = {"closed","started","viewing","editing"};
        final String hostName = "127.0.0.1";
        final int portNumber = 12136;
        String stateUpdate = "none";

        // Check whether proper arguments is given.
        if(args.length != 0) // more than 0 args
        {
          for(int i=0;i<validstates.length;i++) // whether args[0] is in valid states
          {
            if(args[0].equals(validstates[i]))
            {
              stateUpdate = args[0]; // note: all other args discarded
            }
          }
        }

        // Abort if improper arguments.
        if(stateUpdate == "none")
        {
          System.out.println("Error, no/wrong argument. Possible arguments are:");
          for (String i : validstates)
          {
              System.out.println(i);
          }
          System.exit(1);
        }

        // Construing json packages to be sent
        String pairJSON = "{\"type\":\"pair\",\"id\":\"" + pluginname + "\"}\n";
        String stateJSON = "{\"type\":\"stateUpdate\",\"id\":\"" + statename + "\",\"value\":\"" + stateUpdate + "\"}\n";

        // need host and port, we want to connect to TP
        System.out.println("Data constructed. Opening socket...");
        Socket socket = new Socket(hostName, portNumber);
        System.out.println("Connected!");

        // get the output stream from the socket.
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        System.out.println("Sending strings to the Touch Portal:");

        // write the message we want to send and send the message x2
        System.out.println("pairing msg: " + pairJSON);
        dataOutputStream.writeUTF(pairJSON);
        dataOutputStream.flush();

        System.out.println("update msg: " + stateJSON);
        dataOutputStream.writeUTF(stateJSON);
        dataOutputStream.flush();

        // close the output stream when we're done.
        dataOutputStream.close();

        System.out.println("Closing socket and terminating program.");
        socket.close();
    }
}
