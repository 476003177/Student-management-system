package Sms;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class StuModel extends AbstractTableModel{

	Vector rowData,columnNames;        
	
	
	//得到共有多少列，自动调用
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}
	//得到共有多少行
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String) this.columnNames.get(column);
	}
	//得到某行某列的数据，自动调用
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);
	}
	
	//初始化
	public void csh()
	{
		String[] paras1={"1"};
		this.stuselect("select * from stu where 1=?", paras1);
	}
	
	//查询,查询本质就是初始化
	public void stuselect(String sql,String []paras)
	{
		columnNames=new Vector();
		rowData=new Vector();
		columnNames.add("学号");
		columnNames.add("名字");
		columnNames.add("性别");
		columnNames.add("年龄");
		columnNames.add("籍贯");
		columnNames.add("系别");
		ResultSet rs=null;
		SqlHelper sqlh=new SqlHelper();
		try {
			rs=sqlh.queryExectue(sql, paras);
			//System.out.println("到");
			while(rs.next())
			{
				Vector hang=new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getInt(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));	
				rowData.add(hang);                                                            //一整行数据加入到rowData
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				sqlh.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	//添加学生
	public void stuadd(Frame owner,String title,boolean modal)
	{
		StuAddUpDialog sad=new StuAddUpDialog(owner,title,modal,null,0);

	}
	
	//更新学生信息
	public void stuup(Frame owner,String title,boolean modal,StuModel sm,int rowNum)
	{
		StuAddUpDialog sad=new StuAddUpDialog(owner,title,modal,sm,rowNum);
	}
	
	//删除学生信息
	public void studelete(String stuId)
	{
		String sql="delete from stu where stuId=?";
		String []paras={stuId};
		StuModel temp=new StuModel();
		temp.stuuprun(sql, paras);	
	}
	
	//增删改操作
	public boolean stuuprun(String sql,String []paras)
	{
		SqlHelper sqlh=new SqlHelper();
		return sqlh.updExectue(sql, paras);
	}
	
	public StuModel()
	{
		this.csh();
	}
	
}


class StuAddUpDialog extends JDialog implements ActionListener{

	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JPanel jp1,jp2,jp3;
	StuModel sm1=null;
	
	//owner--它的父窗口;title--窗口名;model--指定是模式窗口，还是非模式，模式窗口必须响应
	public StuAddUpDialog(Frame owner,String title,boolean modal,StuModel sm,int rowNum){
		super(owner,title,modal);                                                             //调用父类构造方法，达到模式对话框效果
		jl1=new JLabel("学号");
		jl2=new JLabel("名字");
		jl3=new JLabel("性别");
		jl4=new JLabel("年龄");
		jl5=new JLabel("籍贯");
		jl6=new JLabel("系别");
			
		jtf1=new JTextField();
		jtf2=new JTextField();
		jtf3=new JTextField();
		jtf4=new JTextField();
		jtf5=new JTextField();
		jtf6=new JTextField();
		if(sm!=null)
		{
			sm1=sm;
			jtf1.setText((String)sm.getValueAt(rowNum, 0));
			jtf1.setEditable(false);                                                          //设置jtf1内容不可修改
			jtf2.setText((String)sm.getValueAt(rowNum, 1));
			jtf3.setText((String)sm.getValueAt(rowNum, 2));
			jtf4.setText(sm.getValueAt(rowNum, 3)+"");
			jtf5.setText((String)sm.getValueAt(rowNum, 4));
			jtf6.setText((String)sm.getValueAt(rowNum, 5));
			jb1=new JButton("修改");
		}else{
			jb1=new JButton("添加");
		}
		
		jb2=new JButton("取消");
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//设置布局
		jp1.setLayout(new GridLayout(6,1));
		jp2.setLayout(new GridLayout(6,1));
		
		//添加组件
		jp1.add(jl1);
		jp1.add(jl2);
		jp1.add(jl3);
		jp1.add(jl4);
		jp1.add(jl5);
		jp1.add(jl6);
		
		jp2.add(jtf1);
		jp2.add(jtf2);
		jp2.add(jtf3);
		jp2.add(jtf4);
		jp2.add(jtf5);
		jp2.add(jtf6);
		
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		//展现
		this.setSize(300, 250);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//用户点击添加按钮后的响应动作
		if(e.getSource()==jb1){
			//连接数据库
			if(sm1!=null)
			{
				StuModel temp=new StuModel();
				String sql="update stu set stuName=?,stuSex=?,stuAge=?,stuJg=?,stuDept=? where stuId=?";
				String []paras={jtf2.getText(),jtf3.getText(),jtf4.getText(),jtf5.getText(),jtf6.getText(),jtf1.getText()};
				if(!temp.stuuprun(sql, paras))
				{
					JOptionPane.showMessageDialog(this, "修改失败");
				}

			}else{
				StuModel temp=new StuModel();
				String sql="insert into stu values(?,?,?,?,?,?)";
				String []paras={jtf1.getText(),jtf2.getText(),jtf3.getText(),jtf4.getText(),jtf5.getText(),jtf6.getText()};
				if(!temp.stuuprun(sql, paras))
				{
					JOptionPane.showMessageDialog(this, "添加失败");
				}
			}
			this.dispose();	
		}
		else if(e.getSource()==jb2){
			this.dispose();
		}
	}
	
}
