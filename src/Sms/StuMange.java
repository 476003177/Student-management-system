/*
 * model2ģʽ
 */

package Sms;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class StuMange extends JFrame implements ActionListener{

	JTable jt=null;
	JPanel jp1,jp2;
	JLabel jl1;
	JButton jb1,jb2,jb3,jb4,jb5;
	JScrollPane jsp=null;
	JTextField jtf;
	StuModel sm=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StuMange test1=new StuMange();
		
	}

	public StuMange()
	{
		jp1=new JPanel();
		jtf=new JTextField(10);
		jb1=new JButton("��ѯ");
		jb1.addActionListener(this);
		jb5=new JButton("��ʼ��");
		jb5.addActionListener(this);
		jl1=new JLabel("����������");
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jb5);
		
		jp2=new JPanel();
		jb2=new JButton("���");
		jb2.addActionListener(this);
		jb3=new JButton("�޸�");
		jb3.addActionListener(this);
		jb4=new JButton("ɾ��");
		jb4.addActionListener(this);
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		sm=new StuModel();
		jt=new JTable(sm);
		jsp=new JScrollPane(jt);
		this.add(jsp);
		this.add(jp1,"North");
		this.add(jp2,"South");
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		sm=new StuModel();                                                   //�����µ�����ģ����
		if(arg0.getSource()==jb1)                                            //�˷���������ΪҪ��ͬһ����
		{
			String name=this.jtf.getText().trim();                           //trim�����Ʒ�����
			if (name.equals(""))
			{
				sm.csh();
			}else{
				String sql="select * from stu where stuname=?";
				String paras[]={name};
				sm.stuselect(sql, paras);
			}
		}else if(arg0.getSource()==jb5){
			sm.csh();
		}
		else if(arg0.getSource()==jb2){
			sm.stuadd(this,"���ѧ��",true);
			sm.csh();
		}else if(arg0.getSource()==jb4 || arg0.getSource()==jb3){
			int rowNum=this.jt.getSelectedRow();                             //�����û����е��У������򷵻�-1
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��һ��");              //ûѡ��ʾ
			}else{
				if(arg0.getSource()==jb4)
				{
					String stuId=(String)sm.getValueAt(rowNum, 0);
					sm.studelete(stuId);
				}else if(arg0.getSource()==jb3)
				{
					sm.stuup(this, "�޸���Ϣ", true, sm, rowNum);
				}
				sm.csh();
			}
		}
		jt.setModel(sm);                                                    //����JTable
	} 
}
