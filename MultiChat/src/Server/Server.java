package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends javax.swing.JFrame {
    ArrayList clientOutputStream;
    ArrayList<String> users;
    
    public class ClientHandler implements Runnable{
        BufferedReader reader;
        Socket sock;
        PrintWriter client;
        
        public ClientHandler(Socket clientSocket, PrintWriter user){
            client=user;
            try{
                sock=clientSocket;
                InputStreamReader isr=new InputStreamReader(sock.getInputStream());
                reader=new BufferedReader(isr);
            }catch(Exception e){
                textAreaChat.append("Unexpected error...\n");
            }
        }

        @Override
        public void run() {
            String sms, connect="Connect", 
                    disConnect="Disconnect", chat="Chat";
            String[] smsSplit;
            try{
                while ((sms=reader.readLine())!=null){
                    textAreaChat.append("Received: "+sms+"\n");
                    smsSplit=sms.split(":");
                    for (String token: smsSplit){
                        textAreaChat.append(token+"\n");
                    }
                    if (smsSplit[2].equals(connect)){
                        tellEveryone((smsSplit[0]+":"+smsSplit[1]+":"+chat));
                        userAdd(smsSplit[0]);
                    }else if(smsSplit[2].equals(disConnect)){
                        tellEveryone((smsSplit[0]+":has disconnected."+":"+chat));
                        userRemove(smsSplit[0]);
                    }else if(smsSplit[2].equals(chat)){
                        tellEveryone(sms);
                    }else{
                        textAreaChat.append("Synxtax error!");
                    }
                }
            }catch(Exception ex){
                textAreaChat.append("Lost connection.\n");
                ex.printStackTrace();
                clientOutputStream.remove(client);
            }
        }

        
    }
    public Server() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaChat = new javax.swing.JTextArea();
        btnEnd = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        btnOnline = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        textAreaChat.setColumns(20);
        textAreaChat.setRows(5);
        jScrollPane1.setViewportView(textAreaChat);

        btnEnd.setText("END");
        btnEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndActionPerformed(evt);
            }
        });

        btnStart.setText("START");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnOnline.setText("ONLINE USER");
        btnOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnlineActionPerformed(evt);
            }
        });

        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(btnEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOnline, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnOnline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
            Thread starter=new Thread(new ServerStart());
            starter.start();
            
            textAreaChat.append("Server started...\n");
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndActionPerformed
        try{
            Thread.sleep(5000);
        }catch(InterruptedException ex){
            textAreaChat.append("is stopng;");
            Thread.currentThread().interrupt();
        }
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        textAreaChat.append("Server is stopping...\n");
        
        textAreaChat.setText("");
    }//GEN-LAST:event_btnEndActionPerformed

    private void btnOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnlineActionPerformed
        textAreaChat.append("\n Online user: \n");
        for (String user: users){
            textAreaChat.append(user);
            textAreaChat.append("\n");
        }
    }//GEN-LAST:event_btnOnlineActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        textAreaChat.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    public class ServerStart implements Runnable{
        public void run(){
            clientOutputStream=new ArrayList();
            users=new ArrayList();
            
            try{
                ServerSocket serverSock=new ServerSocket(2222);
                
                while (true){
                    Socket clientSock=serverSock.accept();
                    PrintWriter writer=new PrintWriter(clientSock.getOutputStream());
                    clientOutputStream.add(writer);
                    
                    Thread listener =new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    textAreaChat.append("Got a connection.\n");
                }
            }catch(Exception e){
                textAreaChat.append("Error connection.\n");
            }
        }
    }
    public void tellEveryone(String sms) {
        Iterator it=clientOutputStream.iterator();
        while(it.hasNext()){
            try{
                PrintWriter writer=(PrintWriter) it.next();
                writer.println(sms);
                textAreaChat.append("Sending: "+sms+"\n");
                writer.flush();
                textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
            }catch(Exception ex){
                textAreaChat.append("Error telling everyone.\n");
            }
        }
    }

    public void userAdd(String data) {
        String sms, add=": :Connect", 
                done="Server: :Done", name=data;
        textAreaChat.append("Before "+name+" added.\n");
        users.add(name);
        textAreaChat.append("After "+name+" added.\n");
        String[] tmpList=new String[(users.size())];
        users.toArray(tmpList);

        for(String token: tmpList){
            sms=token+add;
            tellEveryone(sms);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String sms, add = ": :Connect", 
                done = "Server: :Done", name = data;
        users.remove(name);
        String[] tmpList=new String[users.size()];
        users.toArray(tmpList);
        for (String token: tmpList){
            sms=token+add;
            tellEveryone(sms);
        }
        tellEveryone(done);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEnd;
    private javax.swing.JButton btnOnline;
    private javax.swing.JButton btnStart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textAreaChat;
    // End of variables declaration//GEN-END:variables
}
