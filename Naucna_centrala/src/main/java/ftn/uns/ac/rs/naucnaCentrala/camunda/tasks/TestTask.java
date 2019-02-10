package ftn.uns.ac.rs.naucnaCentrala.camunda.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class TestTask implements JavaDelegate{

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("jebi seeeeeeeeeeeeeeeeeeeee");
		
	}

}
