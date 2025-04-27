package com.apcs.disunity.server;

import com.apcs.disunity.Options;
import com.apcs.disunity.ThrottledLoopThread;
import com.apcs.disunity.input.Inputs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HostSideSyncHandler extends SyncHandler implements Closeable {

    private final Host host;
    private final List<Thread> threads = new LinkedList<>();

    public HostSideSyncHandler() throws IOException {
        host = new Host();
        host.attachJoinAction((id) -> {
            PacketTransceiver transceiver = host.getTransceiver(id);

            Thread senderThread = new ThrottledLoopThread(
                Options.getMSPP(),
                ()->{},
                () -> transceiver.send(poll())
            );
            Thread recieverThread = new Thread(()-> {
                while (!Thread.currentThread().isInterrupted()) {
                    Inputs.getInstance(id)
                        .decode(new ByteArrayInputStream(transceiver.recieve()));
                }}
            );

            threads.add(recieverThread);
            threads.add(senderThread);

            recieverThread.start();
            senderThread.start();
        });
    }

    private byte[] poll() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream packetStream = new ByteArrayOutputStream();
            for (Object sync : syncs) {
                CODEC.encodeObject(sync,packetStream);
                out.write(Util.pack(packetStream.toByteArray()));
                packetStream.reset();
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        host.start();
    }

    @Override
    public void close() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    @Override
    public int getEndpointId() { return HOST_ID; }

    public int getPort() {return host.getPort();}
    public String getAddress() {return host.getAddress();}
}