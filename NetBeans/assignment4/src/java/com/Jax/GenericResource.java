/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Jax;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import databaseCredentials.credentials;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import products.products;

/**
 * REST Web Service
 *
 * @author Shriman
 */
@Path("/")
public class GenericResource {

    Connection con;
    products products = new products();
    ArrayList<products> productObj = new ArrayList<>();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
        con = credentials.getConnection();
    }

    /**
     * Retrieves representation of an instance of com.Jax.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/getProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<products> getXml() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products");

        while (rs.next()) {
            products products = new products(rs.getInt("productID"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"));
            productObj.add(products);
        }
        return productObj;
    }

    @GET
    @Path("/getProducts/{productID}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<products> oneProductOnly(@PathParam("productID") int pID) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE productID = " + pID);

        while (rs.next()) {
            products products = new products(rs.getInt("productID"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"));
            productObj.add(products);
        }
        return productObj;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/post")
    public void createProduct(String content) throws ParseException, SQLException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        Object id = json.get("id");
        String newProductID = id.toString();
        int productID = Integer.parseInt(newProductID);

        Object newName = json.get("newName");
        String name = newName.toString();

        Object newDescription = json.get("newDescription");
        String description = newDescription.toString();

        Object qty = json.get("qty");
        String newQty = qty.toString();
        int quantity = Integer.parseInt(newQty);

        Statement stmt = con.createStatement();
        String query = "INSERT INTO products VALUES('" + productID + "','" + newName + "','" + newDescription + "','" + quantity + "')";
        stmt.executeUpdate(query);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public void deleteProduct(String content) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        Object id = json.get("id");
        String newProductID = id.toString();
        int productID = Integer.parseInt(newProductID);

        Object newName = json.get("newName");
        String name = newName.toString();

        Object newDescription = json.get("newDescription");
        String description = newDescription.toString();

        Object qty = json.get("qty");
        String newQty = qty.toString();
        int quantity = Integer.parseInt(newQty);

        Statement stmt = con.createStatement();
        String query = "DELETE FROM products WHERE productID =" + productID;
        stmt.executeUpdate(query);
    }
}
