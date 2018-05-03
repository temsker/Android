package com.tekapic;

/**
 * Created by LEV on 15/04/2018.
 */

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectToServer extends AsyncTask<Void,Void,Void> {

    private static Socket socket = null;

    private static ObjectOutputStream objectOutputStream = null; // write object to the server
    private static DataOutputStream dataOutputStream = null; // write string to the server
    private static BufferedReader bufferedReader = null; // read string from the server
    private static ObjectInputStream objectInputStream = null; // read object from server

    public static Socket getSocket() {
        return socket;
    }

    private static void setSocket(Socket socket) {
        ConnectToServer.socket = socket;
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    private static void setDataOutputStream(DataOutputStream dataOutputStream) {
        ConnectToServer.dataOutputStream = dataOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    private static void setObjectInputStream(ObjectInputStream objectInputStream) {
        ConnectToServer.objectInputStream = objectInputStream;
    }


    private static void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        ConnectToServer.objectOutputStream = objectOutputStream;
    }

    private static void setBufferedReader(BufferedReader bufferedReader) {
        ConnectToServer.bufferedReader = bufferedReader;
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            setSocket(new Socket("192.168.1.12", 10000));


            setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));

            setDataOutputStream(new DataOutputStream(socket.getOutputStream()));

            setBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));

            setObjectInputStream(new ObjectInputStream(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}