package rest.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import rest.model.Produto;
import rest.util.DbUtil;

public class ProdutoDAO {

	private static Connection connection = DbUtil.getConnection();

	public static Produto addProduto(int id, String nome, double preco, String descricao, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("insert into product(id, nome, preco, descricao) values (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, id);
			pStmt.setString(2, nome);
			pStmt.setDouble(3, preco);
			pStmt.setString(4, descricao);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				uploadFile(input, rs.getInt("id"));
				return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getString("descricao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Produto updateProduto(int id, String nome, double preco, String descricao, InputStream input) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("update products set id=?, nome=?, preco=?, where descricao=?",
					Statement.RETURN_GENERATED_KEYS);
			pStmt.setInt(1, id);
			pStmt.setString(2, nome);
			pStmt.setDouble(3, preco);
			pStmt.setString(4, descricao);
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				if(input != null)
					uploadFile(input, rs.getInt("id"));
				return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getString("descricao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void deleteProduto(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("delete from products where id=?");
			pStmt.setInt(1, id);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Produto> getAllProdutos() {
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from products order by id");
			while (rs.next()) {
				Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getString("descricao"));
				produtos.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return produtos;
	}

	public static Produto getProduto(int id) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from products where id=?");
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getString("descricao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Produto getProdutoByNome(String nome) {
		try {
			PreparedStatement pStmt = connection.prepareStatement("select * from produtcts where nome=?");
			pStmt.setString(1, nome);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"), rs.getString("descricao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void uploadFile(InputStream uploadedInputStream, int id) {
		try {
			InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("uploads.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			String folder = prop.getProperty("folder");
			String filePath = folder + id;
			saveFile(uploadedInputStream, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveFile(InputStream uploadedInputStream, String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
