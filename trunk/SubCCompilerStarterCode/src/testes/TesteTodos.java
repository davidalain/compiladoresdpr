package testes;

import junit.framework.Test;
import junit.framework.TestSuite;
import testes.AST.R001;
import testes.AST.R002;

public class TesteTodos 
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("Todos casos de testes");
		
		suite.addTestSuite(R001.class);
		suite.addTestSuite(R002.class);
		
		return suite;
	}

}
