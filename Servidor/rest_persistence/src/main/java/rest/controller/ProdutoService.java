package rest.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import rest.dao.ProdutoDAO;
import rest.model.Produto;

@Path("/produtos")
public class ProdutoService {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Produto> getProdutos() {
		return ProdutoDAO.getAllProdutos();
	}

	// Controle da resposta (status code, mensagem)
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getProduto(@PathParam("id") int id) {
		return Response.status(Status.OK).entity(ProdutoDAO.getProduto(id)).build();
	}

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Produto getProdutoByNome(@QueryParam("nome") String nome) {
		return ProdutoDAO.getProdutoByNome(nome);
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Produto addProduto(@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("id") int id, @FormDataParam("nome") String nome, @FormDataParam("preco") String preco, @FormDataParam("descricao") String descricao) {

		return ProdutoDAO.addProduto(id, nome, preco, descricao, uploadedInputStream);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Produto updateProduto(@PathParam("id") int id, @FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("id") int id, @FormDataParam("nome") String nome, @FormDataParam("preco") String preco, @FormDataParam("descricao") String descricao) {
		
		if(contentDispositionHeader.getFileName() == null) {
			return ProdutoDAO.updateProduto(id, nome, preco, descricao, null);	
		} else {
			return ProdutoDAO.updateProduto(id, nome, preco, descricao, uploadedInputStream);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deleteProduto(@PathParam("id") int id) {
		ProdutoDAO.deleteProduto(id);
	}
	
	//Session
	@POST
	@Path("/oi")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response hello(@Context HttpServletRequest req, @FormDataParam("nome") String nome) {
        HttpSession session = req.getSession(true);
        Object foo = session.getAttribute("foo");
        System.out.println(session.getId());
        
        if (foo != null) {
            System.out.println(foo.toString());
        } else {
            foo = "bar";
            session.setAttribute("foo", foo);
            System.out.println("first");
        }
        
        return Response.status(Status.OK).entity(foo.toString()).build();
    }
}