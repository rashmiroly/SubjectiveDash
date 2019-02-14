package org.rashmi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;



/**
 * Servlet implementation class TestSrvlet
 */
@WebServlet(name = "TestServlet", description = "Test Datasource form context", urlPatterns = { "/TestServletPath" })
public class TestSrvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DataSource dataSource;
	private Connection connection;
	private Statement statement;
	
	private final static Logger logger = Logger.getLogger(TestSrvlet.class);
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSrvlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			// Get DataSource
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/dashboarddb");

			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			logger.info("Starting doGet method");
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "insert into login(user_name,pwd) values(\"test\",\"admin\")";
			statement.execute(query);

			String query1 = "select * from login";
			ResultSet rs = statement.executeQuery(query1);
			while (rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
