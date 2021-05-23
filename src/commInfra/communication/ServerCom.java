package commInfra.communication;

import java.io.*;
import java.net.*;

/**
 * Server Communication
 *
 * <p>
 *      This data type implements the server-side communication channel for a communication based on passing message over sockets using the TCP protocol.
 *      The data transfer is object-based, one object at a time.
 * </p>
 *
 * @author António Rui De Oliveira E Silva Borges
 */

public class ServerCom
{
    /**
     *  Socket de escuta
     *    @serialField listeningSocket
     */

    private ServerSocket listeningSocket = null;

    /**
     *  Socket de comunicação
     *    @serialField commSocket
     */

    private Socket commSocket = null;

    /**
     *  Número do port de escuta do servidor
     *    @serialField serverPortNumb
     */

    private int serverPortNumb;

    /**
     *  Stream de entrada do canal de comunicação
     *    @serialField in
     */

    private ObjectInputStream in = null;

    /**
     *  Stream de saída do canal de comunicação
     *    @serialField out
     */

    private ObjectOutputStream out = null;

    /**
     *  Instantiation of a communication channel (form 1).
     *
     *    @param portNumb Number of the server's listening port
     */
    public ServerCom (int portNumb)
    {
        serverPortNumb = portNumb;
    }

    /**
     *  Instantiation of a communication channel (form 2).
     *
     *    @param portNumb Number of the server's listening port
     *    @param lSocket Listening socket
     */
    public ServerCom (int portNumb, ServerSocket lSocket)
    {
        serverPortNumb = portNumb;
        listeningSocket = lSocket;
    }

    /**
     * Establishing the service.
     * Instantiation of a listen socket and its association with the local host address and the public listening port.
     */
    public void start ()
    {
        try
        { listeningSocket = new ServerSocket (serverPortNumb);
            setTimeout (10000);
        }
        catch (BindException e)                         // erro fatal --- port já em uso
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível a associação do socket de escuta ao port: " +
                serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (IOException e)                           // erro fatal --- outras causas
        { System.out.println (Thread.currentThread ().getName () +
                " - ocorreu um erro indeterminado na associação do socket de escuta ao port: " +
                serverPortNumb + "!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     * Shutting down the service.
     * Closing the listening socket.
     */
    public void end ()
    {
        try
        { listeningSocket.close ();
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível fechar o socket de escuta!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     * Listening process.
     * Creation of a communication channel for a pending request.
     * Instantiation of a communication socket and its association with the client address.
     * Opening the socket's input and output streams.
     *
     * @return communication channel
     */
    public ServerCom accept () throws SocketTimeoutException
    {
        ServerCom scon;                                      // canal de comunicação

        scon = new ServerCom(serverPortNumb, listeningSocket);
        try
        { scon.commSocket = listeningSocket.accept();
        }
        catch (SocketTimeoutException e)
        { throw new SocketTimeoutException ("Timeout!");
        }
        catch (SocketException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - foi fechado o socket de escuta durante o processo de escuta!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível abrir um canal de comunicação para um pedido pendente!");
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { scon.in = new ObjectInputStream (scon.commSocket.getInputStream ());
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível abrir o canal de entrada do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { scon.out = new ObjectOutputStream (scon.commSocket.getOutputStream ());
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível abrir o canal de saída do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        return scon;
    }

    /**
     * Close the communication channel.
     * Close the input and output streams of the socket.
     * Close the communication socket.
     */
    public void close ()
    {
        try
        { in.close();
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível fechar o canal de entrada do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { out.close();
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível fechar o canal de saída do socket!");
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { commSocket.close();
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - não foi possível fechar o socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     *  Defining a listening time out.
     */
    public void setTimeout (int time)
    {
        try
        { listeningSocket.setSoTimeout (time);
        }
        catch (SocketException e)
        {
        }
    }

    /**
     * Reading an object from the communication channel.
     *
     * @return object read
     */
    public Object readObject ()
    {
        Object fromClient = null;                            // objecto

        try
        { fromClient = in.readObject ();
        }
        catch (InvalidClassException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - o objecto lido não é passível de desserialização!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - erro na leitura de um objecto do canal de entrada do socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (ClassNotFoundException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - o objecto lido corresponde a um tipo de dados desconhecido!");
            e.printStackTrace ();
            System.exit (1);
        }

        return fromClient;
    }

    /**
     * Writing an object to the communication channel.
     *
     * @param toClient object to be written
     */
    public void writeObject (Object toClient)
    {
        try
        { out.writeObject (toClient);
        }
        catch (InvalidClassException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - o objecto a ser escrito não é passível de serialização!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotSerializableException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - o objecto a ser escrito pertence a um tipo de dados não serializável!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (IOException e)
        { System.out.println (Thread.currentThread ().getName () +
                " - erro na escrita de um objecto do canal de saída do socket de comunicação!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
}
