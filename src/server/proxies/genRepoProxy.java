package server.proxies;

import commInfra.communication.ServerCom;
import commInfra.messages.message;
import server.interfaces.genRepoInterface;


/**
 * genRepoProxy
 *
 * <p>
 *     Explain
 * </p>
 *
 * @author Tomás Freitas
 * @author Tiago Almeida
 */

public class genRepoProxy extends Thread {

    private final ServerCom sconi;

    private server.interfaces.genRepoInterface genRepoInterface;

    /**
     * Interface instantiation
     * @param sconi
     * @param genRepoInterface
     */
    public genRepoProxy(ServerCom sconi, genRepoInterface genRepoInterface){
        this.sconi = sconi;
        this.genRepoInterface = genRepoInterface;
    }

    /**
     * Life cycle of SPA Thread
     */
    @Override
    public void run() {
        message in, out;                                    // mensagem de entrada/saida

        in = (message) this.sconi.readObject();

        out = this.genRepoInterface.processAndReply(in);

        this.sconi.writeObject(out);
        this.sconi.close();
    }

    public ServerCom getSconi() {
        return sconi;
    }

}
