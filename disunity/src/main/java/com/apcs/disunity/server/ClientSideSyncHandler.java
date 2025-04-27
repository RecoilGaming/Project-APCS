package com.apcs.disunity.server;

import com.apcs.disunity.Options;
import com.apcs.disunity.ThrottledLoopThread;
import com.apcs.disunity.input.Inputs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

import static com.apcs.disunity.server.CODEC.decodeInt;

public class ClientSideSyncHandler extends SyncHandler implements Closeable {

    private final PacketTransceiver transceiver;
    private final Thread recieverThread;
    private final Thread senderThread;
    private final Client client;

    public ClientSideSyncHandler(String host, int port) throws IOException {
        client = new Client(host, port);
        transceiver = client.getTransceiver();

        recieverThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                distribute(transceiver.recieve());
            }
        });

        senderThread = new ThrottledLoopThread(
            Options.getMSPP(),
            () -> {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Inputs.runtimeInstance().encode(out);
                transceiver.send(out.toByteArray());
            },
            ()->{}
        );
    }

    protected void distribute(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            for (Object sync : syncs) {

                int size = decodeInt(in);
                byte[] nodePacket = new byte[size];
                int numBytes = in.read(nodePacket);
                if (numBytes == -1 && size != 0) throw new RuntimeException("packet was smaller than expected.");

                ByteArrayInputStream packetStream = new ByteArrayInputStream(nodePacket);
                CODEC.decodeObject(sync, packetStream);
                if(packetStream.available() != 0) throw new RuntimeException("synced object did not consume all contents of packet");
            }
            if(in.available() != 0) throw new RuntimeException("client did not consume all contents of packet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        senderThread.start();
        recieverThread.start();
    }


    @Override
    public void close() throws IOException {
        client.close();
        recieverThread.interrupt();
        senderThread.interrupt();
    }

    @Override
    public int getEndpointId() {
        return client.id();
    }
}
