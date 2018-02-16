package com.example.android.androidchatter.comm;

import com.example.android.androidchatter.interfacer.MySocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Speed on 14/02/2018.
 */

public class Socketer implements MySocket {
    private  static  final String AUTHENTICATION_SERVER_ADDRESS="http://127.0.0.1/androidchatter";
    private int listeninhPort=0;
    private static final String HTTP_REQUEST_FAILED=null;
    private HashMap<InetAddress,Socket>sockets=new HashMap<InetAddress, Socket>();
    private ServerSocket serverSocket=null;
    private boolean listening;


    private class ReceiveConnection extends  Thread{
        Socket clientSocket = null;
        public ReceiveConnection(Socket socket){
            this.clientSocket=socket;
            Socketer.this.sockets.put((socket).getInetAddress(),socket);
            //Socketer.this.sockets.put(((ServerSocket)socket).getInetAddress(),socket);
        }

        @Override
        public void run() {
            try {
                BufferedReader in=new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine=in.readLine())!=null){
                    if(inputLine.equals("exit")==false){

                    }else {
                        clientSocket.shutdownInput();
                        clientSocket.shutdownOutput();
                        clientSocket.close();
                        Socketer.this.sockets.remove((clientSocket).getInetAddress());
                        //Socketer.this.sockets.remove(((ServerSocket)clientSocket).getInetAddress());
                    }

                }


            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public String sendHttpRequest(String params){
        URL url;
        String result=new String();
        try{
            url=new URL(AUTHENTICATION_SERVER_ADDRESS);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);

            PrintWriter out=new PrintWriter(connection.getOutputStream());
            out.println(params);
            out.close();

            BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine=in.readLine())!=null){
                result=result.concat(inputLine);
            }
            in.close();




        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.length()==0){
            result=HTTP_REQUEST_FAILED;
        }

        return result;
    }


    @Override
    public String sendHTTPRequest(String params) {
        return null;
    }

    @Override
    public int startListeningPort(int port) {
        listening=true;
        try{
            serverSocket=new ServerSocket(port);
            this.listeninhPort=port;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        while (listening){
            try{
                new ReceiveConnection(serverSocket.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
                return 2;
            }
        }

        try {
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            return 3;
        }
        return 1;
    }


    public void exit(){
        for(Iterator<Socket> iterator=sockets.values().iterator();iterator.hasNext();){
            Socket socket=iterator.next();
            try{
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*@Override
    public void stopListening() {

    }

    @Override
    public void exit() {

    }*/
}
