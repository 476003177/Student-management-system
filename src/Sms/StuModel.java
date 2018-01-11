package Sms;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class StuModel extends AbstractTableModel{

	Vector rowData,columnNames;        
	
	
	//�õ����ж����У��Զ�����
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}
	//�õ����ж�����
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
	//�õ�ĳ��ĳ�е����ݣ��Զ�����
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);
	}
	
	//��ʼ��
	public void csh()
	{
		String[] paras1={"1"};
		this.stuselect("select * from stu where 1=?", paras1);
	}
	
	//��ѯ,��ѯ���ʾ��ǳ�ʼ��
	public void stuselect(String sql,String []paras)
	{
		columnNames=new Vector();
		rowData=new Vector();
		columnNames.add("ѧ��");
		columnNames.add("����");
		columnNames.add("�Ա�");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("ϵ��");
		ResultSet rs=null;
		SqlHelper sqlh=new SqlHelper();
		try {
			rs=sqlh.queryExectue(sql, paras);
			//System.out.println("��");
			while(rs.next())
			{
				Vector hang=new Vector();
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getInt(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));	
				rowData.add(hang);                                                            //һ�������ݼ��뵽rowData
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
	
	//���ѧ��
	public void stuadd(Frame owner,String title,boolean modal)
	{
		StuAddUpDialog sad=new StuAddUpDialog(owner,title,modal,null,0);

	}
	
	//����ѧ����Ϣ
	public void stuup(Frame owner,String title,boolean modal,StuModel sm,int rowNum)
	{
		StuAddUpDialog sad=new StuAddUpDialog(owner,title,modal,sm,rowNum);
	}
	
	//ɾ��ѧ����Ϣ
	public void studelete(String stuId)
	{
		String sql="delete from stu where stuId=?";
		String []paras={stuId};
		StuModel temp=new StuModel();
		temp.stuuprun(sql, paras);	
	}
	
	//��ɾ�Ĳ���
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
	
	//owner--���ĸ�����;title--������;model--ָ����ģʽ���ڣ����Ƿ�ģʽ��ģʽ���ڱ�����Ӧ
	public StuAddUpDialog(Frame owner,String title,boolean modal,StuModel sm,int rowNum){
		super(owner,title,modal);                                                             //���ø��๹�췽�����ﵽģʽ�Ի���Ч��
		jl1=new JLabel("ѧ��");
		jl2=new JLabel("����");
		jl3=new JLabel("�Ա�");
		jl4=new JLabel("����");
		jl5=new JLabel("����");
		jl6=new JLabel("ϵ��");
			
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
			jtf1.setEditable(false);                                                          //����jtf1���ݲ����޸�
			jtf2.setText((String)sm.getValueAt(rowNum, 1));
			jtf3.setText((String)sm.getValueAt(rowNum, 2));
			jtf4.setText(sm.getValueAt(rowNum, 3)+"");
			jtf5.setText((String)sm.getValueAt(rowNum, 4));
			jtf6.setText((String)sm.getValueAt(rowNum, 5));
			jb1=new JButton("�޸�");
		}else{
			jb1=new JButton("���");
		}
		
		jb2=new JButton("ȡ��");
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		//���ò���
		jp1.setLayout(new GridLayout(6,1));
		jp2.setLayout(new GridLayout(6,1));
		
		//������
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
		
		//չ��
		this.setSize(300, 250);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//�û������Ӱ�ť�����Ӧ����
		if(e.getSource()==jb1){
			//�������ݿ�
			if(sm1!=null)
			{
				StuModel temp=new StuModel();
				String sql="update stu set stuName=?,stuSex=?,stuAge=?,stuJg=?,stuDept=? where stuId=?";
				String []paras={jtf2.getText(),jtf3.getText(),jtf4.getText(),jtf5.getText(),jtf6.getText(),jtf1.getText()};
				if(!temp.stuuprun(sql, paras))
				{
					JOptionPane.showMessageDialog(this, "�޸�ʧ��");
				}

			}else{
				StuModel temp=new StuModel();
				String sql="insert into stu values(?,?,?,?,?,?)";
				String []paras={jtf1.getText(),jtf2.getText(),jtf3.getText(),jtf4.getText(),jtf5.getText(),jtf6.getText()};
				if(!temp.stuuprun(sql, paras))
				{
					JOptionPane.showMessageDialog(this, "���ʧ��");
				}
			}
			this.dispose();	
		}
		else if(e.getSource()==jb2){
			this.dispose();
		}
	}
	
}
