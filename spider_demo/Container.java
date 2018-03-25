package com.spider.spider_demo;
/***
生成Graphic user interface，並監聽從MainProcessor傳來的資料，連接FBPageProcessor、MainProcessor
***/
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class Container extends JFrame {
	
    public Container(MainProcessor mainprocessor) {
    	
    	this.mainprocessor = mainprocessor;
    	quest = new FBPageProcessor(mainprocessor);
    }
   
    public Container(){
    	
    	try{
    		
    		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    			
    			if ("Nimbus".equals(info.getName())) {
    				
    				UIManager.setLookAndFeel(info.getClassName());
    				break;
    			}
    		}
    	}catch(UnsupportedLookAndFeelException u){
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nContainer:UnsupportedLookAndFeelException\n - - - - - - - - - - - - - - -");
    	}catch(InstantiationException in){
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nContainer:InstantiationException\n - - - - - - - - - - - - - - -");
    	}catch(ClassNotFoundException c){
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nContainer:ClassNotFoundException\n - - - - - - - - - - - - - - -");
    	}catch(IllegalAccessException i){
    		new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nContainer:IllegalAccessException\n - - - - - - - - - - - - - - -");
    	}
    }
    
    public void initComponents() {

        jPanel2 = new JPanel();
        jScrollPane2 = new JScrollPane();
        String[] name = new String[]{"Post", "Path", "Type"};
        String[][] data = new String[][]{};
        tableModel = new DefaultTableModel(data,name);
        jTable1 = new JTable(tableModel);
        seed = new JLabel();
        input = new JTextField();
        username = new JLabel();
        input2 = new JTextField();
        password = new JLabel();
        input3 = new JTextField();
        jLabel3 = new JLabel();
        start = new JButton();
        Stop = new JButton();
        current = new JLabel();
        jScrollPane1 = new JScrollPane();
        Status = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new Color(255, 255, 255));

        jTable1.setFont(new Font("新細明體", 1, 14));  
        jTable1.setIntercellSpacing(new Dimension(0, 0));
        jTable1.setRowHeight(34);
        jTable1.setShowHorizontalLines(false);
        jScrollPane2.setViewportView(jTable1);

        seed.setFont(new Font("Segoe Script", 1, 18)); 
        seed.setText("Initial Url ~ Only 4 FB page");

        input.setText("https://www.facebook.com/cowbaychunghsing/");

        username.setFont(new Font("Segoe Script", 1, 18)); 
        username.setText("Your Username");

        input2.setText(new String(new File("C:/cookies/browser.data").exists()?"已存在cookies":"帳號"));

        password.setFont(new Font("Segoe Script", 1, 18)); 
        password.setText("Your password");

        input3.setText(new String(new File("C:/cookies/browser.data").exists()?"已存在cookies":"密碼"));

        jLabel3.setFont(new Font("Segoe Script", 1, 18)); 
        jLabel3.setText("Information of profile");

        start.setBackground(new Color(102, 102, 102));
        start.setFont(new Font("Segoe Script", 1, 14)); 
        start.setText("START");
        start.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), null));
        start.setContentAreaFilled(false);
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                startActionPerformed(evt);
            }
        });

        Stop.setBackground(new Color(102, 102, 102));
        Stop.setFont(new Font("Segoe Script", 1, 14)); 
        Stop.setText("DelCookies");
        Stop.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), null));
        Stop.setContentAreaFilled(false);
        Stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });
        
        timer = new Timer(1000,new  ActionListener(){
        	public void actionPerformed(ActionEvent evt){
        		timerActionPerformed(evt);
        	}
        });
        
        current.setFont(new Font("Segoe Script", 1, 18)); 
        current.setText("Current Status");

        Status.setColumns(20);
        Status.setRows(5);
        jScrollPane1.setViewportView(Status);
        Status.addMouseListener(new MouseListener(){
        	public void mouseEntered(MouseEvent evt){
        		 
        	}
        	public void mousePressed(MouseEvent evt){
        		
        	}
        	public void mouseClicked(MouseEvent evt){
        		try{
        			
        			StatusActionPerformed(evt);
        		}catch(NumberFormatException n){
        			return;
        		}
        	}
        	
        	public void mouseReleased(MouseEvent evt){
        		
        	}
        	
        	public void mouseExited(MouseEvent evt){
        		
        	}
        	
        });
        
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(start, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(Stop, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(seed)
                                .addComponent(username)
                                .addComponent(input2)
                                .addComponent(password)
                                .addComponent(input3, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(10, 10, 10))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(input, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(current)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel3)
                        .addGap(103, 103, 103))
                    .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(seed)
                    .addComponent(jLabel3))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(input, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(username)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(password)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(start, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                            .addComponent(Stop, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(current)
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }                 

    private void startActionPerformed(ActionEvent evt) {
    	
    	quest = new FBPageProcessor(quest,input.getText(),input2.getText(),input3.getText());
    	Stop.setText("Stop");
    	quest.start();
    }                                     
    
    private void StopActionPerformed(ActionEvent evt) {
    	
    	if(Stop.getText()=="DelCookies"){
    		
    		file = new File("C:/cookies/browser.data");
    		if(file.exists()){
    			
    			file.delete();
    			input2.setText("帳號");
    			input3.setText("密碼");
    			return;
    		}
    		else{
    			
    			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nDelete cookies:NullPointException\n - - - - - - - - - - - - - - -");
    			return;
    		}
    	}
        quest.Stop();
    }    
    
    private void timerActionPerformed(ActionEvent evt){
    	if(flag){
    		
			tableModel.addRow(new Object[]{"POST  " + count + "  :", "C:/down/Post" + count + "", "txt"});
			flag = false;
    	}	
    }
    
    private void StatusActionPerformed(MouseEvent evt){
    	if(Stop.getText()=="DelCookies"){
    		
    		if(evt.getClickCount() == 2){
    			
    			int num = Integer.parseInt(JOptionPane.showInputDialog("輸入欲開始爬取的貼文，默認為1"));
    			quest.setcount(num);
    			new StatusMessage(mainprocessor,"- - - - - - - - - - - - - - - \nSet posts form:\t"+num+"\n - - - - - - - - - - - - - - -");
    		}
    	}
    }
    
    public void rewrite(String message){
    	
    	Status.append(message);
    	Status.setSelectionStart(Status.getText().length());
    }
    
    public void rewrite(int count,boolean flag){
    	
    	this.count = count;
    	this.flag = flag;
    	timer.start();
    }
    
    private JTextArea Status;
    private JButton Stop;
    private JLabel current;
    private JTextField input;
    private JTextField input2;
    private JTextField input3;
    private JLabel jLabel3;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private DefaultTableModel tableModel;
    private JLabel password;
    private JLabel seed;
    private JButton start;
    private JLabel username;
    private FBPageProcessor quest;
    private MainProcessor mainprocessor;
    private Timer timer;
    private int count;
    private boolean flag = false;
    private File file;
    private static final long serialVersionUID = 1L;

}
