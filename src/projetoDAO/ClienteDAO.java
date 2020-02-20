package projetoDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import projetoDAO.Cliente;


public class ClienteDAO {
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String usuario = "c##curso_java";
	static String senha = "schema";
	static Connection con=null;
/************************************************************************************* */	
public static void conectar() throws SQLException 
/************************************************************************************* */
	{ 	
			con = DriverManager.getConnection(url,usuario,senha);
			DatabaseMetaData meta = con.getMetaData();
			con.setAutoCommit(false);
			System.out.println("Banco de dados:"+meta.getDatabaseProductVersion());
	}
/************************************************************************************* */
public static void desconectar() throws SQLException 
/************************************************************************************* */
	{ 	
			con.close();
	}
/************************************************************************************* */
public static void inserir(Cliente cliente) throws SQLException
/************************************************************************************* */
{
	 String sql = "insert into Cliente values ("+cliente.getCpf()+",'"
				+cliente.getNome()+"','"+cliente.getEmail()+"')";
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
public static Cliente consultar(long cpf) throws SQLException
/************************************************************************************* */
{
    String sql = "Select * from CLIENTE where cpf= " + cpf + "";
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql);
	Cliente cliente = null;
	while(rs.next()) {   
		cliente = new Cliente(rs.getLong(1),rs.getString(2),rs.getString(3));
	}	
	return cliente;
}

/************************************************************************************ */
public static List<Cliente> consultarTodos() throws SQLException
/************************************************************************************* */
{
	String sql = "select * from Cliente";
	Statement st = con.createStatement();
	ResultSet rs = st.executeQuery(sql);
	List<Cliente> clientes = new LinkedList<>();
	while(rs.next()) {   
		Cliente cliente = new Cliente(rs.getLong(1),rs.getString(2),rs.getString(3));
		clientes.add(cliente);
	}	
	return clientes;
}

/************************************************************************************ */
public static void alterar(Cliente cliente) throws SQLException
/************************************************************************************* */
{
	String sql = "update Cliente set nome='"+cliente.getNome()
	+"',email='"+cliente.getEmail()+"' where cpf="+cliente.getCpf();
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
					List<Cliente> clientes = ClienteDAO.consultarTodos();
					clientes.forEach(System.out::println);
					System.out.println("Número de Clientes >>>"+clientes.size()+"\n");
					break;
					}
					case 2:{ //Consultar todos
						System.out.println("[2] Consultar cliente especifico");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						Cliente cliente = ClienteDAO.consultar(cpf);
						System.out.println(cliente);
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
						email = entrada.nextLine();
						Cliente cliente = new Cliente(cpf,nome,email);
						ClienteDAO.inserir(cliente);
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
						Cliente cliente = new Cliente(cpf,nome,email);
						ClienteDAO.alterar(cliente);
						break;

					}
					case 5:{//Ecluir cliente
						System.out.println("[4] Alterar cliente");
						System.out.println("Informar cpf");
						cpf = entrada.nextLong();
						ClienteDAO.excluir(cpf);
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
