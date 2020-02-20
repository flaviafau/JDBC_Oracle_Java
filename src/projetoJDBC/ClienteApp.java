package projetoJDBC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClienteApp {

	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String usuario = "c##curso_java";
	static String senha = "schema";
	static Connection con=null;
/************************************************************************************* */	
public static void conectar() throws SQLException 
/************************************************************************************* */
	{ 	
			con = DriverManager.getConnection(url,usuario,senha);
			con.setAutoCommit(false);
	}
/************************************************************************************* */
public static void desconectar() throws SQLException 
/************************************************************************************* */
	{ 	
			con.close();
	}
/************************************************************************************* */
public static void inserir(long cpf,  String nome, String email) throws SQLException
/************************************************************************************* */
{
	 String sql = "insert into CLIENTE values (" + cpf + ",'" + nome + "','" + email + "')";
	Statement st = con.createStatement();
	st.execute(sql);
	con.commit();
}
/************************************************************************************* */
public static void inserirPS(long cpf,  String nome, String email) throws SQLException
/************************************************************************************* */
{
	 String sql = "insert into CLIENTE values (?,?,?)";
	PreparedStatement st = con.prepareStatement(sql);
	st.setLong(1,cpf);
	st.setString(2, nome);
	st.setString(3,email);
	st.executeUpdate();
	con.commit();
}
public static void inserirSP(long cpf,  String nome, String email) throws SQLException
/************************************************************************************* */
{
	 String sql = "{call sp_inserircliente (?,?,?)}";
	CallableStatement cs = con.prepareCall(sql);
	cs.setLong(1,cpf);
	cs.setString(2, nome);
	cs.setString(3,email);
	cs.execute();
	con.commit();
}
/************************************************************************************* */
public static void consultar(long cpf) throws SQLException
/************************************************************************************* */
{
    String sql = "Select * from CLIENTE where cpf= " + cpf + "";
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql);
	while (rs.next()) {
		System.out.printf("cpf: " + rs.getInt(1) + " nome: " + rs.getString(2) + " email: " + rs.getString(3)+"\n");
	}
}

/************************************************************************************ */
public static void consultarTodos() throws SQLException
/************************************************************************************* */
{
	 String sql = "Select * from Cliente";
	 Statement st = con.createStatement();
	 ResultSet rs = st.executeQuery(sql);
	 int cont = 0;
	while (rs.next()) {
		System.out.println("cpf:" + rs.getInt(1) + " nome:" + rs.getString(2) + " email" + rs.getString(3));
		cont++;
	}
	System.out.println("número de clientes listados>>>>>>>" + cont + "\n");
}

/************************************************************************************ */
public static void alterar(long cpf, String nome, String email) throws SQLException
/************************************************************************************* */
{
    String sql = "update Cliente set nome='"+nome+"',email='"+email+"' where cpf="+cpf;
    Statement st = con.createStatement();
	st.executeUpdate(sql);
	con.commit();

}

/************************************************************************************ */
public static void excluir( long cpf) throws SQLException
/************************************************************************************* */
{
	 String sql = "delete from Cliente where cpf= " + cpf + "";
	 Statement st = con.createStatement();
	 st.executeUpdate(sql);
	con.commit();
}

public static void main(final String[] args) {
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conectar();
			 Scanner entrada = new Scanner(System.in);
		     int opcao = 0;
			 long cpf;
		    String nome, email =null;
			while (opcao != 6) {
				System.out.println("Sistema de Gerenciamento de clientes");
				System.out.println("Digite [1] para consultar todos os clientes");
				System.out.println("Digite [2] para consultar um cliente especifico");
				System.out.println("Digite [3] para casdastrar um novo cliente");
				System.out.println("Digite [4] para alterar um cliente");
				System.out.println("Digite [5] para excluir um cliente");
				System.out.println("Digite [6] para sair");
				opcao = entrada.nextInt();

				switch(opcao){
					case 1:{ //Consultar todos
					System.out.println("[1] Consultar Todos");
					consultarTodos();
					break;
					}
					case 2:{ //Consultar todos
						System.out.println("[2] Consultar cliente especifico");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						consultar(cpf);
						break;

					}
					case 3:{//Cadastrar cliente
						System.out.println("[3] Cadastrar cliente");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						entrada.nextLine();
						System.out.println("Informar nome");
						nome = entrada.nextLine();
						System.out.println("Informar email");
						email= entrada.nextLine();
						//inserir(cpf,nome,email);
						//inserirPS(cpf,nome,email);
						inserirSP(cpf,nome,email);
						break;

					}
					case 4:{//Alterar cliente
						System.out.println("[4] Alterar cliente");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						entrada.nextLine();
						System.out.println("Informar nome");
						nome = entrada.nextLine();
						System.out.println("Informar email");
						email = entrada.nextLine();	
						alterar(cpf,nome,email);
						break;

					}
					case 5:{//Ecluir cliente
						System.out.println("[4] Alterar cliente");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						excluir(cpf);
						break;

					}
					case 6:{
						System.out.println("Encerrando o sistema");
						break;
					}
				}
			}
			entrada.close();
		} catch (Exception e) {
			//TODO: handle exception
		}
		

	}

}
