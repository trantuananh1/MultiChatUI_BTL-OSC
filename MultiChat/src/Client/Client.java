package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Client extends javax.swing.JFrame {
    String username, address="localhost";
    ArrayList<String> users =new ArrayList();
    int port =2222;
    boolean isConnected=false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    
    //-------------------------//
    
    public void ListenThread(){
        Thread IncomingReader=new Thread(new IncomingReader());
        IncomingReader.start();
    }
    //--------------------------//
    public void userAdd(String data){
        users.add(data);
    }
    public void userRemove(String data){
        textAreaChat.append(data+" is now offline.\n");
    }
    //------------------------//
    public void writeUsers(){
        String[] tmpList=new String[(users.size())];
        users.toArray(tmpList);
        for (String token:tmpList){
            
        }
    }
    //----------------------//
    public void sendDisconnect(){
        String bye=(username+": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            textAreaChat.append("Could not send Disconnect message.\n");
        }
    }
    //-------------------------//
    public void Disconnect(){
        try{
            textAreaChat.append("Disconnected.\n");
            sock.close();
        }catch(Exception e){
            textAreaChat.append("Failed to disconnect.\n");
        }
        isConnected=false;
        textFieldUsername.setEditable(true);
    }
    public Client() {
        initComponents();
    }

    //---------------------//
    public class IncomingReader implements Runnable{
        public void run(){
            String[] data;
            String stream, done="Done", connect="Connect",
                    disconnect="Disconnect", chat="Chat";
            
            try{
                while((stream=reader.readLine())!=null){
                    data=stream.split(":");
                    if (data[2].equals(chat)){
                        textAreaChat.append(data[0]+": "+data[1]+"\n");
                        textAreaChat.setCaretPosition(textAreaChat.getDocument().getLength());
                    }else if(data[2].equals(connect)){
                        textAreaChat.removeAll();
                        userAdd(data[0]);
                    }else if(data[2].equals(disconnect)){
                        userRemove(data[0]);
                    }else if(data[2].equals(done)){
                        writeUsers();
                        users.clear();
                    }
                }
            }catch(Exception ex){}
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaChat = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();
        btnDisconnect = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        textFieldUsername = new javax.swing.JTextField();
        textFieldPass = new javax.swing.JTextField();
        textFieldChat = new javax.swing.JTextField();
        btnAnoLogin = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAreaChat.setColumns(20);
        textAreaChat.setRows(5);
        jScrollPane1.setViewportView(textAreaChat);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("User name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Pass");

        btnConnect.setText("CONNECT");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnDisconnect.setText("DISCONNECT");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        btnSend.setText("SEND");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnAnoLogin.setText("ANONYMOUS LOGIN");
        btnAnoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnoLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFieldChat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldPass)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                            .addComponent(btnAnoLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        if(isConnected==false){
            username=textFieldUsername.getText();
            textFieldUsername.setEditable(false);
            
            try{
                sock=new Socket(address, port);
                InputStreamReader isr=new InputStreamReader(sock.getInputStream());
                reader=new BufferedReader(isr);
                writer=new PrintWriter(sock.getOutputStream());
                writer.println(username+":has connected.:Connect");
                writer.flush();
                isConnected=true;
            }catch(Exception ex){
                textAreaChat.append("Can't connect! Try again.\n");
                textFieldUsername.setEditable(true);
            }
            ListenThread();
        }else if (isConnected==true){
            textAreaChat.append("You are already connected.\n");
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        String nothing="";
        if ((textFieldChat.getText()).equals(nothing)){
            textFieldChat.setText("");
            textFieldChat.requestFocus();
        }else{
            try{
                writer.println(username+":"+textFieldChat.getText()+":Chat");
                writer.flush(); //flushes the buffer
            }catch(Exception e){
                textAreaChat.append("Message wasn't sent.\n");
            }
            textFieldChat.setText("");
            textFieldChat.requestFocus();
        }
        textFieldChat.setText("");
        textFieldChat.requestFocus();
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnAnoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnoLoginActionPerformed
        textFieldUsername.setText("");
        if (isConnected==false){
            String anon="anon";
            Random generator =new Random();
            int i=generator.nextInt(999)+1;
            anon+=String.valueOf(i);
            username=anon;
            
            textFieldUsername.setText(anon);
            textFieldUsername.setEditable(false);
        
            try{
                sock=new Socket(address, port);
                InputStreamReader isr=new InputStreamReader(sock.getInputStream());
                reader=new BufferedReader(isr);
                writer=new PrintWriter(sock.getOutputStream());
                writer.println(anon+":has connected.:Connect");
                writer.flush();
                isConnected=true;
            }catch(Exception e){
                textAreaChat.append("Can't connect! Try again.\n");
                textFieldUsername.setEditable(true);
            }
            ListenThread();
        }else if (isConnected==true){
            textAreaChat.append("You are already connected.\n");
        }
    }//GEN-LAST:event_btnAnoLoginActionPerformed


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnoLogin;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textAreaChat;
    private javax.swing.JTextField textFieldChat;
    private javax.swing.JTextField textFieldPass;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
