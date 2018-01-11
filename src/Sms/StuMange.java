/*
 * model2模式
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
		jb1=new JButton("查询");
		jb1.addActionListener(this);
		jb5=new JButton("初始化");
		jb5.addActionListener(this);
		jl1=new JLabel("请输入名字");
		jp1.add(jl1);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jb5);
		
		jp2=new JPanel();
		jb2=new JButton("添加");
		jb2.addActionListener(this);
		jb3=new JButton("修改");
		jb3.addActionListener(this);
		jb4=new JButton("删除");
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
		sm=new StuModel();                                                   //构建新的数据模型类
		if(arg0.getSource()==jb1)                                            //此方法局限性为要在同一个类
		{
			String name=this.jtf.getText().trim();                           //trim将控制符过滤
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
			sm.stuadd(this,"添加学生",true);
			sm.csh();
		}else if(arg0.getSource()==jb4 || arg0.getSource()==jb3){
			int rowNum=this.jt.getSelectedRow();                             //返回用户点中的行，若无则返回-1
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "请选择一行");              //没选提示
			}else{
				if(arg0.getSource()==jb4)
				{
					String stuId=(String)sm.getValueAt(rowNum, 0);
					sm.studelete(stuId);
				}else if(arg0.getSource()==jb3)
				{
					sm.stuup(this, "修改信息", true, sm, rowNum);
				}
				sm.csh();
			}
		}
		jt.setModel(sm);                                                    //更新JTable
	} 
}
