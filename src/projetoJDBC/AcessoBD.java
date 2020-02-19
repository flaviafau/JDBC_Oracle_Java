package projetoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class AcessoBD { 
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String usuario = "c##curso_java";
	static String senha = "schema";
	static  Connection con =null ;
	static Statement st=null;
	static ResultSet rs=null;
	
	public static void conectar() throws SQLException 
	{ 	
			con = DriverManager.getConnection(url,usuario,senha);
			con.setAutoCommit(false);

	}
	public static void criarstatement() throws SQLException 
	{ 	
		st= con.createStatement();
	}
	public static void executaSql() throws SQLException 
	{ 	
		rs = st.executeQuery("select * from CLIENTE");
	}
	public static void main(String[] args) {
		

		try {
			//carrega o driver para registro
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Estabelece conexão
			conectar();
			// cria objeto Statement
			if(con!=null) {
				criarstatement();
			}
	
			if(st!=null) {
				//envia informações sobre o banco de dados
				executaSql();
			}
			//processa o resultado do objeto
			if(rs!=null) {
				while(rs.next()) {
					JOptionPane.showMessageDialog(null, "cpf:"+rs.getInt(1)+
					" nome:"+ rs.getString(2)+ " email"+ rs.getString(3));
					}
			}
		} catch (SQLException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
		}
	 catch (ClassNotFoundException cnf) {
		// TODO Auto-generated catch block
		cnf.printStackTrace();
	}

		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}


