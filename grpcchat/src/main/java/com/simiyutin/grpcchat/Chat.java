package com.simiyutin.grpcchat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Chat extends ChatGrpc.ChatImplBase {

    private static final Logger LOGGER = LogManager.getLogger(Chat.class);

    private static StreamObserver<ChatOuterClass.Message> handleToClient;
    private StreamObserver<ChatOuterClass.Message> handleToServer;

    private String userName;

    private static void initClient(final Chat chat, String[] args) {
        chat.userName = args[2];

        String host = args[0];
        String port = args[1];
        ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress(host, Integer.parseInt(port)).usePlaintext(true);
        ManagedChannel channel = builder.build();
        ChatGrpc.ChatStub stub  = ChatGrpc.newStub(channel);

        chat.handleToServer = stub.routeChat(new StreamObserver<ChatOuterClass.Message>() {
            public void onNext(ChatOuterClass.Message message) {
                chat.printMsg(message);
            }

            public void onError(Throwable throwable) {
                LOGGER.error("server error:", throwable);
            }

            public void onCompleted() {}
        });
    }

    private static void initServer(final Chat chat, String[] args) {
        chat.userName = args[0];
        Server server = ServerBuilder.forPort(50051).addService(new Chat()).build();

        try {
            server.start();
        } catch (IOException e) {
            LOGGER.error("server error:", e);
        }
    }


    public static void main(String[] args) {
        final Chat chat = new Chat();

        if (args.length == 3) {

            initClient(chat, args);

        } else if (args.length == 1) {

            initServer(chat, args);

        } else {

            System.out.println("input parameters: [host, port, user name] or [user name]");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();
            chat.sendMessage(message);
        }
    }

    private void sendMessage(String message) {
        ChatOuterClass.Message msg = ChatOuterClass.Message
                .newBuilder()
                .setFrom(userName)
                .setText(message)
                .setTime(new SimpleDateFormat("h:mm a").format(new Date()))
                .build();

        if (handleToClient != null) {

            handleToClient.onNext(msg);

        } else if (handleToServer != null) {

            handleToServer.onNext(msg);
        }
    }

    private void printMsg(ChatOuterClass.Message msg) {
        System.out.format("[%s], %s: %s\n", msg.getTime(), msg.getFrom(), msg.getText());
    }

    @Override
    public StreamObserver<ChatOuterClass.Message> routeChat(final StreamObserver<ChatOuterClass.Message> responseObserver) {
        handleToClient = responseObserver;

        return new StreamObserver<ChatOuterClass.Message>() {
            public void onNext(ChatOuterClass.Message message) {
                printMsg(message);
            }

            public void onError(Throwable throwable) {
                LOGGER.error("client error:", throwable);
            }

            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
